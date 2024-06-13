package uni.isw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.isw.model.Problema;

import java.util.Optional;

@Repository
public interface ProblemaRepository extends JpaRepository<Problema, Long> {
    Optional<Problema> findsById(Long id);
    boolean existsById(Long id);
}
