package com.example.rto_login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import androidx.appcompat.widget.Toolbar;

public class Add_Vehicle extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    String flag,type_fuel,date,RTO_id;
    Toolbar toolbar_2;
    ImageView reg_date_img,insurance_date_img,puc_date_img,license_upto_img;
    EditText reg_date,insurance_date,puc_date,e_aadhar,e_owner,e_mobile,e_email,e_address,e_vno,e_vcno,e_vclass,e_vmodel,e_regloc,e_license,license_upto;
    Spinner fuel_type;
    Button btn_save;
    String aadhar,owner,mobile,email,address,vno,vcno,vclass,vmodel,reg_loc,rg_date,insu_date,pu_date,lice_date,lice_no;
    ProgressDialog progressDoalog;
    String add_url = "http://vsproi.com/RTO/insert_test.php?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__vehicle);

        toolbar_2 = (Toolbar)findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar_2);
        TextView toolbar_title = (TextView) toolbar_2.findViewById(R.id.tool_title);

        reg_date = (EditText)findViewById(R.id.reg_date);
        insurance_date = (EditText)findViewById(R.id.insurance_date);
        puc_date = (EditText)findViewById(R.id.puc_date);

        fuel_type = (Spinner)findViewById(R.id.fuel_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.fuel, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuel_type.setAdapter(adapter);
        fuel_type.setOnItemSelectedListener(this);

        reg_date_img = (ImageView)findViewById(R.id.reg_date_img);
        insurance_date_img = (ImageView)findViewById(R.id.insurance_date_img);
        puc_date_img = (ImageView)findViewById(R.id.puc_date_img);
        license_upto_img = (ImageView)findViewById(R.id.license_upto_img);

        btn_save = (Button)findViewById(R.id.btn_save);
        e_aadhar = (EditText)findViewById(R.id.e_aadhar);
        e_owner = (EditText)findViewById(R.id.e_owner);
        e_mobile = (EditText)findViewById(R.id.e_mobile);
        e_email = (EditText)findViewById(R.id.e_email);
        e_address = (EditText)findViewById(R.id.e_address);
        e_vno = (EditText)findViewById(R.id.e_vno);
        e_vcno = (EditText)findViewById(R.id.e_vcno);
        e_vclass = (EditText)findViewById(R.id.e_vclass);
        e_vmodel = (EditText)findViewById(R.id.e_vmodel);
        e_regloc = (EditText)findViewById(R.id.e_regloc);
        e_license = (EditText)findViewById(R.id.e_license);
        license_upto = (EditText)findViewById(R.id.license_upto);

        Bundle b = getIntent().getExtras();
        try{
            toolbar_title.setText(b.getString("title"));
            RTO_id = b.getString("id");
        }
        catch (Exception e){}

        reg_date_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = "R";
                showdatepicker();
            }
        });

        insurance_date_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = "I";
            showdatepicker();
            }
        });

        puc_date_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = "P";
                showdatepicker();
            }
        });

        license_upto_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = "L";
                showdatepicker();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aadhar = e_aadhar.getText().toString();
                owner = e_owner.getText().toString();
                mobile = e_mobile.getText().toString();
                email = e_email.getText().toString();
                address = e_address.getText().toString();
                vno = e_vno.getText().toString();
                vcno = e_vcno.getText().toString();
                vclass = e_vclass.getText().toString();
                vmodel = e_vmodel.getText().toString();
                rg_date = reg_date.getText().toString();
                insu_date = insurance_date.getText().toString();
                pu_date = puc_date.getText().toString();
                reg_loc = e_regloc.getText().toString();
                lice_no = e_license.getText().toString();
                lice_date = license_upto.getText().toString();

                progressDoalog = new ProgressDialog(Add_Vehicle.this);
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
                                    Toast.makeText(getApplicationContext(), "Vehicle Registered Succeesfully:" + status, Toast.LENGTH_LONG).show();
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

                        param.put("uid", RTO_id);
                        param.put("adhar_no", aadhar);
                        param.put("name", owner);
                        param.put("email", email);
                        param.put("mobile", mobile);
                        param.put("address", address);
                        param.put("vnumber", vno);
                        param.put("vcnumber", vcno);
                        param.put("vclass", vclass);
                        param.put("vmodel", vmodel);
                        param.put("fuel_type", type_fuel);
                        param.put("reg_date", rg_date);
                        param.put("reg_loc", reg_loc);
                        param.put("insurance_upto", insu_date);
                        param.put("puc_upto", pu_date);
                        param.put("license_no",lice_no);
                        param.put("license_upto",lice_date);
                        return param;
                    }
                };

                MySingleton.getInstance(Add_Vehicle.this).addToRequestque(stringRequest);
            }

        });
    }
    public void showdatepicker(){
        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+5"));
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mMonth = c.get(Calendar.MONTH);
        int mYear = c.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this, mYear,mMonth,mDay);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month +=01;
         date = ""+dayOfMonth+"/"+month+"/"+year;

        if(flag.equals("R")){
            reg_date.setText(date);
        }
        else if(flag.equals("I")){
            insurance_date.setText(date);
        }
        else if(flag.equals("P")){
            puc_date.setText(date);
        }
        else {
            license_upto.setText(date);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         type_fuel = parent.getItemAtPosition(position).toString();
        //Toast.makeText(this, ""+type_fuel, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Select Fuel Type...!", Toast.LENGTH_SHORT).show();
    }
}