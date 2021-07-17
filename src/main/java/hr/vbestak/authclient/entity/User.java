package hr.vbestak.authclient.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.vbestak.authclient.entity.common.AuditableEntity;
import hr.vbestak.authclient.entity.common.Identifiable;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User extends AuditableEntity implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String username;
    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String password;

    private Boolean isVerified;
    private Boolean isEnabled;
    private Boolean isTerminated;

    @ManyToMany(mappedBy = "user")
    private List<Role> roles;

    private String attributes;

    @Column(updatable = false)
    private String consents;

}
