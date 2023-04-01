package com.example.loan_project.dto;

import java.io.Serializable;

import com.example.loan_project.exception.BaseException;
import com.example.loan_project.exception.ResultType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultObject implements Serializable {

  public String code;

  public String desc;

  public ResultObject(ResultType resultType) {
    this.code = resultType.getCode();
    this.desc = resultType.getDesc();
  }

  public ResultObject(ResultType resultCode, String message) {
    this.code = resultCode.getCode();
    this.desc = message;
  }

  public ResultObject(BaseException e) {
    this.code = e.getCode();
    this.desc = e.getDesc();
  }

  public static ResultObject getSuccess() {
    return new ResultObject(ResultType.SUCCESS);
  }
}
