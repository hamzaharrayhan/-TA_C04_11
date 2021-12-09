package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.MesinModel;

import java.util.List;

public interface MesinRestService {
    List<MesinModel> retrieveListMesin();
    MesinModel getMesinByIdMesin(Long idMesin);
    List<MesinModel> getMesinByIdKategori(Integer idKategori);
    Integer getIdKategoriByKategori(String kategori);
}
