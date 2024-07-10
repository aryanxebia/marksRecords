package com.student.marks.service;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.student.marks.entity.Mark;
import com.student.marks.exception.MarkServiceException;

public interface MarkService {
    void saveMarksFromCsv(MultipartFile file) throws MarkServiceException, IOException;
    List<Mark> getAllMarks();
    Optional<Mark> getMarksByStudentId(String studentId);
}
