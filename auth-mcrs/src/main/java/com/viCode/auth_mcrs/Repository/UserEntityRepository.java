package com.viCode.auth_mcrs.Repository;

import com.viCode.auth_mcrs.Entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository <UserEntity, Integer> {

   Optional <UserEntity> findUserEntityByUsername(String username);


}
