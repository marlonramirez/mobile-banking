package co.edu.usbcali.mobilebanking.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.edu.usbcali.mobilebanking.model.DocumentType;

/**
 * Created by Marlon.Ramirez on 26/01/2018.
 */

public class DocumentTypeAccess {
    private static DocumentTypeAccess instance;

    public List<DocumentType> getAll(Context context) {
        ConnexionSQLiteHelper conn = new ConnexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        String query = "SELECT id_document_type, code, description FROM document_type";
        Cursor result = db.rawQuery(query, null);
        ArrayList<DocumentType> documentTypes = new ArrayList<>();
        while(result.moveToNext()) {
            DocumentType documentType = new DocumentType(result.getInt(0), result.getString(1), result.getString(2));
            documentTypes.add(documentType);
        }
        conn.close();
        db.close();
        return documentTypes;
    }

    public static DocumentTypeAccess getInstance() {
        if (instance == null) {
            instance = new DocumentTypeAccess();
        }
        return instance;
    }
}
