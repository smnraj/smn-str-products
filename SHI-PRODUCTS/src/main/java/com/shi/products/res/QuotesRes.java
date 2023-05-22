/**
 * 
 */
package com.shi.products.res;

import java.util.List;

import lombok.Data;

/**
 * @author suman.raju
 *
 */
@Data
public class QuotesRes {
	
	private List<Quotes> quotes;
	
	private List<String> message;

}
