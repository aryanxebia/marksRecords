package com.student.marks.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.student.marks.dao.MarkRepository;
import com.student.marks.entity.Mark;
import com.student.marks.exception.MarkServiceException;
import com.student.marks.service.MarkService;

@Service
public class MarkServiceImpl implements MarkService {

	@Autowired
	private MarkRepository markRepository;

	@Override
	public void saveMarksFromCsv(MultipartFile file) throws MarkServiceException, IOException {
		Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
		HeaderColumnNameTranslateMappingStrategy<Mark> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
		strategy.setType(Mark.class);
		Map<String, String> mapping = new HashMap<>();
		mapping.put("studentId", "studentId");
		mapping.put("marks", "marks");
		strategy.setColumnMapping(mapping);

		CsvToBean<Mark> csvToBean = new CsvToBeanBuilder<Mark>(reader).withMappingStrategy(strategy)
				.withIgnoreLeadingWhiteSpace(true).build();

		List<Mark> marks = csvToBean.parse();
		markRepository.saveAll(marks);
	}

	





}
