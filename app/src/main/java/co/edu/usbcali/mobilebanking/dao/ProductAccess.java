package co.edu.usbcali.mobilebanking.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.edu.usbcali.mobilebanking.model.Product;

/**
 * Created by Marlon.Ramirez on 27/01/2018.
 */

public class ProductAccess {
    private static ProductAccess instance;

    public List<Product> getByCustomer(Context context, int customerId) {
        String[] params = {String.valueOf(customerId)};
        ConnexionSQLiteHelper conn = new ConnexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<Product> products = new ArrayList<>();
        String query = "SELECT p.id_product, pt.description, p.product_num, p.balance " +
                "FROM product p INNER JOIN product_type pt USING(id_product_type) " +
                "WHERE p.id_customer = ?";
        Cursor result = db.rawQuery(query, params);
        while (result.moveToNext()) {
            Product product = new Product(result.getInt(0), result.getString(1), result.getString(2), result.getDouble(3));
            products.add(product);
        }
        db.close();
        conn.close();
        return products;
    }

    public static ProductAccess getInstance() {
        if (instance == null) {
            instance = new ProductAccess();
        }
        return instance;
    }
}
