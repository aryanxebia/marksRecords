package com.student.marks.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.student.marks.exception.MarkServiceException;
import com.student.marks.service.MarkService;

@RestController
@RequestMapping("/marks")
public class MarkController {

	@Autowired
	private MarkService markService;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadMarksCsv(@RequestParam("file") MultipartFile file)
			throws MarkServiceException, IOException {
		if (file.isEmpty()) {
			throw new MarkServiceException(HttpStatus.BAD_REQUEST, "Please upload a CSV file!");
		}

		markService.saveMarksFromCsv(file);
		return new ResponseEntity<>("File uploaded and marks saved to database!", HttpStatus.OK);
	}

}
