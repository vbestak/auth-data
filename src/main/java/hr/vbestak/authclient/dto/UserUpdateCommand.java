package hr.vbestak.authclient.dto;

import lombok.Data;

@Data
public class UserUpdateCommand {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
}
