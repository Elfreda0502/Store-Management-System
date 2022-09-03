package com.game.framework.manager.factory;

import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.game.common.constant.Constants;
import com.game.common.utils.LogUtils;
import com.game.common.utils.ServletUtils;
import com.game.common.utils.StringUtils;
import com.game.common.utils.ip.AddressUtils;
import com.game.common.utils.ip.IpUtils;
import com.game.common.utils.spring.SpringUtils;
import com.game.system.domain.SysLogininfor;
import com.game.system.domain.SysOperLog;
import com.game.system.service.ISysLogininforService;
import com.game.system.service.ISysOperLogService;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * Asynchronous factory (for generating tasks)
 *
 * @author Yu Yue
 */
public class AsyncFactory
{
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * Record login information
     *
     * @param username Username
     * @param status State
     * @param message message
     * @param args list
     * @return task task
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message,
            final Object... args)
    {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        return new TimerTask()
        {
            @Override
            public void run()
            {
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                
                sys_user_logger.info(s.toString(), args);
                
                String os = userAgent.getOperatingSystem().getName();
                
                String browser = userAgent.getBrowser().getName();
                
                SysLogininfor logininfor = new SysLogininfor();
                logininfor.setUserName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                
                if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER))
                {
                    logininfor.setStatus(Constants.SUCCESS);
                }
                else if (Constants.LOGIN_FAIL.equals(status))
                {
                    logininfor.setStatus(Constants.FAIL);
                }
                
                SpringUtils.getBean(ISysLogininforService.class).insertLogininfor(logininfor);
            }
        };
    }

    /**
     * Operational logging
     *
     * @param operLog operation log information
     * @return task task
     */
    public static TimerTask recordOper(final SysOperLog operLog)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(ISysOperLogService.class).insertOperlog(operLog);
            }
        };
    }
}
