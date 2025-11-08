package com.hkp.freetre.ExceptionHandle;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(IdHandle.class)
    public ResponseEntity<?> IDExistsException(IdHandle st) {

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatusCode(HttpStatus.OK.value());
        errorDetails.setStatus(HttpStatus.OK.name());
        errorDetails.setErrMessage(st.getMessage());
        errorDetails.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(errorDetails,HttpStatus.OK);

    }
	
	 // Exception handler for ProductNotFoundException
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }


}
