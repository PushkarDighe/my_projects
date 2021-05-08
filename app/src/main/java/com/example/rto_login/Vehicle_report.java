package com.example.rto_login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import android.os.Bundle;

public class Vehicle_report extends AppCompatActivity {

    String vehicle_report = "http://vsproi.com/RTO/vehicle_report.php";
    ProgressBar progressBar;
    ProgressDialog progressDoalog;
    ArrayList<HashMap<String, String>> post_arryList;
    vehical_recyclerAdapter_2 demo_recyclerAdapter_2;
    private RecyclerView.LayoutManager layoutManager_demo;
    private RecyclerView recyclerView_demo;
    Toolbar toolbar_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_report);

        toolbar_2 = (Toolbar)findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar_2);
        TextView toolbar_title = (TextView) toolbar_2.findViewById(R.id.tool_title);

        Bundle b = getIntent().getExtras();
        try{
            toolbar_title.setText(b.getString("title"));
        }
        catch (Exception e){}

        post_arryList = new ArrayList<HashMap<String, String>>();
        progressBar=(ProgressBar)findViewById(R.id.pg_2);

        recyclerView_demo=(RecyclerView)findViewById(R.id.recycler_vehical_2);
        layoutManager_demo = new LinearLayoutManager(Vehicle_report.this, LinearLayoutManager.VERTICAL, false);
        recyclerView_demo.setLayoutManager(layoutManager_demo);

        demo_recyclerAdapter_2=new vehical_recyclerAdapter_2(Vehicle_report.this,post_arryList);
        recyclerView_demo.setAdapter(demo_recyclerAdapter_2);
        load_data();
    }

    void load_data()
    {
        {
            progressDoalog = new ProgressDialog(Vehicle_report.this);
            progressDoalog.setMessage("Loading....");
            progressDoalog.show();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, vehicle_report, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressBar.setVisibility(View.VISIBLE);
                    if (response != null) {
                        progressDoalog.dismiss();
                        progressBar.setVisibility(View.INVISIBLE);
                        // Toast.makeText(Report.this, ""+response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject postobject = new JSONObject(response.toString());
                            JSONObject jsonObject = postobject.getJSONObject("posts");
                            String status = jsonObject.getString("status");
                            if (status.equals("200")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("post");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject c = jsonArray.optJSONObject(i);
                                    if (c != null) {
                                        HashMap<String, String> map = new HashMap<String, String>();
                                        String aadhar = c.getString("ADHAR");
                                        String name = c.getString("NAME");
                                        String mobile = c.getString("MOBILE");
                                        String email = c.getString("EMAIL");
                                        String addr= c.getString("ADDR");
                                        String vno = c.getString("VNUM");
                                        String vcno= c.getString("VCNUM");
                                        String vclass= c.getString("VCLASS");
                                        String vmodel = c.getString("VMODEL");

                                        map.put("aadhar", aadhar);
                                        map.put("name", name);
                                        map.put("mobile", mobile);
                                        map.put("email", email);
                                        map.put("addr", addr);
                                        map.put("vno", vno);
                                        map.put("vcno", vcno);
                                        map.put("vclass", vclass);
                                        map.put("vmodel", vmodel);

                                        post_arryList.add(map);
                                        // Log.d("ssss",""+post_arryList);
                                        //  Toast.makeText(Report.this, ""+post_arryList.toString(), Toast.LENGTH_SHORT).show();
                                        //json_responce.setText(""+post_arryList);
                                    }
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            MySingleton.getInstance(Vehicle_report.this).addToRequestque(jsonObjectRequest);
        }
        if (demo_recyclerAdapter_2 != null) {
            demo_recyclerAdapter_2.notifyDataSetChanged();
            System.out.println("Adapter " + demo_recyclerAdapter_2.toString());
        }
    }

    public class vehical_recyclerAdapter_2 extends RecyclerView.Adapter<vehical_recyclerAdapter_2.DemoViewHolder>
    {
        Context context;
        ArrayList<HashMap<String, String>> img_list;

        public vehical_recyclerAdapter_2(Context context, ArrayList<HashMap<String, String>> quans_list) {
            this.img_list = quans_list;
            this.context = context;
        }
        @Override
        public vehical_recyclerAdapter_2.DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_3, parent, false);
            vehical_recyclerAdapter_2.DemoViewHolder ViewHolder = new vehical_recyclerAdapter_2.DemoViewHolder(view);
            return ViewHolder;
        }
        @Override
        public void onBindViewHolder(vehical_recyclerAdapter_2.DemoViewHolder merchantViewHolder, final int position)
        {
            merchantViewHolder.c_name_1.setText(img_list.get(position).get("name"));
            merchantViewHolder.c_aadhar.setText(img_list.get(position).get("aadhar"));
            merchantViewHolder.c_Mobile_1.setText(img_list.get(position).get("mobile"));
            merchantViewHolder.c_email.setText(img_list.get(position).get("email"));
            merchantViewHolder.c_addr.setText(img_list.get(position).get("addr"));
            merchantViewHolder.c_vno.setText(img_list.get(position).get("vno"));
            merchantViewHolder.c_vcno.setText(img_list.get(position).get("vcno"));
            merchantViewHolder.c_class.setText(img_list.get(position).get("vclass"));
            merchantViewHolder.c_model.setText(img_list.get(position).get("vmodel"));
        }
        @Override
        public int getItemCount() {
            return img_list.size();
        }
        public class DemoViewHolder extends RecyclerView.ViewHolder
        {      TextView c_name_1,c_aadhar,c_Mobile_1,c_email,c_addr,c_vno,c_vcno,c_class,c_model;
            public DemoViewHolder(View itemView) {
                super(itemView);
                this.c_name_1=(TextView)itemView.findViewById(R.id.c_name_1);
                this.c_aadhar=(TextView)itemView.findViewById(R.id.c_aadhar);
                this.c_Mobile_1=(TextView)itemView.findViewById(R.id.c_Mobile_1);
                this.c_email=(TextView)itemView.findViewById(R.id.c_email);
                this.c_addr=(TextView)itemView.findViewById(R.id.c_addr);
                this.c_vno=(TextView)itemView.findViewById(R.id.c_vno);
                this.c_vcno=(TextView)itemView.findViewById(R.id.c_vcno);
                this.c_class=(TextView)itemView.findViewById(R.id.c_class);
                this.c_model=(TextView)itemView.findViewById(R.id.c_model);
            }
        }
    }
}