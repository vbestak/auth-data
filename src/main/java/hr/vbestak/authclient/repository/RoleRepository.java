package hr.vbestak.authclient.repository;

import hr.vbestak.authclient.entity.Role;
import hr.vbestak.authclient.repository.common.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends ResourceRepository<Role, Long> {
}
