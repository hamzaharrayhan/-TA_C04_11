package apap.group.assignment.SIFACTORY.service;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.repository.RequestUpdateItemDB;
import apap.group.assignment.SIFACTORY.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.sql.Date;

@Service
@Transactional
public class RequestUpdateItemRestServiceImpl implements RequestUpdateItemRestService {
    private final WebClient webClient;
    @Autowired
    private RequestUpdateItemDB requestUpdateItemDB;

    @Override
    public RequestUpdateItemModel addRequestUpdateItem(RequestUpdateItemModel item) {
        item.setExecuted(false);
        return requestUpdateItemDB.save(item);
    }

    public RequestUpdateItemRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.updateStokUrl).build();
    }

}
