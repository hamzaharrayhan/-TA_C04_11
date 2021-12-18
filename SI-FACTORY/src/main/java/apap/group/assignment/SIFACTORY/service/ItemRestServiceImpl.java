package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.model.ProduksiModel;
import apap.group.assignment.SIFACTORY.repository.ProduksiDB;
import apap.group.assignment.SIFACTORY.rest.ItemDetail;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ItemRestServiceImpl implements ItemRestService {
    private final WebClient webClient;
    private final WebClient webClientProposeItem;

    @Autowired
    private ItemRestService itemRestService;

    @Autowired
    private MesinService mesinService;

    @Autowired
    private PegawaiService pegawaiService;

    @Autowired
    private ProduksiDB produksiDB;

    public ItemRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.itemUrl).build();
        this.webClientProposeItem = webClientBuilder.baseUrl(Setting.proposeItem).build();
    }

    @Override
    public Mono<String> proposeItem(ItemDetail item) {
        System.out.println(this.webClientProposeItem
                .post()
                .uri("/api/v1/propose-item")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(item)
                .retrieve()
                .bodyToMono(String.class).block());
        return this.webClientProposeItem
                .post()
                .uri("/api/v1/propose-item")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(item)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public HashMap<String, List<ItemModel>> retrieveListItem() {
        HashMap<String, Object> response = this.webClient
                .get()
                .uri("/api/item")
                .retrieve()
                .bodyToMono(HashMap.class)
                .block();

        List<HashMap> item = (List<HashMap>) response.get("result");
        HashMap<String, List<ItemModel>> result = new HashMap<>();

        for (int i = 0; i < item.size(); i++) {
            ItemModel tempItem = new ItemModel();
            HashMap itemJson = item.get(i);
            tempItem.setUuid((String) itemJson.get("uuid"));
            tempItem.setNama((String) itemJson.get("nama"));
            tempItem.setHarga((Integer) itemJson.get("harga"));
            tempItem.setStok((Integer) itemJson.get("stok"));
            tempItem.setKategori((String) itemJson.get("kategori"));

            String tempItemKategori = tempItem.getKategori();
            if (result.containsKey(tempItemKategori)) {
                result.get(tempItemKategori).add(tempItem);
            } else {
                List<ItemModel> listItem = new ArrayList<ItemModel>();
                result.put(tempItemKategori, listItem);
                result.get(tempItemKategori).add(tempItem);
            }
        }
        return result;
    }

    @Override
    public ItemModel getItemByUuid(String uuid) {
        HashMap<String, Object> response = this.webClient
                .get()
                .uri("/api/item/"+uuid)
                .retrieve()
                .bodyToMono(HashMap.class)
                .block();
        HashMap result = (HashMap) response.get("result");
        ItemModel item = new ItemModel();

        item.setUuid((String)result.get("uuid"));
        item.setNama((String)result.get("nama"));
        item.setHarga((Integer)result.get("harga"));
        item.setStok((Integer)result.get("stok"));
        item.setKategori((String)result.get("kategori"));

        return item;
    }

    @Override
    public ItemModel updateItem(ItemModel item, Integer jumlahStokDitambahkan, MesinModel mesin) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        PegawaiModel pegawai = pegawaiService.getPegawaiByUsername(username);

        item.setStok(item.getStok() + jumlahStokDitambahkan);
        putItem(item);

        updateAfterSubmit(item, jumlahStokDitambahkan, pegawai, mesin);

        return item;
    }

    @Override
    public Mono<String> putItem(ItemModel item) {
        System.out.println(this.webClient
                .put()
                .uri("/api/item/"+item.getUuid())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(item)
                .retrieve()
                .bodyToMono(String.class).block());
        return this.webClient
                .put()
                .uri("/api/item/"+item.getUuid())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(item)
                .retrieve()
                .bodyToMono(String.class);
    }

    public void updateAfterSubmit(ItemModel item, Integer jumlahStokDitambahkan, PegawaiModel pegawai, MesinModel mesin) {
        ProduksiModel produksi = new ProduksiModel();
        Date tanggal = new Date();

        // set produksi
        produksi.setIdItem(item.getUuid());
        produksi.setIdKategori(itemRestService.getIdKategoriByKategori(item.getKategori()));
        produksi.setTambahanStok(jumlahStokDitambahkan);
        produksi.setTanggalProduksi(tanggal);
        produksi.setPegawai(pegawai);
        produksi.setMesin(mesin);
        produksi.setRequestUpdateItem(null);
        produksiDB.save(produksi);

        // mengurangi kapasitas mesin
        mesin.setKapasitas(mesin.getKapasitas()-1);

        // menambahkan add counter pada pegawai
        pegawaiService.addCounter(pegawai);

    }

    @Override
    public Integer getIdKategoriByKategori(String kategori) {
        Integer idKategori = 0;
        if (kategori.equals("BUKU")) {
            idKategori = 1;
        } else if (kategori.equals("DAPUR")) {
            idKategori = 2;
        } else if (kategori.equals("MAKANAN & MINUMAN")) {
            idKategori = 3;
        } else if (kategori.equals("ELEKTRONIK")) {
            idKategori = 4;
        } else if (kategori.equals("FASHION")) {
            idKategori = 5;
        } else if (kategori.equals("KECANTIKAN & PERAWATAN DIRI")) {
            idKategori = 6;
        } else if (kategori.equals("FILM & MUSIK")) {
            idKategori = 7;
        } else if (kategori.equals("GAMING")) {
            idKategori = 8;
        } else if (kategori.equals("GADGET")) {
            idKategori = 9;
        } else if (kategori.equals("KESEHATAN")) {
            idKategori = 10;
        } else if (kategori.equals("RUMAH TANGGA")) {
            idKategori = 11;
        } else if (kategori.equals("FURNITURE")) {
            idKategori = 12;
        } else if (kategori.equals("ALAT & PERANGKAT KERAS")) {
            idKategori = 13;
        } else if (kategori.equals("WEDDING")) {
            idKategori = 14;
        }
        return idKategori;
    }
}
