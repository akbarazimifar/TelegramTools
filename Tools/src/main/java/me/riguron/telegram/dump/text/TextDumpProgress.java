package me.riguron.telegram.dump.text;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@AllArgsConstructor
@Data
@Setter(AccessLevel.NONE)
public class TextDumpProgress {

    private final int dumped;
    private final int total;

}
