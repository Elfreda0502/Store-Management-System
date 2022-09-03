package com.game.common.core.page;

import com.game.common.core.text.Convert;
import com.game.common.utils.ServletUtils;

/**
 * Form Data processing
 *
 * @author Yu Yue
 */
public class TableSupport
{
    /**
     * current record start index
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * Number of records displayed per page
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * Sort column
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * The sort direction "desc" or "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * Rationalization of paging parameters
     */
    public static final String REASONABLE = "reasonable";

    /**
     * Encapsulate the paging object
     */
    public static PageDomain getPageDomain()
    {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(Convert.toInt(ServletUtils.getParameter(PAGE_NUM), 1));
        pageDomain.setPageSize(Convert.toInt(ServletUtils.getParameter(PAGE_SIZE), 10));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
        pageDomain.setReasonable(ServletUtils.getParameterToBool(REASONABLE));
        return pageDomain;
    }

    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }
}
