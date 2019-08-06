package me.nextgeneric.telegram.validate;

import org.springframework.stereotype.Component;

// Russian numbers are the only currently supported. (+7)
@Component
public class PhoneNumberFormatter {

    public String transform(String phoneNumber) {
        String numberString = phoneNumber.replaceAll("[^\\d]", "");

        if (numberString.charAt(0) == '8') {
            numberString = '7' + numberString.substring(1);
        }

        return '+' + numberString;
    }
}
