package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import java.util.List;

public interface MesinService {
    List<MesinModel> getMesinList();
    MesinModel getMesinByIdMesin(Long idMesin);
    List<MesinModel> getMesinByIdKategori(Integer idKategori);
    Integer getIdKategoriByKategori(String kategori);
}
