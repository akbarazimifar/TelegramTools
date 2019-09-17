package me.riguron.telegram.confirm.action;

import me.riguron.telegram.confirm.ConfirmationService;
import me.riguron.telegram.user.UserSession;
import me.riguron.telegram.confirm.ConfirmationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Map;

import static me.riguron.telegram.confirm.ConfirmationController.CONFIRM;

@Component
public class ConfirmationSendAction implements ConfirmationAction {

    private ConfirmationService confirmationService;

    private UserSession userSession;

    @Autowired
    public ConfirmationSendAction(ConfirmationService confirmationService, UserSession userSession) {
        this.confirmationService = confirmationService;
        this.userSession = userSession;
    }

    @Override
    public String execute(Model model, Map<String, String> allRequestParams) {
        boolean codeSent = confirmationService.sendCode(userSession);
        if (codeSent) {
            model.addAttribute("info", "Code sent");
        } else {
            model.addAttribute("error", "Failed to send code");
        }
        return ConfirmationController.CONFIRM;
    }

}
