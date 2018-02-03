package co.edu.usbcali.mobilebanking.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.edu.usbcali.mobilebanking.model.Service;

/**
 * Created by Marlon.Ramirez on 2/02/2018.
 */

public class ServiceAccess {
    private static ServiceAccess instance;

    public void save(Context context, int customerId, String name, double maxPayment, double minPayment) {
        ConnexionSQLiteHelper conn = new ConnexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        String sql = "INSERT INTO service (name, min_payment, max_payment, id_customer) " +
                "VALUES ('" + name + "', '" + minPayment + "', '" + maxPayment + "', '" + customerId + "')";
        db.execSQL(sql);
        db.close();
        conn.close();
    }

    public List<Service> getByCustomer(Context context, int customerId) {
        String[] params = {String.valueOf(customerId)};
        ConnexionSQLiteHelper conn = new ConnexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<Service> services = new ArrayList<>();
        String query = "SELECT s.id_service, s.name, s.max_payment " +
                "FROM service s " +
                "WHERE s.id_customer = ?";
        Cursor result = db.rawQuery(query, params);
        while (result.moveToNext()) {
            Service service = new Service(result.getInt(0), result.getString(1), result.getDouble(2));
            services.add(service);
        }
        db.close();
        conn.close();
        return services;
    }

    public Service getById(Context context, int id) {
        String[] params = {String.valueOf(id)};
        ConnexionSQLiteHelper conn = new ConnexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        Service service = null;
        String query = "SELECT s.name, s.max_payment, s.min_payment " +
                "FROM service s " +
                "WHERE s.id_service = ?";
        Cursor result = db.rawQuery(query, params);
        if (result.moveToFirst()) {
            service = new Service(id, result.getString(0), result.getDouble(1), result.getDouble(2));
        }
        db.close();
        conn.close();
        return service;
    }

    public static ServiceAccess getInstance() {
        if (instance == null) {
            instance = new ServiceAccess();
        }
        return instance;
    }
}
