package com.game.common.utils.ip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.game.common.config.RuoYiConfig;
import com.game.common.constant.Constants;
import com.game.common.utils.StringUtils;
import com.game.common.utils.http.HttpUtils;

/**
 * Get address class
 *
 * @author Yu Yue
 */
public class AddressUtils
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    // IP address query
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";


    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip)
    {

        if (IpUtils.internalIp(ip))
        {
            return "Intranet IP";
        }
        if (RuoYiConfig.isAddressEnabled())
        {
            try
            {
                String rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", Constants.GBK);
                if (StringUtils.isEmpty(rspStr))
                {
                    log.error("Get geolocation exception {}", ip);
                    return UNKNOWN;
                }
                JSONObject obj = JSONObject.parseObject(rspStr);
                String region = obj.getString("pro");
                String city = obj.getString("city");
                return String.format("%s %s", region, city);
            }
            catch (Exception e)
            {
                log.error("Get geolocation exception {}", ip);
            }
        }
        return UNKNOWN;
    }
}
