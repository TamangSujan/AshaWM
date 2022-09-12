package com.ayata.urldatabase.routes.mobile;

import com.ayata.urldatabase.controller.AuthController;
import com.ayata.urldatabase.model.bridge.ForgotPassword;
import com.ayata.urldatabase.model.bridge.ResponseDetailsV2;
import com.ayata.urldatabase.model.bridge.ResponseMessage;
import com.ayata.urldatabase.model.database.Users;
import com.ayata.urldatabase.model.token.UsernamePassword;
import com.ayata.urldatabase.model.token.UsernameToken;
import com.ayata.urldatabase.repository.UserRepository;
import com.ayata.urldatabase.security.Jwt;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@RestController
@RequestMapping("/api/v2/mobile")
@AllArgsConstructor
public class MobileURLAuth {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private AuthController authController;

    @PostMapping(value = "/loginUser", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity loginUser(UsernamePassword usernamePassword){
        if(usernamePassword.getPhone().equals("") || usernamePassword.getPassword().equals("")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("400", "Failure", "Field Should Not Be Empty"));
        }
        Users dbUser = userRepository.findByPhone(usernamePassword.getPhone());
        if(dbUser==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("400", "Failure", "User Not Found"));
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernamePassword.getPhone(), usernamePassword.getPassword()));
        String access_token = Jwt.getAccessToken(usernamePassword.getPhone(), 60*8, "/api/loginUser");
        ResponseDetailsV2 response = new ResponseDetailsV2("200", "success", new UsernameToken(dbUser.getChw_id(), dbUser.getChw_name(), dbUser.getChw_gender(), dbUser.getChw_dob(), dbUser.getChw_address(), dbUser.getChw_designation(), dbUser.getImage(), access_token));
        return ResponseEntity.status(HttpStatus.OK).body(response);
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

    @PostMapping(value = "/updateProfile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateUser(HttpServletRequest request, @RequestParam("image") MultipartFile image) throws IOException, ServletException {
        System.out.println(image.getResource().getFilename());
        Users user = userRepository.findByChwId(Integer.parseInt(request.getParameter("chw_id")));
        user.setChw_name(request.getParameter("chw_name"));
        user.setChw_address(request.getParameter("chw_address"));
        user.setChw_dob(request.getParameter("chw_dob"));
        user.setChw_designation(request.getParameter("chw_designation"));
        user.setChw_gender(request.getParameter("chw_gender"));
        String filepath = System.getProperty("user.dir")+"/Assets/Image/"+image.getResource().getFilename();
        createFile(image, filepath);
        user.setImage("http://192.168.1.83:8082"+filepath);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "success", "Updated!"));
    }

    private void createFile(MultipartFile file, String path) throws IOException {
        Path uploadPath = Paths.get(path);
        Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
