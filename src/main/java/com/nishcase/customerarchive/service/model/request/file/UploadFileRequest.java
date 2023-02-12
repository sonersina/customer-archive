package com.nishcase.customerarchive.service.model.request.file;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class UploadFileRequest {

  @NotNull
  @NotEmpty
  @NotBlank
  private MultipartFile file;

  @NotNull
  @NotEmpty
  @NotBlank
  private Long customerId;

  public MultipartFile getFile() {
    return file;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }
}