package com.game.common.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import com.game.common.utils.spring.SpringUtils;

/**
 * Get i18n resource file
 *
 * @author Yu Yue
 */
public class MessageUtils
{
    /**
     * According to the message key and parameters, get the Cancel message and delegate it to spring messageSource
     *
     * @param code message key
     * @param args parameter
     * @return get the internationalized translation value
     */
    public static String message(String code, Object... args)
    {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
