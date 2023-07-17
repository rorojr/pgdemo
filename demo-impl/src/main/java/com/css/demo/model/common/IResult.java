package com.css.demo.model.common;

import lombok.Data;

@Data
public class IResult<T> {
  public IResult() {}
  public Boolean isSuccess() {
    return success;
  }

  private Boolean success;
  private int code;
  private String msg;
  private T data;
}
