package com.smartsimjgraphics.bccihub;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ActivitySendMail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        Objects.requireNonNull(getSupportActionBar()).hide();

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySendMail.this)
                .setTitle("BCC IHUB")
                .setMessage("Options Send Mail")
                .setIcon(R.mipmap.ic_launcher_bcc_round)
                .setCancelable(false)
                .setPositiveButton("WARNING", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sendMail("BCC IHUB WARNING", "Your rent property is due. Visit our offices to pay");
                    }
                })
                .setNegativeButton("REMINDER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sendMail("BCC IHUB REMINDER",
                                "YOUR RENT PROP will expire in 7 Days");

                    }
                });
        builder.create();
        builder.show();

    }

    private void sendMail(String mail_subject, String mail_content) {

        Intent intent = null, chooser = null;
        intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("message/rfc822");
        String[] send_to = {"ndipolucy3@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, send_to);

        //giving the subject
        intent.putExtra(Intent.EXTRA_SUBJECT, mail_subject);

        //giving the body of the email
        intent.putExtra(Intent.EXTRA_TEXT, mail_content);

        chooser =Intent.createChooser(intent, "Open Email using..");
        if(intent.resolveActivity(getPackageManager()) != null){

            startActivity(chooser);

        }else{

            Toast.makeText(this, "No apps available to open Map",
                    Toast.LENGTH_SHORT).show();
        }
    }
}