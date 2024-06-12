package uni.isw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.isw.model.Cliente;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Puedes agregar métodos personalizados aquí si los necesitas, por ejemplo:
    Optional<Cliente> findByDni(String dni);
    boolean existsByDni(String dni); // Para verificar si existe un cliente con el mismo DNI
}
