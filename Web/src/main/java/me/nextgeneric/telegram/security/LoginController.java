package me.nextgeneric.telegram.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loadLoginPage(String error, Model model, @ModelAttribute("failureHandlerError") String failureHandlerError) {

        if (error != null) {
            model.addAttribute("error", "Failed to login");
        }

        if (!failureHandlerError.isEmpty()) {
            model.addAttribute("error", failureHandlerError);
        }

        return "login";
    }


}

