package com.smartsimjgraphics.bccihub;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import java.util.Objects;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //get customer phone and email
        retrieveCustEmail();

        //Check for permissions using Dexter
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        //check if all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            //start thread

                            Thread thread = new Thread() {

                                @Override
                                public void run() {

                                    try {

                                        //2 second timer
                                        sleep(2000);

                                    } catch (InterruptedException e) {

                                        e.printStackTrace();

                                    } finally {

                                        SharedPreferences sharedPreferences =
                                                getSharedPreferences("bcc_login_details", MODE_PRIVATE);

                                        String verify_status_username = sharedPreferences.getString(
                                                "log_in_status_username", "");
                                        String verify_status_password = sharedPreferences.getString(
                                                "log_in_status_password", "");
                                        String verify_status_role = sharedPreferences.getString(
                                                "log_in_status_role", "");

                                        if (!verify_status_username.isEmpty() && verify_status_role.contains("Admin")) {

                                            Intent intentLoginStaffMember = new Intent(Splash.this,
                                                    ActivityDashBoard.class);
                                            Splash.this.startActivity(intentLoginStaffMember);
                                            finish();

                                        } else if (!verify_status_username.isEmpty() && verify_status_role.contains("Customer")) {

                                            Intent intentLoginCustomer = new Intent(Splash.this,
                                                    ActivityCustomerDashBoard.class);
                                            Splash.this.startActivity(intentLoginCustomer);
                                            finish();

                                        } else {

                                            startActivity(new Intent(Splash.this, ActivitySignIn.class));
                                            finish();

                                        }

                                    }
                                }
                            };

                            thread.start();
                        }

                        //check for permanent denial of any permission
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {

                            //permission is permanently denied, navigate user to phone settings
                            Toast.makeText(Splash.this, "Please enable all required permissions for the app!", Toast.LENGTH_LONG).show();
                            Intent gotoSettings = new Intent();
                            gotoSettings.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                            gotoSettings.setData(uri);
                            startActivity(gotoSettings);

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }


                })
                .onSameThread()
                .check();

    }

    public void retrieveCustEmail(){

        SharedPreferences sharedPreferences =
                getSharedPreferences("bcc_cust_phone_email_sharedPref", MODE_PRIVATE);

        String status_phone = sharedPreferences.getString("cust_status_phone", "");
        String status_email = sharedPreferences.getString("cust_status_email", "");

    }


}