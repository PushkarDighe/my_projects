package com.example.rto_login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDoalog;
    EditText et_username,et_password,et_mobile;
    LinearLayout l1,l2;
    Button btn_login;
    RadioButton rb_rto,rb_superadmin,rb_user;
    String selected_user;
    String login_url = "";
    String rto_url= "http://vsproi.com/RTO/rto_login.php?";
    String user_url= "http://vsproi.com/RTO/user_login.php?";
    String superadmin_url= "http://vsproi.com/RTO/admin_login.php?";
    String user_name,user_pwd,user_mobile;
    String name,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = (EditText)findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
        et_mobile = (EditText)findViewById(R.id.et_mobile);

        l1 = (LinearLayout)findViewById(R.id.l1);
        l2 = (LinearLayout)findViewById(R.id.l2);

        btn_login = (Button)findViewById(R.id.btn_login);

        rb_rto = (RadioButton)findViewById(R.id.rb_rto);
        rb_superadmin = (RadioButton)findViewById(R.id.rb_superadmin);
        rb_user = (RadioButton)findViewById(R.id.rb_user);


        rb_rto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb_rto.isChecked()) {

                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.INVISIBLE);
                    clear();
                }
            }
        });

        rb_superadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb_superadmin.isChecked()) {

                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.INVISIBLE);
                    clear();
                }
            }
        });
        rb_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb_user.isChecked()) {

                    l1.setVisibility(View.INVISIBLE);
                    l2.setVisibility(View.VISIBLE);
                    clear();
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    public void login(){

            if(rb_rto.isChecked()==true) {
                selected_user = "RTO";
                user_name = et_username.getText().toString();
                user_pwd = et_password.getText().toString();
                login_url = rto_url;
            }
            else if(rb_superadmin.isChecked()==true) {
                selected_user = "Super Admin";
                user_name = et_username.getText().toString();
                user_pwd = et_password.getText().toString();
                login_url = superadmin_url;
            }
            else {
                selected_user = "User";
                user_mobile = et_mobile.getText().toString();
                login_url = user_url;
            }

        progressDoalog = new ProgressDialog(MainActivity.this);
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

                            name = postobject.getString("name");
                            id = postobject.getString("id");

                            if(selected_user.equals("RTO")) {
                                //Toast.makeText(getApplicationContext(), " " + user_name + " " + user_pwd, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(),RTO_Page.class);
                                i.putExtra("username",name);
                                i.putExtra("id",id);
                                i.putExtra("title","RTO DASHBOARD");
                                startActivity(i);
                            }
                            else if(selected_user.equals("Super Admin")){
                                //Toast.makeText(getApplicationContext(), " " +user_mobile, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(),Superadmin_Page.class);
                                i.putExtra("username",name);
                                i.putExtra("id",id);
                                startActivity(i);
                            }
                            else{
                                Intent i = new Intent(getApplicationContext(),User_Page.class);
                                i.putExtra("username",name);
                                i.putExtra("id",id);
                                i.putExtra("title","USER DASHBOARD");
                                startActivity(i);
                            }
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

                if(!selected_user.equals("User")){
                    param.put("username",user_name);
                    param.put("password",user_pwd);
                }
                else{
                    param.put("mobile",user_mobile);
                }
                return param;
               }
        };

        MySingleton.getInstance(MainActivity.this).addToRequestque(stringRequest);

    }

    public void clear(){
        et_username.setText("");
        et_password.setText("");
        et_mobile.setText("");
    }
}