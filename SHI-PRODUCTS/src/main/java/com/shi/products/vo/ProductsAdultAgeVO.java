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
@Table(name="SHI_PRODUCTS_ADULTS_AGE")
public class ProductsAdultAgeVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String batchId;
	private String productCode;
	private String productName;
	private String period;
	private String ageBandInYears;
	private String schemeCode;
	private String plan;
	private String isActive;
	private String flag;
	public Long getId() {
		return id;
	}
	public String getBatchId() {
		return batchId;
	}
	public String getProductCode() {
		return productCode;
	}
	public String getProductName() {
		return productName;
	}
	public String getPeriod() {
		return period;
	}
	public String getAgeBandInYears() {
		return ageBandInYears;
	}
	public String getSchemeCode() {
		return schemeCode;
	}
	public String getPlan() {
		return plan;
	}
	public String getIsActive() {
		return isActive;
	}
	public String getFlag() {
		return flag;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public void setAgeBandInYears(String ageBandInYears) {
		this.ageBandInYears = ageBandInYears;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public int hashCode() {
		return Objects.hash(ageBandInYears, batchId, flag, id, isActive, period, plan, productCode, productName,
				schemeCode);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductsAdultAgeVO other = (ProductsAdultAgeVO) obj;
		return Objects.equals(ageBandInYears, other.ageBandInYears) && Objects.equals(batchId, other.batchId)
				&& Objects.equals(flag, other.flag) && Objects.equals(id, other.id)
				&& Objects.equals(isActive, other.isActive) && Objects.equals(period, other.period)
				&& Objects.equals(plan, other.plan) && Objects.equals(productCode, other.productCode)
				&& Objects.equals(productName, other.productName) && Objects.equals(schemeCode, other.schemeCode);
	}
	@Override
	public String toString() {
		return "ProductsAdultAgeVO [id=" + id + ", batchId=" + batchId + ", productCode=" + productCode
				+ ", productName=" + productName + ", period=" + period + ", ageBandInYears=" + ageBandInYears
				+ ", schemeCode=" + schemeCode + ", plan=" + plan + ", isActive=" + isActive + ", flag=" + flag + "]";
	}
	
}
