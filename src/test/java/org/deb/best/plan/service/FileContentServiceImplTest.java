package org.deb.best.plan.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class FileContentServiceImplTest {
	
	FileContentService fileContentService = new FileContentServiceImpl();

	@Test
	public void testGetFileContent() throws IOException {
		List<String> records = fileContentService.getFileContent("./src/test/resources/example1.txt");
		assertTrue(!records.isEmpty());
		assertTrue(records.size() == 4);
		assertEquals("PLAN1,100,voice,email", records.get(0));
		assertEquals("PLAN2,150,email,database,admin", records.get(1));
		assertEquals("PLAN3,125,voice,admin", records.get(2));
		assertEquals("PLAN4,135,database,admin", records.get(3));
	}

}
