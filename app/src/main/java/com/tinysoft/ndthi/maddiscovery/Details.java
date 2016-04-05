package com.tinysoft.ndthi.maddiscovery;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Details extends AppCompatActivity implements View.OnClickListener {
    private EditText eventName;
    private EditText location;
    private EditText eventDate;
    private EditText startTime;
    private EditText orgName;
    private Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        eventName = (EditText) findViewById(R.id.txtEventName);
        location = (EditText) findViewById(R.id.txtLocation);
        eventDate = (EditText) findViewById(R.id.txtDate);
        startTime = (EditText) findViewById(R.id.txttime);
        orgName = (EditText) findViewById(R.id.txtOrgName);
        btnShare = (Button) findViewById(R.id.btnShare);
        btnShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Events ev = getIntent().getExtras().getParcelable("eventObj");
        System.out.println("clicked");

        SharingToSocialMedia("com.facebook.katana");
    }

    public void SharingToSocialMedia(String application) {
        String myText = "Hey!\nThis is a neat pic!";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Sample Photo");
        sendIntent.setPackage("com.facebook.katana");


        boolean installed = appInstalledOrNot(application);
        if (installed) {
            System.out.println("Installed");
            startActivity(sendIntent);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Installed application first", Toast.LENGTH_LONG).show();
        }

    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
