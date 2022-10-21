package com.smartsimjgraphics.bccihub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActivityDashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Objects.requireNonNull(getSupportActionBar()).hide();

        retrieveCustEmail();



    }

    public void retrieveCustEmail(){

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_cust_phone_email_sharedPref", MODE_PRIVATE);

        String status_phone = sharedPreferences.getString("cust_status_phone", "");
        String status_email = sharedPreferences.getString("cust_status_email", "");

    }

    public void addUser(View view) {

        alertSelectUserOption();

    }

    public void showProperties(View view) {
       alertSelectPropertyOption();
    }

    public void performLogout(View view) {

        //Clear all sharedPreferences here
        SharedPreferences credentialsLogout = getSharedPreferences("bcc_login_details", MODE_PRIVATE);
        SharedPreferences.Editor editor = credentialsLogout.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(ActivityDashBoard.this, "Logout Successful.", Toast.LENGTH_LONG).show();
        startActivity(new Intent(ActivityDashBoard.this, ActivitySignIn.class));

    }

    public void showStatistics(View view) {
        startActivity(new Intent(ActivityDashBoard.this, ActivityStatistics.class));
    }

    public void alertSelectUserOption(){
        List<String> options = new ArrayList<String>();
        options.add("New Customer");
        options.add("View Customers");

        final CharSequence[] option = options.toArray(new String[options.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        //dialogBuilder.setTitle("Select Option");
        dialogBuilder.setItems(option, (dialog, which) -> {

            String selectedText = option[which].toString();

            if(selectedText.equals("New Customer")){
                Intent addClient = new Intent(ActivityDashBoard.this, ActivityUserAdd.class);
                startActivity(addClient);

            }else if(selectedText.equals("View Customers")){
                Intent findClient = new Intent(ActivityDashBoard.this, ActivityUsers.class);
                startActivity(findClient);
            }
        });
        AlertDialog alertDialogObject = dialogBuilder.create();
        alertDialogObject.show();
    }

    public void alertSelectPropertyOption(){

        List<String> options = new ArrayList<String>();
        options.add("New Property");
        options.add("View Properties");

        final CharSequence[] option = options.toArray(new String[options.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        //dialogBuilder.setTitle("Select Option");
        dialogBuilder.setItems(option, (dialog, which) -> {

            String selectedText = option[which].toString();

            if(selectedText.equals("New Property")){
                Intent addProperty = new Intent(ActivityDashBoard.this,
                        ActivityPropertyAdd.class);
                startActivity(addProperty);

            }else if(selectedText.equals("View Properties")){
                Intent viewProperty = new Intent(ActivityDashBoard.this,
                        ActivityProperties.class);
                startActivity(viewProperty);
            }
        });
        AlertDialog alertDialogObject = dialogBuilder.create();
        alertDialogObject.show();
    }

    public void showPayments(View view) {

        startActivity(new Intent(ActivityDashBoard.this, ActivityPayments.class));

    }
}