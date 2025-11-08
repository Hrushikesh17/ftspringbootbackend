package com.hkp.freetre.ExceptionHandle;

public class IdHandle extends RuntimeException {
	 // Constructor with a message
    public IdHandle(String message) {
        super(message);
    }

    // Constructor with a message and a cause
    public IdHandle(String message, Throwable cause) {
        super(message, cause);
    }
}
