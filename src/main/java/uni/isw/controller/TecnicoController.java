package uni.isw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import uni.isw.model.Tecnico;
import uni.isw.repository.TecnicoRepository;

import uni.isw.service.TecnicoService;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/tecnico")
public class TecnicoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TecnicoService tecnicoService;
    @Autowired
    private TecnicoRepository tecnicoRepository;

    @GetMapping("/listar")
    public ResponseEntity<List<Tecnico>> getTecnicos(){
        List<Tecnico> listaTecnicos = null;
        try{
            listaTecnicos = tecnicoService.getTecnicos();
        }catch(Exception e){
            logger.error("Error inesperado",e);
        }
        return new ResponseEntity<>(listaTecnicos, HttpStatus.OK);
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@RequestBody Optional<Tecnico> tecnico){
        try{
            tecnico = tecnicoService.getTecnicoById(tecnico.get().getId_tecnico());
            return tecnico.map(value -> new ResponseEntity<>(value,HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch(Exception e){
            logger.error("Error inesperado",e);
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value="/insert", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tecnico> insertTecnicos(@RequestBody Tecnico tecnico) {

        if (tecnicoRepository.existsById(tecnico.getId_tecnico())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DNI ya registrado");
        }
        try {
            tecnicoService.saveOrUpdateTecnico(tecnico);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(tecnico, HttpStatus.OK);
    }


    @RequestMapping(value="/update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tecnico> updateTecnicos(@RequestBody Tecnico tecnico) {
        try {
            tecnicoService.saveOrUpdateTecnico(tecnico);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(tecnico, HttpStatus.OK);
    }
    @RequestMapping(value="/delete", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tecnico> deleteTecnicos(@RequestBody Optional<Tecnico> tecnico) {
        try {
            tecnico = tecnicoService.getTecnicoById(tecnico.get().getId_tecnico());
            if (tecnico.isPresent()) {
                tecnicoService.deleteTecnico(tecnico.get().getId_tecnico());
                return new ResponseEntity<>(tecnico.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}


