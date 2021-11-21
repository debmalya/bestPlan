package org.deb.best.plan.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PlanSelectorImplTest {

	PlanSelector planSelector = new PlanSelectorImpl();
	FileContentService fileContentService = new FileContentServiceImpl();

	@Test
	public void testSelectPlan_MissingFeature() throws IOException {
		List<String> records = fileContentService.getFileContent("./src/test/resources/example1.txt");
		assertEquals("0", planSelector.selectPlan(records, "email,voice,videocall"));
	}

	@Test
	public void testSelectPlan_MatchingFeature() throws IOException {
		List<String> records = fileContentService.getFileContent("./src/test/resources/example1.txt");
		assertEquals("225,PLAN1,PLAN3", planSelector.selectPlan(records, "email,voice,admin"));
	}

	@Test
	public void testSelectPlan_MatchingMultipleFeature() throws IOException {
		List<String> records = new ArrayList<>();
		records.add("PLAN1,100,voice,email");
		records.add("PLAN2,150,email,database,admin");
		records.add("PLAN3,125,voice,admin");
		records.add("PLAN4,35,database,admin");
		assertEquals("135,PLAN1,PLAN4", planSelector.selectPlan(records, "email,voice,admin"));
	}
	
	@Test
	public void testSelectPlan_PriceVarriance() throws IOException {
		List<String> records = new ArrayList<>();
		records.add("PLAN1,1000,voice,email");
		records.add("PLAN2,150,email,database,admin");
		records.add("PLAN3,125,voice,admin");
		records.add("PLAN4,35,database,admin");
		assertEquals("275,PLAN2,PLAN3", planSelector.selectPlan(records, "email,voice,admin"));
	}
	
	@Test
	public void testSelectPlan_MultiplePlans() throws IOException {
		List<String> records = new ArrayList<>();
		records.add("PLAN1,1000,voice,email");
		records.add("PLAN2,150,email,database");
		records.add("PLAN3,125,voice,video");
		records.add("PLAN4,5,database,admin");
		assertEquals("280,PLAN2,PLAN3,PLAN4", planSelector.selectPlan(records, "email,voice,admin"));
	}

}
