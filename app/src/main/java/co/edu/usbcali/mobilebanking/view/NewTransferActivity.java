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
import co.edu.usbcali.mobilebanking.dao.BankAccess;
import co.edu.usbcali.mobilebanking.model.Bank;

/**
 * Created by Marlon.Ramirez on 31/01/2018.
 */

public class NewTransferActivity extends AppCompatActivity {
    private Spinner spnBank;
    private Bank selectedBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transfer);
        spnBank = findViewById(R.id.spn_bank);
        loadBanks();
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
