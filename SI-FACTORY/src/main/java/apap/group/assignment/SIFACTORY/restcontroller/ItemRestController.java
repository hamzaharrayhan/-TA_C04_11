package apap.group.assignment.SIFACTORY.restcontroller;

import apap.group.assignment.SIFACTORY.rest.ItemModel;
import apap.group.assignment.SIFACTORY.service.ItemRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ItemRestController {

    @Autowired
    private ItemRestService itemRestService;

    @PostMapping(value = "/propose-item")
    private ResponseEntity proposeItem(@Valid @RequestBody ItemModel item, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        }else{
            itemRestService.proposeItem(item);
            return ResponseEntity.ok("Propose item success");
        }
    }
}
