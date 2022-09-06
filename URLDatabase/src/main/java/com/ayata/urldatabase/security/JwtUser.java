package com.ayata.urldatabase.security;

import com.ayata.urldatabase.model.Users;
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

@Service
@AllArgsConstructor
public class JwtUser implements UserDetailsService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByPhone(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new User(user.getPhone(), user.getPassword(), authorities);
    }
}
