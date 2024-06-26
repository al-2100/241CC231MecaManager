package uni.isw.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uni.isw.model.Cliente;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection=EmbeddedDatabaseConnection.H2)
public class ClienteRepositoryTest {
    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        // Setup code here if needed
    }

    @Test
    public void ClienteRepository_Listar() {
        Cliente cliente1 = Cliente.builder()
                .nombres("Janio Adolfo")
                .apellidos("Zapata Inga")
                .dni("72844798")
                .sexo("Masculino")
                .direccion("Av Tomas Valle")
                .telefono("923845165")
                .build();

        Cliente cliente2 = Cliente.builder()
                .nombres("Julian")
                .apellidos("Casablancas")
                .dni("72234568")
                .sexo("Masculino")
                .direccion("Av Tupac Amaru")
                .telefono("923844445")
                .build();

        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        List<Cliente> clienteList = clienteRepository.findAll();

        Assertions.assertThat(clienteList).isNotNull();
        Assertions.assertThat(clienteList.size()).isEqualTo(2);
    }

    @Test
    public void ClienteRepository_Insert() {
        Cliente cliente1 = Cliente.builder()
                .nombres("Carlos")
                .apellidos("Santana")
                .dni("73124578")
                .sexo("Masculino")
                .direccion("Av Los Incas")
                .telefono("923845678")
                .build();

        Cliente newCliente = clienteRepository.save(cliente1);

        Assertions.assertThat(newCliente).isNotNull();
        Assertions.assertThat(newCliente.getId_cliente()).isGreaterThan(0);
    }

    @Test
    public void ClienteRepository_FindByDni() {
        Cliente cliente1 = Cliente.builder()
                .nombres("Carlos")
                .apellidos("Santana")
                .dni("73124578")
                .sexo("Masculino")
                .direccion("Av Los Incas")
                .telefono("923845678")
                .build();

        clienteRepository.save(cliente1);

        Optional<Cliente> foundCliente = clienteRepository.findByDni(cliente1.getDni());

        Assertions.assertThat(foundCliente).isPresent();
        Assertions.assertThat(foundCliente.get().getDni()).isEqualTo(cliente1.getDni());
    }

    @Test
    public void ClienteRepository_ExistsByDni() {
        Cliente cliente1 = Cliente.builder()
                .nombres("Carlos")
                .apellidos("Santana")
                .dni("73124578")
                .sexo("Masculino")
                .direccion("Av Los Incas")
                .telefono("923845678")
                .build();

        clienteRepository.save(cliente1);

        boolean exists = clienteRepository.existsByDni(cliente1.getDni());

        Assertions.assertThat(exists).isTrue();
    }
}