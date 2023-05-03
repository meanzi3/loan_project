package com.example.loan_project.controller;

import com.example.loan_project.dto.ApplicationDto;
import com.example.loan_project.dto.ApplicationDto.Response;
import com.example.loan_project.dto.ApplicationDto.Request;
import com.example.loan_project.dto.FileDto;
import com.example.loan_project.dto.ResponseDto;
import com.example.loan_project.service.ApplicationService;
import com.example.loan_project.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applications")
public class ApplicationController extends AbstractController{

  private final ApplicationService applicationService;

  private final FileStorageService fileStorageService;

  @PostMapping
  public ResponseDto<Response> create(@RequestBody Request request){
    return ok(applicationService.create(request));
  }

  @GetMapping("/{applicationId}")
  public ResponseDto<Response> get(@PathVariable Long applicationId){
    return ok(applicationService.get(applicationId));
  }

  @PutMapping("/{applicationId}")
  public ResponseDto<Response> update(@PathVariable Long applicationId, @RequestBody Request request){
    return ok(applicationService.update(applicationId, request));
  }

  @DeleteMapping("/{applicationId}")
  public ResponseDto<Void> delete(@PathVariable Long applicationId){
    applicationService.delete(applicationId);

    return ok();
  }

  @PostMapping("/{applicationId}/terms")
  public ResponseDto<Boolean> acceptTerms(@PathVariable Long applicationId, @RequestBody ApplicationDto.AcceptTerms request){
    return ok(applicationService.acceptTerms(applicationId, request));
  }

  @PostMapping("/files")
  public ResponseDto<Void> upload(MultipartFile file){
    fileStorageService.save(file);
    return ok();
  }

  @GetMapping("/files")
  public ResponseEntity<Resource> download(@RequestParam(value="fileName") String fileName){
    Resource file = fileStorageService.load(fileName);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+file.getFilename()+"\"").body(file);
  }

  @GetMapping("/files/info")
  public ResponseDto<List<FileDto>> getFileInfos(){
    List<FileDto> fileInfos = fileStorageService.loadAll().map(path -> {
      String fileName = path.getFileName().toString();
      return FileDto.builder()
              .name(fileName)
              .url(MvcUriComponentsBuilder.fromMethodName(ApplicationController.class, "download", fileName).build().toString())
              .build();
    }).collect(Collectors.toList());

    return ok(fileInfos);
  }
}
