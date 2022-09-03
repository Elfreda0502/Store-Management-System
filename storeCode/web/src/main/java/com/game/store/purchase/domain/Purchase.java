package com.game.store.purchase.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.game.common.annotation.Excel;
import com.game.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 采购对象 purchase
 *
 * @author store
 * @date 2022-05-31
 */
public class Purchase extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    private Long id;


    @Excel(name = "品名")
    private String productName;


    @Excel(name = "商品Id")
    private Long productId;


    @Excel(name = "数量")
    private Long number;


    @Excel(name = "类型")
    private String type;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;


    @Excel(name = "客户")
    private String customer;


    @Excel(name = "地址")
    private String address;


    @Excel(name = "联系人")
    private String contacts;


    @Excel(name = "手机")
    private String phoneNumber;


    @Excel(name = "单位")
    private String unit;


    @Excel(name = "单价")
    private BigDecimal price;


    @Excel(name = "备注")
    private String remarks;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductName()
    {
        return productName;
    }
    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Long getProductId()
    {
        return productId;
    }
    public void setNumber(Long number)
    {
        this.number = number;
    }

    public Long getNumber()
    {
        return number;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setTime(Date time)
    {
        this.time = time;
    }

    public Date getTime()
    {
        return time;
    }
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }

    public String getCustomer()
    {
        return customer;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setContacts(String contacts)
    {
        this.contacts = contacts;
    }

    public String getContacts()
    {
        return contacts;
    }
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("productName", getProductName())
            .append("productId", getProductId())
            .append("number", getNumber())
            .append("type", getType())
            .append("time", getTime())
            .append("customer", getCustomer())
            .append("address", getAddress())
            .append("contacts", getContacts())
            .append("phoneNumber", getPhoneNumber())
            .append("unit", getUnit())
            .append("price", getPrice())
            .append("remarks", getRemarks())
            .toString();
    }
}
