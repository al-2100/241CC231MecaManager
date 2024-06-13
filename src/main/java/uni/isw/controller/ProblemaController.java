package uni.isw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import uni.isw.model.Problema;
import uni.isw.repository.ProblemaRepository;
import uni.isw.service.ProblemaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/problemas")
public class ProblemaController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProblemaService problemaService;
    @Autowired
    private ProblemaRepository problemaRepository;

    @GetMapping("/listar")
    public ResponseEntity<List<Problema>> getProblemas() {
        List<Problema
                > listaProblemas = null;
        try {
            listaProblemas = problemaService.getProblemas();
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(listaProblemas, HttpStatus.OK);
    }

    @RequestMapping(value="/search", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@RequestBody Optional<Problema> problema) {
        try {
            problema = problemaService.getProblemaById(problema.get().getIdproblema());
            return problema.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/insert", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Problema> insertProblema(@RequestBody Problema problema) {

        if (problemaRepository.existsById(problema.getIdproblema())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id ya registrado");
        }
        try {
            problemaService.saveOrUpdateProblema(problema);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(problema, HttpStatus.OK);
    }

    @RequestMapping(value="/update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Problema> updateProblema(@RequestBody Problema problema) {
        try {
            problemaService.saveOrUpdateProblema(problema);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(problema, HttpStatus.OK);
    }

    @RequestMapping(value="/delete", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Problema> deleteProblema(@RequestBody Optional<Problema> problema) {
        try {
            problema = problemaService.getProblemaById(problema.get().getIdproblema());
            if (problema.isPresent()) {
                problemaService.deleteProblema(problema.get().getIdproblema());
                return new ResponseEntity<>(problema.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

