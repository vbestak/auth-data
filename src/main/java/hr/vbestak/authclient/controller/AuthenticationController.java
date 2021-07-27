package hr.vbestak.authclient.controller;

import hr.vbestak.authclient.entity.User;
import hr.vbestak.authclient.model.LoginCommand;
import hr.vbestak.authclient.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody User user){
        authenticationService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginCommand loginCommand){
        String token = authenticationService.login(loginCommand);

        return ResponseEntity.ok(token);
    }

}
