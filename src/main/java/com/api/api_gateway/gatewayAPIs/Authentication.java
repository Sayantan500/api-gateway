package com.api.api_gateway.gatewayAPIs;

import com.api.api_gateway.models.LoginData;
import com.api.api_gateway.models.LoginResponse;
import com.api.api_gateway.models.ResponseObject;
import com.api.api_gateway.models.User;
import com.api.api_gateway.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/signup")
    public ResponseEntity<String> AuthSignUp(@RequestBody User user)
    {
        return authServices.signUp(user);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(CustomError customError)
    {
        return new ResponseEntity<>(customError.getMessage(), customError.getStatus());
    }

    @PostMapping("/verify")
    public ResponseEntity<String> AuthVerifyOtp(
            @RequestHeader(name = "verification_code")
            String verification_code,

            @CookieValue("session_id")
            String sessionID
    )
    {
        return authServices.verifyOtp(verification_code, sessionID);
    }
}
