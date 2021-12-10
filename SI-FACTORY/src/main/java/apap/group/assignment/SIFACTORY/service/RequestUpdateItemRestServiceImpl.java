package apap.group.assignment.SIFACTORY.service;
import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.model.ProduksiModel;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.repository.ProduksiDB;
import apap.group.assignment.SIFACTORY.repository.RequestUpdateItemDB;
import apap.group.assignment.SIFACTORY.rest.ItemDetail;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RequestUpdateItemRestServiceImpl implements RequestUpdateItemRestService {

    private final WebClient webClient;
    private final WebClient webClientItem;

    @Autowired
    private RequestUpdateItemDB requestUpdateItemDB;

    @Autowired
    private RequestUpdateItemService requestUpdateItemService;

    @Override
    public RequestUpdateItemModel addRequestUpdateItem(RequestUpdateItemModel item) {
        item.setExecuted(false);
        return requestUpdateItemDB.save(item);
    }

    @Override
    public RequestUpdateItemModel getRequestUpdateItemByIdRequestUpdateItem(Long idRequestUpdateItem) {
        Optional<RequestUpdateItemModel> requestUpdateItem = requestUpdateItemDB.findByIdRequestUpdateItem(idRequestUpdateItem);
        if(requestUpdateItem.isPresent()){
            return requestUpdateItem.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Mono<String> updateItem(String uuid, Integer stokTambahan, Long idRequestUpdateItem, PegawaiModel staf, Integer mesin, Integer kategori) {
//        MultiValueMap<String, Integer> data = new LinkedMultiValueMap<>();
        System.out.println("stok di restservice = " + stokTambahan);
        System.out.println("uuid = " + uuid);
//        data.add("stok", stokTambahan);
        requestUpdateItemService.updateRequestUpdateItem(idRequestUpdateItem, staf, mesin, uuid, stokTambahan, kategori);
        System.out.println(this.webClientItem
                .put()
                .uri("/api/item/" + uuid)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(stokTambahan)
                .retrieve()
                .bodyToMono(String.class).block());
        return this.webClientItem
                .put()
                .uri("/api/item/" + uuid)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(stokTambahan)
                .retrieve()
                .bodyToMono(String.class);
    }

    public RequestUpdateItemRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.updateStokUrl).build();
        this.webClientItem = webClientBuilder.baseUrl(Setting.itemUrl).build();
    }

}
