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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.usbcali.mobilebanking.R;
import co.edu.usbcali.mobilebanking.Session;
import co.edu.usbcali.mobilebanking.dao.AccountAccess;
import co.edu.usbcali.mobilebanking.dao.MovementAccess;
import co.edu.usbcali.mobilebanking.dao.ProductAccess;
import co.edu.usbcali.mobilebanking.model.Account;
import co.edu.usbcali.mobilebanking.model.Product;

/**
 * Created by Marlon.Ramirez on 2/02/2018.
 */

public class TransferActivity extends AppCompatActivity {
    private Spinner spnProducts;
    private TextView viewName;
    private TextView viewAccount;
    private TextView txtAmount;
    private Button btnAccept;
    private Button btnReturn;
    private Product selectedProduct;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        spnProducts = findViewById(R.id.spn_products);
        viewAccount = findViewById(R.id.view_account);
        viewName = findViewById(R.id.view_name);
        txtAmount = findViewById(R.id.txt_amount);
        btnAccept = findViewById(R.id.btn_accept);
        btnReturn = findViewById(R.id.btn_return);
        loadProducts();
        loadAccountInfo();
        pressAccept();
        pressReturn();
    }

    private void pressAccept() {
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = view.getContext();
                final String strAmount = txtAmount.getText().toString();
                if (strAmount.isEmpty()) {
                    Toast.makeText(context, "Digite la cantidad a transferir", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle(R.string.dialog_title);
                alertDialog.setMessage("¿Desea realizar la tranferencia a " + account.getHolder() + "?");
                alertDialog.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MovementAccess.getInstance().saveTransfer(context, selectedProduct.getId(), account.getId(), Double.parseDouble((strAmount)));
                        Intent transferListIntent = new Intent(context, AccountListActivity.class);
                        startActivity(transferListIntent);
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

    private void pressReturn() {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accountListIntent = new Intent(view.getContext(), AccountListActivity.class);
                startActivity(accountListIntent);
            }
        });
    }

    private void loadAccountInfo() {
        Integer id = getIntent().getIntExtra("accountId", 0);
        account = AccountAccess.getInstance().getById(this, id);
        viewName.setText(account.getHolder());
        viewAccount.setText(account.getBank() + " " + account.getNum());
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
