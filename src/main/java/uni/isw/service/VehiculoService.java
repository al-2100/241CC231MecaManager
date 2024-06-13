package uni.isw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.isw.model.Vehiculo;
import uni.isw.repository.VehiculoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> getVehiculos() {
        return vehiculoRepository.findAll();
    }

    public Optional<Vehiculo> getVehiculoById(Long id) {
        return vehiculoRepository.findById(id);
    }

    public void saveOrUpdateVehiculo(Vehiculo vehiculo) {
        vehiculoRepository.save(vehiculo);
    }

    public Optional<Vehiculo> findbyPLaca(String placa) {
        return vehiculoRepository.findByPlaca(placa);
    }

    public void deleteVehiculo(Long id) {
        vehiculoRepository.deleteById(id);
    }
}
