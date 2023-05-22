/**
 * 
 */
package com.shi.products.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.shi.products.repo.AgeAndAgeBandRepo;
import com.shi.products.repo.GetQuoteRequestRepo;
import com.shi.products.repo.GetQuoteResponseRepo;
import com.shi.products.repo.ProductsAdultAgeRepo;
import com.shi.products.repo.ProductsChildAgeRepo;
import com.shi.products.repo.ProductsParentAndParentInLawAgeRepo;
import com.shi.products.util.ApplicationConstant;
import com.shi.products.util.ProductUtil;
import com.shi.products.vo.AgeAndAgeBandVO;
import com.shi.products.vo.GetQuoteRequestVO;
import com.shi.products.vo.ProductsAdultAgeVO;
import com.shi.products.vo.ProductsChildAgeVO;
import com.shi.products.vo.ProductsParentAndParentInLawAgeVO;

/**
 * @author suman.raju
 *
 */
@Service
public class ProductsBatchService {
	
	private static final Logger log = LoggerFactory.getLogger(ProductsBatchService.class);
	
	@Autowired
	GetQuoteRequestRepo getQuoteRequestRepo;
	
	@Autowired
	GetQuoteResponseRepo getQuoteResponseRepo;
	
	@Autowired
	ProductsMetaDataService productsMetaDataService;
	
	@Autowired
	ProductsAdultAgeRepo adultAgeRepo;
	
	@Autowired
	ProductsParentAndParentInLawAgeRepo parentAgeRepo;
	
	@Autowired
	ProductsChildAgeRepo childAgeRepo; 
	
	@Autowired
	ProductUtil productUtil;
	
	@Autowired
	Environment environment;
	
	@Autowired
	AgeAndAgeBandRepo ageAndAgeBandRepo;
	
	@Value("${Adults}")
	private String adults;
	
	@Value("${Parents}")
	private String parents;
	
	@Value("${Child}")
	private String child;
	
	private String simpleDateFormat = "yyyyMMddHHmmss";

	public String getQuotes(String productCode) {
		
		log.debug("GetQuote Service Starts");
		
		List<GetQuoteRequestVO> getQuoteRequestVOs = getQuoteRequestRepo.getBatchRequests(productCode);
		log.debug("Batch Request Size = {} " ,getQuoteRequestVOs.size());
		
		if(CollectionUtils.isNotEmpty(getQuoteRequestVOs)) {
			getQuoteRequestRepo.updateGetQuoteRequestInQueue(productCode);
		}
		
		int totalPlanCount = 0;
		for(GetQuoteRequestVO getQuoteRequestVO : getQuoteRequestVOs) {
			log.debug("getQuoteRequestVO={} " ,getQuoteRequestVO);
			getQuoteRequestRepo.updateGetQuoteRequestInProgress(productCode, getQuoteRequestVO.getId());
			try {
				totalPlanCount = productsMetaDataService.getQuotes(getQuoteRequestVO, totalPlanCount);
			} catch (Exception e) {
				log.error("BatchRequestFailed {}" ,getQuoteRequestVO.getId());
				getQuoteResponseRepo.deleteInCompleteRequest(productCode, getQuoteRequestVO.getId());
				getQuoteRequestRepo.updateGetQuoteRequestFailed(productCode, getQuoteRequestVO.getId());
				log.error(e.getMessage());
			}
		}
		
		log.debug("GetQuote Service End TotalPlanCount = {}" , totalPlanCount);
		
		return ApplicationConstant.SUCCESS;
	}

	public String getQuotesRequestBatchSchedule(String productCode) {
		
		log.debug("GetQuote Batch Service Starts {} " ,productCode);
		
		List<GetQuoteRequestVO> getQuoteRequestVOs = getQuoteRequestRepo.getBatchFailedRequests(productCode);
		log.debug("Batch Request Size = {} " ,getQuoteRequestVOs.size());
		
		if(CollectionUtils.isNotEmpty(getQuoteRequestVOs)) {
			getQuoteRequestRepo.updateScheduleGetQuoteRequestInQueue(productCode);
		}
		
		int totalPlanCount = 0;
		for(GetQuoteRequestVO getQuoteRequestVO : getQuoteRequestVOs) {
			log.debug("getQuoteRequestVO={} " ,getQuoteRequestVO);
			getQuoteRequestRepo.updateGetQuoteRequestInProgress(productCode, getQuoteRequestVO.getId());
			try {
				totalPlanCount = productsMetaDataService.getQuotes(getQuoteRequestVO, totalPlanCount);
			} catch (Exception e) {
				log.error("BatchRequestFailed {}" ,getQuoteRequestVO.getId());
				getQuoteResponseRepo.deleteInCompleteRequest(productCode, getQuoteRequestVO.getId());
				getQuoteRequestRepo.updateGetQuoteRequestFailed(productCode, getQuoteRequestVO.getId());
				log.error(e.getMessage());
			}
		}
		log.debug("GetQuote Batch Service End TotalPlanCount = {}" , totalPlanCount);
		
		return ApplicationConstant.SUCCESS;
	}
	
