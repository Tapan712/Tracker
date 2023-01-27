package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.biometric.BiometricPrompt;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.Executor;

public class DashboardActivity extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        customActionBar();
        Button btnView = findViewById(R.id.btn_view);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loadViewForm();
            }
        });
        Button btnTrn = findViewById(R.id.btnTrn);
        btnTrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loadTrnForm();
            }
        });
    }

    public void customActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("Dashboard");
            actionBar.setIcon(R.drawable.ic_action_bar_back);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void loadViewForm(){
        Intent intent = new Intent(this,ViewFormActivity.class);
        startActivity(intent);
    }
    private void loadTrnForm(){
        //Intent intent = new Intent(this,ViewFormActivity.class);
        //startActivity(intent);
    }
}