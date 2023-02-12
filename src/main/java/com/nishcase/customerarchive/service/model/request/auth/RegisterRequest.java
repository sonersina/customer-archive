package com.nishcase.customerarchive.service.model.request.auth;

import com.nishcase.customerarchive.service.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RegisterRequest {

  @NotEmpty
  @NotNull
  @NotBlank
  private String firstName;

  @NotEmpty
  @NotNull
  @NotBlank
  private String lastName;

  @NotEmpty
  @NotNull
  @NotBlank
  private String username;

  @NotEmpty
  @NotNull
  @NotBlank
  private String email;

  @NotEmpty
  @NotNull
  @NotBlank
  private String password;

  private Role role;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
