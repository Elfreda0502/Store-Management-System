package com.game.framework.web.service;


import com.game.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.game.common.constant.Constants;
import com.game.common.constant.UserConstants;
import com.game.common.core.domain.entity.SysUser;
import com.game.common.core.domain.model.RegisterBody;
import com.game.common.core.redis.RedisCache;
import com.game.common.exception.user.CaptchaException;
import com.game.common.exception.user.CaptchaExpireException;
import com.game.common.utils.MessageUtils;
import com.game.common.utils.SecurityUtils;
import com.game.common.utils.StringUtils;
import com.game.framework.manager.AsyncManager;
import com.game.framework.manager.factory.AsyncFactory;
import com.game.system.service.ISysConfigService;
import com.game.system.service.ISysUserService;

/**
 * Registration verification method
 *
 * @author Yu Yue
 */
@Component
public class SysRegisterService
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;


    @Autowired
    private ISysRoleService sysRoleService;


    /**
     * register
     */
    public String register(RegisterBody registerBody)
    {



        String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword();

        boolean captchaOnOff = configService.selectCaptchaOnOff();

        if (captchaOnOff)
        {
            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
        }

        if (StringUtils.isEmpty(username))
        {
            msg = "Username cannot be empty";
        }
        else if (StringUtils.isEmpty(password))
        {
            msg = "User password cannot be empty";
        }
        else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            msg = "Account length must be between 2 and 20 characters";
        }
        else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            msg = "Password length must be between 5 and 20 characters";
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(username)))
        {
            msg = "Save user'" + username + "'failed, the registered account already exists";
        }
        else
        {
            SysUser sysUser = new SysUser();
            sysUser.setUserName(username);
            sysUser.setNickName(username);
            sysUser.setEmail(registerBody.getEmail());
            sysUser.setPassword(SecurityUtils.encryptPassword(registerBody.getPassword()));
            sysUser.setDeptId(207L);
            boolean regFlag = userService.registerUser(sysUser);
            if (!regFlag)
            {
                msg = "Registration failed, please contact system administrator";
            }
            else
            {

                Long[] userId = {sysUser.getUserId()};
                sysRoleService.insertAuthUsers(2L,userId);
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER,
                        MessageUtils.message("user.register.success")));
            }
        }
        return msg;
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
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            throw new CaptchaException();
        }
    }
}
