package me.riguron.telegram.validate;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PhoneNumberTransformerTest {

    private final PhoneNumberFormatter phoneNumberTransformer = new PhoneNumberFormatter();

    @Test
    public void whenTransformPopularInputsThenFormatted() {

        assertEquals("+79696212207", phoneNumberTransformer.transform("+79696212207"));
        assertEquals("+79696212207", phoneNumberTransformer.transform("89696212207"));
        assertEquals("+79696212207", phoneNumberTransformer.transform("79696212207"));
    }

    @Test
    public void whenWithBracketsThenFormatted() {

        assertEquals("+79696212207", phoneNumberTransformer.transform("+7(969)6212207"));
        assertEquals("+79696212207", phoneNumberTransformer.transform(" 8-969-621-22-07"));
        assertEquals("+79696212207", phoneNumberTransformer.transform(" 7 (969) 621 22 07"));

    }
}