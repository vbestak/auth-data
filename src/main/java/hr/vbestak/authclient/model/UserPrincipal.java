package hr.vbestak.authclient.model;

import hr.vbestak.authclient.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPrincipal {

    private String firstName;
    private String lastName;

    private String username;
    private String email;

    public UserPrincipal(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
