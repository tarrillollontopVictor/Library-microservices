package com.viCode.auth_mcrs.Dto.Response;

public record UserCredentialResponse(

        String userName,
        TokenResponse tokenResponse
) {

}
