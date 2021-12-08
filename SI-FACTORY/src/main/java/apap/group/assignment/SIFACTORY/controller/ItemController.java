package apap.group.assignment.SIFACTORY.controller;

import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.service.ItemRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemRestService itemRestService;

    @GetMapping("/viewall")
    public String listItem(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "").replace("]", "");
        HashMap<String, List<ItemModel>> itemHashMap = itemRestService.retrieveListItem();

        model.addAttribute("role", role);
        model.addAttribute("itemHashMap", itemHashMap);

        return "viewall-item";
    }

}