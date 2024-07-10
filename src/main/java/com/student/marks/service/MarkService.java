package com.student.marks.service;


import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.student.marks.exception.MarkServiceException;

public interface MarkService {
    void saveMarksFromCsv(MultipartFile file) throws MarkServiceException, IOException;

}
