package com.example.rto_login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Scan_QR extends AppCompatActivity {

    ProgressDialog progressDoalog;
    Spinner sp_reason;
    Toolbar toolbar_fine;
    LinearLayout id_hide, num_hide;
    AlertDialog dialog;
    ImageView btn_cancel;
    private IntentIntegrator qrScan;
    IntentResult result;
    ArrayList<HashMap<String, String>> post_arryList;
    ArrayList<String> group_list;
    ProgressBar progressBar;
    Button btn,btn_save;
    String scan_data_one = "";
    String[] elements;
    String fine_url="http://vsproi.com/RTO/insert_scaned_data.php";
    String json_url = "http://vsproi.com/RTO/vehicle_list.php?adhar_no=";
    String reason_url="http://vsproi.com/RTO/fine_reason_list.php";
    ContentLoadingProgressBar c;
    String rto_id, strDate,str_reason,str_sp_section,str_sp_amt,str_section,fine_amt;
    EditText vid, v_num, txt_adhar, txt_name, txt_mobile, txt_email, txt_addr, txt_vnumber, txt_chasis_number, txt_vclass, txt_vmodel, txt_fule_type, txt_reg_date, txt_reg_location, txt_insurance_upto, txt_pucc_upto;
    String str_vid, str_adhar, str_name, str_mobile, str_email, str_addr, str_vnumber, str_chasis_number, str_vclass, str_vmodel, str_fule_type, str_reg_date, str_reg_location, str_insurance_upto, str_pucc_upto;
    EditText edit_fine_amt,edit_fine_section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan__q_r);

        id_hide = (LinearLayout) findViewById(R.id.id_hide);
        num_hide = (LinearLayout) findViewById(R.id.num_hide);

        toolbar_fine = (Toolbar) findViewById(R.id.toolbar_fine);

        progressBar = (ProgressBar) findViewById(R.id.pg_fine);
        post_arryList = new ArrayList<HashMap<String, String>>();
        group_list = new ArrayList<String>();

        c = (ContentLoadingProgressBar) findViewById(R.id.pregress_bar);

        toolbar_fine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fine_popup_form();
            }
        });

        Bundle b = getIntent().getExtras();
        try {

            rto_id = b.getString("id");
        } catch (Exception e) {
        }

        vid = (EditText) findViewById(R.id.vid);
        v_num = (EditText) findViewById(R.id.v_num);

        txt_adhar = (EditText) findViewById(R.id.txt_adhar);
        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_mobile = (EditText) findViewById(R.id.txt_mobile);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_addr = (EditText) findViewById(R.id.txt_addr);
        txt_vnumber = (EditText) findViewById(R.id.txt_vnumber);
        txt_chasis_number = (EditText) findViewById(R.id.txt_chasis_number);
        txt_vclass = (EditText) findViewById(R.id.txt_vclass);
        txt_vmodel = (EditText) findViewById(R.id.txt_vmodel);
        txt_fule_type = (EditText) findViewById(R.id.txt_fule_type);
        txt_reg_date = (EditText) findViewById(R.id.txt_reg_date);
        txt_reg_location = (EditText) findViewById(R.id.txt_reg_location);
        txt_reg_location = (EditText) findViewById(R.id.txt_reg_location);
        txt_insurance_upto = (EditText) findViewById(R.id.txt_insurance_upto);
        txt_pucc_upto = (EditText) findViewById(R.id.txt_pucc_upto);

        if (vid.getText().toString().equals("")) {
            id_hide.setVisibility(View.INVISIBLE);
        } else {
            num_hide.setVisibility(View.INVISIBLE);
        }

        vid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                load_data(scan_data_one);
            }
        });

        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String num = v_num.getText().toString();
                load_data(num);
            }
        });

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        strDate = sdf.format(c.getTime());
        Log.d("dddd",strDate);
        scan_code();
    }

    public void load_data(String anum) {
        {  /* progressDoalog = new ProgressDialog(QR_Scan_Activity.this);
            progressDoalog.setMessage("Loading....");
            progressDoalog.show();*/

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, json_url + anum, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    c.setVisibility(View.VISIBLE);
                    //    Toast.makeText(getApplicationContext(),"Responce"+response,Toast.LENGTH_LONG).show();
                    try {
                        if (response != null) {
                            c.setVisibility(View.INVISIBLE);
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONObject postobject = jsonObject.getJSONObject("posts");
                            String status = postobject.getString("status");
                            if (status.equals("200")) {
                                // Toast.makeText(getApplicationContext(),"Success:"+status,Toast.LENGTH_LONG).show();
                                JSONArray jsonArray = postobject.getJSONArray("post");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject c = jsonArray.optJSONObject(i);
                                    if (c != null) {
                                        str_vid = c.getString("V_ID");
                                        str_adhar = c.getString("ADHAR");
                                        str_name = c.getString("NAME");
                                        str_mobile = c.getString("MOBILE");
                                        str_email = c.getString("EMAIL");
                                        str_addr = c.getString("ADDR");
                                        str_vnumber = c.getString("VNUM");
                                        str_chasis_number = c.getString("VCNUM");
                                        str_vclass = c.getString("VCLASS");
                                        str_vmodel = c.getString("VMODEL");
                                        str_fule_type = c.getString("FUEL_TYPE");
                                        str_reg_date = c.getString("REG_DATE");
                                        str_reg_location = c.getString("REG_LOG");
                                        str_insurance_upto = c.getString("INSURANCE");
                                        str_pucc_upto = c.getString("PUC");

                                        txt_adhar.setText(str_adhar);
                                        txt_name.setText(str_name);
                                        txt_mobile.setText(str_mobile);
                                        txt_email.setText(str_email);
                                        txt_addr.setText(str_addr);
                                        txt_vnumber.setText(str_vnumber);
                                        txt_chasis_number.setText(str_chasis_number);
                                        txt_vclass.setText(str_vclass);
                                        txt_vmodel.setText(str_vmodel);
                                        txt_fule_type.setText(str_fule_type);
                                        txt_reg_date.setText(str_reg_date);
                                        txt_reg_location.setText(str_reg_location);
                                        txt_insurance_upto.setText(str_insurance_upto);
                                        txt_pucc_upto.setText(str_pucc_upto);

                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(Scan_QR.this, "" + error, Toast.LENGTH_SHORT).show();
                }
            });
            MySingleton.getInstance(Scan_QR.this).addToRequestque(jsonObjectRequest);
        }
    }

    public void scan_code() {

//        IntentIntegrator integrator = new IntentIntegrator(Scan_QR.this);
//        integrator.setPrompt("Smart RTO");
//        integrator.setCameraId(0);  // Use a specific camera of the device
//        integrator.setOrientationLocked(true);
//        integrator.setBeepEnabled(true);
//        integrator.setCaptureActivity(CaptureActivityPortrait.class);
//        integrator.initiateScan();

        IntentIntegrator intentIntegrator = new IntentIntegrator(Scan_QR.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setPrompt("SMART RTO");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }

    //Getting scan results

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Log.d("barcode_result", "" + result);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(getApplicationContext(), "Result Not Found", Toast.LENGTH_LONG).show();
            } else {

                String res_data = result.getContents();

                Toast.makeText(getApplicationContext(), "Scane Data Done: " + res_data, Toast.LENGTH_LONG).show();
                elements = res_data.trim().split("\\s*,\\s*");
                //while generating qr code for multiple text make it separate with (,)coma
                int size = elements.length;
                //Toast.makeText(getApplicationContext(), "Result : " +size, Toast.LENGTH_LONG).show();
                scan_data_one = elements[0];
                vid.setText(scan_data_one);

            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void fine_popup_form()
    {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.add_fine, null);

        edit_fine_amt = (EditText) alertLayout.findViewById(R.id.edit_fine_amt);
        edit_fine_section = (EditText) alertLayout.findViewById(R.id.edit_fine_section);
        sp_reason = (Spinner) alertLayout.findViewById(R.id.sp_reason);
        btn_save = (Button) alertLayout.findViewById(R.id.btn_save);
        btn_cancel = (ImageView)alertLayout.findViewById(R.id.btn_cancel);

        progressDoalog = new ProgressDialog(Scan_QR.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, reason_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.VISIBLE);
                progressDoalog.dismiss();
                // Toast.makeText(getApplicationContext(),"Responce"+response,Toast.LENGTH_LONG).show();
                try
                {
                    if(response != null){
                        progressBar.setVisibility(View.INVISIBLE);
                        JSONObject jsonObject = new JSONObject(response.toString());
                        JSONObject postobject = jsonObject.getJSONObject("posts");
                        String status = postobject.getString("status");
                        if (status.equals("200")) {
                            // Toast.makeText(getApplicationContext(),"Success:"+status,Toast.LENGTH_LONG).show();
                            JSONArray jsonArray=postobject.getJSONArray("post");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.optJSONObject(i);
                                if (c != null) {
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    String  ID = c.getString("ID");
                                    String  REASON = c.getString("REASON");
                                    String  SECTION = c.getString("SECTION");
                                    String  AMT = c.getString("AMT");

                                    map.put("ID", ID);
                                    map.put("REASON", REASON);
                                    map.put("SECTION", SECTION);
                                    map.put("AMT", AMT);
                                    post_arryList.add(map);
                                    group_list.add(REASON);
                                    //json_responce.setText(""+post_arryList);
                                }
                            }
                        }
                    }
                    sp_reason.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item, group_list));
                    sp_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            str_reason = post_arryList.get(position).get("REASON");
                            str_sp_section = post_arryList.get(position).get("SECTION");
                            str_sp_amt = post_arryList.get(position).get("AMT");

                            edit_fine_section.setText(str_sp_section);
                            edit_fine_amt.setText(str_sp_amt);
                            Toast.makeText(Scan_QR.this, ""+str_sp_section+str_sp_amt, Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(Scan_QR.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        MySingleton.getInstance(Scan_QR.this).addToRequestque(jsonObjectRequest);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fine_amt = edit_fine_amt.getText().toString();
                str_section = edit_fine_section.getText().toString();
                StringRequest stringRequest=new StringRequest(Request.Method.POST, fine_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response != null) {
                                // progressbar.setVisibility(View.INVISIBLE);
                                progressDoalog.dismiss();
                                JSONObject jsonObject = new JSONObject(response.toString());
                                JSONObject postobject = jsonObject.getJSONObject("posts");
                                String status = postobject.getString("status");
                                //String client_status = postobject.getString("client_status");
                                if (status.equals("200")) {
                                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Fined Successfully..!!", Toast.LENGTH_SHORT).show();
                                } else if (status.equals("404")) {
                                    // english_poemList.clear();
                                    Toast.makeText(getApplicationContext(), "Error:" + status, Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "No data found ... please try again", Toast.LENGTH_SHORT).show();
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
                        param.put("uid",rto_id);
                        param.put("vid",str_vid);
                        param.put("fine_amt",fine_amt);
                        param.put("fine_reason",str_reason);
                        param.put("section",str_section);
                        param.put("date_time",strDate);
                        return param;
                    }
                };

                MySingleton.getInstance(Scan_QR.this).addToRequestque(stringRequest);
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(Scan_QR.this);
        alert.setView(alertLayout);
        dialog = alert.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Scan_QR.this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Fine Amount Is Not Added.");
        //builder.setIcon(R.drawable.warn);
        builder.setMessage("Are You Sure You Want To Save The Default Record ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDoalog = new ProgressDialog(Scan_QR.this);
                progressDoalog.setMessage("Saving....");
                progressDoalog.show();
                StringRequest stringRequest=new StringRequest(Request.Method.POST, fine_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response != null) {
                                // progressbar.setVisibility(View.INVISIBLE);
                                progressDoalog.dismiss();
                                JSONObject jsonObject = new JSONObject(response.toString());
                                JSONObject postobject = jsonObject.getJSONObject("posts");
                                String status = postobject.getString("status");
                                //String client_status = postobject.getString("client_status");
                                if (status.equals("200")) {
                                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Fined Successfully..!!", Toast.LENGTH_SHORT).show();
                                } else if (status.equals("404")) {
                                    // english_poemList.clear();
                                    Toast.makeText(getApplicationContext(), "Error:" + status, Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "No data found ... please try again", Toast.LENGTH_SHORT).show();
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
                        param.put("vid",str_vid);
                        param.put("fine_amt","0");
                        param.put("fine_reason","No Reason Found");
                        param.put("section","-");
                        param.put("date_time",strDate);
                        return param;
                    }
                };

                MySingleton.getInstance(Scan_QR.this).addToRequestque(stringRequest);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                dialog.dismiss();
            }
        });

        builder.setCancelable(false);
        builder.show();
        //super.onBackPressed();
    }

    }
