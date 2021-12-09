package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.DeliveryModel;
import apap.group.assignment.SIFACTORY.repository.DeliveryDB;
import apap.group.assignment.SIFACTORY.rest.AlamatDTO;
import apap.group.assignment.SIFACTORY.rest.Setting;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService{
    private final WebClient webClient;

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

    @Override
    public Map<Long,String> alamatCabang(){
        JsonNode jsonNode = this.webClient.get().uri("/api/cabang/list-alamat-cabang").retrieve().bodyToMono(JsonNode.class).block();
        Map<Long, String> listAlamat = new HashMap<>();
        System.out.println(jsonNode);
        for (JsonNode j : jsonNode){
            listAlamat.put(j.get("id").asLong(),j.get("alamat").asText());
        }
        System.out.println(listAlamat);
        return listAlamat;
    }

    public DeliveryServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.alamatCabangUrl).build();
    }
}