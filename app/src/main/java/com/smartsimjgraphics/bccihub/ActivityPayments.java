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
import com.smartsimjgraphics.bccihub.payments.AdapterPayments;
import com.smartsimjgraphics.bccihub.payments.Payments_Model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ActivityPayments extends AppCompatActivity {

    private RecyclerView recyclerViewPayments;
    private AdapterPayments adapterPayments;
    private ArrayList<Payments_Model> payments;
    RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialization
        init();

        retrieveCustEmail();

        //retrieve info on all payments
        checkConnectivity();

    }

    private void init(){

        recyclerViewPayments = findViewById(R.id.recyclerViewPayments);
        rQueue = Volley.newRequestQueue(getApplicationContext());
        //Link the layout to the data
//        adapter = new MyCustomeAdapter(this, Data.getData());
//        recyclerView.setAdapter(adapter);
//        //set the layout
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapterPayments = new AdapterPayments();

        recyclerViewPayments.setHasFixedSize(true);
        recyclerViewPayments.setLayoutManager(new LinearLayoutManager(this));

        payments = new ArrayList<>();
        recyclerViewPayments.setAdapter(adapterPayments);


    }

    public void retrieveCustEmail(){

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_cust_phone_email_sharedPref", MODE_PRIVATE);

        String status_phone = sharedPreferences.getString("cust_status_phone", "");
        String status_email = sharedPreferences.getString("cust_status_email", "");

    }


    private void loadRecyclerViewPaymentsData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Payment Information");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                getResources().getString(R.string.url) +
                        "retrieve_all_payments.php",
                response -> {

                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);

                            Payments_Model payments_model = new Payments_Model();
                            payments_model.setPayID(jsonObject.getInt("payID"));
                            payments_model.setPay_source(jsonObject.getString("pay_source"));
                            payments_model.setPay_date_time(jsonObject.getString("pay_date_time"));
                            payments_model.setCust_username(jsonObject.getString("cust_username"));
                            payments_model.setCust_phone(jsonObject.getString("cust_email"));
                            payments_model.setCust_phone(jsonObject.getString("cust_phone"));

                            payments.add(payments_model);

                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                        Toast.makeText(ActivityPayments.this, "JSON not valid",
                                Toast.LENGTH_SHORT).show();

                    }

                    adapterPayments.setData(payments);
                    adapterPayments.notifyDataSetChanged();

                    progressDialog.dismiss();

                }, error -> {

            progressDialog.dismiss();
            Toast.makeText(ActivityPayments.this, "Error " +
                    "Occured" + error.getMessage(), Toast.LENGTH_LONG).show();
        });

        rQueue.add(jsonArrayRequest);

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
                //Action here
                loadRecyclerViewPaymentsData();

            } else if (mobileConnected) {
                //Action here
                loadRecyclerViewPaymentsData();

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