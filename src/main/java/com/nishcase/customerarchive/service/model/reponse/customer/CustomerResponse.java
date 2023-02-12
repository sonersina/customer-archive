package com.nishcase.customerarchive.service.model.reponse.customer;

import com.nishcase.customerarchive.service.model.dto.FileDTO;
import com.nishcase.customerarchive.service.model.reponse.file.FileResponse;
import java.util.List;

public class CustomerResponse {

  private Long id;
  private String name;
  private String email;
  private String address;
  private String phone;
  private List<FileResponse> fileList;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public List<FileResponse> getFileList() {
    return fileList;
  }

  public void setFileList(List<FileResponse> fileList) {
    this.fileList = fileList;
  }
}
