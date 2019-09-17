package me.riguron.telegram.confirm.action;

import me.riguron.telegram.confirm.ConfirmationController;
import org.springframework.ui.Model;

import java.util.Map;

import static me.riguron.telegram.confirm.ConfirmationController.CONFIRM;

public enum ConfirmationNoOpAction implements ConfirmationAction {

    INSTANCE;

    @Override
    public String execute(Model model, Map<String, String> allRequestParams) {
        model.addAttribute("error", "Unknown action");
        return ConfirmationController.CONFIRM;
    }
}
