package com.api.api_gateway.gatewayAPIs;

import com.api.api_gateway.models.LoginData;
import com.api.api_gateway.models.LoginResponse;
import com.api.api_gateway.models.ResponseObject;
import com.api.api_gateway.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class Authentication
{
    @Autowired
    private AuthServices authServices;

    @PostMapping("/login")
    public ResponseEntity<Object> AuthSignIn(@RequestBody LoginData loginData)
    {
        ResponseObject<LoginResponse> responseObject = authServices.signIn(loginData);
        return new ResponseEntity<>(
                responseObject.getMessageBody(),
                responseObject.getStatus()
        );
    }
}
