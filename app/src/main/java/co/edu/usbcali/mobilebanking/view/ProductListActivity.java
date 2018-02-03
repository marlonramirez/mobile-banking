package co.edu.usbcali.mobilebanking.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.edu.usbcali.mobilebanking.R;
import co.edu.usbcali.mobilebanking.Session;
import co.edu.usbcali.mobilebanking.dao.ProductAccess;
import co.edu.usbcali.mobilebanking.model.Product;
import co.edu.usbcali.mobilebanking.view.adapter.ProductAdapter;

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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.dialog_title);
        alertDialog.setMessage("¿Desea cerra sesión?");
        alertDialog.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ProductListActivity.super.onBackPressed();
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
        final List<Product> products = ProductAccess.getInstance().getByCustomer(this, Session.user.getId());
        ProductAdapter arrayAdapter = new ProductAdapter(this, products);
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
