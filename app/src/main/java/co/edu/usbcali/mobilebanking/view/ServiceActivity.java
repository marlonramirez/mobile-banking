package co.edu.usbcali.mobilebanking.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
    private Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        txtServiceName = findViewById(R.id.txt_service_name);
        txtMaxPayment = findViewById(R.id.txt_max_payment);
        txtMinPayment = findViewById(R.id.txt_min_payment);
        btnAccept = findViewById(R.id.btn_accept);
        btnReturn = findViewById(R.id.btn_return);
        pressAccept();
        pressReturn();
    }

    private void pressReturn() {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serviceListIntent = new Intent(view.getContext(), ServiceListActivity.class);
                startActivity(serviceListIntent);
            }
        });
    }

    private void pressAccept() {
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = view.getContext();
                final String serviceName = txtServiceName.getText().toString();
                String strMaxPayment = txtMaxPayment.getText().toString();
                String strMinPayment = txtMinPayment.getText().toString();
                if (serviceName.isEmpty() || strMinPayment.isEmpty() || strMaxPayment.isEmpty()) {
                    Toast.makeText(context, "Digite todos los campos por favor", Toast.LENGTH_SHORT).show();
                    return;
                }
                final double maxPayment = Double.parseDouble(strMaxPayment);
                final double minPayment = Double.parseDouble(strMinPayment);
                if (maxPayment < minPayment) {
                    Toast.makeText(context, "El pago maximo permitido es mayor que el minimo por favor ajustelos", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle(R.string.dialog_title);
                alertDialog.setMessage("¿Desea crear " + serviceName + "?");
                alertDialog.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ServiceAccess.getInstance().save(context, Session.user.getId(), serviceName, maxPayment, minPayment);
                        Intent serviceListIntent = new Intent(context, ServiceListActivity.class);
                        startActivity(serviceListIntent);
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
}
