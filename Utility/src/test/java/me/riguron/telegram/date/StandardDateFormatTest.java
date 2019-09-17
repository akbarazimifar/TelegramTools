package me.riguron.telegram.date;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertTrue;

public class StandardDateFormatTest {

    private static final long TIMESTAMP = 1555338611645L;

    @Test
    public void format() {
        DateFormat dateFormat = new StandardDateFormat();
        String format = dateFormat.format(TIMESTAMP, Locale.forLanguageTag("ru"));
        String expected = "Понедельник, 15 апреля 2019, 1%d:30";
        assertTrue(format.equalsIgnoreCase(String.format(expected, 4)) || format.equalsIgnoreCase(String.format(expected, 7)));
    }
}