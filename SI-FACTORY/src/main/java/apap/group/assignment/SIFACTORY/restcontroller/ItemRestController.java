package apap.group.assignment.SIFACTORY.restcontroller;

import apap.group.assignment.SIFACTORY.rest.ItemDetail;
import apap.group.assignment.SIFACTORY.service.ItemRestService;
import apap.group.assignment.SIFACTORY.service.MesinRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
}
