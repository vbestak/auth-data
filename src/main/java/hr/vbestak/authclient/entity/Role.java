package hr.vbestak.authclient.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.vbestak.authclient.entity.common.Identifiable;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority, Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String name;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> user;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return name;
    }
}
