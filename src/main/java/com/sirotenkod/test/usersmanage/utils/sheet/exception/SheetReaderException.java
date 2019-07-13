package com.sirotenkod.test.usersmanage.utils.sheet.exception;

public class SheetReaderException extends RuntimeException {
    public SheetReaderException() {
    }

    public SheetReaderException(String message) {
        super(message);
    }

    public SheetReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public SheetReaderException(Throwable cause) {
        super(cause);
    }

    public SheetReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
