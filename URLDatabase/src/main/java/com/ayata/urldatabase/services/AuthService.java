package com.ayata.urldatabase.services;

import com.ayata.urldatabase.model.database.Users;
import com.ayata.urldatabase.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    @Autowired    private UserRepository userRepo;
    private BCryptPasswordEncoder encoder;
    public String createUser(String phone, String password){
        Users user = getUserByPhone(phone);
        if(user!=null){
            return "user exists";
        }
        Users lastUser = userRepo.lastUser();
        user = new Users();
        user.setChw_id(lastUser.getChw_id()+1);
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

    public void deleteUser(Users user){
        // TODO: Delete Image by name
        userRepo.delete(user);
    }

    public void updateUser(Users user){
        userRepo.save(user);
    }

    public String changePassword(Integer chw_id, String confirmPassword){
        Users user = getUserByChwId(chw_id);
        user.setPassword(encoder.encode(confirmPassword));
        userRepo.save(user);
        return "ok";
    }

    private Users getUserByPhone(String phone){
        return userRepo.findByPhone(phone);
    }

    private Users getUserByChwId(Integer chwId){
        return userRepo.findByChwId(chwId);
    }
}
