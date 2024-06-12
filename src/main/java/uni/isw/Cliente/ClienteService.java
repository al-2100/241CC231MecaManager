package uni.isw.Cliente;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {



    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    public void addCliente(Cliente cliente){
        clienteRepository.save(cliente);
    }

    public List<Cliente> getClientes(){
        return clienteRepository.findAll();
    }


}
