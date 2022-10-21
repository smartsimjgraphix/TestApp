package com.smartsimjgraphics.bccihub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ActivityCustomerDashBoard extends AppCompatActivity {

    String str_intent_cust_id, str_intent_cust_type, str_intent_cust_phone,
            str_intent_cust_email, str_intent_cust_fax;

    String verify_status_username, verify_status_password,
            verify_status_role, verify_status_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dash_board);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //retrieving key details for a specified user
        getCurrentUser();

    }

    private void getCurrentUser(){

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_login_details", MODE_PRIVATE);

        String str_value_data_username = sharedPreferences.getString("log_in_status_username", "");
        String str_value_data_password = sharedPreferences.getString("log_in_status_password", "");
        String str_value_data_role = sharedPreferences.getString("log_in_status_role", "");

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.url) +
                        "retrive_curr_user_email.php?cust_username="
                        +str_value_data_username,
                response -> {

                    String str_current_mail = response;
                    SharedPreferences prefStoreLogin = getSharedPreferences("bcc_session_user_email",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefStoreLogin.edit();
                    editor.putString("key_session_username", str_current_mail);
                    editor.apply();

                    Toast.makeText(ActivityCustomerDashBoard.this, "Email retrieved is: "
                            + str_current_mail, Toast.LENGTH_LONG).show();

                }, error -> {


            Toast.makeText(ActivityCustomerDashBoard.this, "Ooops!! "
                    + error.toString().trim(), Toast.LENGTH_LONG).show();

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("user_username", "verify_status_username");
                //data.put("user_password", str_password);
                return data;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    public void viewMyAccount(View view) {
        //giving a user choices
        actionSelectAccountOptions();

    }

    public void showMyProperties(View view) {

        Intent intentProperty = new Intent(ActivityCustomerDashBoard.this,
                ActivityCustomerViewOwnProperty.class);
        intentProperty.putExtra("intent_key_user_id", verify_status_id);
        intentProperty.putExtra("intent_key_user_username", verify_status_username);
        this.startActivity(intentProperty);

    }

    public void performLogoutUser(View view) {

        //Clear all sharedPreferences here
        SharedPreferences credentialsLogout = getSharedPreferences("bcc_login_details", MODE_PRIVATE);
        SharedPreferences.Editor editor = credentialsLogout.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(ActivityCustomerDashBoard.this, "Logout Successful.", Toast.LENGTH_LONG).show();
        startActivity(new Intent(ActivityCustomerDashBoard.this, ActivitySignIn.class));

    }

    public void showForumActions(View view) {

        startActivity(new Intent(ActivityCustomerDashBoard.this,
                ActivityCustomerComplaint.class));

    }

    private void actionSelectAccountOptions() {

        List<String> options = new ArrayList<String>();
        options.add("Create Profile");
        options.add("Edit Profile");

        final CharSequence[] option = options.toArray(new String[options.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setTitle("Available Options");

        dialogBuilder.setItems(option, (dialog, which) -> {

            String selectedText = option[which].toString();

            if (selectedText.equals("Create Profile")) {
                Intent intentParseCreateProfile = new Intent(ActivityCustomerDashBoard.this,
                        ActivityUserAdd.class);
                intentParseCreateProfile.putExtra("key_username", verify_status_username);
                startActivity(intentParseCreateProfile);

            } else if (selectedText.equals("Edit Profile")) {

                Intent intentParseEditProfile = new Intent(ActivityCustomerDashBoard.this,
                        ActivityEditProfile.class);
                startActivity(intentParseEditProfile);
            }
        });

        AlertDialog alertDialogObject = dialogBuilder.create();
        alertDialogObject.show();
    }

}