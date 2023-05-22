/**
 * 
 */
package com.shi.products.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.shi.products.repo.ProductComparisonMappingRepo;
import com.shi.products.repo.ProductsRepo;
import com.shi.products.repo.QuotesAuditRepo;
import com.shi.products.vo.GetQuoteResponseVO;
import com.shi.products.vo.ProductComparisonMappingVO;
import com.shi.products.vo.ProductListVO;
import com.shi.products.vo.QuotesAuditVO;

/**
 * @author suman.raju
 *
 */

@Repository
public class ProductsDAO {
	
	private static final Logger log = LoggerFactory.getLogger(ProductsDAO.class);
	
	@Autowired
	ProductsRepo productRepo;
	
	@Autowired
	ProductComparisonMappingRepo productComparisonMappingRepo;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Environment environment;
	
	@Autowired
	QuotesAuditRepo quotesAuditRepo;
	
	public void saveAllProducts(List<ProductListVO> productsVO) {
		log.info("Save Products Starts");
		productRepo.saveAll(productsVO);
	}
	
	public void saveProductComparisonMapping(List<ProductComparisonMappingVO> productComparisonMappingVOs) {
		log.info("Save productComparisonMappingRepo Starts");
		productComparisonMappingRepo.saveAll(productComparisonMappingVOs);
	}
	
	public List<GetQuoteResponseVO> getQuoteResponse(String sql){
		//log.debug("Get Quote Response SQL DAO = "+sql);
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<GetQuoteResponseVO>(GetQuoteResponseVO.class));
	}


	public Map<String, String> getQuoteRequestStatus() {
		Map<String, String> getQuoteRequestStatus = new HashMap<>();
		String sql = "select productcode,flag, count(*) as count from SHI_PRODUCTS_GET_QUOTE_REQUEST where isActive = 'TRUE' group by productcode,flag";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map row : rows) {
			String productCode = (String) row.get("productcode");
			String flag = (String) row.get("flag");
			long count = ((Long) row.get("count")).longValue();
		
			if(flag.equals("0")) {
				getQuoteRequestStatus.put(productCode+" (0)", "YETTOSTART = "+count);
			}else if(flag.equals("1")) {
				getQuoteRequestStatus.put(productCode+" (1)", "INPROGRESS = "+count);
			}else if(flag.equals("2")) {
				getQuoteRequestStatus.put(productCode+" (2)", "COMPLETED = "+count);
			}else if(flag.equals("3")) {
				getQuoteRequestStatus.put(productCode+" (3)", "FAILED = "+count);
			}else if(flag.equals("4")) {
				getQuoteRequestStatus.put(productCode+" (4)", "INQUEUE = "+count);
			}else if(flag.equals("5")) {
				getQuoteRequestStatus.put(productCode+" (5)", "ERROR-MESSAGE = "+count);
			}else {
				getQuoteRequestStatus.put(productCode+" ("+flag+")" , "Flag_Count = "+count);
			}
		}
		return getQuoteRequestStatus;
	}

	public Map<String, String> getQuoteResponseStatus() {
		Map<String, String> getQuoteResponseStatus = new HashMap<>();
		String sql = "select productcode,flag, count(*) as count from SHI_PRODUCTS_GET_QUOTE_RESPONSE where isActive = 'TRUE' group by productcode,flag";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map row : rows) {
			String productCode = (String) row.get("productcode");
			String flag = (String) row.get("flag");
			long count = ((Long) row.get("count")).longValue();
		
			if(flag.equals("0")) {
				getQuoteResponseStatus.put(productCode+" (0)", "YETTOSTART = "+count);
			}else if(flag.equals("1")) {
				getQuoteResponseStatus.put(productCode+" (1)", "INPROGRESS = "+count);
			}else if(flag.equals("2")) {
				getQuoteResponseStatus.put(productCode+" (2)", "COMPLETED = "+count);
			}else if(flag.equals("3")) {
				getQuoteResponseStatus.put(productCode+" (3)", "FAILED = "+count);
			}else if(flag.equals("4")) {
				getQuoteResponseStatus.put(productCode+" (4)", "INQUEUE = "+count);
			}else {
				getQuoteResponseStatus.put(productCode+" ("+flag+")" , "Flag_Count = "+count);
			}
		}
		return getQuoteResponseStatus;
	}
	
	
	public String quotesAudit(String productCode, String productName, String sourceSystem, Long getQuoteResponseId) {
		String uniqueIdString = "";
		
		UUID uuid = UUID.randomUUID();  
		String uniqueUUID= uuid.toString();
		
		QuotesAuditVO quotesAuditVO = new QuotesAuditVO();
		quotesAuditVO.setUuid(uniqueUUID);
		quotesAuditVO.setProductCode(productCode);
		quotesAuditVO.setProductName(productName);
		quotesAuditVO.setQuoteResId(getQuoteResponseId);
		quotesAuditVO.setSourceSystem(sourceSystem);
		quotesAuditVO.setCreatedTime(new java.sql.Timestamp(new java.util.Date().getTime()));
		quotesAuditVO = quotesAuditRepo.save(quotesAuditVO);
		uniqueIdString = sourceSystem.concat("-").concat(String.valueOf(getQuoteResponseId)).concat("-").concat(String.valueOf(quotesAuditVO.getUniqueId()));
		log.debug("quoteId={}",uniqueIdString);
		return uniqueIdString;
	}

}
