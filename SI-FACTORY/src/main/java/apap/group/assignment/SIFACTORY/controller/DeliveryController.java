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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @GetMapping(value = "/kirim/{id}")
    public String listAlamatDelivery(Model model, @PathVariable("id") Long idDelivery) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().replace("[", "").replace("]", "");
        PegawaiModel kurir = pegawaiService.getPegawaiByUsername(auth.getName());
        DeliveryModel delivery = deliveryDB.findByIdDelivery(idDelivery);
        model.addAttribute("role", role);
        java.util.Date date = new java.util.Date();

        try {
            Map<Long,String> alamat = deliveryService.alamatCabang();
            System.out.println(alamat);

            if (!alamat.get(delivery.getIdCabang()).isEmpty()){
                String message = "Pengiriman ke alamat " + alamat.get(delivery.getIdCabang()) + " berhasil!";
                model.addAttribute("message", message);
                pegawaiService.addCounter(kurir);
                delivery.setTanggalDikirim(date);
                delivery.setSent(true);
                deliveryDB.save(delivery);
                return "status-pengiriman";
            } else {
                String message = "Alamat untuk cabang tersebut tidak ditemukan. Pengiriman gagal!";
                model.addAttribute("message", message);
                return "status-pengiriman";
            }
        } catch (NullPointerException e){
            String message = "Alamat untuk cabang tersebut tidak ditemukan. Pengiriman gagal!";
            model.addAttribute("message", message);
            return "status-pengiriman";
        }
    }
}