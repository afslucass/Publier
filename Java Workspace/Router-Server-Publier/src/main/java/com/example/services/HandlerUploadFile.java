package com.example.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class HandlerUploadFile {
	
	public static Resource createTempFile(byte[] content) throws IOException {
		Path tempFile = Files.createTempFile("upload-file", ".txt");
		Files.write(tempFile, content);
		return new FileSystemResource(tempFile.toFile());
	}
}
