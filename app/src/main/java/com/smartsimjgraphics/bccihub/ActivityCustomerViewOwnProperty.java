package com.smartsimjgraphics.bccihub;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.smartsimjgraphics.bccihub.properties.PropertyAdapter;
import com.smartsimjgraphics.bccihub.properties.Property_Model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ActivityCustomerViewOwnProperty extends AppCompatActivity {

    RequestQueue rQueue;
    PropertyAdapter adapter;
    ArrayList<Property_Model> properties;
    RecyclerView recyclerViewCustProp;

    String str_value_data_username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_own_property);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialize components
        init();

        retrieveCustEmail();

        checkConnectivity();

    }

    private void init(){

        rQueue = Volley.newRequestQueue(getApplicationContext());
        recyclerViewCustProp = findViewById(R.id.property_cust_recyclerview);
        recyclerViewCustProp.setLayoutManager( new LinearLayoutManager(this));
        adapter = new PropertyAdapter();
        recyclerViewCustProp.setAdapter(adapter);
        properties = new ArrayList<>();

    }

    public void retrieveCustEmail(){

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_session_user_email", MODE_PRIVATE);

        str_value_data_username =
                sharedPreferences.getString("key_session_username", "");

    }

    private void retrieveProperties() {

        //clear recyclerview to avoid duplication of records (swipe to refresh)
        properties.clear();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading your properties...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                getResources().getString(R.string.url)
                        + "retrieve_user_properties.php?cust_email="+str_value_data_username,
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
                        Toast.makeText(ActivityCustomerViewOwnProperty.this,
                                "JSON not valid", Toast.LENGTH_SHORT).show();

                    }

                    adapter.setData(properties);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                }, error -> {

            progressDialog.dismiss();
            Toast.makeText(ActivityCustomerViewOwnProperty.this, "Error Occured"+error.getMessage(), Toast.LENGTH_LONG).show();

        });

        rQueue.add(jsonArrayRequest);

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

            }else if(mobileConnected){

                //Action

                retrieveProperties();


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