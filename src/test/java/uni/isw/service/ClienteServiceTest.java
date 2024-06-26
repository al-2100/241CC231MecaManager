package uni.isw.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import uni.isw.model.Cliente;
import uni.isw.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @MockBean
    private ClienteRepository clienteRepository;

    private Cliente cliente1;
    private Cliente cliente2;

    @BeforeEach
    public void setUp() {
        cliente1 = Cliente.builder()
                .nombres("Janio Adolfo")
                .apellidos("Zapata Inga")
                .dni("72844798")
                .sexo("Masculino")
                .direccion("Av Tomas Valle")
                .telefono("923845165")
                .build();

        cliente2 = Cliente.builder()
                .nombres("Julian")
                .apellidos("Casablancas")
                .dni("72234568")
                .sexo("Masculino")
                .direccion("Av Tupac Amaru")
                .telefono("923844445")
                .build();

        Mockito.when(clienteRepository.findAll()).thenReturn(List.of(cliente1, cliente2));
        Mockito.when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente1));
        Mockito.when(clienteRepository.findByDni("72844798")).thenReturn(Optional.of(cliente1));
    }

    @Test
    public void ClienteService_getClientes() {
        List<Cliente> clienteList = (List<Cliente>) clienteService.getClientes();

        Assertions.assertThat(clienteList).isNotNull();
        Assertions.assertThat(clienteList.size()).isEqualTo(2);
        Assertions.assertThat(clienteList).contains(cliente1, cliente2);
    }

    @Test
    public void ClienteService_getClienteById() {
        Optional<Cliente> foundCliente = clienteService.getClienteById(1L);

        Assertions.assertThat(foundCliente).isPresent();
        Assertions.assertThat(foundCliente.get()).isEqualTo(cliente1);
    }

    @Test
    public void ClienteService_findByDni() {
        Optional<Cliente> foundCliente = clienteService.findByDni(72844798L);

        Assertions.assertThat(foundCliente).isPresent();
        Assertions.assertThat(foundCliente.get()).isEqualTo(cliente1);
    }

    @Test
    public void ClienteService_saveOrUpdateCliente() {
        Cliente newCliente = Cliente.builder()
                .nombres("Carlos")
                .apellidos("Santana")
                .dni("73124578")
                .sexo("Masculino")
                .direccion("Av Los Incas")
                .telefono("923845678")
                .build();

        clienteService.saveOrUpdateCliente(newCliente);

        Mockito.verify(clienteRepository, Mockito.times(1)).save(newCliente);
    }

    @Test
    public void ClienteService_deleteCliente() {
        clienteService.deleteCliente(1L);

        Mockito.verify(clienteRepository, Mockito.times(1)).deleteById(1L);
    }
}