package com.nishcase.customerarchive.service.dao.service;

import com.nishcase.customerarchive.service.dao.entity.User;
import com.nishcase.customerarchive.service.dao.helper.GlobalResponse;
import com.nishcase.customerarchive.service.dao.mapper.UserMapper;
import com.nishcase.customerarchive.service.dao.repository.UserRepository;
import com.nishcase.customerarchive.service.model.dto.UserDTO;
import com.nishcase.customerarchive.service.model.enums.Role;
import com.nishcase.customerarchive.service.model.reponse.auth.LoginResponse;
import com.nishcase.customerarchive.service.model.reponse.auth.RegisterResponse;
import com.nishcase.customerarchive.service.model.request.auth.LoginRequest;
import com.nishcase.customerarchive.service.model.request.auth.RegisterRequest;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserMapper userMapper;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthService(AuthenticationManager authenticationManager, UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder,
      JwtService jwtService) {
    this.authenticationManager = authenticationManager;
    this.userMapper = userMapper;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  private void checkUsernameAndPassword(RegisterRequest request) throws Exception {
    boolean existsUser = userRepository.existsByUsernameOrEmail(request.getUsername(), request.getEmail());
    if (existsUser) {
      throw new Exception("Kullanıcı Adı Veya E-mail daha önceden alınmış.");
    }
  }

  public GlobalResponse<RegisterResponse> register(RegisterRequest request) {

    try {
      this.checkUsernameAndPassword(request);
      request.setPassword(passwordEncoder.encode(request.getPassword()));
      request.setRole(Role.ADMIN);
      UserDTO userDTO = userMapper.registerRequest2dto(request);
      User entity = userMapper.dto2entity(userDTO);
      userRepository.saveAndFlush(entity);

      RegisterResponse response = new RegisterResponse();
      response.setEmail(userDTO.getEmail());
      response.setUsername(userDTO.getUsername());
      return new GlobalResponse<RegisterResponse>().success(response).message("Basariyla Kayıt Olundu!");
    } catch (Exception e) {
      return new GlobalResponse<RegisterResponse>().badRequest().message(e.getMessage());
    }
  }

  public GlobalResponse<LoginResponse> login(LoginRequest request) {

    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getUsername(),
              request.getPassword()
          )
      );
      if (authentication == null){
        throw  new Exception("Kullanıcı Bulunamadı");
      }
      if ( authentication.getPrincipal() == null ){
        throw  new Exception("Kullanıcı Bulunamadı");
      }
      if (authentication.getPrincipal() instanceof User user){
        String token = jwtService.generateToken(user);

        LoginResponse response = new LoginResponse();
        response.setUsername(request.getUsername());
        response.setToken(token);

        return new GlobalResponse<LoginResponse>().success(response).message("Basariyla Giris Yapıldı!");
      }
      throw new Exception("Bir Hata Oluştu !");

    } catch (Exception e) {
      return new GlobalResponse<LoginResponse>().badRequest().message(e.getMessage());
    }

  }

}
