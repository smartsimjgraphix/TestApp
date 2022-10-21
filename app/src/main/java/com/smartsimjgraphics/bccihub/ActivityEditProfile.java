package com.smartsimjgraphics.bccihub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class ActivityEditProfile extends AppCompatActivity {

    Button btnUpdateUserDetails;
    EditText et_cust_username, et_cust_phone, et_cust_email, et_cust_password,
            et_cust_conf_password;

    String et_str_cust_username, et_str_cust_phone, et_str_cust_email,
            et_str_cust_password, et_str_cust_conf_password, str_value_data_username;

    RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialize components
        init();

        btnUpdateUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Action update user details
                validatePasswords();

            }
        });

    }

    public void retrieveCustEmail(){

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_session_user_email", MODE_PRIVATE);

        str_value_data_username =
                sharedPreferences.getString("key_session_username", "");

    }

    private void getUserDetaills(){

        //Get SharedPref From Registraion page
        SharedPreferences sharedPref_getCustomer = getSharedPreferences("sharedPref_reg_details", MODE_PRIVATE);
        String cust_fname = sharedPref_getCustomer.getString("reg_user_sharedPref_fname", "");
        String cust_lname = sharedPref_getCustomer.getString("reg_user_haredPref_lname", "");
        String cust_username = sharedPref_getCustomer.getString("reg_user_haredPref_username", "");
        String cust_pass = sharedPref_getCustomer.getString("reg_user_sharedPref_password", "");
        String cust_phone_number = sharedPref_getCustomer.getString("reg_user_sharedPref_phone_number", "");
        String cust_address = sharedPref_getCustomer.getString("reg_user_sharedPref_address", "");
        String cust_region = sharedPref_getCustomer.getString("reg_user_sharedPref_RegionSelected", "");
        String cust_user_area_or_state = sharedPref_getCustomer.getString("reg_user_sharedPref_AreaState", "");
        String cust_postal_code = sharedPref_getCustomer.getString("reg_user_sharedPref_postal_code", "");
        String cust_country = sharedPref_getCustomer.getString("reg_user_sharedPref_CountrySelected", "");
        String cust_credit_limit = sharedPref_getCustomer.getString("reg_user_sharedPref_credit_limit", "");
        String cust_occupation = sharedPref_getCustomer.getString("reg_user_sharedPref_occupation", "");
        String cust_email = sharedPref_getCustomer.getString("reg_user_sharedPref_email", "");
        String cust_fax = sharedPref_getCustomer.getString("reg_user_sharedPref_fax", "");

    }

    private void init() {

        btnUpdateUserDetails = findViewById(R.id.btn_update_user_details);

        et_cust_username = findViewById(R.id.edit_user_username);
        et_cust_phone = findViewById(R.id.edit_user_phone_number);
        et_cust_email = findViewById(R.id.edit_user_email);
        et_cust_password = findViewById(R.id.edit_user_password);
        et_cust_conf_password = findViewById(R.id.edit_user_confirm_password);

    }

    private void validatePasswords() {

        if (et_cust_password.getText().toString().equals(et_cust_conf_password.getText().toString())) {

            editProfile();

        } else {

            Toast.makeText(this, "passwords do not match", Toast.LENGTH_SHORT).show();

        }
    }

    private void editProfile() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating your profile details. Please Wait...");

        et_str_cust_username = et_cust_username.getText().toString();
        et_str_cust_password = et_cust_password.getText().toString();
        et_str_cust_conf_password = et_cust_conf_password.getText().toString();
        et_str_cust_phone = et_cust_phone.getText().toString();
        et_str_cust_email = et_cust_email.getText().toString();

        if (et_str_cust_username.isEmpty()) {
            et_cust_username.setError("This Field is required!");
            progressDialog.dismiss();
            et_cust_username.requestFocus();

        } else if (et_str_cust_password.isEmpty()) {
            et_cust_password.setError("This Field is required!");
            progressDialog.dismiss();
            et_cust_password.requestFocus();

        } else if (et_str_cust_conf_password.isEmpty()) {
            et_cust_conf_password.setError("This Field is required!");
            progressDialog.dismiss();
            et_cust_conf_password.requestFocus();

        } else if (et_str_cust_phone.isEmpty()) {
            et_cust_phone.setError("This Field is required!");
            progressDialog.dismiss();
            et_cust_phone.requestFocus();

        } else if (et_str_cust_email.isEmpty()) {
            et_cust_email.setError("This Field is required!");
            progressDialog.dismiss();
            et_cust_email.requestFocus();

        } else {

            progressDialog.show();

            //Edit user details here...
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.url) + "update_user_details.php",
                    response -> {

                        progressDialog.dismiss();

                        if (response.equalsIgnoreCase("Account Updated!")) {

                            Toast.makeText(ActivityEditProfile.this, response, Toast.LENGTH_LONG).show();

                            Intent gotoUsers = new Intent(ActivityEditProfile.this,
                                    ActivityCustomerDashBoard.class);

                            startActivity(gotoUsers);
                            finish();

                        } else {

                            Toast.makeText(ActivityEditProfile.this, "Oops!! " +
                                    response, Toast.LENGTH_SHORT).show();

                        }

                    }, error -> {

                progressDialog.dismiss();
                Toast.makeText(ActivityEditProfile.this, "Ooops!! Somthing happened. " + error.toString(), Toast.LENGTH_LONG).show();

            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("cust_username", et_str_cust_username);
                    params.put("cust_password", et_str_cust_password);
                    params.put("cust_phone", et_str_cust_phone);
                    params.put("cust_email", et_str_cust_email);

                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }

    }

    //Checking Connectivity
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
                //Action here
                validatePasswords();

            } else if (mobileConnected) {
                //Action here
                validatePasswords();

            }
        } else {

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