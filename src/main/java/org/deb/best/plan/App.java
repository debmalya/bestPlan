package org.deb.best.plan;

import java.io.IOException;

import org.deb.best.plan.service.FileContentService;
import org.deb.best.plan.service.FileContentServiceImpl;
import org.deb.best.plan.service.PlanSelector;
import org.deb.best.plan.service.PlanSelectorImpl;

public class App {

	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.err.println("Please mention input file name and features as argument");
		}else {
			FileContentService fileConentService = new FileContentServiceImpl();
			PlanSelector planSelector = new PlanSelectorImpl();
			System.out.println(planSelector.selectPlan(fileConentService.getFileContent(args[0]), args[1]));
		}

	}

}
