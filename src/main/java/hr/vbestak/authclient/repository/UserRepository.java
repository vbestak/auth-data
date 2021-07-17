package hr.vbestak.authclient.repository;

import hr.vbestak.authclient.entity.User;
import hr.vbestak.authclient.repository.common.ResourceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends ResourceRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
