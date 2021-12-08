package apap.group.assignment.SIFACTORY.controller;

import apap.group.assignment.SIFACTORY.model.DeliveryModel;
import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.repository.DeliveryDB;
import apap.group.assignment.SIFACTORY.repository.PegawaiDB;
import apap.group.assignment.SIFACTORY.service.DeliveryService;
import apap.group.assignment.SIFACTORY.service.PegawaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private PegawaiService pegawaiService;
    @Autowired
    private DeliveryDB deliveryDB;

    @GetMapping("/list-delivery")
    public String listDelivery(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "").replace("]", "");
        List<DeliveryModel> listDeliveryOp = deliveryService.getListOfDelivery();
        model.addAttribute("role", role);

        Long pegawai = pegawaiService.getPegawaiByUsername(auth.getName()).getIdPegawai();
        if (role.equalsIgnoreCase("STAFF_KURIR")){
            System.out.println(pegawaiService.getPegawaiByUsername(auth.getName()).getIdPegawai().getClass());
            List<DeliveryModel> listDeliveryKu = deliveryDB.findAllByIdKurir(pegawai);
            model.addAttribute("listDelivery", listDeliveryKu);
            return "viewall-delivery";
        }
        model.addAttribute("listDelivery", listDeliveryOp);
        return "viewall-delivery";
    }
}
