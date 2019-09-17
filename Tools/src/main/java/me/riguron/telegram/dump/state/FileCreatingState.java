package me.riguron.telegram.dump.state;

import me.riguron.telegram.dump.text.TextDump;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a state while an output document is being created from fetched messages.
 */
public class FileCreatingState implements ChannelDumpState {

    private final TextDump pdfDumper;

    public FileCreatingState(TextDump pdfDumper) {
        this.pdfDumper = pdfDumper;
    }

    @Override
    public String errorDescription() {
        return "execution.error.file.creation";
    }

    @Override
    public String publicDescription() {
        return "execution.state.file_generation";
    }

    @Override
    public List<Object> getStateVariables() {
        return Arrays.asList(pdfDumper.getStatus().getDumped(), pdfDumper.getStatus().getTotal());
    }
}
