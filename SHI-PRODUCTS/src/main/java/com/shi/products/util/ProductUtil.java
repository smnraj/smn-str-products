/**
 * 
 */
package com.shi.products.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.shi.products.req.GetQuoteRequest;
import com.shi.products.res.Quotes;
import com.shi.products.vo.GetQuoteResponseVO;

/**
 * @author suman.raju
 *
 */
@Component
public class ProductUtil {
	private static final Logger log = LoggerFactory.getLogger(ProductUtil.class);
	
	@Autowired
	Environment environment;
	private String INDIVIDUAL = "INDIVIDUAL";
	private String FAMILY = "FAMILY";
	
	@Value("${upfront_discount_perc}")
	private String upfrontDiscountPerc;
	
	@Value("${upfront_discount_gst}")
	private String upfrontDiscountGST;
	
	private static Map<String, String> schemeCode;
    static
    {
    	schemeCode = new HashMap<>();
    	schemeCode.put("1A", "1A");
    	schemeCode.put("2A", "2A");
    	schemeCode.put("3A", "3A");
    	schemeCode.put("1A+1C", "1A+1C");
    	schemeCode.put("2A+1C", "2A+1C");
    	schemeCode.put("3A+1C", "3A+1C");
    	schemeCode.put("1A+2C", "1A+2C");
    	schemeCode.put("2A+2C", "2A+2C");
    	schemeCode.put("3A+2C", "3A+2C");
    	schemeCode.put("1A+3C", "1A+3C");
    	schemeCode.put("2A+3C", "2A+3C");
    	schemeCode.put("3A+3C", "3A+3C");
    	schemeCode.put("Individual", "1A");
    	schemeCode.put("individual", "1A");
    }
	
