package co.edu.usbcali.mobilebanking.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import co.edu.usbcali.mobilebanking.R;

/**
 * Created by Marlon.Ramirez on 31/01/2018.
 */

public class TransferListActivity extends AppCompatActivity {
    private FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_list);
        btnAdd = findViewById(R.id.btn_add);
        pressNewAccount();
    }

    public void pressNewAccount() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newAccountIntent = new Intent(view.getContext(), NewTransferActivity.class);
                startActivity(newAccountIntent);
            }
        });
    }
}
