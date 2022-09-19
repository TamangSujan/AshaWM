package com.ayata.urldatabase.controller;

import com.ayata.urldatabase.model.bridge.UpdateProfile;
import com.ayata.urldatabase.model.database.Users;
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

    public String forgotPassword(String phone, String password, String confirmPassword){
        if(isEmptyPhone(phone) || isEmptyPassword(password)){
            return "Fields should not be empty!";
        }
        if(!isNepalesePhoneNumber(phone)){
            return "Please provide a valid number!";
        }
        if(password.equals(confirmPassword)){
            return authService.changePassword(Integer.parseInt(phone), confirmPassword);
        }
        return "Error";
    }

    public void updateProfile(UpdateProfile profile, Users user){
        user.setChw_address(profile.getChw_address());
        user.setChw_name(profile.getChw_name());
        user.setChw_dob(profile.getChw_dob());
        user.setChw_gender(profile.getChw_gender());
        user.setChw_designation(profile.getChw_designation());
        user.setImage(profile.getImage());
        authService.updateUser(user);
    }

    public void removeUser(Users user){
        authService.deleteUser(user);
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
        if(!phone.startsWith("9")){
            return false;
        }
        if(phone.length()!=10){
            return false;
        }
        return true;
    }

}
