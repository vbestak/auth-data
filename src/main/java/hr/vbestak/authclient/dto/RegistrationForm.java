package hr.vbestak.authclient.dto;

import lombok.Data;

@Data
public class RegistrationForm {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private String passwordRepeat;
}
