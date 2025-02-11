package com.viCode.auth_mcrs.Config;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.viCode.auth_mcrs.Util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtValidateToken extends OncePerRequestFilter {

   private JwtUtil jwtUtil;

   public JwtValidateToken(JwtUtil jwtUtil) {
      this.jwtUtil = jwtUtil;
   }

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

      String token = request.getHeader(HttpHeaders.AUTHORIZATION);

      if(token != null){
         token = token.substring(7);

         DecodedJWT decodedJWT = jwtUtil.validateToken(token);

         String username = decodedJWT.getSubject().toString();
         String authorities = decodedJWT.getClaim("Authorities").asString();

         List <GrantedAuthority> authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

         SecurityContext context = SecurityContextHolder.getContext();
         Authentication authToken = new UsernamePasswordAuthenticationToken(username, null, authoritiesList);
         context.setAuthentication(authToken);
         SecurityContextHolder.setContext(context);

      }
      filterChain.doFilter(request, response);
   }
}
