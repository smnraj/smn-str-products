/**
 * 
 */
package com.shi.products.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shi.products.exception.FaultException;
import com.shi.products.service.ProductBatchService;
import com.shi.products.service.ProductMetaDataService;
import com.shi.products.util.ApplicationConstant;
import com.shi.products.util.EncryptionDecryptionAES;
import com.shi.products.vo.ProductComparisonMappingVO;
import com.shi.products.vo.ProductListVO;
import com.shi.products.vo.ProductPincodeVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

//import io.swagger.v3.oas.annotations.Operation;

/**
 * @author suman.raju
 *
 */
@RestController
@RequestMapping("/Meta-Data")
public class ProductMetaDataController {
	private static final Logger log = LoggerFactory.getLogger(ProductMetaDataController.class);
	
	@Autowired
	ProductMetaDataService productMetaDataService;
	
	@Autowired
	ProductBatchService productBatchService;
	
	@Autowired
	Environment environment;
	
	@Value("${product_upload_key}")
	private String productKey;

	@Operation(summary = "Upload the Products Lists", description = "List of Products", tags="MetaData")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Uploaded the Products Successfully", content = @Content),
	  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content) })
	// Upload Product Features
	@PostMapping(value = "/ProductsFeature", consumes = {"multipart/form-data"})
	ResponseEntity<String> uploadProductList(
			@RequestParam MultipartFile file, 
			@RequestParam(name = "key") String key)
			throws FaultException, IOException {
		log.info("Upload Product List Starts");
		int count = 0;
		if (key.equalsIgnoreCase(EncryptionDecryptionAES.decrypt(productKey))) {
			try {
				log.debug("ProductsFeature File Name :: {}" , file.getOriginalFilename());
				XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
				XSSFSheet worksheet = workbook.getSheetAt(0);
				List<ProductListVO> productsVO = productMetaDataService.upLoadProductsList(worksheet);
				workbook.close();
				count = productsVO.size();
				return new ResponseEntity<>(+ productsVO.size() + " Product Features uploaded......",
						HttpStatus.ACCEPTED);
			} catch (FaultException msg) {
				return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_ACCEPTABLE);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}

		} else {
			return new ResponseEntity<>(ApplicationConstant.VALIDATE_KEY, HttpStatus.NOT_ACCEPTABLE);
		}
		log.info("Upload Products End ::");
		return new ResponseEntity<>( + count + " Product Features uploaded......", HttpStatus.ACCEPTED);
	}
	
	
	@Operation(summary = "Upload the Products Comparison Mapping Data", description = "List of Mapping Products", tags="MetaData")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Uploaded the Mapping Products Successfully", content = @Content),
	  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content) })
	// uploadProductComparisonMapping
	@PostMapping(value = "/ProductsComparisonMapping", consumes = {"multipart/form-data"})
	ResponseEntity<String> uploadProductComparisonMapping(@RequestParam("file") MultipartFile file, @RequestHeader(name = "key") String key)
			throws FaultException, IOException {
		log.info("uploadProductComparisonMapping Starts ");
		int count = 0;
		if (key.equalsIgnoreCase(EncryptionDecryptionAES.decrypt(productKey))) {
			try {
				log.debug("ProductsComparisonMapping File Name :: {}" , file.getOriginalFilename());
				XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
				XSSFSheet worksheet = workbook.getSheetAt(0);
				List<ProductComparisonMappingVO> productsVO = productMetaDataService
						.uploadProductsComparisonMapping(worksheet);

				workbook.close();
				count = productsVO.size();
				return new ResponseEntity<>(+ productsVO.size() + " Product Mapping uploaded......",
						HttpStatus.ACCEPTED);
			} catch (FaultException msg) {
				return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_ACCEPTABLE);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}

		} else {
			return new ResponseEntity<>(ApplicationConstant.VALIDATE_KEY, HttpStatus.NOT_ACCEPTABLE);
		}
		log.info("Upload Products End ::");
		return new ResponseEntity<>( + count + " ProductsComparisonMapping uploaded......", HttpStatus.ACCEPTED);
	}
	
	
	
	@Operation(summary = "Upload the Pin Codes", description = "List of Pin Codes", tags="MetaData")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Uploaded the Pin Codes Successfully", content = @Content),
	  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content) })
	// upload Product Pin Code
	@PostMapping(value = "/ProductsPinCode", consumes = {"multipart/form-data"})
	ResponseEntity<String> uploadProductPinCode(@RequestParam("file") MultipartFile file, @RequestHeader(name = "key") String key)
			throws FaultException, IOException {
		log.info("uploadProductPinCode Starts ");
		int count = 0;
		if (key.equalsIgnoreCase(EncryptionDecryptionAES.decrypt(productKey))) {
			try {
				log.debug("ProductsPinCode File Name :: {}" , file.getOriginalFilename());
				XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
				XSSFSheet worksheet = workbook.getSheetAt(0);
				List<ProductPincodeVO> productsVO = productMetaDataService.uploadProductsPincode(worksheet);

				workbook.close();
				count = productsVO.size();
				return new ResponseEntity<>(+ productsVO.size() + " PinCode uploaded......", HttpStatus.ACCEPTED);
			} catch (FaultException msg) {
				return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_ACCEPTABLE);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}

		} else {
			return new ResponseEntity<>(ApplicationConstant.VALIDATE_KEY, HttpStatus.NOT_ACCEPTABLE);
		}
		log.info("Upload PinCode End ::");
		return new ResponseEntity<>(+ count + " PinCode uploaded......", HttpStatus.ACCEPTED);
	}
	
	@Operation(summary = "Get Quote Request Status", description = "Get Quote Request Status", tags="MetaData-GetQuotes-Status")
	@GetMapping(value = "/GetQuoteRequestStatus")
	@ResponseBody
	public ResponseEntity<Map<String, String>> getQuoteRequestStatus(HttpServletRequest req, HttpServletResponse res,
			@RequestHeader(name = "key") String key) throws Exception, FaultException {
		log.debug("Get Quote Request Status Batch Starts ::" );
		long startTime = System.currentTimeMillis();
		Map<String, String> getQuoteRequestStatus = new HashMap<>();
		if (key.equalsIgnoreCase(EncryptionDecryptionAES.decrypt(productKey))) {
			getQuoteRequestStatus = productBatchService.getQuoteRequestStatus();
		}else {
			getQuoteRequestStatus.put("Key", ApplicationConstant.VALIDATE_KEY);
			return new ResponseEntity<>(getQuoteRequestStatus, HttpStatus.NOT_ACCEPTABLE);
		}
		
		long endTime = System.currentTimeMillis();
		float sec = (endTime - startTime) / 1000F;
		log.debug("Get Quote Request Status ends in = {} seconds / {} milliseconds",sec,(endTime - startTime));
		return new ResponseEntity<>(getQuoteRequestStatus, HttpStatus.OK);
	}
	
	@Operation(summary = "Get Quote Response Status", description = "Get Quote Response Status", tags="MetaData-GetQuotes-Status")
	@GetMapping(value = "/GetQuoteResponseStatus")
	@ResponseBody
	public ResponseEntity<Map<String, String>> getQuoteResponseStatus(HttpServletRequest req, HttpServletResponse res,
			@RequestHeader(name = "key") String key) throws Exception, FaultException {
		log.debug("Get Quote Response Status Batch Starts ::" );
		long startTime = System.currentTimeMillis();
		Map<String, String> getQuoteResponseStatus = new HashMap<>();
		if (key.equalsIgnoreCase(EncryptionDecryptionAES.decrypt(productKey))) {
			getQuoteResponseStatus = productBatchService.getQuoteResponseStatus();
		}else {
			getQuoteResponseStatus.put("Key", ApplicationConstant.VALIDATE_KEY);
			return new ResponseEntity<>(getQuoteResponseStatus, HttpStatus.NOT_ACCEPTABLE);
		}
		
		long endTime = System.currentTimeMillis();
		float sec = (endTime - startTime) / 1000F;
		log.debug("Get Quote Response Status ends in = {} seconds / {} milliseconds",sec,(endTime - startTime));
		return new ResponseEntity<>(getQuoteResponseStatus, HttpStatus.OK);
	}
	
	@Operation(summary = "Sample files for Meta Data", description = "Sample files for Meta Data", tags="MetaData")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", content = { @Content(mediaType = "multipart/form-data")}),
			  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content), 
			  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content) })
	@GetMapping(path = "/Files/Download")
	//@Operation(summary = "Download a File")
	public ResponseEntity<Resource> downloadFile() throws IOException {
		//String fileName = "Meta_Data_Test.xlsx";
		String fileName = environment.getProperty("Sample_File_Name");
		String filePath = environment.getProperty("Sample_File_Path");
		//File file = new File("D:\\STAR\\Projects\\Product Comparison\\Document\\Data\\Version-7\\" + fileName);
		File file = new File(filePath + fileName);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
	//	Path path = Paths.get("D:\\STAR\\Projects\\Product Comparison\\Document\\Data\\Version-7\\" + fileName);
		Path path = Paths.get(filePath + fileName);
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}
	
}
