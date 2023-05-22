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
@Table(name="SHI_PRODUCTS_FAMILY_COMBINATIONS")
public class ProductsFamilyCombinationsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String batchId;
	private String productCode;
	private String productName;
	private Integer adult;
	private Integer child;
	private Integer parentAndParentInlaw;
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
	public Integer getAdult() {
		return adult;
	}
	public Integer getChild() {
		return child;
	}
	public Integer getParentAndParentInlaw() {
		return parentAndParentInlaw;
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
	public void setAdult(Integer adult) {
		this.adult = adult;
	}
	public void setChild(Integer child) {
		this.child = child;
	}
	public void setParentAndParentInlaw(Integer parentAndParentInlaw) {
		this.parentAndParentInlaw = parentAndParentInlaw;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public int hashCode() {
		return Objects.hash(adult, batchId, child, flag, id, isActive, parentAndParentInlaw, productCode, productName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductsFamilyCombinationsVO other = (ProductsFamilyCombinationsVO) obj;
		return Objects.equals(adult, other.adult) && Objects.equals(batchId, other.batchId)
				&& Objects.equals(child, other.child) && Objects.equals(flag, other.flag)
				&& Objects.equals(id, other.id) && Objects.equals(isActive, other.isActive)
				&& Objects.equals(parentAndParentInlaw, other.parentAndParentInlaw)
				&& Objects.equals(productCode, other.productCode) && Objects.equals(productName, other.productName);
	}
	@Override
	public String toString() {
		return "ProductsFamilyCombinationsVO [id=" + id + ", batchId=" + batchId + ", productCode=" + productCode
				+ ", productName=" + productName + ", adult=" + adult + ", child=" + child + ", parentAndParentInlaw="
				+ parentAndParentInlaw + ", isActive=" + isActive + ", flag=" + flag + "]";
	}
	
	
	
}
