package com.sen.common.exception;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
public class FileSizeLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize", new Object[]{defaultMaxSize});
    }
}
