package com.example.loan_project.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FileDto implements Serializable {

  private String name;
  private String url;
}
