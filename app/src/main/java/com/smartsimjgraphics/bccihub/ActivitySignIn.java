package com.smartsimjgraphics.bccihub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivitySignIn extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    TextView tv_forgot_password, tv_register_company;
    Boolean single_back = false;
    Button btnSignIn;
    Spinner spinnerLoginRole;

    String selected_user_role, tmp_id;
    String[] str_array_loginRole = {"<-- Select Login Role -->", "Staff Member",
            "Customer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialization
        init();

        btnListeners();

        /*spinnerLoginRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selected_user_role = String.valueOf(adapterView.getSelectedItem());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

    }

    private void btnListeners() {

        btnSignIn.setOnClickListener(v -> {

            //action log in user based on login role...
            checkConnectivity();

        });

        tv_forgot_password.setOnClickListener(v -> {

            Intent gotoForgotPassword = new Intent(ActivitySignIn.this,
                    ActivityForgotPassword.class);
            startActivity(gotoForgotPassword);

        });

        tv_register_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotoRegisterAcivity = new Intent(ActivitySignIn.this,
                        ActivityRegisterBusiness.class);
                startActivity(gotoRegisterAcivity);

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (single_back) {
            super.onBackPressed();
            return;
        }

        this.single_back = true;
        Toast.makeText(this, "Press Back again to exit",
                Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                single_back = false;
            }
        }, 2000);

    }

    private void init() {

        tv_forgot_password = findViewById(R.id.login_tv_forgot);
        tv_register_company = findViewById(R.id.login_tv_registercompany);

        editTextEmail = findViewById(R.id.id_et_email_login);
        editTextPassword = findViewById(R.id.id_et_password_login);
        btnSignIn = findViewById(R.id.id_btn_signin);

        /*spinnerLoginRole = findViewById(R.id.id_spinner_login_role);
        ListAdapter userRoleAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, str_array_loginRole);
        spinnerLoginRole.setAdapter((SpinnerAdapter) userRoleAdapter);*/


    }

    private void login() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login you in. Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        final String str_username = editTextEmail.getText().toString();
        final String str_password = editTextPassword.getText().toString();

        if (str_username.isEmpty()) {
            editTextEmail.setError("This Field is required");
            progressDialog.dismiss();
            editTextEmail.requestFocus();

        } else if (str_password.isEmpty()) {

            editTextPassword.setError("This Field is required");
            progressDialog.dismiss();
            editTextPassword.requestFocus();

        }/*else if(selected_user_role.equalsIgnoreCase("<-- Select Login Role -->")){
            Toast.makeText(ActivitySignIn.this, "Please select login role", Toast.LENGTH_SHORT).show();
        }*/ else {

            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.url) + "authenticate_user.php",
                    response -> {

                        if (response.contains("uid")) {

                            String remove_chars = response.toString();
                            tmp_id = remove_chars.substring(4);
                            progressDialog.dismiss();

                            SharedPreferences prefStoreLogin = getSharedPreferences("bcc_login_details",
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefStoreLogin.edit();
                            editor.putString("log_in_status_username", str_username);
                            editor.putString("log_in_status_password", str_password);
                            editor.putString("log_in_status_role", tmp_id);
                            editor.apply();

                            if(tmp_id.contains("Customer")){
                                //user is customer
                                Intent gotoLogin = new Intent(ActivitySignIn.this,
                                        ActivityCustomerDashBoard.class);
                                //gotoLogin.putExtra("User_ID", tmp_id);
                                startActivity(gotoLogin);

                                Toast.makeText(ActivitySignIn.this, "Welcome " + str_username
                                        /*+response*/, Toast.LENGTH_SHORT).show();

                                finish();

                            }else{
                                //user is Admin
                                Intent gotoLogin = new Intent(ActivitySignIn.this,
                                        ActivityDashBoard.class);
                                //gotoLogin.putExtra("User_ID", tmp_id);
                                startActivity(gotoLogin);

                                Toast.makeText(ActivitySignIn.this, "Welcome " + str_username
                                        /*+response*/, Toast.LENGTH_SHORT).show();

                                finish();
                            }


                        } else if (response.contains("false")) {

                            progressDialog.dismiss();
                            Toast.makeText(ActivitySignIn.this, "Invalid credentials! " +
                                    "Please check your credentials and try again", Toast.LENGTH_SHORT).show();

                        }

                    }, error -> {

                progressDialog.dismiss();
                Toast.makeText(ActivitySignIn.this, "Ooops!! "
                        + error.toString().trim(), Toast.LENGTH_SHORT).show();

            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("user_username", str_username);
                    data.put("user_password", str_password);
                    return data;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }
    }

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
                //Action
                login();
                //progressDialog.dismiss();

            } else if (mobileConnected) {

                //Action
                login();
                //progressDialog.dismiss();

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