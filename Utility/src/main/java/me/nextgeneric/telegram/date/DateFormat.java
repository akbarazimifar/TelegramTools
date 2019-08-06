package me.nextgeneric.telegram.date;

import java.util.Locale;

public interface DateFormat {

    String format(long date, Locale locale);
}
