package hr.vbestak.authclient.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import hr.vbestak.authclient.dto.TokenResponse;
import hr.vbestak.authclient.entity.Role;
import hr.vbestak.authclient.entity.User;
import hr.vbestak.authclient.model.common.TokenType;
import hr.vbestak.authclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class JwtUtil {
    private String AUTHORITY_KEY = "authority";

    @Value("${jwt.secret}")
    private String ACCESS_TOKEN_SECRET;
    @Value("${jwt.expiration-time}")
    private long AT_EXPIRATION_TIME;

    @Value("${jwt.refresh-token.secret}")
    private String REFRESH_TOKEN_SECRET;
    @Value("${jwt.refresh-token.expiration-time}")
    private long RT_EXPIRATION_TIME;

    private String generateAccessToken(User user) {
        List<String> authorities = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim(AUTHORITY_KEY, authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + AT_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(ACCESS_TOKEN_SECRET));
    }

    private String generateRefreshToken(User user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + RT_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(REFRESH_TOKEN_SECRET));
    }

    public TokenResponse encode(User user) throws JsonProcessingException {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);

        TokenResponse tokenResponse = new TokenResponse(AuthUtil.TOKEN_TYPE.toLowerCase().trim(), accessToken, refreshToken);

        return tokenResponse;
    };

    private String getSecret(TokenType tokenType){
        switch (tokenType){
            case ACCESS_TOKEN:
                return ACCESS_TOKEN_SECRET;
            case REFRESH_TOKEN:
                return REFRESH_TOKEN_SECRET;
            default:
                return "";
        }
    }

    public Boolean validate(String token, TokenType tokenType){
        try{
            decode(token, tokenType);
            return true;
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decode(token, TokenType.ACCESS_TOKEN);

        Collection<? extends GrantedAuthority> authorities = decodedJWT.getClaim(AUTHORITY_KEY)
                .asList(String.class)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), token, authorities);
    }

    public DecodedJWT decode(String token, TokenType tokenType){
            String secret = getSecret(tokenType);
            return JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token);
    };

}
