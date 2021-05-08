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

public class Vehicle_scan_report extends AppCompatActivity {

    String vehicle_scan_report = "http://vsproi.com/RTO/vehicle_scaned_list.php";
    ProgressBar progressBar;
    ProgressDialog progressDoalog;
    ArrayList<HashMap<String, String>> post_arryList;
    vehical_recyclerAdapter_1 demo_recyclerAdapter_1;
    private RecyclerView.LayoutManager layoutManager_demo;
    private RecyclerView recyclerView_demo;
    Toolbar toolbar_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_scan_report);

        toolbar_2 = (Toolbar)findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar_2);
        TextView toolbar_title = (TextView) toolbar_2.findViewById(R.id.tool_title);

        Bundle b = getIntent().getExtras();
        try{
            toolbar_title.setText(b.getString("title"));
        }
        catch (Exception e){}

        post_arryList = new ArrayList<HashMap<String, String>>();
        progressBar=(ProgressBar)findViewById(R.id.pg_1);

        recyclerView_demo=(RecyclerView)findViewById(R.id.recycler_vehical_1);
        layoutManager_demo = new LinearLayoutManager(Vehicle_scan_report.this, LinearLayoutManager.VERTICAL, false);
        recyclerView_demo.setLayoutManager(layoutManager_demo);

        demo_recyclerAdapter_1=new vehical_recyclerAdapter_1(Vehicle_scan_report.this,post_arryList);
        recyclerView_demo.setAdapter(demo_recyclerAdapter_1);
        load_data();
    }

    void load_data()
    {
        {
            progressDoalog = new ProgressDialog(Vehicle_scan_report.this);
            progressDoalog.setMessage("Loading....");
            progressDoalog.show();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, vehicle_scan_report, null, new Response.Listener<JSONObject>() {
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
                                        String v_num = c.getString("VNUM");
                                        String fine_amt = c.getString("FINE_AMT");
                                        String fine_rsn = c.getString("FINE_REASON");
                                        String section = c.getString("SECTION");
                                        String d_t= c.getString("DATE_TIME");
                                        String rto_name= c.getString("RTO_NAME");

                                        map.put("v_num", v_num);
                                        map.put("fine_amt", fine_amt);
                                        map.put("fine_rsn", fine_rsn);
                                        map.put("section", section);
                                        map.put("d_t", d_t);
                                        map.put("rto_name", rto_name);

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
            MySingleton.getInstance(Vehicle_scan_report.this).addToRequestque(jsonObjectRequest);
        }
        if (demo_recyclerAdapter_1 != null) {
            demo_recyclerAdapter_1.notifyDataSetChanged();
            System.out.println("Adapter " + demo_recyclerAdapter_1.toString());
        }
    }

    public class vehical_recyclerAdapter_1 extends RecyclerView.Adapter<vehical_recyclerAdapter_1.DemoViewHolder>
    {
        Context context;
        ArrayList<HashMap<String, String>> img_list;

        public vehical_recyclerAdapter_1(Context context, ArrayList<HashMap<String, String>> quans_list) {
            this.img_list = quans_list;
            this.context = context;
        }
        @Override
        public vehical_recyclerAdapter_1.DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_1, parent, false);
            vehical_recyclerAdapter_1.DemoViewHolder ViewHolder = new vehical_recyclerAdapter_1.DemoViewHolder(view);
            return ViewHolder;
        }
        @Override
        public void onBindViewHolder(vehical_recyclerAdapter_1.DemoViewHolder merchantViewHolder, final int position)
        {
            merchantViewHolder.c_vehicle_no.setText(img_list.get(position).get("v_num"));
            merchantViewHolder.c_fine_amt.setText(img_list.get(position).get("fine_amt"));
            merchantViewHolder.c_fine_rsn.setText(img_list.get(position).get("fine_rsn"));
            merchantViewHolder.c_section.setText(img_list.get(position).get("section"));
            merchantViewHolder.c_d_and_t.setText(img_list.get(position).get("d_t"));
            merchantViewHolder.c_rto_name.setText(img_list.get(position).get("rto_name"));
        }
        @Override
        public int getItemCount() {
            return img_list.size();
        }
        public class DemoViewHolder extends RecyclerView.ViewHolder
        {      TextView c_vehicle_no,c_fine_amt,c_fine_rsn,c_section,c_d_and_t,c_rto_name;
            public DemoViewHolder(View itemView) {
                super(itemView);
                this.c_vehicle_no=(TextView)itemView.findViewById(R.id.c_vehicle_no);
                this.c_fine_amt=(TextView)itemView.findViewById(R.id.c_fine_amt);
                this.c_fine_rsn=(TextView)itemView.findViewById(R.id.c_fine_rsn);
                this.c_section=(TextView)itemView.findViewById(R.id.c_section);
                this.c_d_and_t=(TextView)itemView.findViewById(R.id.c_d_and_t);
                this.c_rto_name=(TextView)itemView.findViewById(R.id.c_rto_name);
            }
        }
    }
}