package co.edu.usbcali.mobilebanking.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.edu.usbcali.mobilebanking.R;
import co.edu.usbcali.mobilebanking.Session;
import co.edu.usbcali.mobilebanking.dao.ProductAccess;
import co.edu.usbcali.mobilebanking.model.Product;

/**
 * Created by Marlon.Ramirez on 27/01/2018.
 */

public class ProductListActivity extends AppCompatActivity {
    private TextView viewName;
    private ListView listProducts;
    private ImageButton btnPaymentServices;
    private ImageButton btnTransfers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        viewName = findViewById(R.id.view_name);
        listProducts = findViewById(R.id.list_products);
        btnPaymentServices= findViewById(R.id.btn_payment_services);
        btnTransfers = findViewById(R.id.btn_transfers);
        viewName.setText(Session.user.getFirstName() + " " + Session.user.getSecondName());
        loadProducts();
        pressPaymentServices();
        pressTransfers();
    }

    private void pressPaymentServices() {
        btnPaymentServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentServicesIntent = new Intent(view.getContext(), ServiceListActivity.class);
                startActivity(paymentServicesIntent);
            }
        });
    }

    private void pressTransfers() {
        btnTransfers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent transferenceIntent = new Intent(view.getContext(), AccountListActivity.class);
                startActivity(transferenceIntent);
            }
        });
    }

    private void loadProducts() {
        final List<String> arrayProducts = new ArrayList<>();
        final List<Product> products = ProductAccess.getInstance().getByCustomer(this, Session.user.getId());
        for (Product p: products) {
            arrayProducts.add(p.getType() + " $" + p.getBalance());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrayProducts );
        listProducts.setAdapter(arrayAdapter);
        listProducts.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3)
            {
                Product selected = products.get(position);
                Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
                intent.putExtra("productId", selected.getId());
                startActivity(intent);
            }
        });
    }
}
