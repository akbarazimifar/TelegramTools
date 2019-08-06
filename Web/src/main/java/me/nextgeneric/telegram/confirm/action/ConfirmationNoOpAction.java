package me.nextgeneric.telegram.confirm.action;

import org.springframework.ui.Model;

import java.util.Map;

import static me.nextgeneric.telegram.confirm.ConfirmationController.CONFIRM;

public enum ConfirmationNoOpAction implements ConfirmationAction {

    INSTANCE;

    @Override
    public String execute(Model model, Map<String, String> allRequestParams) {
        model.addAttribute("error", "Unknown action");
        return CONFIRM;
    }
}
