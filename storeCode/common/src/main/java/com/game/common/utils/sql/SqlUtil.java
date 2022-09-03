package com.game.common.utils.sql;

import com.game.common.exception.UtilException;
import com.game.common.utils.StringUtils;

/**
 * sql operation tool class
 *
 * @author Yu Yue
 */
public class SqlUtil
{
    /**
     * Define common sql keywords
     */
    public static String SQL_REGEX = "select |insert |delete |update |drop |count |exec |chr |mid |master |truncate |char |and |declare ";

    /**
     * Only supports letters, numbers, underscores, spaces, commas, decimal points (multiple fields are supported)
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * Check characters to prevent injection bypass
     */
    public static String escapeOrderBySql(String value)
    {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value))
        {
            throw new UtilException("The parameter does not meet the specification and cannot be queried");
        }
        return value;
    }

    /**
     * Verify that the order by syntax conforms to the specification
     */
    public static boolean isValidOrderBySql(String value)
    {
        return value.matches(SQL_PATTERN);
    }

    /**
     * SQL keyword checking
     */
    public static void filterKeyword(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return;
        }
        String[] sqlKeywords = StringUtils.split(SQL_REGEX, "\\|");
        for (String sqlKeyword : sqlKeywords)
        {
            if (StringUtils.indexOfIgnoreCase(value, sqlKeyword) > -1)
            {
                throw new UtilException("Parameters are at risk of SQL injection");
            }
        }
    }
}
