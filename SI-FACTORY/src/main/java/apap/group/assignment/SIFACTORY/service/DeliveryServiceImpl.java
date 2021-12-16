package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.DeliveryModel;
import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.repository.DeliveryDB;
import apap.group.assignment.SIFACTORY.rest.Setting;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService{
    private final WebClient webClient;

    @Autowired
    private DeliveryDB deliveryDB;

    @Autowired
    private RequestUpdateItemService requestUpdateItemService;

    @Override
    public List<DeliveryModel> getListOfDelivery() {
        return deliveryDB.findAll();
    }

    @Override
    public List<DeliveryModel> listDeliveryByIdPegawai(Long id) {
        return deliveryDB.findAllByIdKurir(id);
    }

    @Override
    public DeliveryModel getDeliveryByIdDelivery(Long idDelivery) {
        Optional<DeliveryModel> delivery = deliveryDB.findDeliveryByIdDelivery(idDelivery);
        if (delivery.isPresent()) {
            return delivery.get();
        }
        return null;
    }

    @Override
    public void addDelivery(RequestUpdateItemModel reqUpdateItem, PegawaiModel pegawai, PegawaiModel kurir) {
        DeliveryModel delivery = new DeliveryModel();
        java.util.Date date = new java.util.Date();
        delivery.setRequestUpdateItem(reqUpdateItem);
        delivery.setPegawai(pegawai);
        delivery.setTanggalDibuat(date);
        delivery.setSent(false);
        delivery.setIdCabang(Long.valueOf(reqUpdateItem.getIdCabang()));
        delivery.setIdKurir(kurir.getIdPegawai());
        deliveryDB.save(delivery);
    }

    @Override
    public Map<Long,String> alamatCabang(){
        JsonNode jsonNode = this.webClient.get().uri("/api/cabang/list-alamat-cabang").retrieve().bodyToMono(JsonNode.class).block().get("result");
        Map<Long, String> listAlamat = new HashMap<>();
        for (JsonNode j : jsonNode){
            listAlamat.put(j.get("id").asLong(),j.get("alamat").asText());
        }
        return listAlamat;
    }

    public DeliveryServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.alamatCabangUrl).build();
    }
}