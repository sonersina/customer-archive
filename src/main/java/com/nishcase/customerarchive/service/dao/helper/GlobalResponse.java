package com.nishcase.customerarchive.service.dao.helper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

public class GlobalResponse<T> {

  private int statusCode;
  private String message;
  private T data;

  public GlobalResponse() {

  }

  @JsonIgnore
  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public GlobalResponse<T> message(String message) {
    this.message = message;
    return this;
  }

  public GlobalResponse<T> success() {
    this.statusCode = HttpStatus.OK.value();
    return this;
  }

  public GlobalResponse<T> success(T data) {
    this.statusCode = HttpStatus.OK.value();
    this.data = data;
    return this;
  }

  public GlobalResponse<T> badRequest() {
    this.statusCode = HttpStatus.BAD_REQUEST.value();
    return this;
  }

}

