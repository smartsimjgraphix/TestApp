package com.smartsimjgraphics.bccihub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.smartsimjgraphics.bccihub.users.UserAdapter;
import com.smartsimjgraphics.bccihub.users.Users_Model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityUsers extends AppCompatActivity {

    FloatingActionButton fab_add_user;
    TextView count_holder;
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    UserAdapter adapter;
    ArrayList<Users_Model> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialixe components
        init();

        retrieveCustEmail();

        //retrieve user count and details
        checkConnectivity();

        fab_add_user.setOnClickListener(v -> {

            Intent gotoAddUser = new Intent(ActivityUsers.this,
                    ActivityUserAdd.class);

            //push company name & id via intent
            startActivity(gotoAddUser);

        });

    }

    private void init() {

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        count_holder = findViewById(R.id.tv_users_count);
        recyclerView = findViewById(R.id.users_recyclerview);
        fab_add_user = findViewById(R.id.fab_users_add);

        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityUsers.this));
        adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);
        users = new ArrayList<>();

    }

    public void retrieveCustEmail(){

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_cust_phone_email_sharedPref", MODE_PRIVATE);

        String status_phone = sharedPreferences.getString("cust_status_phone", "");
        String status_email = sharedPreferences.getString("cust_status_email", "");

    }

    private void getUsers() {

        //clear recyclerview to avoid duplication of records (swipe to refresh)
        users.clear();


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading details...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getResources().getString(R.string.url)
                + "retrieve_all_users.php", response -> {

            try {

                for (int i = 0; i < response.length(); i++) {

                    JSONObject jsonObject = response.getJSONObject(i);
                    Users_Model users_model = new Users_Model();
                    users_model.setCustID(jsonObject.getString("custID"));
                    users_model.setCust_type(jsonObject.getString("cust_type"));
                    users_model.setCust_fname(jsonObject.getString("cust_fname"));
                    users_model.setCust_lname(jsonObject.getString("cust_lname"));
                    users_model.setCust_username(jsonObject.getString("cust_username"));
                    users_model.setCust_password(jsonObject.getString("cust_password"));
                    users_model.setCust_phone(jsonObject.getString("cust_phone"));
                    users_model.setCust_address(jsonObject.getString("cust_address"));
                    users_model.setCust_city(jsonObject.getString("cust_city"));
                    users_model.setCust_occupation(jsonObject.getString("cust_occupation"));
                    users_model.setCust_email(jsonObject.getString("cust_email"));
                    users_model.setCust_fax(jsonObject.getString("cust_fax"));
                    users.add(users_model);

                }

            } catch (Exception e) {

                e.printStackTrace();
                Toast.makeText(ActivityUsers.this, "JSON not valid",
                        Toast.LENGTH_SHORT).show();

            }

            adapter.setData(users);
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();

        }, error -> {

            progressDialog.dismiss();
            Toast.makeText(ActivityUsers.this, "Error Occured" + error.getMessage(),
                    Toast.LENGTH_LONG).show();

        });

        requestQueue.add(jsonArrayRequest);

    }

    private void countCustomers(){
        count_holder.setText("Count");
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.url) + "count_customers.php",
                    response -> {

                        if(!response.isEmpty() || response != null){

                            Toast.makeText(ActivityUsers.this,
                                    response, Toast.LENGTH_LONG).show();
                            count_holder.setText(response);

                        }else{

                            Toast.makeText(ActivityUsers.this,
                                    "Cannot count users in the system",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }, error -> {Toast.makeText(ActivityUsers.this,

                    error.toString(), Toast.LENGTH_LONG).show();

            }) {


                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_username", "user");
                    return params;
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
                countCustomers();
                getUsers();

            } else if (mobileConnected) {

                //Action
                countCustomers();
                getUsers();
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