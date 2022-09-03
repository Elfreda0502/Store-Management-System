package com.game.common.exception.user;

import com.game.common.exception.base.BaseException;

/**
 * User information exception class
 *
 * @author Yu Yue
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
