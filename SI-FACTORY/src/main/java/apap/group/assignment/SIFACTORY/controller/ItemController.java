package apap.group.assignment.SIFACTORY.controller;

import apap.group.assignment.SIFACTORY.repository.MesinDB;
import apap.group.assignment.SIFACTORY.rest.ItemDetail;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.service.ItemRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemRestService itemRestService;

    @Autowired
    private MesinDB mesinDB;

    @GetMapping(value = "/propose-item")
    public String purposeItemForm(
            Model model
    ) {
        model.addAttribute("listKategori", mesinDB.findAll());
        return "form-propose-item";
    }

    @PostMapping(value = "/api/v1/propose-item")
    public String purposeItemSubmit(
            @ModelAttribute ItemDetail item,
            RedirectAttributes redirect
    ) {
        itemRestService.proposeItem(item);
        return "redirect:/";
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