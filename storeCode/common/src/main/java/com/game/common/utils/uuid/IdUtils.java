package com.game.common.utils.uuid;

/**
 * ID generator tool class
 *
 * @author Yu Yue
 */
public class IdUtils
{
    /**
     * Get random UUID
     *
     * @return random UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * Simplified UUID, removed the horizontal line
     *
     * @return Simplified UUID, stripped of dashes
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString(true);
    }

    /**
     * Get random UUID, use ThreadLocalRandom with better performance to generate UUID
     *
     * @return random UUID
     */
    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }

    /**
     * Simplified UUID, remove the horizontal line, use ThreadLocalRandom with better performance to generate UUID
     *
     * @return Simplified UUID, stripped of dashes
     */
    public static String fastSimpleUUID()
    {
        return UUID.fastUUID().toString(true);
    }
}
