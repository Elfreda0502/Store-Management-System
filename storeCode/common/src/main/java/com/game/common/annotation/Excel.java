package com.game.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import com.game.common.utils.poi.ExcelHandlerAdapter;

/**
 * Custom export ExcelData annotations
 *
 * @author Yu Yue
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel
{
    /**
     * Sort in excel when exporting
     */
    public int sort() default Integer.MAX_VALUE;

    /**
     * Names exported to Excel.
     */
    public String name() default "";

    /**
     * Date format, such as: yyyy-MM-dd
     */
    public String dateFormat() default "";

    /**
     * If it is Dictionary type, please set the type value of Dictionary (eg: sys_user_sex)
     */
    public String dictType() default "";

    /**
     * Read content to expression (eg: 0=male, 1=female, 2=unknown)
     */
    public String readConverterExp() default "";

    /**
     * delimiter, read the contents of the string group
     */
    public String separator() default ",";

    /**
     * BigDecimal precision default :-1 (default does not enable BigDecimal formatting)
     */
    public int scale() default -1;

    /**
     * BigDecimal rounding rules default :BigDecimal.ROUND_HALF_EVEN
     */
    public int roundingMode() default BigDecimal.ROUND_HALF_EVEN;

    /**
     * The height of each column in excel when exporting is in characters
     */
    public double height() default 14;

    /**
     * When exporting, the width of each column in excel is in characters
     */
    public double width() default 16;

    /**
     * Text suffix, such as % 90 becomes 90%
     */
    public String suffix() default "";

    /**
     * When the value is empty, the default value of the field
     */
    public String defaultValue() default "";

    /**
     * Tips
     */
    public String prompt() default "";

    /**
     * The setting can only select the column content that cannot be entered.
     */
    public String[] combo() default {};

    /**
     * Whether to export Data, to meet the needs: Sometimes we need to export a template, which is required for the title but the content needs to be filled in manually by the User.
     */
    public boolean isExport() default true;

    /**
     * The property name in another class, supports multi-level access, separated by decimal points
     */
    public String targetAttr() default "";

    /**
     * Whether to automatically count Data, append a line at the end to count the sum of Data
     */
    public boolean isStatistics() default false;

    /**
     * Export type (0 number 1 string)
     */
    public ColumnType cellType() default ColumnType.STRING;

    /**
     * Export font color
     */
    public IndexedColors color() default IndexedColors.BLACK;

    /**
     * Export field alignment
     */
    public HorizontalAlignment align() default HorizontalAlignment.CENTER;

    /**
     * Custom Data Processor
     */
    public Class<?> handler() default ExcelHandlerAdapter.class;

    /**
     * Custom data processor parameters
     */
    public String[] args() default {};

    public enum Align
    {
        AUTO(0), LEFT(1), CENTER(2), RIGHT(3);
        private final int value;

        Align(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

    /**
     * Field type (0: Export Import; 1: Export only; 2: Import only)
     */
    Type type() default Type.ALL;

    public enum Type
    {
        ALL(0), EXPORT(1), IMPORT(2);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

    public enum ColumnType
    {
        NUMERIC(0), STRING(1), IMAGE(2);
        private final int value;

        ColumnType(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }
}