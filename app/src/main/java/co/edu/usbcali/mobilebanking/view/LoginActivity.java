package co.edu.usbcali.mobilebanking.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.usbcali.mobilebanking.R;
import co.edu.usbcali.mobilebanking.Session;
import co.edu.usbcali.mobilebanking.dao.CustomerAccess;
import co.edu.usbcali.mobilebanking.dao.DocumentTypeAccess;
import co.edu.usbcali.mobilebanking.model.Customer;
import co.edu.usbcali.mobilebanking.model.DocumentType;

public class LoginActivity extends AppCompatActivity {
    private Spinner spnDocumentType;
    private TextView txtDcomentNum;
    private TextView txtPassword;
    private Button btnLogin;
    private DocumentType selectedDocumentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spnDocumentType = findViewById(R.id.spn_document_type);
        txtDcomentNum = findViewById(R.id.txt_document_num);
        txtPassword = findViewById(R.id.txt_password);
        btnLogin = findViewById(R.id.btn_login);
        loadDocumentTypes();
        login();
    }

    private void loadDocumentTypes() {
        final List<DocumentType> documentTypes = DocumentTypeAccess.getInstance().getAll(this);
        List<String> labels = new ArrayList<>();
        for (DocumentType documentType: documentTypes) {
            labels.add(documentType.getDescription());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDocumentType.setAdapter(dataAdapter);
        spnDocumentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDocumentType = documentTypes.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedDocumentType = null;
            }
        });
    }

    private void login() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String document = txtDcomentNum.getText().toString();
                String password = txtPassword.getText().toString();
                if (document.isEmpty() || password.isEmpty()) {
                    Toast.makeText(view.getContext(), R.string.msg_empty_credentials, Toast.LENGTH_SHORT).show();
                    return;
                }
                Customer customer = CustomerAccess.getInstance().login(view.getContext(), selectedDocumentType.getId(), document, password);
                if (customer == null) {
                    Toast.makeText(view.getContext(), R.string.msg_invalid_credentials, Toast.LENGTH_SHORT).show();
                    return;
                }
                Session.user = customer;
                Intent productListIntent = new Intent(view.getContext(), ProductListActivity.class);
                startActivity(productListIntent);
            }
        });
    }
}
