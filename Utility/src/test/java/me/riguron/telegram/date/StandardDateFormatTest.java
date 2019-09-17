package me.riguron.telegram.date;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class StandardDateFormatTest {

    private static final long TIMESTAMP = 1555338611645L;

    @Test
    public void format() {
        DateFormat dateFormat = new StandardDateFormat();
        String format = dateFormat.format(TIMESTAMP, Locale.forLanguageTag("ru"));
        assertEquals("Понедельник, 15 апреля 2019, 17:30", format);
    }
}