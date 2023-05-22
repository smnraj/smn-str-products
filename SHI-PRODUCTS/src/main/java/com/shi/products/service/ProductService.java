/**
 * 
 */
package com.shi.products.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.shi.products.req.GetQuoteRequest;
import com.shi.products.req.ProductsFeature;
import com.shi.products.res.Products;
import com.shi.products.res.Product;
import com.shi.products.res.ProductFeatureResponse;
import com.shi.products.res.Quotes;
import com.shi.products.res.QuotesRes;
import com.shi.products.client.req.GetQuoteSOAPRequest;
import com.shi.products.client.res.GetQuoteSOAPResponse;
import com.shi.products.dao.ProductsDAO;
import com.shi.products.exception.FaultException;
import com.shi.products.repo.GetQuoteResponseRepo;
import com.shi.products.repo.ProductColumnRepo;
import com.shi.products.repo.ProductComparisonMappingRepo;
import com.shi.products.repo.ProductPincodeRepo;
import com.shi.products.repo.ProductsRepo;
import com.shi.products.repo.SourceSystemRepo;
import com.shi.products.util.ApplicationConstant;
import com.shi.products.util.ProductSQLUtil;
import com.shi.products.util.ProductUtil;
import com.shi.products.vo.GetQuoteRequestVO;
import com.shi.products.vo.GetQuoteResponseVO;
import com.shi.products.vo.ProductColumnVO;
import com.shi.products.vo.ProductComparisonMappingVO;
import com.shi.products.vo.ProductListVO;
import com.shi.products.vo.SourceSystemVO;

/**
 * @author suman.raju
 *
 */
@Service
public class ProductService {
	private static final Logger log = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	ProductUtil productUtil;
	
	@Autowired
	ProductSQLUtil productSQLUtil;
	
	@Autowired
	ProductColumnRepo productColumnRepo;
	
	@Autowired
	ProductsRepo productsRepo;
	
	@Autowired
	ProductComparisonMappingRepo productComparisonMappingRepo;
	
	@Autowired
	Environment environment;
	
	@Autowired
	GetQuoteResponseRepo getQuoteResponseRepo;
	
	@Autowired
	ProductPincodeRepo productPincodeRepo;
	
	@Autowired
	ProductsDAO productsDAO;
	
	@Autowired
	SourceSystemRepo sourceSystemRepo;
	
	@Autowired
	GetQuoteSOAPRequest soapClient;
	
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
	
	@Value("${upfront_discount_perc}")
	private String upfrontDiscountPerc;
	
	private String success = "SUCCESS";

	private Map<String, String> addProductFeatures(ProductColumnVO productColumnVO, ProductListVO productListVO) {
		Map<String, String> productColumn = new LinkedHashMap<>();

		if(productColumnVO.getProductCode().equalsIgnoreCase("True")) {
			productColumn.put("productCode", productListVO.getProductCode());
		}
		if(productColumnVO.getBatchId().equalsIgnoreCase("True")) {
			productColumn.put("batchId", productListVO.getBatchId());
		}
		if(productColumnVO.getProductName().equalsIgnoreCase("True")) {
			productColumn.put("productName", productListVO.getProductName());
		}
		if(productColumnVO.getTypeOfCover().equalsIgnoreCase("True")) {
			productColumn.put("typeOfCover", productListVO.getTypeOfCover());
		}
		if(productColumnVO.getPlanVariants().equalsIgnoreCase("True")) {
			productColumn.put("planVariants", productListVO.getPlanVariants());
		}
		if(productColumnVO.getAgeOfEntry().equalsIgnoreCase("True")) {
			productColumn.put("ageOfEntry", productListVO.getAgeOfEntry());
		}
		if(productColumnVO.getSize().equalsIgnoreCase("True")) {
			productColumn.put("size", productListVO.getSize());
		}
		if(productColumnVO.getPreAcceptanceMedicalScreening().equalsIgnoreCase("True")) {
			productColumn.put("preAcceptanceMedicalScreening", productListVO.getPreAcceptanceMedicalScreening());
		}
		if(productColumnVO.getSumInsuredOptions().equalsIgnoreCase("True")) {
			productColumn.put("sumInsuredOptions", productListVO.getSumInsuredOptions());
		}
		if(productColumnVO.getPolicyTerm().equalsIgnoreCase("True")) {
			productColumn.put("policyTerm", productListVO.getPolicyTerm());
		}
		if(productColumnVO.getInstallmentFacility().equalsIgnoreCase("True")) {
			productColumn.put("installmentFacility", productListVO.getInstallmentFacility());
		}
		if(productColumnVO.getGracePeriod().equalsIgnoreCase("True")) {
			productColumn.put("gracePeriod", productListVO.getGracePeriod());
		}
		if(productColumnVO.getRoomRent().equalsIgnoreCase("True")) {
			productColumn.put("roomRent",productListVO.getRoomRent());
		}
		if(productColumnVO.getPreAndPostHospitalisation().equalsIgnoreCase("True")) {
			productColumn.put("preAndPostHospitalisation", productListVO.getPreAndPostHospitalisation());
		}
		if(productColumnVO.getDayCareProcedures().equalsIgnoreCase("True")) {
			productColumn.put("dayCareProcedures", productListVO.getDayCareProcedures());
		}
		if(productColumnVO.getCataract().equalsIgnoreCase("True")) {
			productColumn.put("cataract", productListVO.getCataract());
		}
		if(productColumnVO.getHealthCheckup().equalsIgnoreCase("True")) {
			productColumn.put("healthCheckup", productListVO.getHealthCheckup());
		}
		if(productColumnVO.getRoadAmbulance().equalsIgnoreCase("True")) {
			productColumn.put("roadAmbulance", productListVO.getRoadAmbulance());
		}
		if(productColumnVO.getAirAmbulance().equalsIgnoreCase("True")) {
			productColumn.put("airAmbulance", productListVO.getAirAmbulance());
		}
		if(productColumnVO.getAyushTreatment().equalsIgnoreCase("True")) {
			productColumn.put("ayushTreatment", productListVO.getAyushTreatment());
		}
		if(productColumnVO.getRenewals().equalsIgnoreCase("True")) {
			productColumn.put("renewals", productListVO.getRenewals());
		}
		if(productColumnVO.getCopayment().equalsIgnoreCase("True")) {
			productColumn.put("copayment", productListVO.getCopayment());
		}
		if(productColumnVO.getModernTreatment().equalsIgnoreCase("True")) {
			productColumn.put("modernTreatment",productListVO.getModernTreatment());
		}
		if(productColumnVO.getCummulativeBonus().equalsIgnoreCase("True")) {
			productColumn.put("cummulativeBonus", productListVO.getCummulativeBonus());
		}
		if(productColumnVO.getWaitingPeriods().equalsIgnoreCase("True")) {
			productColumn.put("waitingPeriods", productListVO.getWaitingPeriods());
		}
		if(productColumnVO.getPreExsistingDiseases().equalsIgnoreCase("True")) {
			productColumn.put("preExsistingDiseases", productListVO.getPreExsistingDiseases());
		}
		if(productColumnVO.getSpecificDiseases().equalsIgnoreCase("True")) {
			productColumn.put("specificDiseases", productListVO.getSpecificDiseases());
		}
		if(productColumnVO.getInitialWaitingPeriod().equalsIgnoreCase("True")) {
			productColumn.put("initialWaitingPeriod", productListVO.getInitialWaitingPeriod());
		}
		if(productColumnVO.getSpecialFeatures().equalsIgnoreCase("True")) {
			productColumn.put("specialFeatures", productListVO.getSpecialFeatures());
		}
		if(productColumnVO.getBrochure().equalsIgnoreCase("True")) {
			productColumn.put("brochure", productListVO.getBrochure());
		}
		if(productColumnVO.getPolicyClause().equalsIgnoreCase("True")) {
			productColumn.put("policyClause", productListVO.getPolicyClause());
		}
		
		return productColumn;
	}

