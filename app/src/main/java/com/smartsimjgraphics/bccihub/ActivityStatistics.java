package com.smartsimjgraphics.bccihub;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityStatistics extends AppCompatActivity {
    TextView count_holder_customers, count_holder_residentials, count_holder_payments,
            count_holder_locations, count_holder_commercials;
    RequestQueue requestQueue;

    String str_value_data_username, str_value_data_password, str_value_data_role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialization
        init();

        retrieveCustEmail();

        //retrieving count data and information
        checkConnectivity();

    }

    private void init(){

        count_holder_customers = findViewById(R.id.id_tv_count_users);
        count_holder_residentials = findViewById(R.id.id_tv_count_residentials);
        count_holder_commercials = findViewById(R.id.id_tv_count_commericals);
        count_holder_payments = findViewById(R.id.id_tv_count_payments);
        count_holder_locations = findViewById(R.id.id_tv_count_locations);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

    }

    public void retrieveCustEmail(){

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_login_details", MODE_PRIVATE);

        str_value_data_username = sharedPreferences.getString("log_in_status_username", "");
        str_value_data_password = sharedPreferences.getString("log_in_status_password", "");
        str_value_data_role = sharedPreferences.getString("log_in_status_role", "");

    }

    private void countCommercials() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.url) + "count_commercials.php",
                response -> {
                    count_holder_commercials.setText(response);

                }, error -> {

            Toast.makeText(ActivityStatistics.this, "Ooops!! "
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

    private void countResidentials() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.url) + "count_residentials.php",
                response -> {
                    count_holder_residentials.setText(response);

                }, error -> {

            Toast.makeText(ActivityStatistics.this, "Ooops!! "
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

    private void countLocations() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.url) + "count_locations.php",
                response -> {
                    count_holder_locations.setText(response);

                }, error -> {

            Toast.makeText(ActivityStatistics.this, "Ooops!! "
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

    private void countPayments() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.url) + "count_payments.php",
                response -> {
                    count_holder_payments.setText(response);

                }, error -> {

            Toast.makeText(ActivityStatistics.this, "Ooops!! "
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

    private void countCustomers() {

        //count_holder_customers.setText("");

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.url) + "count_customers.php",
                response -> {
                    count_holder_customers.setText(response);

                }, error -> {

            Toast.makeText(ActivityStatistics.this, "Ooops!! "
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
                // call count methods
                countCustomers();
                countPayments();
                countCommercials();
                countResidentials();
                countLocations();

            } else if (mobileConnected) {

                //Action
                countCustomers();
                countPayments();
                countCommercials();
                countResidentials();
                countLocations();

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