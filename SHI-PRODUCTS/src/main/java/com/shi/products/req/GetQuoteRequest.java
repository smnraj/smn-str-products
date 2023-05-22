/**
 * 
 */
package com.shi.products.req;

import org.springframework.stereotype.Component;

import lombok.Data;


/**
 * @author suman.raju
 *
 */
@Component
@Data
public class GetQuoteRequest {
	
	private String productId;
	private String productName;
	private String insuranceCover;
	private String deduction;
	private String adults;
	private String child;
	private String schemeCode;
	private String ageBandInYears;
	private String adultDOB1;
	private String adultDOB2;
	private String zone;
	private String pinCode;
	private String period;
	private String option1;
	private String plan;
	private String polFmDt;
	private String parents;
	private String parentDOB1;
	private String parentDOB2;
	private String parentDOB3;
	private String parentDOB4;
	private String insuranceCover2;
	private String onlineDiscountPerc;
	private String areaCode;
	private String addDiscntYN;
	private String divnCode;
	private String category;
	private String buyBackPedYN;
	private String hospitalCashYN;
	private String patientCareYN;
	private String addOnCvrSec1YN;
	private String adultLumpSumSI;
	private String childLumpSumSI1;
	private String childLumpSumSI2;
	private String childLumpSumSI3;
	private String childDOB1;
	private String childDOB2;
	private String childDOB3;
	private String others;
	private String otherDOB1;
	private String sourceSystem;
	private String divisonCode;
	private String upfrontDiscountYN;
	private String upfrontDiscountPerc;
	

	
}
