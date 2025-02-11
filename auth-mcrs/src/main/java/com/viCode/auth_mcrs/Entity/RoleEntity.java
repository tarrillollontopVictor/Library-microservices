package com.viCode.auth_mcrs.Entity;


import com.viCode.auth_mcrs.Enum.RoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "role_db")
public class RoleEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idRole;

   @Column(name = "role_name")
   @Enumerated(EnumType.STRING)
   private RoleEnum roleName;


}