	public String runAdultsAgeAndAgeBand(String productCode) {
		log.debug("runAdultsAgeAndAgeBand Starts {}",productCode);
		
		List<ProductsAdultAgeVO> adultAgeVOs  = adultAgeRepo.getAgeList(productCode);
		log.debug("Adult Age Size = {}",adultAgeVOs.size());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(simpleDateFormat);
		LocalDateTime now = LocalDateTime.now();
		log.debug("AdultsAgeAnd Date Format :: {}" , dtf.format(now));
		String batchId = dtf.format(now);
		
		for(ProductsAdultAgeVO adultAgeVO : adultAgeVOs) {
			List<String> ageList = productUtil.getAgeListInArray(adultAgeVO.getAgeBandInYears());
			List<AgeAndAgeBandVO> ageAndAgeBandVOs = new ArrayList<>();
			for(String age :  ageList) {
				AgeAndAgeBandVO ageAndAgeBandVO = new AgeAndAgeBandVO();
				ageAndAgeBandVO.setBatchId(batchId);
				ageAndAgeBandVO.setProductCode(adultAgeVO.getProductCode());
				ageAndAgeBandVO.setProductName(adultAgeVO.getProductName());
				ageAndAgeBandVO.setPeriod(adultAgeVO.getPeriod());
				ageAndAgeBandVO.setPlan(adultAgeVO.getPlan());
				ageAndAgeBandVO.setSchemeCode(adultAgeVO.getSchemeCode());
				ageAndAgeBandVO.setPerson(adults);
				ageAndAgeBandVO.setAge(age);
				ageAndAgeBandVO.setAgeBandInYears(adultAgeVO.getAgeBandInYears());
				ageAndAgeBandVO.setFlag("0");
				ageAndAgeBandVO.setIsActive("TRUE");
				ageAndAgeBandVOs.add(ageAndAgeBandVO);
			}
			
			ageAndAgeBandRepo.saveAll(ageAndAgeBandVOs);
		}
		
		log.debug("runAdultsAgeAndAgeBand End {} " ,productCode);
		return ApplicationConstant.SUCCESS;
	}

	public String runChildAgeAndAgeBand(String productCode) {
		log.debug("runChildAgeAndAgeBand Starts {} " ,productCode);
		
		List<ProductsChildAgeVO> childAgeVOs  = childAgeRepo.getAgeList(productCode);
		log.debug("Child Age Size = {} " ,childAgeVOs.size());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(simpleDateFormat);
		LocalDateTime now = LocalDateTime.now();
		log.debug("ChildAgeAndAgeBand Date Format :: {}" , dtf.format(now));
		String batchId = dtf.format(now);
		
		for(ProductsChildAgeVO childAgeVO : childAgeVOs) {
			List<String> ageList = productUtil.getAgeListInArray(childAgeVO.getAgeBandInYears());
			List<AgeAndAgeBandVO> ageAndAgeBandVOs = new ArrayList<>();
			for(String age :  ageList) {
				AgeAndAgeBandVO ageAndAgeBandVO = new AgeAndAgeBandVO();
				ageAndAgeBandVO.setBatchId(batchId);
				ageAndAgeBandVO.setProductCode(childAgeVO.getProductCode());
				ageAndAgeBandVO.setProductName(childAgeVO.getProductName());
				ageAndAgeBandVO.setPeriod(childAgeVO.getPeriod());
				ageAndAgeBandVO.setPerson(child);
				ageAndAgeBandVO.setAge(age);
				ageAndAgeBandVO.setAgeBandInYears(childAgeVO.getAgeBandInYears());
				ageAndAgeBandVO.setFlag("0");
				ageAndAgeBandVO.setIsActive("TRUE");
				ageAndAgeBandVOs.add(ageAndAgeBandVO);
			}
			
			ageAndAgeBandRepo.saveAll(ageAndAgeBandVOs);
		}
		
		log.debug("runChildAgeAndAgeBand End {} " ,productCode);
		return ApplicationConstant.SUCCESS;
	}

	public String runParentsAgeAndAgeBand(String productCode) {
		log.debug("runParentsAgeAndAgeBand Starts {} " ,productCode);
		
		List<ProductsParentAndParentInLawAgeVO> parentsAgeVOs  = parentAgeRepo.getAgeList(productCode);
		log.debug("Parents Age Size = {} " ,parentsAgeVOs.size());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(simpleDateFormat);
		LocalDateTime now = LocalDateTime.now();
		log.debug("ParentsAgeAndAgeBand Date Format :: {}" , dtf.format(now));
		String batchId = dtf.format(now);
		
		for(ProductsParentAndParentInLawAgeVO parentAgeVO : parentsAgeVOs) {
			List<String> ageList = productUtil.getAgeListInArray(parentAgeVO.getAgeBandInYears());
			List<AgeAndAgeBandVO> ageAndAgeBandVOs = new ArrayList<>();
			for(String age :  ageList) {
				AgeAndAgeBandVO ageAndAgeBandVO = new AgeAndAgeBandVO();
				ageAndAgeBandVO.setBatchId(batchId);
				ageAndAgeBandVO.setProductCode(parentAgeVO.getProductCode());
				ageAndAgeBandVO.setProductName(parentAgeVO.getProductName());
				ageAndAgeBandVO.setPeriod(parentAgeVO.getPeriod());
				ageAndAgeBandVO.setPerson(parents);
				ageAndAgeBandVO.setAge(age);
				ageAndAgeBandVO.setAgeBandInYears(parentAgeVO.getAgeBandInYears());
				ageAndAgeBandVO.setFlag("0");
				ageAndAgeBandVO.setIsActive("TRUE");
				ageAndAgeBandVOs.add(ageAndAgeBandVO);
			}
			
			ageAndAgeBandRepo.saveAll(ageAndAgeBandVOs);
		}
		
		log.debug("runParentsAgeAndAgeBand End {} " ,productCode);
		return ApplicationConstant.SUCCESS;
	}

}
