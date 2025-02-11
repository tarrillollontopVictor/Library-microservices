package com.viCode.auth_mcrs.Repository;

import com.viCode.auth_mcrs.Entity.RoleEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleEntityRepository  extends JpaRepository <RoleEntity, Integer>{

   Optional <RoleEntity> findByRoleName(String roleEnum);

}
