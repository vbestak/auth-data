package hr.vbestak.authclient.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hr.vbestak.authclient.entity.common.AuditableEntity;
import hr.vbestak.authclient.entity.common.Identifiable;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User extends AuditableEntity implements UserDetails, Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String username;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private Boolean isVerified;
    private Boolean isEnabled;
    private Boolean isTerminated;

    @JsonIgnoreProperties({"id"})
    @ManyToMany(mappedBy = "user")
    private List<Role> roles;

    private String attributes;

    @Column(updatable = false)
    private String consents;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
