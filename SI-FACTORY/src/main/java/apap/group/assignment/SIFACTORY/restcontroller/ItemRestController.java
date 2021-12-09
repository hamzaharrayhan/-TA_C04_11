package apap.group.assignment.SIFACTORY.restcontroller;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.service.ItemRestService;
import apap.group.assignment.SIFACTORY.service.MesinRestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class ItemRestController {

    @Autowired
    private ItemRestService itemRestService;

    @Autowired
    private MesinRestService mesinRestService;
//
//    @PostMapping(value = "/propose-item")
//    private ResponseEntity proposeItem(@Valid @RequestBody ItemModel item, BindingResult bindingResult){
//        if(bindingResult.hasFieldErrors()) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
//            );
//        }else{
//            itemRestService.proposeItem(item);
//            return ResponseEntity.ok("Propose item success");
//        }
//    }

    @PutMapping(value = "/update/{uuid}")
    private ItemModel updateItem (@PathVariable("uuid") String uuid,
                                  @RequestParam("jumlahStokDitambahkan") Integer jumlahStokDitambahkan,
                                  @RequestBody ItemModel item){
        try{
            String kategori = item.getKategori();
            Integer idKategori = mesinRestService.getIdKategoriByKategori(kategori);
            List<MesinModel> mesin = mesinRestService.getMesinByIdKategori(idKategori);

            return itemRestService.updateItem(item, jumlahStokDitambahkan);

        } catch (NoSuchElementException | JsonProcessingException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Item dengan uuid " + uuid + " tidak ditemukan."
            );
        }
    }
}
