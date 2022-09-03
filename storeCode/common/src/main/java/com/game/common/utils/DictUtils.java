package com.game.common.utils;

import java.util.Collection;
import java.util.List;
import com.game.common.constant.Constants;
import com.game.common.core.domain.entity.SysDictData;
import com.game.common.core.redis.RedisCache;
import com.game.common.utils.spring.SpringUtils;

/**
 * Dictionary tool class
 *
 * @author Yu Yue
 */
public class DictUtils
{
    /**
     * delimiter
     */
    public static final String SEPARATOR = ",";

    /**
     * Set Dictionary cache
     *
     * @param key parameter key
     * @param dictDatas DictionaryData list
     */
    public static void setDictCache(String key, List<SysDictData> dictDatas)
    {
        SpringUtils.getBean(RedisCache.class).setCacheObject(getCacheKey(key), dictDatas);
    }

    /**
     * Get Dictionary cache
     *
     * @param key parameter key
     * @return dictDatas DictionaryData list
     */
    public static List<SysDictData> getDictCache(String key)
    {
        Object cacheObj = SpringUtils.getBean(RedisCache.class).getCacheObject(getCacheKey(key));
        if (StringUtils.isNotNull(cacheObj))
        {
            return StringUtils.cast(cacheObj);
        }
        return null;
    }

    /**
     * Get Dictionary tag based on Dictionary type and Dictionary value
     *
     * @param dictType Dictionary type
     * @param dictValue Dictionary value
     * @return Dictionary tag
     */
    public static String getDictLabel(String dictType, String dictValue)
    {
        return getDictLabel(dictType, dictValue, SEPARATOR);
    }

    /**
     * Get Dictionary value based on Dictionary type and Dictionary tag
     *
     * @param dictType Dictionary type
     * @param dictLabel Dictionary label
     * @return Dictionary value
     */
    public static String getDictValue(String dictType, String dictLabel)
    {
        return getDictValue(dictType, dictLabel, SEPARATOR);
    }

    /**
     * Get Dictionary tag based on Dictionary type and Dictionary value
     *
     * @param dictType Dictionary type
     * @param dictValue Dictionary value
     * @param separator separator
     * @return Dictionary tag
     */
    public static String getDictLabel(String dictType, String dictValue, String separator)
    {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictData> datas = getDictCache(dictType);

        if (StringUtils.containsAny(separator, dictValue) && StringUtils.isNotEmpty(datas))
        {
            for (SysDictData dict : datas)
            {
                for (String value : dictValue.split(separator))
                {
                    if (value.equals(dict.getDictValue()))
                    {
                        propertyString.append(dict.getDictLabel()).append(separator);
                        break;
                    }
                }
            }
        }
        else
        {
            for (SysDictData dict : datas)
            {
                if (dictValue.equals(dict.getDictValue()))
                {
                    return dict.getDictLabel();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * Get Dictionary value based on Dictionary type and Dictionary tag
     *
     * @param dictType Dictionary type
     * @param dictLabel Dictionary label
     * @param separator separator
     * @return Dictionary value
     */
    public static String getDictValue(String dictType, String dictLabel, String separator)
    {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictData> datas = getDictCache(dictType);

        if (StringUtils.containsAny(separator, dictLabel) && StringUtils.isNotEmpty(datas))
        {
            for (SysDictData dict : datas)
            {
                for (String label : dictLabel.split(separator))
                {
                    if (label.equals(dict.getDictLabel()))
                    {
                        propertyString.append(dict.getDictValue()).append(separator);
                        break;
                    }
                }
            }
        }
        else
        {
            for (SysDictData dict : datas)
            {
                if (dictLabel.equals(dict.getDictLabel()))
                {
                    return dict.getDictValue();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * Delete the specified Dictionary cache
     *
     * @param key Dictionary key
     */
    public static void removeDictCache(String key)
    {
        SpringUtils.getBean(RedisCache.class).deleteObject(getCacheKey(key));
    }

    /**
     * Clear Dictionary cache
     */
    public static void clearDictCache()
    {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(Constants.SYS_DICT_KEY + "*");
        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }

    /**
     * Set the cache key
     *
     * @param configKey parameter key
     * @return cache key key
     */
    public static String getCacheKey(String configKey)
    {
        return Constants.SYS_DICT_KEY + configKey;
    }
}
