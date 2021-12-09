package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;

import java.util.List;

public interface RequestUpdateItemService {
    List<RequestUpdateItemModel> getRequestUpdateItemList();
    void updateRequestUpdateItem(Long idRequestUpdateItem, PegawaiModel staf, Integer mesin, String uuid, Integer stok, Integer kategori);
    RequestUpdateItemModel getRequestUpdateItemByIdRequestUpdateItem(Long idRequestUpdateItem);
}
