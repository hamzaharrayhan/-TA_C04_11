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
        ItemModel item = itemRestService.getItemByUuid(reqUpdateItem.getIdItem());
        // filter mesin berdasarkan kategori
        for (MesinModel mesin: listMesin) {
            if (mesin.getIdKategori() == reqUpdateItem.getIdKategori() && mesin.getKapasitas() > 0) {
                listMesinByKategori.add(mesin);
            }
        }
        for (MesinModel m: listMesinByKategori) {
            System.out.println(m.getNama());
        }
//        RequestUpdateItemModel reqUpdateItem = requestUpdateItemRestService.getRequestUpdateItemByIdRequestUpdateItem(idRequestUpdateItem);
        model.addAttribute("stokTambahan", reqUpdateItem.getTambahanStok());
        model.addAttribute("item", item);
        model.addAttribute("listMesin", listMesinByKategori);
        return "form-update-reqUpdateItem";
    }

    @PostMapping("/item/update-request")
    public String updateItemSubmit(
            @ModelAttribute RequestUpdateItemModel requestUpdateItem,
            String username,
            Integer mesin,
            Model model
    ) {
        // update stok di API & service biasa
        String uuid = requestUpdateItem.getIdItem();
        Integer stok = requestUpdateItem.getTambahanStok();
        Long idRequestUpdateItem = requestUpdateItem.getIdRequestUpdateItem();
        PegawaiModel staf = pegawaiService.getPegawaiByUsername(username);
        requestUpdateItemRestService.updateItem(uuid, stok, idRequestUpdateItem, staf, mesin);

        // set executed == TRUE
        requestUpdateItem.setExecuted(true);

        // add counter pegawai
        pegawaiService.addCounter(staf);

//        model.addAttribute("noBioskop", bioskop.getNoBioskop());
        return "update-bioskop";
    }
}
