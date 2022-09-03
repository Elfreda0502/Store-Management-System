package com.game.user.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.game.common.annotation.Excel;
import com.game.common.core.domain.BaseEntity;

/**
 * Useraddress对象 user_address
 * 
 * @author Yu Yue
 * @date 2022-05-13
 */
public class UserAddress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    
    private Long id;

    
    @Excel(name = "address")
    private String address;

    
    @Excel(name = "收货姓名")
    private String consignee;

    
    @Excel(name = "收货手机号码")
    private String receivingNumber;

    
    private Long userId;

    
    @Excel(name = "是否 default 地址")
    private Long isDefault;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setConsignee(String consignee) 
    {
        this.consignee = consignee;
    }

    public String getConsignee() 
    {
        return consignee;
    }
    public void setReceivingNumber(String receivingNumber) 
    {
        this.receivingNumber = receivingNumber;
    }

    public String getReceivingNumber() 
    {
        return receivingNumber;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setDefault(Long isDefault)
    {
        this.isDefault = isDefault;
    }

    public Long getDefault() 
    {
        return isDefault;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("address", getAddress())
            .append("consignee", getConsignee())
            .append("receivingNumber", getReceivingNumber())
            .append("userId", getUserId())
            .append("default", getDefault())
            .toString();
    }
}
