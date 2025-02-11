package com.viCode.auth_mcrs.Repository;


import com.viCode.auth_mcrs.Entity.TokenEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenEntityRepository extends JpaRepository<TokenEntity, Integer> {

   Optional<TokenEntity> findByRefreshToken(String token);

}
