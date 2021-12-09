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
    public Mono<ItemDetail> updateItem(String uuid, Integer stok, Long idRequestUpdateItem, PegawaiModel staf, Integer mesin) {
        MultiValueMap<String, Integer> data = new LinkedMultiValueMap<>();
        data.add("stok", stok);
        requestUpdateItemService.updateRequestUpdateItem(idRequestUpdateItem, staf, mesin);
        return this.webClientItem.put().uri("/api/item" + uuid).retrieve().bodyToMono(ItemDetail.class);
    }

    public RequestUpdateItemRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.updateStokUrl).build();
        this.webClientItem = webClientBuilder.baseUrl(Setting.itemUrl).build();
    }

}
