package com.smartsimjgraphics.bccihub;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityCustomerPay extends AppCompatActivity {

    Button btnMakePayment;
    Button btnSendReceipt;

    AutoCompleteTextView editTextReceiptSource;
    String[] string_available_sources = {"National Bank", "Standard Bank", "Mpamba", "Airtel Money"};

    RequestQueue rQueue;
    String cust_username, cust_email, cust_phone_number, cust_occupation, str_date_time_upload;
    SharedPreferences sharedPref_getCustomer;

    //paypal
    private int PAYPAL_REQ_CODE = 14;
    private static PayPalConfiguration paymentConfig = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalClientIDConfigClass.PAYPAL_CLIENT_ID);
    String str_value_data_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_pay);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialize componenets
        init();

        retrieveCustEmail();

        //auto date pick
        pickDateTime();

        btnMakePayment.setOnClickListener(v -> makePayment());

    }

    private void init() {

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paymentConfig);
        startService(intent);

        btnMakePayment = findViewById(R.id.id_btn_pay_paypal);
        editTextReceiptSource = findViewById(R.id.id_et_receipt_source);

        ArrayAdapter autoAdapterSources = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, string_available_sources);
        editTextReceiptSource.setAdapter(autoAdapterSources);
        editTextReceiptSource.setThreshold(2);

        //getting user detils of one making payment
        getUserDetailsSharedPref();

    }

    private void getUserDetailsSharedPref() {

        //Get SharedPref From Registraion page
        sharedPref_getCustomer = getSharedPreferences("sharedPref_reg_details", MODE_PRIVATE);
        String cust_fname = sharedPref_getCustomer.getString("reg_user_sharedPref_fname", "");
        String cust_lname = sharedPref_getCustomer.getString("reg_user_haredPref_lname", "");
        cust_username = sharedPref_getCustomer.getString("reg_user_haredPref_username", "");
        String cust_pass = sharedPref_getCustomer.getString("reg_user_sharedPref_password", "");
        cust_phone_number = sharedPref_getCustomer.getString("reg_user_sharedPref_phone_number", "");
        String cust_address = sharedPref_getCustomer.getString("reg_user_sharedPref_address", "");
        String cust_region = sharedPref_getCustomer.getString("reg_user_sharedPref_RegionSelected", "");
        String cust_user_area_or_state = sharedPref_getCustomer.getString("reg_user_sharedPref_AreaState", "");
        String cust_postal_code = sharedPref_getCustomer.getString("reg_user_sharedPref_postal_code", "");
        String cust_country = sharedPref_getCustomer.getString("reg_user_sharedPref_CountrySelected", "");
        String cust_credit_limit = sharedPref_getCustomer.getString("reg_user_sharedPref_credit_limit", "");
        cust_occupation = sharedPref_getCustomer.getString("reg_user_sharedPref_occupation", "");
        cust_email = sharedPref_getCustomer.getString("reg_user_sharedPref_email", "");
        String cust_fax = sharedPref_getCustomer.getString("reg_user_sharedPref_fax", "");

    }

    public void retrieveCustEmail(){

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_session_user_email", MODE_PRIVATE);

        str_value_data_username =
                sharedPreferences.getString("key_session_username", "");

    }

    public void pickDateTime() {
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        Date todayTime = new Date();
        String thisTime = currentTime.format(todayTime);

        //editText_dateTime.setText(thisDate + " " + thisTime);
        str_date_time_upload = thisDate + " " + thisTime;

    }

    //function upload payment
    private void makePayment() {

        ProgressDialog progressDialog = new ProgressDialog(ActivityCustomerPay.this);
        progressDialog.setMessage("Posting. Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //add volley upload to server
        String str_receipt_source = editTextReceiptSource.getText().toString();

        if (str_receipt_source.isEmpty()) {

            editTextReceiptSource.setError("This Field is required.");
            editTextReceiptSource.requestFocus();
            progressDialog.dismiss();

        } else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.url) + "insert_payment_receipt.php",
                    response -> {

                        if(response.equalsIgnoreCase("Success")){

                            progressDialog.dismiss();
                            Toast.makeText(ActivityCustomerPay.this,
                                    "Your proof of payment has been sent and received successfully!",
                                    Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(ActivityCustomerPay.this,
                                    ActivityCustomerDashBoard.class);
                            startActivity(intent);

                            finish();

                        }else{

                            progressDialog.dismiss();
                            Toast.makeText(ActivityCustomerPay.this, "Oops!! "+response, Toast.LENGTH_SHORT).show();

                        }


                    }, error -> {

                progressDialog.dismiss();
                Toast.makeText(ActivityCustomerPay.this,
                        "Something Happened at our End"+ error.toString(),Toast.LENGTH_LONG).show();

            }){

                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("pay_source", str_receipt_source);
                    params.put("receipt_img", "Sample Image");
                    params.put("pay_date_time", str_date_time_upload);
                    params.put("cust_username", "Patience");
                    params.put("cust_email", "lucyndipo@gmail.com");
                    params.put("cust_phone", "0884610424");

                    return params;
                    
                }
            };

            rQueue = Volley.newRequestQueue(ActivityCustomerPay.this);
            rQueue.add(stringRequest);

        }


    }

    private void paypalPayAction(){

        PayPalPayment payment = new PayPalPayment(new BigDecimal(100), "usd",
                "Test Payment", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paymentConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, PAYPAL_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == PAYPAL_REQ_CODE){

            if(resultCode == Activity.RESULT_OK){

                Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();

            }else{

                Toast.makeText(this, "Payment unsuccessful", Toast.LENGTH_SHORT).show();

            }

        }

    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

}