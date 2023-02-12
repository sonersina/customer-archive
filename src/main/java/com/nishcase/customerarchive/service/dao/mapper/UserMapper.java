package com.nishcase.customerarchive.service.dao.mapper;

import com.nishcase.customerarchive.service.dao.entity.User;
import com.nishcase.customerarchive.service.model.dto.UserDTO;
import com.nishcase.customerarchive.service.model.request.auth.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserDTO entity2dto(User entity) {
    if (entity == null) {
      return null;
    }
    UserDTO dto = new UserDTO();
    dto.setId(entity.getId());
    dto.setFirstName(entity.getFirstName());
    dto.setLastName(entity.getLastName());
    dto.setUsername(entity.getUsername());
    dto.setEmail(entity.getEmail());
    dto.setPassword(entity.getPassword());
    dto.setRole(entity.getRole());
    return dto;
  }

  public User dto2entity(UserDTO dto) {
    if (dto == null) {
      return null;
    }
    User entity = new User();
    entity.setId(dto.getId());
    entity.setFirstName(dto.getFirstName());
    entity.setLastName(dto.getLastName());
    entity.setUsername(dto.getUsername());
    entity.setEmail(dto.getEmail());
    entity.setPassword(dto.getPassword());
    entity.setRole(dto.getRole());
    return entity;
  }

  public UserDTO registerRequest2dto(RegisterRequest request) {
    if (request == null) {
      return null;
    }
    UserDTO dto = new UserDTO();
    dto.setFirstName(request.getFirstName());
    dto.setLastName(request.getLastName());
    dto.setUsername(request.getUsername());
    dto.setEmail(request.getEmail());
    dto.setPassword(request.getPassword());
    dto.setRole(request.getRole());
    return dto;
  }
}
