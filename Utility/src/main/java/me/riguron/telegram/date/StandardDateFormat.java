package me.riguron.telegram.date;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.TimeZone;

@Component
public class StandardDateFormat implements DateFormat {

    private static final TextStyle TEXT_STYLE = TextStyle.FULL;

    @Override
    public String format(long date, Locale locale) {
        LocalDateTime triggerTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), TimeZone.getDefault().toZoneId());
        DayOfWeek dayOfWeek = triggerTime.getDayOfWeek();
        Month month = triggerTime.getMonth();
        String dayString = dayOfWeek.getDisplayName(TEXT_STYLE, locale);

        return String.format("%s, %d %s %d, %s:%s",
                dayString.substring(0, 1).toUpperCase() + dayString.substring(1),
                triggerTime.getDayOfMonth(),
                month.getDisplayName(TEXT_STYLE, locale),
                triggerTime.getYear(),
                formatTime(triggerTime.getHour()),
                formatTime(triggerTime.getMinute())
        );
    }

    private String formatTime(int time) {
        return time < 10 ? "0" + time : String.valueOf(time);
    }
}
