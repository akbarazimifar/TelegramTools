package me.nextgeneric.telegram.dump;

import lombok.*;
import me.nextgeneric.telegram.DocumentType;

import java.util.Locale;

@Value
public class DumpRequest {

    private final String channelName;
    private final DocumentType documentType;
    private final boolean images;
    private final boolean loadOnlyNew;
    private final Locale locale;



}
