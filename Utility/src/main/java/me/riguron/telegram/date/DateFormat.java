package me.riguron.telegram.date;

import java.util.Locale;

public interface DateFormat {

    String format(long date, Locale locale);
}
