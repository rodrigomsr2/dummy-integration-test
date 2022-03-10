package com.integration.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.MultiPartSpecification;

public class FileUtils {

	private static final String BASE_PATH = "src/test/resources/files/";
	private static final String XLS_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	private static byte[] getFileContentAsBytes(String testFileName) throws IOException {
		File file = new File(BASE_PATH + testFileName);
		FileInputStream input = new FileInputStream(file);
		return IOUtils.toByteArray(input);
	}
	
	public static MultiPartSpecification getMultiPart(String testFileName, String requestParamName) throws IOException {
		byte[] fileContent = getFileContentAsBytes(testFileName);
		
		return new MultiPartSpecBuilder(fileContent).
	               fileName(testFileName).
	               controlName(requestParamName).
	               mimeType(XLS_CONTENT_TYPE).
	               build();
  }
	
}
