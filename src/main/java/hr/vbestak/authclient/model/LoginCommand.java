package hr.vbestak.authclient.model;

import lombok.Getter;

@Getter
public class LoginCommand {
    private String email;
    private String password;
}
