package me.nextgeneric.telegram;

import me.nextgeneric.telegram.editable.EditableDocument;
import me.nextgeneric.telegram.editable.PDFDocument;
import me.nextgeneric.telegram.editable.WordDocument;
import org.junit.Test;

import java.io.File;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EditableDocumentFactoryTest {

    private final EditableDocumentFactory editableDocumentFactory = new EditableDocumentFactory();

    @Test
    public void doTestCreation() {
        assertDocumentCreated(DocumentType.PDF, PDFDocument.class);
        assertDocumentCreated(DocumentType.WORD, WordDocument.class);
    }

    private void assertDocumentCreated(DocumentType documentType, Class<? extends EditableDocument> expectedDocType) {
        Optional<EditableDocument> documentOptional = editableDocumentFactory.createDocument(documentType, new File("target"));
        assertTrue(documentOptional.isPresent());
        EditableDocument editableDocument = documentOptional.get();
        assertEquals(expectedDocType, editableDocument.getClass());
    }

}