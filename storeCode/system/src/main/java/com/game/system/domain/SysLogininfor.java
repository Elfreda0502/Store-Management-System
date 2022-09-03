package com.game.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.game.common.annotation.Excel;
import com.game.common.annotation.Excel.ColumnType;
import com.game.common.core.domain.BaseEntity;

/**
 * System access log table sys_logininfor
 *
 * @author Yu Yue
 */
public class SysLogininfor extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    @Excel(name = "序号", cellType = ColumnType.NUMERIC)
    private Long infoId;


    @Excel(name = "User账号")
    private String userName;


    @Excel(name = "登录 State", readConverterExp = "0=成功,1=失败")
    private String status;


    @Excel(name = "登录地址")
    private String ipaddr;


    @Excel(name = "登录地点")
    private String loginLocation;


    @Excel(name = "浏览器")
    private String browser;


    @Excel(name = "操作系统")
    private String os;


    @Excel(name = "提示消息")
    private String msg;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    public Long getInfoId()
    {
        return infoId;
    }

    public void setInfoId(Long infoId)
    {
        this.infoId = infoId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getIpaddr()
    {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr)
    {
        this.ipaddr = ipaddr;
    }

    public String getLoginLocation()
    {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation)
    {
        this.loginLocation = loginLocation;
    }

    public String getBrowser()
    {
        return browser;
    }

    public void setBrowser(String browser)
    {
        this.browser = browser;
    }

    public String getOs()
    {
        return os;
    }

    public void setOs(String os)
    {
        this.os = os;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Date getLoginTime()
    {
        return loginTime;
    }

    public void setLoginTime(Date loginTime)
    {
        this.loginTime = loginTime;
    }
}
