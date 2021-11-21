package org.deb.best.plan.model;

import java.util.HashSet;
import java.util.Set;

public class Plan {
	private int price;
	private Set<String> missingFeatures = new HashSet<>();
	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	/**
	 * @return the missingFeatures
	 */
	public Set<String> getMissingFeatures() {
		return missingFeatures;
	}
	/**
	 * @param missingFeatures the missingFeatures to set
	 */
	public void setMissingFeatures(Set<String> missingFeatures) {
		this.missingFeatures = missingFeatures;
	}
	

}
