package uni.isw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.isw.model.Cliente;
import uni.isw.model.Vehiculo;

import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    Optional<Vehiculo> findByPlaca(String placa);
    boolean existsByPlaca(String dni);
}

