package com.game.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotations prevent repeated form submissions
 *
 * @author Yu Yue
 *
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit
{
    /**
     * Interval time (ms), less than this time is regarded as repeated submission
     */
    public int interval() default 5000;

    /**
     * Prompt message
     */
    public String message() default "Duplicate submissions are not allowed, please try again later";
}