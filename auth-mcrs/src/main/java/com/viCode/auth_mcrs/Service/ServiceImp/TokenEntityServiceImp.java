package com.viCode.auth_mcrs.Service.ServiceImp;


import com.viCode.auth_mcrs.Dto.Request.TokenRequest;
import com.viCode.auth_mcrs.Dto.Response.TokenResponse;
import com.viCode.auth_mcrs.Entity.TokenEntity;
import com.viCode.auth_mcrs.Repository.TokenEntityRepository;
import com.viCode.auth_mcrs.Service.ServiceInterface.TokenEntityService;
import com.viCode.auth_mcrs.Util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class TokenEntityServiceImp implements TokenEntityService {


   private HttpServletRequest request;
   private TokenEntityRepository tokenRepository;
   private JwtUtil jwtUtil;

   public TokenEntityServiceImp(HttpServletRequest request, TokenEntityRepository tokenRepository, JwtUtil jwtUtil) {
      this.request = request;
      this.tokenRepository = tokenRepository;
      this.jwtUtil = jwtUtil;
   }

   @Override
   public TokenResponse createRefreshToken(TokenRequest tokenRequest) {

      String ipAddress = request.getHeader("X-Forwarded-For");
      if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
         ipAddress = request.getRemoteAddr();
      }
      String userAgent = request.getHeader("User-Agent");
      userAgent = userAgent != null ? userAgent : "unknown";

      String accessToken = jwtUtil.createToken(tokenRequest.auth());
      String refreshToken = jwtUtil.refreshToken(tokenRequest.auth());

      TokenEntity tokenEntity = TokenEntity.builder()
                                           .refreshToken(refreshToken)
                                           .createdAt(new Date().toInstant())
                                           .expiresAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000).toInstant())
                                           .deviceInfo(userAgent)
                                           .ipAddress(ipAddress)
                                           .user(tokenRequest.userEntity())
                                           .status("ACTIVE")
                                           .build();

      tokenRepository.save(tokenEntity);
      return new TokenResponse( accessToken, tokenEntity.getRefreshToken(), tokenEntity.getDeviceInfo());
   }

   @Override
   public TokenResponse validateRefreshToken(String refreshToken) {

      TokenEntity tokenEntity = tokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new NoSuchElementException("Token not found or invalid"));

      List <SimpleGrantedAuthority> authorityList = tokenEntity.getUser().getRoles().stream().map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getRoleName().name())).toList();

      Authentication authentication = new UsernamePasswordAuthenticationToken(tokenEntity.getUser().getUsername(), null, authorityList);

      String newAccessToken = jwtUtil.createToken(authentication);

      return new TokenResponse( newAccessToken, refreshToken, tokenEntity.getDeviceInfo());
   }
}
