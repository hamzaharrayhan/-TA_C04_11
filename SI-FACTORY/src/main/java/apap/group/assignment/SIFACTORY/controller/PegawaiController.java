package apap.group.assignment.SIFACTORY.controller;

import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.model.RoleModel;
import apap.group.assignment.SIFACTORY.service.PegawaiService;
import apap.group.assignment.SIFACTORY.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pegawai")
public class PegawaiController {

    @Autowired
    private PegawaiService pegawaiService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/viewall")
    public String listPegawai(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "").replace("]","");

        List<PegawaiModel> listPegawai = pegawaiService.getListOfPegawai();
        List<Integer> gaji = new ArrayList<>();
//      forloop untuk gaji
        int gajiTemp = 0;
        for (PegawaiModel pegawaiModel : listPegawai){
            if (pegawaiModel.getCounter() == null){
                pegawaiModel.setCounter(0);
                gajiTemp = 0;
                gaji.add(gajiTemp);
            }
            else {
                gajiTemp += pegawaiModel.getRole().getBaseWages() * pegawaiModel.getCounter();
                gaji.add(gajiTemp);
                gajiTemp = 0;
            }
            System.out.println(gaji);
        }

        model.addAttribute("role", role);
        model.addAttribute("listPegawai", listPegawai);
        model.addAttribute("gaji", gaji);
        model.addAttribute("activePage", "viewallPegawai");

        //Return view template yang diinginkan
        return "viewall-pegawai";
    }

    @GetMapping("/add")
    private String addPegawaiFormPage(Model model) {
        PegawaiModel pegawai = new PegawaiModel();
        List<RoleModel> listRole = roleService.getListRole();
        System.out.println(listRole);
        model.addAttribute("pegawai", pegawai);
        model.addAttribute("listRole", listRole);
        return "form-add-pegawai";
    }

    @PostMapping(value = "/add")
    private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, String username, Model model) {
        // System.out.println("admin dari parameter = " + username);
        String[] arrOfUsername = username.split(",", 5); // it'll be [usernameAdmin, addedPegawai]
        String usernameAdmin = (String) Array.get(arrOfUsername, 0);
        String usernameAddedPegawai = (String) Array.get(arrOfUsername, arrOfUsername.length - 1);
        PegawaiModel admin = pegawaiService.getPegawaiByUsername(usernameAdmin);
        List<PegawaiModel> listPegawai = pegawaiService.getListOfPegawai();
        for (PegawaiModel p : listPegawai) {
            if (usernameAddedPegawai.equals(p.getUsername())) {
                model.addAttribute("username", usernameAddedPegawai);
                return "error-delete-pegawai";
            }
        }
        pegawaiService.addCounter(admin);
        pegawaiService.addPegawai(pegawai);
        model.addAttribute("pegawai", pegawai);
        return "redirect:/";
    }

}
