package com.nishcase.customerarchive.service.controller;

import com.nishcase.customerarchive.service.dao.helper.GlobalResponse;
import com.nishcase.customerarchive.service.dao.service.CustomerService;
import com.nishcase.customerarchive.service.model.reponse.customer.CustomerResponse;
import com.nishcase.customerarchive.service.model.request.customer.CreateCustomerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }


  @PostMapping("/create")
  public ResponseEntity<GlobalResponse<CustomerResponse>> create(@Validated @RequestBody CreateCustomerRequest request) {
    GlobalResponse<CustomerResponse> response = customerService.create(request);
    return ResponseEntity.status(response.getStatusCode()).body(
        response
    );
  }

  @GetMapping("read/{id}")
  public ResponseEntity<GlobalResponse<CustomerResponse>> readCustomer(@PathVariable("id") Long id) {
    GlobalResponse<CustomerResponse> response = customerService.read(id);
    return ResponseEntity.status(response.getStatusCode()).body(
        response
    );
  }

  @PutMapping("update/{id}")
  public ResponseEntity<GlobalResponse<CustomerResponse>> update(@PathVariable Long id, @Validated @RequestBody CreateCustomerRequest request) {
    GlobalResponse<CustomerResponse> response = customerService.update(id, request);
    return ResponseEntity.status(response.getStatusCode()).body(
        response
    );
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<GlobalResponse<CustomerResponse>> deleteCustomer(@PathVariable Long id) {
    GlobalResponse<CustomerResponse> response = customerService.delete(id);
    return ResponseEntity.status(response.getStatusCode()).body(
        response
    );
  }

  @GetMapping("/list")
  public ResponseEntity<GlobalResponse<Page<CustomerResponse>>> listCustomers(Pageable pageable) {
    GlobalResponse<Page<CustomerResponse>> response = customerService.listCustomers(pageable);
    return ResponseEntity.status(response.getStatusCode()).body(
        response
    );
  }


}
