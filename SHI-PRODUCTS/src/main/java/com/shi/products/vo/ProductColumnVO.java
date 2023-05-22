package com.shi.products.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;


@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name="SHI_PRODUCTS_COLUMN")
public class ProductColumnVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String productCode;
	private String batchId;
	private String productName;
	private String classificationOfPolicies;
	private String typeOfCover;
	private String planVariants;
	private String ageOfEntry;
	private String size;
	private String preAcceptanceMedicalScreening;
	private String sumInsuredOptions;
	private String policyTerm;
	private String installmentFacility;
	private String gracePeriod;
	private String roomRent;
	private String preAndPostHospitalisation;
	private String dayCareProcedures;
	private String cataract;
	private String healthCheckup;
	private String roadAmbulance;
	private String airAmbulance;
	private String ayushTreatment; 
	private String renewals;
	private String copayment;
	private String modernTreatment;
	private String cummulativeBonus;
	private String waitingPeriods;
	private String preExsistingDiseases;
	private String specificDiseases;
	private String initialWaitingPeriod;
	private String specialFeatures;
	private String brochure;
	private String policyClause;
	
}
