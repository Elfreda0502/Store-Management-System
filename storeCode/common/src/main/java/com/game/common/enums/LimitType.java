package com.game.common.enums;

/**
 * Current limiting type
 *
 * @author Yu Yue
 */

public enum LimitType
{
    /**
     * default policy to limit traffic globally
     */
    DEFAULT,

    /**
     * Limit current based on requester IP
     */
    IP
}
