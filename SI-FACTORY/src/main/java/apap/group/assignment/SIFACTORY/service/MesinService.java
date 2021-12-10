package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.rest.ItemModel;

import java.util.List;

public interface MesinService {
    List<MesinModel> getMesinList();
    MesinModel getMesinByIdMesin(Long idMesin);
    List<MesinModel> getListMesinByKategori(ItemModel item);
}
