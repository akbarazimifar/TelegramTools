package me.nextgeneric.telegram.confirm.action;

import org.springframework.ui.Model;

import java.util.Map;

public interface ConfirmationAction {

    String execute(Model model, Map<String, String> allRequestParams);
}
