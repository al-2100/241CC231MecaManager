package uni.isw.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;
import uni.isw.model.Cliente;
import uni.isw.repository.ClienteRepository;
import uni.isw.service.ClienteService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "api/v1/cliente")
public class ClienteController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ClienteService clienteService;
    @Autowired
    private ClienteRepository clienteRepository;


    @RequestMapping(value="/listar", method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cliente>> getClientes() {
        List<Cliente> listaClientes = null;
        try {
            listaClientes = clienteService.getClientes();
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(listaClientes, HttpStatus.OK);
    }

    @RequestMapping(value="/search", method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@RequestBody Optional<Cliente> cliente) {
        try {
            cliente = clienteService.getClienteById(cliente.get().getId_cliente());
            return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/searchDNI", method=RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchbyDNI(@RequestBody Optional<Cliente> cliente) {
        try {
            cliente = clienteService.findByDni(Long.valueOf(cliente.get().getDni()));
            return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/insert", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> insertCliente(@RequestBody Cliente cliente) {
        // Verificar si el DNI ya existe
        if (clienteRepository.existsByDni(cliente.getDni())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DNI ya registrado");
        }
        try {
            clienteService.saveOrUpdateCliente(cliente);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @RequestMapping(value="/update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente cliente) {
        try {
            Optional<Cliente> existingCliente = clienteService.getClienteById(cliente.getId_cliente());
            if (existingCliente.isPresent()) {
                Cliente savedCliente = existingCliente.get();
                savedCliente.setDni(cliente.getDni());
                savedCliente.setNombres(cliente.getNombres());
                savedCliente.setApellidos(cliente.getApellidos());
                savedCliente.setDireccion(cliente.getDireccion());
                savedCliente.setSexo(cliente.getSexo());
                savedCliente.setTelefono(cliente.getTelefono());
                clienteService.saveOrUpdateCliente(savedCliente);
                return new ResponseEntity<>(savedCliente, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/delete", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> deleteCliente(@RequestBody Optional<Cliente> cliente) {
        try {
            cliente = clienteService.getClienteById(cliente.get().getId_cliente());
            if (cliente.isPresent()) {
                clienteService.deleteCliente(cliente.get().getId_cliente());
                return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
