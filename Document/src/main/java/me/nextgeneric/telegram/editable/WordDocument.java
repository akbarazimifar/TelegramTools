package me.nextgeneric.telegram.editable;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class WordDocument implements EditableDocument {

    private XWPFDocument document;
    private final File target;

    public WordDocument(File target) {
        this.target = target;
    }

    @Override
    public void open() {
        this.document = new XWPFDocument();
    }

    @Override
    public void write(String text) {
        this.createTextRun(text, false);
    }

    @Override
    public void writeBold(String text) {
        this.createTextRun(text, true);
    }

    @Override
    public void writeImage(byte[] media) {
        XWPFRun run = createRun();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(media);
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(media));

            double w = img.getWidth();
            double h = img.getHeight();

            double scaling = 1.0;
            if (w > 72 * 6)
                scaling = (72 * 6) / w;

            run.addPicture(inputStream, XWPFDocument.PICTURE_TYPE_JPEG, "", Units.toEMU(w * scaling), Units.toEMU(h * scaling));
            run.addBreak();
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void writeSeparator() {
        document.createParagraph().createRun().addBreak();
    }

    @Override
    public void close() {
        try (OutputStream out = new FileOutputStream(target)) {
            document.write(out);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void createTextRun(String text, boolean bold) {
        XWPFRun run = createRun();
        run.setFontFamily("Verdana");
        run.setBold(bold);

        run.setFontSize(20);
        run.setText(text);
        run.addBreak();
    }

    private XWPFRun createRun() {
        XWPFParagraph paragraph = document.createParagraph();
        return paragraph.createRun();
    }
}
