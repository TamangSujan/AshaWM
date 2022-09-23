package com.ayata.urldatabase.routes.web;

import com.ayata.urldatabase.model.bridge.*;
import com.ayata.urldatabase.model.database.Doctors;
import com.ayata.urldatabase.model.database.Users;
import com.ayata.urldatabase.model.token.Message;
import com.ayata.urldatabase.repository.ChwRepository;
import com.ayata.urldatabase.repository.DoctorRepository;
import com.ayata.urldatabase.repository.WebChwRepository;
import com.ayata.urldatabase.security.Jwt;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/web/CHW")
public class URLChw {
    private ChwRepository chwRepository;
    private DoctorRepository doctorRepository;
    private WebChwRepository webChwRepository;
    private BCryptPasswordEncoder encoder;
    @GetMapping("/get")
    public ResponseEntity<?> getChwList(@RequestParam int perPage, @RequestParam int currentPage){
        if(currentPage<=0){
            currentPage = 1;
        }
        List<Users> list;
        list = chwRepository.getLimitPageUsers(perPage, (currentPage-1)*perPage);
        if(list.size()>0){
            return ResponseEntity.status(HttpStatus.OK).body(new ChwListResponse(perPage, currentPage, chwRepository.totalUser(), list));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Message("chwList not found in database."));
        }
    }

    @GetMapping("/total")
    public ResponseEntity<?> getTotal(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDetails(200, "Success", "", chwRepository.totalUser()));
    }

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createCHWStaff(HttpServletRequest request, @ModelAttribute WebAddStaffForm form) throws IOException {
        String phone = Jwt.getPhone(request);
        Optional<Doctors> doc = doctorRepository.findDoctorByPhone(phone);
        if(doc.isPresent()){
            Optional<Users> user = chwRepository.getByPhone(form.getPhone());
            if(user.isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDetails(400, "Failure", "Phone already exists!",""));
            }
            if(form.getPhone().equals("") || form.getPassword().equals("")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDetails(400, "Failure", "Fields shouldn't be empty!",""));
            }
            if(!form.getPhone().startsWith("9") || form.getPhone().length()!=10){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDetails(400, "Failure" , "Phone number is invalid!", ""));
            }
            Date date = new Date();
            WebStaff webStaff = WebStaff.getWebStaff(form);

            if(form.getImage()!=null){
                String imagePath = System.getProperty("user.dir")+"/Assets/Image/"+ date +form.getImage().getResource().getFilename();
                createFile(form.getImage(), imagePath);
                webStaff.setImage(imagePath);
            }

            if(form.getFile()!=null){
                String filePath = System.getProperty("user.dir")+"/Assets/File/"+ date +form.getFile().getName();
                createFile(form.getFile(), filePath);
                webStaff.setFile(filePath);
            }

            webStaff.setPassword(encoder.encode(form.getPassword()));

            webChwRepository.save(webStaff);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDetails(200, "Success", "Staff successfully created!", ""));
        }
        throw new IllegalStateException("Invalid Credentials");
    }

    @GetMapping("/details/{chw_id}")
    public ResponseEntity<?> getChw(@PathVariable(value = "chw_id") String chw_id){
        Optional<WebStaff> webStaff = webChwRepository.getByChwId(chw_id);
        if(webStaff.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDetails(200, "Success", "", webStaff.get()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDetails(400, "Failure", "User not found!", ""));
    }


    @PutMapping(value = "/update/{chw_id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateChw(@PathVariable(value = "chw_id") String chw_id, HttpServletRequest request, @ModelAttribute WebAddStaffForm form) throws IOException {
        String phone = Jwt.getPhone(request);
        Optional<Doctors> doc = doctorRepository.findDoctorByPhone(phone);
        if(doc.isPresent()){
            if(form.getPhone().equals("") || form.getPassword().equals("")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDetails(400, "Failure", "Fields shouldn't be empty!",""));
            }
            if(!form.getPhone().startsWith("9") || form.getPhone().length()!=10){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDetails(400, "Failure" , "Phone number is invalid!", ""));
            }
            Date date = new Date();
            WebStaff webStaff = WebStaff.getWebStaff(form);

            if(form.getImage()!=null){
                String imagePath = System.getProperty("user.dir")+"/Assets/Image/"+ date +form.getImage().getResource().getFilename();
                createFile(form.getImage(), imagePath);
                webStaff.setImage(imagePath);
            }

            if(form.getFile()!=null){
                String filePath = System.getProperty("user.dir")+"/Assets/File/"+ date +form.getFile().getName();
                createFile(form.getFile(), filePath);
                webStaff.setFile(filePath);
            }

            webStaff.setPassword(encoder.encode(form.getPassword()));
            Optional<Users> user;
            Optional<WebStaff> webStaffUser;
            user = chwRepository.getByChwId(chw_id);
            if(user.isPresent()){
                webStaff.setChw_identifier(chw_id);
            }else{
                webStaffUser = webChwRepository.getByChwId(chw_id);
                if(webStaffUser.isPresent()){
                    webStaff.setChw_identifier(chw_id);
                }
            }
            webChwRepository.save(webStaff);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDetails(200, "Success", "Staff successfully created!", ""));
        }
        throw new IllegalStateException("Invalid Credentials");
    }

    private void createFile(MultipartFile file, String path) throws IOException {
        Path uploadPath = Paths.get(path);
        Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
    }
    /*
    private void createFile(MultipartFile file, String path) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(file.));
        FileWriter fileWriter = new FileWriter(path);
        String data = "";
        String line = "";
        while ((data = fileReader.readLine())!=null){
            line += data;
        }
        fileWriter.write(line);
        fileReader.close();
        fileWriter.close();
    }*/
}
