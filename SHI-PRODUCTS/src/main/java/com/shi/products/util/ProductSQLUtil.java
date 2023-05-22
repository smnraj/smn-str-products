/**
 * 
 */
package com.shi.products.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Lob;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.shi.products.repo.ProductPincodeRepo;
import com.shi.products.repo.ProductsSumInsureRepo;
import com.shi.products.repo.SourceSystemRepo;
import com.shi.products.req.GetQuoteRequest;
import com.shi.products.vo.GetQuoteRequestVO;
import com.shi.products.vo.ProductsSumInsureVO;

/**
 * @author suman.raju
 *
 */
@Component
public class ProductSQLUtil {
	private static final Logger log = LoggerFactory.getLogger(ProductSQLUtil.class);
	
	@Autowired
	ProductUtil productUtil;
	
	@Autowired
	ProductAgeUtil productAgeUtil;
	
	@Autowired
	Environment environment;
	
	@Autowired
	ProductPincodeRepo productPincodeRepo;
	
	@Autowired
	SourceSystemRepo sourceSystemRepo;
	
	@Autowired
	ProductsSumInsureRepo productsSumInsureRepo;
	
	@Value("${InstalmentPremium_1Year}")
	private String instalmentPremium1Year;
	
	@Value("${InstalmentPremium_2Year}")
	private String instalmentPremium2Year;
	
	@Value("${InstalmentPremium_3Year}")
	private String instalmentPremium3Year;
	
	@Value("${MediClassic_Plan_GOLD}")
	private String mediClassicPlanGOLD;
	
	@Value("${MediClassic_Plan_BASIC}")
	private String mediClassicPlanBASIC;
	
	@Value("${MediClassic_ZONE_2}")
	private String mediClassicZONE2;
	
	@Value("${FHO_ZONE_A}")
	private String fhoZONEA;
	
	@Value("${floater}")
	private String floater;
	
	@Value("${individual}")
	private String individual;
	
	@Value("${Young_Star_Plan_GOLD}")
	private String youngStarPlanGOLD;
	
	@Value("${Young_Star_Plan_SILVER}")
	private String youngStarPlanSilver;
	
	@Value("${Super_surplus_Plan_GOLD}")
	private String superSurplusPlanGold;
	
	@Value("${Super_surplus_Plan_SILVER}")
	private String superSurplusPlanSilver;
	
	@Value("${Super_surplus_Deduction}")
	private String superSurplusDeduction;
	
	@Value("${Health_Premier_ZONE_1}")
	private String healthPremierZone1;
	
	@Value("${Health_Assure_ZONE_A}")
	private String healthAssureZoneA;
	
	@Value("${MediClassic_ProductCode}")
	private String mediClassicProductCode;
	
	@Value("${Family_Health_Optima_ProductCode}")
	private String familyHealthOptimaProductCode;
	
	@Value("${Family_Health_Optima_ProductName}")
	private String familyHealthOptimaProductName;
	
	@Value("${Young_Star_ProductCode}")
	private String youngStarProductCode;
	
	@Value("${StarComprehensive_ProductCode}")
	private String starComprehensiveProductCode;
	
	@Value("${ArogyaSanjeevani_ProductCode}")
	private String arogyaSanjeevaniProductCode;
	
	@Value("${Super_surplus_individual_ProductCode}")
	private String superSurplusIndividualProductCode;
	
	@Value("${Super_surplus_floater_ProductCode}")
	private String superSurplusFloaterProductCode;
	
	@Value("${Health_Premier_ProductCode}")
	private String healthPremierProductCode;
	
	@Value("${Health_Premier_ProductName}")
	private String healthPremierProductName;
	
	@Value("${Health_Assure_ProductCode}")
	private String healthAssureProductCode;
	
	@Value("${Health_Assure_ProductName}")
	private String healthAssureProductName;
	
	@Value("${Star_Women_Care_ProductCode}")
	private String starWomenCareProductCode;
	

