package co.edu.usbcali.mobilebanking.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.usbcali.mobilebanking.R;
import co.edu.usbcali.mobilebanking.Session;
import co.edu.usbcali.mobilebanking.dao.MovementAccess;
import co.edu.usbcali.mobilebanking.dao.ProductAccess;
import co.edu.usbcali.mobilebanking.dao.ServiceAccess;
import co.edu.usbcali.mobilebanking.model.Product;
import co.edu.usbcali.mobilebanking.model.Service;

/**
 * Created by Marlon.Ramirez on 2/02/2018.
 */

public class PaymentActivity extends AppCompatActivity {
    private Spinner spnProducts;
    private TextView txtOther;
    private Button btnAccept;
    private TextView viewMaxPayment;
    private TextView viewMinPayment;
    private Product selectedProduct;
    private Service service;
    private RadioGroup rdgType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        spnProducts = findViewById(R.id.spn_products);
        txtOther = findViewById(R.id.txt_other);
        btnAccept = findViewById(R.id.btn_accept);
        viewMaxPayment = findViewById(R.id.view_max_payment);
        viewMinPayment = findViewById(R.id.view_min_payment);
        rdgType = findViewById(R.id.rgd_type);
        loadServiceInfo();
        pressAccept();
        loadProducts();
    }

    private void loadServiceInfo() {
        Integer id = getIntent().getIntExtra("serviceId", 0);
        service = ServiceAccess.getInstance().getById(this, id);
        viewMinPayment.setText(viewMinPayment.getText().toString() + " $" + service.getMinPayment());
        viewMaxPayment.setText(viewMaxPayment.getText().toString() + " $" + service.getMaxPayment());
    }

    private void pressAccept() {
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = view.getContext();
                Double value = null;
                int selectedRadio = rdgType.getCheckedRadioButtonId();

                if (selectedRadio == R.id.rdb_max_payment) {
                    value = service.getMaxPayment();
                } else if (selectedRadio == R.id.rdb_min_payment) {
                    value = service.getMinPayment();
                } else if (selectedRadio == R.id.rdb_other) {
                    String strOther = txtOther.getText().toString();
                    if (strOther.isEmpty()) {
                        Toast.makeText(view.getContext(), "Por favor digite el valor a pagar", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    value = Double.parseDouble(strOther);
                }
                if (value == null) {
                    Toast.makeText(view.getContext(), "Por seleccione una opción", Toast.LENGTH_SHORT).show();
                    return;
                }
                final double saveValue = value;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle(R.string.dialog_title);
                alertDialog.setMessage("¿Desea realizar el " + service.getName() + "?");
                alertDialog.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MovementAccess.getInstance().savePayment(context, selectedProduct.getId(), service.getId(), saveValue);
                        Intent accountListIntent = new Intent(context, ServiceListActivity.class);
                        startActivity(accountListIntent);
                    }
                });
                alertDialog.setNegativeButton(R.string.dialog_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void loadProducts() {
        final List<Product> products = ProductAccess.getInstance().getByCustomer(this, Session.user.getId());
        List<String> labels = new ArrayList<>();
        for (Product product: products) {
            labels.add(product.getType());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProducts.setAdapter(dataAdapter);
        spnProducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProduct = products.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedProduct = null;
            }
        });
    }
}
