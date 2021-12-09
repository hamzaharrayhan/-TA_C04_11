package apap.group.assignment.SIFACTORY.controller;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.repository.MesinDB;
import apap.group.assignment.SIFACTORY.rest.ItemDetail;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.service.ItemRestService;
import apap.group.assignment.SIFACTORY.service.PegawaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    private MesinDB mesinDB;

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
        model.addAttribute("listKategori", listKategori);
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
        model.addAttribute("nama", item.getName());
        model.addAttribute("harga", item.getPrice());
        model.addAttribute("stok", item.getStock());
        model.addAttribute("id", item.getCategory());

        return "proposed-item";
    }

    @GetMapping("/viewall")
    public String listItem(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "").replace("]", "");
        HashMap<String, List<ItemModel>> itemHashMap = itemRestService.retrieveListItem();

        System.out.println("map: " + itemHashMap);
        System.out.println("role: " + role);

        for (String name: itemHashMap.keySet()) {
            String key = name.toString();
            List value = itemHashMap.get(name);
            System.out.println(key + " " + value.toString());
        }

        model.addAttribute("role", role);
        model.addAttribute("itemHashMap", itemHashMap);

        return "viewall-item";
    }
}