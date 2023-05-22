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
@Table(name="SHI_PRODUCTS_AGE_AND_AGEBANDS")
public class AgeAndAgeBandVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String batchId;
	private String productCode;
	private String productName;
	private String period;
	private String plan;
	private String schemeCode;
	private String person;
	private String age;
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
	public String getPlan() {
		return plan;
	}
	public String getSchemeCode() {
		return schemeCode;
	}
	public String getPerson() {
		return person;
	}
	public String getAge() {
		return age;
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
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public void setAge(String age) {
		this.age = age;
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
		return Objects.hash(age, ageBandInYears, batchId, flag, id, isActive, period, person, plan, productCode,
				productName, schemeCode);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgeAndAgeBandVO other = (AgeAndAgeBandVO) obj;
		return Objects.equals(age, other.age) && Objects.equals(ageBandInYears, other.ageBandInYears)
				&& Objects.equals(batchId, other.batchId) && Objects.equals(flag, other.flag)
				&& Objects.equals(id, other.id) && Objects.equals(isActive, other.isActive)
				&& Objects.equals(period, other.period) && Objects.equals(person, other.person)
				&& Objects.equals(plan, other.plan) && Objects.equals(productCode, other.productCode)
				&& Objects.equals(productName, other.productName) && Objects.equals(schemeCode, other.schemeCode);
	}
	@Override
	public String toString() {
		return "AgeAndAgeBandVO [id=" + id + ", batchId=" + batchId + ", productCode=" + productCode + ", productName="
				+ productName + ", period=" + period + ", plan=" + plan + ", schemeCode=" + schemeCode + ", person="
				+ person + ", age=" + age + ", ageBandInYears=" + ageBandInYears + ", isActive=" + isActive + ", flag="
				+ flag + "]";
	}

}
