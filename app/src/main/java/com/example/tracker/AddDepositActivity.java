package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tracker.dbhelper.TrackerDB;
import com.example.tracker.dto.Deposit;
import com.example.tracker.entity.Transaction;
import com.example.tracker.util.DateUtil;
import com.example.tracker.util.ExpenseMapper;
import com.example.tracker.util.MyEditTextDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddDepositActivity extends AppCompatActivity {

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deposit);
        TextInputLayout depName = findViewById(R.id.til_name);
        TextInputLayout depAmount = findViewById(R.id.til_amount);
        TextInputLayout depFrom = findViewById(R.id.til_from);
        TextInputLayout depDate = findViewById(R.id.til_date);
//        TextInputEditText date = findViewById(R.id.txt_date);
        Button btnSubmit = findViewById(R.id.btn_dep_submit);
        Button btnClr = findViewById(R.id.btn_dep_clear);
        Intent intent = getIntent();
        Object obj = intent.getSerializableExtra("EditTransaction");
        if(obj!=null) {
            Transaction trn = (Transaction) obj;
            Objects.requireNonNull(depName.getEditText()).setText(trn.trnName);
            Objects.requireNonNull(depAmount.getEditText()).setText(trn.price.toString());
            Objects.requireNonNull(depFrom.getEditText()).setText(trn.byWhom);
            Objects.requireNonNull(depDate.getEditText()).setText(DateUtil.getStrDateFromLongDate(trn.trnDate));
        }
        Objects.requireNonNull(depDate.getEditText()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyEditTextDatePicker datePicker = new MyEditTextDatePicker(context, R.id.txt_date);
                datePicker.onClick(view);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isError = false;
                Deposit deposit = new Deposit();
                String dName = Objects.requireNonNull(depName.getEditText()).getText().toString().trim();
                String amount = Objects.requireNonNull(depAmount.getEditText()).getText().toString().trim();
                String from = Objects.requireNonNull(depFrom.getEditText()).getText().toString().trim();
                String dDate = Objects.requireNonNull(depDate.getEditText()).getText().toString().trim();
                if (dName.equalsIgnoreCase("")){
                    depName.setError("Enter Deposit Name");
                    isError = true;
                }
                if(amount.equalsIgnoreCase("")){
                    depAmount.setError("Enter Deposit Amount");
                    isError = true;
                } else {
                    try {
                        deposit.setAmount(Float.parseFloat(amount));
                    } catch (NumberFormatException|NullPointerException ex){
                        Objects.requireNonNull(depAmount.getEditText()).setError("Enter A Valid Amount");
                        isError = true;
                    } catch (Exception e){
                        Objects.requireNonNull(depAmount.getEditText()).setError("Enter A Valid Amount");
                        isError = true;
                    }
                }
                if (from.equalsIgnoreCase("")){
                    depFrom.setError("Enter Deposit From");
                    isError = true;
                }
                if(dDate.equalsIgnoreCase("")){
                    depDate.setError("Enter Deposit Date");
                    isError = true;
                }
                if(!isError){
                    deposit.setName(dName);
                    deposit.setFrom(from);
                    deposit.setDate(dDate);
                    Transaction trn = ExpenseMapper.depositToTransaction(deposit);
                    TrackerDB db = TrackerDB.getDb(context);
                    if(obj!=null) {
                        try {
                            Transaction t1 = (Transaction) obj;
                            trn.id = t1.id;
                            db.transactionDao().update(trn);
                            new SweetAlertDialog(
                                    context, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Success")
                                    .setContentText("Deposit Edited Successfully.")
                                    .show();

                        } catch (Exception ex) {
                            new SweetAlertDialog(
                                    context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Failed")
                                    .setContentText("Deposit Edit Failed.")
                                    .show();
                        }
                    }else {
                        try {
                            db.transactionDao().insertAll(trn);
                            new SweetAlertDialog(
                                    context, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Success")
                                    .setContentText("Deposit Created Successfully.")
                                    .show();
                            btnClr.performClick();

                        } catch (Exception ex) {
                            new SweetAlertDialog(
                                    context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Failed")
                                    .setContentText("Deposit Creation Failed.")
                                    .show();
                        }
                    }

                }
            }
        });
        btnClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(depName.getEditText()).getText().clear();
                Objects.requireNonNull(depAmount.getEditText()).getText().clear();
                Objects.requireNonNull(depFrom.getEditText()).getText().clear();
                Objects.requireNonNull(depDate.getEditText()).getText().clear();
            }
        });
    }
}