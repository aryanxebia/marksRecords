package com.student.marks.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.student.marks.exception.MarkServiceException;
import com.student.marks.service.DataMergeService;
import com.student.marks.service.MarkService;
import com.student.marks.util.ConstantUtils;

@RestController
@RequestMapping("/api/v1/")
public class MarkController {

	@Autowired
	private MarkService markService;
	@Autowired
	private DataMergeService dataMergeService;

	@PostMapping("upload")
	public ResponseEntity<String> uploadMarksCsv(@RequestParam("file") MultipartFile file)
			throws MarkServiceException, IOException {
		if (file.isEmpty()) {
			throw new MarkServiceException(HttpStatus.BAD_REQUEST, "Please upload a CSV file!");
		}

		markService.saveMarksFromCsv(file);
		return new ResponseEntity<>(ConstantUtils.FILE_UPLOAD_SUCCESSFUL, HttpStatus.OK);
	}

	@GetMapping("merged-data/csv")
	public ResponseEntity<byte[]> getMergedDataCsv() {
		String csvData = dataMergeService.getMergedDataCsv();

		byte[] output = csvData.getBytes();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "merged_data.csv");

		return new ResponseEntity<>(output, headers, HttpStatus.OK);
	}

}
