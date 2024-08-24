package com.example.testeffectivemobile.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.ZonedDateTime;
import java.util.Date;


@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;


    public String generateToken(String name){

        Date date = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject("User")
                .withClaim("name" , name)
                .withIssuedAt(new Date())
                .withExpiresAt(date)
                .sign(Algorithm.HMAC256(secret));
    }


    public String validateToken(String token){


        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User")
                .build();


        DecodedJWT jwt = jwtVerifier.verify(token);

        return jwt.getClaim("name").asString();
    }





}
