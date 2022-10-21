package com.smartsimjgraphics.bccihub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.smartsimjgraphics.bccihub.forum.AdapterForum;
import com.smartsimjgraphics.bccihub.forum.Forum_Model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ActivityForum extends AppCompatActivity {

    TextView count_holder;
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    FloatingActionButton fab_post_forum;
    AdapterForum adapter;
    ArrayList<Forum_Model> forum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        Objects.requireNonNull(getSupportActionBar()).hide();

        init();

        retrieveCustEmail();

        //retrieving properties
        checkConnectivity();



        fab_post_forum.setOnClickListener(v -> {

            Intent gotoAddForum = new Intent(ActivityForum.this,
                    ActivityCustomerComplaint.class);
            startActivity(gotoAddForum);

        });

    }

    private void init(){

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        count_holder = findViewById(R.id.tv_forum_count);
        recyclerView = findViewById(R.id.forum_recyclerview);
        fab_post_forum = findViewById(R.id.fab_forum_add);

        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        adapter = new AdapterForum();
        recyclerView.setAdapter(adapter);
        forum = new ArrayList<>();

    }

    public void retrieveCustEmail(){

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_cust_phone_email_sharedPref", MODE_PRIVATE);

        String status_phone = sharedPreferences.getString("cust_status_phone", "");
        String status_email = sharedPreferences.getString("cust_status_email", "");

    }

    private void retrieveForum() {

        //clear recyclerview to avoid duplication of records (swipe to refresh)
        forum.clear();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading FOrum Data...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                getResources().getString(R.string.url) + "retrieve_all_forum.php",
                response -> {

                    try {

                        for(int i = 0; i < response.length(); i++){

                            JSONObject jsonObject = response.getJSONObject(i);
                            Forum_Model forum_model = new Forum_Model();

                            forum_model.setForumID(jsonObject.getInt("forumID"));
                            forum_model.setForum_category(jsonObject.getString("forum_category"));
                            forum_model.setForum_desc(jsonObject.getString("forum_desc"));
                            forum_model.setForum_date(jsonObject.getString("forum_date"));
                            forum_model.setForum_time(jsonObject.getString("forum_time"));
                            forum_model.setCust_email(jsonObject.getString("cust_email"));
                            forum_model.setCust_phone(jsonObject.getString("cust_phone"));
                            forum.add(forum_model);

                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                        Toast.makeText(ActivityForum.this,
                                "JSON not valid", Toast.LENGTH_SHORT).show();

                    }

                    adapter.setData(forum);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                }, error -> {

            progressDialog.dismiss();
            Toast.makeText(ActivityForum.this,
                    "Error Occured"+error.getMessage(), Toast.LENGTH_LONG).show();

        });

        requestQueue.add(jsonArrayRequest);

    }

    private void checkConnectivity() {

        boolean wifiConnected;
        boolean mobileConnected;

        ConnectivityManager connectivitymanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connectivitymanager.getActiveNetworkInfo();
        if(activeInfo != null && activeInfo.isConnected()){
            //internet connection is available
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if(wifiConnected){

                //Action
                retrieveForum();

            }else if(mobileConnected){

                //Action
                retrieveForum();

            }

        }else{

            Toast toast = Toast.makeText(this, "Not Connected. \nCheck your internet " +
                    "connection and try again.", Toast.LENGTH_LONG);
            LinearLayout layout = (LinearLayout) toast.getView();

            if (layout.getChildCount() > 0) {
                TextView tv = (TextView) layout.getChildAt(0);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            }

            toast.show();

        }
    }


}