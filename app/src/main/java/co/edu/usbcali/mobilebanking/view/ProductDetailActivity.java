package co.edu.usbcali.mobilebanking.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.edu.usbcali.mobilebanking.R;
import co.edu.usbcali.mobilebanking.dao.MovementAccess;
import co.edu.usbcali.mobilebanking.dao.ProductAccess;
import co.edu.usbcali.mobilebanking.model.Movement;
import co.edu.usbcali.mobilebanking.model.Product;

/**
 * Created by Marlon.Ramirez on 28/01/2018.
 */

public class ProductDetailActivity extends AppCompatActivity {
    private TextView viewTitle;
    private TextView viewNumber;
    private ListView listMovements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        viewTitle = findViewById(R.id.view_title);
        viewNumber = findViewById(R.id.view_number);
        listMovements = findViewById(R.id.list_movements);
        Integer id = getIntent().getIntExtra("productId", 0);
        loadProductInfo(id);
        loadMovements(id);
    }

    private void loadProductInfo(int productId) {
        Product product = ProductAccess.getInstance().getById(this, productId);
        viewTitle.setText(product.getType());
        viewNumber.setText(product.getNumber());
    }

    private void loadMovements(int productId) {
        final List<String> arrayMovements = new ArrayList<>();
        final List<Movement> movements = MovementAccess.getInstance().getByProduct(this, productId);
        for (Movement p: movements) {
            arrayMovements.add(p.getType() + ": $" + p.getValue());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrayMovements );
        listMovements.setAdapter(arrayAdapter);
    }
}
