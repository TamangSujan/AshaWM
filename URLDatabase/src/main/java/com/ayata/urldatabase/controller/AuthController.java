package com.ayata.urldatabase.controller;

import com.ayata.urldatabase.model.Users;
import com.ayata.urldatabase.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthController {
    private AuthService authService;
    public String register(String phone, String password){
        if(isEmptyPhone(phone) || isEmptyPassword(password)){
            return "Fields should not be empty!";
        }
        if(!isNepalesePhoneNumber(phone)){
            return "Please provide a valid number!";
        }
        return authService.createUser(phone, password);
    }

    private boolean isEmptyPhone(String phone){
        if(phone.equals("")){
            return true;
        }
        return false;
    }

    private boolean isEmptyPassword(String password){
        if(password.equals("")) {
            return true;
        }
        return false;
    }

    private boolean isNepalesePhoneNumber(String phone){
        if(!phone.startsWith("+977")){
            return false;
        }
        if(phone.length()<14){
            return false;
        }
        return true;
    }

}
