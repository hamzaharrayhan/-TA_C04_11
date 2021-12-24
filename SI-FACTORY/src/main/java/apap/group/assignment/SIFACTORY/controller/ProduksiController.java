package apap.group.assignment.SIFACTORY.controller;

import apap.group.assignment.SIFACTORY.model.ProduksiModel;
import apap.group.assignment.SIFACTORY.service.ProduksiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/produksi")
public class ProduksiController {

    @Autowired
    ProduksiService produksiService;

    @GetMapping("/viewall")
    public String listProduksi(Model model){
        List<ProduksiModel> listProduksi = produksiService.getListProduksiByCheckItem();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "")
                .replace("]","");
        model.addAttribute("role", role);
        model.addAttribute("listProduksi", listProduksi);

        return "viewall-produksi";
    }
}
