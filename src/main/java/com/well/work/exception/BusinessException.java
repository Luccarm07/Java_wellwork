package com.well.work.exception;

/**
 * Exceção customizada para regras de negócio
 * Alinhado com: Api_visual_content
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
