/**
 * 
 */
package com.shi.products.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.shi.products.client.req.GetQuoteSOAPRequest;
import com.shi.products.client.res.GetQuoteSOAPResponse;
import com.shi.products.dao.ProductsDAO;
import com.shi.products.exception.FaultException;
import com.shi.products.repo.GetQuoteRequestRepo;
import com.shi.products.repo.GetQuoteResponseRepo;
import com.shi.products.repo.ProductsAdultAgeRepo;
import com.shi.products.repo.ProductsChildAgeRepo;
import com.shi.products.repo.ProductsFamilyCombinationsRepo;
import com.shi.products.repo.ProductsParentAndParentInLawAgeRepo;
import com.shi.products.repo.ProductsSumInsureRepo;
import com.shi.products.util.ApplicationConstant;
import com.shi.products.util.ProductUtil;
import com.shi.products.vo.GetQuoteRequestVO;
import com.shi.products.vo.GetQuoteResponseVO;
import com.shi.products.vo.ProductsAdultAgeVO;
import com.shi.products.vo.ProductsChildAgeVO;
import com.shi.products.vo.ProductsFamilyCombinationsVO;
import com.shi.products.vo.ProductsParentAndParentInLawAgeVO;
import com.shi.products.vo.ProductsSumInsureVO;

/**
 * @author suman.raju
 *
 */
@Service
public class ProductsMetaDataService {
	private static final Logger log = LoggerFactory.getLogger(ProductsMetaDataService.class);

	@Autowired
	Environment environment;

	@Autowired
	ProductsDAO productDAO;

	@Autowired
	ProductUtil productUtil;

	@Autowired
	ProductsFamilyCombinationsRepo productsFamilyCombinationsRepo;

	@Autowired
	ProductsSumInsureRepo productsSumInsureRepo;

	@Autowired
	ProductsAdultAgeRepo productsAdultAgeRepo;

	@Autowired
	ProductsChildAgeRepo productsChildAgeRepo;

	@Autowired
	ProductsParentAndParentInLawAgeRepo productsParentAndParentInLawAgeRepo;

	@Autowired
	GetQuoteRequestRepo getQuoteRequestRepo;

	@Autowired
	GetQuoteSOAPRequest soapClient;

	@Autowired
	GetQuoteResponseRepo getQuoteResponseRepo;
	
	@Value("${MediClassic_ProductCode}")
	private String mediclassicProductcode;
	
	@Value("${Family_Health_Optima_ProductCode}")
	private String familyHealthOptimaProductcode;
	
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

	public void uploadProductsFamilyCombination(XSSFSheet worksheet) {
		log.info("Read Product Meta Data Family Combination Starts ::");
		String nameP = null;
		int rowNum = 0;
		String colNum = "";
		ArrayList<ProductsFamilyCombinationsVO> productsFamilyCombinationsVOs = new ArrayList<>();
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ApplicationConstant.DATETIMEFORMAT);
			LocalDateTime now = LocalDateTime.now();
			log.info("Date Format :: {}" , dtf.format(now));
			String batchId = dtf.format(now);
			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				ProductsFamilyCombinationsVO productsFamilyCombinationsVO = new ProductsFamilyCombinationsVO();

				productsFamilyCombinationsVO.setBatchId(batchId);

				rowNum = i;
				log.info("Row Num= {}" , rowNum);
				XSSFRow row = worksheet.getRow(i);

				colNum = "A";
				String productCode = row.getCell(0).getStringCellValue();
				nameP = productCode;
				productsFamilyCombinationsVO.setProductCode(productCode.trim());
				log.info(productCode);

				String productName = "";
				colNum = "B";
				if (row.getCell(1) != null) {
					XSSFCell xSSFCell = row.getCell(1);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						productName = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						productName = (row.getCell(1).getStringCellValue() == null) ? " "
								: row.getCell(1).getStringCellValue();
				}
				log.info(productName);
				if (StringUtils.isNotBlank(productName)) {
					productsFamilyCombinationsVO.setProductName(productName.trim());
				}

				String adult = "";
				colNum = "C";
				if (row.getCell(2) != null) {
					XSSFCell xSSFCell = row.getCell(2);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						adult = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						adult = (row.getCell(2).getStringCellValue() == null) ? " "
								: row.getCell(2).getStringCellValue();
				}
				if (StringUtils.isNotBlank(adult)) {
					productsFamilyCombinationsVO.setAdult(Integer.parseInt(adult));
				}
				log.info("adult {} " , adult);

				String child = "";
				colNum = "D";
				if (row.getCell(3) != null) {
					XSSFCell xSSFCell = row.getCell(3);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						child = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						child = (row.getCell(3).getStringCellValue() == null) ? " "
								: row.getCell(3).getStringCellValue();
				}
				if (StringUtils.isNotBlank(child)) {
					productsFamilyCombinationsVO.setChild(Integer.parseInt(child));
				}
				log.info("child = {}" , child);

				String parentAndParentInlaw = "";
				colNum = "E";
				if (row.getCell(4) != null) {
					XSSFCell xSSFCell = row.getCell(4);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						parentAndParentInlaw = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						parentAndParentInlaw = (row.getCell(4).getStringCellValue() == null) ? " "
								: row.getCell(4).getStringCellValue();
				}
				if (StringUtils.isNotBlank(parentAndParentInlaw)) {
					productsFamilyCombinationsVO.setParentAndParentInlaw(Integer.parseInt(parentAndParentInlaw));
				}

				log.info("parentAndParentInlaw {} " , parentAndParentInlaw);

				productsFamilyCombinationsVO.setIsActive("TRUE");
				productsFamilyCombinationsVO.setFlag("0");

