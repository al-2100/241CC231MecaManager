package uni.isw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.isw.model.OST;
import uni.isw.repository.OSTRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OSTService {

    @Autowired
    private OSTRepository ostRepository;

    public List<OST> getOSTs() {
        return ostRepository.findAll();
    }

    public Optional<OST> getOSTById(Long id) {
        return ostRepository.findById(id);
    }

    public void saveOrUpdateOST(OST ost) {
        ostRepository.save(ost);
    }

    public void deleteOST(Long id) {
        ostRepository.deleteById(id);
    }
}

