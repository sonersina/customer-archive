package com.nishcase.customerarchive.service.model.request.file;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class UpdateFileRequest {

  @NotNull
  @NotEmpty
  @NotBlank
  private MultipartFile file;

  @NotNull
  @NotEmpty
  @NotBlank
  private String fileId;

  public MultipartFile getFile() {
    return file;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }

  public String getFileId() {
    return fileId;
  }

  public void setFileId(String fileId) {
    this.fileId = fileId;
  }
}
