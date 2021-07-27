package hr.vbestak.authclient.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.vbestak.authclient.entity.User;
import hr.vbestak.authclient.model.UserPrincipal;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public final class JwtUtil {
    private ObjectMapper objectMapper;

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration-time}")
    private long EXPIRATION_TIME;

    private String AUTHORITY_KEY = "authority";

    public JwtUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String encode(User user) throws JsonProcessingException {
        UserPrincipal userPrincipal = new UserPrincipal(user);
        String authorities = userPrincipal.getRoles().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim(AUTHORITY_KEY, authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    };

    public Boolean validate(String token){
        try{
            JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token);

            return true;
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decode(token);

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(decodedJWT.getClaim(AUTHORITY_KEY).asString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), token, authorities);
    }

    public DecodedJWT decode(String token){
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token);
    };

}
