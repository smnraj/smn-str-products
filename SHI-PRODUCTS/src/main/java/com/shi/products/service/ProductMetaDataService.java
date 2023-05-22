/**
 * 
 */
package com.shi.products.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shi.products.dao.ProductsDAO;
import com.shi.products.exception.FaultException;
import com.shi.products.repo.ProductPincodeRepo;
import com.shi.products.util.ApplicationConstant;
import com.shi.products.util.ProductUtil;
import com.shi.products.vo.ProductComparisonMappingVO;
import com.shi.products.vo.ProductListVO;
import com.shi.products.vo.ProductPincodeVO;

/**
 * @author suman.raju
 *
 */
@Service
public class ProductMetaDataService {
	
	private static final Logger log = LoggerFactory.getLogger(ProductMetaDataService.class);
	
	@Autowired
	ProductsDAO productDAO;
	
	@Autowired
	ProductUtil productUtil;
	
	@Autowired
	ProductPincodeRepo productPincodeRepo;
	
	public List<ProductListVO> upLoadProductsList(XSSFSheet worksheet) throws FaultException {
		log.debug("Read File Starts ::");
		String nameP = null;
		int rowNum = 0;
		String colNum = "";
		List<ProductListVO> productListsVO = new ArrayList<>();
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ApplicationConstant.DATETIMEFORMAT);
			LocalDateTime now = LocalDateTime.now();
			log.debug("ProductsList Date Format :: {} " , dtf.format(now));
			String batchId = dtf.format(now);
			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				ProductListVO productListVO = new ProductListVO();
				
				//productVO.setId(1l);
				
				productListVO.setBatchId(batchId);
				productListVO.setIsActive("True");
				
				rowNum = i;
				log.debug("ProductsList Row Num={} " , rowNum);
				XSSFRow row = worksheet.getRow(i);
				
				colNum = "A";
				String productCode = "";
				if (row.getCell(0) != null) {
					XSSFCell xSSFCell = row.getCell(0);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						productCode = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						productCode = (row.getCell(0).getStringCellValue() == null) ? " "
								: row.getCell(0).getStringCellValue();
				}
				productListVO.setProductCode(productCode);
				log.debug(productCode);
				
				colNum = "B";
				String name = row.getCell(1).getStringCellValue();
				nameP = name;
				productListVO.setProductName(name.trim());
				log.debug(name);
				
				String typeOfCover = "";
				colNum = "C";
				if (row.getCell(2) != null) {
					XSSFCell xSSFCell = row.getCell(2);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						typeOfCover = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						typeOfCover = (row.getCell(2).getStringCellValue() == null) ? " "
								: row.getCell(2).getStringCellValue();
				}
				log.debug(typeOfCover);
				productListVO.setTypeOfCover(typeOfCover);
				
