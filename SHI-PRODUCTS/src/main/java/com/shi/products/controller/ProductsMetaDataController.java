/**
 * 
 */
package com.shi.products.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shi.products.exception.FaultException;
import com.shi.products.service.ProductBatchService;
import com.shi.products.service.ProductsBatchService;
import com.shi.products.service.ProductsMetaDataService;
import com.shi.products.service.ProductsUpFrontDiscountService;
import com.shi.products.util.ApplicationConstant;
import com.shi.products.util.EncryptionDecryptionAES;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * @author suman.raju
 *
 */
@RestController
@RequestMapping("/Meta-Data")
public class ProductsMetaDataController {
	
	private static final Logger log = LoggerFactory.getLogger(ProductsMetaDataController.class);
	
	@Autowired
	Environment environment;
	
	@Autowired
	ProductsMetaDataService  productsMetaDataService;
	
	@Autowired
	ProductsBatchService productsBatchService;
	
	@Autowired
	ProductBatchService productBatchService;
	
	@Autowired
	ProductsUpFrontDiscountService productsUpFrontDiscountService;
	
	@Value("${product_upload_key}")
	private String productKey;
	
	@Value("${Adults}")
	private String adults;
	
	@Value("${Parents}")
	private String parents;
	
	@Value("${Child}")
	private String child;
	
