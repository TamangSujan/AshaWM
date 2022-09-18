package com.ayata.urldatabase.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Jwt {
    private static String secret = "ayata";

    public static String getAccessToken(String phone, int minutes, String path, boolean expire){
        if(expire) {
            return JWT.create()
                    .withSubject(phone)
                    .withExpiresAt(new Date(System.currentTimeMillis() + (minutes * 60 * 1000)))
                    .withIssuer(path)
                    .sign(Algorithm.HMAC256(secret));
        }else{
            return JWT.create()
                    .withSubject(phone)
                    .withIssuer(path)
                    .sign(Algorithm.HMAC256(secret));
        }
    }

    public static String getRefreshToken(String phone, int minutes, String path){
        return JWT.create()
                .withSubject(phone)
                .withExpiresAt(new Date(System.currentTimeMillis()+(minutes*60*1000)))
                .withIssuer(path)
                .sign(Algorithm.HMAC256(secret));
    }

    public static void provideTokens(String accessToken, Object object, HttpServletResponse response){
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("access_token", accessToken);
            map.put("details", object);
            System.out.println(accessToken);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DecodedJWT extractToken(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        return verifier.verify(token);
    }

    public static void checkPhone(String phone){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(phone, null, null);
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public static void returnMap(HttpServletResponse response, Object object){
        try {
            new ObjectMapper().writeValue(response.getOutputStream(), object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