				/*
				 * ProductCoverVO productCoverVO = new ProductCoverVO();
				 * productCoverVO.setBatchId(batchId);
				 * productCoverVO.setName(typeOfCover.trim()); productCoverVO.setActive("True");
				 * productCoverVO.setProductCode(productCode);
				 * productDAO.saveProductCover(productCoverVO);
				 */
				
				
				String planVariants = "";
				colNum = "D";
				if (row.getCell(3) != null) {
					XSSFCell xSSFCell = row.getCell(3);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						planVariants = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						planVariants = (row.getCell(3).getStringCellValue() == null) ? " "
								: row.getCell(3).getStringCellValue();
				}
				productListVO.setPlanVariants(planVariants);
				log.debug(planVariants);
				
				
				String ageOfEntry = "";
				colNum = "E";
				if (row.getCell(4) != null) {
					XSSFCell xSSFCell = row.getCell(4);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						ageOfEntry = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						ageOfEntry = (row.getCell(4).getStringCellValue() == null) ? " "
								: row.getCell(4).getStringCellValue();
				}
				productListVO.setAgeOfEntry(ageOfEntry);
				log.debug(ageOfEntry);
				
				
				String size = "";
				colNum = "F";
				if (row.getCell(5) != null) {
					XSSFCell xSSFCell = row.getCell(5);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						size = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						size = (row.getCell(5).getStringCellValue() == null) ? " "
								: row.getCell(5).getStringCellValue();
				}
				productListVO.setSize(size);
				log.debug(size);
				
				
				String preAcceptanceMedicalScreening = "";
				colNum = "G";
				if (row.getCell(6) != null) {
					XSSFCell xSSFCell = row.getCell(6);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						preAcceptanceMedicalScreening = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						preAcceptanceMedicalScreening = (row.getCell(6).getStringCellValue() == null) ? " "
								: row.getCell(6).getStringCellValue();
				}
				productListVO.setPreAcceptanceMedicalScreening(preAcceptanceMedicalScreening);
				log.debug(preAcceptanceMedicalScreening);
				
				
				String sumInsuredOptions = "";
				colNum = "H";
				if (row.getCell(7) != null) {
					XSSFCell xSSFCell = row.getCell(7);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						sumInsuredOptions = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						sumInsuredOptions = (row.getCell(7).getStringCellValue() == null) ? " "
								: row.getCell(7).getStringCellValue();
				}
				productListVO.setSumInsuredOptions(sumInsuredOptions);
				log.debug(sumInsuredOptions);
				
				
				String policyTerm = "";
				colNum = "I";
				if (row.getCell(8) != null) {
					XSSFCell xSSFCell = row.getCell(8);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						policyTerm = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						policyTerm = (row.getCell(8).getStringCellValue() == null) ? " "
								: row.getCell(8).getStringCellValue();
				}
				productListVO.setPolicyTerm(policyTerm);
				log.debug(policyTerm);
				
				
				String installmentFacility = "";
				colNum = "J";
				if (row.getCell(9) != null) {
					XSSFCell xSSFCell = row.getCell(9);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						installmentFacility = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						installmentFacility = (row.getCell(9).getStringCellValue() == null) ? " "
								: row.getCell(9).getStringCellValue();
				}
				productListVO.setInstallmentFacility(installmentFacility);
				log.debug(installmentFacility);
				
				
				String gracePeriod = "";
				colNum = "K";
				if (row.getCell(10) != null) {
					XSSFCell xSSFCell = row.getCell(10);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						gracePeriod = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						gracePeriod = (row.getCell(10).getStringCellValue() == null) ? " "
								: row.getCell(10).getStringCellValue();
				}
				productListVO.setGracePeriod(gracePeriod);
				log.debug(gracePeriod);	
				
				
				String roomRent = "";
				colNum = "L";
				if (row.getCell(11) != null) {
					XSSFCell xSSFCell = row.getCell(11);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						roomRent = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						roomRent = (row.getCell(11).getStringCellValue() == null) ? " "
								: row.getCell(11).getStringCellValue();
				}
				productListVO.setRoomRent(roomRent);
				log.debug(roomRent);
				
				
				String preAndPostHospitalisation = "";
				colNum = "M";
				if (row.getCell(12) != null) {
					XSSFCell xSSFCell = row.getCell(12);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						preAndPostHospitalisation = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						preAndPostHospitalisation = (row.getCell(12).getStringCellValue() == null) ? " "
								: row.getCell(12).getStringCellValue();
				}
				productListVO.setPreAndPostHospitalisation(preAndPostHospitalisation);
				log.debug(preAndPostHospitalisation);
				
				
				String dayCareProcedures = "";
				colNum = "N";
				if (row.getCell(13) != null) {
					XSSFCell xSSFCell = row.getCell(13);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						dayCareProcedures = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						dayCareProcedures = (row.getCell(13).getStringCellValue() == null) ? " "
								: row.getCell(13).getStringCellValue();
				}
				productListVO.setDayCareProcedures(dayCareProcedures);
				log.debug(dayCareProcedures);
				
				String cataract = "";
				colNum = "O";
				if (row.getCell(14) != null) {
					XSSFCell xSSFCell = row.getCell(14);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						cataract = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						cataract = (row.getCell(14).getStringCellValue() == null) ? " "
								: row.getCell(14).getStringCellValue();
				}
				productListVO.setCataract(cataract);
				log.debug(cataract);
				
