package uni.isw.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uni.isw.model.Cliente;
import uni.isw.model.OST;
import uni.isw.model.Vehiculo;
import uni.isw.repository.ClienteRepository;
import uni.isw.repository.OSTRepository;
import uni.isw.repository.VehiculoRepository;
import uni.isw.service.OSTService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/ost")
public class OSTController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OSTService ostService;
    @Autowired
    private OSTRepository ostRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/listar")
    public ResponseEntity<List<OST>> getOSTs() {
        List<OST> listaOSTs = null;
        try {
            listaOSTs = ostService.getOSTs();
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(listaOSTs, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@RequestBody Optional<OST> ost) {
        try {
            ost = ostService.getOSTById(ost.get().getId_ost());
            return ost.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OST> insertOST(@RequestBody OST ost) {
        try {
            if (ost.getVehiculo() == null || ost.getCliente() == null || ost.getFallareportada() == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            ost.setEstado("Pendiente");
            ost.setFechahora(LocalDateTime.now());
            ostService.saveOrUpdateOST(ost);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ost, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OST> updateOST(@RequestBody OST ost) {
        try {
            ostService.saveOrUpdateOST(ost);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ost, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OST> deleteOST(@RequestBody Optional<OST> ost) {
        try {
            ost = ostService.getOSTById(ost.get().getId_ost());
            if (ost.isPresent()) {
                ostService.deleteOST(ost.get().getId_ost());
                return new ResponseEntity<>(ost.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

