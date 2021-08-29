package hr.vbestak.authclient.dto;

import hr.vbestak.authclient.entity.Role;
import hr.vbestak.authclient.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal {

    private String firstName;
    private String lastName;

    private String username;
    private String email;

    private List<Role> roles;

    public UserPrincipal(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
