package com.game.common.core.text;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import com.game.common.utils.StringUtils;

/**
 * Character set tool class
 *
 * @author Yu Yue
 */
public class CharsetKit
{
    
    public static final String ISO_8859_1 = "ISO-8859-1";
    
    public static final String UTF_8 = "UTF-8";
    
    public static final String GBK = "GBK";

    
    public static final Charset CHARSET_ISO_8859_1 = Charset.forName(ISO_8859_1);
    
    public static final Charset CHARSET_UTF_8 = Charset.forName(UTF_8);
    
    public static final Charset CHARSET_GBK = Charset.forName(GBK);

    /**
     * Convert to Charset object
     *
     * @param charset character set, if empty, return default character set
     * @return Charset
     */
    public static Charset charset(String charset)
    {
        return StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset);
    }

    /**
     * Convert the character set encoding of the string
     *
     * @param source string
     * @param srcCharset source character set, default ISO-8859-1
     * @param destCharset target character set, default UTF-8
     * @return the converted character set
     */
    public static String convert(String source, String srcCharset, String destCharset)
    {
        return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
    }

    /**
     * Convert the character set encoding of the string
     *
     * @param source string
     * @param srcCharset source character set, default ISO-8859-1
     * @param destCharset target character set, default UTF-8
     * @return the converted character set
     */
    public static String convert(String source, Charset srcCharset, Charset destCharset)
    {
        if (null == srcCharset)
        {
            srcCharset = StandardCharsets.ISO_8859_1;
        }

        if (null == destCharset)
        {
            destCharset = StandardCharsets.UTF_8;
        }

        if (StringUtils.isEmpty(source) || srcCharset.equals(destCharset))
        {
            return source;
        }
        return new String(source.getBytes(srcCharset), destCharset);
    }

    /**
     * @return system character set encoding
     */
    public static String systemCharset()
    {
        return Charset.defaultCharset().name();
    }
}
