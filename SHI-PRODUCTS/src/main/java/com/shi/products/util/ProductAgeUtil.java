/**
 * 
 */
package com.shi.products.util;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.shi.products.repo.AgeAndAgeBandRepo;
import com.shi.products.req.GetQuoteRequest;

/**
 * @author suman.raju
 *
 */
@Component
public class ProductAgeUtil {
	
private static final Logger log = LoggerFactory.getLogger(ProductAgeUtil.class);
	
	@Autowired
	Environment environment;
	
	@Autowired
	ProductUtil productUtil;
	
	@Autowired
	AgeAndAgeBandRepo ageAndAgeBandRepo;
	
	@Value("${InstalmentPremium_1Year}")
	private String instalmentPremium1Year;
	
	@Value("${InstalmentPremium_2Year}")
	private String instalmentPremium2Year;
	
	@Value("${InstalmentPremium_3Year}")
	private String instalmentPremium3Year;
	
	@Value("${MediClassic_ProductCode}")
	private String mediClassicProductCode;
	
	@Value("${Family_Health_Optima_ProductCode}")
	private String familyHealthOptimaProductCode;
	
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
	
	@Value("${Health_Assure_ProductCode}")
	private String healthAssureProductCode;
	
	@Value("${Star_Women_Care_ProductCode}")
	private String starWomenCareProductCode;
	
	@Value("${MediClassic_Plan_GOLD}")
	private String mediClassicPlanGOLD;
	
	@Value("${MediClassic_Plan_BASIC}")
	private String mediClassicPlanBASIC;
	
	@Value("${floater}")
	private String floater;
	
	@Value("${individual}")
	private String individual;
	
	@Value("${Adults}")
	private String adults;
	
	@Value("${Parents}")
	private String parents;
	
	@Value("${Child}")
	private String child;
	
	public String getMediClassicAgeList(String dob, String plan, String period) {
		String ageList = "";
		String ageBandInYerString = "";
		long age = productUtil.getAgeInYear(dob);
		long month = productUtil.getAgeInMonth(dob);
		long days = productUtil.getAgeInDays(dob);
		
		if(StringUtils.isNotBlank(plan)) {
			if(plan.equalsIgnoreCase(mediClassicPlanGOLD)) {	// GOLD
				if(period.equalsIgnoreCase(instalmentPremium1Year)) {
					if(days >=16 && age<=35 ) {
						ageList = ApplicationConstant.AGE16DAYSTO35YEARS;
					}else if(age <=80) {
						ageList = String.valueOf(age);
					}else if(age >80) {
						ageList = ApplicationConstant.AGE81ANDABOVE;
					}
					
				}else if(period.equalsIgnoreCase(instalmentPremium2Year)){
					if(days >=16 && age<=34) {
						ageList = "16Days-34";
					}else if(age <=80) {
						ageList = String.valueOf(age);
					}else if(age >80) {
						ageList = ApplicationConstant.AGE81ANDABOVE;
					}
					
				}else if(period.equalsIgnoreCase(instalmentPremium3Year)) {
					if(days >=16 && age<=33) {
						ageList = "16Days-33";
					}else if(age <=80) {
						ageList = String.valueOf(age);
					}else if(age >80) {
						ageList = ApplicationConstant.AGE81ANDABOVE;
					}
				}
				log.debug("Medi Classic GOLD Age List {} ",ageList);
				
				if(StringUtils.isNotBlank(ageList)) {
					ArrayList<String> ageBands = ageAndAgeBandRepo.getAgeBandsByPeriodAndPlan(mediClassicProductCode, ageList, period, mediClassicPlanGOLD);
					if(ageBands.size()==1) {
						ageBandInYerString = ageBands.get(0);
					}
					log.debug("Medi Classic GOLD Age Band In Years {} ",ageList);
				}
			}else {	// BASIC
				
				if(period.equalsIgnoreCase(instalmentPremium1Year)) {
					if(month >=5 && age<=35) {
						ageList = "5Months-35";
					}else if( age <=80) {
						ageList = String.valueOf(age);
					}else if(age >80) {
						ageList = ApplicationConstant.AGE81ANDABOVE;
					}
					
				}else if(period.equalsIgnoreCase(instalmentPremium2Year)){
					if(month >=5 && age<=34) {
						ageList = "5Months-34";
					}else if(age <=80) {
						ageList = String.valueOf(age);
					}else if(age >80) {
						ageList = ApplicationConstant.AGE81ANDABOVE;
					}
					
				}else if(period.equalsIgnoreCase(instalmentPremium3Year)) {
					if(month >=5 && age<=33) {
						ageList = "5Months-33";
					}else if( age <=80) {
						ageList = String.valueOf(age);
					}else if(age >80) {
						ageList = ApplicationConstant.AGE81ANDABOVE;
					}
					
				}
				
				log.debug("Medi Classic BASIC Age List {} ",ageList);
			}
			
			if(StringUtils.isNotBlank(ageList)) {
				ArrayList<String> ageBands = ageAndAgeBandRepo.getAgeBandsByPeriodAndPlan(mediClassicProductCode, ageList, period, mediClassicPlanBASIC);
				if(ageBands.size()==1) {
					ageBandInYerString = ageBands.get(0);
				}
				log.debug("Medi Classic BASIC Age Band In Years {} ",ageList);
			}
			
			
		}
		return ageBandInYerString;
	}
	
	
	public String getArogyaSanjeevaniAdultsAgeList(String dob, String combination ) {
		String ageList = "";
		String ageBandInYerString = "";
		long age = productUtil.getAgeInYear(dob);
		long  month = productUtil.getAgeInMonth(dob);
		
		if(combination.equalsIgnoreCase(individual)) {	//INDIVIDUAL
			if(month >=3 && age <=35) {
				ageList = ApplicationConstant.AGE3MONTHSTO35YEARS;
			}else if(age <=80) {
				ageList = String.valueOf(age);
			}else if(age > 80){
				ageList = ApplicationConstant.AGE81ANDABOVE;
			}
		}else if(combination.equalsIgnoreCase(floater)) {	// FLOATER
			if(age>= 18 && age <=35) {
				ageList = ApplicationConstant.AGE3MONTHSTO35YEARS;
			}else if(age <=80) {
				ageList = String.valueOf(age);
			}else if(age > 80){
				ageList = ApplicationConstant.AGE81ANDABOVE;
			}
			
		}		
		log.debug("Arogya Sanjeevani Adult Age List {} ",ageList);
		if(StringUtils.isNotBlank(ageList)) {
			ArrayList<String> ageBands = ageAndAgeBandRepo.getAgeBandByPerson(arogyaSanjeevaniProductCode, ageList, adults);
			
			if(ageBands.size()==1) {
				ageBandInYerString = ageBands.get(0);
			}
			log.debug("Arogya Sanjeevani Adult Age Band In Years {} ",ageList);
		}
		return ageBandInYerString;
	}
	
