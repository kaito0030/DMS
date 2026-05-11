package dto;

import java.io.Serializable;

public class HistoryDTO implements Serializable {

    private String userName;
    private String realName;
    private String documentId;
    private String title;
    private String author;
    private String viewedAt;

    public HistoryDTO(
            String userName,
            String realName,
            String documentId,
            String title,
            String author,
            String viewedAt) {

        this.userName = userName;
        this.realName = realName;
        this.documentId = documentId;
        this.title = title;
        this.author = author;
        this.viewedAt = viewedAt;
    }

    public String getUserName() {
        return userName;
    }

    public String getRealName() {
        return realName;
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

    public String getViewedAt() {
        return viewedAt;
    }
}