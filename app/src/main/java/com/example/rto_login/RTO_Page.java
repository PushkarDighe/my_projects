package com.example.rto_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class RTO_Page extends AppCompatActivity {
    String user,id;
    TextView rto_name;
    Toolbar toolbar_2;
    ImageView reg_veh,report,qr_generate,qr_scanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_t_o__page);

        rto_name = (TextView)findViewById(R.id.rto_name);
        toolbar_2 = (Toolbar)findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar_2);
        TextView toolbar_title = (TextView) toolbar_2.findViewById(R.id.tool_title);
        reg_veh = (ImageView)findViewById(R.id.reg_veh);
        report = (ImageView)findViewById(R.id.report);
        qr_generate = (ImageView)findViewById(R.id.qr_generate);
        qr_scanner = (ImageView)findViewById(R.id.qr_scanner);

        Bundle b = getIntent().getExtras();
        try{
            user=b.getString("username");
            id = b.getString("id");
            toolbar_title.setText(b.getString("title"));
            Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
            rto_name.setText("Hello "+user);
        }
        catch (Exception e){}

        reg_veh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),Add_Vehicle.class);
                i.putExtra("title","VEHICLE REGISTRATION");
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),RTO_Report.class);
                i.putExtra("title","REPORTS");
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        qr_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),Generate_QR.class);
                i.putExtra("title","QR GENERATOR");
                startActivity(i);
            }
        });

        qr_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),Scan_QR.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });
    }
}