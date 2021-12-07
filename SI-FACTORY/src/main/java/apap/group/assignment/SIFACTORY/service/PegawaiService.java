package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import java.util.List;

public interface PegawaiService {
    PegawaiModel addPegawai(PegawaiModel pegawai);
    String encrypt(String password);
    List<PegawaiModel> getListOfPegawai();
    void deletePegawai(PegawaiModel pegawai);
    PegawaiModel getPegawaiByUsername(String username);
    void addCounter(PegawaiModel pegawai);
}
