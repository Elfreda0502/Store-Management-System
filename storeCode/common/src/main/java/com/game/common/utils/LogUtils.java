package com.game.common.utils;

/**
 * Process and record log files
 *
 * @author Yu Yue
 */
public class LogUtils
{
    public static String getBlock(Object msg)
    {
        if (msg == null)
        {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
