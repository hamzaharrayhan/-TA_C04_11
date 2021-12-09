package apap.group.assignment.SIFACTORY.restcontroller;

import apap.group.assignment.SIFACTORY.rest.ItemDetail;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.service.ItemRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ItemRestController {

    @Autowired
    private ItemRestService itemRestService;

    @GetMapping(value = "/propose-item")
    private Mono<String> proposeItem(@ModelAttribute ItemDetail item){
        return itemRestService.proposeItem(item);
    }
}
