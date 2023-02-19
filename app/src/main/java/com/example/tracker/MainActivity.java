package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tracker.util.LocaleUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton mButton = findViewById(R.id.btnLogin);
        TextView tv1 = findViewById(R.id.textView);
        TextView tv2 = findViewById(R.id.textView2);
        TextView tvOd = findViewById(R.id.txtOd);
        Switch langSw= findViewById(R.id.langSwt);
        String lg = LocaleUtil.startupLocale(context);
        if(lg.equalsIgnoreCase("or")){
            langSw.setChecked(true);
            tv1.setText(context.getResources().getText(R.string.str_welcome));
            tv2.setText(context.getResources().getText(R.string.str_login));
            tvOd.setText(context.getResources().getText(R.string.lbl_land_od));
        } else {
            langSw.setChecked(false);
        }
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                authUser();
            }
        });
        authUser();
//        LocaleUtil.setCurAppLocale(context,"or",false);
        langSw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(langSw.isChecked()){
                    LocaleUtil.setCurAppLocale(context,"or",true);
                } else {
                    LocaleUtil.setCurAppLocale(context,"en-us",true);
                }
                tv1.setText(context.getResources().getText(R.string.str_welcome));
                tv2.setText(context.getResources().getText(R.string.str_login));
                tvOd.setText(context.getResources().getText(R.string.lbl_land_od));
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        authUser();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        authUser();
    }

    private void loadDashboard(){
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }

    public void authUser(){
        ActionBar actionBar = getSupportActionBar();
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                                "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                loadDashboard();
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

                Toast.makeText(getApplicationContext(), "Authentication failed",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login To Access Dashboard")
                .setSubtitle("Log in using your biometric credential")
                .setDeviceCredentialAllowed(true)
                //.setNegativeButtonText("Use account password")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

}