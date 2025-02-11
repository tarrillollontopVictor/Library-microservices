package com.viCode.auth_mcrs.Dto.Request;

import com.viCode.auth_mcrs.Entity.UserEntity;
import org.springframework.security.core.Authentication;

public record TokenRequest(

        Authentication auth,
        UserEntity userEntity


) {




}
