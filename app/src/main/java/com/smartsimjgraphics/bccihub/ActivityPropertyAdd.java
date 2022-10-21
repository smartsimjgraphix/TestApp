package com.smartsimjgraphics.bccihub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityPropertyAdd extends AppCompatActivity {

    EditText et_propTitle, et_propDistrict, et_propLocation, et_propSize,
            et_propNumOfBedRooms, et_propPrice, et_propDescription,
            et_cust_email, et_cust_phone;

    AppCompatSpinner spinner_propType, spinner_propStatus,
            spinner_propPayStatus, spinner_propYear, spinner_propPayMonth;
    String[] str_array_propType = {"<-- Select Property Type -->",
            "Commercial", "Residential"};
    String[] str_array_propStatus = {"<-- Select Property Status -->",
            "Booked", "Available"};
    String[] str_array_propPayStatus = {"<-- Select Property Pay Status -->",
            "Paid", "Unpaid"};
    String[] str_array_propYear = {"<-- Select Property Year -->",
            "1995", "1996", "1997", "1998", "1999", "2000"};
    String[] str_array_propPayMonth = {"<-- Select Property Month Pay -->",
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

    String str_selected_prop_type, str_selected_prop_status, str_selected_prop_pay_status,
            str_selected_prop_year, str_selected_prop_pay_month;

    Button btnAddProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_add);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialization
        init();

        retrieveCustEmail();

        btnAddProperty.setOnClickListener(v -> checkConnectivity());

    }

    private void init() {

        et_propTitle = findViewById(R.id.id_et_prop_title);
        et_propDistrict = findViewById(R.id.id_et_prop_district);
        et_propLocation = findViewById(R.id.id_et_prop_location);
        et_propSize = findViewById(R.id.id_et_prop_size);
        et_propNumOfBedRooms = findViewById(R.id.id_et_prop_no_of_rooms);
        et_propPrice = findViewById(R.id.id_et_prop_price);
        et_propDescription = findViewById(R.id.id_et_prop_desc);
        et_cust_email = findViewById(R.id.id_et_prop_cust_email);
        et_cust_phone = findViewById(R.id.id_et_prop_cust_phone);
        btnAddProperty = findViewById(R.id.id_btn_prop_add);

        spinner_propType = findViewById(R.id.id_spinner_prop_type);
        spinner_propStatus = findViewById(R.id.id_spinner_prop_status);
        spinner_propPayStatus = findViewById(R.id.id_spinner_prop_pay_status);
        spinner_propYear = findViewById(R.id.id_spinner_prop_year);
        spinner_propPayMonth = findViewById(R.id.id_spinner_prop_pay_month);

        ListAdapter propTypeAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, str_array_propType);
        spinner_propType.setAdapter((SpinnerAdapter) propTypeAdapter);

        ListAdapter propStatusAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, str_array_propStatus);
        spinner_propStatus.setAdapter((SpinnerAdapter) propStatusAdapter);

        ListAdapter propPayStatusAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, str_array_propPayStatus);
        spinner_propPayStatus.setAdapter((SpinnerAdapter) propPayStatusAdapter);

        ListAdapter propYearAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, str_array_propYear);
        spinner_propYear.setAdapter((SpinnerAdapter) propYearAdapter);

        ListAdapter propPayMonthAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, str_array_propPayMonth);
        spinner_propPayMonth.setAdapter((SpinnerAdapter) propPayMonthAdapter);

    }

    public void retrieveCustEmail(){

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_cust_phone_email_sharedPref", MODE_PRIVATE);

        String status_phone = sharedPreferences.getString("cust_status_phone", "");
        String status_email = sharedPreferences.getString("cust_status_email", "");

    }

    private void addProperty() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering new property. Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //Spinners getting selected item
        str_selected_prop_type = spinner_propType.getSelectedItem().toString();
        str_selected_prop_status = spinner_propStatus.getSelectedItem().toString();
        str_selected_prop_pay_status = spinner_propPayStatus.getSelectedItem().toString();
        str_selected_prop_year = spinner_propYear.getSelectedItem().toString();
        str_selected_prop_pay_month = spinner_propPayMonth.getSelectedItem().toString();

        //EditText
        String str_propTitle = et_propTitle.getText().toString();
        String str_propDistrict = et_propDistrict.getText().toString();
        String str_propLocation = et_propLocation.getText().toString();
        String str_propSize = et_propSize.getText().toString();
        String str_propNumOfBedRooms = et_propNumOfBedRooms.getText().toString();
        String str_propPrice = et_propPrice.getText().toString();
        String str_propDescription = et_propDescription.getText().toString();
        String str_cust_email = et_cust_email.getText().toString();
        String str_cust_phone = et_cust_phone.getText().toString();

        if (str_selected_prop_type.equals("<-- Select Property Type -->")) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else if (str_selected_prop_status.equals("<-- Select Property Status -->")) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else if (str_selected_prop_pay_status.equals("<-- Select Property Pay Status -->")) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else if (str_selected_prop_year.equals("<-- Select Property Year -->")) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else if (str_selected_prop_pay_month.equals("<-- Select Property Month Pay -->")) {
            Toast.makeText(this, "Please indicate the pay status for a month", Toast.LENGTH_SHORT).show();

        } else if (str_propTitle.isEmpty()) {

            et_propTitle.setError("This Field is required!");
            progressDialog.dismiss();
            et_propTitle.requestFocus();

        }else if(str_propDistrict.isEmpty()){

            et_propDistrict.setError("This Field is required!");
            progressDialog.dismiss();
            et_propDistrict.requestFocus();

        }else if(str_propLocation.isEmpty()){

            et_propLocation.setError("This Field is required!");
            progressDialog.dismiss();
            et_propLocation.requestFocus();

        }else if(str_propSize.isEmpty()){

            et_propSize.setError("This Field is required!");
            progressDialog.dismiss();
            et_propSize.requestFocus();

        }else if(str_propNumOfBedRooms.isEmpty()){

            et_propNumOfBedRooms.setError("This Field is required!");
            progressDialog.dismiss();
            et_propNumOfBedRooms.requestFocus();

        }else if(str_propPrice.isEmpty()){

            et_propPrice.setError("This Field is required!");
            progressDialog.dismiss();
            et_propPrice.requestFocus();

        }else if(str_propDescription.isEmpty()){

            et_propDescription.setError("This Field is required!");
            progressDialog.dismiss();
            et_propDescription.requestFocus();

        }else if(str_cust_email.isEmpty()){

            et_cust_email.setError("This Field is required!");
            progressDialog.dismiss();
            et_cust_email.requestFocus();

        }else if(str_cust_phone.isEmpty()){

            et_cust_phone.setError("This Field is required!");
            progressDialog.dismiss();
            et_cust_phone.requestFocus();

        }else{

            //Add property to database
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.url) + "insert_property.php",
                    response -> {

                        progressDialog.dismiss();
                        if (response.equalsIgnoreCase("Property created!")) {

                            Toast.makeText(ActivityPropertyAdd.this, response, Toast.LENGTH_LONG).show();
                            Intent gotoUsers = new Intent(ActivityPropertyAdd.this,
                                    ActivityProperties.class);

                            startActivity(gotoUsers);
                            finish();

                        } else {

                            Toast.makeText(ActivityPropertyAdd.this, "Oops!! " +
                                    response, Toast.LENGTH_SHORT).show();

                        }

                    }, error -> {

                progressDialog.dismiss();
                Toast.makeText(ActivityPropertyAdd.this,
                        "Ooops!! Somthing happened. " + error.toString(),
                        Toast.LENGTH_LONG).show();

            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("propTitle", str_propTitle);
                    params.put("propDistrict", str_propDistrict);
                    params.put("propLocation", str_propLocation);
                    params.put("propSize", str_propSize);
                    params.put("propNumOfBedRooms", str_propNumOfBedRooms);
                    params.put("propYear", str_selected_prop_year);
                    params.put("propPrice", str_propPrice);
                    params.put("propType", str_selected_prop_type);
                    params.put("propStatus", str_selected_prop_status);
                    params.put("propPayStatus", str_selected_prop_pay_status);
                    params.put("propPayMonth", str_selected_prop_pay_month);
                    params.put("propDescription", str_propDescription);
                    params.put("cust_email", str_cust_email);
                    params.put("cust_phone", str_cust_phone);

                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }

    }

    //Checking if device has an internet connection
    private void checkConnectivity() {
        boolean wifiConnected;
        boolean mobileConnected;

        ConnectivityManager connectivitymanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connectivitymanager.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {

            //internet connection is available
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;

            if (wifiConnected) {

                addProperty();

            } else if (mobileConnected) {

                addProperty();

            }

        } else {

            Toast toast = Toast.makeText(this, "Login failed. " +
                    "\nCheck your internet connection and try again.", Toast.LENGTH_LONG);
            LinearLayout layout = (LinearLayout) toast.getView();
            if (layout.getChildCount() > 0) {
                TextView tv = (TextView) layout.getChildAt(0);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            }
            toast.show();
        }
    }

}