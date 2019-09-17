package me.riguron.telegram.confirm.action;

import me.riguron.telegram.SystemRoles;
import me.riguron.telegram.confirm.ConfirmationService;
import me.riguron.telegram.security.RoleAssistant;
import me.riguron.telegram.user.UserSession;
import me.riguron.telegram.SystemRoles;
import me.riguron.telegram.security.RoleAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Map;

@Component
public class ConfirmationConfirmAction implements ConfirmationAction {

    private RoleAssistant roleManager;

    private UserSession userSession;

    private ConfirmationService confirmationService;

    @Autowired
    public ConfirmationConfirmAction(RoleAssistant roleManager, UserSession userSession, ConfirmationService registrationService) {
        this.roleManager = roleManager;
        this.userSession = userSession;
        this.confirmationService = registrationService;
    }

    @Override
    public String execute(Model model, Map<String, String> allRequestParams) {

        String codeParam = allRequestParams.get("code");

        if (codeParam == null || codeParam.isEmpty() || !codeParam.chars().allMatch(Character::isDigit)) {
            return error(model, "Please provide a valid code");
        }

        int confirmedUserId = confirmationService.confirm(userSession, Integer.parseInt(codeParam));

        if (confirmedUserId <= 0) {
            return error(model, "Failed to authenticate. Is the code correct?");
        }

        userSession.setUserId(confirmedUserId);
        roleManager.set(SystemRoles.ROLE_FULL);
        return "application";

    }

    private String error(Model model, String error) {
        model.addAttribute("error", error);
        return "confirm";
    }


}
