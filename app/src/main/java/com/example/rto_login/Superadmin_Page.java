package com.example.rto_login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Superadmin_Page extends AppCompatActivity {
    TextView rto_count,scan_count,vehicle_count,fine_count;
    Button register;
    EditText rg_name,rg_pwd,rg_mobile,rg_city,rg_area,rg_state,rg_country;
    ProgressDialog progressDoalog;
    String login_url = "http://vsproi.com/RTO/rto_superadmin.php";
    String add_url = "http://vsproi.com/RTO/add_rto.php";
    String rto_no,scan_no,vehicle_no,total_fine;
    Toolbar toolbar;
    AlertDialog dialog;
    ImageView exit_1,reg_rto,scan_vehicle,reg_vehicle,fine_report;
    String name,pwd,mbile,city,area,state,country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin__page);

        rto_count = (TextView)findViewById(R.id.rto_count);
        scan_count = (TextView)findViewById(R.id.scan_count);
        vehicle_count = (TextView)findViewById(R.id.vehicle_count);
        fine_count = (TextView)findViewById(R.id.fine_count);

         reg_rto = (ImageView)findViewById(R.id.reg_rto);
        scan_vehicle = (ImageView)findViewById(R.id.scan_vehicle);
        reg_vehicle = (ImageView)findViewById(R.id.reg_vehicle);
        fine_report = (ImageView)findViewById(R.id.fine_report);

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);//title
        ImageView toolbar_img = (ImageView) toolbar.findViewById(R.id.img_logout);
        ImageView img_add_rto = (ImageView) toolbar.findViewById(R.id.img_add_rto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        load_data();

        img_add_rto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_rto_popup_form();
                //Toast.makeText(Superadmin_Page.this, "Add Rto", Toast.LENGTH_SHORT).show();
            }
        });

        reg_rto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Reg_rto_report.class);
                i.putExtra("title","REGISTERED RTO");
                startActivity(i);
            }
        });

        scan_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Vehicle_scan_report.class);
                i.putExtra("title","SCANNED VEHICLES");
                startActivity(i);
            }
        });

        reg_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Vehicle_report.class);
                i.putExtra("title","REGISTERED VEHICLES");
                startActivity(i);
            }
        });

        fine_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Fine_report.class);
                i.putExtra("title","FINE REPORT");
                startActivity(i);
            }
        });
    }
    public void add_rto_popup_form(){

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.popup_form, null);

        register = (Button)alertLayout.findViewById(R.id.register);
        exit_1 = (ImageView)alertLayout.findViewById(R.id.exit_1);
        rg_name = (EditText)alertLayout.findViewById(R.id.rg_name);
        rg_pwd = (EditText)alertLayout.findViewById(R.id.rg_pwd);
        rg_mobile = (EditText)alertLayout.findViewById(R.id.rg_mobile);
        rg_city = (EditText)alertLayout.findViewById(R.id.rg_city);
        rg_area = (EditText)alertLayout.findViewById(R.id.rg_area);
        rg_state = (EditText)alertLayout.findViewById(R.id.rg_state);
        rg_country = (EditText)alertLayout.findViewById(R.id.rg_country);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = rg_name.getText().toString();
                pwd = rg_pwd.getText().toString();
                mbile = rg_mobile.getText().toString();
                city = rg_city.getText().toString();
                area = rg_area.getText().toString();
                state = rg_state.getText().toString();
                country = rg_country.getText().toString();

                progressDoalog = new ProgressDialog(Superadmin_Page.this);
                progressDoalog.setMessage("Hold On....");
                progressDoalog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, add_url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response != null) {
                                progressDoalog.dismiss();
                                JSONObject jsonObject = new JSONObject(response.toString());
                                JSONObject postobject = jsonObject.getJSONObject("posts");

                                String status = postobject.getString("status");

                                if (status.equals("200")) {
                                    Toast.makeText(getApplicationContext(), "RTO Added Succeesfully:" + status, Toast.LENGTH_LONG).show();
                                } else if (status.equals("404")) {
                                    Toast.makeText(getApplicationContext(), "Error:" + status, Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "No dat found ... please try again", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<String, String>();

                        param.put("uname", name);
                        param.put("upass", pwd);
                        param.put("mobile", mbile);
                        param.put("city", city);
                        param.put("area", area);
                        param.put("state", state);
                        param.put("country", country);
                        return param;
                    }
                };

                MySingleton.getInstance(Superadmin_Page.this).addToRequestque(stringRequest);
            }
        });

        exit_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(Superadmin_Page.this);
        alert.setView(alertLayout);
        dialog = alert.create();
        dialog.show();
    }

    public void load_data()
    {
        progressDoalog = new ProgressDialog(Superadmin_Page.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response != null) {
                        progressDoalog.dismiss();
                        JSONObject jsonObject = new JSONObject(response.toString());
                        JSONObject postobject = jsonObject.getJSONObject("posts");

                        String status = postobject.getString("status");

                        if (status.equals("200")) {

                            rto_no = postobject.getString("rto_reg");
                            scan_no = postobject.getString("no_v_scaned");
                            vehicle_no = postobject.getString("v_reg");
                            total_fine = postobject.getString("fine_amt");

                            rto_count.setText(rto_no);
                            scan_count.setText(scan_no);
                            vehicle_count.setText(vehicle_no);
                            fine_count.setText(total_fine);

                        } else if (status.equals("404")) {
                            Toast.makeText(getApplicationContext(), "Error:" + status, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No dat found ... please try again", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });
        MySingleton.getInstance(Superadmin_Page.this).addToRequestque(stringRequest);
    }
}