CREATE DATABASE SHI_PRODUCTS;
USE SHI_PRODUCTS;
CREATE TABLE SHI_PRODUCTS_LIST (
    id bigint(20) NOT NULL auto_increment,
    productCode varchar(255),
    batchId varchar(255),
    productName varchar(255),
    typeOfCover varchar(255),
    planVariants varchar(255),
    ageOfEntry varchar(255),
    size varchar(255),
    preAcceptanceMedicalScreening varchar(255),
    sumInsuredOptions varchar(255),
    policyTerm varchar(255),
    installmentFacility varchar(255),
    gracePeriod varchar(255),
    roomRent varchar(255),
    preAndPostHospitalisation varchar(255),
    dayCareProcedures varchar(255),
    cataract varchar(255),
    healthCheckup varchar(255),
    roadAmbulance varchar(255),
    airAmbulance varchar(255),
    ayushTreatment varchar(255),
    renewals varchar(255),
    copayment varchar(255),
    modernTreatment varchar(255),
    cummulativeBonus varchar(255),
	autoRestoration varchar(255),
    waitingPeriods varchar(255),
    preExsistingDiseases varchar(255),
	specificDiseases varchar(255),
    initialWaitingPeriod varchar(255),
    specialFeatures text,
	brochure varchar(255),
	policyClause varchar(255),
    isActive varchar(255),
    flag varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE SHI_PRODUCTS_COLUMN (
    id bigint(20) NOT NULL auto_increment,
    productCode varchar(10),
    batchId varchar(10),
    productName varchar(10),
	classificationOfPolicies varchar(10),
    typeOfCover varchar(10),
    planVariants varchar(10),
    ageOfEntry varchar(10),
    size varchar(10),
	preAcceptanceMedicalScreening varchar(10),
    sumInsuredOptions varchar(10),
    policyTerm varchar(10),
    installmentFacility varchar(10),
    gracePeriod varchar(10),
    roomRent varchar(10),
    preAndPostHospitalisation varchar(10),
    dayCareProcedures varchar(10),
    cataract varchar(10),
    healthCheckup varchar(10),
    roadAmbulance varchar(10),
    airAmbulance varchar(10),
    ayushTreatment varchar(10),
    renewals varchar(10),
    copayment varchar(10),
    modernTreatment varchar(10),
    cummulativeBonus varchar(10),
    waitingPeriods varchar(10),
    preExsistingDiseases varchar(10),
    specificDiseases varchar(10),
    initialWaitingPeriod varchar(10),
    specialFeatures varchar(10),
	brochure varchar(10),
	policyClause varchar(10),
    PRIMARY KEY (id)
);

INSERT INTO SHI_PRODUCTS_COLUMN (productCode, batchId, productName, classificationOfPolicies, typeOfCover, planVariants, ageOfEntry, size, preAcceptanceMedicalScreening, 
sumInsuredOptions, policyTerm, installmentFacility, gracePeriod, roomRent, preAndPostHospitalisation, dayCareProcedures, cataract,
healthCheckup, roadAmbulance, airAmbulance, ayushTreatment, renewals, copayment, modernTreatment, cummulativeBonus, waitingPeriods,
preExsistingDiseases, specificDiseases, initialWaitingPeriod, specialFeatures, brochure, policyClause)
VALUES("True", "False", "True", "True", "True", "True", "True", "True", "True", "True", "True", "True", "True", "True", "True", "True",
"True", "True", "True", "True", "True", "True", "True", "True", "True", "True", "True", "True", "True", "True", "True", "True");

CREATE TABLE SHI_PRODUCTS_COMPARISON_MAPPING (
    id bigint(20) NOT NULL auto_increment,
    productCode varchar(100),
	mappingProductCode varchar(100),
    sequence varchar(50),
	combination varchar(100),
	instalmentPremium varchar(50),
	planType varchar(50),
	schemeCode VARCHAR(50),
	ageBandInYears varchar(50),
	sumInsured varchar(50),
	zone varchar(50),
	buyBackPedYN VARCHAR(10),
	deduction varchar(50),
	mappingTableName varchar(255),
    isActive varchar(50),
    flag varchar(50),
    batchId varchar(50),
    PRIMARY KEY (id)
);

CREATE TABLE SHI_PRODUCTS_PINCODE (
    id bigint(20) NOT NULL auto_increment,
	batchId varchar(50),
    productCode varchar(100),
    productName varchar(200),
	zone varchar(50),
    pinCode varchar(50),
    isActive varchar(50),
    flag varchar(50),
    PRIMARY KEY (id)
);

CREATE TABLE SHI_PRODUCTS_FAMILY_COMBINATIONS (
id bigint(20) NOT NULL auto_increment,
batchId varchar(255),
productCode varchar(50),
productName varchar(255),
adult INTEGER,
child INTEGER,
parentAndParentInlaw INTEGER,
flag varchar(10),
isActive VARCHAR(10),
PRIMARY KEY (id)
);

CREATE TABLE SHI_PRODUCTS_YEARS_SUMINSURE (
id bigint(20) NOT NULL auto_increment,
batchId varchar(255),
productCode varchar(50),
productName varchar(255),
period varchar(10),
planType varchar(50),
zone varchar(50),
pinCode varchar(50),
PED varchar(50),
deduction INTEGER,
onlineDiscountPerc VARCHAR(10),
sumInsure INTEGER,
flag varchar(10),
isActive VARCHAR(10),
PRIMARY KEY (id)
);

CREATE TABLE SHI_PRODUCTS_ADULTS_AGE (
id bigint(20) NOT NULL auto_increment,
batchId varchar(255),
productCode varchar(50),
productName varchar(255),
period varchar(10),
ageBandInYears varchar(50),
schemeCode varchar(50),
plan varchar(50),
flag varchar(10),
isActive VARCHAR(10),
PRIMARY KEY (id)
);

CREATE TABLE SHI_PRODUCTS_CHILD_AGE (
id bigint(20) NOT NULL auto_increment,
batchId varchar(255),
productCode varchar(50),
productName varchar(255),
period varchar(10),
ageBandInYears varchar(50),
flag varchar(10),
isActive VARCHAR(10),
PRIMARY KEY (id)
);

CREATE TABLE SHI_PRODUCTS_PARENT_PARENTINLAW_AGE (
id bigint(20) NOT NULL auto_increment,
batchId varchar(255),
productCode varchar(50),
productName varchar(255),
period varchar(10),
ageBandInYears varchar(50),
flag varchar(10),
isActive VARCHAR(10),
PRIMARY KEY (id)
);


CREATE TABLE SHI_PRODUCTS_GET_QUOTE_REQUEST (
id bigint(20) NOT NULL auto_increment,
batchId varchar(50),
productId bigint(20),
familyId bigint(20),
sumInsureId bigint(20),
adultAgeId bigint(20),
childAgeId bigint(20),
parentAgeId bigint(20),
productBatchId varchar(50),
productCode varchar(50),
productName varchar(200),
insuranceCover varchar(50),
deduction varchar(50),
adults varchar(50),
child varchar(50),
schemeCode varchar(50),
adult1AgeBandInYears varchar(50),
adult2AgeBandInYears varchar(50),
adult1AgeList VARCHAR(255),
adult2AgeList VARCHAR(255),
adultDOB1 varchar(50),
adultDOB2 varchar(50),
zone varchar(50),
pinCode varchar(50),
period varchar(50),
option1 varchar(50),
plan varchar(50),
polFmDt varchar(50),
parents varchar(50),
parent1AgeBandInYears varchar(50),
parent2AgeBandInYears varchar(50),
parent3AgeBandInYears varchar(50),
parent4AgeBandInYears varchar(50),
parent1AgeList VARCHAR(255),
parent2AgeList VARCHAR(255),
parent3AgeList VARCHAR(255),
parent4AgeList VARCHAR(255),
parentDOB1 varchar(50),
parentDOB2 varchar(50),
parentDOB3 varchar(50),
parentDOB4 varchar(50),
insuranceCover2 varchar(50),
onlineDiscountPerc varchar(50),
areaCode varchar(50),
addDiscntYN varchar(50),
divnCode varchar(50),
category varchar(50),
buyBackPedYN varchar(50),
hospitalCashYN varchar(50),
patientCareYN varchar(50),
addOnCvrSec1YN varchar(50),
adultLumpSumSI varchar(50),
childLumpSumSI1 varchar(50),
childLumpSumSI2 varchar(50),
childLumpSumSI3 varchar(50),
child1AgeBandInYears varchar(50),
child2AgeBandInYears varchar(50),
child3AgeBandInYears varchar(50),
child1AgeList VARCHAR(255),
child2AgeList VARCHAR(255),
child3AgeList VARCHAR(255),
childDOB1 varchar(50),
childDOB2 varchar(50),
childDOB3 varchar(50),
message VARCHAR(255),
flag varchar(50),
isActive VARCHAR(10),
PRIMARY KEY (id)
);

CREATE TABLE SHI_PRODUCTS_GET_QUOTE_RESPONSE (
id bigint(20) NOT NULL auto_increment,
batchId varchar(50),
getQuoteReqId bigint(20),
productCode varchar(50),
productName varchar(200),
productId bigint(20),
familyId bigint(20),
productBatchId varchar(50),
insuranceCover varchar(50),
deduction varchar(50),
adults varchar(50),
child varchar(50),
parents varchar(50),
schemeCode VARCHAR(50),
zone varchar(50),
pinCode VARCHAR(20),
period varchar(50),
plan varchar(50),
adult1AgeBandInYears varchar(50),
adult2AgeBandInYears varchar(50),
adult1AgeList VARCHAR(255),
adult2AgeList VARCHAR(255),
adultDOB1 varchar(50),
adultDOB2 varchar(50),
child1AgeBandInYears varchar(50),
child2AgeBandInYears varchar(50),
child3AgeBandInYears varchar(50),
child1AgeList VARCHAR(255),
child2AgeList VARCHAR(255),
child3AgeList VARCHAR(255),
childDOB1 varchar(50),
childDOB2 varchar(50),
childDOB3 varchar(50),
parent1AgeBandInYears varchar(50),
parent2AgeBandInYears varchar(50),
parent3AgeBandInYears varchar(50),
parent4AgeBandInYears varchar(50),
parent1AgeList VARCHAR(255),
parent2AgeList VARCHAR(255),
parent3AgeList VARCHAR(255),
parent4AgeList VARCHAR(255),
parentDOB1 varchar(50),
parentDOB2 varchar(50),
parentDOB3 varchar(50),
parentDOB4 varchar(50),
category varchar(50),
buyBackPedYN VARCHAR(10),
onlineDiscountPerc VARCHAR(10),
premiumAmount varchar(50),
premiumTax varchar(50),
totalAmount varchar(50),
message varchar(255),
hlflyPremAmntFirstPrem varchar(50),
hlflyPremAmntFirstTax varchar(50),
hlflyPremAmntFirstTotal varchar(50),
hlflyPremAmntOthersPrem varchar(50),
hlflyPremAmntOthersTax varchar(50),
hlflyPremAmntOthersTotal varchar(50),
qtrlyPremAmntFirstPrem varchar(50),
qtrlyPremAmntFirstTax varchar(50),
qtrlyPremAmntFirstTotal varchar(50),
qtrlyPremAmntOthersPrem varchar(50),
qtrlyPremAmntOthersTax varchar(50),
qtrlyPremAmntOthersTotal varchar(50),
mnthlyPremAmntFirstPrem varchar(50),
mnthlyPremAmntFirstTax varchar(50),
mnthlyPremAmntFirstTotal varchar(50),
mnthlyPremAmntOthersPrem varchar(50),
mnthlyPremAmntOthersTax varchar(50),
mnthlyPremAmntOthersTotal varchar(50),
totalRuralDiscountAmt varchar(50),
totalOnlineDiscountAmt varchar(50),
totalAddDiscnt varchar(50),
buyBackPrem varchar(50),
hospitalCashPrem varchar(50),
patientCarePrem varchar(50),
addOnCvrSec1Prem varchar(50),
addOnCvrSec2Prem varchar(50),
saving2Year varchar(50),
saving3Year varchar(50),
flag varchar(50),
isActive VARCHAR(10),
PRIMARY KEY (id)
);


CREATE TABLE SHI_PRODUCTS_AGE_AND_AGEBANDS (
id bigint(20) NOT NULL auto_increment,
batchId varchar(255),
productCode varchar(50),
productName varchar(255),
period varchar(10),
plan varchar(50),
schemeCode varchar(50),
person varchar(50),
age varchar(50),
ageBandInYears varchar(50),
flag varchar(10),
isActive VARCHAR(10),
PRIMARY KEY (id)
);

CREATE TABLE SHI_PRODUCTS_QUOTES_AUDIT (
    uniqueId bigint(20) NOT NULL auto_increment,
    productCode varchar(50),
    productName varchar(255),
	sourceSystem varchar(50),
	quoteResId bigint(20),
	uuid varchar(100),
	createdTime timestamp default current_timestamp,
	modifiedTime timestamp default current_timestamp,
	PRIMARY KEY (uniqueId)
);
ALTER TABLE SHI_PRODUCTS_QUOTES_AUDIT AUTO_INCREMENT=100;

CREATE TABLE SHI_PRODUCTS_SOURCE_SYSTEM (
    id bigint(20),
    sourceSystem varchar(200),
	productCode varchar(50),
    productName varchar(255),
	divisonCode varchar(255),
    onlineDiscountPerc varchar(50),
	prefixName varchar(200),
	createdTime timestamp default current_timestamp,
	modifiedTime timestamp default current_timestamp,
	flag varchar(10),
	isActive VARCHAR(10),
	PRIMARY KEY (id)
);

INSERT INTO SHI_PRODUCTS_SOURCE_SYSTEM (id, sourceSystem, productCode, productName, divisonCode, onlineDiscountPerc,
 prefixName, flag, isActive)
VALUES (1, 'star-purchase', 'MED-PRD-113', 'Medi classic Insurance Policy', '700002', '5', 'STAR_WEB', '0','TRUE'),
	   (2, 'star-purchase', 'MED-PRD-088', 'Star Comprehensive Insurance Policy', '700002', '5', 'STAR_WEB', '0','TRUE'),
	   (3, 'star-purchase', 'MED-PRD-091', 'Young Star Insurance Policy', '700002', '5', 'STAR_WEB', '0','TRUE'),
	   (4, 'star-purchase', 'MED-PRD-083', 'Arogya Sanjeevani Policy', '700002', '', 'STAR_WEB', '0','TRUE'),
	   (5, 'star-purchase', 'MED-PRD-119', 'Family Health Optima Insurance Plan', '700002', '', 'STAR_WEB', '0','TRUE'),
	   (6, 'star-purchase', 'MED-PRD-097', 'SUPER SURPLUS INDIVIDUAL', '700002', '5', 'STAR_WEB', '0','TRUE'),
	   (7, 'star-purchase', 'MED-PRD-098', 'SUPER SURPLUS FLOATER', '700002', '5', 'STAR_WEB', '0','TRUE'),
	   (8, 'star-purchase', 'MED-PRD-108', 'Star Women Care Insurance Policy', '700002', '5', 'STAR_WEB', '0','TRUE'),
	   (9, 'star-purchase', 'MED-PRD-109', 'Star Health Premier Insurance Policy', '700002', '', 'STAR_WEB', '0','TRUE'),
	   (10, 'star-purchase', 'MED-PRD-110', 'Star Health Assure Insurance Policy', '700002', '', 'STAR_WEB', '0','TRUE');	   

alter table SHI_PRODUCTS_GET_QUOTE_REQUEST add column createdTime timestamp not null default current_timestamp;
alter table SHI_PRODUCTS_GET_QUOTE_REQUEST add column modifiedTime timestamp not null  default current_timestamp;
alter table SHI_PRODUCTS_GET_QUOTE_RESPONSE add column createdTime timestamp not null  default current_timestamp;
alter table SHI_PRODUCTS_GET_QUOTE_RESPONSE add column modifiedTime timestamp not null  default current_timestamp;

#alter table SHI_PRODUCTS_GET_QUOTE_REQUEST add column createdTime timestamp default current_timestamp;
#alter table SHI_PRODUCTS_GET_QUOTE_REQUEST add column modifiedTime timestamp default current_timestamp;
#alter table SHI_PRODUCTS_GET_QUOTE_RESPONSE add column createdTime timestamp default current_timestamp;
#alter table SHI_PRODUCTS_GET_QUOTE_RESPONSE add column modifiedTime timestamp default current_timestamp;


CREATE TABLE SHI_PRODUCTS_QUOTE_USER (
    id bigint(20) NOT NULL auto_increment,
    username varchar(255),
	password varchar(255),
    role varchar(255),
	createdTime timestamp default current_timestamp,
	modifiedTime timestamp default current_timestamp,
	flag varchar(10),
	isActive VARCHAR(10),
	PRIMARY KEY (id)
);
ALTER TABLE SHI_PRODUCTS_QUOTE_USER AUTO_INCREMENT=100;

INSERT INTO SHI_PRODUCTS_QUOTE_USER (username,password,role,createdTime,modifiedTime,flag,isActive) VALUES ('posp_uat','$t@r$3cre3T@2023','user', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '0', 'TRUE');
INSERT INTO SHI_PRODUCTS_QUOTE_USER (username,password,role,createdTime,modifiedTime,flag,isActive) VALUES ('website_uat','$t@r$3cre3T@2023','user',  CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '0', 'TRUE');
