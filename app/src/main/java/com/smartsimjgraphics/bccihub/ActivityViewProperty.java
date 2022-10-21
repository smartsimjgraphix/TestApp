package com.smartsimjgraphics.bccihub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityViewProperty extends AppCompatActivity {

    TextView tv_propID, tv_propTitle, tv_propDistrict, tv_propLocation, tv_propSize,
            tv_propNumOfBedRooms, tv_propYear, tv_propPrice, tv_propType, tv_propStatus,
            tv_propPayStatus, tv_propPayMonth, tv_propDescription, tv_cust_email, tv_cust_phone;

    Button btnChangeStatus, btnDeleteProperty;
    String str_propID, str_propTitle, str_propDistrict, str_propLocation, str_propSize,
            str_propNumOfBedRooms, str_propYear, str_propPrice, str_propType, str_propStatus,
            str_propPayStatus, str_propPayMonth, str_propDescription, str_cust_email, str_cust_phone;

    String str_value_data_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initiate components
        init();

        Intent intent = getIntent();

        if (intent == null) {

            Toast.makeText(this, "No Data transferred", Toast.LENGTH_SHORT).show();

        } else {

            String str_prop_id = intent.getStringExtra("key_propID");
            String str_prop_title = intent.getStringExtra("key_propTitle");
            String str_prop_district = intent.getStringExtra("key_propDistrict");
            String str_prop_location = intent.getStringExtra("key_propLocation");
            String str_prop_size = intent.getStringExtra("key_propSize");
            String str_number_of_bed_rooms = intent.getStringExtra("key_propNumOfBedRooms");
            String str_property_year = intent.getStringExtra("key_propYear");
            String str_property_price = intent.getStringExtra("key_propPrice");
            String str_property_type = intent.getStringExtra("key_propType");
            String str_property_status = intent.getStringExtra("key_propStatus");
            String str_property_pay_status = intent.getStringExtra("key_propPayStatus");
            String str_property_pay_month = intent.getStringExtra("key_propPayMonth");
            String str_property_description = intent.getStringExtra("key_propDescription");
            String str_property_email = intent.getStringExtra("key_cust_email");
            String str_property_phone = intent.getStringExtra("key_cust_phone");

            tv_propID.setText(str_prop_id);
            tv_propTitle.setText(str_prop_title);
            tv_propDistrict.setText(str_prop_district);
            tv_propLocation.setText(str_prop_location);
            tv_propSize.setText(str_prop_size);
            tv_propNumOfBedRooms.setText(str_number_of_bed_rooms);
            tv_propYear.setText(str_property_year);
            tv_propPrice.setText(str_property_price);
            tv_propType.setText(str_property_type);
            tv_propStatus.setText(str_property_status);
            tv_propPayStatus.setText(str_property_pay_status);
            tv_propPayMonth.setText(str_property_pay_month);
            tv_propDescription.setText(str_property_description);
            tv_cust_email.setText(str_property_email);
            tv_cust_phone.setText(str_property_phone);

        }

        retrieveCustEmail();

        btnChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatePropertyStatus();

            }
        });

        btnDeleteProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteProperty();

            }
        });

    }

    private void init() {

        tv_propID = findViewById(R.id.id_tv_view_prop_id);
        tv_propTitle = findViewById(R.id.id_tv_view_prop_title);
        tv_propDistrict = findViewById(R.id.id_tv_view_prop_district);
        tv_propLocation = findViewById(R.id.id_tv_view_prop_location);
        tv_propSize = findViewById(R.id.id_tv_view_prop_size);
        tv_propNumOfBedRooms = findViewById(R.id.id_tv_view_prop_no_of_rooms);
        tv_propYear = findViewById(R.id.id_tv_view_prop_year);
        tv_propPrice = findViewById(R.id.id_tv_view_prop_price);
        tv_propType = findViewById(R.id.id_tv_view_prop_type);
        tv_propStatus = findViewById(R.id.id_tv_view_prop_status);
        tv_propPayStatus = findViewById(R.id.id_tv_view_prop_pay_status);
        tv_propPayMonth = findViewById(R.id.id_tv_view_prop_pay_month);
        tv_propDescription = findViewById(R.id.id_tv_view_prop_desc);
        tv_cust_email = findViewById(R.id.id_tv_view_prop_cust_email);
        tv_cust_phone = findViewById(R.id.id_tv_view_prop_cust_phone);

        btnChangeStatus = findViewById(R.id.id_btn_change_status_prop);
        btnDeleteProperty = findViewById(R.id.id_btn_delete_prop);

    }

    private void retrieveCustEmail(){

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_session_user_email", MODE_PRIVATE);

        str_value_data_username =
                sharedPreferences.getString("key_session_username", "");

    }

    private void updatePropertyStatus() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating property status. Please Wait...");

        str_propID = tv_propID.getText().toString();
        str_propTitle = tv_propTitle.getText().toString();
        str_propDistrict = tv_propDistrict.getText().toString();
        str_propLocation = tv_propLocation.getText().toString();
        str_propSize = tv_propSize.getText().toString();
        str_propNumOfBedRooms = tv_propNumOfBedRooms.getText().toString();
        str_propYear = tv_propYear.getText().toString();
        str_propPrice = tv_propPrice.getText().toString();
        str_propType = tv_propType.getText().toString();
        str_propStatus = tv_propStatus.getText().toString();
        str_propPayStatus = tv_propPayStatus.getText().toString();
        str_propPayMonth = tv_propPayMonth.getText().toString();
        str_propDescription = tv_propDescription.getText().toString();
        str_cust_email = tv_cust_email.getText().toString();
        str_cust_phone = tv_cust_phone.getText().toString();

        progressDialog.show();

        //Add user here...
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.url) +
                        "update_property_details.php?cust_email"+str_value_data_username,
                response -> {

                    progressDialog.dismiss();

                    if (response.equalsIgnoreCase("Property Updated")) {

                        Toast.makeText(ActivityViewProperty.this, "Property details updated successfully", Toast.LENGTH_LONG).show();

                        Intent gotoUsers = new Intent(ActivityViewProperty.this,
                                ActivityDashBoard.class);

                        startActivity(gotoUsers);
                        finish();

                    } else {

                        Toast.makeText(ActivityViewProperty.this, "Oops!! " +
                                response, Toast.LENGTH_SHORT).show();

                    }

                }, error -> {

            progressDialog.dismiss();
            Toast.makeText(ActivityViewProperty.this, "Ooops!! Somthing happened. " + error.toString(), Toast.LENGTH_LONG).show();

        }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("propTitle", str_propTitle);
                params.put("propDistrict", str_propDistrict);
                params.put("propLocation", str_propLocation);
                params.put("propSize", str_propSize);
                params.put("propNumOfBedRooms", str_propNumOfBedRooms);
                params.put("propYear", str_propYear);
                params.put("propPrice", str_propPrice);
                params.put("propType", str_propType);
                params.put("propStatus", str_propStatus);
                params.put("propPayStatus", str_propPayStatus);
                params.put("propPayMonth", str_propPayMonth);
                params.put("propDescription", str_propDescription);
                params.put("cust_email", str_cust_email);
                params.put("cust_phone", str_cust_phone);
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void deleteProperty() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("deleting property. Please Wait...");

        str_propID = tv_propID.getText().toString();
        str_propTitle = tv_propTitle.getText().toString();
        str_propYear = tv_propYear.getText().toString();
        str_propPrice = tv_propPrice.getText().toString();
        str_cust_email = tv_cust_email.getText().toString();
        str_cust_phone = tv_cust_phone.getText().toString();

        if (str_propID.isEmpty()) {

            Toast.makeText(this, "Requested fields cannot be empty...", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            tv_propID.setTextColor(Color.RED);

        } else if (str_propYear.isEmpty()) {

            Toast.makeText(this, "Requested fields cannot be empty...", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            tv_propYear.setTextColor(Color.RED);

        } else {

            progressDialog.show();

            //Add user here...
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.url) + "delete_property.php",
                    response -> {


                        progressDialog.dismiss();

                        if (response.equalsIgnoreCase("Property Deleted")) {

                            Toast.makeText(ActivityViewProperty.this, "Action detele successful on "+ str_propTitle , Toast.LENGTH_LONG).show();

                            Intent gotoUsers = new Intent(ActivityViewProperty.this,
                                    ActivityDashBoard.class);

                            startActivity(gotoUsers);
                            finish();

                        } else {

                            Toast.makeText(ActivityViewProperty.this, "Oops!! " +
                                    response, Toast.LENGTH_SHORT).show();

                        }

                    }, error -> {

                progressDialog.dismiss();
                Toast.makeText(ActivityViewProperty.this, "Ooops!! Somthing happened. " + error.toString(), Toast.LENGTH_LONG).show();

            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("propID", str_propID);
                    params.put("propTitle", str_propTitle);
                    params.put("propYear", str_propYear);
                    params.put("propPrice", str_propPrice);
                    params.put("cust_email", str_cust_email);
                    params.put("cust_phone", str_cust_phone);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }


    }


}