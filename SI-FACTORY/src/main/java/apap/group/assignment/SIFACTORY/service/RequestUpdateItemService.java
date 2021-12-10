package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.rest.ItemModel;

import java.util.List;

public interface RequestUpdateItemService {
    List<RequestUpdateItemModel> getRequestUpdateItemList();
    void updateProduksiItem(ItemModel item, Integer jumlahStokDitambahkan, PegawaiModel pegawai, MesinModel mesin, Long idRequestUpdateItem);
    RequestUpdateItemModel getRequestUpdateItemByIdRequestUpdateItem(Long idRequestUpdateItem);
}
