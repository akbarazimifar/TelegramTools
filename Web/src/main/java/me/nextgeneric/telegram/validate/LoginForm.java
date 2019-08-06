package me.nextgeneric.telegram.validate;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Value
public class LoginForm {

    @NotBlank
    @Pattern(regexp = "^(\\s*)?(\\+)?([- _():=+]?\\d[- _():=+]?){10,14}(\\s*)?$", message = "Please enter a valid phone number")
    private String login;

    @NotBlank(message = "Please specify password")
    private String password;
}
