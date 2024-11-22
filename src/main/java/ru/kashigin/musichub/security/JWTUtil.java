package ru.kashigin.musichub.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${secret.jwt}")
    private String secret;
    private Algorithm algo;
    @PostConstruct
    public void init() {
        this.algo = Algorithm.HMAC256(secret);
    }

    public String generateToken(String name){
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("name", name)
                .withIssuedAt(new Date())
                .withIssuer("MusicHub")
                .withExpiresAt(expirationDate)
                .sign(algo);
    }

    public boolean validateToken(String token){
        try {
            JWTVerifier verifier = JWT.require(algo)
                    .withSubject("User details")
                    .withIssuer("MusicHub")
                    .build();
            verifier.verify(token);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    public String extractName(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }
}
