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
import android.widget.Spinner;
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

public class ActivityUserAdd extends AppCompatActivity {

    Button btnAddNewUser;
    EditText et_cust_fname, et_cust_lname, et_cust_username,
            et_cust_password, et_cust_conf_password, et_cust_phone, et_cust_address, et_cust_city, et_cust_occupation,
            et_cust_email, et_cust_fax;

    String et_str_cust_type, et_str_cust_fname, et_str_cust_lname, et_str_cust_username,
            et_str_cust_password, et_str_cust_conf_password, et_str_cust_phone,
            et_str_cust_address, et_str_cust_city, et_str_cust_occupation,
            et_str_cust_email, et_str_cust_fax, final_account_type;

    Spinner sp_cust_type;
    String[] account_types = {"<-- SELECT USER TYPE -->", "Admin", "Customer"};
    RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialize components
        init();

        btnAddNewUser.setOnClickListener(v -> checkConnectivity());

    }

    private void init() {

        btnAddNewUser = findViewById(R.id.id_btn_add_new_user);

        sp_cust_type = findViewById(R.id.id_spinner_reg_user_type);
        et_cust_fname = findViewById(R.id.id_et_reg_user_fname);
        et_cust_lname = findViewById(R.id.id_et_reg_user_lname);
        et_cust_username = findViewById(R.id.id_et_reg_user_username);
        et_cust_password = findViewById(R.id.id_et_reg_user_password);
        et_cust_conf_password = findViewById(R.id.id_et_reg_user_conf_password);
        et_cust_phone = findViewById(R.id.id_et_reg_user_phone);
        et_cust_address = findViewById(R.id.id_et_reg_user_address);
        et_cust_city = findViewById(R.id.id_et_reg_user_city);
        et_cust_occupation = findViewById(R.id.id_et_reg_user_occupation);
        et_cust_email = findViewById(R.id.id_et_reg_user_email);
        et_cust_fax = findViewById(R.id.id_et_reg_user_fax);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, account_types);
        sp_cust_type.setAdapter(arrayAdapter);

    }

    private void validatePasswords() {

        if (et_cust_password.getText().toString().equals(et_cust_conf_password.getText().toString())) {

            registerUser();

        } else {

            Toast.makeText(this, "passwords do not match", Toast.LENGTH_SHORT).show();

        }
    }

    private void registerUser() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering new user. Please Wait...");

        et_str_cust_type = sp_cust_type.getSelectedItem().toString();
        et_str_cust_fname = et_cust_fname.getText().toString();
        et_str_cust_lname = et_cust_lname.getText().toString();
        et_str_cust_username = et_cust_username.getText().toString();
        et_str_cust_password = et_cust_password.getText().toString();
        et_str_cust_conf_password = et_cust_conf_password.getText().toString();
        et_str_cust_phone = et_cust_phone.getText().toString();
        et_str_cust_address = et_cust_address.getText().toString();
        et_str_cust_city = et_cust_city.getText().toString();
        et_str_cust_occupation = et_cust_occupation.getText().toString();
        et_str_cust_email = et_cust_email.getText().toString();
        et_str_cust_fax = et_cust_fax.getText().toString();


        if (et_str_cust_type.equalsIgnoreCase("<-- SELECT USER TYPE -->")) {
            Toast.makeText(this, "Select user role please...", Toast.LENGTH_SHORT).show();

        } else if (et_str_cust_fname.isEmpty()) {
            et_cust_fname.setError("This Field is required!");
            progressDialog.dismiss();
            et_cust_fname.requestFocus();

        } else if (et_str_cust_lname.isEmpty()) {
            et_cust_lname.setError("This Field is required!");
            progressDialog.dismiss();
            et_cust_lname.requestFocus();

        } else if (et_str_cust_username.isEmpty()) {
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

        } else if (et_str_cust_address.isEmpty()) {
            et_cust_address.setError("This Field is required!");
            progressDialog.dismiss();
            et_cust_address.requestFocus();

        } else if (et_str_cust_city.isEmpty()) {
            et_cust_city.setError("This Field is required!");
            progressDialog.dismiss();
            et_cust_city.requestFocus();

        } else if (et_str_cust_occupation.isEmpty()) {
            et_cust_occupation.setError("This Field is required!");
            progressDialog.dismiss();
            et_cust_occupation.requestFocus();

        } else if (et_str_cust_email.isEmpty()) {
            et_cust_email.setError("This Field is required!");
            progressDialog.dismiss();
            et_cust_email.requestFocus();

        } else if (et_str_cust_fax.isEmpty()) {
            et_cust_fax.setError("This Field is required!");
            progressDialog.dismiss();
            et_cust_fax.requestFocus();

        } else {

            progressDialog.show();

            SharedPreferences prefStoreRegUserRole = getSharedPreferences("registeredUserRole_Details",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefStoreRegUserRole.edit();
            editor.putString("register_user_role", final_account_type);
            editor.apply();

            //Add user here...
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.url) + "insert_user.php",
                    response -> {


                        progressDialog.dismiss();

                        if (response.equalsIgnoreCase("Account created!")) {

                            SharedPreferences prefStorePhoneEmail = getSharedPreferences("bcc_cust_phone_email_sharedPref",
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorSharedPref = prefStorePhoneEmail.edit();
                            editorSharedPref.putString("cust_status_phone", et_str_cust_phone);
                            editorSharedPref.putString("cust_status_email", et_str_cust_email);
                            editorSharedPref.apply();

                            Toast.makeText(ActivityUserAdd.this, response, Toast.LENGTH_LONG).show();


                            Intent gotoUsers = new Intent(ActivityUserAdd.this,
                                    ActivityDashBoard.class);

                            startActivity(gotoUsers);
                            finish();

                        } else {

                            Toast.makeText(ActivityUserAdd.this, "Oops!! " +
                                    response, Toast.LENGTH_SHORT).show();

                        }

                    }, error -> {

                progressDialog.dismiss();
                Toast.makeText(ActivityUserAdd.this,
                        "Ooops!! Somthing happened. " +
                                error.toString(), Toast.LENGTH_LONG).show();

            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("cust_type", et_str_cust_type);
                    params.put("cust_fname", et_str_cust_fname);
                    params.put("cust_lname", et_str_cust_lname);
                    params.put("cust_username", et_str_cust_username);
                    params.put("cust_password", et_str_cust_password);
                    params.put("cust_phone", et_str_cust_phone);
                    params.put("cust_address", et_str_cust_address);
                    params.put("cust_city", et_str_cust_city);
                    params.put("cust_occupation", et_str_cust_occupation);
                    params.put("cust_email", et_str_cust_email);
                    params.put("cust_fax", et_str_cust_fax);

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