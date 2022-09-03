package com.game.common.exception.file;

import com.game.common.exception.base.BaseException;

/**
 * File information exception class
 *
 * @author Yu Yue
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