				productsFamilyCombinationsVOs.add(productsFamilyCombinationsVO);
			}

			if (CollectionUtils.isNotEmpty(productsFamilyCombinationsVOs)) {
				productsFamilyCombinationsRepo.saveAll(productsFamilyCombinationsVOs);
				log.info("Read Product Meta Data Family Combination End {}" , productsFamilyCombinationsVOs.size());
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new FaultException(
					"FamilyCombination Unable To Read The Data... Row Number '" + rowNum + "' FamilyCombination Column  '" + colNum + "' FamilyCombination Name : " + nameP);
		}

		log.info("Read File End :: {}" , rowNum);
	}

	public void uploadProductsSumInsure(XSSFSheet worksheet) {

		log.info("Read Product Meta Data SumInsure Starts ::");
		String nameP = null;
		int rowNum = 0;
		String colNum = "";
		ArrayList<ProductsSumInsureVO> productsYearAndSumInsureVOs = new ArrayList<ProductsSumInsureVO>();
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ApplicationConstant.DATETIMEFORMAT);
			LocalDateTime now = LocalDateTime.now();
			log.info("SumInsure Date Format :: {} " , dtf.format(now));
			String batchId = dtf.format(now);
			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				ProductsSumInsureVO productsSumInsureVO = new ProductsSumInsureVO();

				productsSumInsureVO.setBatchId(batchId);

				rowNum = i;
				log.info("SumInsure Row Num={} " , rowNum);
				XSSFRow row = worksheet.getRow(i);

				colNum = "A";
				String productCode = row.getCell(0).getStringCellValue();
				nameP = productCode;
				productsSumInsureVO.setProductCode(productCode.trim());
				log.info(productCode);

				String productName = "";
				colNum = "B";
				if (row.getCell(1) != null) {
					XSSFCell xSSFCell = row.getCell(1);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						productName = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						productName = (row.getCell(1).getStringCellValue() == null) ? " "
								: row.getCell(1).getStringCellValue();
				}
				log.info(productName);
				if (StringUtils.isNotBlank(productName)) {
					productsSumInsureVO.setProductName(productName.trim());
				}

				String period = "";
				colNum = "C";
				if (row.getCell(2) != null) {
					XSSFCell xSSFCell = row.getCell(2);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						period = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						period = (row.getCell(2).getStringCellValue() == null) ? " "
								: row.getCell(2).getStringCellValue();
				}
				if (StringUtils.isNotBlank(period)) {
					productsSumInsureVO.setPeriod(period.trim());
				}
				log.info("Period {} " , period);

				String planType = "";
				colNum = "D";
				if (row.getCell(3) != null) {
					XSSFCell xSSFCell = row.getCell(3);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						planType = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						planType = (row.getCell(3).getStringCellValue() == null) ? " "
								: row.getCell(3).getStringCellValue();
				}
				if (StringUtils.isNotBlank(planType)) {
					productsSumInsureVO.setPlanType(planType.trim());
				}
				log.info("PlanType {} " , planType);

				String zone = "";
				colNum = "E";
				if (row.getCell(4) != null) {
					XSSFCell xSSFCell = row.getCell(4);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						zone = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						zone = (row.getCell(4).getStringCellValue() == null) ? " "
								: row.getCell(4).getStringCellValue();
				}
				if (StringUtils.isNotBlank(zone)) {
					productsSumInsureVO.setZone(zone.trim());
				}

				log.info("zone {} " , zone);

				String pinCode = "";
				colNum = "F";
				if (row.getCell(5) != null) {
					XSSFCell xSSFCell = row.getCell(5);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						pinCode = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						pinCode = (row.getCell(5).getStringCellValue() == null) ? " "
								: row.getCell(5).getStringCellValue();
				}
				if (StringUtils.isNotBlank(pinCode)) {
					pinCode = StringUtils.deleteWhitespace(pinCode);
					productsSumInsureVO.setPinCode(pinCode);
				}
				log.info("pinCode {} " , pinCode);

				String ped = "";
				colNum = "G";
				if (row.getCell(6) != null) {
					XSSFCell xSSFCell = row.getCell(6);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						ped = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						ped = (row.getCell(6).getStringCellValue() == null) ? " " : row.getCell(6).getStringCellValue();
				}
				if (StringUtils.isNotBlank(ped)) {
					productsSumInsureVO.setPED(StringUtils.deleteWhitespace(ped));
				}
				log.info("PED {} " , ped);

				String deduction = "";
				colNum = "H";
				if (row.getCell(7) != null) {
					XSSFCell xSSFCell = row.getCell(7);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						deduction = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						deduction = (row.getCell(7).getStringCellValue() == null) ? " "
								: row.getCell(7).getStringCellValue();
				}
				if (StringUtils.isNotBlank(deduction)) {
					productsSumInsureVO.setDeduction(Integer.parseInt(StringUtils.deleteWhitespace(deduction)));
				}
				log.info("deduction {} " , deduction);

				String onlineDiscountPerc = "";
				colNum = "I";
				if (row.getCell(8) != null) {
					XSSFCell xSSFCell = row.getCell(8);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						onlineDiscountPerc = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						onlineDiscountPerc = (row.getCell(8).getStringCellValue() == null) ? " "
								: row.getCell(8).getStringCellValue();
				}
				if (StringUtils.isNotBlank(onlineDiscountPerc)) {
					productsSumInsureVO.setOnlineDiscountPerc(onlineDiscountPerc.trim());
				}

				log.info("OnlineDiscountPerc {} " , onlineDiscountPerc);

				String sumInsure = "";
				colNum = "J";
				if (row.getCell(9) != null) {
					XSSFCell xSSFCell = row.getCell(9);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						sumInsure = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						sumInsure = (row.getCell(9).getStringCellValue() == null) ? " "
								: row.getCell(9).getStringCellValue();
				}
				if (StringUtils.isNotBlank(sumInsure)) {
					productsSumInsureVO.setSumInsure(Integer.parseInt(StringUtils.deleteWhitespace(sumInsure)));
				}
				log.info("sumInsure {} " , sumInsure);

				productsSumInsureVO.setIsActive("TRUE");
				productsSumInsureVO.setFlag("0");

				productsYearAndSumInsureVOs.add(productsSumInsureVO);
			}

			if (CollectionUtils.isNotEmpty(productsYearAndSumInsureVOs)) {
				productsSumInsureRepo.saveAll(productsYearAndSumInsureVOs);
				log.info("Read Product Meta Data SumInsure End {} " , productsYearAndSumInsureVOs.size());
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new FaultException(
					"SumInsure Unable To Read The Data... Row Number '" + rowNum + "' SumInsure Column  '" + colNum + "' SumInsure Name : " + nameP);
		}

		log.info("SumInsure Read File End ::{} " , rowNum);
	}

	public void uploadProductsAdultAgeBandInYears(XSSFSheet adultAgeBandInYears) {
		log.info("Read Product Meta Data Adult AgeBandInYears Starts ::");
		String nameP = null;
		int rowNum = 0;
		String colNum = "";
		ArrayList<ProductsAdultAgeVO> productsAdultAgeVOs = new ArrayList<ProductsAdultAgeVO>();
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ApplicationConstant.DATETIMEFORMAT);
			LocalDateTime now = LocalDateTime.now();
			log.info("AdultAgeBand Date Format :: {} " , dtf.format(now));
			String batchId = dtf.format(now);
			for (int i = 1; i < adultAgeBandInYears.getPhysicalNumberOfRows(); i++) {
				ProductsAdultAgeVO productsAdultAgeVO = new ProductsAdultAgeVO();

				productsAdultAgeVO.setBatchId(batchId);

				rowNum = i;
				log.info("AdultAgeBand Row Num={} " , rowNum);
				XSSFRow row = adultAgeBandInYears.getRow(i);

				colNum = "A";
				String productCode = row.getCell(0).getStringCellValue();
				nameP = productCode;
				productsAdultAgeVO.setProductCode(productCode.trim());
				log.info(productCode);

				String productName = "";
				colNum = "B";
				if (row.getCell(1) != null) {
					XSSFCell xSSFCell = row.getCell(1);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						productName = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						productName = (row.getCell(1).getStringCellValue() == null) ? " "
								: row.getCell(1).getStringCellValue();
				}
				log.info(productName);
				if (StringUtils.isNotBlank(productName)) {
					productsAdultAgeVO.setProductName(productName.trim());
				}

				String period = "";
				colNum = "C";
				if (row.getCell(2) != null) {
					XSSFCell xSSFCell = row.getCell(2);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						period = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						period = (row.getCell(2).getStringCellValue() == null) ? " "
								: row.getCell(2).getStringCellValue();
				}
				if (StringUtils.isNotBlank(period)) {
					productsAdultAgeVO.setPeriod(period.trim());
				}
				log.info("AdultAgeBand period = {}" , period);

				String ageBandInYears = "";
				colNum = "D";
				if (row.getCell(3) != null) {
					XSSFCell xSSFCell = row.getCell(3);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						ageBandInYears = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						ageBandInYears = (row.getCell(3).getStringCellValue() == null) ? " "
								: row.getCell(3).getStringCellValue();
				}
				if (StringUtils.isNotBlank(ageBandInYears)) {
					productsAdultAgeVO.setAgeBandInYears(ageBandInYears.trim());
				}
				log.info("ageBandInYears = {}" , ageBandInYears);
				
				String schemeCode = "";
				colNum = "E";
				if (row.getCell(4) != null) {
					XSSFCell xSSFCell = row.getCell(4);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						schemeCode = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						schemeCode = (row.getCell(4).getStringCellValue() == null) ? " "
								: row.getCell(4).getStringCellValue();
				}
				if (StringUtils.isNotBlank(schemeCode)) {
					productsAdultAgeVO.setSchemeCode(schemeCode.trim());
				}
				log.info("schemeCode {} " , schemeCode);
				
				String plan = "";
				colNum = "F";
				if (row.getCell(5) != null) {
					XSSFCell xSSFCell = row.getCell(5);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						plan = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						plan = (row.getCell(5).getStringCellValue() == null) ? " "
								: row.getCell(5).getStringCellValue();
				}
				if (StringUtils.isNotBlank(plan)) {
					productsAdultAgeVO.setPlan(plan.trim());
				}
				log.info("plan {} " , plan);

				productsAdultAgeVO.setIsActive("TRUE");
				productsAdultAgeVO.setFlag("0");

				productsAdultAgeVOs.add(productsAdultAgeVO);
			}

			if (CollectionUtils.isNotEmpty(productsAdultAgeVOs)) {
				productsAdultAgeRepo.saveAll(productsAdultAgeVOs);
				log.info("Read Product Meta Data Adult AgeBandInYears End {} " , productsAdultAgeVOs.size());
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new FaultException(
					"AdultAgeBand Unable To Read The Data... Row Number '" + rowNum + "' AdultAgeBand Column  '" + colNum + "' AdultAgeBand Name : " + nameP);
		}

		log.info("AdultAgeBand Read File End ::{} " , rowNum);
	}

	public void uploadProductsChildAgeBandInYears(XSSFSheet childAgeBandInYears) {
		log.info("Read Product Meta Data Child AgeBandInYears Starts ::");
		String nameP = null;
		int rowNum = 0;
		String colNum = "";
		ArrayList<ProductsChildAgeVO> productsChildAgeVOs = new ArrayList<ProductsChildAgeVO>();
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ApplicationConstant.DATETIMEFORMAT);
			LocalDateTime now = LocalDateTime.now();
			log.info("ChildAgeBand Date Format :: {} " , dtf.format(now));
			String batchId = dtf.format(now);
			for (int i = 1; i < childAgeBandInYears.getPhysicalNumberOfRows(); i++) {
				ProductsChildAgeVO productsChildAgeVO = new ProductsChildAgeVO();

				productsChildAgeVO.setBatchId(batchId);

				rowNum = i;
				log.info("ChildAgeBand Row Num={} " , rowNum);
				XSSFRow row = childAgeBandInYears.getRow(i);

				colNum = "A";
				String productCode = row.getCell(0).getStringCellValue();
				nameP = productCode;
				productsChildAgeVO.setProductCode(productCode.trim());
				log.info(productCode);

				String productName = "";
				colNum = "B";
				if (row.getCell(1) != null) {
					XSSFCell xSSFCell = row.getCell(1);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						productName = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						productName = (row.getCell(1).getStringCellValue() == null) ? " "
								: row.getCell(1).getStringCellValue();
				}
				log.info(productName);
				if (StringUtils.isNotBlank(productName)) {
					productsChildAgeVO.setProductName(productName.trim());
				}

				String period = "";
				colNum = "C";
				if (row.getCell(2) != null) {
					XSSFCell xSSFCell = row.getCell(2);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						period = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						period = (row.getCell(2).getStringCellValue() == null) ? " "
								: row.getCell(2).getStringCellValue();
				}
				if (StringUtils.isNotBlank(period)) {
					productsChildAgeVO.setPeriod(period.trim());
				}
				log.info("ChildAgeBand period = {}" , period);

				String ageBandInYears = "";
				colNum = "D";
				if (row.getCell(3) != null) {
					XSSFCell xSSFCell = row.getCell(3);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						ageBandInYears = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						ageBandInYears = (row.getCell(3).getStringCellValue() == null) ? " "
								: row.getCell(3).getStringCellValue();
				}
				if (StringUtils.isNotBlank(ageBandInYears)) {
					productsChildAgeVO.setAgeBandInYears(ageBandInYears.trim());
				}
				log.info("child = {}" , ageBandInYears);

				productsChildAgeVO.setIsActive("TRUE");
				productsChildAgeVO.setFlag("0");

				productsChildAgeVOs.add(productsChildAgeVO);
			}

			if (CollectionUtils.isNotEmpty(productsChildAgeVOs)) {
				productsChildAgeRepo.saveAll(productsChildAgeVOs);
				log.info("Read Product Meta Data Child AgeBandInYears End {} " , productsChildAgeVOs.size());
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new FaultException(
					"ChildAgeBand Unable To Read The Data... Row Number '" + rowNum + "' ChildAgeBand Column  '" + colNum + "' ChildAgeBand Name : " + nameP);
		}

		log.info("Read File End ::{} " , rowNum);
	}

	public void uploadProductsParentAndParentInLawAgeBandInYears(XSSFSheet parentAgeBandInYears) {
		log.info("Read Product Meta Data Parent and Parent In Law AgeBandInYears Starts ::");
		String nameP = null;
		int rowNum = 0;
		String colNum = "";
		ArrayList<ProductsParentAndParentInLawAgeVO> productsParentAgeVOs = new ArrayList<ProductsParentAndParentInLawAgeVO>();
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ApplicationConstant.DATETIMEFORMAT);
			LocalDateTime now = LocalDateTime.now();
			log.info("Date Format :: {} " , dtf.format(now));
			String batchId = dtf.format(now);
			for (int i = 1; i < parentAgeBandInYears.getPhysicalNumberOfRows(); i++) {
				ProductsParentAndParentInLawAgeVO productsParentAgeVO = new ProductsParentAndParentInLawAgeVO();

				productsParentAgeVO.setBatchId(batchId);

				rowNum = i;
				log.info("ParentAndParentInLawAgeBand Row Num={} " , rowNum);
				XSSFRow row = parentAgeBandInYears.getRow(i);

				colNum = "A";
				String productCode = row.getCell(0).getStringCellValue();
				nameP = productCode;
				productsParentAgeVO.setProductCode(productCode.trim());
				log.info(productCode);

				String productName = "";
				colNum = "B";
				if (row.getCell(1) != null) {
					XSSFCell xSSFCell = row.getCell(1);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						productName = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						productName = (row.getCell(1).getStringCellValue() == null) ? " "
								: row.getCell(1).getStringCellValue();
				}
				log.info(productName);
				if (StringUtils.isNotBlank(productName)) {
					productsParentAgeVO.setProductName(productName.trim());
				}

				String period = "";
				colNum = "C";
				if (row.getCell(2) != null) {
					XSSFCell xSSFCell = row.getCell(2);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						period = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						period = (row.getCell(2).getStringCellValue() == null) ? " "
								: row.getCell(2).getStringCellValue();
				}
				if (StringUtils.isNotBlank(period)) {
					productsParentAgeVO.setPeriod(period.trim());
				}
				log.info("ParentAndParentInLawAgeBand period = {}" , period);

				String ageBandInYears = "";
				colNum = "D";
				if (row.getCell(3) != null) {
					XSSFCell xSSFCell = row.getCell(3);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						ageBandInYears = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						ageBandInYears = (row.getCell(3).getStringCellValue() == null) ? " "
								: row.getCell(3).getStringCellValue();
				}
				if (StringUtils.isNotBlank(ageBandInYears)) {
					productsParentAgeVO.setAgeBandInYears(ageBandInYears.trim());
				}
				log.info("ageBandInYears = {}" , ageBandInYears);

				productsParentAgeVO.setIsActive("TRUE");
				productsParentAgeVO.setFlag("0");

				productsParentAgeVOs.add(productsParentAgeVO);
			}

			if (CollectionUtils.isNotEmpty(productsParentAgeVOs)) {
				productsParentAndParentInLawAgeRepo.saveAll(productsParentAgeVOs);
				log.info("Read Product Meta Data Parent and Parent In Law AgeBandInYears End {}"
						, productsParentAgeVOs.size());
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new FaultException(
					"ParentAndParentInLawAgeBand Unable To Read The Data... Row Number '" + rowNum + "' ParentAndParentInLawAgeBand Column  '" + colNum + "'ParentAndParentInLawAgeBand Name : " + nameP);
		}

		log.info("Read File End ::{} " , rowNum);
	}

	public String buildGetQuoteRequest(String productCode) throws Exception, FaultException {
		log.info("Get Quote Starts ::");
		String status = "SUCCESS";

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ApplicationConstant.DATETIMEFORMAT);
		LocalDateTime now = LocalDateTime.now();
		log.info("Date Format :: {} " , dtf.format(now));
		String batchId = dtf.format(now);

		log.info("Get Product Meta Data FamilyCombinations");
		List<ProductsFamilyCombinationsVO> familyCombinationsVOs = productsFamilyCombinationsRepo.getProductsFamilyCombinations(productCode);
		log.info("Product Meta Data FamilyCombinations Size {} " , familyCombinationsVOs.size());

		int totalPlanCount = 1;
		if (CollectionUtils.isNotEmpty(familyCombinationsVOs)) {
			try {
				productsFamilyCombinationsRepo.updateAllFamilyCombinationsInprogress(productCode);
				for (ProductsFamilyCombinationsVO familyCombinationsVO : familyCombinationsVOs) {
					log.info("FamilyCombinations {} " , familyCombinationsVO);
					productsFamilyCombinationsRepo.updateFamilyCombinationsInprogress(familyCombinationsVO.getId(),
							productCode, familyCombinationsVO.getBatchId());

					log.info("Get Product Meta Data SumInsure");
					List<ProductsSumInsureVO> sumInsureVOs = productsSumInsureRepo.getProductsSumInsure(productCode);
					log.info("Product Meta Data SumInsure Size {} " , sumInsureVOs.size());

					if (CollectionUtils.isNotEmpty(sumInsureVOs)) {
						for (ProductsSumInsureVO sumInsureVO : sumInsureVOs) {
							log.info("YearAndSumInsure {} " , sumInsureVO);

							List<ProductsAdultAgeVO> adultAgeVOs = new ArrayList<>();
							if(healthAssureProductCode.equalsIgnoreCase(productCode)) {
								if(familyCombinationsVO.getAdult()==1 && familyCombinationsVO.getChild()==0
									&& familyCombinationsVO.getParentAndParentInlaw()==0){
									log.info("Get Product Meta Data AdultAge Assure 1A");
									adultAgeVOs = productsAdultAgeRepo.getAssureAdultAge(productCode,	sumInsureVO.getPeriod());
									log.info("Product Meta Data AdultAge Assure Size {} " , adultAgeVOs.size());
								}else {
									log.info("Get Product Meta Data AdultAge Assure ALL");
									adultAgeVOs = productsAdultAgeRepo.getAssureAdultsAge(productCode, sumInsureVO.getPeriod());
									log.info("Product Meta Data AdultAge Assure Size {} " , adultAgeVOs.size());
								}
								
							}else if(mediclassicProductcode.equalsIgnoreCase(productCode)){
								log.info("Get Product Meta Data AdultAge Medi Classic");
								adultAgeVOs = productsAdultAgeRepo.getMediClassicAdultAge(productCode, sumInsureVO.getPeriod(), sumInsureVO.getPlanType());
								log.info("Product Meta Data AdultAge Medi Classic {} " , adultAgeVOs.size());
							}else {
								log.info("Get Product Meta Data AdultAge");
								adultAgeVOs = productsAdultAgeRepo.getProductsAdultAge(productCode, sumInsureVO.getPeriod());
								log.info("Product Meta Data AdultAge Size {} " , adultAgeVOs.size());
							}

							List<ProductsChildAgeVO> childAgeVOs = new ArrayList<>();
							if (familyCombinationsVO.getChild() != null && familyCombinationsVO.getChild() != 0) {
								log.info("Get Product Meta Data ChildAge");
								childAgeVOs = productsChildAgeRepo.getProductsChildAge(productCode,
										sumInsureVO.getPeriod());
								log.info("Product Meta Data ChildAge Size {} " , childAgeVOs.size());
							}

							List<ProductsParentAndParentInLawAgeVO> parentAndParentInLawAgeVOs = new ArrayList<>();
							if (familyCombinationsVO.getParentAndParentInlaw() != null
									&& familyCombinationsVO.getParentAndParentInlaw() != 0) {
								log.info("Get Product Meta Data ParentAndParentInLawAge");
								parentAndParentInLawAgeVOs = productsParentAndParentInLawAgeRepo.getProductsParentAndParentInLawAge(productCode, sumInsureVO.getPeriod());
								log.info("Product Meta Data ParentAndParentInLawAge Size = {}", parentAndParentInLawAgeVOs.size());
							}

							if (CollectionUtils.isNotEmpty(adultAgeVOs)) {
							//	for (ProductsAdultAgeVO adult1AgeVO : adultAgeVOs) {
								for(int n=0;n<adultAgeVOs.size();++n){
									log.info("AdultAge {} " , adultAgeVOs.get(n));

									// Build Get Quote Request
									if (familyCombinationsVO.getChild() != null && familyCombinationsVO.getChild() != 0
											&& familyCombinationsVO.getParentAndParentInlaw() != null
											&& familyCombinationsVO.getParentAndParentInlaw() != 0) {
										log.info("FamilyCombination = Adult + Child + Parent");

										if ((healthAssureProductCode.equalsIgnoreCase(familyCombinationsVO.getProductCode()))) {
											totalPlanCount = getAdultsParentsChildCountInfo(adultAgeVOs, adultAgeVOs.get(n),
													parentAndParentInLawAgeVOs, familyCombinationsVO,
													sumInsureVO, batchId, n, totalPlanCount);

										} else if (CollectionUtils.isNotEmpty(childAgeVOs) && CollectionUtils.isNotEmpty(parentAndParentInLawAgeVOs)) {

											// No Products in this combination

										} else if (CollectionUtils.isNotEmpty(parentAndParentInLawAgeVOs)) {
											if ((arogyaSanjeevaniProductCode.equalsIgnoreCase(familyCombinationsVO.getProductCode()))
													|| (familyHealthOptimaProductcode.equalsIgnoreCase(familyCombinationsVO.getProductCode()))) {

												totalPlanCount = getParentsInfo(parentAndParentInLawAgeVOs,
														familyCombinationsVO, sumInsureVO, adultAgeVOs.get(n), null, batchId, totalPlanCount);

											}
										} else if (CollectionUtils.isNotEmpty(childAgeVOs)) {
											// No Products in this combination
										}
									} else if (familyCombinationsVO.getChild() != null
											&& familyCombinationsVO.getChild() != 0) {
										log.info("FamilyCombination = Adult + Child");

										if ((healthPremierProductCode.equalsIgnoreCase(familyCombinationsVO.getProductCode()))) {
											/*
											 * if (!adultAgeVOs.get(n).getAgeBandInYears().equalsIgnoreCase("Upto-49"))
											 * { // to skip the combination Upto-49 & Upto-49 totalPlanCount =
											 * getAdultsChildInfo(adultAgeVOs, adultAgeVOs.get(n), childAgeVOs,
											 * familyCombinationsVO, sumInsureVO, batchId, n, totalPlanCount); }
											 */
											totalPlanCount = getAdultsChildInfo(adultAgeVOs, adultAgeVOs.get(n),
													childAgeVOs, familyCombinationsVO, sumInsureVO, batchId,
													n, totalPlanCount);

										}else if(healthAssureProductCode.equalsIgnoreCase(familyCombinationsVO.getProductCode())) {
											totalPlanCount = getAdultsChildCountInfo(adultAgeVOs, adultAgeVOs.get(n),
													familyCombinationsVO, sumInsureVO, batchId,	n, totalPlanCount);
										} else if (CollectionUtils.isNotEmpty(childAgeVOs)) {
											totalPlanCount = getChildInfo(childAgeVOs, familyCombinationsVO,
													sumInsureVO, adultAgeVOs.get(n), null, batchId, totalPlanCount);
										} else {
											GetQuoteRequestVO getQuoteRequestVO = buildGetQuoteRequest(
													familyCombinationsVO, sumInsureVO, adultAgeVOs.get(n), null, null, null,
													batchId);
											totalPlanCount = saveGetQuoteRequests(getQuoteRequestVO, totalPlanCount);
										}

									} else if (familyCombinationsVO.getParentAndParentInlaw() != null
											&& familyCombinationsVO.getParentAndParentInlaw() != 0) {
										log.info("FamilyCombination = Adult + Parent");

										if ((healthAssureProductCode.equalsIgnoreCase(familyCombinationsVO.getProductCode()))) {
											totalPlanCount = getAdultsParentsInfo(adultAgeVOs, adultAgeVOs.get(n),
													parentAndParentInLawAgeVOs, familyCombinationsVO, sumInsureVO,
													batchId, n, totalPlanCount);
										} else if (CollectionUtils.isNotEmpty(parentAndParentInLawAgeVOs)) {
											totalPlanCount = getParentsInfo(parentAndParentInLawAgeVOs,
													familyCombinationsVO, sumInsureVO, adultAgeVOs.get(n), null, batchId, totalPlanCount);
										} else {
											// No Products in this combination
										}

									} else if (familyCombinationsVO.getAdult() != 0
											&& (familyCombinationsVO.getChild() == null
													|| familyCombinationsVO.getChild() == 0)
											&& (familyCombinationsVO.getParentAndParentInlaw() == null
													|| familyCombinationsVO.getParentAndParentInlaw() == 0)) {
										log.info("FamilyCombination = Adult");
										if ((healthPremierProductCode.equalsIgnoreCase(familyCombinationsVO.getProductCode()))) {
											/*
											 * if (!adultAgeVOs.get(n).getAgeBandInYears().equalsIgnoreCase("Upto-49"))
											 * { // to skip the combination Upto-49 & Upto-49 totalPlanCount =
											 * getAdultsInfo(adultAgeVOs, adultAgeVOs.get(n), familyCombinationsVO,
											 * sumInsureVO, batchId, n, totalPlanCount); }
											 */
											totalPlanCount = getAdultsInfo(adultAgeVOs, adultAgeVOs.get(n), familyCombinationsVO, 
													sumInsureVO, batchId, n, totalPlanCount);

										}else if(healthAssureProductCode.equalsIgnoreCase(familyCombinationsVO.getProductCode())) {
											totalPlanCount = getAdultsInfo(adultAgeVOs, adultAgeVOs.get(n), familyCombinationsVO, 
													sumInsureVO, batchId, n, totalPlanCount);
										} else {
											GetQuoteRequestVO getQuoteRequestVO = buildGetQuoteRequest(
													familyCombinationsVO, sumInsureVO, adultAgeVOs.get(n), null, null, null,
													batchId);
											totalPlanCount = saveGetQuoteRequests(getQuoteRequestVO, totalPlanCount);
										}
									}
								}
							}
						}
					}
					productsFamilyCombinationsRepo.updateFamilyCombinationsCompleted(familyCombinationsVO.getId(),
							productCode, familyCombinationsVO.getBatchId());

				}
			} catch (Exception e) {
				status = "FAILED";
				log.error("Update Family Combinations flag to Failed ");
				// productsFamilyCombinationsRepo.updateAllFamilyCombinationsFailed(productCode);
				log.error(e.getMessage());
			}
		}

		return status;
	}

	private GetQuoteRequestVO getFamilyCombinationInfo(ProductsFamilyCombinationsVO familyCombinationsVO,
			GetQuoteRequestVO getQuoteRequestVO) {
		getQuoteRequestVO.setFamilyId(familyCombinationsVO.getId());
		getQuoteRequestVO.setProductName(familyCombinationsVO.getProductName());
		getQuoteRequestVO.setProductCode(familyCombinationsVO.getProductCode());
		if (familyCombinationsVO.getAdult() != 0) {
			getQuoteRequestVO.setAdults(String.valueOf(familyCombinationsVO.getAdult()));
		}
		if (familyCombinationsVO.getChild() != 0) {
			getQuoteRequestVO.setChild(String.valueOf(familyCombinationsVO.getChild()));
		}
		if (familyCombinationsVO.getParentAndParentInlaw() != 0) {
			getQuoteRequestVO.setParents(String.valueOf(familyCombinationsVO.getParentAndParentInlaw()));
		}

		if (arogyaSanjeevaniProductCode.equalsIgnoreCase(familyCombinationsVO.getProductCode())) {
			if (familyCombinationsVO.getAdult() == 1 && familyCombinationsVO.getChild() == 0
					&& familyCombinationsVO.getParentAndParentInlaw() == 0) {
				getQuoteRequestVO.setSchemeCode("");
			} else {
				getQuoteRequestVO.setSchemeCode(String.valueOf(familyCombinationsVO.getAdult()).concat("A"));
			}
		} else if (healthAssureProductCode.equalsIgnoreCase(familyCombinationsVO.getProductCode())) {
			if (familyCombinationsVO.getChild() != 0) {
				getQuoteRequestVO.setSchemeCode(String.valueOf(familyCombinationsVO.getAdult()).concat("A").concat("+")
						.concat(String.valueOf(familyCombinationsVO.getChild())).concat("C"));
			} else if (familyCombinationsVO.getAdult() ==1 
					&& familyCombinationsVO.getChild() == 0 
					&& familyCombinationsVO.getParentAndParentInlaw() == 0) {
				getQuoteRequestVO.setSchemeCode("");
			}else {
				getQuoteRequestVO.setSchemeCode(String.valueOf(familyCombinationsVO.getAdult()).concat("A"));
			}

		} else if (familyCombinationsVO.getChild() != 0) {
			getQuoteRequestVO.setSchemeCode(String.valueOf(familyCombinationsVO.getAdult()).concat("A").concat("+")
					.concat(String.valueOf(familyCombinationsVO.getChild())).concat("C"));
		} else if (familyCombinationsVO.getAdult() != 1) {
			getQuoteRequestVO.setSchemeCode(String.valueOf(familyCombinationsVO.getAdult()).concat("A"));
		}

		return getQuoteRequestVO;
	}

	private GetQuoteRequestVO getYearAndSumInsureInfo(ProductsSumInsureVO yearAndSumInsureVO,
			GetQuoteRequestVO getQuoteRequestVO) {
		getQuoteRequestVO.setSumInsureId(yearAndSumInsureVO.getId());
		getQuoteRequestVO.setInsuranceCover(String.valueOf(yearAndSumInsureVO.getSumInsure()));
		getQuoteRequestVO.setPeriod(yearAndSumInsureVO.getPeriod());
		if (yearAndSumInsureVO.getDeduction() != null && yearAndSumInsureVO.getDeduction() > 0) {
			getQuoteRequestVO.setDeduction(String.valueOf(yearAndSumInsureVO.getDeduction()));
		}
		if (StringUtils.isNotBlank(yearAndSumInsureVO.getPlanType())) {
			getQuoteRequestVO.setPlan(yearAndSumInsureVO.getPlanType().trim());
		}
		if (StringUtils.isNotBlank(yearAndSumInsureVO.getPinCode())) {
			getQuoteRequestVO.setPinCode(yearAndSumInsureVO.getPinCode().trim());
			getQuoteRequestVO.setZone(yearAndSumInsureVO.getZone().trim());
		}
		if (StringUtils.isNotBlank(yearAndSumInsureVO.getOnlineDiscountPerc())) {
			getQuoteRequestVO.setOnlineDiscountPerc(yearAndSumInsureVO.getOnlineDiscountPerc().trim());
		}
		if (StringUtils.isNotBlank(yearAndSumInsureVO.getPED())) {
			getQuoteRequestVO.setBuyBackPedYN(yearAndSumInsureVO.getPED().trim());
		}

		return getQuoteRequestVO;
	}

	private GetQuoteRequestVO getAdultAgeInfo(ProductsAdultAgeVO adult1AgeVO, ProductsAdultAgeVO adult2AgeVO,
			GetQuoteRequestVO getQuoteRequestVO) {
		
		String adultAgeList = "";
		String adult1AgeList = productUtil.getAgeList(adult1AgeVO.getAgeBandInYears());
		adultAgeList = StringUtils.deleteWhitespace(adult1AgeList);
		getQuoteRequestVO.setAdult1AgeList(adultAgeList);
		
		getQuoteRequestVO.setAdultAgeId(adult1AgeVO.getId());
		getQuoteRequestVO.setAdultDOB1(productUtil.getDOB(adult1AgeVO.getAgeBandInYears()));
		getQuoteRequestVO.setAdult1AgeBandInYears(adult1AgeVO.getAgeBandInYears());
		if (adult2AgeVO != null) {
			String adult2AgeList = productUtil.getAgeList(adult2AgeVO.getAgeBandInYears());
			adultAgeList = StringUtils.deleteWhitespace(adult1AgeList).concat(",")
					.concat(StringUtils.deleteWhitespace(adult2AgeList));
			getQuoteRequestVO.setAdult1AgeList(adultAgeList);
			getQuoteRequestVO.setAdult2AgeList(adultAgeList);
			
			getQuoteRequestVO.setAdultDOB2(productUtil.getDOB(adult2AgeVO.getAgeBandInYears()));
			getQuoteRequestVO.setAdult2AgeBandInYears(adult2AgeVO.getAgeBandInYears());
		}
		return getQuoteRequestVO;
	}

	private GetQuoteRequestVO getParentAgeInfo(GetQuoteRequestVO getQuoteRequestParent,
			GetQuoteRequestVO getQuoteRequestVO) {
		getQuoteRequestVO.setParentAgeId(getQuoteRequestParent.getParentAgeId());
		getQuoteRequestVO.setParents(getQuoteRequestParent.getParents());

		getQuoteRequestVO.setParentDOB1(getQuoteRequestParent.getParentDOB1());
		getQuoteRequestVO.setParentDOB2(getQuoteRequestParent.getParentDOB2());
		getQuoteRequestVO.setParentDOB3(getQuoteRequestParent.getParentDOB3());
		getQuoteRequestVO.setParentDOB4(getQuoteRequestParent.getParentDOB4());
		getQuoteRequestVO.setParent1AgeBandInYears(getQuoteRequestParent.getParent1AgeBandInYears());
		getQuoteRequestVO.setParent2AgeBandInYears(getQuoteRequestParent.getParent2AgeBandInYears());
		getQuoteRequestVO.setParent3AgeBandInYears(getQuoteRequestParent.getParent3AgeBandInYears());
		getQuoteRequestVO.setParent4AgeBandInYears(getQuoteRequestParent.getParent4AgeBandInYears());
		
		String parentsAgeList = "";
		String parent1AgeList = "";
		String parent2AgeList = "";
		String parent3AgeList = "";
		String parent4AgeList = "";
		if(StringUtils.isNotBlank(getQuoteRequestParent.getParent1AgeBandInYears())) {
			parent1AgeList = productUtil.getAgeList(getQuoteRequestParent.getParent1AgeBandInYears());
			parentsAgeList = StringUtils.deleteWhitespace(parent1AgeList);
			getQuoteRequestVO.setParent1AgeList(parentsAgeList);
		}
		if(StringUtils.isNotBlank(getQuoteRequestParent.getParent2AgeBandInYears())) {
			parent2AgeList = productUtil.getAgeList(getQuoteRequestParent.getParent2AgeBandInYears());
			parentsAgeList = StringUtils.deleteWhitespace(parent1AgeList).concat(",")
					.concat(StringUtils.deleteWhitespace(parent2AgeList));
			getQuoteRequestVO.setParent1AgeList(parentsAgeList);
			getQuoteRequestVO.setParent2AgeList(parentsAgeList);
		}
		if(StringUtils.isNotBlank(getQuoteRequestParent.getParent3AgeBandInYears())) {
			parent3AgeList = productUtil.getAgeList(getQuoteRequestParent.getParent3AgeBandInYears());
			parentsAgeList = StringUtils.deleteWhitespace(parent1AgeList).concat(",")
					.concat(StringUtils.deleteWhitespace(parent2AgeList)).concat(",")
					.concat(StringUtils.deleteWhitespace(parent3AgeList));
			getQuoteRequestVO.setParent1AgeList(parentsAgeList);
			getQuoteRequestVO.setParent2AgeList(parentsAgeList);
			getQuoteRequestVO.setParent3AgeList(parentsAgeList);
		}
		if(StringUtils.isNotBlank(getQuoteRequestParent.getParent4AgeBandInYears())) {
			parent4AgeList = productUtil.getAgeList(getQuoteRequestParent.getParent4AgeBandInYears());
			parentsAgeList = StringUtils.deleteWhitespace(parent1AgeList).concat(",")
					.concat(StringUtils.deleteWhitespace(parent2AgeList)).concat(",")
					.concat(StringUtils.deleteWhitespace(parent3AgeList)).concat(",")
					.concat(StringUtils.deleteWhitespace(parent4AgeList));
			getQuoteRequestVO.setParent1AgeList(parentsAgeList);
			getQuoteRequestVO.setParent2AgeList(parentsAgeList);
			getQuoteRequestVO.setParent3AgeList(parentsAgeList);
			getQuoteRequestVO.setParent4AgeList(parentsAgeList);
		}

		
		return getQuoteRequestVO;
	}

	private GetQuoteRequestVO getChildAgeInfo(GetQuoteRequestVO getQuoteRequestChild,
			GetQuoteRequestVO getQuoteRequestVO) {
		getQuoteRequestVO.setChildAgeId(getQuoteRequestChild.getChildAgeId());
		getQuoteRequestVO.setChild(getQuoteRequestChild.getChild());

		getQuoteRequestVO.setChildDOB1(getQuoteRequestChild.getChildDOB1());
		getQuoteRequestVO.setChildDOB2(getQuoteRequestChild.getChildDOB2());
		getQuoteRequestVO.setChildDOB3(getQuoteRequestChild.getChildDOB3());
		getQuoteRequestVO.setChild1AgeBandInYears(getQuoteRequestChild.getChild1AgeBandInYears());
		getQuoteRequestVO.setChild2AgeBandInYears(getQuoteRequestChild.getChild2AgeBandInYears());
		getQuoteRequestVO.setChild3AgeBandInYears(getQuoteRequestChild.getChild3AgeBandInYears());
		
		String childAgeList = "";
		String child1AgeList = "";
		String child2AgeList = "";
		String child3AgeList = "";
		if(StringUtils.isNotBlank(getQuoteRequestChild.getChild1AgeBandInYears())) {
			child1AgeList = productUtil.getAgeList(getQuoteRequestChild.getChild1AgeBandInYears());
			childAgeList = StringUtils.deleteWhitespace(child1AgeList);
			getQuoteRequestVO.setChild1AgeList(childAgeList);
		}
		if(StringUtils.isNotBlank(getQuoteRequestChild.getChild2AgeBandInYears())) {
			child2AgeList = productUtil.getAgeList(getQuoteRequestChild.getChild2AgeBandInYears());
			childAgeList = StringUtils.deleteWhitespace(child1AgeList).concat(",")
					.concat(StringUtils.deleteWhitespace(child2AgeList));
			getQuoteRequestVO.setChild1AgeList(childAgeList);
			getQuoteRequestVO.setChild2AgeList(childAgeList);
		}
		if(StringUtils.isNotBlank(getQuoteRequestChild.getChild3AgeBandInYears())) {
			child3AgeList = productUtil.getAgeList(getQuoteRequestChild.getChild3AgeBandInYears());
			childAgeList = StringUtils.deleteWhitespace(child1AgeList).concat(",")
					.concat(StringUtils.deleteWhitespace(child2AgeList)).concat(",")
					.concat(StringUtils.deleteWhitespace(child3AgeList));
			getQuoteRequestVO.setChild1AgeList(childAgeList);
			getQuoteRequestVO.setChild2AgeList(childAgeList);
			getQuoteRequestVO.setChild3AgeList(childAgeList);
		}
		
		return getQuoteRequestVO;
	}

	private int getAdultsInfo(List<ProductsAdultAgeVO> adultAgeVOs, ProductsAdultAgeVO adult1AgeVO,
			ProductsFamilyCombinationsVO familyCombinationsVO, ProductsSumInsureVO sumInsureVO,
			String batchId, int n, int totalPlanCount) throws FaultException, Exception {
		if (familyCombinationsVO.getAdult() == 1) {
			log.info("adults1AgeVO = {}" , adult1AgeVO);
			GetQuoteRequestVO getQuoteRequestVO = buildGetQuoteRequest(familyCombinationsVO, sumInsureVO, adult1AgeVO,
					null, null, null, batchId);
			totalPlanCount = saveGetQuoteRequests(getQuoteRequestVO, totalPlanCount);
		} else if (familyCombinationsVO.getAdult() == 2){
			for(int m=n ;m<adultAgeVOs.size();m++) {
				log.info("adult1 AgeVO = {}" , adultAgeVOs.get(n));
				log.info("adult2 AgeVO = {}" , adultAgeVOs.get(m));
				GetQuoteRequestVO getQuoteRequestVO = buildGetQuoteRequest(familyCombinationsVO, sumInsureVO,
							adultAgeVOs.get(n), adultAgeVOs.get(m), null, null, batchId);
				totalPlanCount = saveGetQuoteRequests(getQuoteRequestVO, totalPlanCount);
					
			}
		}
		return totalPlanCount;
	}
	
	private int getAdultsChildCountInfo(List<ProductsAdultAgeVO> adultAgeVOs, ProductsAdultAgeVO adult1AgeVO,
			ProductsFamilyCombinationsVO familyCombinationsVO, ProductsSumInsureVO sumInsureVO, String batchId,
			int n, int totalPlanCount) throws FaultException, Exception {
		if (familyCombinationsVO.getAdult() == 1) {
			log.info("AdultsChild adult1AgeVO = {}" , adult1AgeVO);
			GetQuoteRequestVO getQuoteRequestVO = buildGetQuoteRequest(familyCombinationsVO, sumInsureVO, adult1AgeVO,
					null, null, null, batchId);
			totalPlanCount = saveGetQuoteRequests(getQuoteRequestVO, totalPlanCount);
		} else if (familyCombinationsVO.getAdult() == 2){
			for(int m=n;m<adultAgeVOs.size();m++) {
					log.info("AdultsChild adult1AgeVO = {}" , adultAgeVOs.get(n));
					log.info("AdultsChild adult2AgeVO = {}" , adultAgeVOs.get(m));
					GetQuoteRequestVO getQuoteRequestVO = buildGetQuoteRequest(familyCombinationsVO, sumInsureVO,
							adultAgeVOs.get(n), adultAgeVOs.get(m), null, null, batchId);
					totalPlanCount = saveGetQuoteRequests(getQuoteRequestVO, totalPlanCount);
					
				}
		}
		return totalPlanCount;
	}
	
	
	private int getAdultsChildInfo(List<ProductsAdultAgeVO> adultAgeVOs, ProductsAdultAgeVO adult1AgeVO,
			List<ProductsChildAgeVO> childAgeVOs, ProductsFamilyCombinationsVO familyCombinationsVO,
			ProductsSumInsureVO sumInsureVO, String batchId, int n, int totalPlanCount) throws FaultException, Exception {

		if (familyCombinationsVO.getAdult() == 1) {
			log.info("AdultsChildInfo adult1AgeVO = {}" , adult1AgeVO);
			if (CollectionUtils.isNotEmpty(childAgeVOs)) {
				totalPlanCount = getChildInfo(childAgeVOs, familyCombinationsVO, sumInsureVO, adult1AgeVO, null,
						batchId, totalPlanCount);
			}
		} else if (familyCombinationsVO.getAdult() == 2) {
			for(int m=n;m<adultAgeVOs.size();m++) {
				//System.out.println(adultAgeVOs.get(n)+""+adultAgeVOs.get(m));
				log.info("AdultsChildInfo adult1AgeVO = {}" , adultAgeVOs.get(n));
				log.info("AdultsChildInfo adult2AgeVO = {}" , adultAgeVOs.get(m));
				totalPlanCount = getChildInfo(childAgeVOs, familyCombinationsVO, sumInsureVO, adultAgeVOs.get(n), adultAgeVOs.get(m),
						batchId, totalPlanCount);
			}
		}
		return totalPlanCount;
	}
	
	private int getAdultsParentsInfo(List<ProductsAdultAgeVO> adultAgeVOs, ProductsAdultAgeVO adult1AgeVO,
			List<ProductsParentAndParentInLawAgeVO> parentAndParentInLawAgeVOs, ProductsFamilyCombinationsVO familyCombinationsVO,
			 ProductsSumInsureVO sumInsureVO, String batchId, int n,  int totalPlanCount) throws FaultException, Exception {
		if (familyCombinationsVO.getAdult() == 1) {
			log.info("AdultsParentsInfo adult1AgeVO = {}" , adult1AgeVO);
			if (CollectionUtils.isNotEmpty(parentAndParentInLawAgeVOs)) {
				totalPlanCount = getParentsInfo(parentAndParentInLawAgeVOs, familyCombinationsVO, sumInsureVO,
						adult1AgeVO, null, batchId, totalPlanCount);
			}
		}else if (familyCombinationsVO.getAdult() == 2) {
			for(int m=n;m<adultAgeVOs.size();m++) {
				//	System.out.println(adultAgeVOs.get(n)+""+adultAgeVOs.get(m));
					log.info("AdultsParentsInfoadult1AgeVO = {}" , adultAgeVOs.get(n));
					log.info("AdultsParentsInfo adult2AgeVO = {}" , adultAgeVOs.get(m));
					totalPlanCount = getParentsInfo(parentAndParentInLawAgeVOs, familyCombinationsVO, sumInsureVO,
							adultAgeVOs.get(n), adultAgeVOs.get(m), batchId, totalPlanCount);
				}
		}
		return totalPlanCount;
	}

	private int getAdultsParentsChildCountInfo(List<ProductsAdultAgeVO> adultAgeVOs, ProductsAdultAgeVO adult1AgeVO,
			List<ProductsParentAndParentInLawAgeVO> parentAndParentInLawAgeVOs, ProductsFamilyCombinationsVO familyCombinationsVO,
			ProductsSumInsureVO sumInsureVO, String batchId, int n, int totalPlanCount) throws FaultException, Exception {
		if (familyCombinationsVO.getAdult() == 1) {
			log.info("AdultsParentsChildCountInfo adult1AgeVO = {}" , adult1AgeVO);
			if (CollectionUtils.isNotEmpty(adultAgeVOs)) {
				totalPlanCount = getParentsInfo(parentAndParentInLawAgeVOs, familyCombinationsVO, sumInsureVO,
						adult1AgeVO, null, batchId, totalPlanCount);
			}
		}else if (familyCombinationsVO.getAdult() == 2) {
			for(int m=n;m<adultAgeVOs.size();m++) {
					log.info("AdultsParentsChildCountInfo adult1AgeVO = {}" , adultAgeVOs.get(n));
					log.info("AdultsParentsChildCountInfo adult2AgeVO = {}" , adultAgeVOs.get(m));
					totalPlanCount = getParentsInfo(parentAndParentInLawAgeVOs, familyCombinationsVO, sumInsureVO,
							adultAgeVOs.get(n), adultAgeVOs.get(m), batchId, totalPlanCount);
				}
		}
		return totalPlanCount;
	}
	
	
	private int getParentsInfo(List<ProductsParentAndParentInLawAgeVO> parentAndParentInLawAgeVOs,
			ProductsFamilyCombinationsVO familyCombinationsVO, ProductsSumInsureVO yearAndSumInsureVO,
			ProductsAdultAgeVO adult1AgeVO, ProductsAdultAgeVO adult2AgeVO, String batchId, int totalPlanCount) throws FaultException, Exception {
		
		if (familyCombinationsVO.getParentAndParentInlaw() == 1) {
			for (ProductsParentAndParentInLawAgeVO parentAgeVO1 : parentAndParentInLawAgeVOs) {
				log.info("ParentsInfo parentAgeVO1 = {}" , parentAgeVO1);
				GetQuoteRequestVO getQuoteRequestParent = new GetQuoteRequestVO();
				getQuoteRequestParent.setParentAgeId(parentAgeVO1.getId());
				getQuoteRequestParent.setParents(String.valueOf(familyCombinationsVO.getParentAndParentInlaw()));

				getQuoteRequestParent.setParentDOB1(productUtil.getDOB(parentAgeVO1.getAgeBandInYears()));
				getQuoteRequestParent.setParent1AgeBandInYears(parentAgeVO1.getAgeBandInYears());
				
				GetQuoteRequestVO getQuoteRequestVO = buildGetQuoteRequest(familyCombinationsVO, yearAndSumInsureVO,
						adult1AgeVO, adult2AgeVO, null, getQuoteRequestParent, batchId);
				totalPlanCount = saveGetQuoteRequests(getQuoteRequestVO, totalPlanCount);
			}
		}else if (familyCombinationsVO.getParentAndParentInlaw() == 2) {
			for(int n=0;n<parentAndParentInLawAgeVOs.size();++n){
				for(int m=n;m<parentAndParentInLawAgeVOs.size();m++) {
				//	System.out.println(parentAndParentInLawAgeVOs.get(n)+""+parentAndParentInLawAgeVOs.get(m));
					log.info("ParentsInfo parentAgeVO1 = {}" , parentAndParentInLawAgeVOs.get(n));
					GetQuoteRequestVO getQuoteRequestParent = new GetQuoteRequestVO();
					getQuoteRequestParent.setParentAgeId(parentAndParentInLawAgeVOs.get(n).getId());
					getQuoteRequestParent.setParents(String.valueOf(familyCombinationsVO.getParentAndParentInlaw()));

					getQuoteRequestParent.setParentDOB1(productUtil.getDOB(parentAndParentInLawAgeVOs.get(n).getAgeBandInYears()));
					getQuoteRequestParent.setParent1AgeBandInYears(parentAndParentInLawAgeVOs.get(n).getAgeBandInYears());
					
					log.info("ParentsInfo parentAgeVO2 = {} " , parentAndParentInLawAgeVOs.get(m));
					getQuoteRequestParent.setParentDOB2(productUtil.getDOB(parentAndParentInLawAgeVOs.get(m).getAgeBandInYears()));
					getQuoteRequestParent.setParent2AgeBandInYears(parentAndParentInLawAgeVOs.get(m).getAgeBandInYears());

					GetQuoteRequestVO getQuoteRequestVO = buildGetQuoteRequest(familyCombinationsVO,
							yearAndSumInsureVO, adult1AgeVO, adult2AgeVO, null, getQuoteRequestParent, batchId);
					totalPlanCount = saveGetQuoteRequests(getQuoteRequestVO, totalPlanCount);
				}
			}
			
		}else if (familyCombinationsVO.getParentAndParentInlaw() == 3) {
			for(int n=0;n<parentAndParentInLawAgeVOs.size();++n) {
				for(int m=n;m<parentAndParentInLawAgeVOs.size();m++) {
					for(int p=m;p<parentAndParentInLawAgeVOs.size();p++) {
					//	System.out.println(parentAndParentInLawAgeVOs.get(n)+""+parentAndParentInLawAgeVOs.get(m)+""+parentAndParentInLawAgeVOs.get(p));
						log.info("parentAgeVO1 = {}" , parentAndParentInLawAgeVOs.get(n));
						GetQuoteRequestVO getQuoteRequestParent = new GetQuoteRequestVO();
						getQuoteRequestParent.setParentAgeId(parentAndParentInLawAgeVOs.get(n).getId());
						getQuoteRequestParent.setParents(String.valueOf(familyCombinationsVO.getParentAndParentInlaw()));

						getQuoteRequestParent.setParentDOB1(productUtil.getDOB(parentAndParentInLawAgeVOs.get(n).getAgeBandInYears()));
						getQuoteRequestParent.setParent1AgeBandInYears(parentAndParentInLawAgeVOs.get(n).getAgeBandInYears());
						
						log.info("parentAgeVO2 = {}" , parentAndParentInLawAgeVOs.get(m));
						getQuoteRequestParent.setParentDOB2(productUtil.getDOB(parentAndParentInLawAgeVOs.get(m).getAgeBandInYears()));
						getQuoteRequestParent.setParent2AgeBandInYears(parentAndParentInLawAgeVOs.get(m).getAgeBandInYears());
						
						log.info("parentAgeVO3 {} " , parentAndParentInLawAgeVOs.get(p));
						getQuoteRequestParent.setParentDOB3(productUtil.getDOB(parentAndParentInLawAgeVOs.get(p).getAgeBandInYears()));
						getQuoteRequestParent.setParent3AgeBandInYears(parentAndParentInLawAgeVOs.get(p).getAgeBandInYears());
						
						GetQuoteRequestVO getQuoteRequestVO = buildGetQuoteRequest(familyCombinationsVO,
								yearAndSumInsureVO, adult1AgeVO, adult2AgeVO, null, getQuoteRequestParent, batchId);
						totalPlanCount = saveGetQuoteRequests(getQuoteRequestVO, totalPlanCount);
					
					}
				}
			}
		}else if (familyCombinationsVO.getParentAndParentInlaw() == 4) {
			 for(int n=0;n<parentAndParentInLawAgeVOs.size();++n) {
				 for(int m=n;m<parentAndParentInLawAgeVOs.size();m++) {
					 for(int p=m;p<parentAndParentInLawAgeVOs.size();p++) {
						 for(int q=p;q<parentAndParentInLawAgeVOs.size();q++) {
						//	 System.out.println(parentAndParentInLawAgeVOs.get(n)+""+parentAndParentInLawAgeVOs.get(m)+""+parentAndParentInLawAgeVOs.get(p)+""+parentAndParentInLawAgeVOs.get(q));
							 	log.info("parentAgeVO1 = {}" , parentAndParentInLawAgeVOs.get(n));
								GetQuoteRequestVO getQuoteRequestParent = new GetQuoteRequestVO();
								getQuoteRequestParent.setParentAgeId(parentAndParentInLawAgeVOs.get(n).getId());
								getQuoteRequestParent.setParents(String.valueOf(familyCombinationsVO.getParentAndParentInlaw()));

								getQuoteRequestParent.setParentDOB1(productUtil.getDOB(parentAndParentInLawAgeVOs.get(n).getAgeBandInYears()));
								getQuoteRequestParent.setParent1AgeBandInYears(parentAndParentInLawAgeVOs.get(n).getAgeBandInYears());
								
								log.info("parentAgeVO2 = {}" , parentAndParentInLawAgeVOs.get(m));
								getQuoteRequestParent.setParentDOB2(productUtil.getDOB(parentAndParentInLawAgeVOs.get(m).getAgeBandInYears()));
								getQuoteRequestParent.setParent2AgeBandInYears(parentAndParentInLawAgeVOs.get(m).getAgeBandInYears());
								
								log.info("parentAgeVO3 {} " , parentAndParentInLawAgeVOs.get(p));
								getQuoteRequestParent.setParentDOB3(productUtil.getDOB(parentAndParentInLawAgeVOs.get(p).getAgeBandInYears()));
								getQuoteRequestParent.setParent3AgeBandInYears(parentAndParentInLawAgeVOs.get(p).getAgeBandInYears());
								
								log.info("parentAgeVO4 {} " , parentAndParentInLawAgeVOs.get(q));
								getQuoteRequestParent.setParentDOB4(productUtil.getDOB(parentAndParentInLawAgeVOs.get(q).getAgeBandInYears()));
								getQuoteRequestParent.setParent4AgeBandInYears(parentAndParentInLawAgeVOs.get(q).getAgeBandInYears());

								GetQuoteRequestVO getQuoteRequestVO = buildGetQuoteRequest(familyCombinationsVO,
										yearAndSumInsureVO, adult1AgeVO, adult2AgeVO, null, getQuoteRequestParent, batchId);
								totalPlanCount = saveGetQuoteRequests(getQuoteRequestVO, totalPlanCount);
						 
						 }
					 }
				 }
			 }
		}

		return totalPlanCount;
	}

	
	private int getChildInfo(List<ProductsChildAgeVO> childAgeVOs, ProductsFamilyCombinationsVO familyCombinationsVO,
			ProductsSumInsureVO yearAndSumInsureVO, ProductsAdultAgeVO adult1AgeVO, ProductsAdultAgeVO adult2AgeVO,
			String batchId, int totalPlanCount)
			throws FaultException, Exception {
		if (familyCombinationsVO.getChild() == 1) {
			for (ProductsChildAgeVO childAgeVO1 : childAgeVOs) {
				log.info("ChildInfo child1 AgeVO = {}" , childAgeVO1);
				GetQuoteRequestVO getQuoteRequestChild = new GetQuoteRequestVO();
				getQuoteRequestChild.setChildAgeId(childAgeVO1.getId());
				getQuoteRequestChild.setChild(String.valueOf(familyCombinationsVO.getChild()));

				getQuoteRequestChild.setChildDOB1(productUtil.getDOB(childAgeVO1.getAgeBandInYears()));
				getQuoteRequestChild.setChild1AgeBandInYears(childAgeVO1.getAgeBandInYears());
				
				GetQuoteRequestVO getQuoteRequestVO = buildGetQuoteRequest(familyCombinationsVO, yearAndSumInsureVO,
						adult1AgeVO, adult2AgeVO, getQuoteRequestChild, null, batchId);
				totalPlanCount = saveGetQuoteRequests(getQuoteRequestVO, totalPlanCount); 
			}
		}else if (familyCombinationsVO.getChild() == 2) {
			for(int n=0; n<childAgeVOs.size(); ++n) {
				for(int m=n; m<childAgeVOs.size(); m++) {
					//System.out.println(inputstring.get(n)+""+inputstring.get(m));
					GetQuoteRequestVO getQuoteRequestChild = new GetQuoteRequestVO();
					
					log.info("ChildInfo child1AgeVO = {}" , childAgeVOs.get(n));
					getQuoteRequestChild.setChildAgeId(childAgeVOs.get(n).getId());
					getQuoteRequestChild.setChild(String.valueOf(familyCombinationsVO.getChild()));

					getQuoteRequestChild.setChildDOB1(productUtil.getDOB(childAgeVOs.get(n).getAgeBandInYears()));
					getQuoteRequestChild.setChild1AgeBandInYears(childAgeVOs.get(n).getAgeBandInYears());
					
					log.info("ChildInfo child2AgeVO = {}" , childAgeVOs.get(m));
					getQuoteRequestChild.setChildDOB2(productUtil.getDOB(childAgeVOs.get(m).getAgeBandInYears()));
					getQuoteRequestChild.setChild2AgeBandInYears(childAgeVOs.get(m).getAgeBandInYears());
		
					GetQuoteRequestVO getQuoteRequestVO = buildGetQuoteRequest(familyCombinationsVO, yearAndSumInsureVO,
							adult1AgeVO, adult2AgeVO, getQuoteRequestChild, null, batchId);
					totalPlanCount = saveGetQuoteRequests(getQuoteRequestVO, totalPlanCount);

				}

			}
		}else if (familyCombinationsVO.getChild() == 3) {
			for(int n=0;n<childAgeVOs.size();++n) {
				for(int m=n;m<childAgeVOs.size();m++) {
					for(int p=m;p<childAgeVOs.size();p++) {
						GetQuoteRequestVO getQuoteRequestChild = new GetQuoteRequestVO();
						
						log.info("ChildInfo child1AgeVO = {}" , childAgeVOs.get(n));
						getQuoteRequestChild.setChildAgeId(childAgeVOs.get(n).getId());
						getQuoteRequestChild.setChild(String.valueOf(familyCombinationsVO.getChild()));

						getQuoteRequestChild.setChildDOB1(productUtil.getDOB(childAgeVOs.get(n).getAgeBandInYears()));
						getQuoteRequestChild.setChild1AgeBandInYears(childAgeVOs.get(n).getAgeBandInYears());
						
						log.info("ChildInfo child2AgeVO {} " , childAgeVOs.get(m));
						getQuoteRequestChild.setChildDOB2(productUtil.getDOB(childAgeVOs.get(m).getAgeBandInYears()));
						getQuoteRequestChild.setChild2AgeBandInYears(childAgeVOs.get(m).getAgeBandInYears());
						
						log.info("ChildInfo child3AgeVO {} " , childAgeVOs.get(p));
						getQuoteRequestChild.setChildDOB3(productUtil.getDOB(childAgeVOs.get(p).getAgeBandInYears()));
						getQuoteRequestChild.setChild3AgeBandInYears(childAgeVOs.get(p).getAgeBandInYears());
						
						GetQuoteRequestVO getQuoteRequestVO = buildGetQuoteRequest(familyCombinationsVO,
								yearAndSumInsureVO, adult1AgeVO, adult2AgeVO, getQuoteRequestChild, null,
								batchId);
						totalPlanCount = saveGetQuoteRequests(getQuoteRequestVO, totalPlanCount);
						
					}
				}
				
			}

			
		}
		
		return totalPlanCount;
	}


	private GetQuoteRequestVO buildGetQuoteRequest(ProductsFamilyCombinationsVO familyCombinationsVO,
			ProductsSumInsureVO yearAndSumInsureVO, ProductsAdultAgeVO adult1AgeVO, ProductsAdultAgeVO adult2AgeVO,
			GetQuoteRequestVO getQuoteRequestChild, GetQuoteRequestVO getQuoteRequestParent, String batchId) {
		GetQuoteRequestVO getQuoteRequestVO = new GetQuoteRequestVO();
		getQuoteRequestVO.setBatchId(batchId);
		getFamilyCombinationInfo(familyCombinationsVO, getQuoteRequestVO);
		getYearAndSumInsureInfo(yearAndSumInsureVO, getQuoteRequestVO);
		getAdultAgeInfo(adult1AgeVO, adult2AgeVO, getQuoteRequestVO);
		if (getQuoteRequestChild != null) {
			getChildAgeInfo(getQuoteRequestChild, getQuoteRequestVO);
		}
		if (getQuoteRequestParent != null) {
			getParentAgeInfo(getQuoteRequestParent, getQuoteRequestVO);
		}
		return getQuoteRequestVO;
	}

	private int saveGetQuoteRequests(GetQuoteRequestVO getQuoteRequestVO, int totalPlanCount) throws Exception, FaultException {

		getQuoteRequestVO.setFlag("0");
		getQuoteRequestVO.setIsActive("TRUE");
		getQuoteRequestVO.setCreatedTime(new java.sql.Timestamp(new java.util.Date().getTime()));
		getQuoteRequestVO.setModifiedTime(new java.sql.Timestamp(new java.util.Date().getTime()));
		getQuoteRequestRepo.save(getQuoteRequestVO);
		
		log.info("TotalRequestCount {} " , totalPlanCount++);
		
		return totalPlanCount;

	}
	
	public int getQuotes(GetQuoteRequestVO getQuoteRequestVO, int totalPlanCount) throws Exception, FaultException {

		getQuoteRequestVO.setFlag("1");
		getQuoteRequestVO.setModifiedTime(new java.sql.Timestamp(new java.util.Date().getTime()));
		getQuoteRequestVO = getQuoteRequestRepo.save(getQuoteRequestVO);

		GetQuoteSOAPResponse getQuoteResponse = soapClient.getQuotes(getQuoteRequestVO);
		if (StringUtils.isNotBlank(getQuoteResponse.getPremiumAmount())) {
			GetQuoteResponseVO getQuoteResponseVO = addQuoteResponseVO(getQuoteResponse,
					getQuoteRequestVO.getBatchId());
			getQuoteResponseVO = addQuoteRequest(getQuoteResponseVO, getQuoteRequestVO);

			log.info("Save Get Quote Response");
			getQuoteResponseVO.setCreatedTime(new java.sql.Timestamp(new java.util.Date().getTime()));
			getQuoteResponseVO.setModifiedTime(new java.sql.Timestamp(new java.util.Date().getTime()));
			getQuoteResponseRepo.save(getQuoteResponseVO);
			
			getQuoteRequestVO.setFlag("2");
			getQuoteRequestVO.setModifiedTime(new java.sql.Timestamp(new java.util.Date().getTime()));
			getQuoteRequestRepo.save(getQuoteRequestVO);
		} else {
			getQuoteRequestVO.setFlag("5");
			if (StringUtils.isNotBlank(getQuoteResponse.getMessage())) {
				getQuoteRequestVO.setMessage(getQuoteResponse.getMessage());
			}
			getQuoteRequestVO.setModifiedTime(new java.sql.Timestamp(new java.util.Date().getTime()));
			getQuoteRequestRepo.save(getQuoteRequestVO);
			log.info("Get Quote Response Failed : {} " , getQuoteResponse.getMessage());
		}
		 

		log.info("TotalPlanCount {} " , ++totalPlanCount);
		
		return totalPlanCount;

	}

	public GetQuoteResponseVO addQuoteResponseVO(GetQuoteSOAPResponse getQuoteResponse, String batchId) {
		log.info("Add Get Quote Response Starts");
		GetQuoteResponseVO getQuoteResponseVO = new GetQuoteResponseVO();

		getQuoteResponseVO.setBatchId(batchId);
		if (StringUtils.isNotBlank(getQuoteResponse.getPremiumAmount())) {
			getQuoteResponseVO.setPremiumAmount(Integer.parseInt(getQuoteResponse.getPremiumAmount()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getPremiumTax())) {
			getQuoteResponseVO.setPremiumTax(Integer.parseInt(getQuoteResponse.getPremiumTax()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getTotalAmount())) {
			getQuoteResponseVO.setTotalAmount(Integer.parseInt(getQuoteResponse.getTotalAmount()));
		}
		getQuoteResponseVO.setMessage(getQuoteResponse.getMessage());
		if (StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntFirstPrem())) {
			getQuoteResponseVO
					.setHlflyPremAmntFirstPrem(Integer.parseInt(getQuoteResponse.getHlflyPremAmntFirstPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntFirstTax())) {
			getQuoteResponseVO.setHlflyPremAmntFirstTax(Integer.parseInt(getQuoteResponse.getHlflyPremAmntFirstTax()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntFirstTotal())) {
			getQuoteResponseVO
					.setHlflyPremAmntFirstTotal(Integer.parseInt(getQuoteResponse.getHlflyPremAmntFirstTotal()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntOthersPrem())) {
			getQuoteResponseVO
					.setHlflyPremAmntOthersPrem(Integer.parseInt(getQuoteResponse.getHlflyPremAmntOthersPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntOthersTax())) {
			getQuoteResponseVO
					.setHlflyPremAmntOthersTax(Integer.parseInt(getQuoteResponse.getHlflyPremAmntOthersTax()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getHlflyPremAmntOthersTotal())) {
			getQuoteResponseVO
					.setHlflyPremAmntOthersTotal(Integer.parseInt(getQuoteResponse.getHlflyPremAmntOthersTotal()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntFirstPrem())) {
			getQuoteResponseVO
					.setQtrlyPremAmntFirstPrem(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntFirstPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntFirstTax())) {
			getQuoteResponseVO.setQtrlyPremAmntFirstTax(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntFirstTax()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntFirstTotal())) {
			getQuoteResponseVO
					.setQtrlyPremAmntFirstTotal(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntFirstTotal()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntOthersPrem())) {
			getQuoteResponseVO
					.setQtrlyPremAmntOthersPrem(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntOthersPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntOthersTax())) {
			getQuoteResponseVO
					.setQtrlyPremAmntOthersTax(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntOthersTax()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getQtrlyPremAmntOthersTotal())) {
			getQuoteResponseVO
					.setQtrlyPremAmntOthersTotal(Integer.parseInt(getQuoteResponse.getQtrlyPremAmntOthersTotal()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntFirstPrem())) {
			getQuoteResponseVO
					.setMnthlyPremAmntFirstPrem(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntFirstPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntFirstTax())) {
			getQuoteResponseVO
					.setMnthlyPremAmntFirstTax(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntFirstTax()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntFirstTotal())) {
			getQuoteResponseVO
					.setMnthlyPremAmntFirstTotal(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntFirstTotal()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntOthersPrem())) {
			getQuoteResponseVO
					.setMnthlyPremAmntOthersPrem(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntOthersPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntOthersTax())) {
			getQuoteResponseVO
					.setMnthlyPremAmntOthersTax(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntOthersTax()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getMnthlyPremAmntOthersTotal())) {
			getQuoteResponseVO
					.setMnthlyPremAmntOthersTotal(Integer.parseInt(getQuoteResponse.getMnthlyPremAmntOthersTotal()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getTotalRuralDiscountAmt())) {
			getQuoteResponseVO.setTotalRuralDiscountAmt(Integer.parseInt(getQuoteResponse.getTotalRuralDiscountAmt()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getTotalOnlineDiscountAmt())) {
			getQuoteResponseVO
					.setTotalOnlineDiscountAmt(Integer.parseInt(getQuoteResponse.getTotalOnlineDiscountAmt()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getTotalAddDiscnt())) {
			getQuoteResponseVO.setTotalAddDiscnt(Integer.parseInt(getQuoteResponse.getTotalAddDiscnt()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getBuyBackPrem())) {
			getQuoteResponseVO.setBuyBackPrem(Integer.parseInt(getQuoteResponse.getBuyBackPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getHospitalCashPrem())) {
			getQuoteResponseVO.setHospitalCashPrem(Integer.parseInt(getQuoteResponse.getHospitalCashPrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getPatientCarePrem())) {
			getQuoteResponseVO.setPatientCarePrem(Integer.parseInt(getQuoteResponse.getPatientCarePrem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getAddOnCvrSec1Prem())) {
			getQuoteResponseVO.setAddOnCvrSec1Prem(Integer.parseInt(getQuoteResponse.getAddOnCvrSec1Prem()));
		}
		if (StringUtils.isNotBlank(getQuoteResponse.getAddOnCvrSec2Prem())) {
			getQuoteResponseVO.setAddOnCvrSec2Prem(Integer.parseInt(getQuoteResponse.getAddOnCvrSec2Prem()));
		}
		log.info("Add Get Quote Response End");
		return getQuoteResponseVO;
	}

	private GetQuoteResponseVO addQuoteRequest(GetQuoteResponseVO getQuoteResponseVO, GetQuoteRequestVO getQuoteRequestVO) {
		log.info("Add Quote Request Starts");
		getQuoteResponseVO.setGetQuoteReqId(getQuoteRequestVO.getId());
		if (StringUtils.isNotBlank(getQuoteRequestVO.getInsuranceCover())) {
			getQuoteResponseVO.setInsuranceCover(Integer.parseInt(getQuoteRequestVO.getInsuranceCover()));
		}
		getQuoteResponseVO.setFlag("2");
		getQuoteResponseVO.setIsActive("TRUE");
		getQuoteResponseVO.setProductCode(getQuoteRequestVO.getProductCode());
		getQuoteResponseVO.setProductName(getQuoteRequestVO.getProductName());
		getQuoteResponseVO.setProductId(getQuoteRequestVO.getProductId());
		getQuoteResponseVO.setFamilyId(getQuoteRequestVO.getFamilyId());
		getQuoteResponseVO.setProductBatchId(getQuoteRequestVO.getProductBatchId());
		// getQuoteResponseVO.setProductPremium(getQuoteRequestVO.getPremium());
		if (StringUtils.isNotBlank(getQuoteRequestVO.getAdults())) {
			getQuoteResponseVO.setAdults(Integer.parseInt(getQuoteRequestVO.getAdults()));
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getChild())) {
			getQuoteResponseVO.setChild(Integer.parseInt(getQuoteRequestVO.getChild()));
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getChild1AgeBandInYears())) {
			getQuoteResponseVO.setChild1AgeBandInYears(getQuoteRequestVO.getChild1AgeBandInYears());
			getQuoteResponseVO.setChild1AgeList(productUtil.getAgeList(getQuoteRequestVO.getChild1AgeBandInYears()));
			if (StringUtils.isNotBlank(getQuoteRequestVO.getChild1AgeList())) {
				getQuoteResponseVO.setChild1AgeList(getQuoteRequestVO.getChild1AgeList());
			}
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getChild2AgeBandInYears())) {
			getQuoteResponseVO.setChild2AgeBandInYears(getQuoteRequestVO.getChild2AgeBandInYears());
			getQuoteResponseVO.setChild2AgeList(productUtil.getAgeList(getQuoteRequestVO.getChild2AgeBandInYears()));
			if (StringUtils.isNotBlank(getQuoteRequestVO.getChild2AgeList())) {
				getQuoteResponseVO.setChild2AgeList(getQuoteRequestVO.getChild2AgeList());
			}
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getChild3AgeBandInYears())) {
			getQuoteResponseVO.setChild3AgeBandInYears(getQuoteRequestVO.getChild3AgeBandInYears());
			getQuoteResponseVO.setChild3AgeList(productUtil.getAgeList(getQuoteRequestVO.getChild3AgeBandInYears()));
			if (StringUtils.isNotBlank(getQuoteRequestVO.getChild3AgeList())) {
				getQuoteResponseVO.setChild3AgeList(getQuoteRequestVO.getChild3AgeList());
			}
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getParents())) {
			getQuoteResponseVO.setParents(Integer.parseInt(getQuoteRequestVO.getParents()));
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getParent1AgeBandInYears())) {
			getQuoteResponseVO.setParent1AgeBandInYears(getQuoteRequestVO.getParent1AgeBandInYears());
			getQuoteResponseVO.setParent1AgeList(productUtil.getAgeList(getQuoteRequestVO.getParent1AgeBandInYears()));
			if (StringUtils.isNotBlank(getQuoteRequestVO.getParent1AgeList())) {
				getQuoteResponseVO.setParent1AgeList(getQuoteRequestVO.getParent1AgeList());
			}
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getParent2AgeBandInYears())) {
			getQuoteResponseVO.setParent2AgeBandInYears(getQuoteRequestVO.getParent2AgeBandInYears());
			getQuoteResponseVO.setParent2AgeList(productUtil.getAgeList(getQuoteRequestVO.getParent2AgeBandInYears()));
			if (StringUtils.isNotBlank(getQuoteRequestVO.getParent2AgeList())) {
				getQuoteResponseVO.setParent2AgeList(getQuoteRequestVO.getParent2AgeList());
			}
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getParent3AgeBandInYears())) {
			getQuoteResponseVO.setParent3AgeBandInYears(getQuoteRequestVO.getParent3AgeBandInYears());
			getQuoteResponseVO.setParent3AgeList(productUtil.getAgeList(getQuoteRequestVO.getParent3AgeBandInYears()));
			if (StringUtils.isNotBlank(getQuoteRequestVO.getParent3AgeList())) {
				getQuoteResponseVO.setParent3AgeList(getQuoteRequestVO.getParent3AgeList());
			}
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getParent4AgeBandInYears())) {
			getQuoteResponseVO.setParent4AgeBandInYears(getQuoteRequestVO.getParent4AgeBandInYears());
			getQuoteResponseVO.setParent4AgeList(productUtil.getAgeList(getQuoteRequestVO.getParent4AgeBandInYears()));
			if (StringUtils.isNotBlank(getQuoteRequestVO.getParent4AgeList())) {
				getQuoteResponseVO.setParent4AgeList(getQuoteRequestVO.getParent4AgeList());
			}
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getPeriod())) {
			getQuoteResponseVO.setPeriod(getQuoteRequestVO.getPeriod());
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getPlan())) {
			getQuoteResponseVO.setPlan(getQuoteRequestVO.getPlan());
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getAdult1AgeBandInYears())) {
			getQuoteResponseVO.setAdult1AgeBandInYears(getQuoteRequestVO.getAdult1AgeBandInYears());
			getQuoteResponseVO.setAdult1AgeList(productUtil.getAgeList(getQuoteRequestVO.getAdult1AgeBandInYears()));
			if (StringUtils.isNotBlank(getQuoteRequestVO.getAdult1AgeList())) {
				getQuoteResponseVO.setAdult1AgeList(getQuoteRequestVO.getAdult1AgeList());
			}
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getAdult2AgeBandInYears())) {
			getQuoteResponseVO.setAdult2AgeBandInYears(getQuoteRequestVO.getAdult2AgeBandInYears());
			getQuoteResponseVO.setAdult2AgeList(productUtil.getAgeList(getQuoteRequestVO.getAdult2AgeBandInYears()));
			if (StringUtils.isNotBlank(getQuoteRequestVO.getAdult2AgeList())) {
				getQuoteResponseVO.setAdult2AgeList(getQuoteRequestVO.getAdult2AgeList());
			}
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getZone())) {
			getQuoteResponseVO.setZone(getQuoteRequestVO.getZone());
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getSchemeCode())) {
			getQuoteResponseVO.setSchemeCode(getQuoteRequestVO.getSchemeCode());
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getPinCode())) {
			getQuoteResponseVO.setPinCode(Integer.parseInt(getQuoteRequestVO.getPinCode()));
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getBuyBackPedYN())) {
			getQuoteResponseVO.setBuyBackPedYN(getQuoteRequestVO.getBuyBackPedYN());
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getDeduction())) {
			getQuoteResponseVO.setDeduction(Integer.parseInt(getQuoteRequestVO.getDeduction()));
		}
		if (StringUtils.isNotBlank(getQuoteRequestVO.getOnlineDiscountPerc())) {
			getQuoteResponseVO.setOnlineDiscountPerc(Integer.parseInt(getQuoteRequestVO.getOnlineDiscountPerc()));
		}

		log.info("Add Quote Request End");
		return getQuoteResponseVO;
	}

}
