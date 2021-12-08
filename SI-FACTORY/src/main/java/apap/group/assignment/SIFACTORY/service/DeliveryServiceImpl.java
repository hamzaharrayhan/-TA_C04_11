package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.DeliveryModel;
import apap.group.assignment.SIFACTORY.repository.DeliveryDB;
import apap.group.assignment.SIFACTORY.repository.PegawaiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService{
    @Autowired
    private DeliveryDB deliveryDB;

    @Override
    public List<DeliveryModel> getListOfDelivery() {
        return deliveryDB.findAll();
    }

    @Override
    public List<DeliveryModel> listDeliveryByIdPegawai(Long id) {
        return deliveryDB.findAllByIdKurir(id);
    }
}
