package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.repository.MesinDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class MesinRestServiceImpl implements MesinRestService {

    @Autowired
    private MesinDB mesinDB;

    @Override
    public List<MesinModel> retrieveListMesin() {
        return mesinDB.findAll();
    }

    @Override
    public MesinModel getMesinByIdMesin(Long idMesin) {
        Optional<MesinModel> mesin = mesinDB.findByIdMesin(idMesin);
        if(mesin.isPresent()){
            return mesin.get();
        }else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<MesinModel> getMesinByIdKategori(Integer idKategori) {
        List<MesinModel> listMesin = mesinDB.findByIdKategori(idKategori);
        return listMesin;
    }
}
