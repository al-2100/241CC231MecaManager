package uni.isw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;
import uni.isw.model.Cliente;
import uni.isw.service.ClienteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente1, cliente2;

    @BeforeEach
    public void init() {
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
    }

    @Test
    public void ClienteController_Insert() throws Exception {
        doAnswer(invocation -> {
            Cliente cliente = invocation.getArgument(0);
            cliente.setId_cliente(1L);
            return null;
        }).when(clienteService).saveOrUpdateCliente(any(Cliente.class));

        mockMvc.perform(post("/api/v1/cliente/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals("400 BAD_REQUEST \"DNI ya registrado\"", result.getResolvedException().getMessage()));
    }

    @Test
    public void ClienteController_getClientes() throws Exception {
        List<Cliente> clienteList = new ArrayList<>();
        clienteList.add(cliente1);
        clienteList.add(cliente2);

        given(clienteService.getClientes()).willReturn(clienteList);

        mockMvc.perform(get("/api/v1/cliente/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombres", CoreMatchers.is(cliente1.getNombres())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nombres", CoreMatchers.is(cliente2.getNombres())));
    }

    @Test
    public void ClienteController_search() throws Exception {
        given(clienteService.getClienteById(1L)).willReturn(Optional.of(cliente1));

        // Crea un objeto Cliente con el ID que quieres buscar
        Cliente clienteToSearch = new Cliente();
        clienteToSearch.setId_cliente(1L);

        // Realiza la solicitud GET con un cuerpo de solicitud
        ResultActions result = mockMvc.perform(get("/api/v1/cliente/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteToSearch)));

        // Verifica que el estado de la respuesta es 200 (OK)
        result.andExpect(MockMvcResultMatchers.status().isOk());

        // Verifica que el nombre del cliente en la respuesta es el esperado
        result.andExpect(MockMvcResultMatchers.jsonPath("$.nombres", CoreMatchers.is(cliente1.getNombres())));
    }

    @Test
    public void ClienteController_searchbyDNI() throws Exception {
        given(clienteService.findByDni(Long.valueOf(cliente1.getDni()))).willReturn(Optional.of(cliente1));

        mockMvc.perform(post("/api/v1/cliente/searchDNI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombres", CoreMatchers.is(cliente1.getNombres())));
    }

    @Test
    public void ClienteController_update() throws Exception {
        doAnswer(invocation -> {
            Cliente cliente = invocation.getArgument(0);
            cliente.setId_cliente(1L);
            return null;
        }).when(clienteService).saveOrUpdateCliente(any(Cliente.class));

        mockMvc.perform(post("/api/v1/cliente/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombres", CoreMatchers.is(cliente1.getNombres())));
    }

    @Test
    public void ClienteController_delete() throws Exception {
        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            return null;
        }).when(clienteService).deleteCliente(any(Long.class));
    }
}