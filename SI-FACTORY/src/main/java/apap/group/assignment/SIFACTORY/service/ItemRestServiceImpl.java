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
<<<<<<< HEAD
    private final WebClient webClientSiBisnis;

    public ItemRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.itemUrl).build();
        this.webClientSiBisnis = webClientBuilder.baseUrl(Setting.siBisnisUrl).build();
    }

    @Override
    public ItemModel proposeItem(ItemModel item){
        ItemModel item = this.webClientSiBisnis
                .post()
                .uri("/api/v1/")
                .retrieve()
                .bodyToMono(HashMap.class)
                .block();
//        return penjagaDB.save(penjaga);
=======

    public ItemRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.itemUrl).build();
>>>>>>> 90194b611fb1642350d58c320bea4471930d2781
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
<<<<<<< HEAD
        List<ItemModel> listItem = new ArrayList<ItemModel>();
        HashMap<String, List<ItemModel>> result = new HashMap<>();

        ItemModel tempItem = new ItemModel();
        for (int i = 0; i < item.size(); i++) {
=======
        HashMap<String, List<ItemModel>> result = new HashMap<>();

        for (int i = 0; i < item.size(); i++) {
            ItemModel tempItem = new ItemModel();
>>>>>>> 90194b611fb1642350d58c320bea4471930d2781
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
<<<<<<< HEAD
                List<ItemModel> listItemNew = new ArrayList<ItemModel>();
                result.put(tempItemKategori, listItemNew);
=======
                List<ItemModel> listItem = new ArrayList<ItemModel>();
                result.put(tempItemKategori, listItem);
                result.get(tempItemKategori).add(tempItem);
>>>>>>> 90194b611fb1642350d58c320bea4471930d2781
            }
        }
        return result;
    }
}
<<<<<<< HEAD
=======

>>>>>>> 90194b611fb1642350d58c320bea4471930d2781
