package org.deb.best.plan.service;


import java.util.List;

public interface PlanSelector {
  /**
   * From the feature request try to find out the proper plan. If no available plans then return
   * optional empty.
   *
   * @param plans list of available plans
   * @param features looking for
   */
  String selectPlan(List<String> plans, String features);
}
