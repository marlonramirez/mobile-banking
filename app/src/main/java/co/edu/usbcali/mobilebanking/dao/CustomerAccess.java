package co.edu.usbcali.mobilebanking.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import co.edu.usbcali.mobilebanking.model.Customer;

/**
 * Created by Marlon.Ramirez on 27/01/2018.
 */

public class CustomerAccess {
    private static CustomerAccess instance;

    public Customer login(Context context, int documentType, String documentNum, String password) {
        Customer user = null;
        String[] params = {String.valueOf(documentType), documentNum, password};
        ConnexionSQLiteHelper conn = new ConnexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        String query = "SELECT c.id_customer, dt.description, c.document_num, c.first_name, c.second_name, c.phone, c.address " +
                "FROM customer c INNER JOIN document_type dt USING(id_document_type) " +
                "WHERE dt.id_document_type = ? AND c.document_num = ? AND c.password = ?";
        Cursor result = db.rawQuery(query, params);
        if (result.moveToFirst()) {
            user = new Customer(
                    result.getInt(0),
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6)
            );
        }
        db.close();
        conn.close();
        return user;
    }

    public static CustomerAccess getInstance() {
        if (instance == null) {
            instance = new CustomerAccess();
        }
        return instance;
    }
}
