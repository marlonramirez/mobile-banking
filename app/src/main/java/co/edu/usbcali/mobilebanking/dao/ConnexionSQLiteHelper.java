package co.edu.usbcali.mobilebanking.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by juancamilo on 1/19/18.
 */

public class ConnexionSQLiteHelper extends SQLiteOpenHelper{

    public ConnexionSQLiteHelper(Context context) {
        super(context, "bank_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDocumentType = "CREATE TABLE document_type (" +
                "                id_document_type INTEGER PRIMARY KEY," +
                "                code TEXT NOT NULL," +
                "                description TEXT NOT NULL" +
                ");";
        String createProductType = "CREATE TABLE product_type (" +
                "                id_product_type INTEGER PRIMARY KEY," +
                "                code TEXT NOT NULL," +
                "                description TEXT NOT NULL" +
                ");";
        String createService = "CREATE TABLE service (" +
                "                id_service INTEGER PRIMARY KEY," +
                "                name TEXT NOT NULL," +
                "                min_payment REAL NOT NULL," +
                "                max_payment REAL NOT NULL" +
                ");";
        String createBank = "CREATE TABLE bank (" +
                "                id_bank INTEGER PRIMARY KEY," +
                "                code TEXT NOT NULL," +
                "                name TEXT NOT NULL" +
                ");";
        String createMovementType = "CREATE TABLE movement_type (" +
                "                id_movement_type INTEGER PRIMARY KEY," +
                "                code TEXT NOT NULL," +
                "                description TEXT NOT NULL" +
                ");";
        String createCustomer = "CREATE TABLE customer (" +
                "                id_customer INTEGER PRIMARY KEY," +
                "                id_document_type INTEGER NOT NULL," +
                "                document_num TEXT NOT NULL," +
                "                first_name TEXT NOT NULL," +
                "                second_name TEXT NOT NULL," +
                "                phone TEXT NOT NULL," +
                "                address TEXT NOT NULL," +
                "                password TEXT NOT NULL," +
                "CONSTRAINT document_type_customer_fk " +
                "FOREIGN KEY (id_document_type) " +
                "REFERENCES document_type (id_document_type)" +
                ");";
        String createAccount = "CREATE TABLE account (" +
                "                id_account INTEGER PRIMARY KEY," +
                "                id_bank INTEGER NOT NULL," +
                "                account_num TEXT NOT NULL," +
                "                description TEXT NOT NULL," +
                "CONSTRAINT bank_account_fk " +
                "FOREIGN KEY (id_bank) " +
                "REFERENCES bank (id_bank)" +
                ");";
        String createProduct = "CREATE TABLE product (" +
                "                id_product INTEGER PRIMARY KEY," +
                "                id_product_type INTEGER NOT NULL," +
                "                product_num TEXT NOT NULL," +
                "                balance REAL NOT NULL," +
                "                id_customer INTEGER NOT NULL," +
                "CONSTRAINT product_type_product_fk " +
                "FOREIGN KEY (id_product_type) " +
                "REFERENCES product_type (id_product_type)," +
                "CONSTRAINT customer_product_fk " +
                "FOREIGN KEY (id_customer) " +
                "REFERENCES customer (id_customer)" +
                ");";
        String createMovement = "CREATE TABLE movement (" +
                "                id_movement INTEGER PRIMARY KEY," +
                "                id_movement_type INTEGER NOT NULL," +
                "                id_product INTEGER NOT NULL," +
                "                id_service INTEGER," +
                "                id_account INTEGER," +
                "                id_bank INTEGER NOT NULL," +
                "                value REAL NOT NULL," +
                "CONSTRAINT movement_type_movement_fk " +
                "FOREIGN KEY (id_movement_type) " +
                "REFERENCES movement_type (id_movement_type)," +
                "CONSTRAINT service_payment_fk " +
                "FOREIGN KEY (id_service) " +
                "REFERENCES service (id_service)," +
                "CONSTRAINT account_movement_fk " +
                "FOREIGN KEY (id_account, id_bank) " +
                "REFERENCES account (id_account, id_bank)," +
                "CONSTRAINT product_payment_fk " +
                "FOREIGN KEY (id_product) " +
                "REFERENCES product (id_product)" +
                ");";
        String insertDocumentTypes = "INSERT INTO document_type (code, description) VALUES ('CC', 'Cedula de ciudadania'), ('TI', 'Tarjeta de identidad'), ('NIT', 'Número de identificación tributario')";
        String insertCustomer = "INSERT INTO customer (id_document_type, document_num, first_name, second_name, phone, address, password) VALUES (1, '123456789', 'Alexander', 'Lopez', '310987665', 'Carrera 5 # 12-34', '123456')";
        db.execSQL(createDocumentType);
        db.execSQL(createProductType);
        db.execSQL(createService);
        db.execSQL(createBank);
        db.execSQL(createMovementType);
        db.execSQL(createCustomer);
        db.execSQL(createAccount);
        db.execSQL(createProduct);
        db.execSQL(createMovement);
        db.execSQL(insertDocumentTypes);
        db.execSQL(insertCustomer);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS movement");
        db.execSQL("DROP TABLE IF EXISTS product");
        db.execSQL("DROP TABLE IF EXISTS account");
        db.execSQL("DROP TABLE IF EXISTS customer");
        db.execSQL("DROP TABLE IF EXISTS movement_type");
        db.execSQL("DROP TABLE IF EXISTS bank");
        db.execSQL("DROP TABLE IF EXISTS servive");
        db.execSQL("DROP TABLE IF EXISTS product_type");
        db.execSQL("DROP TABLE IF EXISTS document_type");
        onCreate(db);
    }
}
