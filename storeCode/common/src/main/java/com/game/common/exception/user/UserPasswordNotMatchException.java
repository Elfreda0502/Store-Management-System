package com.game.common.exception.user;

/**
 * User password is incorrect or does not meet the specification exception class
 *
 * @author Yu Yue
 */
public class UserPasswordNotMatchException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException()
    {
        super("user.password.not.match", null);
    }
}
