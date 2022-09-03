package com.game.common.constant;

/**
 * Return State code
 *
 * @author Yu Yue
 */
public class HttpStatus
{
    /**
     *Operation successful
     */
    public static final int SUCCESS = 200;

    /**
     * Object created successfully
     */
    public static final int CREATED = 201;

    /**
     * The request has been accepted
     */
    public static final int ACCEPTED = 202;

    /**
     * The operation has been executed successfully, but no Data is returned
     */
    public static final int NO_CONTENT = 204;

    /**
     * Resource has been removed
     */
    public static final int MOVED_PERM = 301;

    /**
     * redirect
     */
    public static final int SEE_OTHER = 303;

    /**
     * The resource has not been modified
     */
    public static final int NOT_MODIFIED = 304;

    /**
     * parameter list error (missing, format mismatch)
     */
    public static final int BAD_REQUEST = 400;

    /**
     * unauthorized
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * Limited access, authorization expired
     */
    public static final int FORBIDDEN = 403;

    /**
     * resource, service not found
     */
    public static final int NOT_FOUND = 404;

    /**
     * Disallowed http methods
     */
    public static final int BAD_METHOD = 405;

    /**
     * Resource conflict, or resource is locked
     */
    public static final int CONFLICT = 409;

    /**
     * Unsupported Data, media type
     */
    public static final int UNSUPPORTED_TYPE = 415;

    /**
     * Internal System Error
     */
    public static final int ERROR = 500;

    /**
     * interface not implemented
     */
    public static final int NOT_IMPLEMENTED = 501;
}