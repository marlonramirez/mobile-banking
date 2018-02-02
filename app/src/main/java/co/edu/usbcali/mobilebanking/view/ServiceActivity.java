package co.edu.usbcali.mobilebanking.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.usbcali.mobilebanking.R;
import co.edu.usbcali.mobilebanking.Session;
import co.edu.usbcali.mobilebanking.dao.ServiceAccess;

/**
 * Created by Marlon.Ramirez on 31/01/2018.
 */

public class ServiceActivity extends AppCompatActivity {
    private TextView txtServiceName;
    private TextView txtMaxPayment;
    private TextView txtMinPayment;
    private Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        txtServiceName = findViewById(R.id.txt_service_name);
        txtMaxPayment = findViewById(R.id.txt_max_payment);
        txtMinPayment = findViewById(R.id.txt_min_payment);
        btnAccept = findViewById(R.id.btn_accept);
        pressAccept();
    }

    private void pressAccept() {
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                String serviceName = txtServiceName.getText().toString();
                String strMaxPayment = txtMaxPayment.getText().toString();
                String strMinPayment = txtMinPayment.getText().toString();
                if (serviceName.isEmpty() || strMinPayment.isEmpty() || strMaxPayment.isEmpty()) {
                    Toast.makeText(context, "Digite todos los campos por favor", Toast.LENGTH_SHORT).show();
                    return;
                }
                double maxPayment = Double.parseDouble(strMaxPayment);
                double minPayment = Double.parseDouble(strMinPayment);
                if (maxPayment < minPayment) {
                    Toast.makeText(context, "El pago maximo permitido es mayor que el minimo por favor ajustelos", Toast.LENGTH_SHORT).show();
                    return;
                }
                ServiceAccess.getInstance().save(context, Session.user.getId(), serviceName, maxPayment, minPayment);
                Intent serviceListIntent = new Intent(context, ServiceListActivity.class);
                startActivity(serviceListIntent);
            }
        });
    }
}
