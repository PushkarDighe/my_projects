package com.example.rto_login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class User_Page extends AppCompatActivity {
    TextView user_no;
    String user_id;
    Toolbar toolbar_2;
    ImageView qr_generate,total_vehicles,total_scan,total_fine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__page);

        qr_generate = (ImageView)findViewById(R.id.qr_generate);

        toolbar_2 = (Toolbar)findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar_2);
        TextView toolbar_title = (TextView) toolbar_2.findViewById(R.id.tool_title);

        user_no = (TextView)findViewById(R.id.user_no);
        total_vehicles = (ImageView)findViewById(R.id.total_vehicles);
        total_fine = (ImageView)findViewById(R.id.total_fine);
        total_scan = (ImageView)findViewById(R.id.total_scan);

        Bundle b = getIntent().getExtras();
        try {

            toolbar_title.setText(b.getString("title"));
            user_no.setText(b.getString("username"));
            user_id = b.getString("id");
        }
        catch (Exception e){}

        qr_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),Generate_QR.class);
                i.putExtra("title","QR GENERATE");
                startActivity(i);
            }
        });

        total_vehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),User_Vehicle_Report.class);
                i.putExtra("user_id",user_id);
                i.putExtra("title","MY VEHICLES");
                startActivity(i);
            }
        });

        total_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),User_Scan_Report.class);
                i.putExtra("user_id",user_id);
                i.putExtra("title","TOTAL SCAN");
                startActivity(i);
            }
        });

        total_fine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),User_Fine_Report.class);
                i.putExtra("user_id",user_id);
                i.putExtra("title","TOTAL FINE");
                startActivity(i);
            }
        });

    }
}