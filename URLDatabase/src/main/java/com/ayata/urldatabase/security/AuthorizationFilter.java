package com.ayata.urldatabase.security;

import com.ayata.urldatabase.path.AshaPath;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        if(path.equals("/api/loginUser") || path.equals("/api/addUser")
                || path.equals("/api/v2/mobile/loginUser") || path.startsWith("/home/sujan/Documents/Java/AshaWM/Assets/Image")){
            filterChain.doFilter(request, response);
        }else{
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(header!=null & header.startsWith("Basic Bearer ")){
                DecodedJWT extractedToken = Jwt.extractToken(header.substring(13));
                Jwt.checkPhone(extractedToken.getSubject());
                filterChain.doFilter(request, response);
            }else{
                filterChain.doFilter(request, response);
            }
        }
    }
}