				String healthCheckup = "";
				colNum = "P";
				if (row.getCell(15) != null) {
					XSSFCell xSSFCell = row.getCell(15);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						healthCheckup = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						healthCheckup = (row.getCell(15).getStringCellValue() == null) ? " "
								: row.getCell(15).getStringCellValue();
				}
				productListVO.setHealthCheckup(healthCheckup);
				log.debug(healthCheckup);
				
				String roadAmbulance = "";
				colNum = "Q";
				if (row.getCell(16) != null) {
					XSSFCell xSSFCell = row.getCell(16);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						roadAmbulance = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						roadAmbulance = (row.getCell(16).getStringCellValue() == null) ? " "
								: row.getCell(16).getStringCellValue();
				}
				productListVO.setRoadAmbulance(roadAmbulance);
				log.debug(roadAmbulance);
				
				String airAmbulance = "";
				colNum = "R";
				if (row.getCell(17) != null) {
					XSSFCell xSSFCell = row.getCell(17);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						airAmbulance = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						airAmbulance = (row.getCell(17).getStringCellValue() == null) ? " "
								: row.getCell(17).getStringCellValue();
				}
				productListVO.setAirAmbulance(airAmbulance);
				log.debug(airAmbulance);
				
				String ayushTreatment = "";
				colNum = "S";
				if (row.getCell(18) != null) {
					XSSFCell xSSFCell = row.getCell(18);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						ayushTreatment = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						ayushTreatment = (row.getCell(18).getStringCellValue() == null) ? " "
								: row.getCell(18).getStringCellValue();
				}
				productListVO.setAyushTreatment(ayushTreatment);
				log.debug(ayushTreatment);
				
				String renewals = "";
				colNum = "T";
				if (row.getCell(19) != null) {
					XSSFCell xSSFCell = row.getCell(19);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						renewals = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						renewals = (row.getCell(19).getStringCellValue() == null) ? " "
								: row.getCell(19).getStringCellValue();
				}
				productListVO.setRenewals(renewals);
				log.debug(renewals);
				
				String copayment = "";
				colNum = "U";
				if (row.getCell(20) != null) {
					XSSFCell xSSFCell = row.getCell(20);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						copayment = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						copayment = (row.getCell(20).getStringCellValue() == null) ? " "
								: row.getCell(20).getStringCellValue();
				}
				productListVO.setCopayment(copayment);
				log.debug(copayment);
				
				String modernTreatment = "";
				colNum = "V";
				if (row.getCell(21) != null) {
					XSSFCell xSSFCell = row.getCell(21);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						modernTreatment = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						modernTreatment = (row.getCell(21).getStringCellValue() == null) ? " "
								: row.getCell(21).getStringCellValue();
				}
				productListVO.setModernTreatment(modernTreatment);
				log.debug(modernTreatment);
				
				String cummulativeBonus = "";
				colNum = "W";
				if (row.getCell(22) != null) {
					XSSFCell xSSFCell = row.getCell(22);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						cummulativeBonus = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						cummulativeBonus = (row.getCell(22).getStringCellValue() == null) ? " "
								: row.getCell(22).getStringCellValue();
				}
				productListVO.setCummulativeBonus(cummulativeBonus);
				log.debug(cummulativeBonus);
				
				String autoRestoration = "";
				colNum = "Y";
				if (row.getCell(23) != null) {
					XSSFCell xSSFCell = row.getCell(23);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						autoRestoration = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						autoRestoration = (row.getCell(23).getStringCellValue() == null) ? " "
								: row.getCell(23).getStringCellValue();
				}
				productListVO.setAutoRestoration(autoRestoration);
				log.debug(autoRestoration);
				
				String waitingPeriods = "";
				colNum = "Y";
				if (row.getCell(24) != null) {
					XSSFCell xSSFCell = row.getCell(24);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						waitingPeriods = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						waitingPeriods = (row.getCell(24).getStringCellValue() == null) ? " "
								: row.getCell(24).getStringCellValue();
				}
				productListVO.setWaitingPeriods(waitingPeriods);
				log.debug(waitingPeriods);
				