	public String getArogyaSanjeevaniParentsAgeList(String dob) {
		String ageList = "";
		String ageBandInYerString = "";
		long age = productUtil.getAgeInYear(dob);
		if(age>= 18 && age <=60) {
			ageList = "Upto-60";
		}else if(age <=70) {
			ageList = String.valueOf(age);
		}else if(age >70) {
			ageList = "71-AndAbove";
		}
		log.debug("Arogya Sanjeevani Parent Age List {} " ,ageList);
		if(StringUtils.isNotBlank(ageList)) {
			ArrayList<String> ageBands = ageAndAgeBandRepo.getAgeBandByPerson(arogyaSanjeevaniProductCode, ageList, parents);
			if(ageBands.size()==1) {
				ageBandInYerString = ageBands.get(0);
			}
			log.debug("Arogya Sanjeevani Parent Age Band In Years {} " ,ageList);
		}
		return ageBandInYerString;
	}
	
	public String getStarComprehensiveAgeList(String dob, String instalmentPremium) {
		String ageList = "";
		String ageBandInYerString = "";
		long age = productUtil.getAgeInYear(dob);
		long month = productUtil.getAgeInMonth(dob);
		
		if(StringUtils.isNotBlank(instalmentPremium)) {
			if(instalmentPremium.equalsIgnoreCase(instalmentPremium1Year)) {
				if(month >=3 && age <=35) {
					ageList = ApplicationConstant.AGE3MONTHSTO35YEARS;
				}else if(age <=75){
					ageList = String.valueOf(age);
				}else if(age >75) {
					ageList = ApplicationConstant.AGE76ANDABOVE;
				}
			}else if(instalmentPremium.equalsIgnoreCase(instalmentPremium2Year)) {
				if(month >=3 && age <=34) {
					ageList = "3Months-34";
				}else if(age <=75){
					ageList = String.valueOf(age);
				}else if(age >75) {
					ageList = ApplicationConstant.AGE76ANDABOVE;
				}
			}else if(instalmentPremium.equalsIgnoreCase(instalmentPremium3Year)) {
				if(month >=3 && age <=33) {
					ageList = "3Months-33";
				}else if(age <=75){
					ageList = String.valueOf(age);
				}else if(age >75) {
					ageList = ApplicationConstant.AGE76ANDABOVE;
				}
			}
			
		}else { // 1 Year
			if(month >=3 && age <=35) {
				ageList = ApplicationConstant.AGE3MONTHSTO35YEARS;
			}else if(age <=75){
				ageList = String.valueOf(age);
			}else if(age >75) {
				ageList = ApplicationConstant.AGE76ANDABOVE;
			}
		}
		log.debug("Star Comprehensive Age List {} " ,ageList);
		
		if(StringUtils.isNotBlank(ageList)) {
			ArrayList<String> ageBands = ageAndAgeBandRepo.getAgeBandsByPeriod(starComprehensiveProductCode, ageList, instalmentPremium);
			if(ageBands.size()==1) {
				ageBandInYerString = ageBands.get(0);
			}
			log.debug("Star Comprehensive Age Band In Years {} " ,ageList);
		}
		
		return ageBandInYerString;
	}

