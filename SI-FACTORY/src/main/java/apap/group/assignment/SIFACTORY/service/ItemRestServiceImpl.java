package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.rest.Setting;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
=======
>>>>>>> 90194b611fb1642350d58c320bea4471930d2781
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ItemRestServiceImpl implements ItemRestService {
    private final WebClient webClient;

    public ItemRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.itemUrl).build();
    }

    @Override
    public HashMap<String, List<ItemModel>> retrieveListItem() {
        HashMap<String, Object> response = this.webClient
                .get()
                .uri("/api/item")
                .retrieve()
                .bodyToMono(HashMap.class)
                .block();

        List<HashMap> item = (List<HashMap>) response.get("result");
        HashMap<String, List<ItemModel>> result = new HashMap<>();

        for (int i = 0; i < item.size(); i++) {
            ItemModel tempItem = new ItemModel();
            HashMap itemJson = item.get(i);
            tempItem.setUuid((String) itemJson.get("uuid"));
            tempItem.setNama((String) itemJson.get("nama"));
            tempItem.setHarga((Integer) itemJson.get("harga"));
            tempItem.setStok((Integer) itemJson.get("stok"));
            tempItem.setKategori((String) itemJson.get("kategori"));

            String tempItemKategori = tempItem.getKategori();
            if (result.containsKey(tempItemKategori)) {
                result.get(tempItemKategori).add(tempItem);
            } else {
                List<ItemModel> listItem = new ArrayList<ItemModel>();
                result.put(tempItemKategori, listItem);
                result.get(tempItemKategori).add(tempItem);
            }
        }
        return result;
    }

    @Override
    public ItemModel updateItem(String uuid, ItemModel itemUpdate) {
//        ItemModel item =
//        item.setNamaBioskop(bioskopUpdate.getNamaBioskop());
//        item.setAlamatBioskop(bioskopUpdate.getAlamatBioskop());
//        item.setJumlahStudio(bioskopUpdate.getJumlahStudio());

        return this.webClient
                .post()
                .uri("/api/item/{uuid}")
                .retrieve()
                .bodyToMono(ItemModel.class)
                .block();
    }


}
