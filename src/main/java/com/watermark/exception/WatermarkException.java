package com.watermark.exception;

/**
 * Created by inazlim on 03/04/17.
 */
public class WatermarkException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public WatermarkException(String message) {
        super(message);
    }
}
