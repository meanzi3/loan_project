package com.example.loan_project.service;

import com.example.loan_project.exception.BaseException;
import com.example.loan_project.exception.ResultType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService{

  @Value("${spring.servlet.multipart.location}")
  private String uploadPath;

  @Override
  public void save(MultipartFile file) {
    try{
      Files.copy(file.getInputStream(), Paths.get(uploadPath).resolve(file.getOriginalFilename()),
              StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e){
      throw new BaseException(ResultType.SYSTEM_ERROR);
    }
  }
}
