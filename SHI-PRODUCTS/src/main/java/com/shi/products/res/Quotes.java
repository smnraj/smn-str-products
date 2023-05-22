/**
 * 
 */
package com.shi.products.res;

import lombok.Data;

/**
 * @author suman.raju
 *
 */
@Data
public class Quotes {
	
	private String quoteId;
	private String productCode;
	private String productName;
	private Integer insuranceCover;
	private Integer deduction;
	private String zone;
	private Integer pinCode;
	private String period;
	private String plan;
	private String buyBackPedYN;
	private Integer onlineDiscountPerc;
	private String schemeCode;
	private String ageBandInYears;
	private Integer premiumAmount;
	private Integer premiumTax;
	private Integer totalAmount;
	private String message;
	private Integer hlflyPremAmntFirstPrem;
	private Integer hlflyPremAmntFirstTax;
	private Integer hlflyPremAmntFirstTotal;
	private Integer hlflyPremAmntOthersPrem;
	private Integer hlflyPremAmntOthersTax;
	private Integer hlflyPremAmntOthersTotal;
	private Integer qtrlyPremAmntFirstPrem;
	private Integer qtrlyPremAmntFirstTax;
	private Integer qtrlyPremAmntFirstTotal;
	private Integer qtrlyPremAmntOthersPrem;
	private Integer qtrlyPremAmntOthersTax;
	private Integer qtrlyPremAmntOthersTotal;
	private Integer mnthlyPremAmntFirstPrem;
	private Integer mnthlyPremAmntFirstTax;
	private Integer mnthlyPremAmntFirstTotal;
	private Integer mnthlyPremAmntOthersPrem;
	private Integer mnthlyPremAmntOthersTax;
	private Integer mnthlyPremAmntOthersTotal;
	private Integer totalRuralDiscountAmt;
	private Integer totalOnlineDiscountAmt;
	private Integer totalAddDiscnt;
	private Integer buyBackPrem;
	private Integer hospitalCashPrem;
	private Integer patientCarePrem;
	private Integer addOnCvrSec1Prem;
	private Integer addOnCvrSec2Prem;
	private Integer saving2Year;
	private Integer saving3Year;
	
}
