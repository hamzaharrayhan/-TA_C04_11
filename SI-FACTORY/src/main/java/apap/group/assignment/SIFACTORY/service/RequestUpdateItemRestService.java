package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.rest.ItemModel;

//import netscape.javascript.JSObject;

public interface RequestUpdateItemRestService {
    RequestUpdateItemModel addRequestUpdateItem(RequestUpdateItemModel item);

    //    RequestUpdateItemModel getRequestUpdateItemByIdRequestUpdateItem(Long idRequestUpdateItem);
    ItemModel updateReqItem(ItemModel item, Integer jumlahStokDitambahkan, MesinModel mesin, String username, Long idRequestUpdateItem);
}
