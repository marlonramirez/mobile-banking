package co.edu.usbcali.mobilebanking.model;

/**
 * Created by Marlon.Ramirez on 27/01/2018.
 */

public class Product {
    private int id;
    private String type;
    private String number;
    private double balance;

    public Product(int id, String type, String number, double balance) {
        this.id = id;
        this.type = type;
        this.number = number;
        this.balance = balance;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
