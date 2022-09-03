package com.game.common.enums;

/**
 * Type of business operation
 *
 * @author Yu Yue
 */
public enum BusinessType
{
    /**
     * other
     */
    OTHER,

    /**
     * new
     */
    INSERT,

    /**
     * Revise
     */
    UPDATE,

    /**
     * delete
     */
    DELETE,

    /**
     * Authorization
     */
    GRANT,

    /**
     * export
     */
    EXPORT,

    /**
     * Import
     */
    IMPORT,

    /**
     * Retire
     */
    FORCE,

    /**
     * Generate code
     */
    GENCODE,

    /**
     * Clear Data
     */
    CLEAN,
}