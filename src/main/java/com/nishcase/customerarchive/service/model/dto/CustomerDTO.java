package com.nishcase.customerarchive.service.model.dto;

import java.util.List;

public class CustomerDTO extends BaseDTO {

  private String name;
  private String email;
  private String address;
  private String phone;
  private List<FileDTO> files;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public List<FileDTO> getFiles() {
    return files;
  }

  public void setFiles(List<FileDTO> files) {
    this.files = files;
  }
}
