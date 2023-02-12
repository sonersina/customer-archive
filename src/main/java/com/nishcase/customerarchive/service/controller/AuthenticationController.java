package com.nishcase.customerarchive.service.controller;

import com.nishcase.customerarchive.service.dao.helper.GlobalResponse;
import com.nishcase.customerarchive.service.dao.service.AuthService;
import com.nishcase.customerarchive.service.model.reponse.auth.LoginResponse;
import com.nishcase.customerarchive.service.model.reponse.auth.RegisterResponse;
import com.nishcase.customerarchive.service.model.request.auth.LoginRequest;
import com.nishcase.customerarchive.service.model.request.auth.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  private final AuthService authService;

  public AuthenticationController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<GlobalResponse<RegisterResponse>> register(@RequestBody RegisterRequest request) {
    GlobalResponse<RegisterResponse> response = authService.register(request);
    return ResponseEntity.status(response.getStatusCode()).body(
        response
    );
  }

  @PostMapping("/login")
  public ResponseEntity<GlobalResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
    GlobalResponse<LoginResponse> response = authService.login(request);
    return ResponseEntity.status(response.getStatusCode()).body(
        response
    );
  }
}
