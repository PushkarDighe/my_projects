package com.example.rto_login;

import androidx.appcompat.app.AppCompatActivity;
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
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Reg_rto_report extends AppCompatActivity {
    ProgressBar progressBar;
    ProgressDialog progressDoalog;
    String rto_reg_report = "http://vsproi.com/RTO/rto_list.php";
    String vehicle_report = "http://vsproi.com/RTO/vehicle_report.php";
    String vehicle_scan_report = "http://vsproi.com/RTO/vehicle_scaned_list.php";
    String fine_report = "http://vsproi.com/RTO/vehicle_fine_list.php";
    ArrayList<HashMap<String, String>> post_arryList;
    vehical_recyclerAdapter demo_recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager_demo;
    private RecyclerView recyclerView_demo;
    Toolbar toolbar_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        toolbar_2 = (Toolbar)findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar_2);
        TextView toolbar_title = (TextView) toolbar_2.findViewById(R.id.tool_title);

        Bundle b = getIntent().getExtras();
        try{
            toolbar_title.setText(b.getString("title"));
        }
        catch (Exception e){}

        post_arryList = new ArrayList<HashMap<String, String>>();
        progressBar=(ProgressBar)findViewById(R.id.pg);

        recyclerView_demo=(RecyclerView)findViewById(R.id.recycler_vehical);
        //--------for linear layout--------------
        layoutManager_demo = new LinearLayoutManager(Reg_rto_report.this, LinearLayoutManager.VERTICAL, false);
        recyclerView_demo.setLayoutManager(layoutManager_demo);
        //---------for grid layout--------------
        // recyclerView_demo.setLayoutManager(new GridLayoutManager(SelectActivity.this,2));
        //------------------------------------------
        demo_recyclerAdapter=new vehical_recyclerAdapter(Reg_rto_report.this,post_arryList);
        recyclerView_demo.setAdapter(demo_recyclerAdapter);
        load_data();

    }
    void load_data(){

        {
            progressDoalog = new ProgressDialog(Reg_rto_report.this);
            progressDoalog.setMessage("Loading....");
            progressDoalog.show();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, rto_reg_report, null, new Response.Listener<JSONObject>() {
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
                                        String Name = c.getString("UNAME");
                                        String Pwd = c.getString("UPASS");
                                        String Mobile = c.getString("MOBILE");
                                        String City = c.getString("CITY");
                                        String Area = c.getString("AREA");
                                        String State = c.getString("STATE");
                                        String Country = c.getString("COUNTRY");

                                        map.put("Name", Name);
                                        map.put("Pwd", Pwd);
                                        map.put("Mobile", Mobile);
                                        map.put("City", City);
                                        map.put("Area", Area);
                                        map.put("State", State);
                                        map.put("Country", Country);

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
            MySingleton.getInstance(Reg_rto_report.this).addToRequestque(jsonObjectRequest);
        }
        if (demo_recyclerAdapter != null) {
            demo_recyclerAdapter.notifyDataSetChanged();
            System.out.println("Adapter " + demo_recyclerAdapter.toString());
        }
    }

    public class vehical_recyclerAdapter extends RecyclerView.Adapter<vehical_recyclerAdapter.DemoViewHolder>
    {
        Context context;
        ArrayList<HashMap<String, String>> img_list;

        public vehical_recyclerAdapter(Context context, ArrayList<HashMap<String, String>> quans_list) {
            this.img_list = quans_list;
            this.context = context;
        }
        @Override
        public vehical_recyclerAdapter.DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
            vehical_recyclerAdapter.DemoViewHolder ViewHolder = new vehical_recyclerAdapter.DemoViewHolder(view);
            return ViewHolder;
        }
        @Override
        public void onBindViewHolder(vehical_recyclerAdapter.DemoViewHolder merchantViewHolder, final int position)
        {
            merchantViewHolder.c_name.setText(img_list.get(position).get("Name"));
            merchantViewHolder.c_pwd.setText(img_list.get(position).get("Pwd"));
            merchantViewHolder.c_Mobile.setText(img_list.get(position).get("Mobile"));
            merchantViewHolder.c_city.setText(img_list.get(position).get("City"));
            merchantViewHolder.c_area.setText(img_list.get(position).get("Area"));
            merchantViewHolder.c_state.setText(img_list.get(position).get("State"));
            merchantViewHolder.c_country.setText(img_list.get(position).get("Country"));
        }
        @Override
        public int getItemCount() {
            return img_list.size();
        }
        public class DemoViewHolder extends RecyclerView.ViewHolder
        {      TextView c_name,c_pwd,c_Mobile,c_city,c_area,c_state,c_country;
            public DemoViewHolder(View itemView) {
                super(itemView);
                this.c_name=(TextView)itemView.findViewById(R.id.c_name);
                this.c_pwd=(TextView)itemView.findViewById(R.id.c_pwd);
                this.c_Mobile=(TextView)itemView.findViewById(R.id.c_Mobile);
                this.c_city=(TextView)itemView.findViewById(R.id.c_city);
                this.c_area=(TextView)itemView.findViewById(R.id.c_area);
                this.c_state=(TextView)itemView.findViewById(R.id.c_state);
                this.c_country=(TextView)itemView.findViewById(R.id.c_country);
            }
        }
    }
}