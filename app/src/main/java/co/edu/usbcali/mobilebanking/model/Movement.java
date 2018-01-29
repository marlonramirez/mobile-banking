package co.edu.usbcali.mobilebanking.model;

/**
 * Created by Marlon.Ramirez on 29/01/2018.
 */

public class Movement {
    private int id;
    private String type;
    private int idProduct;
    private Integer idService;
    private Integer idAccount;
    private double value;

    public Movement(int id, String type, int idProduct, double value) {
        this.id = id;
        this.type = type;
        this.idProduct = idProduct;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getIdService() {
        return idService;
    }

    public void setIdService(Integer idService) {
        this.idService = idService;
    }

    public Integer getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
