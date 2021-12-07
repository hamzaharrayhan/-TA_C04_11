package apap.group.assignment.SIFACTORY.controller;

import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.service.RequestUpdateItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RequestUpdateItemController {

    @Qualifier("requestUpdateItemServiceImpl")
    @Autowired
    private RequestUpdateItemService requestUpdateItemService;

    @GetMapping("/request-update-item/viewall")
    public String listRequestUpdateItem(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "").replace("]","");

        List<RequestUpdateItemModel> listRequestUpdateItem = requestUpdateItemService.getRequestUpdateItemList();

        model.addAttribute("role", role);
        model.addAttribute("listRequestUpdateItem", listRequestUpdateItem);
        model.addAttribute("activePage", "viewallRequest");

        //Return view template yang diinginkan
        return "viewall-request-update-item";
    }
}
