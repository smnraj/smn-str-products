/**
 * 
 */
package com.shi.products.res;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author suman.raju
 *
 */
public class ProductFeatureResponse {
	
	private List<Map<String, String>> productFeatures;

	public List<Map<String, String>> getProductFeatures() {
		return productFeatures;
	}

	public void setProductFeatures(List<Map<String, String>> productFeatures) {
		this.productFeatures = productFeatures;
	}

	@Override
	public int hashCode() {
		return Objects.hash(productFeatures);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductFeatureResponse other = (ProductFeatureResponse) obj;
		return Objects.equals(productFeatures, other.productFeatures);
	}

	@Override
	public String toString() {
		return "ProductFeatureResponse [productFeatures=" + productFeatures + ", getProductFeatures()="
				+ getProductFeatures() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
	}

}
