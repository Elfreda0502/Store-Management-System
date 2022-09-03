package com.game.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.game.common.enums.BusinessType;
import com.game.common.enums.OperatorType;

/**
 * Custom operation logging annotations
 *
 * @author Yu Yue
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * module
     */
    public String title() default "";

    /**
     * Function
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * Operator category
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * Whether to save the parameters of the request
     */
    public boolean isSaveRequestData() default true;

    /**
     * Whether to save the response parameters
     */
    public boolean isSaveResponseData() default true;
}