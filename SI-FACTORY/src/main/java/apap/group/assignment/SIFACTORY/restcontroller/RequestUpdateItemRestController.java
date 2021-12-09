package apap.group.assignment.SIFACTORY.restcontroller;

import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.service.RequestUpdateItemRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/api/v1")
public class RequestUpdateItemRestController {
    @Autowired
    private RequestUpdateItemRestService requestUpdateItemRestService;

    @PostMapping("/request-update-item")
    private RequestUpdateItemModel createRequestUpdateItemModel(@Valid @RequestBody RequestUpdateItemModel data, BindingResult bindingResult) {
        return requestUpdateItemRestService.addRequestUpdateItem(data);
    }
}
