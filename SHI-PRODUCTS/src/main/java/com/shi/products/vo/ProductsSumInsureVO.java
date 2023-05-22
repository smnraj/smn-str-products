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
@Table(name="SHI_PRODUCTS_YEARS_SUMINSURE")
public class ProductsSumInsureVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String batchId;
	private String productCode;
	private String productName;
	private String period;
	private String planType;
	private String zone;
	private String pinCode;
	private String ped;
	private Integer deduction;
	private String onlineDiscountPerc;
	private Integer sumInsure;
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
	public String getPlanType() {
		return planType;
	}
	public String getZone() {
		return zone;
	}
	public String getPinCode() {
		return pinCode;
	}
	public String getPED() {
		return ped;
	}
	public Integer getDeduction() {
		return deduction;
	}
	public String getOnlineDiscountPerc() {
		return onlineDiscountPerc;
	}
	public Integer getSumInsure() {
		return sumInsure;
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
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public void setPED(String pED) {
		ped = pED;
	}
	public void setDeduction(Integer deduction) {
		this.deduction = deduction;
	}
	public void setOnlineDiscountPerc(String onlineDiscountPerc) {
		this.onlineDiscountPerc = onlineDiscountPerc;
	}
	public void setSumInsure(Integer sumInsure) {
		this.sumInsure = sumInsure;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public int hashCode() {
		return Objects.hash(ped, batchId, deduction, flag, id, isActive, onlineDiscountPerc, period, pinCode, planType,
				productCode, productName, sumInsure, zone);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductsSumInsureVO other = (ProductsSumInsureVO) obj;
		return Objects.equals(ped, other.ped) && Objects.equals(batchId, other.batchId)
				&& Objects.equals(deduction, other.deduction) && Objects.equals(flag, other.flag)
				&& Objects.equals(id, other.id) && Objects.equals(isActive, other.isActive)
				&& Objects.equals(onlineDiscountPerc, other.onlineDiscountPerc) && Objects.equals(period, other.period)
				&& Objects.equals(pinCode, other.pinCode) && Objects.equals(planType, other.planType)
				&& Objects.equals(productCode, other.productCode) && Objects.equals(productName, other.productName)
				&& Objects.equals(sumInsure, other.sumInsure) && Objects.equals(zone, other.zone);
	}
	@Override
	public String toString() {
		return "ProductsSumInsureVO [id=" + id + ", batchId=" + batchId + ", productCode=" + productCode
				+ ", productName=" + productName + ", period=" + period + ", planType=" + planType + ", zone=" + zone
				+ ", pinCode=" + pinCode + ", PED=" + ped + ", deduction=" + deduction + ", onlineDiscountPerc="
				+ onlineDiscountPerc + ", sumInsure=" + sumInsure + ", isActive=" + isActive + ", flag=" + flag + "]";
	}
	
}
