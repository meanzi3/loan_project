package com.example.loan_project.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

  void save(MultipartFile file);
}
