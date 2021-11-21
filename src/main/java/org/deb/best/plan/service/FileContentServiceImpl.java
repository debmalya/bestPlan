package org.deb.best.plan.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileContentServiceImpl implements FileContentService {

	public List<String> getFileContent(String fileName) throws IOException {
		Path path = Paths.get(fileName);
		return Files.readAllLines(path);
	}

}
