package com.nishcase.customerarchive.service.dao.service;

import com.nishcase.customerarchive.service.dao.entity.Customer;
import com.nishcase.customerarchive.service.dao.helper.GlobalResponse;
import com.nishcase.customerarchive.service.dao.mapper.CustomerMapper;
import com.nishcase.customerarchive.service.dao.repository.CustomerRepository;
import com.nishcase.customerarchive.service.model.dto.CustomerDTO;
import com.nishcase.customerarchive.service.model.reponse.customer.CustomerResponse;
import com.nishcase.customerarchive.service.model.request.customer.CreateCustomerRequest;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;
  private final FileService fileService;

  @Autowired
  public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, FileService fileService) {
    this.customerRepository = customerRepository;
    this.customerMapper = customerMapper;
    this.fileService = fileService;
  }

  private Customer findCustomerByID(Long customerId) throws Exception {
    Optional<Customer> customerOptional = customerRepository.findById(customerId);
    if (customerOptional.isEmpty()) {
      throw new Exception("Müşteri bulunamadı!");
    }
    return customerOptional.get();
  }


  private void findCustomerByEmailAndId(String email, Long id) throws Exception {
    boolean hasEmail = customerRepository.existsCustomerByEmailAndIdNot(email, id);
    if (hasEmail) {
      throw new Exception("Bu E-mail İle Kayıtlı Bir Müşteri Mevcut!!");
    }
  }

  private void findCustomerByEmail(String email) throws Exception {
    boolean hasEmail = customerRepository.existsCustomerByEmail(email);
    if (hasEmail) {
      throw new Exception("Bu E-mail İle Kayıtlı Bir Müşteri Mevcut!!");
    }
  }

  public boolean hasCustomer(Long id){
    return customerRepository.existsById(id);
  }

  public GlobalResponse<CustomerResponse> create(CreateCustomerRequest request) {
    try {
      this.findCustomerByEmail(request.getEmail());
      CustomerDTO dto = customerMapper.request2dto(request);
      Customer customer = customerMapper.dto2entity(dto);
      customer = customerRepository.saveAndFlush(customer);
      CustomerResponse response = customerMapper.dto2response(customerMapper.entity2dto(customer));

      return new GlobalResponse<CustomerResponse>().success(response).message("Müşteri Başarıyla Oluşturuldu!");
    } catch (Exception e) {
      return new GlobalResponse<CustomerResponse>().badRequest().message(e.getMessage());
    }
  }

  public GlobalResponse<CustomerResponse> read(Long id) {
    try {
      Customer customer = findCustomerByID(id);
      CustomerResponse response = customerMapper.dto2response(customerMapper.entity2dto(customer));

      return new GlobalResponse<CustomerResponse>().success(response).message("Müşteri Başarıyla Görüntülendi!");
    } catch (Exception e) {
      return new GlobalResponse<CustomerResponse>().badRequest().message(e.getMessage());
    }
  }


  public GlobalResponse<CustomerResponse> update(Long id, CreateCustomerRequest request) {
    try {
      this.findCustomerByEmailAndId(request.getEmail(), id);
      Customer customer = this.findCustomerByID(id);
      customer.setEmail(request.getEmail());
      customer.setPhone(request.getPhone());
      customer.setAddress(request.getAddress());
      customer.setName(request.getName());
      customerRepository.saveAndFlush(customer);

      CustomerResponse response = customerMapper.dto2response(customerMapper.entity2dto(customer));

      return new GlobalResponse<CustomerResponse>().success(response).message("Müşteri Başarıyla Güncellendi!");
    } catch (Exception e) {
      return new GlobalResponse<CustomerResponse>().badRequest().message(e.getMessage());
    }
  }


  public GlobalResponse<CustomerResponse> delete(Long id) {
    try {
      Customer customer = this.findCustomerByID(id);
      customer.setDeletedAt(Date.from(Instant.now()));
      fileService.deleteFilesWithCustomerId(id);
      customerRepository.saveAndFlush(customer);

      return new GlobalResponse<CustomerResponse>().success().message("Müşteri Başarıyla Silindi!");
    } catch (Exception e) {
      return new GlobalResponse<CustomerResponse>().badRequest().message(e.getMessage());
    }
  }


  public GlobalResponse<Page<CustomerResponse>> listCustomers(Pageable pageable) {
    try {
      Page<Customer> customersPage = customerRepository.findAll(pageable);
      Page<CustomerResponse> customerResponses = customersPage.map(customer -> customerMapper.dto2response(customerMapper.entity2dto(customer)));

      return new GlobalResponse<Page<CustomerResponse>>().success(customerResponses).message("Başarıyla Listelendi!");
    } catch (Exception e) {
      return new GlobalResponse<Page<CustomerResponse>>().badRequest().message("Bir Hata Oluştu!");
    }
  }


}
