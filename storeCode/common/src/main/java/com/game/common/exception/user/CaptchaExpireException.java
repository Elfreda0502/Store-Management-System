package com.game.common.exception.user;

/**
 * Verification code invalid exception class
 *
 * @author Yu Yue
 */
public class CaptchaExpireException extends UserException
{
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException()
    {
        super("user.jcaptcha.expire", null);
    }
}
