package co.edu.usbcali.mobilebanking.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.edu.usbcali.mobilebanking.model.Account;

/**
 * Created by Marlon.Ramirez on 2/02/2018.
 */

public class AccountAccess {
    private static AccountAccess instance;

    public void save(Context context, int customerId, String accountNum, String holder, int bankId) {
        ConnexionSQLiteHelper conn = new ConnexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        String sql = "INSERT INTO account (account_num, description, id_bank, id_customer) " +
                "VALUES ('" + accountNum + "', '" + holder + "', '" + bankId + "', '" + customerId + "')";
        db.execSQL(sql);
        db.close();
        conn.close();
    }

    public List<Account> getByCustomer(Context context, int customerId) {
        String[] params = {String.valueOf(customerId)};
        ConnexionSQLiteHelper conn = new ConnexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<Account> accounts = new ArrayList<>();
        String query = "SELECT a.id_account, a.account_num, a.description, b.name " +
                "FROM account a INNER JOIN bank b USING(id_bank) " +
                "WHERE a.id_customer = ?";
        Cursor result = db.rawQuery(query, params);
        while (result.moveToNext()) {
            Account account = new Account(result.getInt(0), result.getString(1), result.getString(2), result.getString(3));
            accounts.add(account);
        }
        db.close();
        conn.close();
        return accounts;
    }

    public Account getById(Context context, int id) {
        String[] params = {String.valueOf(id)};
        ConnexionSQLiteHelper conn = new ConnexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        Account account = null;
        String query = "SELECT a.id_account, a.account_num, a.description, b.name " +
                "FROM account a INNER JOIN bank b USING(id_bank) " +
                "WHERE s.id_account = ?";
        Cursor result = db.rawQuery(query, params);
        if (result.moveToFirst()) {
            account = new Account(id, result.getString(0), result.getString(1), result.getString(2));
        }
        db.close();
        conn.close();
        return account;
    }

    public static AccountAccess getInstance() {
        if (instance == null) {
            instance = new AccountAccess();
        }
        return instance;
    }
}
