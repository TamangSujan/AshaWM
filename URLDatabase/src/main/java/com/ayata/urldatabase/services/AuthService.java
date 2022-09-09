package com.ayata.urldatabase.services;

import com.ayata.urldatabase.model.database.Users;
import com.ayata.urldatabase.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private UserRepository userRepo;
    private BCryptPasswordEncoder encoder;
    public String createUser(String phone, String password){
        Users user = getUserByPhone(phone);
        if(user!=null){
            return "user exists";
        }
        Users lastUser = userRepo.lastUser();
        user = new Users();
        user.setChw_id(Integer.parseInt(lastUser.getChw_id())+1+"");
        user.setPhone(phone);
        user.setPassword(encoder.encode(password));
        user.setChw_address("");
        user.setChw_name("");
        user.setImage("");
        user.setChw_dob("");
        user.setChw_gender("");
        user.setChw_designation("");
        user.set__v("0");
        userRepo.save(user);
        return "ok";
    }

    public String deleteUser(String phone){
        userRepo.delete(getUserByPhone(phone));
        return "ok";
    }

    public String updateUser(String chw_id, String chw_address, String chw_name,
                             String chw_dob, String chw_gender, String chw_designation,
                             String path){
        Users user = getUserByChwId(chw_id);
        user.setChw_address(chw_address);
        user.setChw_name(chw_name);
        user.setChw_dob(chw_dob);
        user.setChw_gender(chw_gender);
        user.setChw_designation(chw_designation);
        user.setImage(path);
        userRepo.save(user);
        return "ok";
    }

    public String changePassword(String chw_id, String confirmPassword){
        Users user = getUserByChwId(chw_id);
        user.setPassword(encoder.encode(confirmPassword));
        userRepo.save(user);
        return "ok";
    }

    private Users getUserByPhone(String phone){
        return userRepo.findByPhone(phone);
    }

    private Users getUserByChwId(String chwId){
        return userRepo.findByChwId(chwId);
    }
    private int totalUsers(){
        return userRepo.totalUsers();
    }
}