				String preExsistingDiseases = "";
				colNum = "Z";
				if (row.getCell(25) != null) {
					XSSFCell xSSFCell = row.getCell(25);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						preExsistingDiseases = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						preExsistingDiseases = (row.getCell(25).getStringCellValue() == null) ? " "
								: row.getCell(25).getStringCellValue();
				}
				productListVO.setPreExsistingDiseases(preExsistingDiseases);
				log.debug(preExsistingDiseases);
				
				String specificDiseases = "";
				colNum = "AA";
				if (row.getCell(26) != null) {
					XSSFCell xSSFCell = row.getCell(26);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						specificDiseases = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						specificDiseases = (row.getCell(26).getStringCellValue() == null) ? " "
								: row.getCell(26).getStringCellValue();
				}
				productListVO.setSpecificDiseases(specificDiseases);
				log.debug(specificDiseases);
				
				String initialWaitingPeriod = "";
				colNum = "AB";
				if (row.getCell(27) != null) {
					XSSFCell xSSFCell = row.getCell(27);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						initialWaitingPeriod = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						initialWaitingPeriod = (row.getCell(27).getStringCellValue() == null) ? " "
								: row.getCell(27).getStringCellValue();
				}
				productListVO.setInitialWaitingPeriod(initialWaitingPeriod);
				log.debug(initialWaitingPeriod);
				
				String specialFeatures = "";
				colNum = "AC";
				if (row.getCell(28) != null) {
					XSSFCell xSSFCell = row.getCell(28);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						specialFeatures = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						specialFeatures = (row.getCell(28).getStringCellValue() == null) ? " "
								: row.getCell(28).getStringCellValue();
				}
				productListVO.setSpecialFeatures(specialFeatures);
				log.debug(specialFeatures);
				
				String brochure = "";
				colNum = "AD";
				if (row.getCell(29) != null) {
					XSSFCell xSSFCell = row.getCell(29);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						brochure = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						brochure = (row.getCell(29).getStringCellValue() == null) ? " "
								: row.getCell(29).getStringCellValue();
				}
				productListVO.setBrochure(brochure);
				log.debug(brochure);
				
				String policyClause = "";
				colNum = "AE";
				if (row.getCell(30) != null) {
					XSSFCell xSSFCell = row.getCell(30);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						policyClause = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						policyClause = (row.getCell(30).getStringCellValue() == null) ? " "
								: row.getCell(30).getStringCellValue();
				}
				productListVO.setPolicyClause(policyClause);
				log.debug(policyClause);
				
