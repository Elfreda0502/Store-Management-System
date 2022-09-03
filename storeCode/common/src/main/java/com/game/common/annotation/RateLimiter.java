package com.game.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.game.common.constant.Constants;
import com.game.common.enums.LimitType;

/**
 * Current limit annotation
 *
 * @author Yu Yue
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter
{
    /**
     * Current limiting key
     */
    public String key() default Constants.RATE_LIMIT_KEY;

    /**
     * Current limiting time, in seconds
     */
    public int time() default 60;

    /**
     * Current limit times
     */
    public int count() default 100;

    /**
     * Current limiting type
     */
    public LimitType limitType() default LimitType.DEFAULT;
}