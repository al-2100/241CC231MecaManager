package uni.isw.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.isw.model.Problema;
import uni.isw.repository.ProblemaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemaService {
    @Autowired
    private ProblemaRepository problemaRepository;

    public List<Problema> getProblemas(){return problemaRepository.findAll();}

    public Optional<Problema> getProblemaById(Long id_problema){return problemaRepository.findById(id_problema);}

    public void saveOrUpdateProblema(Problema problema){ problemaRepository.save(problema); }

    public void deleteProblema(Long id_problema){ problemaRepository.deleteById(id_problema); }
}
