package com.game.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.util.AntPathMatcher;
import com.game.common.constant.Constants;
import com.game.common.core.text.StrFormatter;

/**
 * String utility class
 *
 * @author Yu Yue
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils
{

    private static final String NULLSTR = "";


    private static final char SEPARATOR = '_';

    /**
     * Get parameter is not null
     *
     * @param value defaultValue The value to be judged
     * @return value return value
     */
    public static <T> T nvl(T value, T defaultValue)
    {
        return value != null ? value : defaultValue;
    }

    /**
     * * Determine whether a Collection is empty, including List, Set, Queue
     *
     * @param coll Collection to be judged
     * @return true: empty false: not empty
     */
    public static boolean isEmpty(Collection<?> coll)
    {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * Determine whether a Collection is not empty, including List, Set, Queue
     *
     * @param coll Collection to be judged
     * @return true: not empty false: empty
     */
    public static boolean isNotEmpty(Collection<?> coll)
    {
        return !isEmpty(coll);
    }

    /**
     * * Check if an array of objects is empty
     *
     * @param objects The array of objects to be judged
     ** @return true: empty false: not empty
     */
    public static boolean isEmpty(Object[] objects)
    {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * Check if an array of objects is not empty
     *
     * @param objects The array of objects to be judged
     * @return true: not empty false: empty
     */
    public static boolean isNotEmpty(Object[] objects)
    {
        return !isEmpty(objects);
    }

    /**
     * * Determine if a Map is empty
     *
     * @param map The Map to be judged
     * @return true: empty false: not empty
     */
    public static boolean isEmpty(Map<?, ?> map)
    {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * Determine if a Map is empty
     *
     * @param map The Map to be judged
     * @return true: not empty false: empty
     */
    public static boolean isNotEmpty(Map<?, ?> map)
    {
        return !isEmpty(map);
    }

    /**
     * * Determine if a string is an empty string
     *
     * @param str String
     * @return true: empty false: not empty
     */
    public static boolean isEmpty(String str)
    {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * * Determine if a string is a non-empty string
     *
     * @param str String
     * @return true: non-empty string false: empty string
     */
    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }

    /**
     * * Check if an object is empty
     *
     * @param object Object
     * @return true: empty false: not empty
     */
    public static boolean isNull(Object object)
    {
        return object == null;
    }

    /**
     * * Check if an object is not empty
     *
     * @param object Object
     * @return true: not empty false: empty
     */
    public static boolean isNotNull(Object object)
    {
        return !isNull(object);
    }

    /**
     * * Determine whether an object is an array type (array of Java primitive types)
     *
     * @param object object
     * @return true: is an array false: is not an array
     */
    public static boolean isArray(Object object)
    {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * remove spaces
     */
    public static String trim(String str)
    {
        return (str == null ? "" : str.trim());
    }

    /**
     * intercept string
     *
     * @param str string
     * @param start start
     * @return result
     */
    public static String substring(final String str, int start)
    {
        if (str == null)
        {
            return NULLSTR;
        }

        if (start < 0)
        {
            start = str.length() + start;
        }

        if (start < 0)
        {
            start = 0;
        }
        if (start > str.length())
        {
            return NULLSTR;
        }

        return str.substring(start);
    }

    /**
     * intercept string
     *
     * @param str string
     * @param start start
     * @param end end
     * @return result
     */
    public static String substring(final String str, int start, int end)
    {
        if (str == null)
        {
            return NULLSTR;
        }

        if (end < 0)
        {
            end = str.length() + end;
        }
        if (start < 0)
        {
            start = str.length() + start;
        }

        if (end > str.length())
        {
            end = str.length();
        }

        if (start > end)
        {
            return NULLSTR;
        }

        if (start < 0)
        {
            start = 0;
        }
        if (end < 0)
        {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * formatted text, {} means placeholder<br>
     * This method simply replaces the placeholders {} with the parameters in order <br>
     * If you want to output {}, use \\ to escape {, if you want to output the \ before {}, use double escape character \\\\<br>
     * Example: <br>
     * Usually used: format("this is {} for {}", "a", "b") -> this is a for b<br>
     * Escape {}: format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * Escape \: format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param template text template, the replaced part is represented by {}
     * @param params parameter value
     * @return formatted text
     */
    public static String format(String template, Object... params)
    {
        if (isEmpty(params) || isEmpty(template))
        {
            return template;
        }
        return StrFormatter.format(template, params);
    }

    /**
     * Whether it starts with http(s)://
     *
     * @param link link
     * @return result
     */
    public static boolean ishttp(String link)
    {
        return StringUtils.startsWithAny(link, Constants.HTTP, Constants.HTTPS);
    }

    /**
     * String to set
     *
     * @param str string
     * @param sep separator
     * @return set collection
     */
    public static final Set<String> str2Set(String str, String sep)
    {
        return new HashSet<String>(str2List(str, sep, true, false));
    }

    /**
     * String to list
     *
     * @param str string
     * @param sep separator
     * @param filterBlank filter pure blank
     * @param trim remove leading and trailing whitespace
     * @return list collection
     */
    public static final List<String> str2List(String str, String sep, boolean filterBlank, boolean trim)
    {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(str))
        {
            return list;
        }


        if (filterBlank && StringUtils.isBlank(str))
        {
            return list;
        }
        String[] split = str.split(sep);
        for (String string : split)
        {
            if (filterBlank && StringUtils.isBlank(string))
            {
                continue;
            }
            if (trim)
            {
                string = string.trim();
            }
            list.add(string);
        }

        return list;
    }

    /**
     * Finds whether the specified string contains any string in the specified string list and the string ignores case
     *
     * @param cs specifies the string
     * @param searchCharSequences array of strings to check
     * Whether @return contains any string
     */
    public static boolean containsAnyIgnoreCase(CharSequence cs, CharSequence... searchCharSequences)
    {
        if (isEmpty(cs) || isEmpty(searchCharSequences))
        {
            return false;
        }
        for (CharSequence testStr : searchCharSequences)
        {
            if (containsIgnoreCase(cs, testStr))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * CamelCase to underscore naming
     */
    public static String toUnderScoreCase(String str)
    {
        if (str == null)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();

        boolean preCharIsUpperCase = true;

        boolean curreCharIsUpperCase = true;

        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (i > 0)
            {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            }
            else
            {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1))
            {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * whether to contain a string
     *
     * @param str validation string
     * @param strs string group
     * @return contains return true
     */
    public static boolean inStringIgnoreCase(String str, String... strs)
    {
        if (str != null && strs != null)
        {
            for (String s : strs)
            {
                if (str.equalsIgnoreCase(trim(s)))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Converts underscore-capitalized strings to camel case. Returns an empty string if the underscore-capitalization-named string before conversion is empty. For example: HELLO_WORLD->HelloWorld
     *
     * @param name The string named with the underscore capitalization before conversion
     * @return the converted camel cased string
     */
    public static String convertToCamelCase(String name)
    {
        StringBuilder result = new StringBuilder();

        if (name == null || name.isEmpty())
        {

            return "";
        }
        else if (!name.contains("_"))
        {

            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }

        String[] camels = name.split("_");
        for (String camel : camels)
        {

            if (camel.isEmpty())
            {
                continue;
            }

            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * CamelCase For example: user_name->userName
     */
    public static String toCamelCase(String s)
    {
        if (s == null)
        {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            if (c == SEPARATOR)
            {
                upperCase = true;
            }
            else if (upperCase)
            {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            }
            else
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Finds whether the specified string matches any string in the specified string list
     *
     * @param str specifies the string
     * @param strs array of strings to check
     * @return matches
     */
    public static boolean matches(String str, List<String> strs)
    {
        if (isEmpty(str) || isEmpty(strs))
        {
            return false;
        }
        for (String pattern : strs)
        {
            if (isMatch(pattern, str))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine whether the url is configured with the rule:
     * ? represents a single character;
     * * Represents any string within the path of one layer, not across layers;
     * ** represents any layer path;
     *
     * @param pattern matching rules
     * @param url needs to match the url
     * @return
     */
    public static boolean isMatch(String pattern, String url)
    {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj)
    {
        return (T) obj;
    }

    /**
     * The left side of the number is filled with 0 to make it reach the specified length. Note that if the length is greater than size after converting the number to a string, only the last size characters are retained.
     *
     * @param num number object
     * @param size string specifies the length
     * @return Returns the string format of the number, the string is the specified length.
     */
    public static final String padl(final Number num, final int size)
    {
        return padl(num.toString(), size, '0');
    }

    /**
     * String left padding. If the original string s is longer than size, only the last size characters are kept.
     *
     * @param s raw string
     * @param size string specifies the length
     * @param c character for padding
     * @return Returns a string of the specified length, which is left-padded or truncated from the original string.
     */
    public static final String padl(final String s, final int size, final char c)
    {
        final StringBuilder sb = new StringBuilder(size);
        if (s != null)
        {
            final int len = s.length();
            if (s.length() <= size)
            {
                for (int i = size - len; i > 0; i--)
                {
                    sb.append(c);
                }
                sb.append(s);
            }
            else
            {
                return s.substring(len - size, len);
            }
        }
        else
        {
            for (int i = size; i > 0; i--)
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
