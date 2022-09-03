package com.game.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.game.common.enums.DataSourceType;

/**
 * Customize multiple data source switching annotations
 *
 * Priority: method first, class second, if the method overrides the Data source type on the class, the method shall prevail, otherwise the class shall prevail
 *
 * @author Yu Yue
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource
{
    /**
     * Switch Data source name
     */
    public DataSourceType value() default DataSourceType.MASTER;
}