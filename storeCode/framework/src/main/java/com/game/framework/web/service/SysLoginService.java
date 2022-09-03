package com.game.framework.web.service;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.game.common.constant.Constants;
import com.game.common.core.domain.entity.SysUser;
import com.game.common.core.domain.model.LoginUser;
import com.game.common.core.redis.RedisCache;
import com.game.common.exception.ServiceException;
import com.game.common.exception.user.CaptchaException;
import com.game.common.exception.user.CaptchaExpireException;
import com.game.common.exception.user.UserPasswordNotMatchException;
import com.game.common.utils.DateUtils;
import com.game.common.utils.MessageUtils;
import com.game.common.utils.StringUtils;
import com.game.common.utils.ServletUtils;
import com.game.common.utils.ip.IpUtils;
import com.game.framework.manager.AsyncManager;
import com.game.framework.manager.factory.AsyncFactory;
import com.game.system.service.ISysConfigService;
import com.game.system.service.ISysUserService;

/**
 * Login verification method
 *
 * @author Yu Yue
 */
@Component
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    /**
     * Login authentication
     *
     * @param username Username
     * @param password password
     * @param code verification code
     * @param uuid unique identifier
     * @return result
     */
    public String login(String username, String password, String code, String uuid)
    {
        boolean captchaOnOff = configService.selectCaptchaOnOff();
        
        if (captchaOnOff)
        {
            validateCaptcha(username, code, uuid);
        }
        // User authentication
        Authentication authentication = null;
        try
        {
            
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        
        return tokenService.createToken(loginUser);
    }

    /**
     * Verify verification code
     *
     * @param username Username
     * @param code verification code
     * @param uuid unique identifier
     * @return result
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    /**
     * Record login information
     *
     * @param userId UserID
     */
    public void recordLoginInfo(Long userId)
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
}
