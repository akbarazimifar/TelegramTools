package me.nextgeneric.telegram;

public enum DocumentType {

    PDF("pdf", "PDF"), WORD("docx", "Microsoft Word");

    private String extension;
    private String description;

    DocumentType(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }

    public String getExtension() {
        return extension;
    }

    public String getDescription() {
        return description;
    }
}
