package com.viCode.auth_mcrs.Service.ServiceInterface;

import com.viCode.auth_mcrs.Dto.Request.UserCredentialRequest;
import com.viCode.auth_mcrs.Dto.Response.UserCredentialResponse;

public interface UserEntityService {

   public UserCredentialResponse validateUser( UserCredentialRequest userCreateResponse);


}
