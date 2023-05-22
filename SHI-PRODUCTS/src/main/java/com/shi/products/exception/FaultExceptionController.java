/**
 * 
 */
package com.shi.products.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author suman.raju
 *
 */

@ControllerAdvice
public class FaultExceptionController {
	@ExceptionHandler(value = FaultException.class)
	public ResponseEntity<String> exception(FaultException exception){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
