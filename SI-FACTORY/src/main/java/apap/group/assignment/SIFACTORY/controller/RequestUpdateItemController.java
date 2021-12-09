package apap.group.assignment.SIFACTORY.controller;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/request-update-item/viewall")
    public String listRequestUpdateItem(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "").replace("]","");

        List<RequestUpdateItemModel> listRequestUpdateItem = requestUpdateItemService.getRequestUpdateItemList();

        model.addAttribute("role", role);
        model.addAttribute("listRequestUpdateItem", listRequestUpdateItem);
        model.addAttribute("activePage", "viewallRequest");

        //Return view template yang diinginkan
        return "viewall-request-update-item";
    }

    @GetMapping("/item/update-request/{idRequestUpdateItem}")
    public String updateItemForm(
            @PathVariable Long idRequestUpdateItem,
            Model model
    ) {
        List<MesinModel> listMesin = mesinService.getMesinList();
        List<MesinModel> listMesinByKategori = new ArrayList<>();
        RequestUpdateItemModel reqUpdateItem = requestUpdateItemService.getRequestUpdateItemByIdRequestUpdateItem(idRequestUpdateItem);
        System.out.println(reqUpdateItem.getIdKategori());
        ItemModel item = itemRestService.getItemByUuid(reqUpdateItem.getIdItem());
        // filter mesin berdasarkan kategori
        for (MesinModel mesin: listMesin) {
            if (mesin.getIdKategori() == reqUpdateItem.getIdKategori() && mesin.getKapasitas() > 0) {
                listMesinByKategori.add(mesin);
                System.out.println(mesin.getNama());
            }
        }
//        for (MesinModel m: listMesinByKategori) {
//            System.out.println(m.getNama());
//        }
//        RequestUpdateItemModel reqUpdateItem = requestUpdateItemRestService.getRequestUpdateItemByIdRequestUpdateItem(idRequestUpdateItem);
        model.addAttribute("stokTambahan", reqUpdateItem.getTambahanStok());
        model.addAttribute("kategori", reqUpdateItem.getIdKategori());
        model.addAttribute("idRequestUpdateItem", reqUpdateItem.getIdRequestUpdateItem());
        model.addAttribute("uuid", item.getUuid());
        model.addAttribute("item", item);
        model.addAttribute("listMesin", listMesinByKategori);
        return "form-update-reqUpdateItem";
    }

    @PostMapping("/item/update-request")
    public String updateItemSubmit(
            @ModelAttribute ItemModel item,
            String uuid,
            String username,
            Integer mesin,
            Integer stokTambahan,
            Integer kategori,
            Long idRequestUpdateItem,
            Model model
    ) {
        PegawaiModel staf = pegawaiService.getPegawaiByUsername(username);
//        System.out.println("iditem = " + uuid);
//        System.out.println("stok = " + stokTambahan);
//        System.out.println("kategori = " + kategori);
        // do update
        requestUpdateItemRestService.updateItem(uuid, stokTambahan, idRequestUpdateItem, staf, mesin, kategori);

        // set executed == TRUE
        requestUpdateItemService.getRequestUpdateItemByIdRequestUpdateItem(idRequestUpdateItem).setExecuted(true);

        // add counter pegawai
        pegawaiService.addCounter(staf);

        model.addAttribute("uuid", uuid);
        return "update-req-update-item";
    }
}
