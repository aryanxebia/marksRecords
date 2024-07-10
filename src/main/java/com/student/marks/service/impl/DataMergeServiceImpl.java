package com.student.marks.service.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.opencsv.CSVWriter;
import com.student.marks.entity.Mark;
import com.student.marks.request.StudentDto;
import com.student.marks.service.DataMergeService;
import com.student.marks.service.MarkService;

@Service
public class DataMergeServiceImpl implements DataMergeService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MarkService markService;

	@Override
	public String getMergedDataCsv() {
		// Fetch student records from Records Microservice
		String recordsServiceUrl = "http://localhost:8080/api/v1/";
		ResponseEntity<StudentDto[]> responseEntity = restTemplate.getForEntity(recordsServiceUrl, StudentDto[].class);
		StudentDto[] studentsArray = responseEntity.getBody();
		List<StudentDto> students = studentsArray != null ? List.of(studentsArray) : new ArrayList<>();
//		System.out.println("Data from microservice records: " + students.size());
		// Fetch marks data from Marks Microservice database
		List<Mark> marks = markService.getAllMarks();

		// Convert marks to a map for easier merging
		Map<String, Mark> marksMap = marks.stream().collect(Collectors.toMap(Mark::getStudentId, mark -> mark));

		// Prepare CSV data
		List<String[]> csvData = new ArrayList<>();
		csvData.add(new String[] { "studentId", "name", "department", "address", "phoneNumber", "marks" });

		for (StudentDto student : students) {
			Mark mark = marksMap.get(student.getStudentId());
			if (mark != null) {
				csvData.add(new String[] { student.getStudentId(), student.getName(), student.getDepartment(),
						student.getAddress(), student.getPhoneNumber(), String.valueOf(mark.getMarks()) });
			}
		}

		// Convert to CSV format
		StringWriter stringWriter = new StringWriter();
		try (CSVWriter csvWriter = new CSVWriter(stringWriter)) {
			csvWriter.writeAll(csvData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return stringWriter.toString();
	}
}
