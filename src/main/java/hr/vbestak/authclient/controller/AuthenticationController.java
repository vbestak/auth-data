package hr.vbestak.authclient.controller;

import hr.vbestak.authclient.dto.LoginCommand;
import hr.vbestak.authclient.dto.RegistrationForm;
import hr.vbestak.authclient.dto.TokenResponse;
import hr.vbestak.authclient.model.error.ApiValidationError;
import hr.vbestak.authclient.service.AuthenticationService;
import hr.vbestak.authclient.util.AuthUtil;
import hr.vbestak.authclient.util.ValidatorUtil;
import hr.vbestak.authclient.validator.RegistrationFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;
    private RegistrationFormValidator registrationFormValidator;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, RegistrationFormValidator registrationFormValidator) {
        this.authenticationService = authenticationService;
        this.registrationFormValidator = registrationFormValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationForm registrationForm){
        Errors errors = new BeanPropertyBindingResult(registrationForm, "user");
        registrationFormValidator.validate(registrationForm, errors);

        if (errors.hasErrors()) {
            List<ApiValidationError> error = ValidatorUtil.mapFieldError(errors.getFieldErrors());
            return ResponseEntity.badRequest().body(error);
        }

        authenticationService.register(registrationForm);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginCommand loginCommand){
        TokenResponse tokenResponse = authenticationService.login(loginCommand);

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<TokenResponse> refreshToken(@RequestHeader("Authorization") String token){
        String tokenNoPrefix = AuthUtil.getToken(token);
        TokenResponse tokenResponse = authenticationService.refreshToken(tokenNoPrefix);
        return ResponseEntity.ok(tokenResponse);
    }

}
