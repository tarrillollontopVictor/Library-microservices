package com.viCode.auth_mcrs.Service.ServiceInterface;


import com.viCode.auth_mcrs.Dto.Request.TokenRequest;
import com.viCode.auth_mcrs.Dto.Response.TokenResponse;

public interface TokenEntityService {

   TokenResponse createRefreshToken(TokenRequest tokenRequest);

   TokenResponse validateRefreshToken(String refreshToken);


}
