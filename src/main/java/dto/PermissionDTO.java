package dto;

import java.io.Serializable;

public class PermissionDTO implements Serializable {

    private String userName;
    private String documentId;

    public PermissionDTO(String userName, String documentId) {
        this.userName = userName;
        this.documentId = documentId;
    }

    public String getUserName() {
        return userName;
    }

    public String getDocumentId() {
        return documentId;
    }
}