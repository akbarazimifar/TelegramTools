package me.riguron.telegram;

import me.riguron.telegram.editable.EditableDocument;
import me.riguron.telegram.editable.PDFDocument;
import me.riguron.telegram.editable.WordDocument;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;

@Component
public class EditableDocumentFactory {

    public Optional<EditableDocument> createDocument(DocumentType type, File target) {
        EditableDocument result;
        switch (type) {
            case WORD:
                result = new WordDocument(target);
                break;
            case PDF:
                result = new PDFDocument(target);
                break;
            default:
                return Optional.empty();
        }
        return Optional.of(result);
    }
}
