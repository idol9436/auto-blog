package com.sangsang.autoblog.domain.exception;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }
}
