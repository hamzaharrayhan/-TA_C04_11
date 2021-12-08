package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.repository.MesinDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MesinServiceImpl implements MesinService {

    @Autowired
    MesinDB mesinDB;

    @Override
    public List<MesinModel> getMesinList() {
        return mesinDB.findAll(Sort.by(Sort.Direction.ASC, "idMesin"));
    }

    @Override
    public MesinModel getMesinByIdMesin(Long idMesin) {
        Optional<MesinModel> mesin = mesinDB.findByIdMesin(idMesin);
        if (mesin.isPresent()) {
            return mesin.get();
        }
        return null;
    }
}
