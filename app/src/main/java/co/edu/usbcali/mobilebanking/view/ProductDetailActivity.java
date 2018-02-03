package co.edu.usbcali.mobilebanking.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.edu.usbcali.mobilebanking.R;
import co.edu.usbcali.mobilebanking.dao.MovementAccess;
import co.edu.usbcali.mobilebanking.dao.ProductAccess;
import co.edu.usbcali.mobilebanking.model.Movement;
import co.edu.usbcali.mobilebanking.model.Product;
import co.edu.usbcali.mobilebanking.view.adapter.TransactionAdapter;

/**
 * Created by Marlon.Ramirez on 28/01/2018.
 */

public class ProductDetailActivity extends AppCompatActivity {
    private TextView viewTitle;
    private TextView viewNumber;
    private ListView listMovements;
    private Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        viewTitle = findViewById(R.id.view_title);
        viewNumber = findViewById(R.id.view_number);
        listMovements = findViewById(R.id.list_movements);
        btnReturn = findViewById(R.id.btn_return);
        Integer id = getIntent().getIntExtra("productId", 0);
        loadProductInfo(id);
        loadMovements(id);
        pressReturn();
    }

    private void loadProductInfo(int productId) {
        Product product = ProductAccess.getInstance().getById(this, productId);
        viewTitle.setText(product.getType());
        viewNumber.setText(product.getNumber());
    }

    private void pressReturn() {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productListIntent = new Intent(view.getContext(), ProductListActivity.class);
                startActivity(productListIntent);
            }
        });
    }

    private void loadMovements(int productId) {
        final List<Movement> movements = MovementAccess.getInstance().getByProduct(this, productId);
        TransactionAdapter arrayAdapter = new TransactionAdapter(this, movements);
        listMovements.setAdapter(arrayAdapter);
    }
}
