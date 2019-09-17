package me.riguron.telegram.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class DumpRequestDto {

    @NotBlank(message = "{channel.name.blank}")
    private final String channelName;

    @NotBlank
    private final String documentType;

    private final boolean images;
    private final boolean loadOnlyNew;

}
