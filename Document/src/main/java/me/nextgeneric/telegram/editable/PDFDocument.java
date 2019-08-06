package me.nextgeneric.telegram.editable;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFDocument implements EditableDocument {

    private static final Font FONT = FontFactory.getFont("Verdana.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    private static final Font FONT_BOLD = FontFactory.getFont("Verdana.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, FONT.getSize(), Font.BOLD);
    private static final Chunk SEPARATOR = new Chunk(new CustomDashedLineSeparator());

    private final File file;
    private final Document document;

    public PDFDocument(File file) {
        this.document = new Document();
        this.file = file;
    }

    @Override
    public void open() {
        try {
            PdfWriter.getInstance(this.document, new FileOutputStream(this.file)).setStrictImageSequence(true);
            document.open();
        } catch (DocumentException | FileNotFoundException e) {
            throw new IllegalStateException("Failed to open file", e);
        }
    }

    @Override
    public void write(String text) {
        add(new Paragraph(text, FONT));
    }

    @Override
    public void writeBold(String text) {
        add(new Paragraph(text, FONT_BOLD));
    }

    @Override
    public void writeImage(byte[] image) {
        try {
            Image img = Image.getInstance(image);
            img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            add(img);
        } catch (BadElementException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void writeSeparator() {
        add(SEPARATOR);
    }

    @Override
    public void close() {
        document.close();
    }

    private void add(Element element) {
        try {
            document.add(element);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private static class CustomDashedLineSeparator extends DottedLineSeparator {

        private static final float DASH = 10;
        private static final float PHASE = 2.5F;

        private static final int GAP = 7;
        private static final float LINE_WIDTH = 3;

        private CustomDashedLineSeparator() {
            setGap(GAP);
            setLineWidth(LINE_WIDTH);
        }

        @Override
        public void draw(PdfContentByte canvas, float llx, float lly, float urx, float ury, float y) {
            canvas.saveState();
            canvas.setLineWidth(lineWidth);
            canvas.setLineDash(DASH, gap, PHASE);
            drawLine(canvas, llx, urx, y);
            canvas.restoreState();
        }
    }

}
