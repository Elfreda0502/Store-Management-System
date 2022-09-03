package com.game.common.core.domain.model;
/**
 * User login object
 *
 * @author Yu Yue
 */
public class LoginBody
{
    /**
     * Username
     */
    private String username;

    /**
     * User password
     */
    private String password;

    /**
     * verification code
     */
    private String code;

    /**
     * Uniquely identifies
     */
    private String uuid;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
}