	@Operation(summary = "Upload the Product Plans", description = "List of Product Plans", tags="MetaData")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Uploaded the Product Pllans Successfully", content = @Content),
	  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content) })
	@PostMapping(value = "/ProductsMetaData", consumes = {"multipart/form-data"})
	ResponseEntity<String> uploadProductFamilyCombination(@RequestParam("file") MultipartFile file, @RequestHeader(name = "key") String key)
			throws FaultException, IOException {
		log.info("uploadProductMetaData Starts ");
		if (key.equalsIgnoreCase(EncryptionDecryptionAES.decrypt(productKey))) {
			try {
				log.debug("File Name :: {}" , file.getOriginalFilename());
				XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
				
				XSSFSheet familyCombination = workbook.getSheetAt(0);
				productsMetaDataService.uploadProductsFamilyCombination(familyCombination);
				
				XSSFSheet sumInsure = workbook.getSheetAt(1);
				productsMetaDataService.uploadProductsSumInsure(sumInsure);
				
				XSSFSheet adultAgeBandInYears = workbook.getSheetAt(2);
				productsMetaDataService.uploadProductsAdultAgeBandInYears(adultAgeBandInYears);
				
				XSSFSheet childAgeBandInYears = workbook.getSheetAt(3);
				productsMetaDataService.uploadProductsChildAgeBandInYears(childAgeBandInYears);
				
				XSSFSheet parentAgeBandInYears = workbook.getSheetAt(4);
				productsMetaDataService.uploadProductsParentAndParentInLawAgeBandInYears(parentAgeBandInYears);

				workbook.close();
				return new ResponseEntity<>("Products uploaded......", HttpStatus.ACCEPTED);
			} catch (FaultException msg) {
				return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_ACCEPTABLE);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}

		} else {
			return new ResponseEntity<>(ApplicationConstant.VALIDKEY, HttpStatus.NOT_ACCEPTABLE);
		}
		log.info("Upload Products End ::");
		return new ResponseEntity<>(" Meta Data uploaded......", HttpStatus.ACCEPTED);
	}
	
	@Operation(summary = "Build Get Quote Request For Products Plans", description = "Build Get Quote Request Get Quotes For Product Plans", tags="MetaData-GetQuotes")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Success", content = @Content),
	  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content) })
	@GetMapping(value = "/BuildGetQuoteRequest")
	@ResponseBody
	public ResponseEntity<String> buildGetQuoteRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestHeader( name = "productCode") String productCode, @RequestHeader(name = "key") String key) throws Exception, FaultException {
		log.debug("Run BuildGetQuoteRequest Starts ::" );
		long startTime = System.currentTimeMillis();
		String status ;
		if (key.equalsIgnoreCase(EncryptionDecryptionAES.decrypt(productKey))) {
			if(StringUtils.isNotBlank(productCode)) {
				log.debug("BuildGetQuoteRequest Product Code = {}",productCode);
				status = productsMetaDataService.buildGetQuoteRequest(productCode.trim());
			}else {
				return new ResponseEntity<>(ApplicationConstant.PRODUCTCODE, HttpStatus.NOT_ACCEPTABLE);
			}
			
		}else {
			return new ResponseEntity<>(ApplicationConstant.VALIDKEY, HttpStatus.NOT_ACCEPTABLE);
		}
		
		long endTime = System.currentTimeMillis();
		float sec = (endTime - startTime) / 1000F;
		float milliSec=sec/(endTime - startTime);
		log.info("BuildQuote batch Ends in = {} seconds {} milliseconds" , sec ,milliSec);
		if (status.equalsIgnoreCase(ApplicationConstant.SUCCESS)) {
			return new ResponseEntity<>(ApplicationConstant.SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@Operation(summary = "Build Get Quote Request For Products Plans", description = "Build Get Quote Request Get Quotes For Product Plans", tags="MetaData-GetQuotes")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Success", content = @Content),
	  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content) })
	@GetMapping(value = "/BuildUpFrontDiscount")
	@ResponseBody
	public ResponseEntity<String> buildUpFrontDiscount(HttpServletRequest req, HttpServletResponse res,
			@RequestHeader( name = "productCode") String productCode, @RequestHeader(name = "key") String key) throws Exception, FaultException {
		log.debug("Run BuildUpFrontDiscount Starts ::" );
		long startTime = System.currentTimeMillis();
		String status ;
		if (key.equalsIgnoreCase(EncryptionDecryptionAES.decrypt(productKey))) {
			if(StringUtils.isNotBlank(productCode)) {
				log.debug("BuildUpFrontDiscount Product Code = {}",productCode);
				status = productsUpFrontDiscountService.buildUpFrontDiscount(productCode.trim());
			}else {
				return new ResponseEntity<>(ApplicationConstant.PRODUCTCODE, HttpStatus.NOT_ACCEPTABLE);
			}
			
		}else {
			return new ResponseEntity<>(ApplicationConstant.VALIDKEY, HttpStatus.NOT_ACCEPTABLE);
		}
		
		long endTime = System.currentTimeMillis();
		float sec = (endTime - startTime) / 1000F;
		float milliSec=sec/(endTime - startTime);
		log.info("BuildUpFrontDiscount batch Ends in = {} seconds {} milliseconds" , sec ,milliSec);
		if (status.equalsIgnoreCase(ApplicationConstant.SUCCESS)) {
			return new ResponseEntity<>(ApplicationConstant.SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@Operation(summary = "Get Quotes For Product Plans", description = "Get Quotes For Product Plans", tags="MetaData-GetQuotes")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Success", content = @Content),
	  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content) })
	@GetMapping(value = "/GetQuotes")
	@ResponseBody
	public ResponseEntity<String> getQuotes(HttpServletRequest req, HttpServletResponse res,
			@RequestHeader( name = "productCode") String productCode, @RequestHeader(name = "key") String key)  {
		log.debug("Run Get Quote Request Batch Starts ::" );
		long startTime = System.currentTimeMillis();
		try {
			String status ;
			if (key.equalsIgnoreCase(EncryptionDecryptionAES.decrypt(productKey))) {
				if(StringUtils.isNotBlank(productCode)) {
					log.debug("Product Code = {}",productCode);
					status = productsBatchService.getQuotes(productCode.trim());
				}else {
					return new ResponseEntity<>(ApplicationConstant.PRODUCTCODE, HttpStatus.NOT_ACCEPTABLE);
				}
				
			}else {
				return new ResponseEntity<>(ApplicationConstant.VALIDKEY, HttpStatus.NOT_ACCEPTABLE);
			}
			
			long endTime = System.currentTimeMillis();
			float sec = (endTime - startTime) / 1000F;
			float milliSec=sec/(endTime - startTime);
			log.info("RunGetQuote batch Ends in = {} seconds {} milliseconds" , sec ,milliSec);
			if (status.equalsIgnoreCase(ApplicationConstant.SUCCESS)) {
				return new ResponseEntity<>(ApplicationConstant.SUCCESS, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error("Error in getQuotes batch {}",e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<>(ApplicationConstant.SUCCESS, HttpStatus.OK);
	}
	
	@Operation(summary = "Get Quotes For Product Plans", description = "Get Quotes For Product Plans", tags="MetaData-GetQuotes")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Success", content = @Content),
	  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content) })
	@GetMapping(value = "/AgeAndAgeBand")
	@ResponseBody
	public ResponseEntity<String> ageAndAgeBand(HttpServletRequest req, HttpServletResponse res,
			@RequestHeader( name = "productCode") String productCode, @RequestHeader(name = "key") String key)  {
		try {
			log.debug("Run Age And AgeBand Batch Starts ::" );
			long startTime = System.currentTimeMillis();
			
			if (StringUtils.isNotBlank(key)) {
				if(StringUtils.isNotBlank(productCode)) {
					log.debug("Product Code ={} ",productCode);
					if(key.equalsIgnoreCase(adults)) {
						 productsBatchService.runAdultsAgeAndAgeBand(productCode);
					}else if(key.equalsIgnoreCase(child)) {
						productsBatchService.runChildAgeAndAgeBand(productCode);
					}
					if(key.equalsIgnoreCase(parents)) {
						 productsBatchService.runParentsAgeAndAgeBand(productCode);
					}else {
						return new ResponseEntity<>(ApplicationConstant.VALIDKEY, HttpStatus.NOT_ACCEPTABLE);
					}
				}else {
					return new ResponseEntity<>(ApplicationConstant.PRODUCTCODE, HttpStatus.NOT_ACCEPTABLE);
				}
				
			}else {
				return new ResponseEntity<>(ApplicationConstant.VALIDKEY, HttpStatus.NOT_ACCEPTABLE);
			}
			
			long endTime = System.currentTimeMillis();
			float sec = (endTime - startTime) / 1000F;
			float milliSec=sec/(endTime - startTime);
			log.info("Run Age And AgeBand Batch Ends in = {} seconds {} milliseconds" , sec ,milliSec);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

}
