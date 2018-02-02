package co.edu.usbcali.mobilebanking.model;

/**
 * Created by Marlon.Ramirez on 2/02/2018.
 */

public class Service {
    private int id;
    private String name;
    private double maxPayment;
    private double minPayment;

    public Service(int id, String name, double maxPayment, double minPayment) {
        this.id = id;
        this.name = name;
        this.maxPayment = maxPayment;
        this.minPayment = minPayment;
    }

    public Service(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxPayment() {
        return maxPayment;
    }

    public void setMaxPayment(double maxPayment) {
        this.maxPayment = maxPayment;
    }

    public double getMinPayment() {
        return minPayment;
    }

    public void setMinPayment(double minPayment) {
        this.minPayment = minPayment;
    }
}
