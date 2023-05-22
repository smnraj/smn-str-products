/**
 * 
 */
package com.shi.products.util;

import org.springframework.stereotype.Component;

/**
 * @author suman.raju
 *
 */
@Component
public class ApplicationConstant {
	
	public static final String Y = "Y";
	
	public static final String VALIDKEY="Please Enter the Valid Key";
	public static final String PRODUCTCODE="Please Enter the Product Code";
	
	public static final String ADULTS_MISSING = "Adults is Empty";
	public static final String ADULTS_DOB_MISSING= "Adults DOB is Empty";
	public static final String ADULTS_AGE_MISSING = "Adults Age Band In Year is Empty";
	public static final String ADULTS_INFO_MISSING= "Adults Info Missing";
	public static final String PARENTS_INFO_MISSING = "Parents Info Missing";
	public static final String PARENTS_AGE_MISSING = "Parents Age Band In Year is Empty";
	public static final String CHILD_MISSING= "Child Info Missing";
	public static final String CHILD_AGE_MISSING= "Child Age Band In Year is Empty";
	public static final String PLAN_EMPTY= "Plan is Empty";
	public static final String PINCODE_EMPTY = "Pincode is Empty";
	public static final String VALIDATE_KEY = "Please Enter the Valid Key";
	public static final String INSURANCECOVER_EMPTY = "InsuranceCover is Empty";
	public static final String DEDUCTION_EMPTY = "Deduction is Empty";
	public static final String ZONE_EMPTY = "Zone is Empty";
	public static final String OTHERS_DOB_EMPTY = "Others DOB is Empty";
	public static final String OTHER_AGE_EMPTY= "Others Age Band In Year is Empty";
	public static final String SOURCE_SYSTEM_EMPTY = "Source System is Empty";
	public static final String DATETIMEFORMAT ="ddMMyyyyHHmmss";
	
	public static final String INVALID_SOAP_REQ= "Invalid SOAP Request";
	
	public static final String YOUNG_STAR_TABLE = "Young_Star_Table";
	public static final String SUCCESS = "SUCCESS";
	
	public static final String SELECT = "SELECT * FROM ";
	public static final String WHERE = " WHERE";
	public static final String PRODUCT_CODE = " productCode = '";
	public static final String ADULTS_Q = " AND adults = ";
	public static final String CHILD_Q = " AND child = ";
	public static final String PARENTS_Q = " AND parents = ";
	public static final String CHILD_NULL= " AND child is null ";
	public static final String INSURANCE_COVER = " AND insuranceCover = ";
	public static final String INSURANCE_COVER_GREATER= " AND insuranceCover >= ";
	public static final String DEDUCTION_Q= " AND deduction = '";
	public static final String ZONE = " AND zone = '";
	public static final String PERIOD_Q = " AND period = '";
	public static final String PERIOD_Q_1 = " AND period IN ('";
	public static final String PLAN_Q= " AND plan = '";
	public static final String ONLINE_DISCOUNT = " AND onlineDiscountPerc = '";
	public static final String ONLINE_DISCOUNT_NULL = " AND onlineDiscountPerc is null ";
	public static final String UPFRONT_DISCOUNT = " AND upfrontdiscountYN = '";
	public static final String UPFRONT_DISCOUNT_NULL = " AND upfrontdiscountYN is null ";
	public static final String UNION_ALL = ") UNION ALL (";
	public static final String ORDER_BY = "ORDER BY LENGTH(insuranceCover), insuranceCover ASC LIMIT 1";
	
	public static final String ADULT_AGE_BAND = " AND adult1AgeBandInYears IN ('";
	public static final String ADULT_AGE_BAND_11 = " AND ( (adult1AgeBandInYears IN ('";
	public static final String ADULT_AGE_BAND_2 = " AND adult2AgeBandInYears IN ('";
	public static final String ADULT_AGE_BAND_111 = " OR ( adult1AgeBandInYears IN ('";
	