	public List<String> buildMediClassicQuery(GetQuoteRequest getQuoteRequest, String mappingProductCode) throws Exception {
		log.debug("Build Medi CLassic Get Quote Query Starts");
		log.info("Serch Criteria MediClassic = {}", productUtil.doMarshallingJSON(getQuoteRequest));
		
		List<String> validation = new ArrayList<>();
		
		String tableName = environment.getProperty("MediClassic_Table");
		String query1 = ApplicationConstant.SELECT;
		String query2 = ApplicationConstant.SELECT;
		
		query1 = query1.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(mappingProductCode).concat("'");
		query2 = query2.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(mappingProductCode).concat("'");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query1 = query1.concat(ApplicationConstant.INSURANCE_COVER_GREATER+getQuoteRequest.getInsuranceCover());
			query2 = query2.concat(ApplicationConstant.INSURANCE_COVER_GREATER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			query1 = query1.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
			query2 = query2.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());

			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				
				long adultDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB1());
				long adultDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB2());
				String adultDOB = "";
				if(adultDOB1Days>adultDOB2Days) {
					adultDOB = getQuoteRequest.getAdultDOB1().trim();
				}else {
					adultDOB = getQuoteRequest.getAdultDOB2().trim();
				}
				
				String ageListGold = productAgeUtil.getMediClassicAgeList(adultDOB, mediClassicPlanGOLD, getQuoteRequest.getPeriod());
				String ageListBasic = productAgeUtil.getMediClassicAgeList(adultDOB, mediClassicPlanBASIC, getQuoteRequest.getPeriod());
				if(StringUtils.isNotBlank(ageListGold) && StringUtils.isNotBlank(ageListBasic)) {
					query1 = query1.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageListGold).concat("')");
					query2 = query2.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageListBasic).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);				}
				
			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
			
		}else if(StringUtils.isNotBlank(getQuoteRequest.getOthers()) && !getQuoteRequest.getOthers().equalsIgnoreCase("0")){
			query1 = query1.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getOthers());
			query2 = query2.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getOthers());

			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {

				String adultDOB = getQuoteRequest.getOtherDOB1().trim();
				
				String ageListGold = productAgeUtil.getMediClassicAgeList(adultDOB, mediClassicPlanGOLD, getQuoteRequest.getPeriod());
				String ageListBasic = productAgeUtil.getMediClassicAgeList(adultDOB, mediClassicPlanBASIC, getQuoteRequest.getPeriod());
				query1 = query1.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageListGold).concat("')");
				query2 = query2.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageListBasic).concat("')");
			}else {
				validation.add(ApplicationConstant.OTHERS_DOB_EMPTY);
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPinCode())) {
			String zone = "";
			String pincode = String.valueOf(getQuoteRequest.getPinCode());
			zone = productPincodeRepo.getPincodeByZone(mappingProductCode.trim(), pincode.trim());
			if(StringUtils.isBlank(zone)) {
				zone = mediClassicZONE2;
			}
			query1 = query1.concat(ApplicationConstant.ZONE).concat(zone).concat("'");
			query2 = query2.concat(ApplicationConstant.ZONE).concat(zone).concat("'");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPeriod())) {
			query1 = query1.concat(ApplicationConstant.PERIOD_Q).concat(getQuoteRequest.getPeriod().trim()).concat("'");
			query2 = query2.concat(ApplicationConstant.PERIOD_Q).concat(getQuoteRequest.getPeriod().trim()).concat("'");
		}else {
			query1 = query1.concat(ApplicationConstant.PERIOD_Q).concat(instalmentPremium1Year).concat("'");
			query2 = query2.concat(ApplicationConstant.PERIOD_Q).concat(instalmentPremium1Year).concat("'");
		}
		
		query1 = query1.concat(ApplicationConstant.PLAN_Q).concat(mediClassicPlanGOLD).concat("'");
		query2 = query2.concat(ApplicationConstant.PLAN_Q).concat(mediClassicPlanBASIC).concat("'");

		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query1 = query1.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
			query2 = query2.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query1 = query1.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
			query2 = query2.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		query1 = query1.concat(ApplicationConstant.ORDER_BY);
		query2 = query2.concat(ApplicationConstant.ORDER_BY);
		
		
		String query = "(";
		query = query.concat(query1).concat(ApplicationConstant.UNION_ALL).concat(query2).concat(")");
		log.debug("Build Medi Classic Get Quote Query End = {}", query);
		
		if(CollectionUtils.isEmpty(validation)) {
			validation.add(query);
		}
		return validation;
	}
	
	
	public List<String> buildAllMediClassicQuery(GetQuoteRequest getQuoteRequest) throws Exception{
		log.debug("Build All Medi CLassic Get Quote Query Starts");
		log.debug("Serch Criteria AllMediClassic = {}",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		
		String tableName = environment.getProperty("MediClassic_Table");
		String query = ApplicationConstant.SELECT;
		
		query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(getQuoteRequest.getProductId()).concat("'");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query = query.concat(ApplicationConstant.INSURANCE_COVER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
			
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				
				long adultDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB1());
				long adultDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB2());
				String adultDOB = "";
				if(adultDOB1Days>adultDOB2Days) {
					adultDOB = getQuoteRequest.getAdultDOB1().trim();
				}else {
					adultDOB = getQuoteRequest.getAdultDOB2().trim();
				}
				
				String  ageBand1 = productAgeUtil.getMediClassicAgeList(adultDOB, getQuoteRequest.getPlan().trim(), instalmentPremium1Year);
				String  ageBand2 = productAgeUtil.getMediClassicAgeList(adultDOB, getQuoteRequest.getPlan().trim(), instalmentPremium2Year);
				String  ageBand3 = productAgeUtil.getMediClassicAgeList(adultDOB, getQuoteRequest.getPlan().trim(), instalmentPremium3Year);
				if(StringUtils.isNotBlank(ageBand1) && StringUtils.isNotBlank(ageBand2) && StringUtils.isNotBlank(ageBand3)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageBand1).concat("','").concat(ageBand2).concat("','").concat(ageBand3).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
		}else if(StringUtils.isNotBlank(getQuoteRequest.getOthers()) && !getQuoteRequest.getOthers().equalsIgnoreCase("0")){
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getOthers());

			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {

				String adultDOB = getQuoteRequest.getOtherDOB1().trim();
				
				String  ageBand1 = productAgeUtil.getMediClassicAgeList(adultDOB, getQuoteRequest.getPlan().trim(), instalmentPremium1Year);
				String  ageBand2 = productAgeUtil.getMediClassicAgeList(adultDOB, getQuoteRequest.getPlan().trim(), instalmentPremium2Year);
				String  ageBand3 = productAgeUtil.getMediClassicAgeList(adultDOB, getQuoteRequest.getPlan().trim(), instalmentPremium3Year);
				if(StringUtils.isNotBlank(ageBand1) && StringUtils.isNotBlank(ageBand2) && StringUtils.isNotBlank(ageBand3)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageBand1).concat("','").concat(ageBand2).concat("','").concat(ageBand3).concat("')");
				}else {
					validation.add(ApplicationConstant.OTHER_AGE_EMPTY);
				}
				
			}else {
				validation.add(ApplicationConstant.OTHERS_DOB_EMPTY);
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPinCode())) {
			String zone = "";
			String pincode = String.valueOf(getQuoteRequest.getPinCode());
			zone = productPincodeRepo.getPincodeByZone(getQuoteRequest.getProductId().trim(), pincode.trim());
			if(StringUtils.isBlank(zone)) {
				zone = mediClassicZONE2;
			}
			query = query.concat(ApplicationConstant.ZONE).concat(zone).concat("'");
		}
		
		query = query.concat(ApplicationConstant.PERIOD_Q_1).concat(instalmentPremium1Year).concat("','")
				.concat(instalmentPremium2Year).concat("','")
				.concat(instalmentPremium3Year).concat("')");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPlan())) {
			query = query.concat(ApplicationConstant.PLAN_Q).concat(getQuoteRequest.getPlan()).concat("'");
		}else {
			validation.add(ApplicationConstant.PLAN_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		log.debug("Build All Medi Classic Get Quote Query End {}",query);
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}
	

	public List<String> buildFHOQuery(GetQuoteRequest getQuoteRequest, String mappingProductCode) throws Exception {
		log.debug("Build FHO Get Quote Query Starts");
		log.debug("Serch Criteria FHO {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty("Family_Health_Optima_Table");
		String query = ApplicationConstant.SELECT;
		
		query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(mappingProductCode).concat("'");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query = query.concat(ApplicationConstant.INSURANCE_COVER_GREATER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		boolean isOnlyTwoParent = false;
		int adultCount = 0;
		List<String> adultDateList = new ArrayList<>();
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults())){
			adultCount = Integer.parseInt(getQuoteRequest.getAdults().trim());
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())) {
				adultDateList.add(getQuoteRequest.getAdultDOB1().trim());
			}
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				adultDateList.add(getQuoteRequest.getAdultDOB2().trim());
			}
		}
		if(StringUtils.isNotBlank(getQuoteRequest.getOthers())) {
			adultCount = adultCount + Integer.parseInt(getQuoteRequest.getOthers().trim());
			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {
				adultDateList.add(getQuoteRequest.getOtherDOB1().trim());
			}
		}
		if((StringUtils.isBlank(getQuoteRequest.getAdults()) || getQuoteRequest.getAdults().equalsIgnoreCase("0"))
				&& getQuoteRequest.getParents().equalsIgnoreCase("2")) {
			isOnlyTwoParent = true;
			adultCount = adultCount + Integer.parseInt(getQuoteRequest.getParents().trim());
			if(StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())) {
				adultDateList.add(getQuoteRequest.getParentDOB1().trim());
			}
			if(StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				adultDateList.add(getQuoteRequest.getParentDOB2().trim());
			}
		}
		
		if(adultCount>0) {
			query = query.concat(ApplicationConstant.ADULTS_Q+String.valueOf(adultCount));
			
			if(adultDateList.size()>0) {
				adultDateList = productUtil.dateSortingAscending(adultDateList);
				String  ageList= productAgeUtil.getFHOAdultsAgeList(adultDateList.get(0), getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
		}else {
			query = query.concat(ApplicationConstant.CHILD_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getParents()) && !getQuoteRequest.getParents().equalsIgnoreCase("0") && !isOnlyTwoParent) {
			query = query.concat(ApplicationConstant.PARENTS_Q+getQuoteRequest.getParents());
			
			if(getQuoteRequest.getParents().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())) {
				String  ageList1= productAgeUtil.getFHOParentAgeList(getQuoteRequest.getParentDOB1().trim(), getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList1)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList1).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1()) 
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getFHOParentAgeList(dateList.get(0), getQuoteRequest.getPeriod().trim());
				String  ageList2= productAgeUtil.getFHOParentAgeList(dateList.get(1), getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(ageList2).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("3") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getFHOParentAgeList(dateList.get(0), getQuoteRequest.getPeriod().trim());
				String  ageList2= productAgeUtil.getFHOParentAgeList(dateList.get(1), getQuoteRequest.getPeriod().trim());
				String  ageList3= productAgeUtil.getFHOParentAgeList(dateList.get(2), getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2) && StringUtils.isNotBlank(ageList3)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(ageList2).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(ageList3).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("4") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB4())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList.add(getQuoteRequest.getParentDOB4().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getFHOParentAgeList(dateList.get(0), getQuoteRequest.getPeriod().trim());
				String  ageList2= productAgeUtil.getFHOParentAgeList(dateList.get(1), getQuoteRequest.getPeriod().trim());
				String  ageList3= productAgeUtil.getFHOParentAgeList(dateList.get(2), getQuoteRequest.getPeriod().trim());
				String  ageList4= productAgeUtil.getFHOParentAgeList(dateList.get(3), getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2) && StringUtils.isNotBlank(ageList3) && StringUtils.isNotBlank(ageList4)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(ageList2).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(ageList3).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_4).concat(ageList4).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}

			}else {
				validation.add(ApplicationConstant.PARENTS_INFO_MISSING);
			}
		}else {
			query = query.concat(ApplicationConstant.PARENT_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPinCode())) {
			String zone = "";
			String pincode = String.valueOf(getQuoteRequest.getPinCode());
			zone = productPincodeRepo.getPincodeByZone(mappingProductCode.trim(), pincode.trim());
			if(StringUtils.isNotBlank(zone)) {
				//zone = FHO_ZONE_A;
				query = query.concat(ApplicationConstant.ZONE).concat(zone).concat("'");
			}else {
				validation.add(ApplicationConstant.ZONE_EMPTY);
			}
			
		}else {
			validation.add(ApplicationConstant.PINCODE_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPeriod())) {
			query = query.concat(ApplicationConstant.PERIOD_Q).concat(getQuoteRequest.getPeriod().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.PERIOD_Q).concat(instalmentPremium1Year).concat("'");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getUpfrontDiscountYN()) && StringUtils.equalsIgnoreCase(getQuoteRequest.getUpfrontDiscountYN(), ApplicationConstant.Y)) {
			query = query.concat(ApplicationConstant.UPFRONT_DISCOUNT).concat(ApplicationConstant.Y).concat("'");
		}else {
			query = query.concat(ApplicationConstant.UPFRONT_DISCOUNT_NULL);
		}
		
		query = query.concat(ApplicationConstant.ORDER_BY);
		
		log.debug("Build FHO Get Quote Query End {}",query);
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}
	
	
	public List<String> buildAllFHOQuery(GetQuoteRequest getQuoteRequest) throws Exception {
		log.debug("Build All FHO Get Quote Query Starts");
		log.debug("Serch Criteria AllFHO = {}",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty("Family_Health_Optima_Table");
		String query = ApplicationConstant.SELECT;
		
		query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(getQuoteRequest.getProductId()).concat("'");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query = query.concat(ApplicationConstant.INSURANCE_COVER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		boolean isOnlyTwoParent = false;
		int adultCount = 0;
		List<String> adultDateList = new ArrayList<>();
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults())){
			adultCount = Integer.parseInt(getQuoteRequest.getAdults().trim());
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())) {
				adultDateList.add(getQuoteRequest.getAdultDOB1().trim());
			}
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				adultDateList.add(getQuoteRequest.getAdultDOB2().trim());
			}
		}
		if(StringUtils.isNotBlank(getQuoteRequest.getOthers())) {
			adultCount = adultCount + Integer.parseInt(getQuoteRequest.getOthers().trim());
			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {
				adultDateList.add(getQuoteRequest.getOtherDOB1().trim());
			}
		}
		if((StringUtils.isBlank(getQuoteRequest.getAdults()) || getQuoteRequest.getAdults().equalsIgnoreCase("0"))
				&& getQuoteRequest.getParents().equalsIgnoreCase("2")) {
			isOnlyTwoParent = true;
			adultCount = adultCount + Integer.parseInt(getQuoteRequest.getParents().trim());
			if(StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())) {
				adultDateList.add(getQuoteRequest.getParentDOB1().trim());
			}
			if(StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				adultDateList.add(getQuoteRequest.getParentDOB2().trim());
			}
		}
		
		if(adultCount>0) {
			query = query.concat(ApplicationConstant.ADULTS_Q+String.valueOf(adultCount));
			
			if(adultDateList.size()>0) {
				adultDateList = productUtil.dateSortingAscending(adultDateList);
				String  ageBand1 = productAgeUtil.getFHOAdultsAgeList(adultDateList.get(0),  instalmentPremium1Year);
				String  ageBand2 = productAgeUtil.getFHOAdultsAgeList(adultDateList.get(0),  instalmentPremium2Year);
				if(StringUtils.isNotBlank(ageBand1) && StringUtils.isNotBlank(ageBand2)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageBand1).concat("','").concat(ageBand2).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
		}else {
			query = query.concat(ApplicationConstant.CHILD_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getParents()) && !getQuoteRequest.getParents().equalsIgnoreCase("0") && !isOnlyTwoParent) {
			query = query.concat(ApplicationConstant.PARENTS_Q+getQuoteRequest.getParents());
			
			if(getQuoteRequest.getParents().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())) {
				String  ageBand1= productAgeUtil.getFHOParentAgeList(getQuoteRequest.getParentDOB1().trim(), instalmentPremium1Year);
				String  ageBand2= productAgeUtil.getFHOParentAgeList(getQuoteRequest.getParentDOB1().trim(), instalmentPremium2Year);
				if(StringUtils.isNotBlank(ageBand1) && StringUtils.isNotBlank(ageBand2)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageBand1).concat("','").concat(ageBand2).concat("')");			
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1()) 
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);

				String  age1List1Year= productAgeUtil.getFHOParentAgeList(dateList.get(0), instalmentPremium1Year);
				String  age2List1Year= productAgeUtil.getFHOParentAgeList(dateList.get(1), instalmentPremium1Year);
				String  age1List2Year= productAgeUtil.getFHOParentAgeList(dateList.get(0), instalmentPremium2Year);
				String  age2List2Year= productAgeUtil.getFHOParentAgeList(dateList.get(1), instalmentPremium2Year);
				if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age2List1Year) 
						&& StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age2List2Year)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_11).concat(age1List1Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(age2List1Year).concat("'))");
					
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_111).concat(age1List2Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(age2List2Year).concat("')))");

				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("3") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList = productUtil.dateSortingDescending(dateList);

				String  age1List1Year= productAgeUtil.getFHOParentAgeList(dateList.get(0), instalmentPremium1Year);
				String  age2List1Year= productAgeUtil.getFHOParentAgeList(dateList.get(1), instalmentPremium1Year);
				String  age3List1Year= productAgeUtil.getFHOParentAgeList(dateList.get(2), instalmentPremium1Year);
				String  age1List2Year= productAgeUtil.getFHOParentAgeList(dateList.get(0), instalmentPremium2Year);
				String  age2List2Year= productAgeUtil.getFHOParentAgeList(dateList.get(1), instalmentPremium2Year);
				String  age3List2Year= productAgeUtil.getFHOParentAgeList(dateList.get(2), instalmentPremium2Year);
				if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age2List1Year) && StringUtils.isNotBlank(age3List1Year) 
						&& StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age2List2Year) && StringUtils.isNotBlank(age3List2Year)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_11).concat(age1List1Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(age2List1Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(age3List1Year).concat("'))");
					
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_111).concat(age1List2Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(age2List2Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(age3List2Year).concat("')))");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}

			}else if(getQuoteRequest.getParents().equals("4") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB4())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList.add(getQuoteRequest.getParentDOB4().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  age1List1Year= productAgeUtil.getFHOParentAgeList(dateList.get(0), instalmentPremium1Year);
				String  age2List1Year= productAgeUtil.getFHOParentAgeList(dateList.get(1), instalmentPremium1Year);
				String  age3List1Year= productAgeUtil.getFHOParentAgeList(dateList.get(2), instalmentPremium1Year);
				String  age4List1Year= productAgeUtil.getFHOParentAgeList(dateList.get(3), instalmentPremium1Year);
				String  age1List2Year= productAgeUtil.getFHOParentAgeList(dateList.get(0), instalmentPremium2Year);
				String  age2List2Year= productAgeUtil.getFHOParentAgeList(dateList.get(1), instalmentPremium2Year);
				String  age3List2Year= productAgeUtil.getFHOParentAgeList(dateList.get(2), instalmentPremium2Year);
				String  age4List2Year= productAgeUtil.getFHOParentAgeList(dateList.get(3), instalmentPremium2Year);
				if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age2List1Year) && StringUtils.isNotBlank(age3List1Year) && StringUtils.isNotBlank(age4List1Year) 
						&& StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age2List2Year) && StringUtils.isNotBlank(age3List2Year) && StringUtils.isNotBlank(age4List2Year)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_11).concat(age1List1Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(age2List1Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(age3List1Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_4).concat(age4List1Year).concat("'))");
					
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_111).concat(age1List2Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(age2List2Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(age3List2Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_4).concat(age4List2Year).concat("')))");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}

			}else {
				validation.add("Parents Info Empty");
			}
		}else {
			query = query.concat(ApplicationConstant.PARENT_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPinCode())) {
			String zone = "";
			String pincode = String.valueOf(getQuoteRequest.getPinCode());
			zone = productPincodeRepo.getPincodeByZone(getQuoteRequest.getProductId().trim(), pincode.trim());
			if(StringUtils.isNotBlank(zone)) {
				query = query.concat(ApplicationConstant.ZONE).concat(zone).concat("'");
			}else {
				validation.add(ApplicationConstant.ZONE_EMPTY);
			}
			
		}else {
			validation.add(ApplicationConstant.PINCODE_EMPTY);
		}
		
		query = query.concat(ApplicationConstant.PERIOD_Q_1).concat(instalmentPremium1Year).concat("','")
				.concat(instalmentPremium2Year).concat("')");
		
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getUpfrontDiscountYN()) && StringUtils.equalsIgnoreCase(getQuoteRequest.getUpfrontDiscountYN(), ApplicationConstant.Y)) {
			query = query.concat(ApplicationConstant.UPFRONT_DISCOUNT).concat(ApplicationConstant.Y).concat("'");
		}else {
			query = query.concat(ApplicationConstant.UPFRONT_DISCOUNT_NULL);
		}
		
		log.debug("Build All FHO Get Quote Query End {}",query);
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}
	
	public GetQuoteRequestVO buildFHOSOAPRequest(GetQuoteRequest getQuoteRequest, String productCode) throws Exception {
		log.debug("BuildFHOSOAPRequest Starts");
		GetQuoteRequestVO getQuoteRequestVO = new GetQuoteRequestVO();
		log.debug("Serch Criteria FHOSOAPRequest {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		
		getQuoteRequestVO.setProductCode(productCode);
		getQuoteRequestVO.setProductName(familyHealthOptimaProductName);
		
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			ProductsSumInsureVO productsSumInsureVO = productsSumInsureRepo.getAvailableSumInsure(productCode, getQuoteRequest.getInsuranceCover().trim(),
					getQuoteRequest.getPeriod().trim());
			getQuoteRequest.setInsuranceCover(String.valueOf(productsSumInsureVO.getSumInsure()));
			getQuoteRequestVO.setInsuranceCover(getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		boolean isOnlyTwoParent = false;
		int adultCount = 0;
		List<String> adultDateList = new ArrayList<>();
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults())){
			adultCount = Integer.parseInt(getQuoteRequest.getAdults().trim());
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())) {
				adultDateList.add(getQuoteRequest.getAdultDOB1().trim());
			}
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				adultDateList.add(getQuoteRequest.getAdultDOB2().trim());
			}
		}
		if(StringUtils.isNotBlank(getQuoteRequest.getOthers())) {
			adultCount = adultCount + Integer.parseInt(getQuoteRequest.getOthers().trim());
			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {
				adultDateList.add(getQuoteRequest.getOtherDOB1().trim());
			}
		}
		if((StringUtils.isBlank(getQuoteRequest.getAdults()) || getQuoteRequest.getAdults().equalsIgnoreCase("0"))
				&& getQuoteRequest.getParents().equalsIgnoreCase("2")) {
			isOnlyTwoParent = true;
			adultCount = adultCount + Integer.parseInt(getQuoteRequest.getParents().trim());
			if(StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())) {
				adultDateList.add(getQuoteRequest.getParentDOB1().trim());
			}
			if(StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				adultDateList.add(getQuoteRequest.getParentDOB2().trim());
			}
		}
		
		if(adultCount>0) {
			getQuoteRequestVO.setAdults(String.valueOf(adultCount));
			if(adultDateList.size()>0) {
				adultDateList = productUtil.dateSortingAscending(adultDateList);
				String age = String.valueOf(productUtil.getAgeInYear(adultDateList.get(0)));
				String dob = productUtil.getDOBFromAge(age);
				getQuoteRequestVO.setAdultDOB1(dob);
			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			getQuoteRequestVO.setChild(getQuoteRequest.getChild());
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getParents()) && !getQuoteRequest.getParents().equalsIgnoreCase("0") && !isOnlyTwoParent) {
			getQuoteRequestVO.setParents(getQuoteRequest.getParents());
			if(getQuoteRequest.getParents().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())) {
				String age = String.valueOf(productUtil.getAgeInYear(getQuoteRequest.getParentDOB1().trim()));
				String dob = productUtil.getDOBFromAge(age);
				getQuoteRequestVO.setParentDOB1(dob);
			}else if(getQuoteRequest.getParents().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1()) 
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String age1 = String.valueOf(productUtil.getAgeInYear(dateList.get(0)));
				String age2 = String.valueOf(productUtil.getAgeInYear(dateList.get(1)));
				String dob1 = productUtil.getDOBFromAge(age1);
				String dob2 = productUtil.getDOBFromAge(age2);
				getQuoteRequestVO.setParentDOB1(dob1);
				getQuoteRequestVO.setParentDOB2(dob2);
			}else if(getQuoteRequest.getParents().equals("3") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList = productUtil.dateSortingDescending(dateList);

				String age1 = String.valueOf(productUtil.getAgeInYear(dateList.get(0)));
				String age2 = String.valueOf(productUtil.getAgeInYear(dateList.get(1)));
				String age3 = String.valueOf(productUtil.getAgeInYear(dateList.get(2)));
				String dob1 = productUtil.getDOBFromAge(age1);
				String dob2 = productUtil.getDOBFromAge(age2);
				String dob3 = productUtil.getDOBFromAge(age3);
				getQuoteRequestVO.setParentDOB1(dob1);
				getQuoteRequestVO.setParentDOB2(dob2);
				getQuoteRequestVO.setParentDOB3(dob3);
			}else if(getQuoteRequest.getParents().equals("4") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB4())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList.add(getQuoteRequest.getParentDOB4().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String age1 = String.valueOf(productUtil.getAgeInYear(dateList.get(0)));
				String age2 = String.valueOf(productUtil.getAgeInYear(dateList.get(1)));
				String age3 = String.valueOf(productUtil.getAgeInYear(dateList.get(2)));
				String age4 = String.valueOf(productUtil.getAgeInYear(dateList.get(3)));
				String dob1 = productUtil.getDOBFromAge(age1);
				String dob2 = productUtil.getDOBFromAge(age2);
				String dob3 = productUtil.getDOBFromAge(age3);
				String dob4 = productUtil.getDOBFromAge(age4);
				getQuoteRequestVO.setParentDOB1(dob1);
				getQuoteRequestVO.setParentDOB2(dob2);
				getQuoteRequestVO.setParentDOB3(dob3);
				getQuoteRequestVO.setParentDOB4(dob4);
			}else {
				validation.add("Parents Info Empty");
			}
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPinCode())) {
			String pincode = String.valueOf(getQuoteRequest.getPinCode());
			getQuoteRequestVO.setPinCode(pincode);
		}else {
			validation.add(ApplicationConstant.PINCODE_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPeriod())) {
			getQuoteRequestVO.setPeriod(getQuoteRequest.getPeriod());
		}else {
			getQuoteRequestVO.setPeriod(instalmentPremium1Year);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			getQuoteRequestVO.setOnlineDiscountPerc(getQuoteRequest.getOnlineDiscountPerc().trim());
		}
		
		String schemeCode = "";
		if(StringUtils.isNotBlank(getQuoteRequestVO.getAdults())) {
			schemeCode = schemeCode.concat(getQuoteRequestVO.getAdults()).concat("A");
		}
		if(StringUtils.isNotBlank(getQuoteRequestVO.getChild())) {
			schemeCode = schemeCode.concat("+").concat(getQuoteRequestVO.getChild()).concat("C");
		}
		getQuoteRequestVO.setSchemeCode(schemeCode);
		
		log.debug("BuildFHOSOAPRequest End {} ",getQuoteRequestVO);
		return getQuoteRequestVO;
	}
	

	public List<String> buildYoungStarQuery(GetQuoteRequest getQuoteRequest, String mappingProductCode) throws Exception {
		log.debug("Build Young Star Get Quote Query Starts");
		log.debug("Serch Criteria YoungStar = {}",productUtil.doMarshallingJSON(getQuoteRequest));
		
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty(ApplicationConstant.YOUNG_STAR_TABLE);
		String query1 = ApplicationConstant.SELECT;
		String query2 = ApplicationConstant.SELECT;
		
		query1 = query1.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(mappingProductCode).concat("'");
		query2 = query2.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(mappingProductCode).concat("'");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query1 = query1.concat(ApplicationConstant.INSURANCE_COVER_GREATER+getQuoteRequest.getInsuranceCover());
			query2 = query2.concat(ApplicationConstant.INSURANCE_COVER_GREATER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			query1 = query1.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
			query2 = query2.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
			
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				long adultDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB1());
				long adultDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB2());
				String adultDOB = "";
				if(adultDOB1Days>adultDOB2Days) {
					adultDOB = getQuoteRequest.getAdultDOB1().trim();
				}else {
					adultDOB = getQuoteRequest.getAdultDOB2().trim();
				}
				String  ageList= productAgeUtil.getYounStarAgeList(adultDOB, getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList)) {
					query1 = query1.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
					query2 = query2.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
				
			}else {
				validation.add("Adult DOB is Empty");
			}
			
		}else if(StringUtils.isNotBlank(getQuoteRequest.getOthers()) && !getQuoteRequest.getOthers().equalsIgnoreCase("0")){
			query1 = query1.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getOthers());
			query2 = query2.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getOthers());
			
			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {

				String adultDOB = getQuoteRequest.getOtherDOB1().trim();
				
				String  ageList= productAgeUtil.getYounStarAgeList(adultDOB, getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList)) {
					query1 = query1.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
					query2 = query2.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
				}else {
					validation.add(ApplicationConstant.OTHER_AGE_EMPTY);
				}

			}else {
				validation.add(ApplicationConstant.OTHERS_DOB_EMPTY);
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			query1 = query1.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
			query2 = query2.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());

		}else {
			query1 = query1.concat(ApplicationConstant.CHILD_NULL);
			query2 = query2.concat(ApplicationConstant.CHILD_NULL);
		}
		
		if (StringUtils.isNotBlank(getQuoteRequest.getPeriod())) {
			query1 = query1.concat(ApplicationConstant.PERIOD_Q).concat(getQuoteRequest.getPeriod().trim()).concat("'");
			query2 = query2.concat(ApplicationConstant.PERIOD_Q).concat(getQuoteRequest.getPeriod().trim()).concat("'");
		} else {
			query1 = query1.concat(ApplicationConstant.PERIOD_Q).concat(instalmentPremium1Year).concat("'");
			query2 = query2.concat(ApplicationConstant.PERIOD_Q).concat(instalmentPremium1Year).concat("'");
		}
		 
		query1 = query1.concat(ApplicationConstant.PLAN_Q).concat(youngStarPlanGOLD).concat("'");
		query2 = query2.concat(ApplicationConstant.PLAN_Q).concat(youngStarPlanSilver).concat("'");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query1 = query1.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
			query2 = query2.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query1 = query1.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
			query2 = query2.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		query1 = query1.concat(ApplicationConstant.ORDER_BY);
		query2 = query2.concat(ApplicationConstant.ORDER_BY);
		
		String query = "(";
		query = query.concat(query1).concat(ApplicationConstant.UNION_ALL).concat(query2).concat(")");
		
		log.debug("Build Young Star Get Quote Query End {}",query1);
		
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}
	
	
	public List<String> buildAllYoungStarQuery(GetQuoteRequest getQuoteRequest) throws Exception {
		log.debug("Build All Young Star Get Quote Query Starts");
		log.debug("Serch Criteria AllYoungStar {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty(ApplicationConstant.YOUNG_STAR_TABLE);
		String query = ApplicationConstant.SELECT;
		
		query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(getQuoteRequest.getProductId()).concat("'");
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query = query.concat(ApplicationConstant.INSURANCE_COVER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				long adultDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB1());
				long adultDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB2());
				String adultDOB = "";
				if(adultDOB1Days>adultDOB2Days) {
					adultDOB = getQuoteRequest.getAdultDOB1().trim();
				}else {
					adultDOB = getQuoteRequest.getAdultDOB2().trim();
				}
				String  ageList1 = productAgeUtil.getYounStarAgeList(adultDOB, instalmentPremium1Year);
				String  ageList2 = productAgeUtil.getYounStarAgeList(adultDOB, instalmentPremium2Year);
				String  ageList3 = productAgeUtil.getYounStarAgeList(adultDOB, instalmentPremium3Year);
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2) && StringUtils.isNotBlank(ageList3)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList1).concat("','").concat(ageList2).concat("','").concat(ageList3).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
		}else if(StringUtils.isNotBlank(getQuoteRequest.getOthers()) && !getQuoteRequest.getOthers().equalsIgnoreCase("0")){
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getOthers());

			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {

				String adultDOB = getQuoteRequest.getOtherDOB1().trim();
				
				String  ageList1 = productAgeUtil.getYounStarAgeList(adultDOB, instalmentPremium1Year);
				String  ageList2 = productAgeUtil.getYounStarAgeList(adultDOB, instalmentPremium2Year);
				String  ageList3 = productAgeUtil.getYounStarAgeList(adultDOB, instalmentPremium3Year);
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2) && StringUtils.isNotBlank(ageList3)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList1).concat("','").concat(ageList2).concat("','").concat(ageList3).concat("')");
				}else {
					validation.add(ApplicationConstant.OTHER_AGE_EMPTY);
				}
			}else {
				validation.add(ApplicationConstant.OTHERS_DOB_EMPTY);
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
		}else {
			query = query.concat(ApplicationConstant.CHILD_NULL);
		}
		
		query = query.concat(ApplicationConstant.PERIOD_Q_1).concat(instalmentPremium1Year).concat("','")
				.concat(instalmentPremium2Year).concat("','")
				.concat(instalmentPremium3Year).concat("')");

		if(StringUtils.isNotBlank(getQuoteRequest.getPlan())) {
			query = query.concat(ApplicationConstant.PLAN_Q).concat(getQuoteRequest.getPlan()).concat("'");
		}else {
			validation.add(ApplicationConstant.PLAN_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		log.debug("Build All Young Star Get Quote Query End {}",query);
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}
	

	public List<String> buildStarComprehensiveQuery(GetQuoteRequest getQuoteRequest, String mappingProductCode) throws Exception {
		log.debug("Build Star Comprehensive Get Quote Query Starts");
		log.debug("Serch Criteria StarComprehensive {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty(ApplicationConstant.YOUNG_STAR_TABLE);
		String query = ApplicationConstant.SELECT;
		
		query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(mappingProductCode).concat("'");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query = query.concat(ApplicationConstant.INSURANCE_COVER_GREATER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		int adultCount = 0;
		List<String> dateList = new ArrayList<>();
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults())){
			adultCount = Integer.parseInt(getQuoteRequest.getAdults().trim());
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())) {
				dateList.add(getQuoteRequest.getAdultDOB1().trim());
			}
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				dateList.add(getQuoteRequest.getAdultDOB2().trim());
			}
		}
		if(StringUtils.isNotBlank(getQuoteRequest.getOthers())) {
			adultCount = adultCount + Integer.parseInt(getQuoteRequest.getOthers().trim());
			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {
				dateList.add(getQuoteRequest.getOtherDOB1().trim());
			}
		}
		if(StringUtils.isNotBlank(getQuoteRequest.getParents())) {
			adultCount = adultCount + Integer.parseInt(getQuoteRequest.getParents().trim());
			if(StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())) {
				dateList.add(getQuoteRequest.getParentDOB1().trim());
			}
			if(StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				dateList.add(getQuoteRequest.getParentDOB2().trim());
			}
		}
		
		if(adultCount>0) {
			query = query.concat(ApplicationConstant.ADULTS_Q+String.valueOf(adultCount));
			
			if(dateList.size()>0) {
				dateList = productUtil.dateSortingAscending(dateList);
				String  ageList= productAgeUtil.getStarComprehensiveAgeList(dateList.get(0), getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
		}else {
			query = query.concat(ApplicationConstant.CHILD_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPeriod())) {
			query = query.concat(ApplicationConstant.PERIOD_Q).concat(getQuoteRequest.getPeriod().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.PERIOD_Q).concat(instalmentPremium1Year).concat("'");
		}

		if(StringUtils.isNotBlank(getQuoteRequest.getBuyBackPedYN())) {
			query = query.concat(" AND buyBackPedYN = '").concat(getQuoteRequest.getBuyBackPedYN()).concat("'");
		}else {
			query = query.concat(" AND buyBackPedYN = 'N'");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		query = query.concat(ApplicationConstant.ORDER_BY);
		
		log.debug("Build Star Comprehensive Get Quote Query End {}",query);
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}
	
	public List<String> buildAllStarComprehensiveQuery(GetQuoteRequest getQuoteRequest) throws Exception {
		log.debug("Build All Star Comprehensive Get Quote Query Starts");
		log.debug("Serch Criteria AllStarComprehensive {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty(ApplicationConstant.YOUNG_STAR_TABLE);
		String query = ApplicationConstant.SELECT;
		
		query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(getQuoteRequest.getProductId()).concat("'");
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query = query.concat(ApplicationConstant.INSURANCE_COVER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		int adultCount = 0;
		List<String> dateList = new ArrayList<>();
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults())){
			adultCount = Integer.parseInt(getQuoteRequest.getAdults().trim());
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())) {
				dateList.add(getQuoteRequest.getAdultDOB1().trim());
			}
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				dateList.add(getQuoteRequest.getAdultDOB2().trim());
			}
		}
		if(StringUtils.isNotBlank(getQuoteRequest.getOthers())) {
			adultCount = adultCount + Integer.parseInt(getQuoteRequest.getOthers().trim());
			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {
				dateList.add(getQuoteRequest.getOtherDOB1().trim());
			}
		}
		if(StringUtils.isNotBlank(getQuoteRequest.getParents())) {
			adultCount = adultCount + Integer.parseInt(getQuoteRequest.getParents().trim());
			if(StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())) {
				dateList.add(getQuoteRequest.getParentDOB1().trim());
			}
			if(StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				dateList.add(getQuoteRequest.getParentDOB2().trim());
			}
		}
		if(adultCount>0) {
			query = query.concat(ApplicationConstant.ADULTS_Q+String.valueOf(adultCount));
			
			if(dateList.size()>0) {
				dateList = productUtil.dateSortingAscending(dateList);
				String  ageBand1 = productAgeUtil.getStarComprehensiveAgeList(dateList.get(0),  instalmentPremium1Year);
				String  ageBand2 = productAgeUtil.getStarComprehensiveAgeList(dateList.get(0),  instalmentPremium2Year);
				String  ageBand3 = productAgeUtil.getStarComprehensiveAgeList(dateList.get(0),  instalmentPremium3Year);
				if(StringUtils.isNotBlank(ageBand1) && StringUtils.isNotBlank(ageBand2) && StringUtils.isNotBlank(ageBand3)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageBand1).concat("','").concat(ageBand2).concat("','").concat(ageBand3).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
		}else {
			query = query.concat(ApplicationConstant.CHILD_NULL);
		}
		
		query = query.concat(ApplicationConstant.PERIOD_Q_1).concat(instalmentPremium1Year).concat("','")
				.concat(instalmentPremium2Year).concat("','")
				.concat(instalmentPremium3Year).concat("')");

		if(StringUtils.isNotBlank(getQuoteRequest.getBuyBackPedYN())) {
			query = query.concat(" AND buyBackPedYN = '").concat(getQuoteRequest.getBuyBackPedYN()).concat("'");
		}else {
			query = query.concat(" AND buyBackPedYN = 'N'");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		log.debug("Build All Star Comprehensive Get Quote Query End {} ",query);
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}

	
	public List<String> buildArogyaSanjeevaniQuery(GetQuoteRequest getQuoteRequest, String mappingProductCode) throws Exception {
		log.debug("Build Arogya Sanjeevani Get Quote Query Starts");
		log.debug("Serch Criteria ArogyaSanjeevani {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty("ArogyaSanjeevani_Table");
		String query = ApplicationConstant.SELECT;
		
		query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(mappingProductCode).concat("'");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query = query.concat(ApplicationConstant.INSURANCE_COVER_GREATER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		boolean isOnlyTwoParent = false;
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
		
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				
				long adultDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB1());
				long adultDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB2());
				String adultDOB = "";
				if(adultDOB1Days>adultDOB2Days) {
					adultDOB = getQuoteRequest.getAdultDOB1().trim();
				}else {
					adultDOB = getQuoteRequest.getAdultDOB2().trim();
				}
				
				if(getQuoteRequest.getAdults().equals("1") && StringUtils.isBlank(getQuoteRequest.getChild())
						&& StringUtils.isBlank(getQuoteRequest.getParents())) {
					String  ageList = productAgeUtil.getArogyaSanjeevaniAdultsAgeList(adultDOB, individual);
					if(StringUtils.isNotBlank(ageList)) {
						query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
					}else {
						validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
					}
				}else {
					String  ageList = productAgeUtil.getArogyaSanjeevaniAdultsAgeList(adultDOB, floater);
					if(StringUtils.isNotBlank(ageList)) {
						query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
					}else {
						validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
					}
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
		}else if(getQuoteRequest.getParents().equalsIgnoreCase("2"))  {
			isOnlyTwoParent = true;
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getParents());
			
			if(StringUtils.isNotBlank(getQuoteRequest.getParentDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				long parentDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getParentDOB1().trim());
				long parentDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getParentDOB2().trim());
				String parentDOB = "";
				if(parentDOB1Days>parentDOB2Days) {
					parentDOB = getQuoteRequest.getParentDOB1().trim();
				}else {
					parentDOB = getQuoteRequest.getParentDOB2().trim();
				}
				String  ageList = productAgeUtil.getArogyaSanjeevaniAdultsAgeList(parentDOB, floater);
				if(StringUtils.isNotBlank(ageList)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else {
				validation.add("Parents DOB is Empty");
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
		}else {
			query = query.concat(ApplicationConstant.CHILD_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getParents()) && !getQuoteRequest.getParents().equalsIgnoreCase("0") && !isOnlyTwoParent) {
			query = query.concat(ApplicationConstant.PARENTS_Q+getQuoteRequest.getParents());
			
			if(getQuoteRequest.getParents().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())) {
				String  ageList= productAgeUtil.getArogyaSanjeevaniParentsAgeList(getQuoteRequest.getParentDOB1().trim());
				if(StringUtils.isNotBlank(ageList)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1()) 
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(0));
				String  ageList2= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(1));
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(ageList2).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
				
			}else if(getQuoteRequest.getParents().equals("3") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(0));
				String  ageList2= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(1));
				String  ageList3= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(2));
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2) && StringUtils.isNotBlank(ageList3)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(ageList2).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(ageList3).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("4") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB4())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList.add(getQuoteRequest.getParentDOB4().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(0));
				String  ageList2= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(1));
				String  ageList3= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(2));
				String  ageList4= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(3));
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2) && StringUtils.isNotBlank(ageList3) && StringUtils.isNotBlank(ageList4)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(ageList2).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(ageList3).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_4).concat(ageList4).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else {
					validation.add(ApplicationConstant.PARENTS_INFO_MISSING);
			}
		}else {
			query = query.concat(ApplicationConstant.PARENT_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPeriod())) {
			query = query.concat(ApplicationConstant.PERIOD_Q).concat(getQuoteRequest.getPeriod().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.PERIOD_Q).concat(instalmentPremium1Year).concat("'");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		query = query.concat(ApplicationConstant.ORDER_BY);
		
		log.debug("Build Arogya Sanjeevani Get Quote Query End {}",query);
		
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}
	
	
	public List<String> buildAllArogyaSanjeevaniQuery(GetQuoteRequest getQuoteRequest) throws Exception {
		log.debug("Build All Arogya Sanjeevani Get Quote Query Starts");
		log.debug("Serch Criteria AllArogyaSanjeevani {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty("ArogyaSanjeevani_Table");
		String query = ApplicationConstant.SELECT;
		
		query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(getQuoteRequest.getProductId()).concat("'");
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query = query.concat(ApplicationConstant.INSURANCE_COVER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		boolean isOnlyTwoParent = false;
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
		
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				
				long adultDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB1());
				long adultDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB2());
				String adultDOB = "";
				if(adultDOB1Days>adultDOB2Days) {
					adultDOB = getQuoteRequest.getAdultDOB1().trim();
				}else {
					adultDOB = getQuoteRequest.getAdultDOB2().trim();
				}
				
				if(getQuoteRequest.getAdults().equals("1") && StringUtils.isBlank(getQuoteRequest.getChild())
						&& StringUtils.isBlank(getQuoteRequest.getParents())) {
					String  ageList = productAgeUtil.getArogyaSanjeevaniAdultsAgeList(adultDOB, individual);
					if(StringUtils.isNotBlank(ageList)) {
						query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
					}else {
						validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
					}
				}else {
					String  ageList = productAgeUtil.getArogyaSanjeevaniAdultsAgeList(adultDOB, floater);
					if(StringUtils.isNotBlank(ageList)) {
						query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
					}else {
						validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
					}
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
		}else if(getQuoteRequest.getParents().equalsIgnoreCase("2"))  {
			isOnlyTwoParent = true;
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getParents());
			if(StringUtils.isNotBlank(getQuoteRequest.getParentDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				long parentDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getParentDOB1().trim());
				long parentDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getParentDOB2().trim());
				String parentDOB = "";
				if(parentDOB1Days>parentDOB2Days) {
					parentDOB = getQuoteRequest.getParentDOB1().trim();
				}else {
					parentDOB = getQuoteRequest.getParentDOB2().trim();
				}
				String  ageList = productAgeUtil.getArogyaSanjeevaniAdultsAgeList(parentDOB, floater);
				if(StringUtils.isNotBlank(ageList)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else {
				validation.add("Parents DOB is Empty");
			}

		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
		}else {
			query = query.concat(ApplicationConstant.CHILD_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getParents()) && !getQuoteRequest.getParents().equalsIgnoreCase("0") && !isOnlyTwoParent) {
			query = query.concat(ApplicationConstant.PARENTS_Q+getQuoteRequest.getParents());
			
			if(getQuoteRequest.getParents().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())) {
				String  ageList1= productAgeUtil.getArogyaSanjeevaniParentsAgeList(getQuoteRequest.getParentDOB1().trim());
				if(StringUtils.isNotBlank(ageList1)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList1).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1()) 
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(0));
				String  ageList2= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(1));
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(ageList2).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("3") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(0));
				String  ageList2= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(1));
				String  ageList3= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(2));
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2) && StringUtils.isNotBlank(ageList3)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(ageList2).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(ageList3).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("4") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB4())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList.add(getQuoteRequest.getParentDOB4().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(0));
				String  ageList2= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(1));
				String  ageList3= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(2));
				String  ageList4= productAgeUtil.getArogyaSanjeevaniParentsAgeList(dateList.get(3));
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2) && StringUtils.isNotBlank(ageList3) && StringUtils.isNotBlank(ageList4)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(ageList2).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(ageList3).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_4).concat(ageList4).concat("')");	
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.PARENTS_INFO_MISSING);
			}
		}else {
			query = query.concat(ApplicationConstant.PARENT_NULL);
		}
		
		query = query.concat(ApplicationConstant.PERIOD_Q_1).concat(instalmentPremium1Year).concat("','")
				.concat(instalmentPremium2Year).concat("','")
				.concat(instalmentPremium3Year).concat("')");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		log.debug("Build All Arogya Sanjeevani Get Quote Query End {}",query);
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}
	
	
	
	public List<String> buildSuperSurplusIndividualQuery(GetQuoteRequest getQuoteRequest, String mappingProductCode) throws Exception {
		log.debug("Build Super Surplus Individual Get Quote Query Starts");
		log.debug("Serch Criteria SuperSurplusIndividual {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty("Super_surplus_individual_Table");
		String query1 = ApplicationConstant.SELECT;
		String query2 = ApplicationConstant.SELECT;
		
		query1 = query1.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(mappingProductCode).concat("'");
		query2 = query2.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(mappingProductCode).concat("'");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query1 = query1.concat(ApplicationConstant.INSURANCE_COVER_GREATER+getQuoteRequest.getInsuranceCover());
			query2 = query2.concat(ApplicationConstant.INSURANCE_COVER_GREATER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			query1 = query1.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
			query2 = query2.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());

			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				long adultDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB1());
				long adultDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB2());
				String adultDOB = "";
				if(adultDOB1Days>adultDOB2Days) {
					adultDOB = getQuoteRequest.getAdultDOB1().trim();
				}else {
					adultDOB = getQuoteRequest.getAdultDOB2().trim();
				}
				
				String  ageList = productAgeUtil.getSuperSurplusAgeList(adultDOB,  getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList)) {
					query1 = query1.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
					query2 = query2.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
		}else if(StringUtils.isNotBlank(getQuoteRequest.getOthers()) && !getQuoteRequest.getOthers().equalsIgnoreCase("0")){
			query1 = query1.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getOthers());
			query2 = query2.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getOthers());

			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {

				String adultDOB = getQuoteRequest.getOtherDOB1().trim();
				
				String  ageList = productAgeUtil.getSuperSurplusAgeList(adultDOB,  getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList)) {
					query1 = query1.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
					query2 = query2.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
				}else {
					validation.add(ApplicationConstant.OTHER_AGE_EMPTY);
				}
			}else {
				validation.add(ApplicationConstant.OTHERS_DOB_EMPTY);
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if (StringUtils.isNotBlank(getQuoteRequest.getPeriod())) {
			query1 = query1.concat(ApplicationConstant.PERIOD_Q).concat(getQuoteRequest.getPeriod().trim()).concat("'");
			query2 = query2.concat(ApplicationConstant.PERIOD_Q).concat(getQuoteRequest.getPeriod().trim()).concat("'");
		} else {
			query1 = query1.concat(ApplicationConstant.PERIOD_Q).concat(instalmentPremium1Year).concat("'");
			query2 = query2.concat(ApplicationConstant.PERIOD_Q).concat(instalmentPremium1Year).concat("'");
		}
		 
		query1 = query1.concat(ApplicationConstant.PLAN_Q).concat(superSurplusPlanGold).concat("'");
		query2 = query2.concat(ApplicationConstant.PLAN_Q).concat(superSurplusPlanSilver).concat("'");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getDeduction())) {
			query1 = query1.concat(ApplicationConstant.DEDUCTION_Q).concat(getQuoteRequest.getDeduction()).concat("'");
			query2 = query2.concat(ApplicationConstant.DEDUCTION_Q).concat(getQuoteRequest.getDeduction()).concat("'");
		}else {
			validation.add(ApplicationConstant.DEDUCTION_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query1 = query1.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
			query2 = query2.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query1 = query1.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
			query2 = query2.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		query1 = query1.concat(ApplicationConstant.ORDER_BY);
		query2 = query2.concat(ApplicationConstant.ORDER_BY);
		
		String query = "(";
		query = query.concat(query1).concat(ApplicationConstant.UNION_ALL).concat(query2).concat(")");
		
		log.debug("Build Super Surplus Individual Get Quote Query End {}",query);
		
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}
	
	
	
	public List<String> buildAllSuperSurplusIndividualQuery(GetQuoteRequest getQuoteRequest) throws Exception {
		log.debug("Build All Super Surplus Individual Get Quote Query Starts");
		log.debug("Serch Criteria AllSuperSurplusIndividual {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty("Super_surplus_individual_Table");
		String query = ApplicationConstant.SELECT;
		
		query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(getQuoteRequest.getProductId()).concat("'");
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query = query.concat(ApplicationConstant.INSURANCE_COVER+getQuoteRequest.getInsuranceCover());

		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
			
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				long adultDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB1());
				long adultDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB2());
				String adultDOB = "";
				if(adultDOB1Days>adultDOB2Days) {
					adultDOB = getQuoteRequest.getAdultDOB1().trim();
				}else {
					adultDOB = getQuoteRequest.getAdultDOB2().trim();
				}
				
				String  ageList1 = productAgeUtil.getSuperSurplusAgeList(adultDOB,  instalmentPremium1Year);
				String  ageList2 = productAgeUtil.getSuperSurplusAgeList(adultDOB,  instalmentPremium2Year);
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList1).concat("','").concat(ageList2).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
		}else if(StringUtils.isNotBlank(getQuoteRequest.getOthers()) && !getQuoteRequest.getOthers().equalsIgnoreCase("0")){
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getOthers());

			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {

				String adultDOB = getQuoteRequest.getOtherDOB1().trim();
				
				String  ageList1 = productAgeUtil.getSuperSurplusAgeList(adultDOB,  instalmentPremium1Year);
				String  ageList2 = productAgeUtil.getSuperSurplusAgeList(adultDOB,  instalmentPremium2Year);
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList1).concat("','").concat(ageList2).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.OTHERS_DOB_EMPTY);
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		query = query.concat(ApplicationConstant.PERIOD_Q_1).concat(instalmentPremium1Year).concat("','")
				.concat(instalmentPremium2Year).concat("')");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPlan())) {
			query = query.concat(ApplicationConstant.PLAN_Q).concat(getQuoteRequest.getPlan()).concat("'");
		}else {
			validation.add(ApplicationConstant.PLAN_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getDeduction())) {
			query = query.concat(ApplicationConstant.DEDUCTION_Q).concat(getQuoteRequest.getDeduction()).concat("'");
		}else {
			validation.add(ApplicationConstant.DEDUCTION_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		log.debug("Build All Super Surplus Individual Get Quote Query End {} ",query);
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}
	
	
	public List<String> buildSuperSurplusFloaterQuery(GetQuoteRequest getQuoteRequest, String mappingProductCode) throws Exception {
		log.debug("Build Super Surplus Floater Get Quote Query Starts");
		log.debug("Serch Criteria SuperSurplusFloater {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty("Super_surplus_floater_Table");
		String query1 = ApplicationConstant.SELECT;
		String query2 = ApplicationConstant.SELECT;
		
		query1 = query1.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(mappingProductCode).concat("'");
		query2 = query2.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(mappingProductCode).concat("'");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query1 = query1.concat(ApplicationConstant.INSURANCE_COVER_GREATER+getQuoteRequest.getInsuranceCover());
			query2 = query2.concat(ApplicationConstant.INSURANCE_COVER_GREATER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			query1 = query1.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
			query2 = query2.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
			
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				long adultDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB1());
				long adultDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB2());
				String adultDOB = "";
				if(adultDOB1Days>adultDOB2Days) {
					adultDOB = getQuoteRequest.getAdultDOB1().trim();
				}else {
					adultDOB = getQuoteRequest.getAdultDOB2().trim();
				}
				String  ageList = productAgeUtil.getSuperSurplusAgeList(adultDOB,  getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList)){
					query1 = query1.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
					query2 = query2.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
			
		}else if(StringUtils.isNotBlank(getQuoteRequest.getParents()) && getQuoteRequest.getParents().equalsIgnoreCase("2")) {
			query1 = query1.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getParents());
			query2 = query2.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getParents());
			
			if(StringUtils.isNotBlank(getQuoteRequest.getParentDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				long adultDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getParentDOB1());
				long adultDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getParentDOB2());
				String adultDOB = "";
				if(adultDOB1Days>adultDOB2Days) {
					adultDOB = getQuoteRequest.getParentDOB1().trim();
				}else {
					adultDOB = getQuoteRequest.getParentDOB2().trim();
				}
				String  ageList = productAgeUtil.getSuperSurplusAgeList(adultDOB,  getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList)){
					query1 = query1.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
					query2 = query2.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
			
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			query1 = query1.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
			query2 = query2.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
		}else {
			query1 = query1.concat(ApplicationConstant.CHILD_NULL);
			query2 = query2.concat(ApplicationConstant.CHILD_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPeriod())) {
			query1 = query1.concat(ApplicationConstant.PERIOD_Q).concat(getQuoteRequest.getPeriod().trim()).concat("'");
			query2 = query2.concat(ApplicationConstant.PERIOD_Q).concat(getQuoteRequest.getPeriod().trim()).concat("'");
		}else {
			query1 = query1.concat(ApplicationConstant.PERIOD_Q).concat(instalmentPremium1Year).concat("'");
			query2 = query2.concat(ApplicationConstant.PERIOD_Q).concat(instalmentPremium1Year).concat("'");
		}
		
		query1 = query1.concat(ApplicationConstant.PLAN_Q).concat(superSurplusPlanGold).concat("'");
		query2 = query2.concat(ApplicationConstant.PLAN_Q).concat(superSurplusPlanSilver).concat("'");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getDeduction())) {
			query1 = query1.concat(ApplicationConstant.DEDUCTION_Q).concat(getQuoteRequest.getDeduction()).concat("'");
			query2 = query2.concat(ApplicationConstant.DEDUCTION_Q).concat(getQuoteRequest.getDeduction()).concat("'");
		}else {
			validation.add(ApplicationConstant.DEDUCTION_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query1 = query1.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
			query2 = query2.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query1 = query1.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
			query2 = query2.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		query1 = query1.concat(ApplicationConstant.ORDER_BY);
		query2 = query2.concat(ApplicationConstant.ORDER_BY);
		
		String query = "(";
		query = query.concat(query1).concat(ApplicationConstant.UNION_ALL).concat(query2).concat(")");

		log.debug("Build Super Surplus Floater Get Quote Query End {}",query);
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}
	
	
	
	public List<String> buildAllSuperSurplusFloaterQuery(GetQuoteRequest getQuoteRequest) throws Exception {
		log.debug("Build All Super Surplus Floater Get Quote Query Starts");
		log.debug("Serch Criteria AllSuperSurplusFloater {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty("Super_surplus_floater_Table");
		String query = ApplicationConstant.SELECT;
		
		query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(getQuoteRequest.getProductId()).concat("'");
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query = query.concat(ApplicationConstant.INSURANCE_COVER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
			
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				long adultDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB1());
				long adultDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB2());
				String adultDOB = "";
				if(adultDOB1Days>adultDOB2Days) {
					adultDOB = getQuoteRequest.getAdultDOB1().trim();
				}else {
					adultDOB = getQuoteRequest.getAdultDOB2().trim();
				}
				String  ageList1 = productAgeUtil.getSuperSurplusAgeList(adultDOB,  instalmentPremium1Year);
				String  ageList2 = productAgeUtil.getSuperSurplusAgeList(adultDOB,  instalmentPremium2Year);
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList1).concat("','").concat(ageList2).concat("')");

			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
			
		}else if(StringUtils.isNotBlank(getQuoteRequest.getParents()) && getQuoteRequest.getParents().equalsIgnoreCase("2")) {
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getParents());
			
			if(StringUtils.isNotBlank(getQuoteRequest.getParentDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				long adultDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getParentDOB1());
				long adultDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getParentDOB2());
				String adultDOB = "";
				if(adultDOB1Days>adultDOB2Days) {
					adultDOB = getQuoteRequest.getParentDOB1().trim();
				}else {
					adultDOB = getQuoteRequest.getParentDOB2().trim();
				}
				String  ageList1 = productAgeUtil.getSuperSurplusAgeList(adultDOB,  instalmentPremium1Year);
				String  ageList2 = productAgeUtil.getSuperSurplusAgeList(adultDOB,  instalmentPremium2Year);
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList1).concat("','").concat(ageList2).concat("')");

			}else {
				validation.add("Adults/Parents DOB is Empty");
			}
			
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
		}else {
			query = query.concat(ApplicationConstant.CHILD_NULL);
		}
		
		query = query.concat(ApplicationConstant.PERIOD_Q_1).concat(instalmentPremium1Year).concat("','")
				.concat(instalmentPremium2Year).concat("')");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPlan())) {
			query = query.concat(ApplicationConstant.PLAN_Q).concat(getQuoteRequest.getPlan()).concat("'");
		}else {
			validation.add(ApplicationConstant.PLAN_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getDeduction())) {
			query = query.concat(ApplicationConstant.DEDUCTION_Q).concat(getQuoteRequest.getDeduction()).concat("'");
		}else {
			validation.add(ApplicationConstant.DEDUCTION_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		log.debug("Build All Super Surplus Floater Get Quote Query End {} ",query);
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}
	
	
	public List<String> buildHealthPremierQuery(GetQuoteRequest getQuoteRequest, String mappingProductCode) throws Exception {
		log.debug("Build Health Premier Get Quote Query Starts");
		log.info("Serch Criteria HealthPremier {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty("Health_Premier_Table");
		String query = ApplicationConstant.SELECT;
		
		query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(mappingProductCode).concat("'");
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query = query.concat(ApplicationConstant.INSURANCE_COVER_GREATER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
			
			if(getQuoteRequest.getAdults().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())) {
				String  ageList= productAgeUtil.getHealthPremierAdultAgeList(getQuoteRequest.getAdultDOB1().trim(), getQuoteRequest.getPeriod());
				if(StringUtils.isNotBlank(ageList)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getAdults().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())){
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getAdultDOB1().trim());
				dateList.add(getQuoteRequest.getAdultDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(0), getQuoteRequest.getPeriod());
				String  ageList2= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(1), getQuoteRequest.getPeriod());
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(ageList2).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
			}
		}else if(StringUtils.isNotBlank(getQuoteRequest.getOthers()) && !getQuoteRequest.getOthers().equalsIgnoreCase("0")){
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getOthers());

			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {

				String adultDOB = getQuoteRequest.getOtherDOB1().trim();
				String  ageList= productAgeUtil.getHealthPremierAdultAgeList(adultDOB, getQuoteRequest.getPeriod());
				if(StringUtils.isNotBlank(ageList)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
				}else {
					validation.add(ApplicationConstant.OTHER_AGE_EMPTY);
				}
			}else {
				validation.add(ApplicationConstant.OTHERS_DOB_EMPTY);
			}
		}else if(getQuoteRequest.getParents().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
				&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())){
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getParents());
			ArrayList<String> dateList = new ArrayList<>();
			dateList.add(getQuoteRequest.getParentDOB1().trim());
			dateList.add(getQuoteRequest.getParentDOB2().trim());
			dateList = productUtil.dateSortingDescending(dateList);
			
			String  ageList1= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(0), getQuoteRequest.getPeriod());
			String  ageList2= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(1), getQuoteRequest.getPeriod());
			if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2)) {
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList1).concat("')");
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(ageList2).concat("')");
			}else {
				validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
			
			if(getQuoteRequest.getChild().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getChildDOB1())) {
				String  ageList1= productAgeUtil.getHealthPremierChildAgeList(getQuoteRequest.getChildDOB1().trim(), getQuoteRequest.getPeriod());
				if(StringUtils.isNotBlank(ageList1)) {
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_11).concat(ageList1).concat("')");
				}else {
					validation.add(ApplicationConstant.CHILD_AGE_MISSING);
				}
			}else if(getQuoteRequest.getChild().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getChildDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getChildDOB2())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getChildDOB1().trim());
				dateList.add(getQuoteRequest.getChildDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getHealthPremierChildAgeList(dateList.get(0), getQuoteRequest.getPeriod());
				String  ageList2= productAgeUtil.getHealthPremierChildAgeList(dateList.get(1), getQuoteRequest.getPeriod());
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2)) {
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_11).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_2).concat(ageList2).concat("')");
				}else {
					validation.add(ApplicationConstant.CHILD_AGE_MISSING);
				}
			}else if(getQuoteRequest.getChild().equals("3") && StringUtils.isNotBlank(getQuoteRequest.getChildDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getChildDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getChildDOB3())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getChildDOB1().trim());
				dateList.add(getQuoteRequest.getChildDOB2().trim());
				dateList.add(getQuoteRequest.getChildDOB3().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getHealthPremierChildAgeList(dateList.get(0), getQuoteRequest.getPeriod());
				String  ageList2= productAgeUtil.getHealthPremierChildAgeList(dateList.get(1), getQuoteRequest.getPeriod());
				String  ageList3= productAgeUtil.getHealthPremierChildAgeList(dateList.get(2), getQuoteRequest.getPeriod());
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2) && StringUtils.isNotBlank(ageList3)) {
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_11).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_2).concat(ageList2).concat("')");
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_3).concat(ageList3).concat("')");
				}else {
					validation.add(ApplicationConstant.CHILD_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.CHILD_MISSING);
			}
		}else {
			query = query.concat(ApplicationConstant.CHILD_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPinCode())) {
			String zone = "";
			String pincode = String.valueOf(getQuoteRequest.getPinCode());
			zone = productPincodeRepo.getPincodeByZone(mappingProductCode.trim(), pincode.trim());
			if(StringUtils.isNotBlank(zone)) {
				query = query.concat(ApplicationConstant.ZONE).concat(zone).concat("'");
			}else {
				validation.add(ApplicationConstant.ZONE_EMPTY);
			}
		}else {
			validation.add(ApplicationConstant.PINCODE_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPeriod())) {
			query = query.concat(ApplicationConstant.PERIOD_Q).concat(getQuoteRequest.getPeriod().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.PERIOD_Q).concat(instalmentPremium1Year).concat("'");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		query = query.concat(ApplicationConstant.ORDER_BY);
		
		log.debug("Build Health Premier Get Quote Query End {}",query);
		
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		
		return validation;
	}
	
	public GetQuoteRequestVO buildHealthPremierSOAPRequest(GetQuoteRequest getQuoteRequest, String mappingProductCode) throws Exception {
		log.debug("BuildHealthPremierSOAPRequest Starts");
		GetQuoteRequestVO getQuoteRequestVO = new GetQuoteRequestVO();
		log.debug("Serch Criteria HealthPremierSOAP {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		
		getQuoteRequestVO.setProductCode(mappingProductCode);
		getQuoteRequestVO.setProductName(healthPremierProductName);
		
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			ProductsSumInsureVO productsSumInsureVO = productsSumInsureRepo.getAvailableSumInsure(mappingProductCode, getQuoteRequest.getInsuranceCover().trim(),
					getQuoteRequest.getPeriod());
			getQuoteRequest.setInsuranceCover(String.valueOf(productsSumInsureVO.getSumInsure()));
			getQuoteRequestVO.setInsuranceCover(getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			getQuoteRequestVO.setAdults(getQuoteRequest.getAdults());
			if(getQuoteRequest.getAdults().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())) {
				String age = String.valueOf(productUtil.getAgeInYear(getQuoteRequest.getAdultDOB1().trim()));
				String dob = productUtil.getDOBFromAge(age);
				getQuoteRequestVO.setAdultDOB1(dob);
			}else if(getQuoteRequest.getAdults().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())){
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getAdultDOB1().trim());
				dateList.add(getQuoteRequest.getAdultDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String age1 = String.valueOf(productUtil.getAgeInYear(dateList.get(0)));
				String age2 = String.valueOf(productUtil.getAgeInYear(dateList.get(1)));
				String dob1 = productUtil.getDOBFromAge(age1);
				String dob2 = productUtil.getDOBFromAge(age2);
				getQuoteRequestVO.setAdultDOB1(dob1);
				getQuoteRequestVO.setAdultDOB2(dob2);
			}else {
				validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
			}
		}else if(StringUtils.isNotBlank(getQuoteRequest.getOthers()) && !getQuoteRequest.getOthers().equalsIgnoreCase("0")){
			getQuoteRequestVO.setAdults(getQuoteRequest.getOthers());
			
			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {
				String age = String.valueOf(productUtil.getAgeInYear(getQuoteRequest.getOthers().trim()));
				String dob = productUtil.getDOBFromAge(age);
				getQuoteRequestVO.setAdultDOB1(dob);
			}else {
				validation.add(ApplicationConstant.OTHERS_DOB_EMPTY);
			}
		}else if(getQuoteRequest.getParents().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
				&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())){
			ArrayList<String> dateList = new ArrayList<>();
			dateList.add(getQuoteRequest.getParentDOB1().trim());
			dateList.add(getQuoteRequest.getParentDOB2().trim());
			dateList = productUtil.dateSortingDescending(dateList);
			
			String age1 = String.valueOf(productUtil.getAgeInYear(dateList.get(0)));
			String age2 = String.valueOf(productUtil.getAgeInYear(dateList.get(1)));
			String dob1 = productUtil.getDOBFromAge(age1);
			String dob2 = productUtil.getDOBFromAge(age2);
			getQuoteRequestVO.setAdultDOB1(dob1);
			getQuoteRequestVO.setAdultDOB2(dob2);
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			getQuoteRequestVO.setChild(getQuoteRequest.getChild());
			if(getQuoteRequest.getChild().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getChildDOB1())) {
				String age = String.valueOf(productUtil.getAgeInYear(getQuoteRequest.getChildDOB1().trim()));
				String DOB = productUtil.getDOBFromAge(age);
				getQuoteRequestVO.setChildDOB1(DOB);
			}else if(getQuoteRequest.getChild().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getChildDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getChildDOB2())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getChildDOB1().trim());
				dateList.add(getQuoteRequest.getChildDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String age1 = String.valueOf(productUtil.getAgeInYear(dateList.get(0)));
				String age2 = String.valueOf(productUtil.getAgeInYear(dateList.get(1)));
				String dob1 = productUtil.getDOBFromAge(age1);
				String dob2 = productUtil.getDOBFromAge(age2);
				getQuoteRequestVO.setChildDOB1(dob1);
				getQuoteRequestVO.setChildDOB2(dob2);
			}else if(getQuoteRequest.getChild().equals("3") && StringUtils.isNotBlank(getQuoteRequest.getChildDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getChildDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getChildDOB3())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getChildDOB1().trim());
				dateList.add(getQuoteRequest.getChildDOB2().trim());
				dateList.add(getQuoteRequest.getChildDOB3().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String age1 = String.valueOf(productUtil.getAgeInYear(dateList.get(0)));
				String age2 = String.valueOf(productUtil.getAgeInYear(dateList.get(1)));
				String age3 = String.valueOf(productUtil.getAgeInYear(dateList.get(2)));
				String dob1 = productUtil.getDOBFromAge(age1);
				String dob2 = productUtil.getDOBFromAge(age2);
				String dob3 = productUtil.getDOBFromAge(age3);
				getQuoteRequestVO.setChildDOB1(dob1);
				getQuoteRequestVO.setChildDOB2(dob2);
				getQuoteRequestVO.setChildDOB3(dob3);
			}else {
				validation.add(ApplicationConstant.CHILD_MISSING);
			}
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPinCode())) {
			String pincode = String.valueOf(getQuoteRequest.getPinCode());
			getQuoteRequestVO.setPinCode(pincode);
		}else {
			validation.add(ApplicationConstant.PINCODE_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPeriod())) {
			getQuoteRequestVO.setPeriod(getQuoteRequest.getPeriod().trim());
		}else {
			getQuoteRequestVO.setPeriod(instalmentPremium1Year);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			getQuoteRequestVO.setOnlineDiscountPerc(getQuoteRequest.getOnlineDiscountPerc().trim());
		}
		
		String schemeCode = "";
		if(StringUtils.isNotBlank(getQuoteRequestVO.getAdults())) {
			schemeCode = schemeCode.concat(getQuoteRequestVO.getAdults()).concat("A");
		}
		if(StringUtils.isNotBlank(getQuoteRequestVO.getChild())) {
			schemeCode = schemeCode.concat("+").concat(getQuoteRequestVO.getChild()).concat("C");
		}
		getQuoteRequestVO.setSchemeCode(schemeCode);
		
		log.debug("BuildHealthPremierSOAPRequest End {} ",getQuoteRequestVO);
		
		return getQuoteRequestVO;
	}
	
	
	public List<String> buildAllHealthPremierQuery(GetQuoteRequest getQuoteRequest) throws Exception {
		log.debug("Build All Health Premier Get Quote Query Starts");
		log.debug("Serch Criteria AllHealthPremier {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty("Health_Premier_Table");
		String query = ApplicationConstant.SELECT;
		
		query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(getQuoteRequest.getProductId()).concat("'");
		if(StringUtils.isNotBlank(query)) {
			query = query.concat(ApplicationConstant.INSURANCE_COVER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
			
			if(getQuoteRequest.getAdults().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())) {
				String  ageList1= productAgeUtil.getHealthPremierAdultAgeList(getQuoteRequest.getAdultDOB1().trim(), instalmentPremium1Year);
				String  ageList2= productAgeUtil.getHealthPremierAdultAgeList(getQuoteRequest.getAdultDOB1().trim(), instalmentPremium2Year);
				String  ageList3= productAgeUtil.getHealthPremierAdultAgeList(getQuoteRequest.getAdultDOB1().trim(), instalmentPremium3Year);
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2) && StringUtils.isNotBlank(ageList3)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList1).concat("','").concat(ageList2).concat("','").concat(ageList3).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getAdults().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())){
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getAdultDOB1().trim());
				dateList.add(getQuoteRequest.getAdultDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  age1List1Year= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(0), instalmentPremium1Year);
				String  age2List1Year= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(1), instalmentPremium1Year);
				String  age1List2Year= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(0), instalmentPremium2Year);
				String  age2List2Year= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(1), instalmentPremium2Year);
				String  age1List3Year= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(0), instalmentPremium3Year);
				String  age2List3Year= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(1), instalmentPremium3Year);
				if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age2List1Year) && StringUtils.isNotBlank(age1List2Year)
						&& StringUtils.isNotBlank(age2List2Year) && StringUtils.isNotBlank(age1List3Year) && StringUtils.isNotBlank(age2List3Year)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND_11).concat(age1List1Year).concat("')");
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(age2List1Year).concat("'))");
					
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND_111).concat(age1List2Year).concat("')");
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(age2List2Year).concat("'))");
					
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND_111).concat(age1List3Year).concat("')");
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(age2List3Year).concat("')))");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
				
			}else {
				validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
			}
		}else if(StringUtils.isNotBlank(getQuoteRequest.getOthers()) && !getQuoteRequest.getOthers().equalsIgnoreCase("0")){
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getOthers());

			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {

				String adultDOB = getQuoteRequest.getOtherDOB1().trim();
				
				String  age1List1Year= productAgeUtil.getHealthPremierAdultAgeList(adultDOB, instalmentPremium1Year);
				String  age1List2Year= productAgeUtil.getHealthPremierAdultAgeList(adultDOB, instalmentPremium2Year);
				String  age1List3Year= productAgeUtil.getHealthPremierAdultAgeList(adultDOB, instalmentPremium3Year);
				if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age1List3Year)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(age1List1Year).concat("','").concat(age1List2Year).concat("','").concat(age1List3Year).concat("')");

				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.OTHERS_DOB_EMPTY);
			}
		}else if(getQuoteRequest.getParents().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
				&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())){
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getParents());
			ArrayList<String> dateList = new ArrayList<>();
			dateList.add(getQuoteRequest.getParentDOB1().trim());
			dateList.add(getQuoteRequest.getParentDOB2().trim());
			dateList = productUtil.dateSortingDescending(dateList);
			
			String  age1List1Year= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(0), instalmentPremium1Year);
			String  age2List1Year= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(1), instalmentPremium1Year);
			String  age1List2Year= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(0), instalmentPremium2Year);
			String  age2List2Year= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(1), instalmentPremium2Year);
			String  age1List3Year= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(0), instalmentPremium3Year);
			String  age2List3Year= productAgeUtil.getHealthPremierAdultAgeList(dateList.get(1), instalmentPremium3Year);
			if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age2List1Year) && StringUtils.isNotBlank(age1List2Year)
					&& StringUtils.isNotBlank(age2List2Year) && StringUtils.isNotBlank(age1List3Year) && StringUtils.isNotBlank(age2List3Year)) {
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND_11).concat(age1List1Year).concat("')");
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(age2List1Year).concat("'))");
				
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND_111).concat(age1List2Year).concat("')");
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(age2List2Year).concat("'))");
				
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND_111).concat(age1List3Year).concat("')");
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(age2List3Year).concat("')))");
			}else {
				validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
			}
			
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
			
			if(getQuoteRequest.getChild().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getChildDOB1())) {
				String  age1List1Year= productAgeUtil.getHealthPremierChildAgeList(getQuoteRequest.getChildDOB1().trim(), instalmentPremium1Year);
				String  age1List2Year= productAgeUtil.getHealthPremierChildAgeList(getQuoteRequest.getChildDOB1().trim(), instalmentPremium2Year);
				String  age1List3Year= productAgeUtil.getHealthPremierChildAgeList(getQuoteRequest.getChildDOB1().trim(), instalmentPremium3Year);
				if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age1List3Year)) {
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_11).concat(age1List1Year).concat("','").concat(age1List2Year).concat("','").concat(age1List3Year).concat("')");			
				}else {
					validation.add(ApplicationConstant.CHILD_AGE_MISSING);
				}
			}else if(getQuoteRequest.getChild().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getChildDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getChildDOB2())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getChildDOB1().trim());
				dateList.add(getQuoteRequest.getChildDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  age1List1Year= productAgeUtil.getHealthPremierChildAgeList(dateList.get(0), instalmentPremium1Year);
				String  age2List1Year= productAgeUtil.getHealthPremierChildAgeList(dateList.get(1), instalmentPremium1Year);
				
				String  age1List2Year= productAgeUtil.getHealthPremierChildAgeList(dateList.get(0), instalmentPremium2Year);
				String  age2List2Year= productAgeUtil.getHealthPremierChildAgeList(dateList.get(1), instalmentPremium2Year);
				
				String  age1List3Year= productAgeUtil.getHealthPremierChildAgeList(dateList.get(0), instalmentPremium3Year);
				String  age2List3Year= productAgeUtil.getHealthPremierChildAgeList(dateList.get(1), instalmentPremium3Year);
				if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age2List1Year) && StringUtils.isNotBlank(age1List2Year)
						&& StringUtils.isNotBlank(age2List2Year) && StringUtils.isNotBlank(age1List3Year) && StringUtils.isNotBlank(age2List3Year)) {
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_111).concat(age1List1Year).concat("')");
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_2).concat(age2List1Year).concat("'))");
					
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_1).concat(age1List2Year).concat("')");
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_2).concat(age2List2Year).concat("'))");
					
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_1).concat(age1List3Year).concat("')");
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_2).concat(age2List3Year).concat("')))");
				}else {
					validation.add(ApplicationConstant.CHILD_AGE_MISSING);
				}
				
			}else if(getQuoteRequest.getChild().equals("3") && StringUtils.isNotBlank(getQuoteRequest.getChildDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getChildDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getChildDOB3())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getChildDOB1().trim());
				dateList.add(getQuoteRequest.getChildDOB2().trim());
				dateList.add(getQuoteRequest.getChildDOB3().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  age1List1Year= productAgeUtil.getHealthPremierChildAgeList(dateList.get(0), instalmentPremium1Year);
				String  age2List1Year= productAgeUtil.getHealthPremierChildAgeList(dateList.get(1), instalmentPremium1Year);
				String  age3List1Year= productAgeUtil.getHealthPremierChildAgeList(dateList.get(2), instalmentPremium1Year);
				
				String  age1List2Year= productAgeUtil.getHealthPremierChildAgeList(dateList.get(0), instalmentPremium2Year);
				String  age2List2Year= productAgeUtil.getHealthPremierChildAgeList(dateList.get(1), instalmentPremium2Year);
				String  age3List2Year= productAgeUtil.getHealthPremierChildAgeList(dateList.get(2), instalmentPremium2Year);
				
				String  age1List3Year= productAgeUtil.getHealthPremierChildAgeList(dateList.get(0), instalmentPremium3Year);
				String  age2List3Year= productAgeUtil.getHealthPremierChildAgeList(dateList.get(1), instalmentPremium3Year);
				String  age3List3Year= productAgeUtil.getHealthPremierChildAgeList(dateList.get(2), instalmentPremium3Year);
				
				if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age2List1Year) && StringUtils.isNotBlank(age3List1Year) 
						&& StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age2List2Year) && StringUtils.isNotBlank(age3List2Year)
						&& StringUtils.isNotBlank(age1List3Year) && StringUtils.isNotBlank(age2List3Year) && StringUtils.isNotBlank(age3List3Year)) {
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_111).concat(age1List1Year).concat("')");
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_2).concat(age2List1Year).concat("')");
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_3).concat(age3List1Year).concat("'))");
					
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_1).concat(age1List2Year).concat("')");
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_2).concat(age2List2Year).concat("')");
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_3).concat(age3List2Year).concat("'))");
					
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_1).concat(age1List3Year).concat("')");
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_2).concat(age2List3Year).concat("')");
					query = query.concat(ApplicationConstant.CHILD_AGE_BAND_3).concat(age3List3Year).concat("')))");
				}else {
					validation.add(ApplicationConstant.CHILD_AGE_MISSING);
				}
				
			}else {
				validation.add(ApplicationConstant.CHILD_MISSING);
			}
			 
		}else {
			query = query.concat(ApplicationConstant.CHILD_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPinCode())) {
			String zone = "";
			String pincode = String.valueOf(getQuoteRequest.getPinCode());
			zone = productPincodeRepo.getPincodeByZone(getQuoteRequest.getProductId().trim(), pincode.trim());
			if(StringUtils.isNotBlank(zone)) {
				query = query.concat(ApplicationConstant.ZONE).concat(zone).concat("'");
			}else {
				validation.add(ApplicationConstant.ZONE_EMPTY);
			}
			
		}else {
			validation.add(ApplicationConstant.PINCODE_EMPTY);
		}
		
		query = query.concat(ApplicationConstant.PERIOD_Q_1).concat(instalmentPremium1Year).concat("','")
				.concat(instalmentPremium2Year).concat("','")
				.concat(instalmentPremium3Year).concat("')");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		log.debug("Build All Health Premier Get Quote Query End {} ",query);
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}
	
	public List<String> buildHealthAssureQuery(GetQuoteRequest getQuoteRequest, String mappingProductCode) throws Exception {
		log.debug("Build Health Assure Get Quote Query Starts");
		log.debug("Serch Criteria HealthAssure {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty("Health_Assure_Table");
		String query = ApplicationConstant.SELECT;
		
		query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(mappingProductCode).concat("'");
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())){
			query = query.concat(ApplicationConstant.INSURANCE_COVER_GREATER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		boolean isOnlyTwoParent = false;
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
		
			if(getQuoteRequest.getAdults().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())) {
				if(StringUtils.isBlank(getQuoteRequest.getChild()) && StringUtils.isBlank(getQuoteRequest.getParents())) {
					String  ageList= productAgeUtil.getHealthAssureAdultsAgeList(getQuoteRequest.getAdultDOB1().trim(), getQuoteRequest.getPeriod().trim(), "1A");
					if(StringUtils.isNotBlank(ageList)) {
						query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
					}else {
						validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
					}
				}else {
					if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())) {
						String  ageList= productAgeUtil.getHealthAssureAdultsAgeList(getQuoteRequest.getAdultDOB1().trim(), getQuoteRequest.getPeriod().trim(), "ALL");
						if(StringUtils.isNotBlank(ageList)) {
							query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
						}else {
							validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
						}
					}
				}
			}else if(getQuoteRequest.getAdults().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())){
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getAdultDOB1().trim());
				dateList.add(getQuoteRequest.getAdultDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(0), getQuoteRequest.getPeriod().trim(), "ALL");
				String  ageList2= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(1), getQuoteRequest.getPeriod().trim(), "ALL");
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(ageList2).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add("Adults DOB Empty");
			}
			
		}else if(StringUtils.isNotBlank(getQuoteRequest.getOthers()) && !getQuoteRequest.getOthers().equalsIgnoreCase("0")){
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getOthers());

			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {

				String adultDOB = getQuoteRequest.getOtherDOB1().trim();
				String  ageList= productAgeUtil.getHealthAssureAdultsAgeList(adultDOB, getQuoteRequest.getPeriod().trim(), "1A");
				if(StringUtils.isNotBlank(ageList)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
				}else {
					validation.add(ApplicationConstant.OTHER_AGE_EMPTY);
				}
			}else {
				validation.add(ApplicationConstant.OTHERS_DOB_EMPTY);
			}
		}else if(getQuoteRequest.getParents().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
				&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())){
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getParents());
			isOnlyTwoParent = true;
			ArrayList<String> dateList = new ArrayList<>();
			dateList.add(getQuoteRequest.getParentDOB1().trim());
			dateList.add(getQuoteRequest.getParentDOB2().trim());
			dateList = productUtil.dateSortingDescending(dateList);
			
			String  ageList1= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(0), getQuoteRequest.getPeriod().trim(), "ALL");
			String  ageList2= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(1), getQuoteRequest.getPeriod().trim(), "ALL");
			if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2)) {
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList1).concat("')");
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(ageList2).concat("')");
			}else {
				validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
		}else {
			query = query.concat(ApplicationConstant.CHILD_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getParents()) && !getQuoteRequest.getParents().equalsIgnoreCase("0") && !isOnlyTwoParent) {
			query = query.concat(ApplicationConstant.PARENTS_Q+getQuoteRequest.getParents());
			
			if(getQuoteRequest.getParents().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())) {
				String  ageList1= productAgeUtil.getHealthAssureParentsAgeList(getQuoteRequest.getParentDOB1().trim(), getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList1)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList1).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1()) 
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(0), getQuoteRequest.getPeriod().trim());
				String  ageList2= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(1), getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(ageList2).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("3") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(0), getQuoteRequest.getPeriod().trim());
				String  ageList2= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(1), getQuoteRequest.getPeriod().trim());
				String  ageList3= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(2), getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2) && StringUtils.isNotBlank(ageList3)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(ageList2).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(ageList3).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("4") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB4())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList.add(getQuoteRequest.getParentDOB4().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  ageList1= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(0), getQuoteRequest.getPeriod().trim());
				String  ageList2= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(1), getQuoteRequest.getPeriod().trim());
				String  ageList3= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(2), getQuoteRequest.getPeriod().trim());
				String  ageList4= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(3), getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList1) && StringUtils.isNotBlank(ageList2) && StringUtils.isNotBlank(ageList3) && StringUtils.isNotBlank(ageList4)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(ageList1).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(ageList2).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(ageList3).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_4).concat(ageList4).concat("')");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.PARENTS_INFO_MISSING);
			}
		}else {
			query = query.concat(ApplicationConstant.PARENT_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPinCode())) {
			String zone = "";
			String pincode = String.valueOf(getQuoteRequest.getPinCode());
			zone = productPincodeRepo.getPincodeByZone(mappingProductCode.trim(), pincode.trim());
			if(StringUtils.isNotBlank(zone)) {
				query = query.concat(ApplicationConstant.ZONE).concat(zone).concat("'");
			}else {
				validation.add(ApplicationConstant.ZONE_EMPTY);
			}
		}else {
			validation.add(ApplicationConstant.PINCODE_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPeriod())) {
			query = query.concat(ApplicationConstant.PERIOD_Q).concat(getQuoteRequest.getPeriod().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.PERIOD_Q).concat(instalmentPremium1Year).concat("'");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		query = query.concat(ApplicationConstant.ORDER_BY);
		
		log.debug("Build Health Assure Get Quote Query End {}",query);
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}

	
	
	public List<String> buildAllHealthAssureQuery(GetQuoteRequest getQuoteRequest) throws Exception {
		log.debug("Build All Health Assure Get Quote Query Starts");
		log.debug("Serch Criteria AllHealthAssure {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty("Health_Assure_Table");
		String query = ApplicationConstant.SELECT;
		
		query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(getQuoteRequest.getProductId()).concat("'");
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query = query.concat(ApplicationConstant.INSURANCE_COVER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		boolean isOnlyTwoParent = false;
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
			
			if(getQuoteRequest.getAdults().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())) {
				if(StringUtils.isBlank(getQuoteRequest.getChild()) && StringUtils.isBlank(getQuoteRequest.getParents())) {
					String  age1List1Year = productAgeUtil.getHealthAssureAdultsAgeList(getQuoteRequest.getAdultDOB1().trim(), instalmentPremium1Year, "1A");
					String  age1List2Year = productAgeUtil.getHealthAssureAdultsAgeList(getQuoteRequest.getAdultDOB1().trim(), instalmentPremium2Year, "1A");
					String  age1List3Year = productAgeUtil.getHealthAssureAdultsAgeList(getQuoteRequest.getAdultDOB1().trim(), instalmentPremium3Year, "1A");
					if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age1List3Year)) {
						query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(age1List1Year).concat("','").concat(age1List2Year).concat("','").concat(age1List3Year).concat("')");
					}else {
						validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
					}
				}else {
					String  age1List1Year = productAgeUtil.getHealthAssureAdultsAgeList(getQuoteRequest.getAdultDOB1().trim(), instalmentPremium1Year, "ALL");
					String  age1List2Year = productAgeUtil.getHealthAssureAdultsAgeList(getQuoteRequest.getAdultDOB1().trim(), instalmentPremium2Year, "ALL");
					String  age1List3Year = productAgeUtil.getHealthAssureAdultsAgeList(getQuoteRequest.getAdultDOB1().trim(), instalmentPremium3Year, "ALL");
					if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age1List3Year)) {
						query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(age1List1Year).concat("','").concat(age1List2Year).concat("','").concat(age1List3Year).concat("')");
					}else {
						validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
					}
				}

			}else if(getQuoteRequest.getAdults().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1()) 
					&& StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())){
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getAdultDOB1().trim());
				dateList.add(getQuoteRequest.getAdultDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  age1List1Year= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(0), instalmentPremium1Year, "ALL");
				String  age2List1Year= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(1), instalmentPremium1Year, "ALL");
				String  age1List2Year= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(0), instalmentPremium2Year, "ALL");
				String  age2List2Year= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(1), instalmentPremium2Year, "ALL");
				String  age1List3Year= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(0), instalmentPremium3Year, "ALL");
				String  age2List3Year= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(1), instalmentPremium3Year, "ALL");
				if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age2List1Year) 
						&& StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age2List2Year)
						&& StringUtils.isNotBlank(age1List3Year) && StringUtils.isNotBlank(age2List3Year)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND_11).concat(age1List1Year).concat("')");
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(age2List1Year).concat("'))");
					
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND_111).concat(age1List2Year).concat("')");
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(age2List2Year).concat("'))");
					
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND_111).concat(age1List3Year).concat("')");
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(age2List3Year).concat("')))");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
			}
		}else if(StringUtils.isNotBlank(getQuoteRequest.getOthers()) && !getQuoteRequest.getOthers().equalsIgnoreCase("0")){
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getOthers());

			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {

				String  age1List1Year = productAgeUtil.getHealthAssureAdultsAgeList(getQuoteRequest.getOtherDOB1().trim(), instalmentPremium1Year, "1A");
				String  age1List2Year = productAgeUtil.getHealthAssureAdultsAgeList(getQuoteRequest.getOtherDOB1().trim(), instalmentPremium2Year, "1A");
				String  age1List3Year = productAgeUtil.getHealthAssureAdultsAgeList(getQuoteRequest.getOtherDOB1().trim(), instalmentPremium3Year, "1A");
				if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age1List3Year)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(age1List1Year).concat("','").concat(age1List2Year).concat("','").concat(age1List3Year).concat("')");
				}else {
					validation.add(ApplicationConstant.OTHER_AGE_EMPTY);
				}
			}else {
				validation.add(ApplicationConstant.OTHERS_DOB_EMPTY);
			}
		}else if(getQuoteRequest.getParents().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1()) 
				&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())){
			isOnlyTwoParent = true;
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getParents());
			ArrayList<String> dateList = new ArrayList<>();
			dateList.add(getQuoteRequest.getParentDOB1().trim());
			dateList.add(getQuoteRequest.getParentDOB2().trim());
			dateList = productUtil.dateSortingDescending(dateList);
			
			String  age1List1Year= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(0), instalmentPremium1Year, "ALL");
			String  age2List1Year= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(1), instalmentPremium1Year, "ALL");
			String  age1List2Year= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(0), instalmentPremium2Year, "ALL");
			String  age2List2Year= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(1), instalmentPremium2Year, "ALL");
			String  age1List3Year= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(0), instalmentPremium3Year, "ALL");
			String  age2List3Year= productAgeUtil.getHealthAssureAdultsAgeList(dateList.get(1), instalmentPremium3Year, "ALL");
			if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age2List1Year) 
					&& StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age2List2Year)
					&& StringUtils.isNotBlank(age1List3Year) && StringUtils.isNotBlank(age2List3Year)) {
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND_11).concat(age1List1Year).concat("')");
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(age2List1Year).concat("'))");
				
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND_111).concat(age1List2Year).concat("')");
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(age2List2Year).concat("'))");
				
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND_111).concat(age1List3Year).concat("')");
				query = query.concat(ApplicationConstant.ADULT_AGE_BAND_2).concat(age2List3Year).concat("')))");
			}else {
				validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
		}else {
			query = query.concat(ApplicationConstant.CHILD_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getParents()) && !getQuoteRequest.getParents().equalsIgnoreCase("0") && !isOnlyTwoParent) {
			query = query.concat(ApplicationConstant.PARENTS_Q+getQuoteRequest.getParents());

			if(getQuoteRequest.getParents().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())) {
				String  age1List1Year= productAgeUtil.getHealthAssureParentsAgeList(getQuoteRequest.getParentDOB1().trim(), instalmentPremium1Year);
				String  age1List2Year= productAgeUtil.getHealthAssureParentsAgeList(getQuoteRequest.getParentDOB1().trim(), instalmentPremium2Year);
				String  age1List3Year= productAgeUtil.getHealthAssureParentsAgeList(getQuoteRequest.getParentDOB1().trim(), instalmentPremium3Year);
				if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age1List3Year)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_1).concat(age1List1Year).concat("','").concat(age1List2Year).concat("','").concat(age1List3Year).concat("')");			
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1()) 
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  age1List1Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(0), instalmentPremium1Year);
				String  age2List1Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(1), instalmentPremium1Year);
				String  age1List2Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(0), instalmentPremium2Year);
				String  age2List2Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(1), instalmentPremium2Year);
				String  age1List3Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(0), instalmentPremium3Year);
				String  age2List3Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(1), instalmentPremium3Year);
				if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age2List1Year)
						&& StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age2List2Year)
						&& StringUtils.isNotBlank(age1List3Year) && StringUtils.isNotBlank(age2List3Year)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_11).concat(age1List1Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(age2List1Year).concat("'))");
					
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_111).concat(age1List2Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(age2List2Year).concat("'))");
					
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_111).concat(age1List3Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(age2List3Year).concat("')))");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("3") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  age1List1Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(0), instalmentPremium1Year);
				String  age2List1Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(1), instalmentPremium1Year);
				String  age3List1Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(2), instalmentPremium1Year);
				String  age1List2Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(0), instalmentPremium2Year);
				String  age2List2Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(1), instalmentPremium2Year);
				String  age3List2Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(2), instalmentPremium2Year);
				String  age1List3Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(0), instalmentPremium3Year);
				String  age2List3Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(1), instalmentPremium3Year);
				String  age3List3Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(2), instalmentPremium3Year);
				if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age2List1Year) && StringUtils.isNotBlank(age3List1Year)
						&& StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age2List2Year) && StringUtils.isNotBlank(age3List2Year)
						&& StringUtils.isNotBlank(age1List3Year) && StringUtils.isNotBlank(age2List3Year) && StringUtils.isNotBlank(age3List3Year)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_11).concat(age1List1Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(age2List1Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(age3List1Year).concat("'))");
					
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_111).concat(age1List2Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(age2List2Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(age3List2Year).concat("'))");
					
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_111).concat(age1List3Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(age2List3Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(age3List3Year).concat("')))");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else if(getQuoteRequest.getParents().equals("4") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB4())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList.add(getQuoteRequest.getParentDOB4().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String  age1List1Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(0), instalmentPremium1Year);
				String  age2List1Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(1), instalmentPremium1Year);
				String  age3List1Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(2), instalmentPremium1Year);
				String  age4List1Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(3), instalmentPremium1Year);
				String  age1List2Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(0), instalmentPremium2Year);
				String  age2List2Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(1), instalmentPremium2Year);
				String  age3List2Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(2), instalmentPremium2Year);
				String  age4List2Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(3), instalmentPremium2Year);
				String  age1List3Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(0), instalmentPremium3Year);
				String  age2List3Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(1), instalmentPremium3Year);
				String  age3List3Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(2), instalmentPremium3Year);
				String  age4List3Year= productAgeUtil.getHealthAssureParentsAgeList(dateList.get(3), instalmentPremium3Year);
				if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age2List1Year) && StringUtils.isNotBlank(age3List1Year) && StringUtils.isNotBlank(age4List1Year)
						&& StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age2List2Year) && StringUtils.isNotBlank(age3List2Year) && StringUtils.isNotBlank(age4List2Year)
						&& StringUtils.isNotBlank(age1List3Year) && StringUtils.isNotBlank(age2List3Year) && StringUtils.isNotBlank(age3List3Year) && StringUtils.isNotBlank(age4List3Year)) {
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_11).concat(age1List1Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(age2List1Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(age3List1Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_4).concat(age4List1Year).concat("'))");
					
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_111).concat(age1List2Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(age2List2Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(age3List2Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_4).concat(age4List2Year).concat("'))");
					
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_111).concat(age1List3Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_2).concat(age2List3Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_3).concat(age3List3Year).concat("')");
					query = query.concat(ApplicationConstant.PARENT_AGE_BAND_4).concat(age4List3Year).concat("')))");
				}else {
					validation.add(ApplicationConstant.PARENTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.PARENTS_INFO_MISSING);
			}
		}else {
			query = query.concat(ApplicationConstant.PARENT_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPinCode())) {
			String zone = "";
			String pincode = String.valueOf(getQuoteRequest.getPinCode());
			zone = productPincodeRepo.getPincodeByZone(getQuoteRequest.getProductId().trim(), pincode.trim());
			if(StringUtils.isNotBlank(zone)) {
				query = query.concat(ApplicationConstant.ZONE).concat(zone).concat("'");
			}else {
				validation.add(ApplicationConstant.ZONE_EMPTY);
			}
			
		}else {
			validation.add(ApplicationConstant.PINCODE_EMPTY);
		}
		
		query = query.concat(ApplicationConstant.PERIOD_Q_1).concat(instalmentPremium1Year).concat("','")
				.concat(instalmentPremium2Year).concat("','")
				.concat(instalmentPremium3Year).concat("')");
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		log.debug("Build All Health Assure Get Quote Query End {}",query);
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}
	
	public GetQuoteRequestVO buildHealthAssureSOAPRequest(GetQuoteRequest getQuoteRequest, String mappingProductCode) throws Exception {
		log.debug("buildHealthAssureSOAPRequest Starts");
		GetQuoteRequestVO getQuoteRequestVO = new GetQuoteRequestVO();
		log.debug("Serch Criteria HealthAssureSOAP {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		getQuoteRequestVO.setProductCode(mappingProductCode);
		getQuoteRequestVO.setProductName(healthAssureProductName);
	
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			ProductsSumInsureVO productsSumInsureVO = productsSumInsureRepo.getAvailableSumInsure(mappingProductCode, getQuoteRequest.getInsuranceCover().trim(),
					getQuoteRequest.getPeriod());
			getQuoteRequest.setInsuranceCover(String.valueOf(productsSumInsureVO.getSumInsure()));
			getQuoteRequestVO.setInsuranceCover(getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		
		boolean isOnlyTwoParent = false;
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			getQuoteRequestVO.setAdults(getQuoteRequest.getAdults());
			if(getQuoteRequest.getAdults().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())) {
				String age = String.valueOf(productUtil.getAgeInYear(getQuoteRequest.getAdultDOB1().trim()));
				String DOB = productUtil.getDOBFromAge(age);
				getQuoteRequestVO.setAdultDOB1(DOB);
			}else if(getQuoteRequest.getAdults().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())){
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getAdultDOB1().trim());
				dateList.add(getQuoteRequest.getAdultDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String age1 = String.valueOf(productUtil.getAgeInYear(dateList.get(0)));
				String age2 = String.valueOf(productUtil.getAgeInYear(dateList.get(1)));
				String dob1 = productUtil.getDOBFromAge(age1);
				String dob2 = productUtil.getDOBFromAge(age2);
				getQuoteRequestVO.setAdultDOB1(dob1);
				getQuoteRequestVO.setAdultDOB2(dob2);
			}else {
				validation.add("Adults DOB Empty");
			}
			
		}else if(StringUtils.isNotBlank(getQuoteRequest.getOthers()) && !getQuoteRequest.getOthers().equalsIgnoreCase("0")){
			getQuoteRequestVO.setAdults(getQuoteRequest.getOthers());
			
			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {
				String age = String.valueOf(productUtil.getAgeInYear(getQuoteRequest.getOtherDOB1().trim()));
				String DOB = productUtil.getDOBFromAge(age);
				getQuoteRequestVO.setAdultDOB1(DOB);
			}else {
				validation.add(ApplicationConstant.OTHERS_DOB_EMPTY);
			}
		}else if(getQuoteRequest.getParents().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
				&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())){
			isOnlyTwoParent = true;
			getQuoteRequestVO.setAdults(getQuoteRequest.getParents());
			ArrayList<String> dateList = new ArrayList<>();
			dateList.add(getQuoteRequest.getParentDOB1().trim());
			dateList.add(getQuoteRequest.getParentDOB2().trim());
			dateList = productUtil.dateSortingDescending(dateList);
			
			String age1 = String.valueOf(productUtil.getAgeInYear(dateList.get(0)));
			String age2 = String.valueOf(productUtil.getAgeInYear(dateList.get(1)));
			String dob1 = productUtil.getDOBFromAge(age1);
			String dob2 = productUtil.getDOBFromAge(age2);
			getQuoteRequestVO.setAdultDOB1(dob1);
			getQuoteRequestVO.setAdultDOB2(dob2);
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			getQuoteRequestVO.setChild(getQuoteRequest.getChild());
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getParents()) && !getQuoteRequest.getParents().equalsIgnoreCase("0") && !isOnlyTwoParent) {
			getQuoteRequestVO.setParents(getQuoteRequest.getParents());
			
			if(getQuoteRequest.getParents().equals("1") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())) {
				String age = String.valueOf(productUtil.getAgeInYear(getQuoteRequest.getParentDOB1().trim()));
				String DOB = productUtil.getDOBFromAge(age);
				getQuoteRequestVO.setParentDOB1(DOB);
			}else if(getQuoteRequest.getParents().equals("2") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1()) 
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String age1 = String.valueOf(productUtil.getAgeInYear(dateList.get(0)));
				String age2 = String.valueOf(productUtil.getAgeInYear(dateList.get(1)));
				String dob1 = productUtil.getDOBFromAge(age1);
				String dob2 = productUtil.getDOBFromAge(age2);
				getQuoteRequestVO.setParentDOB1(dob1);
				getQuoteRequestVO.setParentDOB2(dob2);
			}else if(getQuoteRequest.getParents().equals("3") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String age1 = String.valueOf(productUtil.getAgeInYear(dateList.get(0)));
				String age2 = String.valueOf(productUtil.getAgeInYear(dateList.get(1)));
				String age3 = String.valueOf(productUtil.getAgeInYear(dateList.get(2)));
				String dob1 = productUtil.getDOBFromAge(age1);
				String dob2 = productUtil.getDOBFromAge(age2);
				String dob3 = productUtil.getDOBFromAge(age3);
				getQuoteRequestVO.setParentDOB1(dob1);
				getQuoteRequestVO.setParentDOB2(dob2);
				getQuoteRequestVO.setParentDOB3(dob3);
			}else if(getQuoteRequest.getParents().equals("4") && StringUtils.isNotBlank(getQuoteRequest.getParentDOB1())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB2())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB3())
					&& StringUtils.isNotBlank(getQuoteRequest.getParentDOB4())) {
				ArrayList<String> dateList = new ArrayList<>();
				dateList.add(getQuoteRequest.getParentDOB1().trim());
				dateList.add(getQuoteRequest.getParentDOB2().trim());
				dateList.add(getQuoteRequest.getParentDOB3().trim());
				dateList.add(getQuoteRequest.getParentDOB4().trim());
				dateList = productUtil.dateSortingDescending(dateList);
				
				String age1 = String.valueOf(productUtil.getAgeInYear(dateList.get(0)));
				String age2 = String.valueOf(productUtil.getAgeInYear(dateList.get(1)));
				String age3 = String.valueOf(productUtil.getAgeInYear(dateList.get(2)));
				String age4 = String.valueOf(productUtil.getAgeInYear(dateList.get(3)));
				String dob1 = productUtil.getDOBFromAge(age1);
				String dob2 = productUtil.getDOBFromAge(age2);
				String dob3 = productUtil.getDOBFromAge(age3);
				String dob4 = productUtil.getDOBFromAge(age4);
				getQuoteRequestVO.setParentDOB1(dob1);
				getQuoteRequestVO.setParentDOB2(dob2);
				getQuoteRequestVO.setParentDOB3(dob3);
				getQuoteRequestVO.setParentDOB3(dob4);
			}else {
				validation.add(ApplicationConstant.PARENTS_INFO_MISSING);
			}
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPinCode())) {
			String pincode = String.valueOf(getQuoteRequest.getPinCode());
			getQuoteRequestVO.setPinCode(pincode);
		}else {
			validation.add(ApplicationConstant.PINCODE_EMPTY);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPeriod())) {
			getQuoteRequestVO.setPeriod(getQuoteRequest.getPeriod().trim());
		}else {
			getQuoteRequestVO.setPeriod(instalmentPremium1Year);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			getQuoteRequestVO.setOnlineDiscountPerc(getQuoteRequest.getOnlineDiscountPerc().trim());
		}
		
		String schemeCode = "";
		if(StringUtils.isNotBlank(getQuoteRequestVO.getAdults())) {
			schemeCode = schemeCode.concat(getQuoteRequestVO.getAdults()).concat("A");
		}
		if(StringUtils.isNotBlank(getQuoteRequestVO.getChild())) {
			schemeCode = schemeCode.concat("+").concat(getQuoteRequestVO.getChild()).concat("C");
		}
		getQuoteRequestVO.setSchemeCode(schemeCode);
		
		log.debug("buildHealthAssureSOAPRequest End {} ",getQuoteRequestVO);

		return getQuoteRequestVO;
	}
	
	public List<String> buildWomenCareQuery(GetQuoteRequest getQuoteRequest, String mappingProductCode) throws Exception {
		log.debug("Build Women Care Get Quote Query Starts");
		log.debug("Serch Criteria WomenCare {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		String tableName = environment.getProperty("Star_Women_Care_Table");
		String query = ApplicationConstant.SELECT;
		
		query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(mappingProductCode).concat("'");
		if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
			query = query.concat(ApplicationConstant.INSURANCE_COVER_GREATER+getQuoteRequest.getInsuranceCover());
		}else {
			validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
		}
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
			
			if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
				long adultDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB1());
				long adultDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB2());
				String adultDOB = "";
				if(adultDOB1Days>adultDOB2Days) {
					adultDOB = getQuoteRequest.getAdultDOB1().trim();
				}else {
					adultDOB = getQuoteRequest.getAdultDOB2().trim();
				}
				String  ageList= productAgeUtil.getWomenCareAgeList(adultDOB,  getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
				}else {
					validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
			}
			
		}else if(StringUtils.isNotBlank(getQuoteRequest.getOthers()) && !getQuoteRequest.getOthers().equalsIgnoreCase("0")){
			query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getOthers());

			if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {

				String adultDOB = getQuoteRequest.getOtherDOB1().trim();
				
				String  ageList= productAgeUtil.getWomenCareAgeList(adultDOB,  getQuoteRequest.getPeriod().trim());
				if(StringUtils.isNotBlank(ageList)) {
					query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(ageList).concat("')");
				}else {
					validation.add("Others/Adults Age Band In Year is Empty");
				}
			}else {
				validation.add(ApplicationConstant.OTHERS_DOB_EMPTY);
			}
		}else {
			validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
			query = query.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
		}else {
			query = query.concat(ApplicationConstant.CHILD_NULL);
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getPeriod())) {
			query = query.concat(ApplicationConstant.PERIOD_Q).concat(getQuoteRequest.getPeriod().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.PERIOD_Q).concat(instalmentPremium1Year).concat("'");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
		}else {
			query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
		}
		
		query = query.concat(ApplicationConstant.ORDER_BY);
		
		log.debug("Build Women Care Get Quote Query End {} ",query);
		
		if(CollectionUtils.isEmpty(validation)){
			validation.add(query);
		}
		return validation;
	}
	
	
	public List<String> buildAllWomenCareQuery(GetQuoteRequest getQuoteRequest) {
		log.debug("Build All Women Care Get Quote Query Starts");
		log.debug("Serch Criteria AllWomenCare {} ",productUtil.doMarshallingJSON(getQuoteRequest));
		List<String> validation = new ArrayList<>();
		try {
			String tableName = environment.getProperty("Star_Women_Care_Table");
			String query = ApplicationConstant.SELECT;
			
			query = query.concat(tableName.trim()).concat(ApplicationConstant.WHERE).concat(ApplicationConstant.PRODUCT_CODE).concat(getQuoteRequest.getProductId()).concat("'");
			if(StringUtils.isNotBlank(getQuoteRequest.getInsuranceCover())) {
				query = query.concat(ApplicationConstant.INSURANCE_COVER+getQuoteRequest.getInsuranceCover());
			}else {
				validation.add(ApplicationConstant.INSURANCECOVER_EMPTY);
			}
			
			if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && !getQuoteRequest.getAdults().equalsIgnoreCase("0")) {
				query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getAdults());
				
				if(StringUtils.isNotBlank(getQuoteRequest.getAdultDOB1()) || StringUtils.isNotBlank(getQuoteRequest.getAdultDOB2())) {
					long adultDOB1Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB1());
					long adultDOB2Days = productUtil.getAgeInDays(getQuoteRequest.getAdultDOB2());
					String adultDOB = "";
					if(adultDOB1Days>adultDOB2Days) {
						adultDOB = getQuoteRequest.getAdultDOB1().trim();
					}else {
						adultDOB = getQuoteRequest.getAdultDOB2().trim();
					}
					String  age1List1Year = productAgeUtil.getWomenCareAgeList(adultDOB, instalmentPremium1Year);
					String  age1List2Year = productAgeUtil.getWomenCareAgeList(adultDOB, instalmentPremium2Year);
					String  age1List3Year = productAgeUtil.getWomenCareAgeList(adultDOB, instalmentPremium3Year);
					if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age1List3Year)) {
						query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(age1List1Year).concat("','").concat(age1List2Year).concat("','").concat(age1List3Year).concat("')");

					}else {
						validation.add(ApplicationConstant.ADULTS_AGE_MISSING);
					}
				}else {
					validation.add(ApplicationConstant.ADULTS_DOB_MISSING);
				}
				
			}else if(StringUtils.isNotBlank(getQuoteRequest.getOthers()) && !getQuoteRequest.getOthers().equalsIgnoreCase("0")){
				query = query.concat(ApplicationConstant.ADULTS_Q+getQuoteRequest.getOthers());

				if(StringUtils.isNotBlank(getQuoteRequest.getOtherDOB1())) {

					String adultDOB = getQuoteRequest.getOtherDOB1().trim();
					
					String  age1List1Year = productAgeUtil.getWomenCareAgeList(adultDOB, instalmentPremium1Year);
					String  age1List2Year = productAgeUtil.getWomenCareAgeList(adultDOB, instalmentPremium2Year);
					String  age1List3Year = productAgeUtil.getWomenCareAgeList(adultDOB, instalmentPremium3Year);
					if(StringUtils.isNotBlank(age1List1Year) && StringUtils.isNotBlank(age1List2Year) && StringUtils.isNotBlank(age1List3Year)) {
						query = query.concat(ApplicationConstant.ADULT_AGE_BAND).concat(age1List1Year).concat("','").concat(age1List2Year).concat("','").concat(age1List3Year).concat("')");

					}else {
						validation.add("Others/Adults Age Band In Year is Empty");
					}
				}else {
					validation.add(ApplicationConstant.OTHERS_DOB_EMPTY);
				}
			}else {
				validation.add(ApplicationConstant.ADULTS_INFO_MISSING);
			}
			
			if(StringUtils.isNotBlank(getQuoteRequest.getChild()) && !getQuoteRequest.getChild().equalsIgnoreCase("0")) {
				query = query.concat(ApplicationConstant.CHILD_Q+getQuoteRequest.getChild());
			}else {
				query = query.concat(ApplicationConstant.CHILD_NULL);
			}
			
			query = query.concat(ApplicationConstant.PERIOD_Q_1).concat(instalmentPremium1Year).concat("','")
					.concat(instalmentPremium2Year).concat("','")
					.concat(instalmentPremium3Year).concat("')");
			
			if(StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())) {
				query = query.concat(ApplicationConstant.ONLINE_DISCOUNT).concat(getQuoteRequest.getOnlineDiscountPerc().trim()).concat("'");
			}else {
				query = query.concat(ApplicationConstant.ONLINE_DISCOUNT_NULL);
			}
			
			log.debug("Build All Women Care Get Quote Query End {} ",query);
			if(CollectionUtils.isEmpty(validation)){
				validation.add(query);
			}
		} catch (Exception e) {
			log.error("Error : "+e.getMessage());
			e.printStackTrace();
		}
		return validation;
	}
	
	public List<String> buildGetQuoteQuery(GetQuoteRequest getQuoteRequest) throws Exception {
		
		List<String> validation = new ArrayList<>();
		if(getQuoteRequest.getProductId().equalsIgnoreCase(mediClassicProductCode)) {
			validation = buildAllMediClassicQuery(getQuoteRequest);

		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(familyHealthOptimaProductCode)) {
			validation = buildAllFHOQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(youngStarProductCode)) {
			validation = buildAllYoungStarQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(starComprehensiveProductCode)) {
			validation = buildAllStarComprehensiveQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(arogyaSanjeevaniProductCode)) {
			validation = buildAllArogyaSanjeevaniQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(superSurplusIndividualProductCode)) {
			validation = buildAllSuperSurplusIndividualQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(superSurplusFloaterProductCode)) {
			validation = buildAllSuperSurplusFloaterQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(healthPremierProductCode)) {
			validation = buildAllHealthPremierQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(healthAssureProductCode)) {
			validation = buildAllHealthAssureQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(starWomenCareProductCode)) {
			validation = buildAllWomenCareQuery(getQuoteRequest);
		
		}else {
			validation.add("Not Implemented for this Product");
		}
		
		return validation;
	}
	
	public List<String> buildGetAllQuoteQuery(GetQuoteRequest getQuoteRequest) throws Exception {
	
		List<String> validation = new ArrayList<>();
		if(getQuoteRequest.getProductId().equalsIgnoreCase(mediClassicProductCode)) {
			validation = buildAllMediClassicQuery(getQuoteRequest);

		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(familyHealthOptimaProductCode)) {
			validation = buildAllFHOQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(youngStarProductCode)) {
			validation = buildAllYoungStarQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(starComprehensiveProductCode)) {
			validation = buildAllStarComprehensiveQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(arogyaSanjeevaniProductCode)) {
			validation = buildAllArogyaSanjeevaniQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(superSurplusIndividualProductCode)) {
			validation = buildAllSuperSurplusIndividualQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(superSurplusFloaterProductCode)) {
			validation = buildAllSuperSurplusFloaterQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(healthPremierProductCode)) {
			validation = buildAllHealthPremierQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(healthAssureProductCode)) {
			validation = buildAllHealthAssureQuery(getQuoteRequest);
		
		}else if(getQuoteRequest.getProductId().equalsIgnoreCase(starWomenCareProductCode)) {
			validation = buildAllWomenCareQuery(getQuoteRequest);
		
		}else {
			validation.add("Not Implemented for this Product");
		}
		
		return validation;
	}


	public GetQuoteRequestVO buildSOAPRequest(GetQuoteRequest getQuoteRequest, String productCode) throws Exception {
		log.debug("BuildSOAPRequest Starts");
		GetQuoteRequestVO getQuoteRequestVO = new GetQuoteRequestVO();
		
		if(productCode.equalsIgnoreCase(familyHealthOptimaProductCode)) {
			getQuoteRequestVO =  buildFHOSOAPRequest(getQuoteRequest, productCode);
		}else if (productCode.equalsIgnoreCase(healthPremierProductCode)) {
			getQuoteRequestVO = buildHealthPremierSOAPRequest(getQuoteRequest, productCode);
		}else if(productCode.equalsIgnoreCase(healthAssureProductCode)) {
			getQuoteRequestVO = buildHealthAssureSOAPRequest(getQuoteRequest, productCode);
		}
		
		log.debug("BuildSOAPRequest End");
		return getQuoteRequestVO;
	}
	
	public List<GetQuoteRequestVO> buildAllSOAPRequest(GetQuoteRequest getQuoteRequest, String productCode) throws Exception {
		List<GetQuoteRequestVO> getQuoteRequestVOs = new ArrayList<>();

		getQuoteRequest.setPeriod(instalmentPremium1Year);
		GetQuoteRequestVO getQuoteRequestVO1 = buildSOAPRequest(getQuoteRequest, productCode);
		getQuoteRequestVOs.add(getQuoteRequestVO1);
		log.debug("getQuoteRequestVO1 {}",getQuoteRequestVO1);
		
		getQuoteRequest.setPeriod(instalmentPremium2Year);
		GetQuoteRequestVO getQuoteRequestVO2 = buildSOAPRequest(getQuoteRequest, productCode);
		getQuoteRequestVOs.add(getQuoteRequestVO2);
		log.debug("getQuoteRequestVO2 {}",getQuoteRequestVO2);
		
		if(!productCode.equalsIgnoreCase(familyHealthOptimaProductCode)) {
			getQuoteRequest.setPeriod(instalmentPremium3Year);
			GetQuoteRequestVO getQuoteRequestVO3 = buildSOAPRequest(getQuoteRequest, productCode);
			getQuoteRequestVOs.add(getQuoteRequestVO3);
			log.debug("getQuoteRequestVO3 {}",getQuoteRequestVO3);
		}
		
		return getQuoteRequestVOs;
	}
	
	public boolean checkOnDemand(String productCode, GetQuoteRequest getQuoteRequest) {
		boolean onDemand = false;
		log.debug("CheckOnDemand Starts");
		if(productCode.trim().equalsIgnoreCase(familyHealthOptimaProductCode)
				&& StringUtils.isNotBlank(getQuoteRequest.getParents()) && StringUtils.isNotBlank(getQuoteRequest.getAdults())
				&&(getQuoteRequest.getParents().trim().equals("2") || getQuoteRequest.getParents().trim().equals("3")
				|| getQuoteRequest.getParents().trim().equals("4")) ) {
			onDemand = true;
		}else if(productCode.trim().equalsIgnoreCase(healthAssureProductCode) && StringUtils.isBlank(getQuoteRequest.getOnlineDiscountPerc())
				&& ( ( (getQuoteRequest.getAdults().equals("1") ) && ( getQuoteRequest.getParents().trim().equals("2")
						||  getQuoteRequest.getParents().trim().equals("3") || getQuoteRequest.getParents().trim().equals("4") ) ) 
				|| ( getQuoteRequest.getAdults().equals("2") )
				|| (StringUtils.isBlank(getQuoteRequest.getAdults()) && getQuoteRequest.getParents().equals("2") ) ) ) {
			onDemand = true;
		}else if(productCode.trim().equalsIgnoreCase(healthAssureProductCode) && StringUtils.isNotBlank(getQuoteRequest.getOnlineDiscountPerc())
				&& ( ( (getQuoteRequest.getAdults().equals("1") ) && ( getQuoteRequest.getParents().trim().equals("1") || getQuoteRequest.getParents().trim().equals("2")
						||  getQuoteRequest.getParents().trim().equals("3") || getQuoteRequest.getParents().trim().equals("4") ) ) 
				|| ( getQuoteRequest.getAdults().equals("2") )
				|| (StringUtils.isBlank(getQuoteRequest.getAdults()) && getQuoteRequest.getParents().equals("2") ) ) ) {
			onDemand = true;
		}else if(productCode.trim().equalsIgnoreCase(healthPremierProductCode) 
				&& StringUtils.isNotBlank(getQuoteRequest.getChild()) && StringUtils.isNotBlank(getQuoteRequest.getAdults())
				&& ( ( getQuoteRequest.getChild().trim().equals("2") || getQuoteRequest.getChild().trim().equals("3") )
						&& (getQuoteRequest.getAdults().trim().equals("2")) ) ){
			onDemand = true;
		}
		
		log.debug("CheckOnDemand End {} ",onDemand);
		return onDemand;
		
	}
	
}
