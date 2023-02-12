package com.nishcase.customerarchive.service.dao.service;

import com.nishcase.customerarchive.service.dao.entity.File;
import com.nishcase.customerarchive.service.dao.helper.GlobalResponse;
import com.nishcase.customerarchive.service.dao.mapper.FileMapper;
import com.nishcase.customerarchive.service.dao.repository.FileRepository;
import com.nishcase.customerarchive.service.model.reponse.file.FileResponse;
import com.nishcase.customerarchive.service.model.request.file.UpdateFileRequest;
import com.nishcase.customerarchive.service.model.request.file.UploadFileRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  private final FileRepository fileRepository;
  private final FileMapper fileMapper;
  private final CustomerService customerService;

  private final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2 MB
  private final String FILE_PATH = "store/";

  @Autowired
  @Lazy
  public FileService(FileRepository fileRepository, FileMapper fileMapper, CustomerService customerService) throws IOException {
    this.fileRepository = fileRepository;
    this.fileMapper = fileMapper;
    this.customerService = customerService;
    Path path = Paths.get(FILE_PATH);
    if (!path.toFile().exists()) {
      Files.createDirectory(Path.of(FILE_PATH));
    }
  }

  private File findFileById(String fileId) throws Exception {
    Optional<File> fileOptional = fileRepository.findById(fileId);
    if (fileOptional.isEmpty()) {
      throw new Exception("Dosya bulunamadı!");
    }
    return fileOptional.get();
  }

  private String createFileName(String originalFileName) {
    String uuid = UUID.randomUUID().toString();
    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
    return uuid + fileExtension;
  }

  private File createFile(MultipartFile file, Long customerId) throws IOException {

    String fileName = this.createFileName(file.getOriginalFilename());
    String filePath = FILE_PATH + fileName;
    String mimeType = file.getContentType();

    Path path = Paths.get(filePath);
    Files.write(path, file.getBytes());

    File createdFile = new File();
    createdFile.setName(fileName);
    createdFile.setPath(filePath);
    createdFile.setMimeType(mimeType);
    createdFile.setCustomerId(customerId);

    return createdFile;
  }

  public void deleteFilesWithCustomerId(Long id) {
    fileRepository.deleteByCustomerId(id);
  }

  public GlobalResponse<FileResponse> uploadFile(UploadFileRequest request) {
    try {
      MultipartFile file = request.getFile();
      Long customerId = request.getCustomerId();

      if (file.getSize() > MAX_FILE_SIZE) {
        throw new IllegalArgumentException("Dosya " + MAX_FILE_SIZE / 1024 / 1024 + "MB" + " boyutundan küçük olmalıdır!");
      }

      if (!Arrays.asList("image/jpeg", "application/pdf", "image/png").contains(file.getContentType())) {
        throw new IllegalArgumentException("Geçersiz dosya tipi. JPG/PDF/PNG tipindeki dosyalar kabul edilmektedir.");
      }

      boolean existsCustomer = customerService.hasCustomer(customerId);
      if (!existsCustomer) {
        throw new Exception("Müşteri Bulunamadı !!");
      }

      File uploadedFile = this.createFile(file, customerId);
      uploadedFile = fileRepository.saveAndFlush(uploadedFile);

      FileResponse response = fileMapper.dto2response(fileMapper.entity2dto(uploadedFile));

      return new GlobalResponse<FileResponse>().success(response).message("Dosya Başarıyla Oluşturuldu!");
    } catch (Exception e) {
      return new GlobalResponse<FileResponse>().badRequest().message(e.getMessage());
    }
  }

  public GlobalResponse<FileResponse> updateFile(UpdateFileRequest request) {
    try {
      MultipartFile file = request.getFile();
      File oldFile = this.findFileById(request.getFileId());

      if (file.getSize() > MAX_FILE_SIZE) {
        throw new IllegalArgumentException("Dosya " + MAX_FILE_SIZE / 1024 / 1024 + "MB" + " boyutundan küçük olmalıdır!");
      }

      if (!Arrays.asList("image/jpeg", "application/pdf", "image/png").contains(file.getContentType())) {
        throw new IllegalArgumentException("Geçersiz dosya tipi. JPG/PDF/PNG tipindeki dosyalar kabul edilmektedir.");
      }

      String fileName = this.createFileName(file.getOriginalFilename());
      String filePath = FILE_PATH + fileName;
      String mimeType = file.getContentType();

      Path path = Paths.get(filePath);
      Files.write(path, file.getBytes());

      Files.deleteIfExists(Paths.get(oldFile.getPath()));

      oldFile.setName(fileName);
      oldFile.setPath(filePath);
      oldFile.setMimeType(mimeType);

      oldFile = fileRepository.saveAndFlush(oldFile);

      FileResponse response = fileMapper.dto2response(fileMapper.entity2dto(oldFile));

      return new GlobalResponse<FileResponse>().success(response).message("Dosya Başarıyla Güncellendi!");
    } catch (Exception e) {
      return new GlobalResponse<FileResponse>().badRequest().message(e.getMessage());
    }
  }

  public GlobalResponse<FileResponse> deleteFile(String fileId) {
    try {
      File file = this.findFileById(fileId);
      file.setDeletedAt(Date.from(Instant.now()));
      fileRepository.saveAndFlush(file);

      Files.deleteIfExists(Paths.get(file.getPath()));

      return new GlobalResponse<FileResponse>().success().message("Dosya Başarıyla Silindi!");
    } catch (Exception e) {
      return new GlobalResponse<FileResponse>().badRequest().message(e.getMessage());
    }
  }

  public ResponseEntity<Resource> getFileById(String fileId) {
    try {
      File file = this.findFileById(fileId);
      Path path = Paths.get(file.getPath());
      ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

      return ResponseEntity.ok()
          .contentType(MediaType.parseMediaType(file.getMimeType()))
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
          .body(resource);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }


  public GlobalResponse<FileResponse> getFileInfo(String fileId) {
    try {
      File file = this.findFileById(fileId);
      FileResponse response = fileMapper.dto2response(fileMapper.entity2dto(file));

      return new GlobalResponse<FileResponse>().success(response).message("Dosya Bilgileri Başarıyla Görüntülendi!");
    } catch (Exception e) {
      return new GlobalResponse<FileResponse>().badRequest().message(e.getMessage());
    }
  }

  public GlobalResponse<Page<FileResponse>> getFilesList(Pageable pageable) {
    try {
      Page<File> files = fileRepository.findAll(pageable);
      Page<FileResponse> fileResponses = files.map(customer -> fileMapper.dto2response(fileMapper.entity2dto(customer)));

      return new GlobalResponse<Page<FileResponse>>().success(fileResponses).message("Başarıyla Listelendi!");
    } catch (Exception e) {
      return new GlobalResponse<Page<FileResponse>>().badRequest().message("Bir Hata Oluştu!");
    }
  }

}
