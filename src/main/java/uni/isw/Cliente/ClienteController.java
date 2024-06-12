package uni.isw.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v0/cliente")
public class ClienteController {
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping()
    public List<Cliente> getClientes(){
        return clienteService.getClientes();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCliente(@RequestBody Cliente cliente){
        clienteService.addCliente(cliente);
        System.out.println(cliente.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente añadido exitosamente");
    }
}
