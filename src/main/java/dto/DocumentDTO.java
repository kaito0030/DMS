package dto;

import java.io.Serializable;

public class DocumentDTO implements Serializable {

    private String documentId;
    private String title;
    private String author;
    private int publishYear;
    private String abstractText;
    private String pdfPath;

    public DocumentDTO(
            String documentId,
            String title,
            String author,
            int publishYear,
            String abstractText,
            String pdfPath) {

        this.documentId = documentId;
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.abstractText = abstractText;
        this.pdfPath = pdfPath;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public String getPdfPath() {
        return pdfPath;
    }
}