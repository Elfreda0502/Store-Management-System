package com.game.framework.manager.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data source switching processing
 *
 * @author Yu Yue
 */
public class DynamicDataSourceContextHolder
{
    public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    /**
     * Use ThreadLocal to maintain variables, ThreadLocal provides an independent copy of the variable for each thread that uses the variable,
     * So each thread can change its own copy independently without affecting the copies corresponding to other threads.
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * Set the variables of the Data source
     */
    public static void setDataSourceType(String dsType)
    {
        log.info("Switch to {}Data source", dsType);
        CONTEXT_HOLDER.set(dsType);
    }

    /**
     * Get the variable of the Data source
     */
    public static String getDataSourceType()
    {
        return CONTEXT_HOLDER.get();
    }

    /**
     * Clear the Data source variable
     */
    public static void clearDataSourceType()
    {
        CONTEXT_HOLDER.remove();
    }
}
