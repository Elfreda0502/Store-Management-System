package com.game.leaving.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.game.common.annotation.Excel;
import com.game.common.core.domain.BaseEntity;

/**
 * 留言对象 sys_leaving
 * 
 * @author Yu Yue
 * @date 2022-05-24
 */
public class SysLeaving extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    
    private Long id;

    
    @Excel(name = "Userid")
    private Long uid;

    
    @Excel(name = "留言信息")
    private String leavingMessage;

    
    @Excel(name = "回复信息")
    private String replyMessage;

    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "留言时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date leavingTime;

    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "回复时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date replyTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUid(Long uid) 
    {
        this.uid = uid;
    }

    public Long getUid() 
    {
        return uid;
    }
    public void setLeavingMessage(String leavingMessage) 
    {
        this.leavingMessage = leavingMessage;
    }

    public String getLeavingMessage() 
    {
        return leavingMessage;
    }
    public void setReplyMessage(String replyMessage) 
    {
        this.replyMessage = replyMessage;
    }

    public String getReplyMessage() 
    {
        return replyMessage;
    }
    public void setLeavingTime(Date leavingTime) 
    {
        this.leavingTime = leavingTime;
    }

    public Date getLeavingTime() 
    {
        return leavingTime;
    }
    public void setReplyTime(Date replyTime) 
    {
        this.replyTime = replyTime;
    }

    public Date getReplyTime() 
    {
        return replyTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("uid", getUid())
            .append("leavingMessage", getLeavingMessage())
            .append("replyMessage", getReplyMessage())
            .append("leavingTime", getLeavingTime())
            .append("replyTime", getReplyTime())
            .toString();
    }
}
