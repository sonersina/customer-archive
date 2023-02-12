package com.nishcase.customerarchive.service.dao.mapper;

import com.nishcase.customerarchive.service.dao.entity.Customer;
import com.nishcase.customerarchive.service.model.dto.CustomerDTO;
import com.nishcase.customerarchive.service.model.reponse.customer.CustomerResponse;
import com.nishcase.customerarchive.service.model.request.customer.CreateCustomerRequest;
import org.springframework.stereotype.Component;


@Component
public class CustomerMapper {

  private final FileMapper fileMapper;

  public CustomerMapper(FileMapper fileMapper) {
    this.fileMapper = fileMapper;
  }

  public CustomerDTO entity2dto(Customer entity) {
    if (entity == null) {
      return null;
    }
    CustomerDTO dto = new CustomerDTO();
    dto.setFiles(fileMapper.entity2dtoList(entity.getFiles()));
    dto.setName(entity.getName());
    dto.setPhone(entity.getPhone());
    dto.setAddress(entity.getAddress());
    dto.setEmail(entity.getEmail());
    dto.setId(entity.getId());

    return dto;
  }

  public Customer dto2entity(CustomerDTO dto) {
    if (dto == null) {
      return null;
    }
    Customer entity = new Customer();
    entity.setId(dto.getId());
    entity.setName(dto.getName());
    entity.setAddress(dto.getAddress());
    entity.setPhone(dto.getPhone());
    entity.setEmail(dto.getEmail());
    entity.setFiles(fileMapper.dto2entityList(dto.getFiles()));

    return entity;
  }

  public CustomerDTO request2dto(CreateCustomerRequest request) {
    if (request == null) {
      return null;
    }
    CustomerDTO dto = new CustomerDTO();
    dto.setName(request.getName());
    dto.setAddress(request.getAddress());
    dto.setEmail(request.getEmail());
    dto.setPhone(request.getPhone());
    return dto;
  }

  public CustomerResponse dto2response(CustomerDTO dto) {
    if (dto == null) {
      return null;
    }
    CustomerResponse response = new CustomerResponse();
    response.setId(dto.getId());
    response.setName(dto.getName());
    response.setEmail(dto.getEmail());
    response.setAddress(dto.getAddress());
    response.setPhone(dto.getPhone());
    response.setFileList(fileMapper.dto2responseList(dto.getFiles()));
    return response;
  }
}
