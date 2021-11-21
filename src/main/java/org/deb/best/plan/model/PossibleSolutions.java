package org.deb.best.plan.model;

import java.util.Set;

public class PossibleSolutions {
	
	private int price;
	private Set<String> plans;
	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}
	/**
	 * @return the plans
	 */
	public Set<String> getPlans() {
		return plans;
	}
	/**
	 * @param price
	 * @param plans
	 */
	public PossibleSolutions(int price, Set<String> plans) {
		this.price = price;
		this.plans = plans;
	}
	
	

}
