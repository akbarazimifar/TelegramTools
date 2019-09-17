package me.riguron.telegram.dump;

import lombok.*;
import me.riguron.telegram.DocumentType;
import me.riguron.telegram.DocumentType;

import java.util.Locale;

@Value
public class DumpRequest {

    private final String channelName;
    private final DocumentType documentType;
    private final boolean images;
    private final boolean loadOnlyNew;
    private final Locale locale;



}