	public List<Products> getProductsByCode(GetQuoteRequest getQuoteRequest) throws Exception {
		List<Products> comparedProductsList = new ArrayList<>();
		ArrayList<String> message = new ArrayList<>();
		if(StringUtils.isNotBlank(getQuoteRequest.getSourceSystem())) {
			String combination = productUtil.getCombination(getQuoteRequest);
			if(StringUtils.isNotBlank(combination)) {
				List<ProductComparisonMappingVO> productComparisonMappingVO = productComparisonMappingRepo.getProductComparisonMapping(getQuoteRequest.getProductId(), combination);
				log.debug("Mapping Product Count ={}", productComparisonMappingVO.size());
				if(CollectionUtils.isNotEmpty(productComparisonMappingVO)) {
					//Mapping Product Quotes based on product Code
					for(ProductComparisonMappingVO productComparisonMapping : productComparisonMappingVO) {
						log.debug("Mapping Product Code = {}",productComparisonMapping.getMappingProductCode());
						Products comparedProducts = getCompareProductQuote(getQuoteRequest, productComparisonMapping);
						comparedProductsList.add(comparedProducts);
					}
				}else {
					message.add("Mapping Products is Empty");
					Products products = new Products();
					products.setMessage(message);
					comparedProductsList.add(products);
				}

			}else {
				message.add("Invalid Combination");
				Products products = new Products();
				products.setMessage(message);
				comparedProductsList.add(products);
			}
		}else {
			message.add(ApplicationConstant.SOURCE_SYSTEM_EMPTY);
			Products products = new Products();
			products.setMessage(message);
			comparedProductsList.add(products);
		}
		
		return comparedProductsList;
	}

	private Products getCompareProductQuote(GetQuoteRequest getQuoteRequest, ProductComparisonMappingVO productComparisonMapping) throws Exception {
		log.debug("SearchGetQuotes Starts :: ");
		Products comparedProducts;
		
		String prefixName = getQuoteRequest.getSourceSystem().trim();
		SourceSystemVO sourceSystemVO = sourceSystemRepo.getSourceSystem(getQuoteRequest.getSourceSystem().trim(), productComparisonMapping.getMappingProductCode().trim(), getQuoteRequest.getDivisonCode());
		if(sourceSystemVO!=null) {
			log.debug("Compare Product Source System = {}", sourceSystemVO);
			prefixName = sourceSystemVO.getPrefixName().trim();
			
			//Online Discount
			if(StringUtils.isNotBlank(sourceSystemVO.getOnlineDiscountPerc())) {
				getQuoteRequest.setOnlineDiscountPerc(sourceSystemVO.getOnlineDiscountPerc());
			}else {
				getQuoteRequest.setOnlineDiscountPerc("");
			}
		}
		
		boolean onDemand = productSQLUtil.checkOnDemand(productComparisonMapping.getMappingProductCode(), getQuoteRequest);
		
		if(onDemand) {
			comparedProducts = getProductQuotesFromCore(getQuoteRequest, productComparisonMapping, prefixName);
		}else {
			comparedProducts = getProductQuotes(getQuoteRequest, productComparisonMapping, prefixName);
		}

		log.debug("SearchGetQuotes End :: ");
		return comparedProducts;
	}
	
