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

public class RTO_Vehicle_Scan_Report extends AppCompatActivity {

    String json_url,sid;
    ProgressBar progressBar;
    ProgressDialog progressDoalog;
    ArrayList<HashMap<String, String>> post_arryList;
    vehical_recyclerAdapter_6 demo_recyclerAdapter_6;
    private RecyclerView.LayoutManager layoutManager_demo;
    private RecyclerView recyclerView_demo;
    Toolbar toolbar_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_t_o__vehicle__scan__report);

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
        progressBar=(ProgressBar)findViewById(R.id.pg_6);

        recyclerView_demo=(RecyclerView)findViewById(R.id.recycler_vehical_6);
        layoutManager_demo = new LinearLayoutManager(RTO_Vehicle_Scan_Report.this, LinearLayoutManager.VERTICAL, false);
        recyclerView_demo.setLayoutManager(layoutManager_demo);

        demo_recyclerAdapter_6 = new vehical_recyclerAdapter_6(RTO_Vehicle_Scan_Report.this,post_arryList);
        recyclerView_demo.setAdapter(demo_recyclerAdapter_6);
        load_data();
    }


    void load_data()
    {
        {
            progressDoalog = new ProgressDialog(RTO_Vehicle_Scan_Report.this);
            progressDoalog.setMessage("Loading....");
            progressDoalog.show();
            json_url = "http://vsproi.com/RTO/rto_vehicle_scaned_list.php?uid="+sid;
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
                                        String VNUM = c.getString("VNUM");
                                        String FINE_AMT = c.getString("FINE_AMT");
                                        String FINE_REASON = c.getString("FINE_REASON");
                                        String SECTION = c.getString("SECTION");
                                        String DATE_TIME= c.getString("DATE_TIME");
                                        String RTO_NAME= c.getString("RTO_NAME");

                                        map.put("VNUM", VNUM);
                                        map.put("FINE_AMT", FINE_AMT);
                                        map.put("FINE_REASON", FINE_REASON);
                                        map.put("SECTION", SECTION);
                                        map.put("DATE_TIME", DATE_TIME);
                                        map.put("RTO_NAME", RTO_NAME);

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
            MySingleton.getInstance(RTO_Vehicle_Scan_Report.this).addToRequestque(jsonObjectRequest);
        }
        if (demo_recyclerAdapter_6 != null) {
            demo_recyclerAdapter_6.notifyDataSetChanged();
            System.out.println("Adapter " + demo_recyclerAdapter_6.toString());
        }
    }

    public class vehical_recyclerAdapter_6 extends RecyclerView.Adapter<vehical_recyclerAdapter_6.DemoViewHolder>
    {
        Context context;
        ArrayList<HashMap<String, String>> img_list;

        public vehical_recyclerAdapter_6(Context context, ArrayList<HashMap<String, String>> quans_list) {
            this.img_list = quans_list;
            this.context = context;
        }
        @Override
        public vehical_recyclerAdapter_6.DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_1, parent, false);
            vehical_recyclerAdapter_6.DemoViewHolder ViewHolder = new vehical_recyclerAdapter_6.DemoViewHolder(view);
            return ViewHolder;
        }
        @Override
        public void onBindViewHolder(vehical_recyclerAdapter_6.DemoViewHolder merchantViewHolder, final int position)
        {
            merchantViewHolder.c_vehicle_no.setText(img_list.get(position).get("VNUM"));
            merchantViewHolder.c_fine_amt.setText(img_list.get(position).get("FINE_AMT"));
            merchantViewHolder.c_fine_rsn.setText(img_list.get(position).get("FINE_REASON"));
            merchantViewHolder.c_section.setText(img_list.get(position).get("SECTION"));
            merchantViewHolder.c_d_and_t.setText(img_list.get(position).get("DATE_TIME"));
            merchantViewHolder.c_rto_name.setText(img_list.get(position).get("RTO_NAME"));

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