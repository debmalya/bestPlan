package org.deb.best.plan.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.deb.best.plan.model.Plan;
import org.deb.best.plan.model.PossibleSolutions;

public class PlanSelectorImpl implements PlanSelector {

//	Key is feature, value is the list of plan names where this feature is available.
//	(e.g. Key is voice value is [PLAN1,PLAN3].
	Map<String, Set<String>> featurePlanMap;

//	Key is plan name, value is price, features of that plan.
	Map<String, Plan> planMap = new HashMap<>();
//  features for which calculating best price
	Set<String> requiredFeatures = new HashSet<>();

	@Override
	public String selectPlan(List<String> plans, String features) {
		populateFeatureMap(features);

		populatePlanMap(plans);

		return bestPlan();
	}

	private String bestPlan() {
		boolean isMissingFeature = featurePlanMap.values().stream().filter(featuresSet -> featuresSet.isEmpty())
				.count() > 0L;
		if (isMissingFeature) {
			return "0";
		}

		return findBestPlan();
	}

	private String findBestPlan() {
		String bestPricePlan = "%d,%s";
		int minPrice = Integer.MAX_VALUE;
		
		List<PossibleSolutions> possibleSolutionList = new ArrayList<>();

		planMap.forEach((plan, planDetails) -> {
			possibleSolutionList.addAll(getComplimentaryPlans(plan, planDetails.getMissingFeatures()));
		});

		possibleSolutionList.sort((possibleSolution1, possibleSolution2) -> {
			return Integer.compare(possibleSolution1.getPrice(), possibleSolution2.getPrice());
		});
		minPrice = possibleSolutionList.get(0).getPrice();
		String[] plans = possibleSolutionList.get(0).getPlans().toArray(new String[0]);
		Arrays.sort(plans);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < plans.length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(plans[i]);
		}
		return String.format(bestPricePlan, minPrice, sb.toString());
	}

	/**
	 * Plan combination to make the required features.
	 * 
	 * @param plan            - original plan
	 * @param missingFeatures - and the missing features.
	 * @return
	 */
	private List<PossibleSolutions> getComplimentaryPlans(String plan, Set<String> missingFeatures) {
		List<PossibleSolutions> possibleSolutionList = new ArrayList<>();

		Set<String> requiredPlans = new HashSet<>();
		missingFeatures.forEach(eachMissingFeature -> {
			requiredPlans.addAll(featurePlanMap.get(eachMissingFeature));
		});

		Set<String> plans = new HashSet<>();
		plans.add(plan);
		AtomicInteger cumulativePrice = new AtomicInteger(planMap.get(plan).getPrice());
		Set<String> collectedMissingFeatures = new HashSet<>();
		
		requiredPlans.forEach(eachRequired -> {
			if (missingFeatures.stream().filter(eachMissingFeature -> planMap.get(eachRequired).getMissingFeatures().contains(eachMissingFeature)).count() == 0) {
				Set<String> matchedPlans = new HashSet<>();
				matchedPlans.add(plan);
				matchedPlans.add(eachRequired);
				PossibleSolutions possibleSolutions = new PossibleSolutions(planMap.get(plan).getPrice()+planMap.get(eachRequired).getPrice(), matchedPlans);
				possibleSolutionList.add(possibleSolutions);
			}else {
				cumulativePrice.addAndGet(planMap.get(eachRequired).getPrice());
				plans.add(eachRequired);
				collectedMissingFeatures.addAll(planMap.get(eachRequired).getMissingFeatures());
				if (collectedMissingFeatures.containsAll(missingFeatures)) {
					PossibleSolutions possibleSolutions = new PossibleSolutions(cumulativePrice.get(), plans);
					possibleSolutionList.add(possibleSolutions);
				}
			}
		});

		return possibleSolutionList;
	}

	private void populatePlanMap(List<String> plans) {

		for (String eachPlan : plans) {
			parseEachPlan(eachPlan);
		}

	}

	private void parseEachPlan(String eachPlan) {
		String[] planDetails = eachPlan.split(",");
		String planName = planDetails[0];
		Integer price = Integer.parseInt(planDetails[1]);

		Set<String> featureSet = new HashSet<>();
		for (int featureIndex = 2; featureIndex < planDetails.length; featureIndex++) {
			if (featurePlanMap.containsKey(planDetails[featureIndex])) {
				Set<String> matchingPlans = featurePlanMap.get(planDetails[featureIndex]);
				matchingPlans.add(planName);
				featureSet.add(planDetails[featureIndex]);
			}
		}

		Plan plan = new Plan();
		plan.setPrice(price);
		Set<String> missingFeatures = new HashSet<>();
		requiredFeatures.forEach(eachRequiredFeature -> {
			if (!featureSet.contains(eachRequiredFeature)) {
				missingFeatures.add(eachRequiredFeature);
			}
		});
		plan.setMissingFeatures(missingFeatures);
		planMap.put(planName, plan);

	}

	private void populateFeatureMap(String features) {

		String[] allFeatures = features.split(",");
		featurePlanMap = new HashMap<>(allFeatures.length);
		for (String eachFeature : allFeatures) {
			featurePlanMap.putIfAbsent(eachFeature, new HashSet<>());
			requiredFeatures.add(eachFeature);
		}

	}

}
