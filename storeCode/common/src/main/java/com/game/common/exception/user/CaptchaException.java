package com.game.common.exception.user;

/**
 * Verification code error exception class
 *
 * @author Yu Yue
 */
public class CaptchaException extends UserException
{
    private static final long serialVersionUID = 1L;

    public CaptchaException()
    {
        super("user.jcaptcha.error", null);
    }
}
