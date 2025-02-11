package com.viCode.auth_mcrs.Config;

import com.viCode.auth_mcrs.Util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

   private JwtUtil jwtUtil;

   public SecurityConfig(JwtUtil jwtUtil) {
      this.jwtUtil = jwtUtil;
   }
   @Bean
   public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
      return httpSecurity
              .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()) // Desactivamos cors
              //.httpBasic(Customizer.withDefaults())
              .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Deshabilitamos las sesiones
              .authorizeHttpRequests(http ->{
                        http.requestMatchers(HttpMethod.POST, "/apiUser/auth-login").permitAll();
                        http.requestMatchers(HttpMethod.POST, "/apiUser/refresh").permitAll();
                        http.requestMatchers(HttpMethod.GET, "/apiUser/getName2/{name}").hasRole("DEVELOPER");
                        http.anyRequest().denyAll(); // Deniega todos los endpoints ue no tienen restriccion
              })
              .addFilterBefore(new JwtValidateToken(jwtUtil),  BasicAuthenticationFilter.class)
              .build();
   }


   @Bean
   public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
   }



}
