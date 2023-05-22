/**
 * 
 */
package com.shi.products.client.req;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.w3c.dom.DOMException;
import org.w3c.dom.NodeList;

import com.shi.products.client.res.GetQuoteSOAPResponse;
import com.shi.products.util.ApplicationConstant;
import com.shi.products.util.ProductUtil;
import com.shi.products.vo.GetQuoteRequestVO;

/**
 * @author suman.raju
 *
 */
@Component
public class GetQuoteSOAPRequest {
	private static final Logger log = LoggerFactory.getLogger(GetQuoteSOAPRequest.class);

	public GetQuoteSOAPRequest() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Value("${GetQuickQuote_URL}")
	private String getQuickQuoteURL;

	@Value("${GetQuote_URL}")
	private String getQuoteURL;

	@Autowired
	ProductUtil productUtil;
	
	@Autowired
	Environment environment;

	private static String getXmlFromSOAPMessage(SOAPMessage msg) throws SOAPException, IOException {
		ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
		msg.writeTo(byteArrayOS);
		return new String(byteArrayOS.toByteArray());

	}

	private static String fancyPrintXML(SOAPMessage msg) throws SOAPException, IOException {
		ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
		msg.writeTo(byteArrayOS);
		String inSoap = new String(byteArrayOS.toByteArray());
		int currentpos = 0;
		int nextpos = 0;
		StringBuilder soap = new StringBuilder(inSoap);
		while ((nextpos = soap.indexOf("><", currentpos)) != -1) {

			String startTag, endTag;
			int start = nextpos - 1;
			int end = nextpos + 1;
			while (soap.charAt(start) != '<') {
				start--;
			}
			startTag = soap.substring(start + 1, nextpos);
			while (soap.charAt(end) != '>') {
				end++;
			}
			endTag = soap.substring(nextpos + 2, end);

			if (!startTag.startsWith("/") && endTag.startsWith("/")) {
				if (startTag.startsWith("/")) {
					startTag = startTag.substring(1);
				} else if (startTag.endsWith("/")) {
					startTag = startTag.substring(0, startTag.length() - 1);
				}
				if (endTag.startsWith("/")) {
					endTag = endTag.substring(1);
				} else if (endTag.endsWith("/")) {
					endTag = endTag.substring(0, endTag.length() - 1);
				}
			}
			if (!startTag.equals(endTag)) {
				soap.replace(nextpos, nextpos + 2, ">\r\n<");
				currentpos = nextpos;
			} else if (!startTag.startsWith(endTag)) {
				if (startTag.charAt(endTag.length()) != ' ') {
					soap.replace(nextpos, nextpos + 2, ">\r\n<");
					currentpos = nextpos;
				}
			} else {
				currentpos = nextpos + 2;
			}
		}

		return soap.toString();
	}
	
