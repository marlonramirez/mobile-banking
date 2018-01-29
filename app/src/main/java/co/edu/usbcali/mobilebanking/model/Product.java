package co.edu.usbcali.mobilebanking.model;

/**
 * Created by Marlon.Ramirez on 27/01/2018.
 */

public class Product {
    private int id;
    private String productType;
    private String productNumber;
    private double balance;

    public Product(int id, String productType, String productNumber, double balance) {
        this.id = id;
        this.productType = productType;
        this.productNumber = productNumber;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
