package co.edu.usbcali.mobilebanking.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import co.edu.usbcali.mobilebanking.R;
import co.edu.usbcali.mobilebanking.Session;
import co.edu.usbcali.mobilebanking.dao.ProductAccess;
import co.edu.usbcali.mobilebanking.model.Product;

/**
 * Created by Marlon.Ramirez on 2/02/2018.
 */

public class TransferActivity extends AppCompatActivity {
    private Spinner spnProducts;
    private Product selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        spnProducts = findViewById(R.id.spn_products);
        loadProducts();
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