	public GetQuoteSOAPResponse getQuotes(GetQuoteRequestVO getQuoteRequestVO) {
		log.info("Get Quote SOAP Service Starts");
		GetQuoteSOAPResponse getQuoteResponse = null;
		try {
			getQuoteResponse = new GetQuoteSOAPResponse();
			
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnectionFactory.createConnection();

			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage message = factory.createMessage();

			SOAPPart part = message.getSOAPPart();
			SOAPEnvelope envelope = part.getEnvelope();
			envelope.addNamespaceDeclaration("star", "http://starhealth.in/");

			SOAPBody body = message.getSOAPBody();

			SOAPElement getQuote = body.addChildElement("GetQuote", "star");
			SOAPElement objGetQuote = getQuote.addChildElement("objGetQuote", "star");
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getProductCode())) {
				objGetQuote.addChildElement(ApplicationConstant.PRODUCTID, "star").addTextNode(getQuoteRequestVO.getProductCode());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.PRODUCTID, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getInsuranceCover())) {
				objGetQuote.addChildElement(ApplicationConstant.INSURANCECOVER, "star").addTextNode(getQuoteRequestVO.getInsuranceCover());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.INSURANCECOVER, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getDeduction())) {
				objGetQuote.addChildElement(ApplicationConstant.DEDUCTION, "star").addTextNode(getQuoteRequestVO.getDeduction());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.DEDUCTION, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getAdults())) {
				objGetQuote.addChildElement(ApplicationConstant.ADULTS, "star").addTextNode(getQuoteRequestVO.getAdults());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.ADULTS, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getChild())) {
				objGetQuote.addChildElement(ApplicationConstant.CHILD, "star").addTextNode(getQuoteRequestVO.getChild());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.CHILD, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getSchemeCode())) {
				objGetQuote.addChildElement(ApplicationConstant.SCHEMECODE, "star").addTextNode(getQuoteRequestVO.getSchemeCode());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.SCHEMECODE, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getAdultDOB1())) {
				objGetQuote.addChildElement(ApplicationConstant.ADULTDOB1, "star").addTextNode(productUtil.getDOB(getQuoteRequestVO.getAdult1AgeBandInYears()));
				getQuoteRequestVO.setAdultDOB1(productUtil.getDOB(getQuoteRequestVO.getAdult1AgeBandInYears()));
			}else {
				objGetQuote.addChildElement(ApplicationConstant.ADULTDOB1, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getAdultDOB2())) {
				objGetQuote.addChildElement(ApplicationConstant.ADULTDOB2, "star").addTextNode(productUtil.getDOB(getQuoteRequestVO.getAdult2AgeBandInYears()));
				getQuoteRequestVO.setAdultDOB2(productUtil.getDOB(getQuoteRequestVO.getAdult2AgeBandInYears()));
			}else {
				objGetQuote.addChildElement(ApplicationConstant.ADULTDOB2, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getPinCode())) {
				objGetQuote.addChildElement(ApplicationConstant.PINCODE, "star").addTextNode(getQuoteRequestVO.getPinCode());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.PINCODE, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getPeriod())) {
				objGetQuote.addChildElement(ApplicationConstant.PERIOD, "star").addTextNode(getQuoteRequestVO.getPeriod());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.PERIOD, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getOption1())) {
				objGetQuote.addChildElement(ApplicationConstant.OPTION, "star").addTextNode(getQuoteRequestVO.getOption1().trim());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.OPTION, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getPlan())) {
				objGetQuote.addChildElement(ApplicationConstant.PLAN, "star").addTextNode(getQuoteRequestVO.getPlan());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.PLAN, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getPolFmDt())) {
				objGetQuote.addChildElement(ApplicationConstant.POLFMDT, "star").addTextNode(getQuoteRequestVO.getPolFmDt().trim());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.POLFMDT, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getParents())) {
				objGetQuote.addChildElement(ApplicationConstant.PARENTS, "star").addTextNode(getQuoteRequestVO.getParents());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.PARENTS, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getParentDOB1())) {
				objGetQuote.addChildElement(ApplicationConstant.PARENTDOB1, "star").addTextNode(productUtil.getDOB(getQuoteRequestVO.getParent1AgeBandInYears()));
				getQuoteRequestVO.setParentDOB1(productUtil.getDOB(getQuoteRequestVO.getParent1AgeBandInYears()));
			}else {
				objGetQuote.addChildElement(ApplicationConstant.PARENTDOB1, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getParentDOB2())) {
				objGetQuote.addChildElement(ApplicationConstant.PARENTDOB2, "star").addTextNode(productUtil.getDOB(getQuoteRequestVO.getParent2AgeBandInYears()));
				getQuoteRequestVO.setParentDOB2(productUtil.getDOB(getQuoteRequestVO.getParent2AgeBandInYears()));
			}else {
				objGetQuote.addChildElement(ApplicationConstant.PARENTDOB2, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getParentDOB3())) {
				objGetQuote.addChildElement(ApplicationConstant.PARENTDOB3, "star").addTextNode(productUtil.getDOB(getQuoteRequestVO.getParent3AgeBandInYears()));
				getQuoteRequestVO.setParentDOB3(productUtil.getDOB(getQuoteRequestVO.getParent3AgeBandInYears()));
			}else {
				objGetQuote.addChildElement(ApplicationConstant.PARENTDOB3, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getParentDOB4())) {
				objGetQuote.addChildElement(ApplicationConstant.PARENTDOB4, "star").addTextNode(productUtil.getDOB(getQuoteRequestVO.getParent4AgeBandInYears()));
				getQuoteRequestVO.setParentDOB4(productUtil.getDOB(getQuoteRequestVO.getParent4AgeBandInYears()));
			}else {
				objGetQuote.addChildElement(ApplicationConstant.PARENTDOB4, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getInsuranceCover2())) {
				objGetQuote.addChildElement(ApplicationConstant.INSURANCECOVER2, "star").addTextNode(getQuoteRequestVO.getInsuranceCover2().trim());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.INSURANCECOVER2, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getOnlineDiscountPerc())) {
				objGetQuote.addChildElement(ApplicationConstant.ONLINEDISCOUNTPERC, "star").addTextNode(getQuoteRequestVO.getOnlineDiscountPerc());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.ONLINEDISCOUNTPERC, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getAreaCode())) {
				objGetQuote.addChildElement(ApplicationConstant.AREACODE, "star").addTextNode(getQuoteRequestVO.getAreaCode().trim());

			}else {
				objGetQuote.addChildElement(ApplicationConstant.AREACODE, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getAddDiscntYN())) {
				objGetQuote.addChildElement(ApplicationConstant.ADDDISCNTYN, "star").addTextNode(getQuoteRequestVO.getAddDiscntYN().trim());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.ADDDISCNTYN, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getDivnCode())) {
				objGetQuote.addChildElement(ApplicationConstant.DIVNCODE, "star").addTextNode(getQuoteRequestVO.getDivnCode().trim());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.DIVNCODE, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getCategory())) {
				objGetQuote.addChildElement(ApplicationConstant.CATEGORY, "star").addTextNode(getQuoteRequestVO.getCategory().trim());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.CATEGORY, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getBuyBackPedYN())) {
				objGetQuote.addChildElement(ApplicationConstant.BUYBACKPEDYN, "star").addTextNode(getQuoteRequestVO.getBuyBackPedYN());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.BUYBACKPEDYN, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getHospitalCashYN())) {
				objGetQuote.addChildElement(ApplicationConstant.HOSPITALCASHYN, "star").addTextNode(getQuoteRequestVO.getHospitalCashYN().trim());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.HOSPITALCASHYN, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getPatientCareYN())) {
				objGetQuote.addChildElement(ApplicationConstant.PATIENTCAREYN, "star").addTextNode(getQuoteRequestVO.getPatientCareYN().trim());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.PATIENTCAREYN, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getAddOnCvrSec1YN())) {
				objGetQuote.addChildElement(ApplicationConstant.ADDONCVRSEC1YN, "star").addTextNode(getQuoteRequestVO.getAddOnCvrSec1YN().trim());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.ADDONCVRSEC1YN, "star").addTextNode("");
			}
			
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getAdultLumpSumSI())) {
				objGetQuote.addChildElement(ApplicationConstant.ADULTLUMPSUMSI, "star").addTextNode(getQuoteRequestVO.getAdultLumpSumSI().trim());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.ADULTLUMPSUMSI, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getChildLumpSumSI1())) {
				objGetQuote.addChildElement(ApplicationConstant.CHILDLUMPSUMSI1, "star").addTextNode(getQuoteRequestVO.getChildLumpSumSI1().trim());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.CHILDLUMPSUMSI1, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getChildLumpSumSI2())) {
				objGetQuote.addChildElement(ApplicationConstant.CHILDLUMPSUMSI2, "star").addTextNode(getQuoteRequestVO.getChildLumpSumSI2().trim());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.CHILDLUMPSUMSI2, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getChildLumpSumSI3())) {
				objGetQuote.addChildElement(ApplicationConstant.CHILDLUMPSUMSI3, "star").addTextNode(getQuoteRequestVO.getChildLumpSumSI3().trim());
			}else {
				objGetQuote.addChildElement(ApplicationConstant.CHILDLUMPSUMSI3, "star").addTextNode("");
			}

			if(StringUtils.isNotBlank(getQuoteRequestVO.getChildDOB1())) {
				objGetQuote.addChildElement(ApplicationConstant.CHILDDOB1, "star").addTextNode(productUtil.getDOB(getQuoteRequestVO.getChild1AgeBandInYears()));
				getQuoteRequestVO.setChildDOB1(productUtil.getDOB(getQuoteRequestVO.getChild1AgeBandInYears()));
			}else {
				objGetQuote.addChildElement(ApplicationConstant.CHILDDOB1, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getChildDOB2())) {
				objGetQuote.addChildElement(ApplicationConstant.CHILDDOB2, "star").addTextNode(productUtil.getDOB(getQuoteRequestVO.getChild2AgeBandInYears()));
				getQuoteRequestVO.setChildDOB2(productUtil.getDOB(getQuoteRequestVO.getChild2AgeBandInYears()));
			}else {
				objGetQuote.addChildElement(ApplicationConstant.CHILDDOB2, "star").addTextNode("");
			}
			
			if(StringUtils.isNotBlank(getQuoteRequestVO.getChildDOB3())) {
				objGetQuote.addChildElement(ApplicationConstant.CHILDDOB3, "star").addTextNode(productUtil.getDOB(getQuoteRequestVO.getChild3AgeBandInYears()));
				getQuoteRequestVO.setChildDOB3(productUtil.getDOB(getQuoteRequestVO.getChild3AgeBandInYears()));
			}else {
				objGetQuote.addChildElement(ApplicationConstant.CHILDDOB3, "star").addTextNode("");
			}
			
			MimeHeaders headers = message.getMimeHeaders();
			headers.addHeader("SOAPAction", "http://starhealth.in/GetQuote");

			log.info("getQuote_URL = {}" , getQuoteURL);
			URL endpoint = new URL(getQuoteURL);
			log.info("SOAP Request 1 = {}" , fancyPrintXML(message));
			log.info("endpoint = {}" , endpoint);
			
			long startTime = System.currentTimeMillis();
			SOAPMessage response = connection.call(message, endpoint);
			connection.close();
			long endTime = System.currentTimeMillis();
			float sec = (endTime - startTime) / 1000F;
			log.debug("SOAP Call Ends in {} Seconds" , sec );

			log.info("SOAP Response 1= {}" , fancyPrintXML(response));
			
			getQuoteResponse = getQuoteResponse(response);
			log.info("GetQuoteResponse ={} ",productUtil.doMarshallingJSON(getQuoteResponse));
			log.info("Get Quote SOAP Service End");
		} catch (Exception e) {
			log.info("Error in getQuotes {}",e.getMessage());
			e.printStackTrace();
		}
		
		return getQuoteResponse;
	}
	
	public GetQuoteSOAPResponse getQuoteOnDemand(GetQuoteRequestVO getQuoteRequestVO) throws Exception, SOAPException {
		log.info("Get Quote SOAP Service Starts");
		GetQuoteSOAPResponse getQuoteResponse ;
		
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection connection = soapConnectionFactory.createConnection();

		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory.createMessage();

		SOAPPart part = message.getSOAPPart();
		SOAPEnvelope envelope = part.getEnvelope();
		envelope.addNamespaceDeclaration("star", "http://starhealth.in/");

		SOAPBody body = message.getSOAPBody();

		SOAPElement getQuote = body.addChildElement("GetQuote", "star");
		SOAPElement objGetQuote = getQuote.addChildElement("objGetQuote", "star");
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getProductCode())) {
			objGetQuote.addChildElement(ApplicationConstant.PRODUCTID, "star").addTextNode(getQuoteRequestVO.getProductCode());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.PRODUCTID, "star").addTextNode("");
		}
		if(StringUtils.isNotBlank(getQuoteRequestVO.getInsuranceCover())) {
			objGetQuote.addChildElement(ApplicationConstant.INSURANCECOVER, "star").addTextNode(getQuoteRequestVO.getInsuranceCover());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.INSURANCECOVER, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getDeduction())) {
			objGetQuote.addChildElement(ApplicationConstant.DEDUCTION, "star").addTextNode(getQuoteRequestVO.getDeduction());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.DEDUCTION, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getAdults())) {
			objGetQuote.addChildElement(ApplicationConstant.ADULTS, "star").addTextNode(getQuoteRequestVO.getAdults());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.ADULTS, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getChild())) {
			objGetQuote.addChildElement(ApplicationConstant.CHILD, "star").addTextNode(getQuoteRequestVO.getChild());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.CHILD, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getSchemeCode())) {
			objGetQuote.addChildElement(ApplicationConstant.SCHEMECODE, "star").addTextNode(getQuoteRequestVO.getSchemeCode());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.SCHEMECODE, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getAdultDOB1())) {
			objGetQuote.addChildElement(ApplicationConstant.ADULTDOB1, "star").addTextNode(getQuoteRequestVO.getAdultDOB1());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.ADULTDOB1, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getAdultDOB2())) {
			objGetQuote.addChildElement(ApplicationConstant.ADULTDOB2, "star").addTextNode(getQuoteRequestVO.getAdultDOB2());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.ADULTDOB2, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getPinCode())) {
			objGetQuote.addChildElement(ApplicationConstant.PINCODE, "star").addTextNode(getQuoteRequestVO.getPinCode());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.PINCODE, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getPeriod())) {
			objGetQuote.addChildElement(ApplicationConstant.PERIOD, "star").addTextNode(getQuoteRequestVO.getPeriod());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.PERIOD, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getOption1())) {
			objGetQuote.addChildElement(ApplicationConstant.OPTION, "star").addTextNode(getQuoteRequestVO.getOption1().trim());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.OPTION, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getPlan())) {
			objGetQuote.addChildElement(ApplicationConstant.PLAN, "star").addTextNode(getQuoteRequestVO.getPlan());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.PLAN, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getPolFmDt())) {
			objGetQuote.addChildElement(ApplicationConstant.POLFMDT, "star").addTextNode(getQuoteRequestVO.getPolFmDt().trim());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.POLFMDT, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getParents())) {
			objGetQuote.addChildElement(ApplicationConstant.PARENTS, "star").addTextNode(getQuoteRequestVO.getParents());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.PARENTS, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getParentDOB1())) {
			objGetQuote.addChildElement(ApplicationConstant.PARENTDOB1, "star").addTextNode(getQuoteRequestVO.getParentDOB1());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.PARENTDOB1, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getParentDOB2())) {
			objGetQuote.addChildElement(ApplicationConstant.PARENTDOB2, "star").addTextNode(getQuoteRequestVO.getParentDOB2());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.PARENTDOB2, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getParentDOB3())) {
			objGetQuote.addChildElement(ApplicationConstant.PARENTDOB3, "star").addTextNode(getQuoteRequestVO.getParentDOB3());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.PARENTDOB3, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getParentDOB4())) {
			objGetQuote.addChildElement(ApplicationConstant.PARENTDOB4, "star").addTextNode(getQuoteRequestVO.getParentDOB4());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.PARENTDOB4, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getInsuranceCover2())) {
			objGetQuote.addChildElement(ApplicationConstant.INSURANCECOVER2, "star").addTextNode(getQuoteRequestVO.getInsuranceCover2().trim());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.INSURANCECOVER2, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getOnlineDiscountPerc())) {
			objGetQuote.addChildElement(ApplicationConstant.ONLINEDISCOUNTPERC, "star").addTextNode(getQuoteRequestVO.getOnlineDiscountPerc());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.ONLINEDISCOUNTPERC, "star").addTextNode("");
		}

		if(StringUtils.isNotBlank(getQuoteRequestVO.getAreaCode())) {
			objGetQuote.addChildElement(ApplicationConstant.AREACODE, "star").addTextNode(getQuoteRequestVO.getAreaCode().trim());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.AREACODE, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getAddDiscntYN())) {
			objGetQuote.addChildElement(ApplicationConstant.ADDDISCNTYN, "star").addTextNode(getQuoteRequestVO.getAddDiscntYN().trim());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.ADDDISCNTYN, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getDivnCode())) {
			objGetQuote.addChildElement(ApplicationConstant.DIVNCODE, "star").addTextNode(getQuoteRequestVO.getDivnCode().trim());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.DIVNCODE, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getCategory())) {
			objGetQuote.addChildElement(ApplicationConstant.CATEGORY, "star").addTextNode(getQuoteRequestVO.getCategory().trim());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.CATEGORY, "star").addTextNode("");
		}

		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getBuyBackPedYN())) {
			objGetQuote.addChildElement(ApplicationConstant.BUYBACKPEDYN, "star").addTextNode(getQuoteRequestVO.getBuyBackPedYN());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.BUYBACKPEDYN, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getHospitalCashYN())) {
			objGetQuote.addChildElement(ApplicationConstant.HOSPITALCASHYN, "star").addTextNode(getQuoteRequestVO.getHospitalCashYN().trim());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.HOSPITALCASHYN, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getPatientCareYN())) {
			objGetQuote.addChildElement(ApplicationConstant.PATIENTCAREYN, "star").addTextNode(getQuoteRequestVO.getPatientCareYN().trim());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.PATIENTCAREYN, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getAddOnCvrSec1YN())) {
			objGetQuote.addChildElement(ApplicationConstant.ADDONCVRSEC1YN, "star").addTextNode(getQuoteRequestVO.getAddOnCvrSec1YN().trim());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.ADDONCVRSEC1YN, "star").addTextNode("");
		}
		
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getAdultLumpSumSI())) {
			objGetQuote.addChildElement(ApplicationConstant.ADULTLUMPSUMSI, "star").addTextNode(getQuoteRequestVO.getAdultLumpSumSI().trim());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.ADULTLUMPSUMSI, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getChildLumpSumSI1())) {
			objGetQuote.addChildElement(ApplicationConstant.CHILDLUMPSUMSI1, "star").addTextNode(getQuoteRequestVO.getChildLumpSumSI1().trim());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.CHILDLUMPSUMSI1, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getChildLumpSumSI2())) {
			objGetQuote.addChildElement(ApplicationConstant.CHILDLUMPSUMSI2, "star").addTextNode(getQuoteRequestVO.getChildLumpSumSI2().trim());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.CHILDLUMPSUMSI2, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getChildLumpSumSI3())) {
			objGetQuote.addChildElement(ApplicationConstant.CHILDLUMPSUMSI3, "star").addTextNode(getQuoteRequestVO.getChildLumpSumSI3().trim());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.CHILDLUMPSUMSI3, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getChildDOB1())) {
			objGetQuote.addChildElement(ApplicationConstant.CHILDDOB1, "star").addTextNode(getQuoteRequestVO.getChildDOB1());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.CHILDDOB1, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getChildDOB2())) {
			objGetQuote.addChildElement(ApplicationConstant.CHILDDOB2, "star").addTextNode(getQuoteRequestVO.getChildDOB2());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.CHILDDOB2, "star").addTextNode("");
		}
		
		if(StringUtils.isNotBlank(getQuoteRequestVO.getChildDOB3())) {
			objGetQuote.addChildElement(ApplicationConstant.CHILDDOB3, "star").addTextNode(getQuoteRequestVO.getChildDOB3());
		}else {
			objGetQuote.addChildElement(ApplicationConstant.CHILDDOB3, "star").addTextNode("");
		}
		
		MimeHeaders headers = message.getMimeHeaders();
		headers.addHeader("SOAPAction", "http://starhealth.in/GetQuote");

		log.info("getQuote_URL = {}" , getQuoteURL);
		URL endpoint = new URL(getQuoteURL);
		log.info("SOAP Request = {}" , getXmlFromSOAPMessage(message));
		log.info("SOAP Request = {}" , fancyPrintXML(message));
		log.info("endpoint = {}" , endpoint);
		
		long startTime = System.currentTimeMillis();
		SOAPMessage response = connection.call(message, endpoint);
		connection.close();
		long endTime = System.currentTimeMillis();
		float sec = (endTime - startTime) / 1000F;
		log.debug("OnDemand SOAP Call Ends in {} Seconds" , sec );
		
		log.info("SOAP Response = {}" , getXmlFromSOAPMessage(response));
		log.info("SOAP Response = {}" , fancyPrintXML(response));
		
		getQuoteResponse = getQuoteResponse(response);
		log.info("GetQuoteResponse = {}",productUtil.doMarshallingJSON(getQuoteResponse));
		log.info("Get Quote SOAP Service End");
		
		return getQuoteResponse;
	}

	private GetQuoteSOAPResponse getQuoteResponse(SOAPMessage response)  {

		GetQuoteSOAPResponse getQuoteResponse = null;
		try {
			getQuoteResponse = new GetQuoteSOAPResponse();
			
			SOAPBody soapBody = response.getSOAPBody();
			NodeList nodesGetQuoteResponse = soapBody.getElementsByTagName("GetQuoteResponse");
			NodeList nodesGetQuoteResult = null;
			for (int i = 0; i < nodesGetQuoteResponse.getLength(); i++) {
				Node nodeGetQuickQuoteResult = (Node) nodesGetQuoteResponse.item(i);
				nodesGetQuoteResult = nodeGetQuickQuoteResult.getChildNodes();
				log.info("GetQuoteResponse child = {}", nodesGetQuoteResponse.item(i).getFirstChild().getNodeName());
				break;
			}

			NodeList getQuickQuoteResults = null;
			if (nodesGetQuoteResult != null) {
				for (int i = 0; i < nodesGetQuoteResult.getLength(); i++) {
					Node getQuickQuoteResult = (Node) nodesGetQuoteResult.item(i);
					if (getQuickQuoteResult.getNodeName().equalsIgnoreCase("GetQuoteResult")) {
						getQuickQuoteResults = getQuickQuoteResult.getChildNodes();
						break;
					}
				}
			}

			if (getQuickQuoteResults != null) {
				for (int i = 0; i < getQuickQuoteResults.getLength(); i++) {
					Node node = (Node) getQuickQuoteResults.item(i);
					if (node.getNodeName().equals("PremiumAmount") && node.getTextContent() != null) {
						getQuoteResponse.setPremiumAmount(node.getTextContent());
					} else if (node.getNodeName().equals("PremiumTax") && node.getTextContent() != null) {
						getQuoteResponse.setPremiumTax(node.getTextContent());
					} else if (node.getNodeName().equals("TotalAmount") && node.getTextContent() != null) {
						getQuoteResponse.setTotalAmount(node.getTextContent());
					} else if (node.getNodeName().equals("Message") && node.getTextContent() != null) {
						getQuoteResponse.setMessage(node.getTextContent());
					} else if (node.getNodeName().equals("HlflyPremAmntFirstPrem") && node.getTextContent() != null) {
						getQuoteResponse.setHlflyPremAmntFirstPrem(node.getTextContent());
					} else if (node.getNodeName().equals("HlflyPremAmntFirstTax") && node.getTextContent() != null) {
						getQuoteResponse.setHlflyPremAmntFirstTax(node.getTextContent());
					} else if (node.getNodeName().equals("HlflyPremAmntFirstTotal") && node.getTextContent() != null) {
						getQuoteResponse.setHlflyPremAmntFirstTotal(node.getTextContent());
					} else if (node.getNodeName().equals("HlflyPremAmntOthersTax") && node.getTextContent() != null) {
						getQuoteResponse.setHlflyPremAmntOthersTax(node.getTextContent());
					} else if (node.getNodeName().equals("HlflyPremAmntOthersTotal") && node.getTextContent() != null) {
						getQuoteResponse.setHlflyPremAmntOthersTotal(node.getTextContent());
					} else if (node.getNodeName().equals("QtrlyPremAmntFirstPrem") && node.getTextContent() != null) {
						getQuoteResponse.setQtrlyPremAmntFirstPrem(node.getTextContent());
					} else if (node.getNodeName().equals("QtrlyPremAmntFirstTax") && node.getTextContent() != null) {
						getQuoteResponse.setQtrlyPremAmntFirstTax(node.getTextContent());
					} else if (node.getNodeName().equals("QtrlyPremAmntFirstTotal") && node.getTextContent() != null) {
						getQuoteResponse.setQtrlyPremAmntFirstTotal(node.getTextContent());
					} else if (node.getNodeName().equals("QtrlyPremAmntOthersPrem") && node.getTextContent() != null) {
						getQuoteResponse.setQtrlyPremAmntOthersPrem(node.getTextContent());
					}else if (node.getNodeName().equals("QtrlyPremAmntOthersTax") && node.getTextContent() != null) {
						getQuoteResponse.setQtrlyPremAmntOthersTax(node.getTextContent());
					}else if (node.getNodeName().equals("QtrlyPremAmntOthersTotal") && node.getTextContent() != null) {
						getQuoteResponse.setQtrlyPremAmntOthersTotal(node.getTextContent());
					}else if (node.getNodeName().equals("MnthlyPremAmntFirstPrem") && node.getTextContent() != null) {
						getQuoteResponse.setMnthlyPremAmntFirstPrem(node.getTextContent());
					}else if (node.getNodeName().equals("MnthlyPremAmntFirstTax") && node.getTextContent() != null) {
						getQuoteResponse.setMnthlyPremAmntFirstTax(node.getTextContent());
					}else if (node.getNodeName().equals("MnthlyPremAmntFirstTotal") && node.getTextContent() != null) {
						getQuoteResponse.setMnthlyPremAmntFirstTotal(node.getTextContent());
					}else if (node.getNodeName().equals("MnthlyPremAmntOthersPrem") && node.getTextContent() != null) {
						getQuoteResponse.setMnthlyPremAmntOthersPrem(node.getTextContent());
					}else if (node.getNodeName().equals("MnthlyPremAmntOthersTax") && node.getTextContent() != null) {
						getQuoteResponse.setMnthlyPremAmntOthersTax(node.getTextContent());
					}else if (node.getNodeName().equals("MnthlyPremAmntOthersTotal") && node.getTextContent() != null) {
						getQuoteResponse.setMnthlyPremAmntOthersTotal(node.getTextContent());
					}else if (node.getNodeName().equals("TotalRuralDiscountAmt") && node.getTextContent() != null) {
						getQuoteResponse.setTotalRuralDiscountAmt(node.getTextContent());
					}else if (node.getNodeName().equals("TotalOnlineDiscountAmt") && node.getTextContent() != null) {
						getQuoteResponse.setTotalOnlineDiscountAmt(node.getTextContent());
					}else if (node.getNodeName().equals("TotalAddDiscnt") && node.getTextContent() != null) {
						getQuoteResponse.setTotalAddDiscnt(node.getTextContent());
					}else if (node.getNodeName().equals("BuyBackPrem") && node.getTextContent() != null) {
						getQuoteResponse.setBuyBackPrem(node.getTextContent());
					}else if (node.getNodeName().equals("HospitalCashPrem") && node.getTextContent() != null) {
						getQuoteResponse.setHospitalCashPrem(node.getTextContent());
					}else if (node.getNodeName().equals("PatientCarePrem") && node.getTextContent() != null) {
						getQuoteResponse.setPatientCarePrem(node.getTextContent());
					}else if (node.getNodeName().equals("AddOnCvrSec1Prem") && node.getTextContent() != null) {
						getQuoteResponse.setAddOnCvrSec1Prem(node.getTextContent());
					}else if (node.getNodeName().equals("AddOnCvrSec2Prem") && node.getTextContent() != null) {
						getQuoteResponse.setAddOnCvrSec2Prem(node.getTextContent());
					}
					
				}
			}
		} catch (DOMException | SOAPException e) {
log.error("Error in getQuoteResponse {}",e.getMessage());
			e.printStackTrace();
		}
		
		return getQuoteResponse;
	}
	
}
