package me.riguron.telegram.editable;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Collectors;

public class WordDocumentTest extends EditableDocumentTest {

    @Override
    String extension() {
        return "docx";
    }

    @Override
    String readContent(FileInputStream fileInputStream) throws IOException {
        try (XWPFDocument document = new XWPFDocument(fileInputStream)) {
            return document.getParagraphs().stream().map(XWPFParagraph::getText).collect(Collectors.joining(", "));
        }
    }

    @Override
    EditableDocument createDocument(File target) {
        return new WordDocument(target);
    }
}
