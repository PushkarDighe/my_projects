package com.example.rto_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class RTO_Report extends AppCompatActivity {

    Toolbar toolbar_2;
    ImageView registered_vehicle,scanned_vehicle,finned_vehicle;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_t_o__report);

        toolbar_2 = (Toolbar)findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar_2);
        TextView toolbar_title = (TextView) toolbar_2.findViewById(R.id.tool_title);

        registered_vehicle = (ImageView)findViewById(R.id.registered_vehicle);
        scanned_vehicle = (ImageView)findViewById(R.id.scanned_vehicle);
        finned_vehicle = (ImageView)findViewById(R.id.finned_vehicle);

        Bundle b = getIntent().getExtras();
        try{
            toolbar_title.setText(b.getString("title"));
            id = b.getString("id");
           // Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){}

        registered_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),RTO_Reg_Vehicle.class);
                i.putExtra("title","REGISTERED VEHICLE");
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        scanned_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RTO_Vehicle_Scan_Report.class);
                i.putExtra("title","VEHICLE SCANNED");
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        finned_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),RTO_Finned_Report.class);
                i.putExtra("title","FINNED VEHICLES");
                i.putExtra("id",id);
                startActivity(i);
            }
        });
    }
}