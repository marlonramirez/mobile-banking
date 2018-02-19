package co.edu.usbcali.mobilebanking.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.edu.usbcali.mobilebanking.R;
import co.edu.usbcali.mobilebanking.Session;
import co.edu.usbcali.mobilebanking.dao.AccountAccess;
import co.edu.usbcali.mobilebanking.model.Account;
import co.edu.usbcali.mobilebanking.view.adapter.AccountAdapter;

/**
 * Created by Marlon.Ramirez on 31/01/2018.
 */

public class AccountListActivity extends AppCompatActivity {
    private FloatingActionButton btnAdd;
    private ListView listAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        btnAdd = findViewById(R.id.btn_add);
        listAccounts = findViewById(R.id.list_accounts);
        pressNewAccount();
        loadAccounts();
    }

    private void loadAccounts() {
        final List<Account> accounts = AccountAccess.getInstance().getByCustomer(this, Session.user.getId());
        AccountAdapter arrayAdapter = new AccountAdapter(this, accounts);
        listAccounts.setAdapter(arrayAdapter);
        listAccounts.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3)
            {
                Account selected = accounts.get(position);
                Intent intent = new Intent(view.getContext(), TransferActivity.class);
                intent.putExtra("accountId", selected.getId());
                startActivity(intent);
            }
        });
    }

    public void pressNewAccount() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newAccountIntent = new Intent(view.getContext(), AccountActivity.class);
                startActivity(newAccountIntent);
            }
        });
    }
}
