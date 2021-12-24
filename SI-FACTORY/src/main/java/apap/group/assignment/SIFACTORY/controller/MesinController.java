package apap.group.assignment.SIFACTORY.controller;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.service.MesinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MesinController {

    @Qualifier("mesinServiceImpl")
    @Autowired
    private MesinService mesinService;

    @GetMapping("/mesin/viewall")
    public String listMesin(Model model){
        List<MesinModel> listMesin = mesinService.getMesinList();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "")
                .replace("]","");
        model.addAttribute("role", role);
        model.addAttribute("listMesin", listMesin);

        return "viewall-mesin";
    }
}
