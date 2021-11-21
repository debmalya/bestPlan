package org.deb.best.plan.service;

import java.io.IOException;
import java.util.List;

public interface FileContentService {
	
	List<String> getFileContent(String fileName) throws IOException;

}
