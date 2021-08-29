package hr.vbestak.authclient.dto;

import lombok.Getter;

@Getter
public class LoginCommand {
    private String email;
    private String password;
}
