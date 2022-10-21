package com.smartsimjgraphics.bccihub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
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

public class ActivityUserManage extends AppCompatActivity {

    //apart from others this class an admin will be able to send emails to users
    Button btnEditUser, btnSendEmail, btnUpdateStatus;

    TextView tv_cust_type, tv_cust_fname, tv_cust_lname, tv_cust_username,
            tv_cust_password, tv_cust_phone, tv_cust_address,
            tv_cust_city, tv_cust_occupation, tv_cust_email, tv_cust_fax;

    String  et_str_cust_id, et_str_cust_type, et_str_cust_fname, et_str_cust_lname, et_str_cust_username,
            et_str_cust_password, et_str_cust_phone,
            et_str_cust_address, et_str_cust_city, et_str_cust_occupation,
            et_str_cust_email, et_str_cust_fax;

    Intent intent = null, chooser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialize componenets
        init();

        getUserIntentDetails();

        retrieveCustEmail();

        btnEditUser.setOnClickListener(v -> editUserAction());

        btnSendEmail.setOnClickListener(v -> sendUserEmailAction());

        btnUpdateStatus.setOnClickListener(v -> updateUserStatusAction());

    }

    private void init() {

        //EditText
        tv_cust_type = findViewById(R.id.id_tv_mg_user_type);
        tv_cust_fname = findViewById(R.id.id_tv_mg_user_fname);
        tv_cust_lname = findViewById(R.id.id_tv_mg_user_lname);
        tv_cust_username = findViewById(R.id.id_tv_mg_user_username);
        tv_cust_password = findViewById(R.id.id_tv_mg_user_password);
        tv_cust_phone = findViewById(R.id.id_tv_mg_user_phone);
        tv_cust_address = findViewById(R.id.id_tv_mg_user_address);
        tv_cust_city = findViewById(R.id.id_tv_mg_user_city);
        tv_cust_occupation = findViewById(R.id.id_tv_mg_user_occupation);
        tv_cust_email = findViewById(R.id.id_tv_mg_user_email);
        tv_cust_fax = findViewById(R.id.id_tv_mg_user_fax);

        btnEditUser = findViewById(R.id.id_btn_mg_cust_edit);
        btnSendEmail = findViewById(R.id.id_btn_mg_cust_email);
        btnUpdateStatus = findViewById(R.id.id_btn_mg_cust_change_status);

    }

    private void getUserIntentDetails() {

        //Getting user intent details
        Intent intent = getIntent();
        et_str_cust_id = intent.getStringExtra("custID");
        et_str_cust_type = intent.getStringExtra("cust_type");
        et_str_cust_fname = intent.getStringExtra("cust_fname");
        et_str_cust_lname = intent.getStringExtra("cust_lname");
        et_str_cust_username = intent.getStringExtra("cust_username");
        et_str_cust_password = intent.getStringExtra("cust_password");
        et_str_cust_phone = intent.getStringExtra("cust_phone");
        et_str_cust_address = intent.getStringExtra("cust_address");
        et_str_cust_city = intent.getStringExtra("cust_city");
        et_str_cust_occupation = intent.getStringExtra("cust_occupation");
        et_str_cust_email = intent.getStringExtra("cust_email");
        et_str_cust_fax = intent.getStringExtra("cust_fax");

        tv_cust_type.setText(et_str_cust_type);
        tv_cust_fname.setText(et_str_cust_fname);
        tv_cust_lname.setText(et_str_cust_lname);
        tv_cust_username.setText(et_str_cust_username);
        tv_cust_password.setText(et_str_cust_password);
        tv_cust_phone.setText(et_str_cust_phone);
        tv_cust_address.setText(et_str_cust_address);
        tv_cust_city.setText(et_str_cust_city);
        tv_cust_occupation.setText(et_str_cust_occupation);
        tv_cust_email.setText(et_str_cust_email);
        tv_cust_fax.setText(et_str_cust_fax);
    }

    public void retrieveCustEmail() {

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_cust_phone_email_sharedPref", MODE_PRIVATE);

        String status_phone = sharedPreferences.getString("cust_status_phone", "");
        String status_email = sharedPreferences.getString("cust_status_email", "");

    }

    private void editUserAction() {

        String tv_str_cust_type = tv_cust_type.getText().toString();
        String tv_str_cust_fname = tv_cust_fname.getText().toString();
        String tv_str_cust_lname = tv_cust_lname.getText().toString();
        String tv_str_cust_username = tv_cust_username.getText().toString();
        String tv_str_cust_password = tv_cust_password.getText().toString();
        String tv_str_cust_phone = tv_cust_phone.getText().toString();
        String tv_str_cust_address = tv_cust_address.getText().toString();
        String tv_str_cust_city = tv_cust_city.getText().toString();
        String tv_str_cust_occupation = tv_cust_occupation.getText().toString();
        String tv_str_cust_email = tv_cust_email.getText().toString();
        String tv_str_cust_fax = tv_cust_fax.getText().toString();

        Intent intent = new Intent(ActivityUserManage.this,
                ActivityEditProfile.class);

        intent.putExtra("cust_type", tv_str_cust_type);
        intent.putExtra("cust_fname", tv_str_cust_fname);
        intent.putExtra("cust_lname", tv_str_cust_lname);
        intent.putExtra("cust_username", tv_str_cust_username);
        intent.putExtra("cust_password", tv_str_cust_password);
        intent.putExtra("cust_phone", tv_str_cust_phone);
        intent.putExtra("cust_address", tv_str_cust_address);
        intent.putExtra("cust_city", tv_str_cust_city);
        intent.putExtra("cust_occupation", tv_str_cust_occupation);
        intent.putExtra("cust_email", tv_str_cust_email);
        intent.putExtra("cust_fax", tv_str_cust_fax);
        startActivity(intent);

    }

    private void sendUserEmailAction() {

        String tv_str_cust_type = tv_cust_type.getText().toString();
        String tv_str_cust_fname = tv_cust_fname.getText().toString();
        String tv_str_cust_lname = tv_cust_lname.getText().toString();
        String tv_str_cust_username = tv_cust_username.getText().toString();
        String tv_str_cust_password = tv_cust_password.getText().toString();
        String tv_str_cust_phone = tv_cust_phone.getText().toString();
        String tv_str_cust_address = tv_cust_address.getText().toString();
        String tv_str_cust_city = tv_cust_city.getText().toString();
        String tv_str_cust_occupation = tv_cust_occupation.getText().toString();
        String tv_str_cust_email = tv_cust_email.getText().toString();
        String tv_str_cust_fax = tv_cust_fax.getText().toString();

        intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("message/rfc822");
        String[] send_to = {tv_str_cust_email, "janafsimeon@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, send_to);

        //giving the subject
        intent.putExtra(Intent.EXTRA_SUBJECT, "BLANTYRE CITY COUNTRY APPLICATION");

        //giving the body of the email
        intent.putExtra(Intent.EXTRA_TEXT, "Please make sure you pay your rent ASAP");

        //getting an attachment
        //intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("android.resource://com.smartsimjgraphics/drawable"+ R.drawable.mbc2bck));
        chooser =Intent.createChooser(intent, "Email using..");
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(chooser);
        }else{
            Toast.makeText(this, "No apps available to open Map", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateUserStatusAction() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating property status. Please Wait...");

        et_str_cust_type = tv_cust_type.getText().toString();
        et_str_cust_fname = tv_cust_fname.getText().toString();
        et_str_cust_lname = tv_cust_lname.getText().toString();
        et_str_cust_username = tv_cust_username.getText().toString();
        et_str_cust_password = tv_cust_password.getText().toString();
        et_str_cust_phone = tv_cust_phone.getText().toString();
        et_str_cust_address = tv_cust_address.getText().toString();
        et_str_cust_city = tv_cust_city.getText().toString();
        et_str_cust_occupation = tv_cust_occupation.getText().toString();
        et_str_cust_email = tv_cust_email.getText().toString();
        et_str_cust_fax = tv_cust_fax.getText().toString();

        progressDialog.show();

        //Add user here...
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.url) + "update_user_details.php",
                response -> {


                    progressDialog.dismiss();

                    if (response.equalsIgnoreCase("Success")) {

                        Toast.makeText(ActivityUserManage.this, "User details updated successfully", Toast.LENGTH_LONG).show();

                        Intent gotoUsers = new Intent(ActivityUserManage.this,
                                ActivityDashBoard.class);

                        startActivity(gotoUsers);
                        finish();

                    } else {

                        Toast.makeText(ActivityUserManage.this, "Oops!! " +
                                response, Toast.LENGTH_SHORT).show();

                    }

                }, error -> {

            progressDialog.dismiss();

            Toast.makeText(ActivityUserManage.this, "Ooops!! Somthing happened. " + error.toString(), Toast.LENGTH_LONG).show();

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