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
                "                id_customer INTEGER," +
                "                name TEXT NOT NULL," +
                "                min_payment REAL NOT NULL," +
                "                max_payment REAL NOT NULL," +
                "CONSTRAINT customer_service_fk " +
                "FOREIGN KEY (id_customer) " +
                "REFERENCES customer (id_customer)" +
                ");";
        String createBank = "CREATE TABLE bank (" +
                "                id_bank INTEGER PRIMARY KEY," +
                "                code TEXT NOT NULL," +
                "                name TEXT NOT NULL" +
                ");";
        String createMovementType = "CREATE TABLE movement_type (" +
                "                id_movement_type INTEGER PRIMARY KEY," +
                "                code TEXT NOT NULL," +
                "                description TEXT NOT NULL," +
                "                type INTEGER NOT NULL" +
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
                "                id_customer INTEGER," +
                "                id_bank INTEGER NOT NULL," +
                "                account_num TEXT NOT NULL," +
                "                description TEXT NOT NULL," +
                "CONSTRAINT customer_account_fk " +
                "FOREIGN KEY (id_customer) " +
                "REFERENCES customer (id_customer)," +
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
                "                value REAL NOT NULL," +
                "CONSTRAINT movement_type_movement_fk " +
                "FOREIGN KEY (id_movement_type) " +
                "REFERENCES movement_type (id_movement_type)," +
                "CONSTRAINT service_movement_fk " +
                "FOREIGN KEY (id_service) " +
                "REFERENCES service (id_service)," +
                "CONSTRAINT account_movement_fk " +
                "FOREIGN KEY (id_account) " +
                "REFERENCES account (id_account)," +
                "CONSTRAINT product_movement_fk " +
                "FOREIGN KEY (id_product) " +
                "REFERENCES product (id_product)" +
                ");";
        String insertDocumentTypes = "INSERT INTO document_type (code, description) VALUES ('CC', 'Cedula de ciudadania'), ('TI', 'Tarjeta de identidad'), ('NIT', 'Número de identificación tributario')";
        String insertCustomer = "INSERT INTO customer (id_document_type, document_num, first_name, second_name, phone, address, password) VALUES (1, '123456789', 'Alexander', 'Lopez', '310987665', 'Carrera 5 # 12-34', '123456')";
        String insertProductTypes = "INSERT INTO product_type (code, description) VALUES ('CTH', 'Cuenta de ahorros'), ('CTC', 'Cuenta corriente'), ('TCV', 'Tarjeta de credito VISA')";
        String insertProducts = "INSERT INTO product (id_customer, id_product_type, product_num, balance) VALUES (1, 1, '12-3456-789-0', 3000000), (1, 2, '98-765-432-10', 5600000), (1, 3, '321-456-78-90', 2000000)";
        String insertMovementTypes = "INSERT INTO movement_type (code, description, type) VALUES ('ABN', 'Abono a cuenta', 1), ('PSV', 'Pago de servicios', 0), ('TC', 'Tranferencia a cuenta', 0)";
        String insertMovements = "INSERT INTO movement (id_movement_type, id_product, value) VALUES (1, 1, 1200000), (1, 2, 600000), (1, 2, 800000)";
        String insertBanks = "INSERT INTO bank (code, name) VALUES ('BCO', 'Bancolombia'), ('CLP', 'Colpatria'), ('BBVA', 'BBVA Colombia')";
        db.execSQL(createDocumentType);
        db.execSQL(createProductType);
        db.execSQL(createBank);
        db.execSQL(createMovementType);
        db.execSQL(createCustomer);
        db.execSQL(createService);
        db.execSQL(createAccount);
        db.execSQL(createProduct);
        db.execSQL(createMovement);
        db.execSQL(insertDocumentTypes);
        db.execSQL(insertCustomer);
        db.execSQL(insertProductTypes);
        db.execSQL(insertProducts);
        db.execSQL(insertMovementTypes);
        db.execSQL(insertMovements);
        db.execSQL(insertBanks);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS movement");
        db.execSQL("DROP TABLE IF EXISTS product");
        db.execSQL("DROP TABLE IF EXISTS account");
        db.execSQL("DROP TABLE IF EXISTS servive");
        db.execSQL("DROP TABLE IF EXISTS customer");
        db.execSQL("DROP TABLE IF EXISTS movement_type");
        db.execSQL("DROP TABLE IF EXISTS bank");
        db.execSQL("DROP TABLE IF EXISTS product_type");
        db.execSQL("DROP TABLE IF EXISTS document_type");
        onCreate(db);
    }
}
