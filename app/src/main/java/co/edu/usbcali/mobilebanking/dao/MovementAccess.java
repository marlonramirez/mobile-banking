package co.edu.usbcali.mobilebanking.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.edu.usbcali.mobilebanking.model.Movement;

/**
 * Created by Marlon.Ramirez on 29/01/2018.
 */

public class MovementAccess {
    private static MovementAccess instance;

    public void saveTransfer(Context context, int product, int account, double value) {
        ConnexionSQLiteHelper conn = new ConnexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        String sql = "INSERT INTO movement (id_movement_type, id_product, id_account, value) " +
                "VALUES ('3', '" + product + "', '" + account + "', '" + value + "')";
        String updateBalance = "UPDATE product SET balance = balance - " + value + " WHERE id_product = " + product;
        db.execSQL(sql);
        db.execSQL(updateBalance);
        db.close();
        conn.close();
    }

    public void savePayment(Context context, int product, int service, double value) {
        ConnexionSQLiteHelper conn = new ConnexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        String sql = "INSERT INTO movement (id_movement_type, id_product, id_service, value) " +
                "VALUES ('2', '" + product + "', '" + service + "', '" + value + "')";
        String updateBalance = "UPDATE product SET balance = balance - " + value + " WHERE id_product = " + product;
        db.execSQL(sql);
        db.execSQL(updateBalance);
        db.close();
        conn.close();
    }

    public List<Movement> getByProduct(Context context, int productId) {
        String[] params = {String.valueOf(productId)};
        ConnexionSQLiteHelper conn = new ConnexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<Movement> movements = new ArrayList<>();
        String query = "SELECT m.id_movement, mt.description, m.value, mt.type " +
                "FROM movement m INNER JOIN movement_type mt USING(id_movement_type) " +
                "WHERE m.id_product = ?";
        Cursor result = db.rawQuery(query, params);
        while (result.moveToNext()) {
            Movement movement = new Movement(result.getInt(0), result.getString(1), productId, result.getDouble(2), result.getInt(3));
            movements.add(movement);
        }
        db.close();
        conn.close();
        return movements;
    }

    public static MovementAccess getInstance() {
        if (instance == null) {
            instance = new MovementAccess();
        }
        return instance;
    }
}
