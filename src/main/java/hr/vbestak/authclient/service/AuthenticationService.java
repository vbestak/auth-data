package hr.vbestak.authclient.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import hr.vbestak.authclient.dto.RegistrationForm;
import hr.vbestak.authclient.dto.TokenResponse;
import hr.vbestak.authclient.entity.User;
import hr.vbestak.authclient.dto.LoginCommand;
import hr.vbestak.authclient.util.JwtUtil;
import hr.vbestak.authclient.model.common.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private JwtUtil jwtUtil;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;

    @Autowired
    public AuthenticationService(JwtUtil jwtUtil, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    public void register(RegistrationForm user){
        userService.create(mapRegistrationForm(user));
    }

    public TokenResponse login(LoginCommand loginCommand){
        User user = userService.findByEmail(loginCommand.getEmail());
        Boolean passwordIsCorrect = bCryptPasswordEncoder.matches(loginCommand.getPassword(), user.getPassword());

        if(passwordIsCorrect){
            try {
                return jwtUtil.encode(user);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Server error. Object to json convert error.");
            }
        }

        throw new IllegalArgumentException("Wrong user credentials");
    }

    public TokenResponse refreshToken(String refreshToken) {
        try {
            DecodedJWT decodedJWT = jwtUtil.decode(refreshToken, TokenType.REFRESH_TOKEN);
            User user = userService.findByEmail(decodedJWT.getSubject());
            return jwtUtil.encode(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Server error. Object to json convert error.");
        }
    }

    public void verifyToken(String bearerToken, TokenType tokenType){
        jwtUtil.decode(bearerToken, tokenType);
    }


    public User mapRegistrationForm(RegistrationForm registrationForm) {
        User user = new User();
        user.setFirstName(registrationForm.getFirstName());
        user.setLastName(registrationForm.getLastName());
        user.setUsername(registrationForm.getUserName());
        user.setEmail(registrationForm.getEmail());
        user.setPassword(registrationForm.getPassword());
        return user;
    }
}
