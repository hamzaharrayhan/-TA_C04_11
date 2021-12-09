package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import com.fasterxml.jackson.databind.util.JSONPObject;
//import netscape.javascript.JSObject;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

public interface RequestUpdateItemRestService {
    RequestUpdateItemModel addRequestUpdateItem(RequestUpdateItemModel item);
}
