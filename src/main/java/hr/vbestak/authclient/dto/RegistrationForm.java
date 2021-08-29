package hr.vbestak.authclient.dto;

import lombok.Data;

@Data
public class RegistrationForm {
    private String email;
    private String password;
    private String passwordRepeat;
}
