package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.rest.ItemModel;

import java.util.HashMap;
import java.util.List;

public interface ItemRestService {
    HashMap<String, List<ItemModel>> retrieveListItem();
    ItemModel updateItem(String uuid, ItemModel itemUpdate);
}
