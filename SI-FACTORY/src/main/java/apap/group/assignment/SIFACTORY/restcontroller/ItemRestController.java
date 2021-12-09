package apap.group.assignment.SIFACTORY.restcontroller;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.rest.ItemDetail;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.service.ItemRestService;
import apap.group.assignment.SIFACTORY.service.MesinRestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class ItemRestController {

    @Autowired
    private ItemRestService itemRestService;

    @Autowired
    private MesinRestService mesinRestService;

    @GetMapping(value = "/propose-item")
    private Mono<String> proposeItem(@ModelAttribute ItemDetail item){
        return itemRestService.proposeItem(item);
    }

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
