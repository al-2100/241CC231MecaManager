package uni.isw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.isw.model.Tecnico;
import uni.isw.repository.TecnicoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {
    @Autowired
    private TecnicoRepository tecnicoRepository;

    public List<Tecnico> getTecnicos(){ return tecnicoRepository.findAll();}

    public Optional<Tecnico> getTecnicoById(Integer id_tecnico){ return tecnicoRepository.findById(id_tecnico);}

    public void saveOrUpdateTecnico(Tecnico tecnico){ tecnicoRepository.save(tecnico); }

    public void deleteTecnico(Integer id){ tecnicoRepository.deleteById(id);}

}
