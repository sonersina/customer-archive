package com.nishcase.customerarchive.service.dao.mapper;

import com.nishcase.customerarchive.service.dao.entity.File;
import com.nishcase.customerarchive.service.model.dto.FileDTO;
import com.nishcase.customerarchive.service.model.reponse.file.FileResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

  public FileDTO entity2dto(File entity) {
    if (entity == null) {
      return null;
    }
    FileDTO dto = new FileDTO();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setCustomerId(entity.getCustomerId());
    dto.setMimeType(entity.getMimeType());
    dto.setPath(entity.getPath());
    return dto;
  }

  public File dto2entity(FileDTO dto) {
    if (dto == null) {
      return null;
    }
    File entity = new File();
    entity.setId(dto.getId());
    entity.setName(dto.getName());
    entity.setCustomerId(dto.getCustomerId());
    entity.setMimeType(dto.getMimeType());
    entity.setPath(dto.getPath());
    return entity;
  }


  public FileResponse dto2response(FileDTO dto) {
    if (dto == null) {
      return null;
    }
    FileResponse response = new FileResponse();
    response.setId(dto.getId());
    response.setName(dto.getName());
    response.setMimeType(dto.getMimeType());
    response.setPath(dto.getPath());
    response.setCustomerId(dto.getCustomerId());
    return response;
  }

  public List<FileDTO> entity2dtoList(List<File> dtoList) {
    if (dtoList == null) {
      return null;
    }
    return dtoList.stream().map(this::entity2dto).collect(Collectors.toList());
  }

  public List<File> dto2entityList(List<FileDTO> dtoList) {
    if (dtoList == null) {
      return null;
    }
    return dtoList.stream().map(this::dto2entity).collect(Collectors.toList());
  }
  public List<FileResponse> dto2responseList(List<FileDTO> dtoList) {
    if (dtoList == null) {
      return null;
    }
    return dtoList.stream().map(this::dto2response).collect(Collectors.toList());
  }
}
