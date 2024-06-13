package uni.isw.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.isw.model.Cliente;
import uni.isw.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> getClienteById(Long id_cliente) {
        return clienteRepository.findById(id_cliente);
    }

    public Optional<Cliente> findByDni(Long dni) {
        return clienteRepository.findByDni(String.valueOf(dni));
    }

    public void saveOrUpdateCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}


