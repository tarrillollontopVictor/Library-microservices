package com.viCode.auth_mcrs.Dto.Response;

public record TokenResponse(

        String accessToken,
        String refreshToken,
        String deviceInfo

) {

}
