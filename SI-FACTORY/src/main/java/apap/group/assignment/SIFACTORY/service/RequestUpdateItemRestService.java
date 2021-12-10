package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.rest.ItemDetail;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import com.fasterxml.jackson.databind.util.JSONPObject;
//import netscape.javascript.JSObject;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

public interface RequestUpdateItemRestService {
    RequestUpdateItemModel addRequestUpdateItem(RequestUpdateItemModel item);
//    RequestUpdateItemModel getRequestUpdateItemByIdRequestUpdateItem(Long idRequestUpdateItem);
    ItemModel updateReqItem(ItemModel item, Integer jumlahStokDitambahkan, MesinModel mesin, String username, Long idRequestUpdateItem);
}
