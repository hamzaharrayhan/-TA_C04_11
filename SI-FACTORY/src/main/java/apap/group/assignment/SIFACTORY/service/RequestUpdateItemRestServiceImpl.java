package apap.group.assignment.SIFACTORY.service;
import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.model.ProduksiModel;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.repository.ProduksiDB;
import apap.group.assignment.SIFACTORY.repository.RequestUpdateItemDB;
import apap.group.assignment.SIFACTORY.rest.ItemDetail;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.rest.RequestUpdateItemDTO;
import apap.group.assignment.SIFACTORY.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private PegawaiService pegawaiService;

    @Autowired
    private ItemRestService itemRestService;

    @Override
    public RequestUpdateItemModel addRequestUpdateItem(RequestUpdateItemDTO item) {
        RequestUpdateItemModel rui = new RequestUpdateItemModel();
        rui.setTanggalRequest(item.getTanggalRequest());
        rui.setIdItem(item.getIdItem());
        rui.setIdCabang(item.getIdCabang());
        rui.setTambahanStok(item.getTambahanStok());
        rui.setIdKategori(item.getIdKategori());
        rui.setExecuted(false);
        return requestUpdateItemDB.save(rui);
    }

//    @Override
//    public RequestUpdateItemModel getRequestUpdateItemByIdRequestUpdateItem(Long idRequestUpdateItem) {
//        Optional<RequestUpdateItemModel> requestUpdateItem = requestUpdateItemDB.findByIdRequestUpdateItem(idRequestUpdateItem);
//        if(requestUpdateItem.isPresent()){
//            return requestUpdateItem.get();
//        } else {
//            throw new NoSuchElementException();
//        }
//    }

    @Override
    public ItemModel updateReqItem(ItemModel item, Integer jumlahStokDitambahkan, MesinModel mesin, String username, Long idRequestUpdateItem) {
        PegawaiModel pegawai = pegawaiService.getPegawaiByUsername(username);
        item.setStok(jumlahStokDitambahkan);
        itemRestService.putItem(item);
        requestUpdateItemService.updateProduksiItem(item, jumlahStokDitambahkan, pegawai, mesin, idRequestUpdateItem);
        return item;
    }

    public RequestUpdateItemRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.updateStokUrl).build();
        this.webClientItem = webClientBuilder.baseUrl(Setting.itemUrl).build();
    }

}
