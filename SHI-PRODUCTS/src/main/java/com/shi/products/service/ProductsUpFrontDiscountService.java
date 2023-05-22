/**
 * 
 */
package com.shi.products.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.shi.products.exception.FaultException;
import com.shi.products.repo.GetQuoteResponseRepo;
import com.shi.products.util.ApplicationConstant;
import com.shi.products.util.ProductUtil;
import com.shi.products.vo.GetQuoteResponseVO;

/**
 * @author suman.raju
 *
 */
@Service
public class ProductsUpFrontDiscountService {
	private static final Logger log = LoggerFactory.getLogger(ProductsUpFrontDiscountService.class);
	

	@Autowired
	GetQuoteResponseRepo getQuoteResponseRepo;
	
	@Autowired
	ProductUtil productUtil;
	
	@Value("${upfront_discount_perc}")
	private String upfrontDiscountPerc;
	
	public String buildUpFrontDiscount(String productCode)  throws Exception, FaultException{
		log.info("Add buildUpFrontDiscount Starts");
		List<GetQuoteResponseVO> getQuoteResponseVOList = getQuoteResponseRepo.getUpFrontDiscountReq(productCode);
		if(null!=getQuoteResponseVOList) {
			for(GetQuoteResponseVO getQuoteResponseVO : getQuoteResponseVOList) {
				log.debug("Before {}",getQuoteResponseVO);
				
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(getQuoteResponseVO);
				
				GetQuoteResponseVO getQuoteRequest = productUtil.deMarshallingJSON(json, GetQuoteResponseVO.class);
				
				getQuoteRequest.setId(null);
				getQuoteRequest.setUpfrontDiscountYN("Y");
				getQuoteRequest.setUpfrontDiscountPerc(upfrontDiscountPerc);
				getQuoteRequest.setCreatedTime(new java.sql.Timestamp(new java.util.Date().getTime()));
				getQuoteRequest.setModifiedTime(new java.sql.Timestamp(new java.util.Date().getTime()));
				
				getQuoteRequest = productUtil.calculateUpFrontDiscount(getQuoteRequest);
				
				log.debug("After Modify {}",getQuoteRequest);
				getQuoteRequest = getQuoteResponseRepo.save(getQuoteRequest);
				log.debug("After Update {}",getQuoteRequest);
			}
		}
		log.info("Add buildUpFrontDiscount End");
		return ApplicationConstant.SUCCESS;
	}

}
