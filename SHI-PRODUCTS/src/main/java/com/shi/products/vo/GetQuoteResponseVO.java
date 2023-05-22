/**
 * 
 */
package com.shi.products.vo;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author suman.raju
 *
 */

@Entity
@Table(name="SHI_PRODUCTS_GET_QUOTE_RESPONSE")
@Data
public class GetQuoteResponseVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long getQuoteReqId;
	private String batchId;
	private String productCode;
	private String productName;
	private Long productId;
	private Long familyId;
	private String productBatchId;
	private Integer insuranceCover;
	private Integer deduction;
	private Integer adults;
	private Integer child;
	private Integer parents;
	private String schemeCode;
	private String zone;
	private Integer pinCode;
	private String period;
	private String plan;
	private String adult1AgeBandInYears;
	private String adult2AgeBandInYears;
	private String adult1AgeList;
	private String adult2AgeList;
	private String adultDOB1;
	private String adultDOB2;
	private String child1AgeBandInYears;
	private String child2AgeBandInYears;
	private String child3AgeBandInYears;
	private String child1AgeList;
	private String child2AgeList;
	private String child3AgeList;
	private String childDOB1;
	private String childDOB2;
	private String childDOB3;
	private String parent1AgeBandInYears;
	private String parent2AgeBandInYears;
	private String parent3AgeBandInYears;
	private String parent4AgeBandInYears;
	private String parent1AgeList;
	private String parent2AgeList;
	private String parent3AgeList;
	private String parent4AgeList;
	private String parentDOB1;
	private String parentDOB2;
	private String parentDOB3;
	private String parentDOB4;
	private String category;
	private String buyBackPedYN;
	private Integer onlineDiscountPerc;
	private String upfrontDiscountYN;
	private String upfrontDiscountPerc;
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
	private Timestamp createdTime;
	private Timestamp modifiedTime;
	private String flag;
	private String isActive;
	
}
