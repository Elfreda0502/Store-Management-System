package com.game.framework.config;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import com.game.common.utils.ServletUtils;

/**
 * Service related configuration
 *
 * @author Yu Yue
 */
@Component
public class ServerConfig
{
    /**
     * Get the complete request path, including: domain name, port, context access path
     *
     * @return service address
     */
    public String getUrl()
    {
        HttpServletRequest request = ServletUtils.getRequest();
        return getDomain(request);
    }

    public static String getDomain(HttpServletRequest request)
    {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }
}
