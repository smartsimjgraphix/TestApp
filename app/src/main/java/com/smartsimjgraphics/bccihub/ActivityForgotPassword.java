package com.smartsimjgraphics.bccihub;

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

public class ActivityForgotPassword extends AppCompatActivity {
    EditText et_forg_email;
    Button btnPasswordForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialize components
        init();

        //button reset action
        btnPasswordForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkConnectivity();
            }
        });

    }

    private void init() {

        et_forg_email = findViewById(R.id.forgot_et_email);
        btnPasswordForgot = findViewById(R.id.forgot_btn_submit);

    }

    private void resetPassword() {

        String str_mail = et_forg_email.getText().toString();

        if (!str_mail.isEmpty()) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        getResources().getString(R.string.url) +
                                "forgot_password.php",
                        response -> {

                            if (response.equalsIgnoreCase("Success")) {
                                //email reset success

                                Toast.makeText(ActivityForgotPassword.this, response,
                                        Toast.LENGTH_LONG).show();
                                Intent gotoLogin = new Intent(ActivityForgotPassword.this,
                                        ActivitySignIn.class);
                                startActivity(gotoLogin);
                                finish();

                            } else {

                                //email reset failled
                                Toast.makeText(ActivityForgotPassword.this, "" +
                                        "Failled to reset password. Try again later", Toast.LENGTH_SHORT).show();
                            }

                        },

                        error -> Toast.makeText(ActivityForgotPassword.this, error.toString(), Toast.LENGTH_LONG).show()) {
                    protected Map<String, String> getParams() {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_username", str_mail);
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
                resetPassword();
            } else if (mobileConnected) {
                //Action
                resetPassword();
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