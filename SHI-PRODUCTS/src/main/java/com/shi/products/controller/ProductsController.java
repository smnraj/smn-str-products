/**
 * 
 */
package com.shi.products.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shi.products.req.GetQuoteRequest;
import com.shi.products.req.ProductFeatureRequest;
import com.shi.products.res.Products;
import com.shi.products.res.ComparedProductsRes;
import com.shi.products.res.ProductFeatureResponse;
import com.shi.products.res.QuotesRes;
import com.shi.products.exception.FaultException;
import com.shi.products.service.ProductService;
import com.shi.products.service.ProductMetaDataService;
import com.shi.products.util.ProductUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.media.ArraySchema;

/**
 * @author suman.raju
 *
 */

@RestController
@RequestMapping("/Quotes")
public class ProductsController {
	
	private static final Logger log = LoggerFactory.getLogger(ProductsController.class);
	
	@Value("${product_upload_key}")
	private String productKey;
	
	@Value("${compare_product_code_Individual}")
	private String compareProductCodeIndividual;
	
	@Value("${compare_product_code_Floater}")
	private String compareProductCodeFloater;
	
	@Autowired
	ProductMetaDataService productMetaDataService;

	@Autowired
	ProductUtil productUtil;
	
	@Autowired
	ProductService productService;
	