	public <T> String doMarshallingJSON(T obj){
		String jsonMessage = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			jsonMessage = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error("ERROR {}", e.getMessage());
		}
		return jsonMessage;
	}
	
	public <T> T deMarshallingJSON(String jsonMessage, Class<T> clazz) throws Exception{
		ObjectMapper Obj = JsonMapper.builder().enable(new JsonReadFeature[] {JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS}).build();
		T obj = Obj.readValue(jsonMessage, clazz);
		return obj;
	}
	
	public Period calculateAge(String DOB) {
		String[] dateOfBerth = DOB.split("/");
		LocalDate fromDate = LocalDate.of(Integer.parseInt(dateOfBerth[2]), Integer.parseInt(dateOfBerth[1]), Integer.parseInt(dateOfBerth[0]));
		LocalDate toDate = LocalDate.now();
		log.debug("From Date = {},  & To Date = {}" ,fromDate, toDate);
		Period p = Period.between(fromDate, toDate);
		log.debug("{} Years {} Months {} Days",p.getYears(), p.getMonths(),p.getDays());
		return p;
	}
	
	public long getAgeInYear(String dob) {
		log.debug("Age In Years {}",dob);
		if(StringUtils.isNotBlank(dob)) {
			String[] dateOfBerth = dob.split("/");
			//LocalDate startDate = LocalDate.of(Integer.parseInt(dateOfBerth[0]), Integer.parseInt(dateOfBerth[1]), Integer.parseInt(dateOfBerth[2]));  
			LocalDate startDate = LocalDate.of(Integer.parseInt(dateOfBerth[2]), Integer.parseInt(dateOfBerth[1]), Integer.parseInt(dateOfBerth[0]));  
			LocalDate endDate = LocalDate.now();
			long years = ChronoUnit.YEARS.between(startDate, endDate);  
			log.debug("Age {} years old.", years); 
			return years;
		}else {
			return 0;
		}
		
	}

	public long getAgeInMonth(String DOB) {
		log.debug("Age In Month");
		if(StringUtils.isNotBlank(DOB)) {
			String[] dateOfBerth = DOB.split("/");
			LocalDate startDate = LocalDate.of(Integer.parseInt(dateOfBerth[2]), Integer.parseInt(dateOfBerth[1]), Integer.parseInt(dateOfBerth[0]));  
			LocalDate endDate = LocalDate.now();
			long years = ChronoUnit.MONTHS.between(startDate, endDate);  
			log.debug("Age {} Month old.", years); 
			return years;
		}else {
			return 0;
		}
		
	}
	
	public long getAgeInDays(String DOB) {
		log.debug("Age In Days");
		if(StringUtils.isNotBlank(DOB)) {
			String[] dateOfBerth = DOB.split("/");
			LocalDate startDate = LocalDate.of(Integer.parseInt(dateOfBerth[2]), Integer.parseInt(dateOfBerth[1]), Integer.parseInt(dateOfBerth[0]));  
			LocalDate endDate = LocalDate.now();
			long days = ChronoUnit.DAYS.between(startDate, endDate);  
			log.debug("Age {} days old.", days); 
			return days;
		}else {
			return 0;
		}
		
	}
	
	public ArrayList<String> dateSortingDescending(ArrayList<String> datesList) {
		log.debug("Date List Sorting Descending Order");
		Collections.sort(datesList, new Comparator<String>() {
			DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

			@Override
			public int compare(String o1, String o2) {
				try {
					return f.parse(o1).compareTo(f.parse(o2));
				} catch (ParseException e) {
					throw new IllegalArgumentException(e);
				}
			}
		}.reversed());
		
		log.debug("Date List Sorting {} " ,datesList);
		return datesList;
	}
	
	public List<String> dateSortingAscending (List<String> datesList) {
		log.debug("Date List Sorting Ascending Order");
		Collections.sort(datesList, new Comparator<String>() {
			DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

			@Override
			public int compare(String o1, String o2) {
				try {
					return f.parse(o1).compareTo(f.parse(o2));
				} catch (ParseException e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
		
		log.debug("Date List Sorting {} " ,datesList);
		return datesList;
	}
	
	public String roundOfString(String value) {
		String roundOfValue ="";
		if(StringUtils.isNotBlank(value)) {
			roundOfValue = String.valueOf(Math.round(Double.valueOf(value)));
		}
		return roundOfValue;
	}
	
	public String getAdult(String schemeCode) {
		log.debug("Plan Type for Adult = {}" , schemeCode );
		String adult = "";
		String[] adults = schemeCode.split("\\+");

		if (adults.length >= 1 && schemeCode.contains("A") && !schemeCode.equalsIgnoreCase("ALL")) {
			adult = schemeCode.split("\\+")[0].substring(0, 1);
		}
		log.debug("Adult = {}" , adult);

		return adult;
	}
	
	public String getChild(String schemeCode) {
		log.debug("Plan Type for Child = {} " , schemeCode );
		String child = "";
		String[] childs = schemeCode.split("\\+");

		if (childs.length == 2 && schemeCode.contains("C")) {
			child = schemeCode.split("\\+")[1].substring(0, 1);
		}else if (childs.length == 1 && schemeCode.contains("C")) {
			child = schemeCode.split("\\+")[0].substring(0, 1);
		}
		log.debug("Child = {} " , child);

		return child;
	}
	
	public String getParents(String schemeCode) {
		log.debug("Plan Type for Parents = {} " , schemeCode );
		String parent = "";
		String[] parents = schemeCode.split("\\+");

		if (parents.length == 2 && schemeCode.contains("P")) {
			parent = schemeCode.split("\\+")[1].substring(0, 1);
		}else if (parents.length == 1 && schemeCode.contains("P")) {
			parent = schemeCode.split("\\+")[0].substring(0, 1);
		}
		log.debug("Parents = {} " , parent);

		return parent;
	}
	
	
	public String getDOB(String ageBandInYears) {
		log.debug("Age Band In Years = {} " ,ageBandInYears);
		String dob="";
        LocalDate localDate  = LocalDate.now();
        int currentYear = localDate.getYear();
        int yearDOB = 0; 
        if(ageBandInYears.contains(ApplicationConstant.ABOVE1) || ageBandInYears.contains("above") || ageBandInYears.contains("ABOVE")) {
        	//yearDOB = currentYear-Integer.parseInt(ageBandInYears.substring(ageBandInYears.length()-2))-1;
        	yearDOB = currentYear-Integer.parseInt(ageBandInYears.substring(0,2));
        }else {
        	yearDOB = currentYear-Integer.parseInt(ageBandInYears.substring(ageBandInYears.length()-2)); 
        }
        
        int currentMonth = localDate.getMonthValue();
        int currentDay = localDate.getDayOfMonth();
        
        if(currentMonth == 1) {
        	currentMonth = 12;
        	yearDOB = yearDOB-1;
        }else if(currentMonth == 3 && currentDay>28){
        	currentMonth = currentMonth-2;
        }else {
        	currentMonth = currentMonth-1;
        }
        
        dob = String.valueOf(currentDay).concat("/").concat(String.valueOf(currentMonth)).concat("/").concat(String.valueOf(yearDOB));
		log.debug("DOB = {}",dob);
        return dob;
	}
	
	public String getDOBFromAge(String age) {
		log.debug("Age In Years = {} " ,age);
		String dob="";
		if (StringUtils.isNotBlank(age)) {
			LocalDate localDate = LocalDate.now();
			int currentYear = localDate.getYear();
			int yearDOB = 0;

			yearDOB = currentYear - Integer.parseInt(age);

			int currentMonth = localDate.getMonthValue();
			int currentDay = localDate.getDayOfMonth();
			if (currentMonth == 1) {
				currentMonth = 12;
				yearDOB = yearDOB - 1;
			}else if(currentMonth == 3 && currentDay>28){
	        	currentMonth = currentMonth-2;
	        } else {
				currentMonth = currentMonth - 1;
			}

			dob = String.valueOf(currentDay).concat("/").concat(String.valueOf(currentMonth)).concat("/").concat(String.valueOf(yearDOB));
		}
		log.debug("DOB = {}",dob);
        return dob;
	}
	
	public String getAgeList(String ageBandInYears) {
		log.debug("Age List = {} " ,ageBandInYears);
		String ageList = "";
		
		if(StringUtils.isNotBlank(ageBandInYears) && !ageBandInYears.contains("Day") && !ageBandInYears.contains("Month") 
				&& !ageBandInYears.contains(ApplicationConstant.ABOVE1) && !ageBandInYears.contains("Upto") && !ageBandInYears.endsWith("C")) {
			if(ageBandInYears.contains("-")) {
				String[] years = ageBandInYears.split("-");
				int yearMin = Integer.parseInt(years[0]);
				int yearMax = Integer.parseInt(years[1]);
				for(int i=yearMin; i<=yearMax; i++) {
					ageList = ageList.concat(String.valueOf(i));
					if(i != yearMax) {
						ageList = ageList.concat(",");
					}
				}
				
			}else {
				ageList = ageBandInYears;
			}
			
		}else {
			ageList = ageBandInYears;
		}
		
		log.debug("Age List {}",ageList);
		return ageList;
	}
	
	public List<String> getAgeListInArray(String ageBandInYears) {
		log.debug("Age List = {} " ,ageBandInYears);
		List<String> ageList = new ArrayList<>();
		
		if(StringUtils.isNotBlank(ageBandInYears) && !ageBandInYears.contains("Day") && !ageBandInYears.contains("Month") 
				&& !ageBandInYears.contains(ApplicationConstant.ABOVE1) && !ageBandInYears.contains("Upto") && !ageBandInYears.endsWith("C")) {
			if(ageBandInYears.contains("-")) {
				String[] years = ageBandInYears.split("-");
				int yearMin = Integer.parseInt(years[0]);
				int yearMax = Integer.parseInt(years[1]);
				for(int i=yearMin; i<=yearMax; i++) {
					ageList.add(String.valueOf(i));
				}
				
			}else {
				ageList.add(ageBandInYears);
			}
			
		}else {
			ageList.add(ageBandInYears);
		}
		
		log.debug("Age List {}",ageList);
		return ageList;
	}

	public String getCombination(GetQuoteRequest getQuoteRequest) {
		log.debug("Famil Combination Starts");
		String combination = "";
		
		if(StringUtils.isNotBlank(getQuoteRequest.getAdults()) && getQuoteRequest.getAdults().equals("1")
				&& StringUtils.isBlank(getQuoteRequest.getParents()) && StringUtils.isBlank(getQuoteRequest.getChild())
				&& StringUtils.isBlank(getQuoteRequest.getOthers())) {
			combination = INDIVIDUAL;
		}else if(StringUtils.isNotBlank(getQuoteRequest.getOthers()) && getQuoteRequest.getOthers().equals("1")
				&& StringUtils.isBlank(getQuoteRequest.getParents()) && StringUtils.isBlank(getQuoteRequest.getChild())
				&& StringUtils.isBlank(getQuoteRequest.getAdults())) {
			combination = INDIVIDUAL;
		}else {
			combination = FAMILY;
		}

		log.debug("Famil Combination End = {}",combination);
		return combination;
	}
	
	
	public Double calculateDiscount(Integer premiumAmount) {
		
		return (double) (premiumAmount*Integer.parseInt(upfrontDiscountPerc)/100);
	}

	public Double calculateTax(Double premiumAmount) {
		return premiumAmount*Integer.parseInt(upfrontDiscountGST)/100;
	}

	public Double calculatePremium(Integer premiumAmount, Double discountAmount) {
		return premiumAmount - discountAmount;
	}

	public Double calculateTotal(Double premiumAmount, Double taxAmount) {
		return premiumAmount + taxAmount;
	}
	
	public GetQuoteResponseVO calculateUpFrontDiscount(GetQuoteResponseVO getQuoteRequest) {
		
		if(getQuoteRequest.getPremiumAmount()!=null && getQuoteRequest.getPremiumAmount()>0) {
			calulatePremium(getQuoteRequest);
		}
		
		if(getQuoteRequest.getHlflyPremAmntFirstPrem()!=null && getQuoteRequest.getHlflyPremAmntFirstPrem()>0) {
			calulateHlflyPremAmntFirstPrem(getQuoteRequest);
		}
		
		if(getQuoteRequest.getHlflyPremAmntOthersPrem()!=null && getQuoteRequest.getHlflyPremAmntOthersPrem()>0) {
			calulateHlflyPremAmntOthersPrem(getQuoteRequest);
		}
		
		if(getQuoteRequest.getQtrlyPremAmntFirstPrem()!=null && getQuoteRequest.getQtrlyPremAmntFirstPrem()>0) {
			calulateQtrlyPremAmntFirstPrem(getQuoteRequest);
		}
		
		if(getQuoteRequest.getQtrlyPremAmntOthersPrem()!=null && getQuoteRequest.getQtrlyPremAmntOthersPrem()>0) {
			calulateQtrlyPremAmntOthersPrem(getQuoteRequest);
		}
		
		if(getQuoteRequest.getMnthlyPremAmntFirstPrem()!=null && getQuoteRequest.getMnthlyPremAmntFirstPrem()>0) {
			calulateMnthlyPremAmntFirstPrem(getQuoteRequest);
		}
		
		if(getQuoteRequest.getMnthlyPremAmntOthersPrem()!=null && getQuoteRequest.getMnthlyPremAmntOthersPrem()>0) {
			calulateMnthlyPremAmntOthersPrem(getQuoteRequest);
		}
		
		return getQuoteRequest;
	}

	private GetQuoteResponseVO calulatePremium(GetQuoteResponseVO getQuoteRequest) {
		Double discountAmount = calculateDiscount(getQuoteRequest.getPremiumAmount());
		Double premiumAmount = calculatePremium(getQuoteRequest.getPremiumAmount(), discountAmount);
		Double taxAmount = calculateTax(premiumAmount);
		Double totalAmount = calculateTotal(premiumAmount,taxAmount);
		
		getQuoteRequest.setPremiumAmount((int)Math.round(premiumAmount));
		getQuoteRequest.setPremiumTax((int)Math.round(taxAmount));
		getQuoteRequest.setTotalAmount((int)Math.round(totalAmount));
		getQuoteRequest.setTotalOnlineDiscountAmt((int)Math.round(discountAmount));
		return getQuoteRequest;	
	}
	
	private GetQuoteResponseVO calulateHlflyPremAmntFirstPrem(GetQuoteResponseVO getQuoteRequest) {
		Double discountAmount = calculateDiscount(getQuoteRequest.getHlflyPremAmntFirstPrem());
		Double premiumAmount = calculatePremium(getQuoteRequest.getHlflyPremAmntFirstPrem(), discountAmount);
		Double taxAmount = calculateTax(premiumAmount);
		Double totalAmount = calculateTotal(premiumAmount,taxAmount);
		
		getQuoteRequest.setHlflyPremAmntFirstPrem((int)Math.round(premiumAmount));
		getQuoteRequest.setHlflyPremAmntFirstTax((int)Math.round(taxAmount));
		getQuoteRequest.setHlflyPremAmntFirstTotal((int)Math.round(totalAmount));
		return getQuoteRequest;	
	}
	
	private GetQuoteResponseVO calulateHlflyPremAmntOthersPrem(GetQuoteResponseVO getQuoteRequest) {
		Double discountAmount = calculateDiscount(getQuoteRequest.getHlflyPremAmntOthersPrem());
		Double premiumAmount = calculatePremium(getQuoteRequest.getHlflyPremAmntOthersPrem(), discountAmount);
		Double taxAmount = calculateTax(premiumAmount);
		Double totalAmount = calculateTotal(premiumAmount,taxAmount);
		
		getQuoteRequest.setHlflyPremAmntOthersPrem((int)Math.round(premiumAmount));
		getQuoteRequest.setHlflyPremAmntOthersTax((int)Math.round(taxAmount));
		getQuoteRequest.setHlflyPremAmntOthersTotal((int)Math.round(totalAmount));
		return getQuoteRequest;	
	}
	
	private GetQuoteResponseVO calulateQtrlyPremAmntFirstPrem(GetQuoteResponseVO getQuoteRequest) {
		Double discountAmount = calculateDiscount(getQuoteRequest.getQtrlyPremAmntFirstPrem());
		Double premiumAmount = calculatePremium(getQuoteRequest.getQtrlyPremAmntFirstPrem(), discountAmount);
		Double taxAmount = calculateTax(premiumAmount);
		Double totalAmount = calculateTotal(premiumAmount,taxAmount);
		
		getQuoteRequest.setQtrlyPremAmntFirstPrem((int)Math.round(premiumAmount));
		getQuoteRequest.setQtrlyPremAmntFirstTax((int)Math.round(taxAmount));
		getQuoteRequest.setQtrlyPremAmntFirstTotal((int)Math.round(totalAmount));
		return getQuoteRequest;			
	}
	
	

	private GetQuoteResponseVO calulateQtrlyPremAmntOthersPrem(GetQuoteResponseVO getQuoteRequest) {
		Double discountAmount = calculateDiscount(getQuoteRequest.getQtrlyPremAmntOthersPrem());
		Double premiumAmount = calculatePremium(getQuoteRequest.getQtrlyPremAmntOthersPrem(), discountAmount);
		Double taxAmount = calculateTax(premiumAmount);
		Double totalAmount = calculateTotal(premiumAmount,taxAmount);
		
		getQuoteRequest.setQtrlyPremAmntOthersPrem((int)Math.round(premiumAmount));
		getQuoteRequest.setQtrlyPremAmntOthersTax((int)Math.round(taxAmount));
		getQuoteRequest.setQtrlyPremAmntOthersTotal((int)Math.round(totalAmount));
		return getQuoteRequest;			
	}
	
	private GetQuoteResponseVO calulateMnthlyPremAmntFirstPrem(GetQuoteResponseVO getQuoteRequest) {
		Double discountAmount = calculateDiscount(getQuoteRequest.getMnthlyPremAmntFirstPrem());
		Double premiumAmount = calculatePremium(getQuoteRequest.getMnthlyPremAmntFirstPrem(), discountAmount);
		Double taxAmount = calculateTax(premiumAmount);
		Double totalAmount = calculateTotal(premiumAmount,taxAmount);
		
		getQuoteRequest.setMnthlyPremAmntFirstPrem((int)Math.round(premiumAmount));
		getQuoteRequest.setMnthlyPremAmntFirstTax((int)Math.round(taxAmount));
		getQuoteRequest.setMnthlyPremAmntFirstTotal((int)Math.round(totalAmount));
		return getQuoteRequest;	
	}
	

	private GetQuoteResponseVO calulateMnthlyPremAmntOthersPrem(GetQuoteResponseVO getQuoteRequest) {
		Double discountAmount = calculateDiscount(getQuoteRequest.getMnthlyPremAmntOthersPrem());
		Double premiumAmount = calculatePremium(getQuoteRequest.getMnthlyPremAmntOthersPrem(), discountAmount);
		Double taxAmount = calculateTax(premiumAmount);
		Double totalAmount = calculateTotal(premiumAmount,taxAmount);
		
		getQuoteRequest.setMnthlyPremAmntOthersPrem((int)Math.round(premiumAmount));
		getQuoteRequest.setMnthlyPremAmntOthersTax((int)Math.round(taxAmount));
		getQuoteRequest.setMnthlyPremAmntOthersTotal((int)Math.round(totalAmount));
		return getQuoteRequest;			
	}

	public Quotes calculateUpfronDiscountOnDemand(Quotes quote) {
		if(quote.getPremiumAmount()!=null && quote.getPremiumAmount()>0) {
			calulatePremiumOnDemand(quote);
		}
		
		if(quote.getHlflyPremAmntFirstPrem()!=null && quote.getHlflyPremAmntFirstPrem()>0) {
			calulateHlflyPremAmntFirstPremOnDemand(quote);
		}
		
		if(quote.getHlflyPremAmntOthersPrem()!=null && quote.getHlflyPremAmntOthersPrem()>0) {
			calulateHlflyPremAmntOthersPremOnDemand(quote);
		}
		
		if(quote.getQtrlyPremAmntFirstPrem()!=null && quote.getQtrlyPremAmntFirstPrem()>0) {
			calulateQtrlyPremAmntFirstPremOnDemand(quote);
		}
		
		if(quote.getQtrlyPremAmntOthersPrem()!=null && quote.getQtrlyPremAmntOthersPrem()>0) {
			calulateQtrlyPremAmntOthersPremOnDemand(quote);
		}
		
		if(quote.getMnthlyPremAmntFirstPrem()!=null && quote.getMnthlyPremAmntFirstPrem()>0) {
			calulateMnthlyPremAmntFirstPremOnDemand(quote);
		}
		
		if(quote.getMnthlyPremAmntOthersPrem()!=null && quote.getMnthlyPremAmntOthersPrem()>0) {
			calulateMnthlyPremAmntOthersPremOnDemand(quote);
		}
		
		return quote;
	}
	
	private Quotes calulatePremiumOnDemand(Quotes quote) {
		Double discountAmount = calculateDiscount(quote.getPremiumAmount());
		Double premiumAmount = calculatePremium(quote.getPremiumAmount(), discountAmount);
		Double taxAmount = calculateTax(premiumAmount);
		Double totalAmount = calculateTotal(premiumAmount,taxAmount);
		
		quote.setPremiumAmount((int)Math.round(premiumAmount));
		quote.setPremiumTax((int)Math.round(taxAmount));
		quote.setTotalAmount((int)Math.round(totalAmount));
		quote.setTotalOnlineDiscountAmt((int)Math.round(discountAmount));
		return quote;	
	}
	
	private Quotes calulateHlflyPremAmntFirstPremOnDemand(Quotes quote) {
		Double discountAmount = calculateDiscount(quote.getHlflyPremAmntFirstPrem());
		Double premiumAmount = calculatePremium(quote.getHlflyPremAmntFirstPrem(), discountAmount);
		Double taxAmount = calculateTax(premiumAmount);
		Double totalAmount = calculateTotal(premiumAmount,taxAmount);
		
		quote.setHlflyPremAmntFirstPrem((int)Math.round(premiumAmount));
		quote.setHlflyPremAmntFirstTax((int)Math.round(taxAmount));
		quote.setHlflyPremAmntFirstTotal((int)Math.round(totalAmount));
		return quote;	
	}
	
	private Quotes calulateHlflyPremAmntOthersPremOnDemand(Quotes quote) {
		Double discountAmount = calculateDiscount(quote.getHlflyPremAmntOthersPrem());
		Double premiumAmount = calculatePremium(quote.getHlflyPremAmntOthersPrem(), discountAmount);
		Double taxAmount = calculateTax(premiumAmount);
		Double totalAmount = calculateTotal(premiumAmount,taxAmount);
		
		quote.setHlflyPremAmntOthersPrem((int)Math.round(premiumAmount));
		quote.setHlflyPremAmntOthersTax((int)Math.round(taxAmount));
		quote.setHlflyPremAmntOthersTotal((int)Math.round(totalAmount));
		return quote;	
	}
	
	private Quotes calulateQtrlyPremAmntFirstPremOnDemand(Quotes quote) {
		Double discountAmount = calculateDiscount(quote.getQtrlyPremAmntFirstPrem());
		Double premiumAmount = calculatePremium(quote.getQtrlyPremAmntFirstPrem(), discountAmount);
		Double taxAmount = calculateTax(premiumAmount);
		Double totalAmount = calculateTotal(premiumAmount,taxAmount);
		
		quote.setQtrlyPremAmntFirstPrem((int)Math.round(premiumAmount));
		quote.setQtrlyPremAmntFirstTax((int)Math.round(taxAmount));
		quote.setQtrlyPremAmntFirstTotal((int)Math.round(totalAmount));
		return quote;			
	}
	
	

	private Quotes calulateQtrlyPremAmntOthersPremOnDemand(Quotes quote) {
		Double discountAmount = calculateDiscount(quote.getQtrlyPremAmntOthersPrem());
		Double premiumAmount = calculatePremium(quote.getQtrlyPremAmntOthersPrem(), discountAmount);
		Double taxAmount = calculateTax(premiumAmount);
		Double totalAmount = calculateTotal(premiumAmount,taxAmount);
		
		quote.setQtrlyPremAmntOthersPrem((int)Math.round(premiumAmount));
		quote.setQtrlyPremAmntOthersTax((int)Math.round(taxAmount));
		quote.setQtrlyPremAmntOthersTotal((int)Math.round(totalAmount));
		return quote;			
	}
	
	private Quotes calulateMnthlyPremAmntFirstPremOnDemand(Quotes quote) {
		Double discountAmount = calculateDiscount(quote.getMnthlyPremAmntFirstPrem());
		Double premiumAmount = calculatePremium(quote.getMnthlyPremAmntFirstPrem(), discountAmount);
		Double taxAmount = calculateTax(premiumAmount);
		Double totalAmount = calculateTotal(premiumAmount,taxAmount);
		
		quote.setMnthlyPremAmntFirstPrem((int)Math.round(premiumAmount));
		quote.setMnthlyPremAmntFirstTax((int)Math.round(taxAmount));
		quote.setMnthlyPremAmntFirstTotal((int)Math.round(totalAmount));
		return quote;	
	}
	

	private Quotes calulateMnthlyPremAmntOthersPremOnDemand(Quotes quote) {
		Double discountAmount = calculateDiscount(quote.getMnthlyPremAmntOthersPrem());
		Double premiumAmount = calculatePremium(quote.getMnthlyPremAmntOthersPrem(), discountAmount);
		Double taxAmount = calculateTax(premiumAmount);
		Double totalAmount = calculateTotal(premiumAmount,taxAmount);
		
		quote.setMnthlyPremAmntOthersPrem((int)Math.round(premiumAmount));
		quote.setMnthlyPremAmntOthersTax((int)Math.round(taxAmount));
		quote.setMnthlyPremAmntOthersTotal((int)Math.round(totalAmount));
		return quote;			
	}

}
