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
@Table(name="SHI_PRODUCTS_PINCODE")
public class ProductPincodeVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String batchId;
	private String productCode;
	private String productName;
	private String zone;
	private String pinCode;
	private String isActive;
	private String flag;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public int hashCode() {
		return Objects.hash(batchId, flag, id, isActive, pinCode, productCode, productName, zone);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductPincodeVO other = (ProductPincodeVO) obj;
		return Objects.equals(batchId, other.batchId) && Objects.equals(flag, other.flag)
				&& Objects.equals(id, other.id) && Objects.equals(isActive, other.isActive)
				&& Objects.equals(pinCode, other.pinCode) && Objects.equals(productCode, other.productCode)
				&& Objects.equals(productName, other.productName) && Objects.equals(zone, other.zone);
	}
	@Override
	public String toString() {
		return "ProductPincodeVO [id=" + id + ", batchId=" + batchId + ", productCode=" + productCode + ", productName="
				+ productName + ", zone=" + zone + ", pinCode=" + pinCode + ", isActive=" + isActive + ", flag=" + flag
				+ ", getId()=" + getId() + ", getBatchId()=" + getBatchId() + ", getProductCode()=" + getProductCode()
				+ ", getProductName()=" + getProductName() + ", getZone()=" + getZone() + ", getPinCode()="
				+ getPinCode() + ", getIsActive()=" + getIsActive() + ", getFlag()=" + getFlag() + ", hashCode()="
				+ hashCode() + ", getClass()=" + getClass() + ", toString()=" + super.toString() + "]";
	}
	
}
