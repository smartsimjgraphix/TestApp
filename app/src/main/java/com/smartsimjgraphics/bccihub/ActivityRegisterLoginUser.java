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

public class ActivityRegisterLoginUser extends AppCompatActivity {

    EditText et_comp_name_cred, et_user_password_cred, et_username_cred;
    Button btn_register_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login_user);
        Objects.requireNonNull(getSupportActionBar()).hide();

        init();

        btn_register_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkConnectivity();

            }

        });
    }

    private void init() {

        et_comp_name_cred = findViewById(R.id.et_comp_name_cred);
        et_user_password_cred = findViewById(R.id.et_user_password_cred);
        et_username_cred = findViewById(R.id.et_username_cred);
        btn_register_user = findViewById(R.id.btn_btn_create_user_cred);

    }

    private void registerUserCred() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating user credentials. Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        String str_compName = et_comp_name_cred.getText().toString();
        String str_userPassword = et_user_password_cred.getText().toString();
        String str_userName = et_username_cred.getText().toString();

        //Input fields validation
        if (str_compName.isEmpty()) {

            et_comp_name_cred.setError("This field is required");
            progressDialog.dismiss();
            et_comp_name_cred.requestFocus();

        } else if (str_userName.isEmpty()) {

            et_username_cred.setError("This field is required");
            progressDialog.dismiss();
            et_username_cred.requestFocus();

        } else if (str_userPassword.isEmpty()) {

            et_user_password_cred.setError("This field is required");
            progressDialog.dismiss();
            et_user_password_cred.requestFocus();

        } else {

            progressDialog.setMessage("Processing. Please Wait...");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.url) + "register_user_cred.php",
                    response -> {

                        if(response.equalsIgnoreCase("Success")){

                            Toast.makeText(ActivityRegisterLoginUser.this,
                                    response, Toast.LENGTH_LONG).show();

                            SharedPreferences prefStoreLogin = getSharedPreferences("bcc_user_cred_details",
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefStoreLogin.edit();
                            editor.putString("key_sp_user_created_username", str_userName);
                            editor.putString("key_sp_user_created_password", str_userPassword);
                            editor.putString("key_sp_user_created_company", str_compName);

                            editor.apply();

                            Intent gotoLogin = new Intent(
                                    ActivityRegisterLoginUser.this,
                                    ActivitySignIn.class);
                            startActivity(gotoLogin);

                            progressDialog.dismiss();
                            finish();

                        }else{

                            Toast.makeText(ActivityRegisterLoginUser.this,
                                    "Process terminated", Toast.LENGTH_SHORT).show();

                        }

                    }, error -> {Toast.makeText(ActivityRegisterLoginUser.this,

                    error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

            }) {


                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("user_password", str_userPassword);
                    params.put("user_username", str_userName);
                    params.put("user_role", "Customer");
                    return params;
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
                registerUserCred();
                //progressDialog.dismiss();

            } else if (mobileConnected) {
                //Action
                registerUserCred();
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