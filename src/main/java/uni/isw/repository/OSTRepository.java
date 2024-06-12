package uni.isw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.isw.model.OST;

@Repository
public interface OSTRepository extends JpaRepository<OST, Long> {
    // Métodos personalizados si los necesitas
}

