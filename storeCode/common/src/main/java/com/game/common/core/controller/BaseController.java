package com.game.common.core.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.game.common.constant.HttpStatus;
import com.game.common.core.domain.AjaxResult;
import com.game.common.core.domain.model.LoginUser;
import com.game.common.core.page.PageDomain;
import com.game.common.core.page.TableDataInfo;
import com.game.common.core.page.TableSupport;
import com.game.common.utils.DateUtils;
import com.game.common.utils.PageUtils;
import com.game.common.utils.SecurityUtils;
import com.game.common.utils.StringUtils;
import com.game.common.utils.sql.SqlUtil;

/**
 * Web layer general data processing
 *
 * @author Yu Yue
 */
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Automatically convert the date format string passed from the front desk to Date type
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date type conversion
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * Set request paging Data
     */
    protected void startPage()
    {
        PageUtils.startPage();
    }

    /**
     * Set the request sorting Data
     */
    protected void startOrderBy()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (StringUtils.isNotEmpty(pageDomain.getOrderBy()))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.orderBy(orderBy);
        }
    }

    /**
     * Clean up paging thread variables
     */
    protected void clearPage()
    {
        PageUtils.clearPage();
    }

    /**
     * Response to request paging Data
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("Query successful");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * return success
     */
    public AjaxResult success()
    {
        return AjaxResult.success();
    }

    /**
     * return failure message
     */
    public AjaxResult error()
    {
        return AjaxResult.error();
    }

    /**
     * return success message
     */
    public AjaxResult success(String message)
    {
        return AjaxResult.success(message);
    }

    /**
     * return failure message
     */
    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }

    /**
     * Response return result
     *
     * @param rows affects the number of rows
     * @return operation result
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * Response return result
     *
     * @param result result
     * @return operation result
     */
    protected AjaxResult toAjax(boolean result)
    {
        return result ? success() : error();
    }

    /**
     * page jump
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }

    /**
     * Get User cache information
     */
    public LoginUser getLoginUser()
    {
        return SecurityUtils.getLoginUser();
    }

    /**
     * Get the login Userid
     */
    public Long getUserId()
    {
        return getLoginUser().getUserId();
    }

    /**
     * Get the login department id
     */
    public Long getDeptId()
    {
        return getLoginUser().getDeptId();
    }

    /**
     * Get the login username
     */
    public String getUsername()
    {
        return getLoginUser().getUsername();
    }
}
