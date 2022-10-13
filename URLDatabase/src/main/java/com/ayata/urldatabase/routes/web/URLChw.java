package com.ayata.urldatabase.routes.web;

import com.ayata.urldatabase.model.bridge.*;
import com.ayata.urldatabase.model.bridge.Response.CIPResponse;
import com.ayata.urldatabase.model.bridge.Response.ChwListResponse;
import com.ayata.urldatabase.model.bridge.Response.FinalResponse;
import com.ayata.urldatabase.model.database.*;
import com.ayata.urldatabase.model.token.Message;
import com.ayata.urldatabase.repository.*;
import com.ayata.urldatabase.security.Jwt;
import com.ayata.urldatabase.services.PatientService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/web")
public class URLChw {
    private ChwRepository chwRepository;
    private DoctorRepository doctorRepository;
    private WebChwRepository webChwRepository;
    private PatientRepository patientRepository;
    private InfantsRepository infantsRepository;
    private ResidentsRepository residentsRepository;
    private VisitListsRepository visitListsRepository;

    private UserRepository userRepository;
    private PatientService patientService;

    private BCryptPasswordEncoder encoder;
    private static Logger log = LogManager.getLogger(URLChw.class);
    @GetMapping("/CHW/get")
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

    @GetMapping("/CHW/total")
    public ResponseEntity<?> getTotal(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDetails(200, "Success", "", chwRepository.totalUser()));
    }

    @PostMapping(value = "/CHW/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createCHWStaff(HttpServletRequest request, @ModelAttribute WebAddStaffForm form) throws IOException {
        log.info("REQUEST: CHW Create");
        String phone = Jwt.getPhone(request);
        Optional<Doctors> doc = doctorRepository.findDoctorByPhone(phone);
        if(doc.isPresent()){
            log.info("CHECK: Doctor Presented");
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
                log.info("CHECK: Image Creation");
                String imagePath = System.getProperty("user.dir")+"/Assets/Image/"+ date +form.getImage().getResource().getFilename();
                createFile(form.getImage(), imagePath);
                webStaff.setImage(imagePath);
            }

            if(form.getFile()!=null){
                log.info("CHECK: File Creation");
                String filePath = System.getProperty("user.dir")+"/Assets/File/"+ date +form.getFile().getResource().getFilename();
                createFile(form.getFile(), filePath);
                webStaff.setFile(filePath);
            }

            webStaff.setPassword(encoder.encode(form.getPassword()));

            webChwRepository.save(webStaff);
            log.info("CHECK: File Saved");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDetails(200, "Success", "Staff successfully created!", ""));
        }
        throw new IllegalStateException("Invalid Credentials");
    }

    @GetMapping("/CHW/details/{chw_id}")
    public ResponseEntity<?> getChw(@PathVariable(value = "chw_id") Integer chw_id){
        Optional<WebStaff> webStaff = webChwRepository.getByChwId(chw_id);
        if(webStaff.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDetails(200, "Success", "", webStaff.get()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDetails(400, "Failure", "User not found!", ""));
    }


    @PutMapping(value = "/CHW/update/{chw_id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateChw(@PathVariable(value = "chw_id") Integer chw_id, HttpServletRequest request, @ModelAttribute WebAddStaffForm form) throws IOException {
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
                String imagePath = System.getProperty("user.dir")+"/Assets/Image/"+ date  +form.getImage().getResource().getFilename();
                createFile(form.getImage(), imagePath);
                webStaff.setImage(imagePath);
            }

            if(form.getFile()!=null){
                String filePath = System.getProperty("user.dir")+"/Assets/File/"+ date +form.getFile().getResource().getFilename();
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

    @GetMapping("/CHW/patientchart/{chw_id}")
    public ResponseEntity<?> getPatientChart(@PathVariable(value = "chw_id")String chw_id) throws Exception {
        try {
            List<PatientChartList> list = patientRepository.getChart(chw_id);
            List<String> x = new ArrayList<>();
            List<Integer> y = new ArrayList<>();
            for (PatientChartList patientChart : list) {
                x.add(patientChart.get_id());
                y.add(patientChart.getCount());
            }
            PatientChart chart = new PatientChart(list.size(), list, x, y);
            return ResponseEntity.status(HttpStatus.OK).body(chart);
        }catch (Exception e){
            throw new Exception(e.getCause());
        }
    }

    @GetMapping("/CHW/findpatient/{id}")
    public ResponseEntity<?> findPatient(@PathVariable(value = "id") String id, @RequestParam int perPage, @RequestParam int currentPage) throws Exception {
        CIPResponse response = patientService.getPatients(perPage, currentPage, id);
        if(response.getPatients()!=null){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        throw new IllegalStateException("No patients found!");
    }
    @GetMapping("/CHW/findinfant/{id}")
    public ResponseEntity<?> findInfant(@PathVariable(value = "id") String id, @RequestParam int perPage, @RequestParam int currentPage){
        List<Infants> infants = infantsRepository.getLimitInfantByUser(perPage, (currentPage-1)*perPage, id);
        CIPResponse response = new CIPResponse(perPage, currentPage, infantsRepository.getTotalInfant());
        response.setInfants(infants);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/CHW/findcensus/{id}")
    public ResponseEntity<?> findCensus(@PathVariable(value = "id") String id, @RequestParam int perPage, @RequestParam int currentPage){
        List<Residents> residents = residentsRepository.getLimitResidentByUser(perPage, (currentPage-1)*perPage, id);
        CIPResponse response = new CIPResponse(perPage, currentPage, residentsRepository.getTotalResident());
        response.setCensus(residents);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/CHW/find/{id}")
    public ResponseEntity<?> findVisitList(@PathVariable(value = "id")String id){
        VisitLists visitList = visitListsRepository.findByUserId(id);
        if(visitList!=null){
            return ResponseEntity.status(HttpStatus.OK).body(visitList);
        }
        throw new IllegalStateException("Visit List not found in database!");
    }

    @DeleteMapping("/CHW/delete/{chw_id}")
    public ResponseEntity<?> deleteChw(@PathVariable(value = "chw_id")Integer chwId){
        Users user = userRepository.findByChwId(chwId);
        if(user!=null){
            userRepository.delete(user);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "Success", "User deleted succesfully!"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("400", "Failure", "User not found!"));
    }

    @GetMapping("/chw/nameofCHW")
    public ResponseEntity<?> getNamesofCHW(){
        List<String> nameList = userRepository.getCHWNameList();
        FinalResponse response = new FinalResponse("400", "Failure");
        if(nameList!=null){
            response.setStatusCode("200", "Success");
            response.setDetails(nameList);
            return ResponseEntity.status(200).body(response);
        }
        response.setMessage("Data not found!");
        return ResponseEntity.status(400).body(response);
    }
    public void createFile(MultipartFile file, String path) throws IOException {
        File convertFile = new File(path);
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
    }
}
