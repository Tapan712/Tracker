package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.biometric.BiometricPrompt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tracker.dbhelper.TrackerDB;
import com.example.tracker.entity.Transaction;
import com.example.tracker.util.DateUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class DashboardActivity extends AppCompatActivity {
    private Context context = this;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        customActionBar();
        String[] valArr = calculateDashBoard();
        TextView txtEarn = findViewById(R.id.txtEarn);
        TextView txtExp = findViewById(R.id.txtExp);
        TextView txtBal = findViewById(R.id.txtBal);
        txtEarn.setText(valArr[0]);
        txtExp.setText(valArr[1]);
        txtBal.setText(valArr[2]);
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
        Intent intent = new Intent(this,TransactionDashboardActivity.class);
        startActivity(intent);
    }

    private String[] calculateDashBoard(){
        TrackerDB db = TrackerDB.getDb(context);
        Long fromDate = 0L;
        Long toDate = 0L;
        try {
            fromDate = DateUtil.getLongDate(DateUtil.getLastMonEndDate(new Date()));
            toDate = DateUtil.getLongDate(DateUtil.getNextDateString(new Date()));
        } catch (ParseException ignored) {
        }

        List<Transaction> creditList=db.transactionDao().findByTypeAndBetweenDates(fromDate,toDate,"Credit");
        List<Transaction> debitList=db.transactionDao().findByTypeAndBetweenDates(fromDate,toDate,"Debit");
        Float credit = creditList.stream().map(i->i.total).reduce(Float::sum).orElse(0F);
        Float debit = debitList.stream().map(i->i.total).reduce(Float::sum).orElse(0F);
        Float bal = credit-debit;
        return new String[]{credit.toString(),debit.toString(),bal.toString()};
    }
}