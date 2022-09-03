package com.game.framework.manager.factory.handle;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import com.alibaba.fastjson.JSON;
import com.game.common.constant.Constants;
import com.game.common.constant.HttpStatus;
import com.game.common.core.domain.AjaxResult;
import com.game.common.core.domain.model.LoginUser;
import com.game.common.utils.ServletUtils;
import com.game.common.utils.StringUtils;
import com.game.framework.manager.AsyncManager;
import com.game.framework.manager.factory.AsyncFactory;
import com.game.framework.web.service.TokenService;

/**
 * Custom exit handler class returns success
 *
 * @author Yu Yue
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler
{
    @Autowired
    private TokenService tokenService;

    /**
     * Exit processing
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser))
        {
            String userName = loginUser.getUsername();

            tokenService.delLoginUser(loginUser.getToken());

            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "Exit Successfully"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(HttpStatus.SUCCESS, "Exit Successfully")));
    }
}