	private Products getProductQuotesFromCore(GetQuoteRequest getQuoteRequest, ProductComparisonMappingVO productComparisonMapping, String prefixName) throws Exception {
		long startTime = System.currentTimeMillis();
		Products comparedProducts = new Products();
		List<Quotes> quotes = new ArrayList<>();
		Product product = new Product();
		List<String> message = new ArrayList<>();
		boolean haveProduct = false;
		
		GetQuoteRequestVO getQuoteRequestVO = productSQLUtil.buildSOAPRequest(getQuoteRequest, productComparisonMapping.getMappingProductCode().trim());
		GetQuoteSOAPResponse getQuoteResponse = soapClient.getQuoteOnDemand(getQuoteRequestVO);
		if (StringUtils.isNotBlank(getQuoteResponse.getPremiumAmount())) {
			haveProduct = true;
			Quotes quote = addGetQuoteResponseFromCore(getQuoteRequestVO, getQuoteResponse, prefixName);
			if(StringUtils.isNotBlank(getQuoteRequest.getUpfrontDiscountYN()) && StringUtils.equalsIgnoreCase(getQuoteRequest.getUpfrontDiscountYN(), ApplicationConstant.Y)) {
				quote = productUtil.calculateUpfronDiscountOnDemand(quote);
			}
			quotes.add(quote);
		}else {
			if(StringUtils.isNotBlank(getQuoteResponse.getMessage())) {
				message.add(getQuoteResponse.getMessage());
			}else {
				message.add(ApplicationConstant.INVALID_SOAP_REQ);
			}
		}
		
		product.setProductCode(productComparisonMapping.getMappingProductCode());
		product.setProductName(getQuoteRequestVO.getProductName());
		
		long endTime = System.currentTimeMillis();
		float sec = (endTime - startTime) / 1000F;
		log.debug("ProductQuotesFromCore Ends in = {} seconds / {} milliseconds",sec,(endTime - startTime));
		
		if(haveProduct) {
			comparedProducts.setSequence(productComparisonMapping.getSequence());
			comparedProducts.setProduct(product);
			comparedProducts.setQuotes(quotes);
		}else {
			product.setProductCode(productComparisonMapping.getMappingProductCode());
			comparedProducts.setProduct(product);
		}
		comparedProducts.setMessage(message);
		
		return comparedProducts;
	}


