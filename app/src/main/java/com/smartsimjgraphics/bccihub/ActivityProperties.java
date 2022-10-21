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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.smartsimjgraphics.bccihub.properties.PropertyAdapter;
import com.smartsimjgraphics.bccihub.properties.Property_Model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityProperties extends AppCompatActivity {

    TextView count_holder;
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    FloatingActionButton fab_add_property;
    PropertyAdapter adapter;
    ArrayList<Property_Model> properties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialization
        init();

        retrieveCustEmail();

        //retrieving properties
        checkConnectivity();

        fab_add_property.setOnClickListener(v -> {

            Intent gotoAddProperty = new Intent(ActivityProperties.this,
                    ActivityPropertyAdd.class);
            startActivity(gotoAddProperty);

        });

    }

    private void init(){

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        count_holder = findViewById(R.id.tv_property_count);
        recyclerView = findViewById(R.id.property_recyclerview);
        fab_add_property = findViewById(R.id.fab_property_add);

        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        adapter = new PropertyAdapter();
        recyclerView.setAdapter(adapter);
        properties = new ArrayList<>();


    }

    public void retrieveCustEmail(){

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_cust_phone_email_sharedPref", MODE_PRIVATE);

        String status_phone = sharedPreferences.getString("cust_status_phone", "");
        String status_email = sharedPreferences.getString("cust_status_email", "");

    }

    private void getPropertiesCount() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.url) + "count_all_properties.php",
                response -> {
                    count_holder.setText(response);

                }, error -> {

            Toast.makeText(ActivityProperties.this, "Ooops!! "
                    + error.toString().trim(), Toast.LENGTH_SHORT).show();

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("username", "users");
                return data;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void retrieveProperties() {

        //clear recyclerview to avoid duplication of records (swipe to refresh)
        properties.clear();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                getResources().getString(R.string.url) + "retrieve_all_property.php",
                response -> {

            try {

                for(int i = 0; i < response.length(); i++){

                    JSONObject jsonObject = response.getJSONObject(i);
                    Property_Model property_model = new Property_Model();

                    property_model.setPropID(jsonObject.getInt("propID"));
                    property_model.setPropTitle(jsonObject.getString("propTitle"));
                    property_model.setPropDistrict(jsonObject.getString("propDistrict"));
                    property_model.setPropLocation(jsonObject.getString("propLocation"));
                    property_model.setPropSize(jsonObject.getString("propSize"));
                    property_model.setPropNumOfBedRooms(jsonObject.getString("propNumOfBedRooms"));
                    property_model.setPropYear(jsonObject.getString("propYear"));
                    property_model.setPropPrice(jsonObject.getString("propPrice"));
                    property_model.setPropType(jsonObject.getString("propType"));
                    property_model.setPropStatus(jsonObject.getString("propStatus"));
                    property_model.setPropPayStatus(jsonObject.getString("propPayStatus"));
                    property_model.setPropPayMonth(jsonObject.getString("propPayMonth"));
                    property_model.setPropDescription(jsonObject.getString("propDescription"));
                    property_model.setCust_email(jsonObject.getString("cust_email"));
                    property_model.setCust_phone(jsonObject.getString("cust_phone"));
                    properties.add(property_model);

                }

            } catch (Exception e) {

                e.printStackTrace();
                Toast.makeText(ActivityProperties.this, "JSON not valid", Toast.LENGTH_SHORT).show();

            }

            adapter.setData(properties);
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();

        }, error -> {

            progressDialog.dismiss();
            Toast.makeText(ActivityProperties.this, "Error Occured"+error.getMessage(), Toast.LENGTH_LONG).show();

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
                retrieveProperties();
                getPropertiesCount();

            }else if(mobileConnected){

                //Action
                retrieveProperties();
                getPropertiesCount();

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