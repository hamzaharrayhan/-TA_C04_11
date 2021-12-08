package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.DeliveryModel;
import java.util.List;

public interface DeliveryService {
    List<DeliveryModel> getListOfDelivery();
    List<DeliveryModel> listDeliveryByIdPegawai(Long id);
}
