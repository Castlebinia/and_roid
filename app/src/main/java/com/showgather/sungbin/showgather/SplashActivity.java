package com.showgather.sungbin.showgather;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.nio.channels.AlreadyBoundException;
import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        linearLayout = (LinearLayout)findViewById(R.id.splashactivity_linearlayout);
        try {
            displayMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    void displayMessage() throws InterruptedException {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }

}
