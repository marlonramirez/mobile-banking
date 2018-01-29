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

    public List<Movement> getByProduct(Context context, int productId) {
        String[] params = {String.valueOf(productId)};
        ConnexionSQLiteHelper conn = new ConnexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<Movement> movements = new ArrayList<>();
        String query = "SELECT m.id_movement, mt.description, m.value " +
                "FROM movement m INNER JOIN movement_type mt USING(id_movement_type) " +
                "WHERE m.id_product = ?";
        Cursor result = db.rawQuery(query, params);
        while (result.moveToNext()) {
            Movement movement = new Movement(result.getInt(0), result.getString(1), productId, result.getDouble(2));
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
