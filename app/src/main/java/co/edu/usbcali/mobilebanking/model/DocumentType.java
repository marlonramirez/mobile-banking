package co.edu.usbcali.mobilebanking.model;

/**
 * Created by Marlon.Ramirez on 26/01/2018.
 */

public class DocumentType {
    private int id;
    private String code;
    private String description;

    public DocumentType(int id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
