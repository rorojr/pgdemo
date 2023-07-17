package com.css.demo.model.common;

import lombok.Data;

@Data
public class BaseMessage {

  public static final BaseMessage SUCCESS = new BaseMessage(200, "成功", true);
  public static final BaseMessage QUERY_EMPTY_VALUE = new BaseMessage(201, "没有查询到数据", true);
  public static final BaseMessage BAD_REQUEST = new BaseMessage(400, "请求格式错误", false);
  public static final BaseMessage BAD_REQUEST_PARAM = new BaseMessage(401, "请求参数有误", false);
  public static final BaseMessage REQUEST_PARAM_NULL = new BaseMessage(403, "请求参数为空", false);
  public static final BaseMessage NOT_FOUND = new BaseMessage(404, "未找到", false);
  public static final BaseMessage PARAM_CHECK_ERROR = new BaseMessage(422, "参数校验不通过", false);
  public static final BaseMessage MANY_REQUEST = new BaseMessage(429, "请求过于频繁", false);
  public static final BaseMessage FAIL = new BaseMessage(500, "系统异常", false);
  public static final BaseMessage FORBID = new BaseMessage(301, "禁止访问", false);


  public static final BaseMessage USER_EXISTING = new BaseMessage(600, "已经存在", false);

  private int code;
  private String message;
  private boolean success;

  public BaseMessage(int code, String message, boolean success) {
    this.code = code;
    this.message = message;
    this.success = success;
  }
}