	public static final String PARENT_NULL = " AND parents is null ";
	public static final String PARENT_AGE_BAND_1 = " AND parent1AgeBandInYears IN ('";
	public static final String PARENT_AGE_BAND_11 = " AND ( (parent1AgeBandInYears IN ('";
	public static final String PARENT_AGE_BAND_111 = " OR ( parent1AgeBandInYears IN ('";
	public static final String PARENT_AGE_BAND_2 = " AND parent2AgeBandInYears IN ('";
	public static final String PARENT_AGE_BAND_3 = " AND parent3AgeBandInYears IN ('";
	public static final String PARENT_AGE_BAND_4 = " AND parent4AgeBandInYears IN ('";
	
	public static final String CHILD_AGE_BAND_1 = " OR ( child1AgeBandInYears IN ('";
	public static final String CHILD_AGE_BAND_11 = " AND child1AgeBandInYears IN ('";
	public static final String CHILD_AGE_BAND_111 = " AND ( (child1AgeBandInYears IN ('";
	public static final String CHILD_AGE_BAND_2 = " AND child2AgeBandInYears IN ('";
	public static final String CHILD_AGE_BAND_3 = " AND child3AgeBandInYears IN ('";
	
	public static final String SCHEMECODE = "SchemeCode";
	public static final String PRODUCTID = "ProductId";
	public static final String PERIOD = "Period";
	public static final String PARENTS = "Parents";
	public static final String PARENTDOB1 ="ParentDOB1";
	public static final String PARENTDOB2 ="ParentDOB2";
	public static final String PARENTDOB3 ="ParentDOB3";
	public static final String PARENTDOB4 ="ParentDOB4";
	public static final String PINCODE = "PinCode";
	public static final String POLFMDT= "PolFmDt";
	public static final String INSURANCECOVER= "InsuranceCover";
	public static final String INSURANCECOVER2= "InsuranceCover2";
	public static final String DEDUCTION= "Deduction";
	public static final String ADULTS="Adults";
	public static final String CHILD="Child";
	public static final String ADULTDOB1="AdultDOB1";
	public static final String ADULTDOB2="AdultDOB2";
	public static final String OPTION="Option";
	public static final String PLAN="Plan";
	public static final String ADULTLUMPSUMSI="AdultLumpSumSI";
	public static final String CHILDLUMPSUMSI1="ChildLumpSumSI1";
	public static final String CHILDLUMPSUMSI2="ChildLumpSumSI2";
	public static final String CHILDLUMPSUMSI3="ChildLumpSumSI3";
	public static final String CHILDDOB1="ChildDOB1";
	public static final String CHILDDOB2="ChildDOB2";
	public static final String CHILDDOB3="ChildDOB3";
	public static final String ONLINEDISCOUNTPERC="OnlineDiscountPerc";
	public static final String AREACODE="AreaCode";
	public static final String ADDDISCNTYN="AddDiscntYN";
	public static final String DIVNCODE="DivnCode";
	public static final String CATEGORY="Category";
	public static final String BUYBACKPEDYN="BuyBackPedYN";
	public static final String HOSPITALCASHYN="HospitalCashYN";
	public static final String PATIENTCAREYN="PatientCareYN";
	public static final String ADDONCVRSEC1YN="AddOnCvrSec1YN";
	
	public static final String AGE91DAYS17YEARS = "91Days-17";
	public static final String AGE91DAYS15YEARS = "91Days-15";
	public static final String AGE81ANDABOVE = "81-AndAbove";
	public static final String AGE76ANDABOVE = "76-AndAbove";
	public static final String AGE21ANDABOVE = "21-AndAbove";
	public static final String AGE3MONTHSTO35YEARS = "3Months-35";
	public static final String AGE16DAYSTO35YEARS = "16Days-35";
	
	public static final String ABOVE1= "Above";

}
