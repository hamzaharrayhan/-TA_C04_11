package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.rest.ItemDetail;
import com.fasterxml.jackson.databind.util.JSONPObject;
//import netscape.javascript.JSObject;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

public interface RequestUpdateItemRestService {
    RequestUpdateItemModel addRequestUpdateItem(RequestUpdateItemModel item);
    RequestUpdateItemModel getRequestUpdateItemByIdRequestUpdateItem(Long idRequestUpdateItem);
    Mono<ItemDetail> updateItem(String uuid, Integer stok, Long idRequestUpdateItem, PegawaiModel staf, Integer mesin, Integer kategori);
}
