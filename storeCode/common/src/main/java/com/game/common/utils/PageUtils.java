package com.game.common.utils;

import com.github.pagehelper.PageHelper;
import com.game.common.core.page.PageDomain;
import com.game.common.core.page.TableSupport;
import com.game.common.utils.sql.SqlUtil;

/**
 * Paging tool class
 *
 * @author Yu Yue
 */
public class PageUtils extends PageHelper
{
    /**
     * Set request paging Data
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * Clean up paging thread variables
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}
