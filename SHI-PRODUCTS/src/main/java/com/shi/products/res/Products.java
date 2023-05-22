/**
 * 
 */
package com.shi.products.res;

import java.util.List;
import java.util.Objects;

/**
 * @author suman.raju
 *
 */
public class Products {
	
	private String sequence;
	private Product product;
	private List<Quotes> quotes;
	private List<String> message;
	
	public String getSequence() {
		return sequence;
	}
	public Product getProduct() {
		return product;
	}
	public List<Quotes> getQuotes() {
		return quotes;
	}
	public List<String> getMessage() {
		return message;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public void setQuotes(List<Quotes> quotes) {
		this.quotes = quotes;
	}
	public void setMessage(List<String> message) {
		this.message = message;
	}
	@Override
	public int hashCode() {
		return Objects.hash(message, product, quotes, sequence);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Products other = (Products) obj;
		return Objects.equals(message, other.message) && Objects.equals(product, other.product)
				&& Objects.equals(quotes, other.quotes) && Objects.equals(sequence, other.sequence);
	}
	@Override
	public String toString() {
		return "Products [sequence=" + sequence + ", product=" + product + ", quotes=" + quotes + ", message=" + message
				+ "]";
	}

}
