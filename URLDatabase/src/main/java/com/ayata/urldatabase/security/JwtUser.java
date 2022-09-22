package com.ayata.urldatabase.security;

import com.ayata.urldatabase.model.database.Doctors;
import com.ayata.urldatabase.model.database.Users;
import com.ayata.urldatabase.repository.DoctorRepository;
import com.ayata.urldatabase.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JwtUser implements UserDetailsService {
    private UserRepository userRepository;
    private DoctorRepository doctorRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByPhone(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Optional<Doctors> doctor = null;
        if(user==null){
            doctor = doctorRepository.findDoctorByPhone(username);
            return new User(doctor.get().getPhone(), doctor.get().getPassword(), authorities);
        }
        return new User(user.getPhone(), user.getPassword(), authorities);
    }
}
