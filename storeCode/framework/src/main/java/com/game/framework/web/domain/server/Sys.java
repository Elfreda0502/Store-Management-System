package com.game.framework.web.domain.server;

/**
 * System related information
 *
 * @author Yu Yue
 */
public class Sys
{
    /**
     * name of server
     */
    private String computerName;

    /**
     *Server Ip
     */
    private String computerIp;

    /**
     * project path
     */
    private String userDir;

    /**
     * operating system
     */
    private String osName;

    /**
     * system structure
     */
    private String osArch;

    public String getComputerName()
    {
        return computerName;
    }

    public void setComputerName(String computerName)
    {
        this.computerName = computerName;
    }

    public String getComputerIp()
    {
        return computerIp;
    }

    public void setComputerIp(String computerIp)
    {
        this.computerIp = computerIp;
    }

    public String getUserDir()
    {
        return userDir;
    }

    public void setUserDir(String userDir)
    {
        this.userDir = userDir;
    }

    public String getOsName()
    {
        return osName;
    }

    public void setOsName(String osName)
    {
        this.osName = osName;
    }

    public String getOsArch()
    {
        return osArch;
    }

    public void setOsArch(String osArch)
    {
        this.osArch = osArch;
    }
}
