/**
 * 
 */
package com.shi.products.client.res;

import java.util.Objects;

import lombok.Data;

/**
 * @author suman.raju
 *
 */
@Data
public class GetQuoteSOAPResponse {
	
	private String premiumAmount;
	private String premiumTax;
	private String totalAmount;
	private String message;
	private String hlflyPremAmntFirstPrem;
	private String hlflyPremAmntFirstTax;
	private String hlflyPremAmntFirstTotal;
	private String hlflyPremAmntOthersPrem;
	private String hlflyPremAmntOthersTax;
	private String hlflyPremAmntOthersTotal;
	private String qtrlyPremAmntFirstPrem;
	private String qtrlyPremAmntFirstTax;
	private String qtrlyPremAmntFirstTotal;
	private String qtrlyPremAmntOthersPrem;
	private String qtrlyPremAmntOthersTax;
	private String qtrlyPremAmntOthersTotal;
	private String mnthlyPremAmntFirstPrem;
	private String mnthlyPremAmntFirstTax;
	private String mnthlyPremAmntFirstTotal;
	private String mnthlyPremAmntOthersPrem;
	private String mnthlyPremAmntOthersTax;
	private String mnthlyPremAmntOthersTotal;
	private String totalRuralDiscountAmt;
	private String totalOnlineDiscountAmt;
	private String totalAddDiscnt;
	private String buyBackPrem;
	private String hospitalCashPrem;
	private String patientCarePrem;
	private String addOnCvrSec1Prem;
	private String addOnCvrSec2Prem;
	
	
}
