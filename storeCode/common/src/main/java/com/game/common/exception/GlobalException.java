package com.game.common.exception;

/**
 * global exception
 *
 * @author Yu Yue
 */
public class GlobalException extends RuntimeException
{

    private static final long serialVersionUID = 1L;

    /**
     * Error message
     */
    private String message;

    /**
     * Error details, internal debugging errors
     *
     * Consistent design with {@link CommonResult#getDetailMessage()}
     */
    private String detailMessage;

    /**
     * Empty constructor to avoid deserialization problems
     */
    public GlobalException()
    {
    }

    public GlobalException(String message)
    {
        this.message = message;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    public GlobalException setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
        return this;
    }

    public String getMessage()
    {
        return message;
    }

    public GlobalException setMessage(String message)
    {
        this.message = message;
        return this;
    }
}