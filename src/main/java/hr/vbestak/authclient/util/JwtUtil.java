package hr.vbestak.authclient.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.vbestak.authclient.entity.User;
import hr.vbestak.authclient.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private ObjectMapper objectMapper;

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration-time}")
    private long EXPIRATION_TIME;

    private static String TOKEN_PREFIX = "Bearer ";

    public JwtUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String encode(User user) throws JsonProcessingException {
        UserPrincipal userPrincipal = new UserPrincipal(user);

        return JWT.create()
                .withSubject(objectMapper.writeValueAsString(userPrincipal))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    };

    public String decode(String token){
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
    };

}
