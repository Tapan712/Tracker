package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tracker.util.DateUtil;
import com.example.tracker.util.MyEditTextDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

public class ReportFormActivity extends AppCompatActivity {

    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_form);
        TextInputLayout fdLayout = findViewById(R.id.lbl_fDate);
        TextInputLayout tdLayout = findViewById(R.id.lbl_To_Date);
        TextInputEditText txtFromDate = findViewById(R.id.txtDate);
        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyEditTextDatePicker datePicker = new MyEditTextDatePicker(context, R.id.txtDate);
                datePicker.onClick(view);
            }
        });
        TextInputEditText txtToDate = findViewById(R.id.txtToDate);
        txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyEditTextDatePicker datePicker = new MyEditTextDatePicker(context, R.id.txtToDate);
                datePicker.onClick(view);
            }
        });
        Button clrBtn = findViewById(R.id.btn_clear);
        clrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(txtFromDate.getText()).clear();
                Objects.requireNonNull(txtToDate.getText()).clear();
            }
        });
        Button searchBtn = findViewById(R.id.btnSearch);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isError = false;
                String fromDate = Objects.requireNonNull(txtFromDate.getText()).toString();
                String toDate = Objects.requireNonNull(txtToDate.getText()).toString();
                Date fd = null;
                Date td = null;
                try {
                    fd = DateUtil.getDate(fromDate);
                } catch (ParseException e) {
                    fdLayout.setError("Enter A Valid Date");
                    isError = true;
                }
                try {
                    td = DateUtil.getDate(toDate);
                } catch (ParseException e) {
                    tdLayout.setError("Enter A Valid Date");
                    isError = true;
                }
                if(td!=null&&fd!=null&&td.before(fd)){
                    tdLayout.setError("Enter Date After FromDate");
                    isError = true;
                }
                if (!isError){
                    loadReportScreen(fromDate,toDate);
                }
            }
        });
        Button curMonBtn = findViewById(R.id.btn_current_month);
        curMonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromDate = DateUtil.getLastMonEndDate(new Date());
                String toDate = DateUtil.getNextDateString(new Date());
                Toast.makeText(getApplicationContext(),
                                fromDate+"to" + toDate, Toast.LENGTH_SHORT)
                        .show();
                loadReportScreen(fromDate,toDate);
            }
        });
        Button prvMonBtn = findViewById(R.id.btn_last_month);
        prvMonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromDate = DateUtil.getLastMonStartDate(new Date());
                String toDate = DateUtil.getLastMonEndDate(new Date());
                Toast.makeText(getApplicationContext(),
                                fromDate+"to" + toDate, Toast.LENGTH_SHORT)
                        .show();
                loadReportScreen(fromDate,toDate);
            }
        });
        Button curYearBtn = findViewById(R.id.btn_current_year);
        curYearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromDate = DateUtil.getCurYearStartDate(new Date());
                String toDate = DateUtil.getNextDateString(new Date());
                Toast.makeText(getApplicationContext(),
                                fromDate+"to" + toDate, Toast.LENGTH_LONG)
                        .show();
                loadReportScreen(fromDate,toDate);
            }
        });
    }
    private void loadReportScreen(String fromDate,String toDate){
        Intent intent = new Intent(this,ReportScreenActivity.class);
        intent.putExtra("fromDate",fromDate);
        intent.putExtra("toDate",toDate);
        startActivity(intent);
    }
}