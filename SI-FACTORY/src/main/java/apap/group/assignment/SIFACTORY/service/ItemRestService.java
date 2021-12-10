package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.rest.ItemDetail;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

public interface ItemRestService {
    HashMap<String, List<ItemModel>> retrieveListItem();
    ItemModel updateItem(ItemModel item, Integer jumlahStokDitambahkan, MesinModel mesin);
    ItemModel getItemByUuid(String uuid);
    Mono<String> putItem(ItemModel item);
    Mono<String> proposeItem(ItemDetail item);
    Integer getIdKategoriByKategori(String kategori);
}
