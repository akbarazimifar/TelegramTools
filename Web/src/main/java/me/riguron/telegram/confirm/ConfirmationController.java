package me.riguron.telegram.confirm;

import me.riguron.telegram.confirm.action.ConfirmationAction;
import me.riguron.telegram.confirm.action.ConfirmationConfirmAction;
import me.riguron.telegram.confirm.action.ConfirmationNoOpAction;
import me.riguron.telegram.confirm.action.ConfirmationSendAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@PreAuthorize("hasRole('ROLE_UNCONFIRMED')")
public class ConfirmationController {

    public static final String CONFIRM = "confirm";

    private final Map<String, ConfirmationAction> actions = new HashMap<>();

    @Autowired
    public ConfirmationController(ConfirmationSendAction sendAction, ConfirmationConfirmAction confirmationConfirmAction) {
        actions.put("send", sendAction);
        actions.put(CONFIRM, confirmationConfirmAction);
    }

    @GetMapping("/confirm")
    public String loadConfirmation() {
        return CONFIRM;
    }

    @PostMapping("/confirm")
    public String response(String action, Model model, @RequestParam Map<String, String> allRequestParams) {
        String target = actions.getOrDefault(action, ConfirmationNoOpAction.INSTANCE).execute(model, allRequestParams);
        return CONFIRM.equals(target) ? target : "redirect:/" + target;
    }

}

