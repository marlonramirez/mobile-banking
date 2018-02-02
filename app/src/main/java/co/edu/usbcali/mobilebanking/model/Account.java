package co.edu.usbcali.mobilebanking.model;

/**
 * Created by Marlon.Ramirez on 2/02/2018.
 */

public class Account {
    private int id;
    private String num;
    private String holder;
    private String bank;

    public Account(int id, String num, String holder, String bank) {
        this.id = id;
        this.num = num;
        this.holder = holder;
        this.bank = bank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
