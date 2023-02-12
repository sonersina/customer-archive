package com.nishcase.customerarchive.service.model.request.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateCustomerRequest {

  @NotEmpty
  @NotNull
  @NotBlank
  private String name;

  @NotEmpty
  @NotNull
  @NotBlank
  private String email;

  @NotEmpty
  @NotNull
  @NotBlank
  private String address;

  @NotEmpty
  @NotNull
  @NotBlank
  private String phone;

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
}
