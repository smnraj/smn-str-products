/**
 * 
 */
package com.shi.products.vo;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author suman.raju
 *
 */

@Entity
@Table(name="SHI_PRODUCTS_COMPARISON_MAPPING")
public class ProductComparisonMappingVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String productCode;
	private String mappingProductCode;
	private String sequence;
	private String instalmentPremium;
	private String planType;
	private String schemeCode;
	private String ageBandInYears;
	private String sumInsured;
	private String zone;
	private String buyBackPedYN;
	private String deduction;
	private String mappingTableName;
	private String combination;
	private String isActive;
	private String flag;
	private String batchId;
	
	public Long getId() {
		return id;
	}
	public String getProductCode() {
		return productCode;
	}
	public String getMappingProductCode() {
		return mappingProductCode;
	}
	public String getSequence() {
		return sequence;
	}
	public String getInstalmentPremium() {
		return instalmentPremium;
	}
	public String getPlanType() {
		return planType;
	}
	public String getSchemeCode() {
		return schemeCode;
	}
	public String getAgeBandInYears() {
		return ageBandInYears;
	}
	public String getSumInsured() {
		return sumInsured;
	}
	public String getZone() {
		return zone;
	}
	public String getBuyBackPedYN() {
		return buyBackPedYN;
	}
	public String getDeduction() {
		return deduction;
	}
	public String getMappingTableName() {
		return mappingTableName;
	}
	public String getCombination() {
		return combination;
	}
	public String getIsActive() {
		return isActive;
	}
	public String getFlag() {
		return flag;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public void setMappingProductCode(String mappingProductCode) {
		this.mappingProductCode = mappingProductCode;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public void setInstalmentPremium(String instalmentPremium) {
		this.instalmentPremium = instalmentPremium;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	public void setAgeBandInYears(String ageBandInYears) {
		this.ageBandInYears = ageBandInYears;
	}
	public void setSumInsured(String sumInsured) {
		this.sumInsured = sumInsured;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public void setBuyBackPedYN(String buyBackPedYN) {
		this.buyBackPedYN = buyBackPedYN;
	}
	public void setDeduction(String deduction) {
		this.deduction = deduction;
	}
	public void setMappingTableName(String mappingTableName) {
		this.mappingTableName = mappingTableName;
	}
	public void setCombination(String combination) {
		this.combination = combination;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(ageBandInYears, batchId, buyBackPedYN, combination, deduction, flag, id, instalmentPremium,
				isActive, mappingProductCode, mappingTableName, planType, productCode, schemeCode, sequence, sumInsured,
				zone);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductComparisonMappingVO other = (ProductComparisonMappingVO) obj;
		return Objects.equals(ageBandInYears, other.ageBandInYears) && Objects.equals(batchId, other.batchId)
				&& Objects.equals(buyBackPedYN, other.buyBackPedYN) && Objects.equals(combination, other.combination)
				&& Objects.equals(deduction, other.deduction) && Objects.equals(flag, other.flag)
				&& Objects.equals(id, other.id) && Objects.equals(instalmentPremium, other.instalmentPremium)
				&& Objects.equals(isActive, other.isActive)
				&& Objects.equals(mappingProductCode, other.mappingProductCode)
				&& Objects.equals(mappingTableName, other.mappingTableName) && Objects.equals(planType, other.planType)
				&& Objects.equals(productCode, other.productCode) && Objects.equals(schemeCode, other.schemeCode)
				&& Objects.equals(sequence, other.sequence) && Objects.equals(sumInsured, other.sumInsured)
				&& Objects.equals(zone, other.zone);
	}
	@Override
	public String toString() {
		return "ProductComparisonMappingVO [id=" + id + ", productCode=" + productCode + ", mappingProductCode="
				+ mappingProductCode + ", sequence=" + sequence + ", instalmentPremium=" + instalmentPremium
				+ ", planType=" + planType + ", schemeCode=" + schemeCode + ", ageBandInYears=" + ageBandInYears
				+ ", sumInsured=" + sumInsured + ", zone=" + zone + ", buyBackPedYN=" + buyBackPedYN + ", deduction="
				+ deduction + ", mappingTableName=" + mappingTableName + ", combination=" + combination + ", isActive="
				+ isActive + ", flag=" + flag + ", batchId=" + batchId + "]";
	}
	
	
	
}
