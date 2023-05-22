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
@Table(name="SHI_PRODUCTS_CHILD_AGE")
public class ProductsChildAgeVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String batchId;
	private String productCode;
	private String productName;
	private String period;
	private String ageBandInYears;
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
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public int hashCode() {
		return Objects.hash(ageBandInYears, batchId, flag, id, isActive, period, productCode, productName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductsChildAgeVO other = (ProductsChildAgeVO) obj;
		return Objects.equals(ageBandInYears, other.ageBandInYears) && Objects.equals(batchId, other.batchId)
				&& Objects.equals(flag, other.flag) && Objects.equals(id, other.id)
				&& Objects.equals(isActive, other.isActive) && Objects.equals(period, other.period)
				&& Objects.equals(productCode, other.productCode) && Objects.equals(productName, other.productName);
	}
	@Override
	public String toString() {
		return "ProductsChildAgeVO [id=" + id + ", batchId=" + batchId + ", productCode=" + productCode
				+ ", productName=" + productName + ", period=" + period + ", ageBandInYears=" + ageBandInYears
				+ ", isActive=" + isActive + ", flag=" + flag + ", getId()=" + getId() + ", getBatchId()="
				+ getBatchId() + ", getProductCode()=" + getProductCode() + ", getProductName()=" + getProductName()
				+ ", getPeriod()=" + getPeriod() + ", getAgeBandInYears()=" + getAgeBandInYears() + ", getIsActive()="
				+ getIsActive() + ", getFlag()=" + getFlag() + ", hashCode()=" + hashCode() + ", getClass()="
				+ getClass() + ", toString()=" + super.toString() + "]";
	}
	
}
