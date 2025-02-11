package com.viCode.auth_mcrs.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token_db")
public class TokenEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column(name = "refresh_token")
   private String refreshToken;

   @Column(name = "ip_address")
   private String ipAddress;

   @Column(name = "device_info")
   private String deviceInfo;

   @Column(name = "create_at")
   private Instant createdAt;

   @Column(name = "expires_at")
   private Instant expiresAt;

   @ManyToOne
   @JoinColumn(name = "user_id", nullable = false)
   private UserEntity user;

   private String status;



}
