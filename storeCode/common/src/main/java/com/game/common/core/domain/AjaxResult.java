package com.game.common.core.domain;

import java.util.HashMap;
import com.game.common.constant.HttpStatus;
import com.game.common.utils.StringUtils;

/**
 * Operation message reminder
 *
 * @author Yu Yue
 */
public class AjaxResult extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;


    public static final String CODE_TAG = "code";


    public static final String MSG_TAG = "msg";


    public static final String DATA_TAG = "data";

    /**
     * Initialize a newly created AjaxResult object to represent an empty message.
     */
    public AjaxResult()
    {
    }

    /**
     * Initialize a newly created AjaxResult object
     *
     * @param code State code
     * @param msg return content
     */
    public AjaxResult(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * Initialize a newly created AjaxResult object
     *
     * @param code State code
     * @param msg return content
     * @param data Data object
     */
    public AjaxResult(int code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data))
        {
            super.put(DATA_TAG, data);
        }
    }
    /**
     * return success message
     *
     * @return success message
     */
    public static AjaxResult success()
    {
        return AjaxResult.success("Operation successful");
    }

    /**
     * Return success Data
     *
     * @return success message
     */
    public static AjaxResult success(Object data)
    {
        return AjaxResult.success("Operation successful", data);
    }

    /**
     * return success message
     *
     * @param msg return content
     * @return success message
     */
    public static AjaxResult success(String msg)
    {
        return AjaxResult.success(msg, null);
    }

    /**
     * return success message
     *
     * @param msg return content
     * @param data Data object
     * @return success message
     */
    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }
    /**
     * return error message
     *
     * @return
     */
    public static AjaxResult error()
    {
        return AjaxResult.error("Operation failed");
    }

    /**
     * return error message
     *
     * @param msg return content
     * @return warning message
     */
    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * return error message
     *
     * @param msg return content
     * @param data Data object
     * @return warning message
     */
    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * return error message
     *
     * @param code State code
     * @param msg return content
     * @return warning message
     */
    public static AjaxResult error(int code, String msg)
    {
        return new AjaxResult(code, msg, null);
    }

    /**
     * Convenient chain call
     *
     * @param key key
     * @param value value
     * @return Data object
     */
    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}
