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
@Table(name="SHI_PRODUCTS_QUOTES_AUDIT")
public class QuotesAuditVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uniqueId;
	private String productCode;
	private String productName;
	private String sourceSystem;
	private Long quoteResId;
	private String uuid;
	private Timestamp createdTime;
	private Timestamp modifiedTime;
	
	public Long getUniqueId() {
		return uniqueId;
	}
	public String getProductCode() {
		return productCode;
	}
	public String getProductName() {
		return productName;
	}
	public String getSourceSystem() {
		return sourceSystem;
	}
	public Long getQuoteResId() {
		return quoteResId;
	}
	public String getUuid() {
		return uuid;
	}
	public Timestamp getCreatedTime() {
		return createdTime;
	}
	public Timestamp getModifiedTime() {
		return modifiedTime;
	}
	public void setUniqueId(Long uniqueId) {
		this.uniqueId = uniqueId;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public void setQuoteResId(Long quoteResId) {
		this.quoteResId = quoteResId;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	@Override
	public int hashCode() {
		return Objects.hash(createdTime, modifiedTime, productCode, productName, quoteResId, sourceSystem, uniqueId,
				uuid);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuotesAuditVO other = (QuotesAuditVO) obj;
		return Objects.equals(createdTime, other.createdTime) && Objects.equals(modifiedTime, other.modifiedTime)
				&& Objects.equals(productCode, other.productCode) && Objects.equals(productName, other.productName)
				&& Objects.equals(quoteResId, other.quoteResId) && Objects.equals(sourceSystem, other.sourceSystem)
				&& Objects.equals(uniqueId, other.uniqueId) && Objects.equals(uuid, other.uuid);
	}
	@Override
	public String toString() {
		return "QuotesAuditVO [uniqueId=" + uniqueId + ", productCode=" + productCode + ", productName=" + productName
				+ ", sourceSystem=" + sourceSystem + ", quoteResId=" + quoteResId + ", uuid=" + uuid + ", createdTime="
				+ createdTime + ", modifiedTime=" + modifiedTime + "]";
	}
	
	
}
