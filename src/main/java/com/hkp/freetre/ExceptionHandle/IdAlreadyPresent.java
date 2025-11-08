package com.hkp.freetre.ExceptionHandle;

public class IdAlreadyPresent extends RuntimeException {
	 // Constructor with a message
    public IdAlreadyPresent(String message) {
        super(message);
    }

    // Constructor with a message and a cause
    public IdAlreadyPresent(String message, Throwable cause) {
        super(message, cause);
    }
}
