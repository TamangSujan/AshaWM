package com.ayata.urldatabase.routes.mobile;

import com.ayata.urldatabase.controller.AuthController;
import com.ayata.urldatabase.model.bridge.ForgotPassword;
import com.ayata.urldatabase.model.bridge.ResponseDetails;
import com.ayata.urldatabase.model.bridge.ResponseMessage;
import com.ayata.urldatabase.model.bridge.UpdateProfileForm;
import com.ayata.urldatabase.model.database.Users;
import com.ayata.urldatabase.model.token.UsernamePassword;
import com.ayata.urldatabase.model.token.UsernameToken;
import com.ayata.urldatabase.repository.UserRepository;
import com.ayata.urldatabase.security.Jwt;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/v2/mobile")
@AllArgsConstructor
public class MobileURLAuth {
    private AuthenticationManager authenticationManager;
    private UserRepository   userRepository;
    private AuthController authController;
    private BCryptPasswordEncoder encoder;

    private static Logger log = LogManager.getLogger(MobileURLAuth.class);
    @PostMapping(value = "/loginUser")
    public ResponseEntity loginUser(@RequestBody UsernamePassword usernamePassword){
        log.debug("User entered!");
        if(usernamePassword.getPhone().equals("") || usernamePassword.getPassword().equals("")){
            log.error("Empty phone or password!");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("400", "Failure", "Field Should Not Be Empty"));
        }
        Users dbUser = userRepository.findByPhone(usernamePassword.getPhone());
        if(dbUser==null){
            log.error("User not found!");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("400", "Failure", "User Not Found"));
        }
        if(encoder.matches(usernamePassword.getPassword(), dbUser.getPassword())){
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernamePassword.getPhone(), usernamePassword.getPassword()));
            String access_token = Jwt.getAccessToken(usernamePassword.getPhone(), 60*8, "/api/v2/mobile/loginUser", false);
            ResponseDetails response = new ResponseDetails(200, "success", "", new UsernameToken(dbUser.getChw_id(), dbUser.getChw_name(), dbUser.getChw_gender(), dbUser.getChw_dob(), dbUser.getChw_address(), dbUser.getChw_designation(), dbUser.getImage(), access_token));
            log.info("User found with correct credentials!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            log.error("User sent bad credentials");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDetails(400, "Failure", "Invalid Credentials", null));
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UsernamePassword usernamePassword){
        String message = authController.register(usernamePassword.getPhone(), usernamePassword.getPassword());
        if(message.equals("ok")){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "Success", "User Created"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("400", "Failure", message));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPassword user){
        String message = authController.forgotPassword(user.getUsername(), user.getPassword(), user.getConfirmPassword());
        if(!message.equals("ok")){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "Success", "Password Changed!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("400", "Failure", message));
    }

    /*
        JSON and XML = @RequestBody
        Multipart Form = @ModelAttribute
     */
    @PostMapping(value = "/updateProfile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateUser(@ModelAttribute("image") UpdateProfileForm form, HttpServletRequest request) throws IOException, ServletException {
        String chw_id = form.getChw_id();
        Users user = userRepository.findByChwId(Integer.parseInt(chw_id));
        user.setChw_name(form.getChw_name());
        user.setChw_address(form.getChw_address());
        user.setChw_dob(form.getChw_dob());
        user.setChw_designation(form.getChw_designation());
        user.setChw_gender(form.getChw_gender());
        String filepath = System.getProperty("user.dir")+"/Assets/Image/"+form.getImage().getResource().getFilename();
        createFile(form.getImage(), filepath);
        user.setImage("http://192.168.1.83:8082"+filepath);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "success", "Updated!"));
    }

    private void createFile(MultipartFile file, String path) throws IOException {
        Path uploadPath = Paths.get(path);
        Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
