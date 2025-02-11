package com.viCode.auth_mcrs.Controller;

import com.viCode.auth_mcrs.Dto.Request.UserCredentialRequest;
import com.viCode.auth_mcrs.Dto.Response.TokenResponse;
import com.viCode.auth_mcrs.Dto.Response.UserCredentialResponse;
import com.viCode.auth_mcrs.Service.ServiceImp.UserEntityServiceImp;

import com.viCode.auth_mcrs.Service.ServiceInterface.TokenEntityService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiUser")

public class UserController {


   private UserEntityServiceImp userEntityServiceImp;

   private TokenEntityService tokenEntityService;

   public UserController(UserEntityServiceImp userEntityServiceImp, TokenEntityService tokenEntityService) {
      this.userEntityServiceImp = userEntityServiceImp;
      this.tokenEntityService = tokenEntityService;
   }

   @GetMapping("/getName/{name}")
   public String getName(@PathVariable String name) {
      return "Hola " + name;
   }

   @GetMapping("/getName2/{name}")
   public String getName2(@PathVariable String name) {
      return "Hola " + name;
   }

   @PostMapping("/auth-login")
   public ResponseEntity <UserCredentialResponse> login(@RequestBody UserCredentialRequest userRequest) {
      return new ResponseEntity <>(this.userEntityServiceImp.validateUser(userRequest), HttpStatus.OK);
   }

   @PostMapping("/refresh")
   public ResponseEntity <TokenResponse> refreshToken(@RequestParam String token) {
      return new ResponseEntity <>(this.tokenEntityService.validateRefreshToken(token), HttpStatus.OK);
   }

}
