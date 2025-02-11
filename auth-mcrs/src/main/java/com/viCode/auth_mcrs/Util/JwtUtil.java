package com.viCode.auth_mcrs.Util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


@Component
public class JwtUtil {

   @Value("${security.jwt.user.generator}")
   private String userGeneratorToken;

   @Value("${security.jwt.key.private}")
   private String keyToken;

   // Creamos el accessToken
   public String createToken(Authentication auth) {

      try {
         Algorithm algorithm = Algorithm.HMAC256(keyToken);

         String username = auth.getPrincipal().toString();
         String authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

         String token = JWT.create()
                           .withIssuer(userGeneratorToken)
                           .withSubject(username)
                           .withClaim("Authorities", authorities)
                           .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
                           .sign(algorithm);
         return token;
      } catch (JWTCreationException exception) {
         throw new JWTCreationException("Error creating accessToken", exception.getCause());
      }

   }


   // realizamos un refresh accessToken
   public String refreshToken(Authentication auth) {

      try {
         Algorithm algorithm = Algorithm.HMAC256(keyToken);

         String username = auth.getPrincipal().toString();
         String authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

         String token = JWT.create()
                           .withIssuer(userGeneratorToken)
                           .withSubject(username)
                           .withClaim("Authorities:", authorities)
                           .withIssuedAt(new Date())
                           .withExpiresAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                           .sign(algorithm);
         return token;
      } catch (JWTCreationException exception) {
         throw new JWTCreationException("Error creating accessToken", exception.getCause());
      }

   }

   //Validamos el accessToken
   public DecodedJWT validateToken(String token) {

      try {
         Algorithm algorithm = Algorithm.HMAC256(keyToken);

         JWTVerifier verifier = JWT.require(algorithm)
                                   .withIssuer(userGeneratorToken)
                                   .build();
         return verifier.verify(token);
      } catch (JWTVerificationException exception) {
         throw new JWTVerificationException("Error verifying accessToken");
      }
   }

}
