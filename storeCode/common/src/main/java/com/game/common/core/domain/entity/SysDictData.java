package com.game.common.core.domain.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.game.common.annotation.Excel;
import com.game.common.annotation.Excel.ColumnType;
import com.game.common.constant.UserConstants;
import com.game.common.core.domain.BaseEntity;

/**
 * DictionaryData table sys_dict_data
 *
 * @author Yu Yue
 */
public class SysDictData extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    @Excel(name = "Dictionary Encoding", cellType = ColumnType.NUMERIC)
    private Long dictCode;


    @Excel(name = "Dictionary sort", cellType = ColumnType.NUMERIC)
    private Long dictSort;


    @Excel(name = "Dictionary Label")
    private String dictLabel;


    @Excel(name = "Dictionary key value")
    private String dictValue;


    @Excel(name = "Dictionary type")
    private String dictType;


    private String cssClass;


    private String listClass;


    @Excel(name = "Is default ", readConverterExp = "Y=Yes, N=No")
    private String isDefault;


    @Excel(name = "State", readConverterExp = "0=normal, 1=disabled")
    private String status;

    public Long getDictCode()
    {
        return dictCode;
    }

    public void setDictCode(Long dictCode)
    {
        this.dictCode = dictCode;
    }

    public Long getDictSort()
    {
        return dictSort;
    }

    public void setDictSort(Long dictSort)
    {
        this.dictSort = dictSort;
    }

    @NotBlank(message = "Dictionary tag cannot be empty")
    @Size(min = 0, max = 100, message = "Dictionary tag length cannot exceed 100 characters")
    public String getDictLabel()
    {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel)
    {
        this.dictLabel = dictLabel;
    }

    @NotBlank(message = "Dictionary key value cannot be empty")
    @Size(min = 0, max = 100, message = "Dictionary key value cannot exceed 100 characters")
    public String getDictValue()
    {
        return dictValue;
    }

    public void setDictValue(String dictValue)
    {
        this.dictValue = dictValue;
    }

    @NotBlank(message = "Dictionary type cannot be empty")
    @Size(min = 0, max = 100, message = "Dictionary type length cannot exceed 100 characters")
    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    @Size(min = 0, max = 100, message = "The length of the style attribute cannot exceed 100 characters")
    public String getCssClass()
    {
        return cssClass;
    }

    public void setCssClass(String cssClass)
    {
        this.cssClass = cssClass;
    }

    public String getListClass()
    {
        return listClass;
    }

    public void setListClass(String listClass)
    {
        this.listClass = listClass;
    }

    public boolean getDefault()
    {
        return UserConstants.YES.equals(this.isDefault) ? true : false;
    }

    public String getIsDefault()
    {
        return isDefault;
    }

    public void setIsDefault(String isDefault)
    {
        this.isDefault = isDefault;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dictCode", getDictCode())
            .append("dictSort", getDictSort())
            .append("dictLabel", getDictLabel())
            .append("dictValue", getDictValue())
            .append("dictType", getDictType())
            .append("cssClass", getCssClass())
            .append("listClass", getListClass())
            .append("isDefault", getIsDefault())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
