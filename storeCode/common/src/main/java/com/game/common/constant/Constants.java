package com.game.common.constant;

import io.jsonwebtoken.Claims;

/**
 * General constant information
 *
 * @author Yu Yue
 */
public class Constants
{
    /**
     * UTF-8 character set
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK character set
     */
    public static final String GBK = "GBK";

    /**
     * http request
     */
    public static final String HTTP = "http://";

    /**
     * https request
     */
    public static final String HTTPS = "https://";

    /**
     * Generic success sign
     */
    public static final String SUCCESS = "0";

    /**
     * Generic failure flag
     */
    public static final String FAIL = "1";

    /**
     * login successful
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * log out
     */
    public static final String LOGOUT = "Logout";

    /**
     * register
     */
    public static final String REGISTER = "Register";

    /**
     * Login failed
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * verification code redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * Login User redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * Anti-resubmit redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * Current limiting redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * Validity period of verification code (minutes)
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * token
     */
    public static final String TOKEN = "token";

    /**
     * token prefix
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * token prefix
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * UserID
     */
    public static final String JWT_USERID = "userid";

    /**
     * User name
     */
    public static final String JWT_USERNAME = Claims.SUBJECT;

    /**
     * User Avatar
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * Creation time
     */
    public static final String JWT_CREATED = "created";

    /**
     * User permissions
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * Parameter management cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * Dictionary management cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * resource mapping path prefix
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * RMI remote method call
     */
    public static final String LOOKUP_RMI = "rmi:";

    /**
     * LDAP remote method call
     */
    public static final String LOOKUP_LDAP = "ldap:";

    /**
     * LDAPS remote method call
     */
    public static final String LOOKUP_LDAPS = "ldaps:";

    /**
     * Scheduled task whitelist configuration (only the package name that is allowed to be accessed, you can add it yourself if you need it)
     */
// public static final String[] JOB_WHITELIST_STR = { "com.ruoyi" };

    /**
     * Characters that violate the timing task
     */
//    public static final String[] JOB_ERROR_STR = { "java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml",
//            "org.springframework", "org.apache", "com.ruoyi.common.utils.file" };
}
