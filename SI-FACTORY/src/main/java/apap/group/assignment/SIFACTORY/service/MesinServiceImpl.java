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

    @Override
    public List<MesinModel> getMesinByIdKategori(Integer idKategori) {
        List<MesinModel> listMesin = mesinDB.findByIdKategori(idKategori);
        return listMesin;
    }

    @Override
    public Integer getIdKategoriByKategori(String kategori) {
        Integer idKategori = 0;
        if (kategori.equals("BUKU")) {
            idKategori = 1;
        } else if (kategori.equals("DAPUR")) {
            idKategori = 2;
        } else if (kategori.equals("MAKANAN & MINUMAN")) {
            idKategori = 3;
        } else if (kategori.equals("ELEKTRONIK")) {
            idKategori = 4;
        } else if (kategori.equals("FASHION")) {
            idKategori = 5;
        } else if (kategori.equals("KECANTIKAN & PERAWATAN DIRI")) {
            idKategori = 6;
        } else if (kategori.equals("FILM & MUSIK")) {
            idKategori = 7;
        } else if (kategori.equals("GAMING")) {
            idKategori = 8;
        } else if (kategori.equals("GADGET")) {
            idKategori = 9;
        } else if (kategori.equals("KESEHATAN")) {
            idKategori = 10;
        } else if (kategori.equals("RUMAH TANGGA")) {
            idKategori = 11;
        } else if (kategori.equals("FURNITURE")) {
            idKategori = 12;
        } else if (kategori.equals("ALAT & PERANGKAT KERAS")) {
            idKategori = 13;
        } else if (kategori.equals("WEDDING")) {
            idKategori = 14;
        }
        return idKategori;
    }
}
