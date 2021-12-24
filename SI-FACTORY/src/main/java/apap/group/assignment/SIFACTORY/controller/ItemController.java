package apap.group.assignment.SIFACTORY.controller;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.model.ProduksiModel;
import apap.group.assignment.SIFACTORY.repository.MesinDB;
import apap.group.assignment.SIFACTORY.rest.ItemDetail;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.service.ItemRestService;
import apap.group.assignment.SIFACTORY.service.MesinService;
import apap.group.assignment.SIFACTORY.service.PegawaiService;
import apap.group.assignment.SIFACTORY.service.ProduksiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private PegawaiService pegawaiService;

    @Autowired
    private ItemRestService itemRestService;

    @Autowired
    private MesinService mesinService;

    @Autowired
    private MesinDB mesinDB;

    @Autowired
    private ProduksiService produksiService;

    @RequestMapping(value = "/propose-item", method = RequestMethod.GET)
    public String purposeItemForm(
            Model model
    ) {
        List<MesinModel> listMesin = mesinDB.findAll();
        List<Integer> listKategori = new ArrayList<>();
        for (MesinModel mesin : listMesin){
            if (!listKategori.contains(mesin.getIdKategori()) && mesin.getKapasitas() > 0){
                listKategori.add(mesin.getIdKategori());
            }
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "")
                .replace("]","");
        model.addAttribute("role", role);
        model.addAttribute("listKategori", listKategori);
        model.addAttribute("activePage", "propose");
        return "form-propose-item";
    }

    @RequestMapping(value = "/propose-item", method = RequestMethod.POST)
    public String purposeItemSubmit(
            @ModelAttribute ItemDetail item, String username, 
            Model model,
            RedirectAttributes redirect
    ) {
        PegawaiModel admin = pegawaiService.getPegawaiByUsername(username);
        itemRestService.proposeItem(item);
        pegawaiService.addCounter(admin);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "")
                .replace("]","");
        model.addAttribute("role", role);
        model.addAttribute("nama", item.getName());
        model.addAttribute("harga", item.getPrice());
        model.addAttribute("stok", item.getStock());
        model.addAttribute("id", item.getCategory());
        return "proposed-item";
    }

    @GetMapping("/viewall")
    public String listItem(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().replace("[", "").replace("]", "");
        HashMap<String, List<ItemModel>> itemHashMap = itemRestService.retrieveListItem();
        model.addAttribute("role", role);
        model.addAttribute("itemHashMap", itemHashMap);

        return "viewall-item";
    }

    @GetMapping("/update/{uuid}")
    public String updateItemForm (
            @PathVariable("uuid") String uuid, Model model){
        ItemModel item = itemRestService.getItemByUuid(uuid);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "")
                .replace("]","");
        model.addAttribute("role", role);
        if (item != null) {
            List<MesinModel> listMesin = mesinService.getListMesinByKategori(item);
            model.addAttribute("item", item);
            model.addAttribute("listMesin", listMesin);
            return "form-update-item";
        }
        model.addAttribute("uuid", uuid);
        return "error-update-item";
    }

    @PostMapping("/update")
    public String updateItemSubmit(
            @ModelAttribute ItemModel item,
            @RequestParam("jumlahStokDitambahkan") Integer jumlahStokDitambahkan,
            @RequestParam("mesin") MesinModel mesin,
            Model model
    ) {
        itemRestService.updateItem(item, jumlahStokDitambahkan, mesin);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "")
                .replace("]","");
        model.addAttribute("role", role);
        model.addAttribute("jumlahStokDitambahkan", jumlahStokDitambahkan);
        model.addAttribute("item", item);
        model.addAttribute("mesin", mesin);
        return "update-item";
    }

    @GetMapping("/view/{uuid}")
    public String viewDetailItem(Model model, @PathVariable String uuid) {
        List<ProduksiModel> listProduksiByItem = new ArrayList<>();
        ItemModel item = itemRestService.getItemByUuid(uuid);
        List<ProduksiModel> listProduksi = produksiService.getListOfProduksi();

        for (ProduksiModel produksi : listProduksi) {
            if (produksi.getIdItem().equals(item.getUuid())) {
                listProduksiByItem.add(produksi);
            }
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "")
                .replace("]","");
        model.addAttribute("role", role);
        model.addAttribute("item", item);
        model.addAttribute("listProduksiByItem", listProduksiByItem);
        return "view-detail-item";
    }
}
