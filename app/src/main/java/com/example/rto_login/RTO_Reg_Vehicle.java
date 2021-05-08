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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RTO_Reg_Vehicle extends AppCompatActivity {

    String json_url,sid;
    ProgressBar progressBar;
    ProgressDialog progressDoalog;
    ArrayList<HashMap<String, String>> post_arryList;
    vehical_recyclerAdapter_5 demo_recyclerAdapter_5;
    private RecyclerView.LayoutManager layoutManager_demo;
    private RecyclerView recyclerView_demo;
    Toolbar toolbar_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_t_o__reg__vehicle);

        toolbar_2 = (Toolbar)findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar_2);
        TextView toolbar_title = (TextView) toolbar_2.findViewById(R.id.tool_title);

        Bundle b = getIntent().getExtras();
        try{
            sid = b.getString("id");
            toolbar_title.setText(b.getString("title"));
        }
        catch (Exception e){}

        post_arryList = new ArrayList<HashMap<String, String>>();
        progressBar=(ProgressBar)findViewById(R.id.pg_5);

        recyclerView_demo=(RecyclerView)findViewById(R.id.recycler_vehical_5);
        layoutManager_demo = new LinearLayoutManager(RTO_Reg_Vehicle.this, LinearLayoutManager.VERTICAL, false);
        recyclerView_demo.setLayoutManager(layoutManager_demo);

        demo_recyclerAdapter_5 = new vehical_recyclerAdapter_5(RTO_Reg_Vehicle.this,post_arryList);
        recyclerView_demo.setAdapter(demo_recyclerAdapter_5);
        load_data();
    }

    void load_data()
    {
        {
            progressDoalog = new ProgressDialog(RTO_Reg_Vehicle.this);
            progressDoalog.setMessage("Loading....");
            progressDoalog.show();
            json_url = "http://vsproi.com/RTO/rto_vehicle_report.php?uid="+sid;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, json_url, null, new Response.Listener<JSONObject>() {
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
                                        String address= c.getString("ADDR");
                                        String vno= c.getString("VNUM");
                                        String vcno= c.getString("VCNUM");
                                        String vclass= c.getString("VCLASS");
                                        String vmodel= c.getString("VMODEL");
                                        String fuel= c.getString("FUEL_TYPE");
                                        String Rdate= c.getString("REG_DATE");
                                        String Rloc= c.getString("REG_LOG");
                                        String insurance= c.getString("INSURANCE");
                                        String puc= c.getString("PUC");

                                        map.put("aadhar", aadhar);
                                        map.put("name", name);
                                        map.put("mobile", mobile);
                                        map.put("email", email);
                                        map.put("address", address);
                                        map.put("vno", vno);
                                        map.put("vcno", vcno);
                                        map.put("vclass", vclass);
                                        map.put("vmodel", vmodel);
                                        map.put("fuel", fuel);
                                        map.put("Rdate", Rdate);
                                        map.put("Rloc", Rloc);
                                        map.put("insurance", insurance);
                                        map.put("puc", puc);

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
            MySingleton.getInstance(RTO_Reg_Vehicle.this).addToRequestque(jsonObjectRequest);
        }
        if (demo_recyclerAdapter_5 != null) {
            demo_recyclerAdapter_5.notifyDataSetChanged();
            System.out.println("Adapter " + demo_recyclerAdapter_5.toString());
        }
    }

    public class vehical_recyclerAdapter_5 extends RecyclerView.Adapter<vehical_recyclerAdapter_5.DemoViewHolder>
    {
        Context context;
        ArrayList<HashMap<String, String>> img_list;

        public vehical_recyclerAdapter_5(Context context, ArrayList<HashMap<String, String>> quans_list) {
            this.img_list = quans_list;
            this.context = context;
        }
        @Override
        public vehical_recyclerAdapter_5.DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list5, parent, false);
            vehical_recyclerAdapter_5.DemoViewHolder ViewHolder = new vehical_recyclerAdapter_5.DemoViewHolder(view);
            return ViewHolder;
        }
        @Override
        public void onBindViewHolder(vehical_recyclerAdapter_5.DemoViewHolder merchantViewHolder, final int position)
        {
            merchantViewHolder.c_aadhar_no.setText(img_list.get(position).get("aadhar"));
            merchantViewHolder.c_Name.setText(img_list.get(position).get("name"));
            merchantViewHolder.c_Mob.setText(img_list.get(position).get("mobile"));
            merchantViewHolder.c_Email.setText(img_list.get(position).get("email"));
            merchantViewHolder.c_Address.setText(img_list.get(position).get("address"));
            merchantViewHolder.c_VNo.setText(img_list.get(position).get("vno"));
            merchantViewHolder.c_VCNo.setText(img_list.get(position).get("vcno"));
            merchantViewHolder.c_Vclass.setText(img_list.get(position).get("vclass"));
            merchantViewHolder.c_Vmodel.setText(img_list.get(position).get("vmodel"));
            merchantViewHolder.c_fuel.setText(img_list.get(position).get("fuel"));
            merchantViewHolder.c_Rdate.setText(img_list.get(position).get("Rdate"));
            merchantViewHolder.c_Rloc.setText(img_list.get(position).get("Rloc"));
            merchantViewHolder.c_insurance.setText(img_list.get(position).get("insurance"));
            merchantViewHolder.c_puc.setText(img_list.get(position).get("puc"));
        }
        @Override
        public int getItemCount() {
            return img_list.size();
        }
        public class DemoViewHolder extends RecyclerView.ViewHolder
        {      TextView c_aadhar_no,c_Name,c_Mob,c_Email,c_Address,c_VNo,c_VCNo,c_Vclass,c_Vmodel,c_fuel,c_Rdate,c_Rloc,c_insurance,c_puc;
            public DemoViewHolder(View itemView) {
                super(itemView);
                this.c_aadhar_no=(TextView)itemView.findViewById(R.id.c_aadhar_no);
                this.c_Name=(TextView)itemView.findViewById(R.id.c_Name);
                this.c_Mob=(TextView)itemView.findViewById(R.id.c_Mob);
                this.c_Email=(TextView)itemView.findViewById(R.id.c_Email);
                this.c_Address=(TextView)itemView.findViewById(R.id.c_Address);
                this.c_VNo=(TextView)itemView.findViewById(R.id.c_VNo);
                this.c_VCNo=(TextView)itemView.findViewById(R.id.c_VCNo);
                this.c_Vclass=(TextView)itemView.findViewById(R.id.c_Vclass);
                this.c_Vmodel=(TextView)itemView.findViewById(R.id.c_Vmodel);
                this.c_fuel=(TextView)itemView.findViewById(R.id.c_fuel);
                this.c_Rdate=(TextView)itemView.findViewById(R.id.c_Rdate);
                this.c_Rloc=(TextView)itemView.findViewById(R.id.c_Rloc);
                this.c_insurance=(TextView)itemView.findViewById(R.id.c_insurance);
                this.c_puc=(TextView)itemView.findViewById(R.id.c_puc);
            }
        }
    }
}