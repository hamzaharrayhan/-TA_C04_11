package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.rest.RequestUpdateItemDTO;

public interface RequestUpdateItemRestService {
    RequestUpdateItemModel addRequestUpdateItem(RequestUpdateItemDTO item);
}
