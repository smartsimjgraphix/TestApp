package com.smartsimjgraphics.bccihub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

public class ActivityRegisterBusiness extends AppCompatActivity {

    EditText et_company_name, et_company_phone, et_email;
    TextView tvLoginInstead;
    Button btn_register;

    String str_comp_status = "Inactive";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_business);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //Calling Components
        init();

        tvLoginInstead.setOnClickListener(view -> startActivity(new Intent(ActivityRegisterBusiness.this,
                ActivitySignIn.class)));

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkConnectivity();

            }

        });

    }

    private void init() {

        et_company_name = findViewById(R.id.register_et_companyname);
        et_company_phone = findViewById(R.id.register_et_phonenumber);
        et_email = findViewById(R.id.register_et_emailaddress);
        tvLoginInstead = findViewById(R.id.tv_login_instead);
        btn_register = findViewById(R.id.register_btn_register);

    }

    private void registerCompany() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering company. Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        String str_compName = et_company_name.getText().toString();
        String str_phoneNumber = et_company_phone.getText().toString();
        String str_userEmail = et_email.getText().toString();

        //Input fields validation
        if (str_compName.isEmpty()) {

            et_company_name.setError("This field is required");
            progressDialog.dismiss();
            et_company_name.requestFocus();

        } else if (str_phoneNumber.isEmpty()) {

            et_company_phone.setError("This field is required");
            progressDialog.dismiss();
            et_company_phone.requestFocus();

        } else if (str_userEmail.isEmpty()) {

            et_email.setError("This field is required");
            progressDialog.dismiss();
            et_email.requestFocus();

        } else {

            progressDialog.setMessage("Registering Company. Please Wait...");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.url) + "insert_company.php",
                    response -> {

                        if(response.equalsIgnoreCase("Success")){

                            Toast.makeText(ActivityRegisterBusiness.this,
                                    response, Toast.LENGTH_LONG).show();

                            Intent gotoLogin = new Intent(
                                    ActivityRegisterBusiness.this,
                                    ActivityRegisterLoginUser.class);
                            gotoLogin.putExtra("comp_key_comp_name_value", str_compName);
                            startActivity(gotoLogin);

                            progressDialog.dismiss();
                            finish();

                        }else{

                            Toast.makeText(ActivityRegisterBusiness.this,
                                    "Process terminated", Toast.LENGTH_SHORT).show();

                        }

                    }, error -> {Toast.makeText(ActivityRegisterBusiness.this,

                    error.toString(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

            }) {


                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("company_name", str_compName);
                    params.put("company_phone", str_phoneNumber);
                    params.put("company_email", str_userEmail);
                    params.put("comp_status", str_comp_status);
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
                registerCompany();
                //progressDialog.dismiss();

            } else if (mobileConnected) {
                //Action
                registerCompany();
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