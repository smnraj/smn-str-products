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
@Table(name="SHI_PRODUCTS_GET_QUOTE_REQUEST")
@Data
public class GetQuoteRequestVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String batchId;
	private Long productId;
	private Long familyId;
	private Long sumInsureId;
	private Long adultAgeId;
	private Long childAgeId;
	private Long parentAgeId;
	private String productBatchId;
	private String productCode;
	private String productName;
	private String insuranceCover;
	private String deduction;
	private String adults;
	private String child;
	private String schemeCode;
	private String adult1AgeBandInYears;
	private String adult2AgeBandInYears;
	private String adult1AgeList;
	private String adult2AgeList;
	private String adultDOB1;
	private String adultDOB2;
	private String zone;
	private String pinCode;
	private String period;
	private String option1;
	private String plan;
	private String polFmDt;
	private String parents;
	private String parent1AgeBandInYears;
	private String parent2AgeBandInYears;
	private String parent3AgeBandInYears;
	private String parent4AgeBandInYears;
	private String parentDOB1;
	private String parentDOB2;
	private String parentDOB3;
	private String parentDOB4;
	private String parent1AgeList;
	private String parent2AgeList;
	private String parent3AgeList;
	private String parent4AgeList;
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
	private String child1AgeBandInYears;
	private String child2AgeBandInYears;
	private String child3AgeBandInYears;
	private String childDOB1;
	private String childDOB2;
	private String childDOB3;
	private String child1AgeList;
	private String child2AgeList;
	private String child3AgeList;
	private String message;
	private Timestamp createdTime;
	private Timestamp modifiedTime;
	private String flag;
	private String isActive;

}
