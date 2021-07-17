package hr.vbestak.authclient.repository.common;

import hr.vbestak.authclient.entity.common.Identifiable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface ResourceRepository<T extends Identifiable<K>, K> extends JpaRepository<T, K>, JpaSpecificationExecutor<T> {
    Optional<T> findById(K id);
}
