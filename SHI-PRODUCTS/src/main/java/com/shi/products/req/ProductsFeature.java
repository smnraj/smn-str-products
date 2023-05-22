/**
 * 
 */
package com.shi.products.req;

import java.util.Objects;

/**
 * @author suman.raju
 *
 */
public class ProductsFeature {
	
	private String productCode;
	private String planVariants;
	private String buyBackPedYN;
	
	public String getProductCode() {
		return productCode;
	}
	public String getPlanVariants() {
		return planVariants;
	}
	public String getBuyBackPedYN() {
		return buyBackPedYN;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public void setPlanVariants(String planVariants) {
		this.planVariants = planVariants;
	}
	public void setBuyBackPedYN(String buyBackPedYN) {
		this.buyBackPedYN = buyBackPedYN;
	}
	@Override
	public int hashCode() {
		return Objects.hash(buyBackPedYN, planVariants, productCode);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductsFeature other = (ProductsFeature) obj;
		return Objects.equals(buyBackPedYN, other.buyBackPedYN) && Objects.equals(planVariants, other.planVariants)
				&& Objects.equals(productCode, other.productCode);
	}
	@Override
	public String toString() {
		return "ProductsFeature [productCode=" + productCode + ", planVariants=" + planVariants + ", buyBackPedYN="
				+ buyBackPedYN + "]";
	}
}
