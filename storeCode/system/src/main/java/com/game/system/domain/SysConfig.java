package com.game.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.game.common.annotation.Excel;
import com.game.common.annotation.Excel.ColumnType;
import com.game.common.core.domain.BaseEntity;

/**
 * Parameter configuration table sys_config
 *
 * @author Yu Yue
 */
public class SysConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    @Excel(name = "parameter primary key", cellType = ColumnType.NUMERIC)
    private Long configId;


    @Excel(name = "parameter name")
    private String configName;


    @Excel(name = "parameter key name")
    private String configKey;


    @Excel(name = "parameter key value")
    private String configValue;


    @Excel(name = "system built-in", readConverterExp = "Y=yes,N=no")
    private String configType;

    public Long getConfigId()
    {
        return configId;
    }

    public void setConfigId(Long configId)
    {
        this.configId = configId;
    }

    @NotBlank(message = "parameter name cannot be empty")
    @Size(min = 0, max = 100, message = "Parameter name cannot exceed 100 characters")
    public String getConfigName()
    {
        return configName;
    }

    public void setConfigName(String configName)
    {
        this.configName = configName;
    }

    @NotBlank(message = "The length of the parameter key name cannot be empty")
    @Size(min = 0, max = 100, message = "The length of the parameter key name cannot exceed 100 characters")
    public String getConfigKey()
    {
        return configKey;
    }

    public void setConfigKey(String configKey)
    {
        this.configKey = configKey;
    }

    @NotBlank(message = "Parameter key value cannot be empty")
    @Size(min = 0, max = 500, message = "Parameter key value length cannot exceed 500 characters")
    public String getConfigValue()
    {
        return configValue;
    }

    public void setConfigValue(String configValue)
    {
        this.configValue = configValue;
    }

    public String getConfigType()
    {
        return configType;
    }

    public void setConfigType(String configType)
    {
        this.configType = configType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("configId", getConfigId())
            .append("configName", getConfigName())
            .append("configKey", getConfigKey())
            .append("configValue", getConfigValue())
            .append("configType", getConfigType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
