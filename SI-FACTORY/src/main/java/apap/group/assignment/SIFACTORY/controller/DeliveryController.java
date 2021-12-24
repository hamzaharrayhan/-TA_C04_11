package apap.group.assignment.SIFACTORY.controller;

import apap.group.assignment.SIFACTORY.model.DeliveryModel;
import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.repository.DeliveryDB;
import apap.group.assignment.SIFACTORY.repository.PegawaiDB;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.service.DeliveryService;
import apap.group.assignment.SIFACTORY.service.ItemRestService;
import apap.group.assignment.SIFACTORY.service.PegawaiService;
import apap.group.assignment.SIFACTORY.service.RequestUpdateItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private PegawaiService pegawaiService;
    @Autowired
    private RequestUpdateItemService requestUpdateItemService;
    @Autowired
    private DeliveryDB deliveryDB;
    @Autowired
    private ItemRestService itemRestService;

    @GetMapping("/list-delivery")
    public String listDelivery(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "").replace("]", "");
        List<DeliveryModel> listDeliveryOp = deliveryService.getListOfDelivery();
        model.addAttribute("role", role);
        model.addAttribute("activePage", "delivery");

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
//            System.out.println(alamat);
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

    @GetMapping("/assign-kurir/{idRequestUpdateItem}/{uuid}")
    public String assignKurirForm(
            @PathVariable Long idRequestUpdateItem,
            @PathVariable("uuid") String uuid,
            Model model
    ){
        RequestUpdateItemModel reqUpdateItem = requestUpdateItemService.getRequestUpdateItemByIdRequestUpdateItem(idRequestUpdateItem);
        try {
            ItemModel item = itemRestService.getItemByUuid(uuid);
            List<PegawaiModel> listPegawai = pegawaiService.getListOfPegawai();
            List<PegawaiModel> listKurir = new ArrayList<>();
            for (PegawaiModel pegawai: listPegawai) {
                if (pegawai.getRole().getIdRole() == 2) {
                    listKurir.add(pegawai);
                }
            }
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String role = auth.getAuthorities().toString().toString().replace("[", "")
                    .replace("]","");
            model.addAttribute("role", role);
            model.addAttribute("reqUpdateItem", reqUpdateItem);
            model.addAttribute("item", item);
            model.addAttribute("idRequestUpdateItem", idRequestUpdateItem);
            model.addAttribute("listKurir", listKurir);
            return "form-assign-kurir";
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Item dengan uuid " + uuid + " tidak ditemukan."
            );
        }
    }

    @PostMapping("/assign-kurir")
    public String assignKurirSubmit(
            @ModelAttribute DeliveryModel delivery,
            @RequestParam("kurir") PegawaiModel kurir,
            String username,
            Long idRequestUpdateItem,
            Model model
    ){
        PegawaiModel pegawai = pegawaiService.getPegawaiByUsername(username);
        RequestUpdateItemModel reqUpdateItem = requestUpdateItemService.getRequestUpdateItemByIdRequestUpdateItem(idRequestUpdateItem);
        deliveryService.addDelivery(reqUpdateItem, pegawai, kurir);
        List<DeliveryModel> listDelivery = deliveryService.getListOfDelivery();
        for (DeliveryModel deliv: listDelivery) {
            if (deliv.getRequestUpdateItem().getIdRequestUpdateItem() == idRequestUpdateItem) {
                reqUpdateItem.setDelivery(deliv);
            }
        }
        pegawaiService.addCounter(pegawai);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "")
                .replace("]","");
        model.addAttribute("role", role);
        model.addAttribute("kurir", kurir);
        model.addAttribute("idRequestUpdateItem", idRequestUpdateItem);
        return "update-assign-kurir";
    }
}