	private Products getProductQuotes(GetQuoteRequest getQuoteRequest, ProductComparisonMappingVO productComparisonMapping, String prefixName) throws Exception {
		Products comparedProducts = new Products();
		ArrayList<Quotes> quotes = new ArrayList<>();
		Product product = new Product();
		List<String> message = new ArrayList<>();
		String sql = "";
		boolean haveProduct = false;
		
		if(productComparisonMapping.getMappingProductCode().trim().equalsIgnoreCase(mediClassicProductCode)) {

			List<String> validation = productSQLUtil.buildMediClassicQuery(getQuoteRequest, productComparisonMapping.getMappingProductCode().trim());
			if(validation.get(0).contains(ApplicationConstant.SELECT)) {
				sql = validation.get(0);
				List<GetQuoteResponseVO> getQuoteResponseVOs = productsDAO.getQuoteResponse(sql);
				log.debug("Medi Classic Result {}",getQuoteResponseVOs.size());
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					Quotes getQuickQuote = addGetQuoteResponse(getQuoteResponseVO, prefixName);
					quotes.add(getQuickQuote);
				}
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					product = addGetQuoteProduct(getQuoteResponseVO);
					haveProduct = true;
					message.add(success);
				}
			}else {
				message = validation;
				log.error("ErrorMessage MediClassic --> {}",message);
			}
			
		}else if(productComparisonMapping.getMappingProductCode().trim().equalsIgnoreCase(familyHealthOptimaProductCode)) {
			List<String> validation = productSQLUtil.buildFHOQuery(getQuoteRequest, productComparisonMapping.getMappingProductCode().trim());
			if(validation.get(0).contains(ApplicationConstant.SELECT)) {
				sql = validation.get(0);
				List<GetQuoteResponseVO> getQuoteResponseVOs = productsDAO.getQuoteResponse(sql);
				log.debug("Family Health Optima Result {}",getQuoteResponseVOs.size());
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					Quotes getQuickQuote = addGetQuoteResponse(getQuoteResponseVO, prefixName);
					quotes.add(getQuickQuote);
				}
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					product = addGetQuoteProduct(getQuoteResponseVO);
					haveProduct = true;
					message.add(success);
				}
			}else {
				message = validation;
				log.error("ErrorMessage FHO --> {}",message);
			}
			
		}else if(productComparisonMapping.getMappingProductCode().trim().equalsIgnoreCase(youngStarProductCode)) {
			List<String> validation = productSQLUtil.buildYoungStarQuery(getQuoteRequest, productComparisonMapping.getMappingProductCode().trim());
			if(validation.get(0).contains(ApplicationConstant.SELECT)) {
				sql = validation.get(0);
				List<GetQuoteResponseVO> getQuoteResponseVOs = productsDAO.getQuoteResponse(sql);
				log.debug("Young Star Result {} ",getQuoteResponseVOs.size());
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					Quotes getQuickQuote = addGetQuoteResponse(getQuoteResponseVO, prefixName);
					quotes.add(getQuickQuote);
				}
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					product = addGetQuoteProduct(getQuoteResponseVO);
					haveProduct = true;
					message.add(success);
				}
			}else {
				message = validation;
				log.error("ErrorMessage YoungStar {}",message);
			}
		}else if(productComparisonMapping.getMappingProductCode().trim().equalsIgnoreCase(starComprehensiveProductCode)) {
			List<String> validation = productSQLUtil.buildStarComprehensiveQuery(getQuoteRequest, productComparisonMapping.getMappingProductCode().trim());
			if(validation.get(0).contains(ApplicationConstant.SELECT)) {
				sql = validation.get(0);
				List<GetQuoteResponseVO> getQuoteResponseVOs = productsDAO.getQuoteResponse(sql);
				log.debug("Star Comprehensive Result {} ",getQuoteResponseVOs.size());
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					Quotes getQuickQuote = addGetQuoteResponse(getQuoteResponseVO, prefixName);
					quotes.add(getQuickQuote);
				}
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					product = addGetQuoteProduct(getQuoteResponseVO);
					haveProduct = true;
					message.add(success);
				}
			}else {
				message = validation;
				log.error("ErrorMessage Star Comprehensive {}",message);
			}
		}else if(productComparisonMapping.getMappingProductCode().trim().equalsIgnoreCase(arogyaSanjeevaniProductCode)) {
			List<String> validation = productSQLUtil.buildArogyaSanjeevaniQuery(getQuoteRequest, productComparisonMapping.getMappingProductCode().trim());
			if(validation.get(0).contains(ApplicationConstant.SELECT)) {
				sql = validation.get(0);
				List<GetQuoteResponseVO> getQuoteResponseVOs = productsDAO.getQuoteResponse(sql);
				log.debug("Arogya Sanjeevani Result {} ",getQuoteResponseVOs.size());
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					Quotes getQuickQuote = addGetQuoteResponse(getQuoteResponseVO, prefixName);
					quotes.add(getQuickQuote);
				}
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					product = addGetQuoteProduct(getQuoteResponseVO);
					haveProduct = true;
					message.add(success);
				}
			}else {
				message = validation;
				log.error("ErrorMessage Arogya Sanjeevani {}",message);
			}
		}else if(productComparisonMapping.getMappingProductCode().trim().equalsIgnoreCase(superSurplusIndividualProductCode)) {
			List<String> validation = productSQLUtil.buildSuperSurplusIndividualQuery(getQuoteRequest, productComparisonMapping.getMappingProductCode().trim());
			if(validation.get(0).contains(ApplicationConstant.SELECT)) {
				sql = validation.get(0);
				List<GetQuoteResponseVO> getQuoteResponseVOs = productsDAO.getQuoteResponse(sql);
				log.debug("Super surplus individual Result {} ",getQuoteResponseVOs.size());
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					Quotes getQuickQuote = addGetQuoteResponse(getQuoteResponseVO, prefixName);
					quotes.add(getQuickQuote);
				}
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					product = addGetQuoteProduct(getQuoteResponseVO);
					haveProduct = true;
					message.add(success);
				}
			}else {
				message = validation;
				log.error("ErrorMessage Super surplus individual {}",message);
			}
		}else if(productComparisonMapping.getMappingProductCode().trim().equalsIgnoreCase(superSurplusFloaterProductCode)) {
			List<String> validation = productSQLUtil.buildSuperSurplusFloaterQuery(getQuoteRequest, productComparisonMapping.getMappingProductCode().trim());
			if(validation.get(0).contains(ApplicationConstant.SELECT)) {
				sql = validation.get(0);
				List<GetQuoteResponseVO> getQuoteResponseVOs = productsDAO.getQuoteResponse(sql);
				log.debug("Super surplus floater Result {} ",getQuoteResponseVOs.size());
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					Quotes getQuickQuote = addGetQuoteResponse(getQuoteResponseVO, prefixName);
					quotes.add(getQuickQuote);
				}
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					product = addGetQuoteProduct(getQuoteResponseVO);
					haveProduct = true;
					message.add(success);
				}
			}else {
				message = validation;
				log.error("ErrorMessage Super surplus floater {}",message);
			}
		}else if(productComparisonMapping.getMappingProductCode().trim().equalsIgnoreCase(starWomenCareProductCode)) {
			List<String> validation = productSQLUtil.buildWomenCareQuery(getQuoteRequest, productComparisonMapping.getMappingProductCode().trim());
			if(validation.get(0).contains(ApplicationConstant.SELECT)) {
				sql = validation.get(0);
				List<GetQuoteResponseVO> getQuoteResponseVOs = productsDAO.getQuoteResponse(sql);
				log.debug("Star Women Care Result {} ",getQuoteResponseVOs.size());
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					Quotes getQuickQuote = addGetQuoteResponse(getQuoteResponseVO, prefixName);
					quotes.add(getQuickQuote);
				}
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					product = addGetQuoteProduct(getQuoteResponseVO);
					haveProduct = true;
					message.add(success);
				}
			}else {
				message = validation;
				log.error("ErrorMessage Women Care {}",message);
			}
		}else if(productComparisonMapping.getMappingProductCode().trim().equalsIgnoreCase(healthPremierProductCode)) {
			List<String> validation = productSQLUtil.buildHealthPremierQuery(getQuoteRequest, productComparisonMapping.getMappingProductCode().trim());
			if(validation.get(0).contains(ApplicationConstant.SELECT)) {
				sql = validation.get(0);
				List<GetQuoteResponseVO> getQuoteResponseVOs = productsDAO.getQuoteResponse(sql);
				log.debug("Health Premier Result {} ",getQuoteResponseVOs.size());
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					Quotes getQuickQuote = addGetQuoteResponse(getQuoteResponseVO, prefixName);
					quotes.add(getQuickQuote);
				}
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					product = addGetQuoteProduct(getQuoteResponseVO);
					haveProduct = true;
					message.add(success);
				}
			}else {
				message = validation;
				log.error("ErrorMessage Health Premier {}",message);
			}
		}else if(productComparisonMapping.getMappingProductCode().trim().equalsIgnoreCase(healthAssureProductCode)) {
			List<String> validation = productSQLUtil.buildHealthAssureQuery(getQuoteRequest, productComparisonMapping.getMappingProductCode().trim());
			if(validation.get(0).contains(ApplicationConstant.SELECT)) {
				sql = validation.get(0);
				List<GetQuoteResponseVO> getQuoteResponseVOs = productsDAO.getQuoteResponse(sql);
				log.debug("Health Assure Result {} ",getQuoteResponseVOs.size());
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					Quotes getQuickQuote = addGetQuoteResponse(getQuoteResponseVO, prefixName);
					quotes.add(getQuickQuote);
				}
				for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
					product = addGetQuoteProduct(getQuoteResponseVO);
					haveProduct = true;
					message.add(success);
				}
			}else {
				message = validation;
				log.error("ErrorMessage Health Assure {}",message);
			}
		}else {
			message.add("Not Implemented for this Product");
		}
		
		if(haveProduct) {
			comparedProducts.setSequence(productComparisonMapping.getSequence());
			comparedProducts.setProduct(product);
			comparedProducts.setQuotes(quotes);
		}else {
			product.setProductCode(productComparisonMapping.getMappingProductCode());
			comparedProducts.setProduct(product);
		}
		comparedProducts.setMessage(message);

		return comparedProducts;
	}

	private Product addGetQuoteProduct(GetQuoteResponseVO getQuoteResponseVO) {
		Product product = new Product();
		product.setProductCode(getQuoteResponseVO.getProductCode());
		product.setProductName(getQuoteResponseVO.getProductName());
		return product;
	}
	
	private Quotes addGetQuoteResponseFromCore(GetQuoteRequestVO getQuoteRequestVO, GetQuoteSOAPResponse getQuoteResponse, String prefixName) {
		log.debug("Add Get Quote Response Starts");
		Quotes quote = new Quotes();
		long getQuoteResponseId = 0;
		String quoteId = productsDAO.quotesAudit(getQuoteRequestVO.getProductCode(), "", prefixName, getQuoteResponseId);
		
		if(StringUtils.isNotBlank(quoteId)) {
			quote.setQuoteId(quoteId);
		}
		if(StringUtils.isNotBlank(getQuoteRequestVO.getProductCode())) {
			quote.setProductCode(getQuoteRequestVO.getProductCode());
		}
		if(StringUtils.isNotBlank(getQuoteRequestVO.getProductName())) {
			quote.setProductName(getQuoteRequestVO.getProductName());
		}
		if(StringUtils.isNotBlank(getQuoteRequestVO.getInsuranceCover())) {
			quote.setInsuranceCover(Integer.parseInt(getQuoteRequestVO.getInsuranceCover()));
		}
		if(StringUtils.isNotBlank(getQuoteRequestVO.getDeduction())) {
			quote.setDeduction(Integer.parseInt(getQuoteRequestVO.getDeduction()));
		}
		if(StringUtils.isNotBlank(getQuoteRequestVO.getPinCode())) {
			quote.setPinCode(Integer.parseInt(getQuoteRequestVO.getPinCode()));
		}
		if(StringUtils.isNotBlank(getQuoteRequestVO.getPeriod())) {
			quote.setPeriod(getQuoteRequestVO.getPeriod());
		}
		if(StringUtils.isNotBlank(getQuoteRequestVO.getBuyBackPedYN())) {
			quote.setBuyBackPedYN(getQuoteRequestVO.getBuyBackPedYN());
		}
		if(StringUtils.isNotBlank(getQuoteRequestVO.getOnlineDiscountPerc())) {
			quote.setOnlineDiscountPerc(Integer.parseInt(getQuoteRequestVO.getOnlineDiscountPerc()));
		}
		if(StringUtils.isNotBlank(getQuoteRequestVO.getPlan())) {
			quote.setPlan(getQuoteRequestVO.getPlan());
		}
		
		if (StringUtils.isNotBlank(getQuoteResponse.getPremiumAmount())) {
			quote.setPremiumAmount(Integer.parseInt(getQuoteResponse.getPremiumAmount()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getPremiumTax())) {
			quote.setPremiumTax(Integer.parseInt(getQuoteResponse.getPremiumTax()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getTotalAmount())) {
			quote.setTotalAmount(Integer.parseInt(getQuoteResponse.getTotalAmount()));
		}
		quote.setMessage(getQuoteResponse.getMessage());
		if (StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntFirstPrem())) {
			quote.setHlflyPremAmntFirstPrem(Integer.parseInt(getQuoteResponse.getHlflyPremAmntFirstPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntFirstTax())) {
			quote.setHlflyPremAmntFirstTax(Integer.parseInt(getQuoteResponse.getHlflyPremAmntFirstTax()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntFirstTotal())) {
			quote.setHlflyPremAmntFirstTotal(Integer.parseInt(getQuoteResponse.getHlflyPremAmntFirstTotal()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntOthersPrem())) {
			quote.setHlflyPremAmntOthersPrem(Integer.parseInt(getQuoteResponse.getHlflyPremAmntOthersPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntOthersTax())) {
			quote.setHlflyPremAmntOthersTax(Integer.parseInt(getQuoteResponse.getHlflyPremAmntOthersTax()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntOthersTotal())) {
			quote.setHlflyPremAmntOthersTotal(Integer.parseInt(getQuoteResponse.getHlflyPremAmntOthersTotal()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntFirstPrem())) {
			quote.setQtrlyPremAmntFirstPrem(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntFirstPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntFirstTax())) {
			quote.setQtrlyPremAmntFirstTax(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntFirstTax()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntFirstTotal())) {
			quote.setQtrlyPremAmntFirstTotal(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntFirstTotal()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntOthersPrem())) {
			quote.setQtrlyPremAmntOthersPrem(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntOthersPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntOthersTax())) {
			quote.setQtrlyPremAmntOthersTax(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntOthersTax()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntOthersTotal())) {
			quote.setQtrlyPremAmntOthersTotal(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntOthersTotal()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntFirstPrem())) {
			quote.setMnthlyPremAmntFirstPrem(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntFirstPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntFirstTax())) {
			quote.setMnthlyPremAmntFirstTax(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntFirstTax()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntFirstTotal())) {
			quote.setMnthlyPremAmntFirstTotal(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntFirstTotal()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntOthersPrem())) {
			quote.setMnthlyPremAmntOthersPrem(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntOthersPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntOthersTax())) {
			quote.setMnthlyPremAmntOthersTax(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntOthersTax()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntOthersTotal())) {
			quote.setMnthlyPremAmntOthersTotal(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntOthersTotal()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getTotalRuralDiscountAmt())) {
			quote.setTotalRuralDiscountAmt(Integer.parseInt(getQuoteResponse.getTotalRuralDiscountAmt()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getTotalOnlineDiscountAmt())) {
			quote.setTotalOnlineDiscountAmt(Integer.parseInt(getQuoteResponse.getTotalOnlineDiscountAmt()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getTotalAddDiscnt())) {
			quote.setTotalAddDiscnt(Integer.parseInt(getQuoteResponse.getTotalAddDiscnt()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getBuyBackPrem())) {
			quote.setBuyBackPrem(Integer.parseInt(getQuoteResponse.getBuyBackPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getHospitalCashPrem())) {
			quote.setHospitalCashPrem(Integer.parseInt(getQuoteResponse.getHospitalCashPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getPatientCarePrem())) {
			quote.setPatientCarePrem(Integer.parseInt(getQuoteResponse.getPatientCarePrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getAddOnCvrSec1Prem())) {
			quote.setAddOnCvrSec1Prem(Integer.parseInt(getQuoteResponse.getAddOnCvrSec1Prem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getAddOnCvrSec2Prem())) {
			quote.setAddOnCvrSec2Prem(Integer.parseInt(getQuoteResponse.getAddOnCvrSec2Prem()));
		}
		log.debug("Add Get Quote Response End");
		return quote;
	}

	private Quotes addGetQuoteResponse(GetQuoteResponseVO getQuoteResponseVO, String prefixName) {
		log.debug("Adding Get Quote Results ");
		String quoteId = productsDAO.quotesAudit(getQuoteResponseVO.getProductCode().trim(), getQuoteResponseVO.getProductName().trim(), prefixName, getQuoteResponseVO.getId());
		Quotes quotes = new Quotes();
		quotes.setQuoteId(quoteId);
		quotes.setProductCode(getQuoteResponseVO.getProductCode());
		quotes.setProductName(getQuoteResponseVO.getProductName());
		quotes.setInsuranceCover(getQuoteResponseVO.getInsuranceCover());
		quotes.setDeduction(getQuoteResponseVO.getDeduction());
		quotes.setZone(getQuoteResponseVO.getZone());
		quotes.setPeriod(getQuoteResponseVO.getPeriod());
		quotes.setBuyBackPedYN(getQuoteResponseVO.getBuyBackPedYN());
		quotes.setOnlineDiscountPerc(getQuoteResponseVO.getOnlineDiscountPerc());
		quotes.setPlan(getQuoteResponseVO.getPlan());
		quotes.setAgeBandInYears(getQuoteResponseVO.getAdult1AgeBandInYears());
		quotes.setSchemeCode(getQuoteResponseVO.getSchemeCode());
		quotes.setPremiumAmount(getQuoteResponseVO.getPremiumAmount());
		quotes.setPremiumTax(getQuoteResponseVO.getPremiumTax());
		quotes.setTotalAmount(getQuoteResponseVO.getTotalAmount());
		quotes.setHlflyPremAmntFirstPrem(getQuoteResponseVO.getHlflyPremAmntFirstPrem());
		quotes.setHlflyPremAmntFirstTax(getQuoteResponseVO.getHlflyPremAmntFirstTax());
		quotes.setHlflyPremAmntFirstTotal(getQuoteResponseVO.getHlflyPremAmntFirstTotal());
		quotes.setHlflyPremAmntOthersPrem(getQuoteResponseVO.getHlflyPremAmntOthersPrem());
		quotes.setHlflyPremAmntOthersTax(getQuoteResponseVO.getHlflyPremAmntOthersTax());
		quotes.setHlflyPremAmntOthersTotal(getQuoteResponseVO.getHlflyPremAmntOthersTotal());
		quotes.setQtrlyPremAmntFirstPrem(getQuoteResponseVO.getQtrlyPremAmntFirstPrem());
		quotes.setQtrlyPremAmntFirstTax(getQuoteResponseVO.getQtrlyPremAmntFirstTax());
		quotes.setQtrlyPremAmntFirstTotal(getQuoteResponseVO.getQtrlyPremAmntFirstTotal());
		quotes.setQtrlyPremAmntOthersPrem(getQuoteResponseVO.getQtrlyPremAmntOthersPrem());
		quotes.setQtrlyPremAmntOthersTax(getQuoteResponseVO.getQtrlyPremAmntOthersTax());
		quotes.setQtrlyPremAmntOthersTax(getQuoteResponseVO.getQtrlyPremAmntOthersTax());
		quotes.setMnthlyPremAmntFirstPrem(getQuoteResponseVO.getMnthlyPremAmntFirstPrem());
		quotes.setMnthlyPremAmntFirstTax(getQuoteResponseVO.getMnthlyPremAmntFirstTax());
		quotes.setMnthlyPremAmntFirstTotal(getQuoteResponseVO.getMnthlyPremAmntFirstTotal());
		quotes.setMnthlyPremAmntOthersPrem(getQuoteResponseVO.getMnthlyPremAmntOthersPrem());
		quotes.setMnthlyPremAmntOthersTax(getQuoteResponseVO.getMnthlyPremAmntOthersTax());
		quotes.setMnthlyPremAmntOthersTotal(getQuoteResponseVO.getMnthlyPremAmntOthersTotal());
		quotes.setTotalRuralDiscountAmt(getQuoteResponseVO.getTotalRuralDiscountAmt());
		quotes.setTotalOnlineDiscountAmt(getQuoteResponseVO.getTotalOnlineDiscountAmt());
		quotes.setTotalAddDiscnt(getQuoteResponseVO.getTotalAddDiscnt());
		quotes.setBuyBackPrem(getQuoteResponseVO.getBuyBackPrem());
		quotes.setHospitalCashPrem(getQuoteResponseVO.getHospitalCashPrem());
		quotes.setPatientCarePrem(getQuoteResponseVO.getPatientCarePrem());
		quotes.setAddOnCvrSec1Prem(getQuoteResponseVO.getAddOnCvrSec1Prem());
		quotes.setAddOnCvrSec2Prem(getQuoteResponseVO.getAddOnCvrSec2Prem());
		quotes.setSaving2Year(getQuoteResponseVO.getSaving2Year());
		quotes.setSaving3Year(getQuoteResponseVO.getSaving3Year());
		return quotes;
	}
	

	public ProductFeatureResponse getProductFeatures(List<ProductsFeature> productsFeature) {
		log.debug("Product Features");
		ProductFeatureResponse productFeatureRes = new ProductFeatureResponse();
		List<Map<String, String>> productFeatures = new ArrayList<>();
		List<ProductListVO> productListVO = new ArrayList<>();
		ProductColumnVO productColumnVO = productColumnRepo.getProductColumn();
		for(ProductsFeature productFeature : productsFeature) {
			if(StringUtils.isNotBlank(productFeature.getProductCode()) && StringUtils.isNotBlank(productFeature.getPlanVariants())) {
				productListVO = productsRepo.getProductInfoByPlan(productFeature.getProductCode().trim(), productFeature.getPlanVariants().trim());
			}else if(StringUtils.isNotBlank(productFeature.getProductCode()) && StringUtils.isNotBlank(productFeature.getBuyBackPedYN())) {
				productListVO = productsRepo.getProductInfoByPlan(productFeature.getProductCode().trim(), productFeature.getBuyBackPedYN().trim());
			}else if(StringUtils.isNotBlank(productFeature.getProductCode())) {
				productListVO = productsRepo.getProductInfo(productFeature.getProductCode().trim());
			}			
			if(CollectionUtils.isNotEmpty(productListVO)) {
				for(ProductListVO productList : productListVO) {
					Map<String, String> feature = addProductFeatures(productColumnVO, productList);
					productFeatures.add(feature);
				}
			}else {
				Map<String, String> message = new LinkedHashMap<>();
				message.put(productFeature.getProductCode(), "Features Not Found");
				productFeatures.add(message);
			}
			
		}
		productFeatureRes.setProductFeatures(productFeatures);
		return productFeatureRes;
	}
	
	public QuotesRes getQuotes(GetQuoteRequest getQuoteRequest) throws Exception, FaultException {
		QuotesRes quotesRes = new QuotesRes();
		List<Quotes> quotes = new ArrayList<>();
		ArrayList<String> message = new ArrayList<>();
		if(StringUtils.isNotBlank(getQuoteRequest.getSourceSystem())) {
			String prefixName = getQuoteRequest.getSourceSystem().trim();
			SourceSystemVO sourceSystemVO = sourceSystemRepo.getSourceSystem(getQuoteRequest.getSourceSystem().trim(), getQuoteRequest.getProductId().trim(), getQuoteRequest.getDivisonCode());
			if(sourceSystemVO!=null) {
				log.debug("GetQuotes Source System = {}" ,sourceSystemVO);
				prefixName = sourceSystemVO.getPrefixName().trim();
				if(StringUtils.isNotBlank(sourceSystemVO.getOnlineDiscountPerc())) {
					getQuoteRequest.setOnlineDiscountPerc(sourceSystemVO.getOnlineDiscountPerc());
				}else {
					getQuoteRequest.setOnlineDiscountPerc("");
				}
			}
			
			boolean onDemand = productSQLUtil.checkOnDemand(getQuoteRequest.getProductId(), getQuoteRequest);
			
			if(onDemand) {
				GetQuoteRequestVO getQuoteRequestVO = productSQLUtil.buildSOAPRequest(getQuoteRequest, getQuoteRequest.getProductId());
				GetQuoteSOAPResponse getQuoteResponse = soapClient.getQuoteOnDemand(getQuoteRequestVO);
				if (StringUtils.isNotBlank(getQuoteResponse.getPremiumAmount())) {
					Quotes quote = addGetQuoteResponseFromCore(getQuoteRequestVO, getQuoteResponse, prefixName);
					if(StringUtils.isNotBlank(getQuoteRequest.getUpfrontDiscountYN()) && StringUtils.equalsIgnoreCase(getQuoteRequest.getUpfrontDiscountYN(), ApplicationConstant.Y)) {
						quote = productUtil.calculateUpfronDiscountOnDemand(quote);
					}
					quotes.add(quote);
					message.add(success);
					quotesRes.setQuotes(quotes);
					quotesRes.setMessage(message);
				}else {
					if(StringUtils.isNotBlank(getQuoteResponse.getMessage())) {
						message.add(getQuoteResponse.getMessage());
					}else {
						message.add(ApplicationConstant.INVALID_SOAP_REQ);
					}
				}
			}else {
				List<String> validation = productSQLUtil.buildGetQuoteQuery(getQuoteRequest);
				if(validation.get(0).contains(ApplicationConstant.SELECT)) {
					String sql = validation.get(0);
					List<GetQuoteResponseVO> getQuoteResponseVOs = productsDAO.getQuoteResponse(sql);
					log.debug("Get All Quote Result {} ",getQuoteResponseVOs.size());
					if(CollectionUtils.isNotEmpty(getQuoteResponseVOs)) {
						for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
							Quotes quote = addGetQuoteResponse(getQuoteResponseVO, prefixName);
							quotes.add(quote);
						}
						message.add(success);
						quotesRes.setQuotes(quotes);
						quotesRes.setMessage(message);
					}else {
						message.add("No Data Found");
						quotesRes.setMessage(message);
					}
				}else {
					quotesRes.setMessage(validation);
				}
			}
		}else {
			message.add(ApplicationConstant.SOURCE_SYSTEM_EMPTY);
			quotesRes.setMessage(message);
		}
		
		return quotesRes;
	}

	public QuotesRes getAllQuotes(GetQuoteRequest getQuoteRequest) throws Exception, FaultException {
		QuotesRes quotesRes = new QuotesRes();
		List<Quotes> quotes = new ArrayList<Quotes>();
		ArrayList<String> message = new ArrayList<>();
		if(StringUtils.isNotBlank(getQuoteRequest.getSourceSystem())) {
			String prefixName = getQuoteRequest.getSourceSystem().trim();
			SourceSystemVO sourceSystemVO = sourceSystemRepo.getSourceSystem(getQuoteRequest.getSourceSystem().trim(), getQuoteRequest.getProductId().trim(), getQuoteRequest.getDivisonCode());
			if(sourceSystemVO!=null) {
				log.debug("GetAllQuotes Source System = {}",sourceSystemVO);
				prefixName = sourceSystemVO.getPrefixName().trim();
				// Online Discount 
				if(StringUtils.isNotBlank(sourceSystemVO.getOnlineDiscountPerc())) {
					getQuoteRequest.setOnlineDiscountPerc(sourceSystemVO.getOnlineDiscountPerc());
				}else {
					getQuoteRequest.setOnlineDiscountPerc("");
				}
			}
			
			boolean onDemand = productSQLUtil.checkOnDemand(getQuoteRequest.getProductId(), getQuoteRequest);
			
			if(onDemand) {
				List<GetQuoteRequestVO> getQuoteRequestVOs = productSQLUtil.buildAllSOAPRequest(getQuoteRequest, getQuoteRequest.getProductId());
				for(GetQuoteRequestVO getQuoteRequestVO : getQuoteRequestVOs) {
					
					GetQuoteSOAPResponse getQuoteResponse = soapClient.getQuoteOnDemand(getQuoteRequestVO);
					
					if (StringUtils.isNotBlank(getQuoteResponse.getPremiumAmount())) {
						Quotes quote = addGetQuoteResponseFromCore(getQuoteRequestVO, getQuoteResponse, prefixName);
						if(StringUtils.isNotBlank(getQuoteRequest.getUpfrontDiscountYN()) && StringUtils.equalsIgnoreCase(getQuoteRequest.getUpfrontDiscountYN(), ApplicationConstant.Y)) {
							quote = productUtil.calculateUpfronDiscountOnDemand(quote);
						}
						quotes.add(quote);
						message.add(success);
						quotesRes.setQuotes(quotes);
						quotesRes.setMessage(message);
					}else {
						if(StringUtils.isNotBlank(getQuoteResponse.getMessage())) {
							message.add(getQuoteResponse.getMessage());
						}else {
							message.add(ApplicationConstant.INVALID_SOAP_REQ);
						}
					}
				}
			}else {
				List<String> validation = productSQLUtil.buildGetAllQuoteQuery(getQuoteRequest);
				if(validation.get(0).contains(ApplicationConstant.SELECT)) {
					String sql = validation.get(0);
					List<GetQuoteResponseVO> getQuoteResponseVOs = productsDAO.getQuoteResponse(sql);
					log.debug("Get All Quote Result {} ",getQuoteResponseVOs.size());
					if(CollectionUtils.isNotEmpty(getQuoteResponseVOs)) {
						for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOs) {
							Quotes quote = addGetQuoteResponse(getQuoteResponseVO, prefixName);
							quotes.add(quote);
						}
						message.add(success);
						quotesRes.setQuotes(quotes);
						quotesRes.setMessage(message);
					}else {
						message.add("No Data Found");
						quotesRes.setMessage(message);
					}
				}else {
					quotesRes.setMessage(validation);
				}
			}
		}else {
			message.add(ApplicationConstant.SOURCE_SYSTEM_EMPTY);
			quotesRes.setMessage(message);
		}
		
		return quotesRes;
	}

}
