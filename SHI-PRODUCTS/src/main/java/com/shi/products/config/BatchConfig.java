/**
 * 
 */
package com.shi.products.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.shi.products.service.ProductsBatchService;


/**
 * @author suman.raju
 *
 */
@Component
public class BatchConfig {
	private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);
	
	@Autowired
	Environment environment;
	
	@Autowired
	ProductsBatchService productsBatchService;
	
	@Value("${batch_product_code}")
	private String batchProductCode;
	
	  //1 Hour // 30 Minutes
	  
		@Scheduled(fixedDelay = 60 * 30 * 1000, initialDelay = 60 * 5000)
	//@Scheduled(fixedDelay = 60 * 30 * 1000, initialDelay = 6000)
		public void LCMZohoClientResponse() throws Exception {
			log.debug("Schedule Products Service Starts ::");
			String productCode = batchProductCode;
			productsBatchService.getQuotesRequestBatchSchedule(productCode);
			log.debug("Schedule Products Service End ::");
		}
	
}
