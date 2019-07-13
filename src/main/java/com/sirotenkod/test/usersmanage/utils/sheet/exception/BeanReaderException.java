package com.sirotenkod.test.usersmanage.utils.sheet.exception;

public class BeanReaderException extends RuntimeException {
    private Object entry;

    public BeanReaderException() {
    }

    public BeanReaderException(String message, Object entry) {
        super(message);

        this.entry = entry;
    }

    public BeanReaderException(String message, Object entry, Throwable cause) {
        super(message, cause);

        this.entry = entry;
    }

    public BeanReaderException(Object entry, Throwable cause) {
        super(cause);

        this.entry = entry;
    }

    public Object getEntry() {
        return entry;
    }
}
