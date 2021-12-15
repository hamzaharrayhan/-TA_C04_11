package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.DeliveryModel;
import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;

import java.util.List;
import java.util.Map;

public interface DeliveryService {
    List<DeliveryModel> getListOfDelivery();
    List<DeliveryModel> listDeliveryByIdPegawai(Long id);
    void addDelivery(RequestUpdateItemModel reqUpdateItem, PegawaiModel pegawai, PegawaiModel kurir);
    Map<Long,String> alamatCabang();
}
