package me.nextgeneric.telegram.editable;

public interface EditableDocument extends AutoCloseable {

    void open();

    void write(String text);

    void writeBold(String text);

    void writeImage(byte[] media);

    void writeSeparator();

    void close();

}
