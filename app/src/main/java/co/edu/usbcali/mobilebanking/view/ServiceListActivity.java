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
import co.edu.usbcali.mobilebanking.dao.ServiceAccess;
import co.edu.usbcali.mobilebanking.model.Product;
import co.edu.usbcali.mobilebanking.model.Service;

/**
 * Created by Marlon.Ramirez on 31/01/2018.
 */

public class ServiceListActivity extends AppCompatActivity {
    private FloatingActionButton btnAdd;
    private ListView listServices;
    private Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        btnAdd = findViewById(R.id.btn_add);
        btnReturn = findViewById(R.id.btn_return);
        listServices = findViewById(R.id.list_services);
        pressNewService();
        pressReturn();
        loadServices();
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

    private void loadServices() {
        final List<String> arrayServices = new ArrayList<>();
        final List<Service> services = ServiceAccess.getInstance().getByCustomer(this, Session.user.getId());
        for (Service s: services) {
            arrayServices.add(s.getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrayServices );
        listServices.setAdapter(arrayAdapter);
        listServices.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3)
            {
                Service selected = services.get(position);
                Intent intent = new Intent(view.getContext(), PaymentActivity.class);
                intent.putExtra("serviceId", selected.getId());
                startActivity(intent);
            }
        });
    }

    public void pressNewService() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newServiceIntent = new Intent(view.getContext(), ServiceActivity.class);
                startActivity(newServiceIntent);
            }
        });
    }
}
