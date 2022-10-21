package com.smartsimjgraphics.bccihub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityCustomerComplaint extends AppCompatActivity {
    TextView tvHeaderUsername;
    EditText editTextForumHead, editTextForumDateTime, editTextForumDetails;
    Button btnSendForum;

    String str_date, str_time, cust_username, cust_phone_number,
            cust_email;
    String verify_status_username, verify_status_password, verify_status_comp_name;
    RequestQueue rQueue;

    String str_value_data_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_complaint);
        Objects.requireNonNull(getSupportActionBar()).hide();



        //initializing Components
        init();

        autoDateTime();

        btnSendForum.setOnClickListener(v -> checkConnectivity());

    }

    private void init() {

        editTextForumHead = findViewById(R.id.id_et_complaint_heading);
        editTextForumDetails = findViewById(R.id.et_forum_details);
        btnSendForum = findViewById(R.id.id_btn_send_complaint);

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_user_cred_details", MODE_PRIVATE);
        verify_status_username = sharedPreferences.getString(
                "key_sp_user_created_username", "");
         verify_status_password = sharedPreferences.getString(
                "key_sp_user_created_password", "");
         verify_status_comp_name = sharedPreferences.getString(
                "key_sp_user_created_company", "");

        //tvHeaderUsername = findViewById(R.id.tv_header_subtitle_forum);
        //tvHeaderUsername.setText("Welcome " + cust_username);
        //editTextForumDateTime = findViewById(R.id.id_et_complaint_date_timr)

    }

    public void retrieveCustEmail(){

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_session_user_email", MODE_PRIVATE);

        str_value_data_username =
                sharedPreferences.getString("key_session_username", "");

    }

    private void sendForumDataToServer() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting. Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        final String str_forum_title = editTextForumHead.getText().toString();
        final String str_forum_details = editTextForumDetails.getText().toString();

        if (str_forum_title.isEmpty()) {

            editTextForumDateTime.setError("This Field is required.");

        } else if (str_forum_details.isEmpty()) {

            editTextForumDetails.setError("This Field is required.");

        } else {

            //SEND DATA TO SERVER USING VOLLEY
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.url) + "insert_forum.php",
                    response -> {

                        progressDialog.dismiss();

                        if (response.equalsIgnoreCase("Success")) {

                            Toast.makeText(ActivityCustomerComplaint.this,
                                    response, Toast.LENGTH_LONG).show();

                            Intent gotoCustDash = new Intent(
                                    ActivityCustomerComplaint.this,
                                    ActivityCustomerDashBoard.class);
                            startActivity(gotoCustDash);
                            finish();

                        } else {

                            Toast.makeText(ActivityCustomerComplaint.this, "Oops!! " + response, Toast.LENGTH_SHORT).show();

                        }

                    }, error -> {

                progressDialog.dismiss();
                Toast.makeText(ActivityCustomerComplaint.this, "Something Happened at our End" + error.toString(), Toast.LENGTH_LONG).show();

            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("forum_category", str_forum_title);
                    params.put("forum_desc", str_forum_details);
                    params.put("forum_date", str_date);
                    params.put("forum_time", str_time);
                    params.put("cust_email", verify_status_username);
                    params.put("cust_phone", verify_status_comp_name);

                    return params;

                }
            };

            rQueue = Volley.newRequestQueue(ActivityCustomerComplaint.this);
            rQueue.add(stringRequest);

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
            if (!wifiConnected) {

                //Send data to server
                sendForumDataToServer();

            } else if (!mobileConnected) {

                //send data to server
                sendForumDataToServer();

            }
        } else {

            Toast.makeText(getApplicationContext(), "Not Connected. Turn on Wifi or Mobile Data", Toast.LENGTH_LONG).show();

        }
    }

    private void autoDateTime() {

        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        str_date = thisDate;

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        Date todayTime = new Date();
        String thisTime = currentTime.format(todayTime);
        str_time = thisTime;
        //editTextForumDateTime.setText(thisTime + " " + thisDate);

    }

}