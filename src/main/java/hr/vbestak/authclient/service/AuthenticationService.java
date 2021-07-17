package hr.vbestak.authclient.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import hr.vbestak.authclient.entity.User;
import hr.vbestak.authclient.model.LoginCommand;
import hr.vbestak.authclient.util.JwtUtil;
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

    public void register(User user){
        userService.create(user);
    }

    public String login(LoginCommand loginCommand){
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

    public void verifyToken(String bearerToken){
        jwtUtil.decode(bearerToken);
    }
}
