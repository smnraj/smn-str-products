/**
 * 
 */
package com.shi.products.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shi.products.client.req.GetQuoteSOAPRequest;
import com.shi.products.client.res.GetQuoteSOAPResponse;
import com.shi.products.dao.ProductsDAO;
import com.shi.products.repo.GetQuoteResponseRepo;
import com.shi.products.util.ProductUtil;
import com.shi.products.vo.GetQuoteResponseVO;

/**
 * @author suman.raju
 *
 */
@Service
public class ProductBatchService {
	private static final Logger log = LoggerFactory.getLogger(ProductBatchService.class);
	
	@Autowired
	ProductUtil productUtil;
	
	@Autowired
	Environment environment;
	
	@Autowired
	GetQuoteSOAPRequest soapClient;
	
	@Autowired
	GetQuoteResponseRepo getQuoteResponseRepo;
	
	@Autowired
	ProductsDAO productsDAO;
	

	public GetQuoteResponseVO addQuoteResponseVO(GetQuoteSOAPResponse getQuoteResponse, String batchId) {
		log.debug("Add Get Quote Response Starts");
		GetQuoteResponseVO getQuoteResponseVO = new GetQuoteResponseVO();
		
		getQuoteResponseVO.setBatchId(batchId);
		if(StringUtils.isNotBlank(getQuoteResponse.getPremiumAmount())) {
			getQuoteResponseVO.setPremiumAmount(Integer.parseInt(getQuoteResponse.getPremiumAmount()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getPremiumTax())) {
			getQuoteResponseVO.setPremiumTax(Integer.parseInt(getQuoteResponse.getPremiumTax()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getTotalAmount())) {
			getQuoteResponseVO.setTotalAmount(Integer.parseInt(getQuoteResponse.getTotalAmount()));
		}
		getQuoteResponseVO.setMessage(getQuoteResponse.getMessage());
		if(StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntFirstPrem())) {
			getQuoteResponseVO.setHlflyPremAmntFirstPrem(Integer.parseInt(getQuoteResponse.getHlflyPremAmntFirstPrem()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntFirstTax())) {
			getQuoteResponseVO.setHlflyPremAmntFirstTax(Integer.parseInt(getQuoteResponse.getHlflyPremAmntFirstTax()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntFirstTotal())) {
			getQuoteResponseVO.setHlflyPremAmntFirstTotal(Integer.parseInt(getQuoteResponse.getHlflyPremAmntFirstTotal()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntOthersPrem())) {
			getQuoteResponseVO.setHlflyPremAmntOthersPrem(Integer.parseInt(getQuoteResponse.getHlflyPremAmntOthersPrem()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntOthersTax())) {
			getQuoteResponseVO.setHlflyPremAmntOthersTax(Integer.parseInt(getQuoteResponse.getHlflyPremAmntOthersTax()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntOthersTotal())) {
			getQuoteResponseVO.setHlflyPremAmntOthersTotal(Integer.parseInt(getQuoteResponse.getHlflyPremAmntOthersTotal()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntFirstPrem())) {
			getQuoteResponseVO.setQtrlyPremAmntFirstPrem(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntFirstPrem()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntFirstTax())) {
			getQuoteResponseVO.setQtrlyPremAmntFirstTax(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntFirstTax()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntFirstTotal())) {
			getQuoteResponseVO.setQtrlyPremAmntFirstTotal(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntFirstTotal()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntOthersPrem())) {
			getQuoteResponseVO.setQtrlyPremAmntOthersPrem(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntOthersPrem()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntOthersTax())) {
			getQuoteResponseVO.setQtrlyPremAmntOthersTax(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntOthersTax()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntOthersTotal())) {
			getQuoteResponseVO.setQtrlyPremAmntOthersTotal(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntOthersTotal()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntFirstPrem())) {
			getQuoteResponseVO.setMnthlyPremAmntFirstPrem(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntFirstPrem()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntFirstTax())) {
			getQuoteResponseVO.setMnthlyPremAmntFirstTax(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntFirstTax()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntFirstTotal())) {
			getQuoteResponseVO.setMnthlyPremAmntFirstTotal(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntFirstTotal()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntOthersPrem())) {
			getQuoteResponseVO.setMnthlyPremAmntOthersPrem(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntOthersPrem()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntOthersTax())) {
			getQuoteResponseVO.setMnthlyPremAmntOthersTax(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntOthersTax()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntOthersTotal())) {
			getQuoteResponseVO.setMnthlyPremAmntOthersTotal(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntOthersTotal()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getTotalRuralDiscountAmt())) {
			getQuoteResponseVO.setTotalRuralDiscountAmt(Integer.parseInt(getQuoteResponse.getTotalRuralDiscountAmt()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getTotalOnlineDiscountAmt())) {
			getQuoteResponseVO.setTotalOnlineDiscountAmt(Integer.parseInt(getQuoteResponse.getTotalOnlineDiscountAmt()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getTotalAddDiscnt())) {
			getQuoteResponseVO.setTotalAddDiscnt(Integer.parseInt(getQuoteResponse.getTotalAddDiscnt()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getBuyBackPrem())) {
			getQuoteResponseVO.setBuyBackPrem(Integer.parseInt(getQuoteResponse.getBuyBackPrem()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getHospitalCashPrem())) {
			getQuoteResponseVO.setHospitalCashPrem(Integer.parseInt(getQuoteResponse.getHospitalCashPrem()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getPatientCarePrem())) {
			getQuoteResponseVO.setPatientCarePrem(Integer.parseInt(getQuoteResponse.getPatientCarePrem()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getAddOnCvrSec1Prem())) {
			getQuoteResponseVO.setAddOnCvrSec1Prem(Integer.parseInt(getQuoteResponse.getAddOnCvrSec1Prem()));
		}
		if(StringUtils.isNotBlank(getQuoteResponse.getAddOnCvrSec2Prem())) {
			getQuoteResponseVO.setAddOnCvrSec2Prem(Integer.parseInt(getQuoteResponse.getAddOnCvrSec2Prem()));
		}
		log.debug("Add Get Quote Response End");
		return getQuoteResponseVO;
	}
	
	
	// save file
	void saveUploadedFiles(List<MultipartFile> files) throws IOException {
		File folder = new File("UPLOADED_FOLDER");
		if (!folder.exists()) {
			folder.mkdir();
		}
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
				// next pls
			}
			byte[] bytes = file.getBytes();
			Path path = Paths.get("UPLOADED_FOLDER" + file.getOriginalFilename());
			Files.write(path, bytes);
		}
	}


	public Map<String, String> getQuoteRequestStatus() {
		log.debug("Get Quote Request Status ");
		return productsDAO.getQuoteRequestStatus();
	}

	public Map<String, String> getQuoteResponseStatus() {
		log.debug("Get Quote Response Status ");
		return productsDAO.getQuoteResponseStatus();
	}

}
