package apap.group.assignment.SIFACTORY.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/")
    public String home(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString().toString().replace("[", "").replace("]","");
//        System.out.println("rolenya "+role);
        model.addAttribute("role", role);
        return "home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login-apap";
    }
}
