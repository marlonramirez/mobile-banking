package co.edu.usbcali.mobilebanking.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.edu.usbcali.mobilebanking.model.Bank;

/**
 * Created by Marlon.Ramirez on 2/02/2018.
 */

public class BankAccess {
    private static BankAccess instance;

    public List<Bank> getAll(Context context) {
        ConnexionSQLiteHelper conn = new ConnexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        String query = "SELECT id_bank, code, name FROM bank";
        Cursor result = db.rawQuery(query, null);
        ArrayList<Bank> banks = new ArrayList<>();
        while(result.moveToNext()) {
            Bank bank = new Bank(result.getInt(0), result.getString(1), result.getString(2));
            banks.add(bank);
        }
        conn.close();
        db.close();
        return banks;
    }

    public static BankAccess getInstance() {
        if (instance == null) {
            instance = new BankAccess();
        }
        return instance;
    }
}
