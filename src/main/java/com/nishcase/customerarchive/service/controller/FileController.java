package com.nishcase.customerarchive.service.controller;

import com.nishcase.customerarchive.service.dao.helper.GlobalResponse;
import com.nishcase.customerarchive.service.dao.service.FileService;
import com.nishcase.customerarchive.service.model.reponse.customer.CustomerResponse;
import com.nishcase.customerarchive.service.model.reponse.file.FileResponse;
import com.nishcase.customerarchive.service.model.request.file.UpdateFileRequest;
import com.nishcase.customerarchive.service.model.request.file.UploadFileRequest;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class FileController {

  private final FileService fileService;

  public FileController(FileService fileService) {
    this.fileService = fileService;
  }


  @PostMapping("/upload")
  public ResponseEntity<GlobalResponse<FileResponse>> upload(UploadFileRequest request) {
    GlobalResponse<FileResponse> response = fileService.uploadFile(request);
    return ResponseEntity.status(response.getStatusCode()).body(
        response
    );
  }

  @PutMapping("/update")
  public ResponseEntity<GlobalResponse<FileResponse>> updateFile(UpdateFileRequest request) {
    GlobalResponse<FileResponse> response = fileService.updateFile(request);
    return ResponseEntity.status(response.getStatusCode()).body(
        response
    );
  }

  @DeleteMapping("/delete/{fileId}")
  public ResponseEntity<GlobalResponse<FileResponse>> deletefile(@PathVariable String fileId) {
    GlobalResponse<FileResponse> response = fileService.deleteFile(fileId);
    return ResponseEntity.status(response.getStatusCode()).body(
        response
    );
  }

  @GetMapping("/get-file/{fileId}")
  public ResponseEntity<Resource> getFileById(@PathVariable String fileId) {
    return fileService.getFileById(fileId);
  }

  @GetMapping("/get-file-info/{fileId}")
  public ResponseEntity<GlobalResponse<FileResponse>> getFileInfo(@PathVariable String fileId) {
    GlobalResponse<FileResponse> response = fileService.getFileInfo(fileId);
    return ResponseEntity.status(response.getStatusCode()).body(
        response
    );
  }

  @GetMapping("/list-files")
  public ResponseEntity<GlobalResponse<Page<FileResponse>>> getFilesList(Pageable pageable) {
    GlobalResponse<Page<FileResponse>> response = fileService.getFilesList(pageable);
    return ResponseEntity.status(response.getStatusCode()).body(
        response
    );
  }
}
