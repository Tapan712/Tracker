package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tracker.util.DateUtil;
import com.example.tracker.util.MyEditTextDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ViewFormActivity extends AppCompatActivity {
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);
        RadioGroup type = findViewById(R.id.type_group);
        RadioButton allRd  = findViewById(R.id.radio_all);
        RadioButton expRd  = findViewById(R.id.radio_exp);
        RadioButton depRd  = findViewById(R.id.radio_deposit);
        final int all = allRd.getId();
        final int exp = expRd.getId();
        final int dep = depRd.getId();

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
                int selectedId = type.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                String trnType = null;
                if (radioButton.getId()==exp){
                    trnType = "EXPENSE";
                } else if (radioButton.getId()==dep) {
                    trnType = "DEPOSIT";
                } else {
                    trnType = "ALL";
                }
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
                    loadViewScreen(trnType,fromDate,toDate);
                }
            }
        });
        Button curMonBtn = findViewById(R.id.btn_current_month);
        curMonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = type.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                String trnType = null;
                if (radioButton.getId()==exp){
                    trnType = "EXPENSE";
                } else if (radioButton.getId()==dep) {
                    trnType = "DEPOSIT";
                } else {
                    trnType = "ALL";
                }
                String fromDate = DateUtil.getLastMonEndDate(new Date());
                String toDate = DateUtil.getNextDateString(new Date());
                Toast.makeText(getApplicationContext(),
                                fromDate+"to" + toDate, Toast.LENGTH_SHORT)
                        .show();
                loadViewScreen(trnType,fromDate,toDate);
            }
        });
        Button prvMonBtn = findViewById(R.id.btn_last_month);
        prvMonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = type.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                String trnType = null;
                if (radioButton.getId()==exp){
                    trnType = "EXPENSE";
                } else if (radioButton.getId()==dep) {
                    trnType = "DEPOSIT";
                } else {
                    trnType = "ALL";
                }
                String fromDate = DateUtil.getLastMonStartDate(new Date());
                String toDate = DateUtil.getLastMonEndDate(new Date());
                Toast.makeText(getApplicationContext(),
                                fromDate+"to" + toDate, Toast.LENGTH_SHORT)
                        .show();
                loadViewScreen(trnType,fromDate,toDate);
            }
        });
        Button curYearBtn = findViewById(R.id.btn_current_year);
        curYearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = type.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                String trnType = null;
                if (radioButton.getId()==exp){
                    trnType = "EXPENSE";
                } else if (radioButton.getId()==dep) {
                    trnType = "DEPOSIT";
                } else {
                    trnType = "ALL";
                }
                String fromDate = DateUtil.getCurYearStartDate(new Date());
                String toDate = DateUtil.getNextDateString(new Date());
                Toast.makeText(getApplicationContext(),
                                fromDate+"to" + toDate, Toast.LENGTH_LONG)
                        .show();
                loadViewScreen(trnType,fromDate,toDate);
            }
        });
    }

    private void loadViewScreen(String trnType,String fromDate,String toDate){
        Intent intent = new Intent(this,ViewScreenActivity.class);
        String type = null;
        if (trnType.equalsIgnoreCase("ALL")){
            type = "All";
        } else if (trnType.equalsIgnoreCase("EXPENSE")) {
            type = "Debit";
        } else if (trnType.equalsIgnoreCase("DEPOSIT")) {
            type = "Credit";
        }
        intent.putExtra("TYPE",type);
        intent.putExtra("fromDate",fromDate);
        intent.putExtra("toDate",toDate);
        startActivity(intent);
    }
}