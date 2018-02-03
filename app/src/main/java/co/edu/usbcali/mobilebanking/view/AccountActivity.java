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
import co.edu.usbcali.mobilebanking.dao.BankAccess;
import co.edu.usbcali.mobilebanking.model.Bank;

/**
 * Created by Marlon.Ramirez on 31/01/2018.
 */

public class AccountActivity extends AppCompatActivity {
    private Spinner spnBank;
    private TextView txtNumAccount;
    private TextView txtHolder;
    private Button btnAccept;
    private Button btnReturn;
    private Bank selectedBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        spnBank = findViewById(R.id.spn_bank);
        txtNumAccount = findViewById(R.id.txt_num_account);
        txtHolder = findViewById(R.id.txt_holder);
        btnAccept = findViewById(R.id.btn_accept);
        btnReturn = findViewById(R.id.btn_return);
        loadBanks();
        pressAccept();
        pressReturn();
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

    private void pressAccept() {
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = view.getContext();
                final String numAccount = txtNumAccount.getText().toString();
                final String holder = txtHolder.getText().toString();
                if (numAccount.isEmpty() || holder.isEmpty()) {
                    Toast.makeText(context, "Digite todos los campos por favor", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle(R.string.dialog_title);
                alertDialog.setMessage("¿Desea crear la cuenta " + numAccount + "?");
                alertDialog.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AccountAccess.getInstance().save(context, Session.user.getId(), numAccount, holder, selectedBank.getId());
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

    private void loadBanks() {
        final List<Bank> banks = BankAccess.getInstance().getAll(this);
        List<String> labels = new ArrayList<>();
        for (Bank bank: banks) {
            labels.add(bank.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBank.setAdapter(dataAdapter);
        spnBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBank = banks.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedBank = null;
            }
        });
    }
}
