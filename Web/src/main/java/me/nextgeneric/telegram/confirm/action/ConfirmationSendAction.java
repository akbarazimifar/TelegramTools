package me.nextgeneric.telegram.confirm.action;

import me.nextgeneric.telegram.confirm.ConfirmationService;
import me.nextgeneric.telegram.user.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Map;

import static me.nextgeneric.telegram.confirm.ConfirmationController.CONFIRM;

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
        return CONFIRM;
    }

}