	public String getYounStarAgeList(String dob, String period){
		
		String ageList = "";
		String ageBandInYerString = "";
		long age = productUtil.getAgeInYear(dob);

		if(age>=18 && age<=65) {
			ageList = String.valueOf(age);
		}else if(age >65) {
			ageList = "66-AndAbove";
		}

		log.debug("Youn Star Age List {} " ,ageList);
		if(StringUtils.isNotBlank(ageList)) {
			ArrayList<String> ageBands = ageAndAgeBandRepo.getAgeBandsByPeriod(youngStarProductCode, ageList, period);
			if(ageBands.size()==1) {
				ageBandInYerString = ageBands.get(0);
			}
			log.debug("Youn Star Age Band In Years {} " ,ageList);
		}
		return ageBandInYerString;
	}
	
	public String getSuperSurplusAgeList(String dob, String instalmentPremium) {
		String ageList = "";
		String ageBandInYerString = "";
		long age = productUtil.getAgeInYear(dob);
		long days = productUtil.getAgeInDays(dob);
		
		if(StringUtils.isNotBlank(instalmentPremium)) {
			if(instalmentPremium.equalsIgnoreCase(instalmentPremium1Year)) {
				if(days >=91 && age <=35) {
					ageList = "91Days-35";
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80) {
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}else if(instalmentPremium.equalsIgnoreCase(instalmentPremium2Year)) {
				if(days >=91 && age <=34) {
					ageList = "91Days-34";
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80) {
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}else {	// 1 Year
				if(days >=91 && age <=35) {
					ageList = "91Days-35";
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80) {
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}
		}
		log.debug("Super Surplus Age List {} " ,ageList);
		if(StringUtils.isNotBlank(ageList)) {
			ArrayList<String> ageBands = ageAndAgeBandRepo.getAgeBandsByPeriod(superSurplusFloaterProductCode, ageList, instalmentPremium);
			if(ageBands.size()==1) {
				ageBandInYerString = ageBands.get(0);
			}
			log.debug("Super Surplus Age Band In Years {} " ,ageList);
		}
		return ageBandInYerString;
	}

	public String getFHOAdultsAgeList(String dob, String instalmentPremium) {
		String ageList = "";
		String ageBandInYerString = "";
		long age = productUtil.getAgeInYear(dob);
		long days = productUtil.getAgeInDays(dob);
		
		if(StringUtils.isNotBlank(instalmentPremium)) {
			if(instalmentPremium.equalsIgnoreCase(instalmentPremium1Year)) {
				if(days >=16 && age <=35) {
					ageList = ApplicationConstant.AGE16DAYSTO35YEARS;
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80) {
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}else if(instalmentPremium.equalsIgnoreCase(instalmentPremium2Year)) {
				if(days >=91 && age <=34) {
					ageList = "16Days-34";
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80) {
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}else {	// 1 Year
				if(days >=91 && age <=35) {
					ageList = ApplicationConstant.AGE16DAYSTO35YEARS;
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80) {
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}
		}
	
		log.debug("FHO Adults Age List {} " ,ageList);
		if(StringUtils.isNotBlank(ageList)) {
			ArrayList<String> ageBands = ageAndAgeBandRepo.getAgeBandByPersonAndPeriod(familyHealthOptimaProductCode, ageList, adults, instalmentPremium);
			if(ageBands.size()==1) {
				ageBandInYerString = ageBands.get(0);
			}
			log.debug("FHO Adults Age Band In Years {} " ,ageList);
		}
		return ageBandInYerString;
	}
	
	public String getFHOParentAgeList(String dob, String instalmentPremium) {
		String ageList = "";
		String ageBandInYerString = "";
		long age = productUtil.getAgeInYear(dob);
		
		if(StringUtils.isNotBlank(instalmentPremium)) {
			if(instalmentPremium.equalsIgnoreCase(instalmentPremium1Year)) {
				if(age <= 50) {
					ageList = "Upto-50";
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80) {
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}else if(instalmentPremium.equalsIgnoreCase(instalmentPremium2Year)) {
				if(age <= 49) {
					ageList = "Upto-49";
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80) {
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}else {	// 1 Year
				if(age <= 50) {
					ageList = "Upto-50";
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80) {
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}
		}
		
		log.debug("FHO Parents Age List {} ",ageList);
		if(StringUtils.isNotBlank(ageList)) {
			ArrayList<String> ageBands = ageAndAgeBandRepo.getAgeBandByPersonAndPeriod(familyHealthOptimaProductCode, ageList, parents, instalmentPremium);
			if(ageBands.size()==1) {
				ageBandInYerString = ageBands.get(0);
			}
			log.debug("FHO Parents Age Band In Years {} ",ageList);
		}
		return ageBandInYerString;
	}
	
	public String getWomenCareAgeList(String dob, String instalmentPremium) {
		String ageList = "";
		String ageBandInYerString = "";
		long age = productUtil.getAgeInYear(dob);
		
		if(age >=18 && age <=75) {
			ageList = String.valueOf(age);
		}else if(age >75) {
			ageList = ApplicationConstant.AGE76ANDABOVE;
		}
		
		log.debug("Women Care Adult Age List {} ",ageList);
		if(StringUtils.isNotBlank(ageList)) {
			ArrayList<String> ageBands = ageAndAgeBandRepo.getAgeBandsByPeriod(starWomenCareProductCode, ageList, instalmentPremium);
			if(ageBands.size()==1) {
				ageBandInYerString = ageBands.get(0);
			}
			log.debug("Women Care Adult Age Band In Years {} ",ageList);
		}
		return ageBandInYerString;
	}
	
	public String getHealthPremierAdultAgeList(String dob, String instalmentPremium) {
		String ageList = "";
		String ageBandInYerString = "";
		long age = productUtil.getAgeInYear(dob);

		if(age <= 49) {
			ageList = "Upto-49";
		}else if(age <= 79) {
			ageList = String.valueOf(age);
		}else if (age >= 80) {
			ageList = "80-AndAbove";
		}
		log.debug("Health Premier Adult Age List {} ",ageList);
		if(StringUtils.isNotBlank(ageList)) {
			ArrayList<String> ageBands = ageAndAgeBandRepo.getAgeBandsByPeriodAndPerson(healthPremierProductCode, ageList, instalmentPremium, adults);
			if(ageBands.size()==1) {
				ageBandInYerString = ageBands.get(0);
			}
			log.debug("Health Premier Adult Age Band In Years {} " ,ageList);
		}
		return ageBandInYerString;
	}
	
	public String getHealthPremierChildAgeList(String dob, String instalmentPremium) {
		String ageList = "";
		String ageBandInYerString = "";
		long age = productUtil.getAgeInYear(dob);
		long days =productUtil.getAgeInDays(dob);
		
		if(StringUtils.isNotBlank(instalmentPremium)) {
			if(instalmentPremium.equalsIgnoreCase(instalmentPremium1Year)) {
				if(days>=91 && age<=15) {
					ageList = ApplicationConstant.AGE91DAYS15YEARS;
				}else if(age <= 20){
					ageList = String.valueOf(age);
				}else{
					ageList = ApplicationConstant.AGE21ANDABOVE;
				}
			}else if(instalmentPremium.equalsIgnoreCase(instalmentPremium2Year)) {
				if(days>=91 && age<=14) {
					ageList = "91Days-14";
				}else if(age <= 20){
					ageList = String.valueOf(age);
				}else{
					ageList = ApplicationConstant.AGE21ANDABOVE;
				}
			}else if(instalmentPremium.equalsIgnoreCase(instalmentPremium3Year)) {
				if(days>=91 && age<=13) {
					ageList = "91Days-13";
				}else if(age <= 20){
					ageList = String.valueOf(age);
				}else{
					ageList = ApplicationConstant.AGE21ANDABOVE;
				}
			}else {
				if(days>=91 && age<=15) {
					ageList = ApplicationConstant.AGE91DAYS15YEARS;
				}else if(age <= 20){
					ageList = String.valueOf(age);
				}else{
					ageList = ApplicationConstant.AGE21ANDABOVE;
				}
			}
		}
		log.debug("Health Premier Child Age List {} " ,ageList);
		if(StringUtils.isNotBlank(ageList)) {
			ArrayList<String> ageBands = ageAndAgeBandRepo.getAgeBandsByPeriodAndPerson(healthPremierProductCode, ageList, instalmentPremium, child);
			if(ageBands.size()==1) {
				ageBandInYerString = ageBands.get(0);
			}
			log.debug("Health Premier Child Age Band In Years {} " ,ageList);
		}
		return ageBandInYerString;
	}
	
	public String getHealthAssureAdultsAgeList(String dob, String instalmentPremium, String scheme) {
		String ageList = "";
		String ageBandInYerString = "";
		long age = productUtil.getAgeInYear(dob);
		long days = productUtil.getAgeInDays(dob);
		
		if(StringUtils.isNotBlank(instalmentPremium)) {
			if(instalmentPremium.equalsIgnoreCase(instalmentPremium1Year)) {
				if(days>=91 && age<=17) {
					ageList = ApplicationConstant.AGE91DAYS17YEARS;
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80){
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}else if(instalmentPremium.equalsIgnoreCase(instalmentPremium2Year)) {
				if(days>=91 && age<=16) {
					ageList = "91Days-16";
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80){
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}else if(instalmentPremium.equalsIgnoreCase(instalmentPremium3Year)) {
				if(days>=91 && age<=15) {
					ageList = ApplicationConstant.AGE91DAYS15YEARS;
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80){
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}else {
				if(days>=91 && age<=17) {
					ageList = ApplicationConstant.AGE91DAYS17YEARS;
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80){
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}
		}
		log.debug("Health Assure Age List {} " ,ageList);
		if(StringUtils.isNotBlank(ageList)) {
			ArrayList<String> ageBands = ageAndAgeBandRepo.getAgeBandsByPeriodAndPersonAndScheme(healthAssureProductCode, ageList, instalmentPremium, adults, scheme);
			if(ageBands.size()==1) {
				ageBandInYerString = ageBands.get(0);
			}
			log.debug("Health Assure Adult Age Band In Years {} " ,ageList);
		}
		return ageBandInYerString;
	}
	
	public String getHealthAssureParentsAgeList(String dob, String instalmentPremium) {
		String ageList = "";
		String ageBandInYerString = "";
		long age = productUtil.getAgeInYear(dob);
		long days = productUtil.getAgeInDays(dob);
		
		if(StringUtils.isNotBlank(instalmentPremium)) {
			if(instalmentPremium.equalsIgnoreCase(instalmentPremium1Year)) {
				if(days>=91 && age<=17) {
					ageList = ApplicationConstant.AGE91DAYS17YEARS;
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80){
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}else if(instalmentPremium.equalsIgnoreCase(instalmentPremium2Year)) {
				if(days>=91 && age<=16) {
					ageList = "91Days-16";
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80){
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}else if(instalmentPremium.equalsIgnoreCase(instalmentPremium3Year)) {
				if(days>=91 && age<=15) {
					ageList = ApplicationConstant.AGE91DAYS15YEARS;
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80){
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}else {
				if(days>=91 && age<=17) {
					ageList = ApplicationConstant.AGE91DAYS17YEARS;
				}else if(age <= 80){
					ageList = String.valueOf(age);
				}else if(age>80){
					ageList = ApplicationConstant.AGE81ANDABOVE;
				}
			}
		}
		log.debug("Health Assure Parent Age List {} " ,ageList);
		if(StringUtils.isNotBlank(ageList)) {
			ArrayList<String> ageBands = ageAndAgeBandRepo.getAgeBandsByPeriodAndPerson(healthAssureProductCode, ageList, instalmentPremium, parents);
			if(ageBands.size()==1) {
				ageBandInYerString = ageBands.get(0);
			}
			log.debug("Health Assure Parent Age Band In Years {} " ,ageList);
		}
		return ageBandInYerString;
	}
	

	public String getAdultAgeList(GetQuoteRequest getQuoteRequest, String period) {
		String ageList = "";
		if(getQuoteRequest.getProductId().equalsIgnoreCase(arogyaSanjeevaniProductCode)) {

			ageList = getArogyaSanjeevaniAdultsAgeList(getQuoteRequest.getAdultDOB1().trim(), getQuoteRequest.getSchemeCode().trim());
		}
		else if(getQuoteRequest.getProductId().equalsIgnoreCase(starComprehensiveProductCode)) {
			ageList = getStarComprehensiveAgeList(getQuoteRequest.getAdultDOB1().trim(), getQuoteRequest.getPeriod().trim());
		}
		else if(getQuoteRequest.getProductId().equalsIgnoreCase(youngStarProductCode)) {
			ageList = getYounStarAgeList(getQuoteRequest.getAdultDOB1().trim(), period);
		}
		else if(getQuoteRequest.getProductId().equalsIgnoreCase(superSurplusIndividualProductCode)) {
			ageList = getSuperSurplusAgeList(getQuoteRequest.getAdultDOB1().trim(), getQuoteRequest.getPeriod().trim());
		}
		else if(getQuoteRequest.getProductId().equalsIgnoreCase(superSurplusFloaterProductCode)) {
			ageList = getSuperSurplusAgeList(getQuoteRequest.getAdultDOB1().trim(), getQuoteRequest.getPeriod().trim());
		}
		else if(getQuoteRequest.getProductId().equalsIgnoreCase(familyHealthOptimaProductCode)) {
			ageList = getFHOAdultsAgeList(getQuoteRequest.getAdultDOB1().trim(), getQuoteRequest.getPeriod());
		}
		else if(getQuoteRequest.getProductId().equalsIgnoreCase(starWomenCareProductCode)) {
			//optional cover-cancer is pending
			ageList = getWomenCareAgeList(getQuoteRequest.getAdultDOB1().trim(),  getQuoteRequest.getPeriod().trim());
		}
		else if(getQuoteRequest.getProductId().equalsIgnoreCase(healthPremierProductCode)) {
			ageList =  getHealthPremierAdultAgeList(getQuoteRequest.getAdultDOB1().trim(), period);
		}
		else if(getQuoteRequest.getProductId().equalsIgnoreCase(healthAssureProductCode)) {
			if(getQuoteRequest.getAdults().equals("1") && StringUtils.isBlank(getQuoteRequest.getChild())
					&& StringUtils.isBlank(getQuoteRequest.getParents())) {
				ageList = getHealthAssureAdultsAgeList(getQuoteRequest.getAdultDOB1().trim(), getQuoteRequest.getPeriod().trim(), "1A");
			}else {
				ageList = getHealthAssureAdultsAgeList(getQuoteRequest.getAdultDOB1().trim(), getQuoteRequest.getPeriod().trim(), "ALL");
			}
		}
		else if (getQuoteRequest.getProductId().equalsIgnoreCase(mediClassicProductCode)) {
			ageList = getMediClassicAgeList(getQuoteRequest.getAdultDOB1().trim(), getQuoteRequest.getPlan().trim(), period);
		}
		 
		
		return ageList;
	}
	
	public String getParentsAgeList(String productCode, String parentDOB, String period) {
		String ageList = "";
		if(productCode.equalsIgnoreCase(arogyaSanjeevaniProductCode)) {
			ageList = getArogyaSanjeevaniParentsAgeList(parentDOB.trim());
		}else if(productCode.equalsIgnoreCase(familyHealthOptimaProductCode)) {
			ageList = getFHOParentAgeList(parentDOB.trim(), period);
		}else if(productCode.equalsIgnoreCase(healthAssureProductCode)) {
			ageList = getHealthAssureParentsAgeList(parentDOB, period.trim());
		}
		
		return ageList;
	}
	
	public String getChildAgeList(String productCode, String childDOB, String period) {
		String ageList = "";
		if(productCode.equalsIgnoreCase(healthPremierProductCode)) {
			ageList =  getHealthPremierChildAgeList(childDOB, period);
		}
		return ageList;
	}
	
	

}
