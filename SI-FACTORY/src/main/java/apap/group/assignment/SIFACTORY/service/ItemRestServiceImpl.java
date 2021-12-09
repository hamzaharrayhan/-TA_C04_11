package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.rest.ItemDetail;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ItemRestServiceImpl implements ItemRestService {
    private final WebClient webClient;
    private final WebClient webClientProposeItem;

    public ItemRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.itemUrl).build();
        this.webClientProposeItem = webClientBuilder.baseUrl(Setting.proposeItem).build();
    }

    @Override
    public Mono<String> proposeItem(ItemDetail item) {
        System.out.println(this.webClientProposeItem
                .post()
                .uri("/api/v1/propose-item")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(item)
                .retrieve()
                .bodyToMono(String.class).block());
        return this.webClientProposeItem
                .post()
                .uri("/api/v1/propose-item")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(item)
                .retrieve()
                .bodyToMono(String.class);
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
    public ItemModel getItemByUuid(String uuid) {
        HashMap<String, Object> response = this.webClient
                .get()
                .uri("/api/item/"+uuid)
                .retrieve()
                .bodyToMono(HashMap.class)
                .block();
        HashMap result = (HashMap) response.get("result");
        ItemModel item = new ItemModel();

        item.setUuid((String)result.get("uuid"));
        item.setNama((String)result.get("nama"));
        item.setHarga((Integer)result.get("harga"));
        item.setStok((Integer)result.get("stok"));
        item.setKategori((String)result.get("kategori"));

        return item;
    }
}