				productListsVO.add(productListVO);
			}
			
			if(CollectionUtils.isNotEmpty(productListsVO)) {
				productDAO.saveAllProducts(productListsVO);
				log.info("Save Products End");
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new FaultException(
					"Unable To Read The Data... Row Number '" + rowNum + "' Column  '" + colNum + "' Name : " + nameP);
		}

		log.debug("ProductsList Read File End ::{} " , rowNum);
		
		return productListsVO;
	}
	
	public List<ProductComparisonMappingVO> uploadProductsComparisonMapping(XSSFSheet worksheet) {
		log.debug("ProductsComparisonMapping Read File Starts ::");
		String nameP = null;
		int rowNum = 0;
		String colNum = "";
		List<ProductComparisonMappingVO> productComparisonMappingVOs = new ArrayList<>();
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ApplicationConstant.DATETIMEFORMAT);
			LocalDateTime now = LocalDateTime.now();
			log.debug("Date Format :: {} " , dtf.format(now));
			String batchId = dtf.format(now);
			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				ProductComparisonMappingVO productComparisonMappingVO = new ProductComparisonMappingVO();
				
				productComparisonMappingVO.setBatchId(batchId);
				productComparisonMappingVO.setIsActive("True");
				
				rowNum = i;
				log.debug("Row Num={} " , rowNum);
				XSSFRow row = worksheet.getRow(i);
				
				colNum = "A";
				String productCode = row.getCell(0).getStringCellValue();
				nameP = productCode;
				productComparisonMappingVO.setProductCode(productCode.trim());
				log.debug(productCode);
				
				String mappingProductCode = "";
				colNum = "B";
				if (row.getCell(1) != null) {
					XSSFCell xSSFCell = row.getCell(1);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						mappingProductCode = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						mappingProductCode = (row.getCell(1).getStringCellValue() == null) ? " "
								: row.getCell(1).getStringCellValue();
				}
				log.debug(mappingProductCode);
				productComparisonMappingVO.setMappingProductCode(mappingProductCode.trim());
				
				String sequence = "";
				colNum = "C";
				if (row.getCell(2) != null) {
					XSSFCell xSSFCell = row.getCell(2);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						sequence = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						sequence = (row.getCell(2).getStringCellValue() == null) ? " "
								: row.getCell(2).getStringCellValue();
				}
				productComparisonMappingVO.setSequence(sequence);
				log.debug(sequence);
				
				String instalmentPremium = "";
				colNum = "D";
				if (row.getCell(3) != null) {
					XSSFCell xSSFCell = row.getCell(3);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						instalmentPremium = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						instalmentPremium = (row.getCell(3).getStringCellValue() == null) ? " "
								: row.getCell(3).getStringCellValue();
				}
				productComparisonMappingVO.setInstalmentPremium(instalmentPremium);
				log.debug(instalmentPremium);
				
				
				String planType = "";
				colNum = "E";
				if (row.getCell(4) != null) {
					XSSFCell xSSFCell = row.getCell(4);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						planType = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						planType = (row.getCell(4).getStringCellValue() == null) ? " "
								: row.getCell(4).getStringCellValue();
				}
				productComparisonMappingVO.setPlanType(planType);
				log.debug(planType);
				
				
				String schemeCode = "";
				colNum = "F";
				if (row.getCell(5) != null) {
					XSSFCell xSSFCell = row.getCell(5);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						schemeCode = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						schemeCode = (row.getCell(5).getStringCellValue() == null) ? " "
								: row.getCell(5).getStringCellValue();
				}
				productComparisonMappingVO.setSchemeCode(schemeCode);
				log.debug(schemeCode);
				
				
				String ageBandInYears = "";
				colNum = "G";
				if (row.getCell(6) != null) {
					XSSFCell xSSFCell = row.getCell(6);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						ageBandInYears = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						ageBandInYears = (row.getCell(6).getStringCellValue() == null) ? " "
								: row.getCell(6).getStringCellValue();
				}
				productComparisonMappingVO.setAgeBandInYears(ageBandInYears);
				log.debug(ageBandInYears);
				
				
				String sumInsured = "";
				colNum = "H";
				if (row.getCell(7) != null) {
					XSSFCell xSSFCell = row.getCell(7);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						sumInsured = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						sumInsured = (row.getCell(7).getStringCellValue() == null) ? " "
								: row.getCell(7).getStringCellValue();
				}
				productComparisonMappingVO.setSumInsured(sumInsured);
				log.debug(sumInsured);
				
				
				String zone = "";
				colNum = "I";
				if (row.getCell(8) != null) {
					XSSFCell xSSFCell = row.getCell(8);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						zone = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						zone = (row.getCell(8).getStringCellValue() == null) ? " "
								: row.getCell(8).getStringCellValue();
				}
				productComparisonMappingVO.setZone(zone);
				log.debug(zone);
				
				
				String buyBackPedYN = "";
				colNum = "J";
				if (row.getCell(9) != null) {
					XSSFCell xSSFCell = row.getCell(9);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						buyBackPedYN = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						buyBackPedYN = (row.getCell(9).getStringCellValue() == null) ? " "
								: row.getCell(9).getStringCellValue();
				}
				productComparisonMappingVO.setBuyBackPedYN(buyBackPedYN);
				log.debug(buyBackPedYN);
				
				String mappingTableName = "";
				colNum = "K";
				if (row.getCell(10) != null) {
					XSSFCell xSSFCell = row.getCell(10);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						mappingTableName = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						mappingTableName = (row.getCell(10).getStringCellValue() == null) ? " "
								: row.getCell(10).getStringCellValue();
				}
				productComparisonMappingVO.setMappingTableName(mappingTableName);
				log.debug(mappingTableName);
				
				String combination = "";
				colNum = "L";
				if (row.getCell(11) != null) {
					XSSFCell xSSFCell = row.getCell(11);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						combination = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						combination = (row.getCell(11).getStringCellValue() == null) ? " "
								: row.getCell(11).getStringCellValue();
				}
				productComparisonMappingVO.setCombination(combination);
				log.debug(combination);
				
				productComparisonMappingVOs.add(productComparisonMappingVO);
			}
			
			if(CollectionUtils.isNotEmpty(productComparisonMappingVOs)) {
				productDAO.saveProductComparisonMapping(productComparisonMappingVOs);
				log.info("Save Products End");
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new FaultException(
					"ProductsComparisonMapping Unable To Read The Data... Row Number '" + rowNum + "' ProductsComparisonMapping Column  '" + colNum + "' ProductsComparisonMapping Name : " + nameP);
		}

		log.debug("Read File End ::{} " , rowNum);
		
		return productComparisonMappingVOs;
	}

	public List<ProductPincodeVO> uploadProductsPincode(XSSFSheet worksheet) {
		log.debug("Read Product Pincode File Starts ::");
		String nameP = null;
		int rowNum = 0;
		String colNum = "";
		List<ProductPincodeVO> productPincodeVOs = new ArrayList<>();
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ApplicationConstant.DATETIMEFORMAT);
			LocalDateTime now = LocalDateTime.now();
			log.debug("Date Format :: {} " , dtf.format(now));
			String batchId = dtf.format(now);
			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				ProductPincodeVO productPincodeVO = new ProductPincodeVO();
				
				productPincodeVO.setBatchId(batchId);
				productPincodeVO.setIsActive("TRUE");
				productPincodeVO.setFlag("2");
				
				rowNum = i;
				log.debug("Row Num={} " , rowNum);
				XSSFRow row = worksheet.getRow(i);
				
				colNum = "A";
				String productCode = row.getCell(0).getStringCellValue();
				nameP = productCode;
				productPincodeVO.setProductCode(productCode.trim());
				log.debug(productCode);
				
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
				log.debug(productName);
				productPincodeVO.setProductName(productName.trim());
				
				String pinCode = "";
				colNum = "C";
				if (row.getCell(2) != null) {
					XSSFCell xSSFCell = row.getCell(2);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						pinCode = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						pinCode = (row.getCell(2).getStringCellValue() == null) ? " "
								: row.getCell(2).getStringCellValue();
				}
				productPincodeVO.setPinCode(pinCode);
				log.debug(pinCode);
				
				String zone = "";
				colNum = "D";
				if (row.getCell(3) != null) {
					XSSFCell xSSFCell = row.getCell(3);
					if (xSSFCell.getCellType() == CellType.NUMERIC)
						zone = NumberToTextConverter.toText(xSSFCell.getNumericCellValue());
					if (xSSFCell.getCellType() == CellType.STRING)
						zone = (row.getCell(3).getStringCellValue() == null) ? " "
								: row.getCell(3).getStringCellValue();
				}
				productPincodeVO.setZone(zone);
				log.debug(zone);

				productPincodeVOs.add(productPincodeVO);
			}
			
			if(CollectionUtils.isNotEmpty(productPincodeVOs)) {
				productPincodeRepo.saveAll(productPincodeVOs);
				log.info("Save Product Pincode End");
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new FaultException(
					"ProductsPincode Unable To Read The Data... Row Number '" + rowNum + "' ProductsPincode Column  '" + colNum + "' ProductsPincode Name : " + nameP);
		}

		log.debug("Read File End ::{} " , rowNum);
		
		return productPincodeVOs;
	}

}
