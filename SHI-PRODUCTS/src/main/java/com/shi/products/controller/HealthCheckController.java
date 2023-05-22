/**
 * 
 */
package com.shi.products.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author suman.raju
 *
 */

@RestController
public class HealthCheckController {
	
	@GetMapping(value="/healthCheck")
	public ResponseEntity<String> healthCheck(){
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}

}
