package com.viCode.auth_mcrs.Service.ServiceImp;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.viCode.auth_mcrs.Dto.Request.TokenRequest;
import com.viCode.auth_mcrs.Dto.Request.UserCredentialRequest;
import com.viCode.auth_mcrs.Dto.Response.TokenResponse;
import com.viCode.auth_mcrs.Dto.Response.UserCredentialResponse;
import com.viCode.auth_mcrs.Entity.UserEntity;
import com.viCode.auth_mcrs.Repository.RoleEntityRepository;
import com.viCode.auth_mcrs.Repository.UserEntityRepository;
import com.viCode.auth_mcrs.Service.ServiceInterface.TokenEntityService;
import com.viCode.auth_mcrs.Service.ServiceInterface.UserEntityService;
import com.viCode.auth_mcrs.Util.JwtUtil;
import java.util.List;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserEntityServiceImp implements UserEntityService {


   private PasswordEncoder passwordEncoder;

   private JwtUtil jwtUtil;

   private UserEntityRepository userEntityRepository;

   private RoleEntityRepository roleEntityRepository;

   private TokenEntityService tokenEntityService;

   public UserEntityServiceImp(PasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserEntityRepository userEntityRepository, RoleEntityRepository roleEntityRepository, TokenEntityService tokenEntityService) {
      this.passwordEncoder = passwordEncoder;
      this.jwtUtil = jwtUtil;
      this.userEntityRepository = userEntityRepository;
      this.roleEntityRepository = roleEntityRepository;
      this.tokenEntityService = tokenEntityService;
   }

   @Override
   public UserCredentialResponse validateUser(UserCredentialRequest userCreateResponse) {

      UserEntity userEntity = userEntityRepository
              .findUserEntityByUsername(userCreateResponse.userName())
              .orElseThrow(() -> new UsernameNotFoundException("The username is invalid"));

      List <SimpleGrantedAuthority> listAuth = userEntity.getRoles().stream().map(roleEntity -> new SimpleGrantedAuthority("ROLE_".concat(roleEntity.getRoleName().name()))).toList();

      if(!passwordEncoder.matches(userCreateResponse.password(), userEntity.getPassword())){
         throw new BadCredentialsException("Password incorrect");
      }

      Authentication auth = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), null,  listAuth);

      TokenResponse tokenResponse = tokenEntityService.createRefreshToken(new TokenRequest(auth, userEntity));

      return new UserCredentialResponse(userEntity.getUsername(), tokenResponse);
   }




}
