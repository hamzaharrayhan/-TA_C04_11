package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.rest.ItemModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

public interface ItemRestService {
    HashMap<String, List<ItemModel>> retrieveListItem();
    ItemModel updateItem(ItemModel item, Integer jumlahStokDitambahkan) throws JsonProcessingException;
    ItemModel getItemByUuid(String uuid);
    Mono<String> putItem(ItemModel item) throws JsonProcessingException;
}
