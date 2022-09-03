package com.game.common.core.page;

import java.io.Serializable;
import java.util.List;

/**
 * Table paging Data object
 *
 * @author Yu Yue
 */
public class TableDataInfo implements Serializable
{
    private static final long serialVersionUID = 1L;


    private long total;


    private List<?> rows;


    private int code;


    private String msg;

    /**
     * Form Data object
     */
    public TableDataInfo()
    {
    }

    /**
     * Pagination
     *
     * @param list list Data
     * @param total total number of records
     */
    public TableDataInfo(List<?> list, int total)
    {
        this.rows = list;
        this.total = total;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<?> getRows()
    {
        return rows;
    }

    public void setRows(List<?> rows)
    {
        this.rows = rows;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }
}
