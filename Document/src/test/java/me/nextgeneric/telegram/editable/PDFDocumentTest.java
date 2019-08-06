package me.nextgeneric.telegram.editable;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PDFDocumentTest extends EditableDocumentTest {

    @Override
    String extension() {
        return "pdf";
    }

    @Override
    String readContent(FileInputStream fileInputStream) throws IOException {
        PdfReader pdfReader = new PdfReader(fileInputStream);
        try {
            return PdfTextExtractor.getTextFromPage(pdfReader, 1);
        } finally {
            pdfReader.close();
        }
    }

    @Override
    EditableDocument createDocument(File target) {
        return new PDFDocument(target);
    }
}