	@Operation(summary = "Get the Products", description = "Get a list of products", tags="Products")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Found the products", 
		  content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = ComparedProductsRes.class)) }),
	  @ApiResponse(responseCode = "204", description = "No Content", 
      	  content = @Content),
	  @ApiResponse(responseCode = "400", description = "Bad Request", 
	      content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Not Found", 
	      content = @Content),
	  @ApiResponse(responseCode = "500", description = "Internal Server Error", 
          content = @Content) })
	@RequestMapping(value = "/GetProducts", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ComparedProductsRes> compareProducts(HttpServletRequest req,
			HttpServletResponse res, @RequestBody GetQuoteRequest getQuoteRequest) throws Throwable, Exception, FaultException {
		log.debug("################################################# CompareProducts Starts #################################################");
		log.debug("CompareProducts Starts :: {}" ,productUtil.doMarshallingJSON(getQuoteRequest));
		long startTime = System.currentTimeMillis();
		ComparedProductsRes comparedProductsRes = new ComparedProductsRes();
		List<Products> products;
		if(StringUtils.isBlank(getQuoteRequest.getProductId())) {
			if(getQuoteRequest.getAdults().equals("1") && StringUtils.isBlank(getQuoteRequest.getChild())
					&& StringUtils.isBlank(getQuoteRequest.getParents())) {
				log.debug("Compare Product Code for Individual {} ",compareProductCodeIndividual);
				getQuoteRequest.setProductId(compareProductCodeIndividual);
			}else {
				log.debug("Compare Product Code for Floater {} ",compareProductCodeFloater);
				getQuoteRequest.setProductId(compareProductCodeFloater);
			}
		}
		try {
			products = productService.getProductsByCode(getQuoteRequest);
		} catch (Exception e) {
			log.error("Excetion :: {}" ,e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		comparedProductsRes.setProducts(products);
		log.debug("CompareProducts Result : {}" , products);
		long endTime = System.currentTimeMillis();
		float sec = (endTime - startTime) / 1000F;
		log.debug("CompareProducts Ends in seconds :: {} " , sec );
		log.debug("################################################# CompareProducts Ends #################################################");
		if (CollectionUtils.isNotEmpty(products)) {
			return new ResponseEntity<>(comparedProductsRes, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@Operation(summary = "Get Quote for the Product", description = "Get Quote for the Product", tags="Products")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Found the Product Quote", 
		  content = { @Content(mediaType = "application/json", 
		  //array = @ArraySchema(schema = @Schema(implementation = Quotes.class))) }),
		  schema = @Schema(implementation = QuotesRes.class)) }),
	  @ApiResponse(responseCode = "204", description = "No Content", 
  	  	  content = @Content),
	  @ApiResponse(responseCode = "400", description = "Bad Request", 
	      content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Not Found", 
          content = @Content),
	  @ApiResponse(responseCode = "500", description = "Internal Server Error", 
          content = @Content)})
	@PostMapping(value = "/GetQuote", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<QuotesRes> getQuote(HttpServletRequest req,
			HttpServletResponse res, @RequestBody GetQuoteRequest getQuoteRequest) throws Exception, FaultException {
		log.debug("################################################# GetQuote Starts #################################################");
		log.debug("Product GetQuote :: {}" ,productUtil.doMarshallingJSON(getQuoteRequest));
		long startTime = System.currentTimeMillis();
		QuotesRes quotesRes = new QuotesRes();
		if (StringUtils.isNotBlank(getQuoteRequest.getProductId())) {
			log.debug("GetQuote Product Code :: {}" , getQuoteRequest.getProductId());
			try {
				quotesRes = productService.getQuotes(getQuoteRequest);
			}catch(FaultException msg) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				log.error("Excetion :: ",e);
			}
		}
		log.debug("GetQuote Result : {}" , quotesRes);
		// return ResponseEntity.ok(products);
		long endTime = System.currentTimeMillis();
		float sec = (endTime - startTime) / 1000F;
		log.debug("Product GetQuote Ends in seconds :: {} " , sec );
		log.debug("################################################# GetQuote End #################################################");
		return new ResponseEntity<>(quotesRes, HttpStatus.OK);
	}
	
	@Operation(summary = "Get All Quotes for the Product", description = "Get Quotes for the Product", tags="Products")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Found the Product Quotes", 
		  content = { @Content(mediaType = "application/json", 
		  schema = @Schema(implementation = QuotesRes.class)) }),
	  @ApiResponse(responseCode = "204", description = "No Content", 
  	  	  content = @Content),
	  @ApiResponse(responseCode = "400", description = "Bad Request", 
	      content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Not Found", 
          content = @Content),
	  @ApiResponse(responseCode = "500", description = "Internal Server Error", 
          content = @Content)})
	@PostMapping(value = "/GetAllQuotes", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<QuotesRes> getAllQuotes(HttpServletRequest req,
			HttpServletResponse res, @RequestBody GetQuoteRequest getQuoteRequest) throws Exception, FaultException {
		log.debug("################################################# GetAllQuotes Starts #################################################");
		log.debug("Product GetAllQuotes :: {}" ,productUtil.doMarshallingJSON(getQuoteRequest));
		long startTime = System.currentTimeMillis();
		QuotesRes quotesRes = new QuotesRes();
		if (StringUtils.isNotBlank(getQuoteRequest.getProductId())) {
			log.debug("Get All Quote Product Code :: {}" , getQuoteRequest.getProductId());
			try {
				quotesRes = productService.getAllQuotes(getQuoteRequest);
			}catch(FaultException msg) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				log.error("Excetion :: ",e);
			}
		}
		log.debug("GetAllQuotes Result : {}" , quotesRes);
		long endTime = System.currentTimeMillis();
		float sec = (endTime - startTime) / 1000F;
		log.debug("Product GetAllQuotes Ends in seconds :: {} " , sec );
		log.debug("################################################# GetAllQuotes End #################################################");
		return new ResponseEntity<>(quotesRes, HttpStatus.OK);
	}
	
	@Operation(summary = "Products Features", description = "Product Features", tags="Products")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Found the Features", 
		  content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = ProductFeatureResponse.class)) }),
	  @ApiResponse(responseCode = "204", description = "No Content", 
          content = @Content),
	  @ApiResponse(responseCode = "400", description = "Bad Request", 
	      content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Not Found", 
	      content = @Content),
	  @ApiResponse(responseCode = "500", description = "Internal Server Error", 
          content = @Content)})
	@PostMapping(value = "/productsFeatures", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ProductFeatureResponse> productsFeatures(HttpServletRequest req,
			HttpServletResponse res, @RequestBody ProductFeatureRequest productFeature) throws Exception {
		log.debug("################################################# Product Features Start #################################################");
		log.debug("Product Features Starts :: {}" ,productUtil.doMarshallingJSON(productFeature));
		long startTime = System.currentTimeMillis();
		ProductFeatureResponse productFeatures ;
		if(CollectionUtils.isNotEmpty(productFeature.getProductsFeature())) {
			productFeatures = productService.getProductFeatures(productFeature.getProductsFeature());
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		long endTime = System.currentTimeMillis();
		float sec = (endTime - startTime) / 1000F;
		log.debug("Product Features Ends in = seconds :: {} " , sec );
		log.debug("################################################# Product Features End #################################################");
		if (CollectionUtils.isNotEmpty(productFeatures.getProductFeatures())) {
			return new ResponseEntity<>(productFeatures, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
}
