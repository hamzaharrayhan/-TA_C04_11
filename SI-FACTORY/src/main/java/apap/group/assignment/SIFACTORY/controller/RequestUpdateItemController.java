package apap.group.assignment.SIFACTORY.controller;

import apap.group.assignment.SIFACTORY.model.*;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class RequestUpdateItemController {

    @Qualifier("requestUpdateItemServiceImpl")
    @Autowired
    private RequestUpdateItemService requestUpdateItemService;

    @Autowired
    private RequestUpdateItemRestService requestUpdateItemRestService;

    @Autowired
    private MesinService mesinService;

    @Autowired
    private PegawaiService pegawaiService;

    @Autowired
    private ItemRestService itemRestService;

    @Autowired
    private ProduksiService produksiService;

    @GetMapping("/request-update-item/viewall")
    public String listRequestUpdateItem(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "").replace("]","");

        List<RequestUpdateItemModel> listRequestUpdateItem = requestUpdateItemService.getRequestUpdateItemList();

        model.addAttribute("role", role);
        model.addAttribute("listRequestUpdateItem", listRequestUpdateItem);
        model.addAttribute("activePage", "request");

        //Return view template yang diinginkan
        return "viewall-request-update-item";
    }

    @GetMapping("/item/update-request/{idRequestUpdateItem}/{uuid}")
    public String updateReqItemForm(
            @PathVariable Long idRequestUpdateItem,
            @PathVariable("uuid") String uuid,
            Model model
    ) {
        RequestUpdateItemModel reqUpdateItem = requestUpdateItemService.getRequestUpdateItemByIdRequestUpdateItem(idRequestUpdateItem);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "")
                .replace("]","");
        try {
            List<MesinModel> listMesin = mesinService.getMesinList();
            List<MesinModel> listMesinByKategori = new ArrayList<>();
            System.out.println(reqUpdateItem.getIdKategori());
            ItemModel item = itemRestService.getItemByUuid(uuid);
            // filter mesin berdasarkan kategori
            for (MesinModel mesin: listMesin) {
                if (mesin.getIdKategori() == reqUpdateItem.getIdKategori() && mesin.getKapasitas() > 0) {
                    listMesinByKategori.add(mesin);
//                System.out.println(mesin.getNama());
                }
            }
            model.addAttribute("role", role);
            model.addAttribute("stokTambahan", reqUpdateItem.getTambahanStok());
            model.addAttribute("idRequestUpdateItem", idRequestUpdateItem);
            model.addAttribute("item", item);
            model.addAttribute("listMesin", listMesinByKategori);
            return "form-update-reqUpdateItem";
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Item dengan uuid " + uuid + " tidak ditemukan."
            );
        }
    }

    @PostMapping("/item/update-request")
    public String updateItemSubmit(
            @ModelAttribute ItemModel item,
            @RequestParam("jumlahStokDitambahkan") Integer jumlahStokDitambahkan,
            @RequestParam("mesin") MesinModel mesin,
            String username,
            Long idRequestUpdateItem,
            Model model
    ) {
        PegawaiModel staf = pegawaiService.getPegawaiByUsername(username);
//        System.out.println("jumlah stok total = " + jumlahStokDitambahkan);
        System.out.println("ID REQ = " + idRequestUpdateItem);

        // do update
        requestUpdateItemRestService.updateReqItem(item, jumlahStokDitambahkan, mesin, username, idRequestUpdateItem);

        // set executed == TRUE
        requestUpdateItemService.getRequestUpdateItemByIdRequestUpdateItem(idRequestUpdateItem).setExecuted(true);

        RequestUpdateItemModel reqUpdateItem = requestUpdateItemService.getRequestUpdateItemByIdRequestUpdateItem(idRequestUpdateItem);
        // set Produksi to req update item
        List<ProduksiModel> listProduksi = produksiService.getListOfProduksi();
        List<Long> ListOfIdProduksi = new ArrayList<>();
        for (ProduksiModel p:listProduksi) {
            ListOfIdProduksi.add(p.getIdProduksi());
        }
        Long max = Collections.max(ListOfIdProduksi);
        System.out.println("id produksi " + max);
        reqUpdateItem.setProduksi(produksiService.getProduksiByIdProduksi(max));

//        for (ProduksiModel prod: listProduksi) {
//            System.out.println("id produksi di list = " + prod.getIdProduksi());
////            System.out.println(prod.getRequestUpdateItem().getIdRequestUpdateItem());
//            if (!prod.getRequestUpdateItem().getIdRequestUpdateItem().equals(null)) {
//                System.out.println("id req update di list = " + prod.getRequestUpdateItem().getIdRequestUpdateItem());
//                if (prod.getRequestUpdateItem().getIdRequestUpdateItem() == idRequestUpdateItem) {
//                    reqUpdateItem.setProduksi(prod);
//                }
//            }
//        }

        // add counter pegawai
        pegawaiService.addCounter(staf);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "")
                .replace("]","");
        model.addAttribute("role", role);
        model.addAttribute("jumlahStokDitambahkan", jumlahStokDitambahkan);
        model.addAttribute("item", item);
        model.addAttribute("mesin", mesin);
        return "update-item";
    }
}
