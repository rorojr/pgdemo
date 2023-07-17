package com.css.demo.model.common;


public class ResultBuilder {

  private ResultBuilder() {
    // ignore
  }

  public static <T> Builder<T> create() {
    return new Builder<>();
  }

  public static <T> IResult<T> build(IResult<?> result) {
    return build(result.isSuccess(), result.getCode(), result.getMsg());
  }

  public static <T> IResult<T> build(boolean success, int code) {
    return build(success, code, null);
  }

  public static <T> IResult<T> build(boolean success, int code, String msg) {
    return build(success, code, msg, null);
  }

  public static <T> IResult<T> build(boolean success, int code, T data) {
    return build(success, code, null, data);
  }

  public static <T> IResult<T> build(boolean success, int code, String msg, T data) {
    return ResultBuilder.<T>create()
        .setSuccess(success)
        .setCode(code)
        .setMsg(msg)
        .setData(data)
        .build();
  }

  public static <T> IResult<T> build(BaseMessage baseMessage) {
    return build(baseMessage, null);
  }

  public static <T> IResult<T> build(BaseMessage baseMessage, T data) {
    return build(baseMessage.isSuccess(), baseMessage.getCode(), baseMessage.getMessage(), data);
  }

  public static class Builder<T> {
    private final IResult<T> result;

    private Builder() {
      result = new IResult<>();
    }

    public Builder<T> setSuccess(boolean success) {
      result.setSuccess(success);
      return this;
    }

    public Builder<T> setCode(int code) {
      result.setCode(code);
      return this;
    }

    public Builder<T> setMsg(String msg) {
      result.setMsg(msg);
      return this;
    }

    public Builder<T> setData(T obj) {
      result.setData(obj);
      return this;
    }

    public IResult<T> build() {
      return result;
    }
  }
}
