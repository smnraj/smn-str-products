/**
 * 
 */
package com.shi.products.vo;

import java.sql.Timestamp;
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
@Table(name="SHI_PRODUCTS_SOURCE_SYSTEM")
public class SourceSystemVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String sourceSystem;
	private String productCode;
	private String productName;
	private String onlineDiscountPerc;
	private String prefixName;
	private Timestamp createdTime;
	private Timestamp modifiedTime;
	private String flag;
	private String isActive;
	
	public Long getId() {
		return id;
	}
	public String getSourceSystem() {
		return sourceSystem;
	}
	public String getProductCode() {
		return productCode;
	}
	public String getProductName() {
		return productName;
	}
	public String getOnlineDiscountPerc() {
		return onlineDiscountPerc;
	}
	public String getPrefixName() {
		return prefixName;
	}
	public Timestamp getCreatedTime() {
		return createdTime;
	}
	public Timestamp getModifiedTime() {
		return modifiedTime;
	}
	public String getFlag() {
		return flag;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setOnlineDiscountPerc(String onlineDiscountPerc) {
		this.onlineDiscountPerc = onlineDiscountPerc;
	}
	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	@Override
	public int hashCode() {
		return Objects.hash(createdTime, flag, id, isActive, modifiedTime, onlineDiscountPerc, prefixName, productCode,
				productName, sourceSystem);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SourceSystemVO other = (SourceSystemVO) obj;
		return Objects.equals(createdTime, other.createdTime) && Objects.equals(flag, other.flag)
				&& Objects.equals(id, other.id) && Objects.equals(isActive, other.isActive)
				&& Objects.equals(modifiedTime, other.modifiedTime)
				&& Objects.equals(onlineDiscountPerc, other.onlineDiscountPerc)
				&& Objects.equals(prefixName, other.prefixName) && Objects.equals(productCode, other.productCode)
				&& Objects.equals(productName, other.productName) && Objects.equals(sourceSystem, other.sourceSystem);
	}
	@Override
	public String toString() {
		return "SourceSystemVO [id=" + id + ", sourceSystem=" + sourceSystem + ", productCode=" + productCode
				+ ", productName=" + productName + ", onlineDiscountPerc=" + onlineDiscountPerc + ", prefixName="
				+ prefixName + ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime + ", flag=" + flag
				+ ", isActive=" + isActive + "]";
	}

}
