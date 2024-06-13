package uni.isw.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.isw.model.Cliente;
import uni.isw.model.Vehiculo;
import uni.isw.service.VehiculoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/vehiculo")
public class VehiculoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    VehiculoService vehiculoService;

    @GetMapping("/listar")
    public ResponseEntity<List<Vehiculo>> getVehiculos() {
        List<Vehiculo> listaVehiculos = null;
        try {
            listaVehiculos = vehiculoService.getVehiculos();
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(listaVehiculos, HttpStatus.OK);
    }

    @RequestMapping(value="/search", method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@RequestBody Optional<Vehiculo> vehiculo) {
        try {
            vehiculo = vehiculoService.getVehiculoById(vehiculo.get().getIdVehiculo());
            return vehiculo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value="/searchPlaca", method=RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchbyPlaca(@RequestBody Optional<Vehiculo> vehiculo) {
        try {
            vehiculo = vehiculoService.findbyPLaca(vehiculo.get().getPlaca());
            return vehiculo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value="/insert", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vehiculo> insertVehiculo(@RequestBody Vehiculo vehiculo) {
        try {
            vehiculoService.saveOrUpdateVehiculo(vehiculo);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(vehiculo, HttpStatus.OK);
    }

    @RequestMapping(value="/update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)    public ResponseEntity<Vehiculo> updateVehiculo(@RequestBody Vehiculo vehiculo) {
        try {
            vehiculoService.saveOrUpdateVehiculo(vehiculo);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(vehiculo, HttpStatus.OK);
    }

    @RequestMapping(value="/delete", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vehiculo> deleteVehiculo(@RequestBody Optional<Vehiculo> vehiculo) {
        try {
            vehiculo = vehiculoService.getVehiculoById(vehiculo.get().getIdVehiculo());
            if (vehiculo.isPresent()) {
                vehiculoService.deleteVehiculo(vehiculo.get().getIdVehiculo());
                return new ResponseEntity<>(vehiculo.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

