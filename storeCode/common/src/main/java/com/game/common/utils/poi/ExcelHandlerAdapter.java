package com.game.common.utils.poi;

/**
 * ExcelData format processing adapter
 *
 * @author Yu Yue
 */
public interface ExcelHandlerAdapter
{
    /**
     * format
     *
     * @param value cell Data value
     * @param args excel annotation args parameter group
     *
     * @return the processed value
     */
    Object format(Object value, String[] args);
}
