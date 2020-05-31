package com.taskagile.web.apis;


import com.taskagile.domain.application.UserService;
import com.taskagile.domain.model.user.EmailAddressExistsException;
import com.taskagile.domain.model.user.RegistrationException;
import com.taskagile.domain.model.user.UsernameExistsException;
import com.taskagile.web.payload.RegistrationPayload;
import com.taskagile.web.result.ApiResult;
import com.taskagile.web.result.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class RegistrationApiController {

    private UserService userService;

    public RegistrationApiController(UserService service) {
        this.userService = service;
    }

    @PostMapping("/api/registrations")
    public ResponseEntity<ApiResult> register(@Valid @RequestBody RegistrationPayload payload){
        try{
            userService.register(payload.toCommand());
            return Result.created();
        }catch (RegistrationException e){
            String errorMessage = "Registration failed";
            if(e instanceof UsernameExistsException){
                errorMessage = "Username already exist";
            }else if( e instanceof EmailAddressExistsException){
                errorMessage = "Email address already exist";
            }
            return Result.failure(errorMessage);
        }
    }
}
