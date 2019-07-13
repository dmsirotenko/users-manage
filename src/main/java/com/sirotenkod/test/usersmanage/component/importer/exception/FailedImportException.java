package com.sirotenkod.test.usersmanage.component.importer.exception;

public class FailedImportException extends RuntimeException {
    public FailedImportException() {
    }

    public FailedImportException(String message) {
        super(message);
    }

    public FailedImportException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedImportException(Throwable cause) {
        super(cause);
    }

    public FailedImportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
