package com.example.tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tracker.dbhelper.TrackerDB;
import com.example.tracker.entity.Category;
import com.example.tracker.entity.Transaction;
import com.example.tracker.util.DateUtil;
import com.example.tracker.util.MyEditTextDatePicker;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddExpenseActivity extends AppCompatActivity {

    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        Button submitBtn = findViewById(R.id.btn_exp_submit);
        Button clrBtn = findViewById(R.id.btn_exp_clear);
        TextInputLayout expName = findViewById(R.id.til_exp_name);
        AutoCompleteTextView selCat = findViewById(R.id.edt_exp_cat);
        TextInputLayout expCat = findViewById(R.id.til_exp_cat);
        TextInputLayout expPrice = findViewById(R.id.til_exp_price);
        TextInputLayout expQuantity = findViewById(R.id.til_exp_quantity);
        TextInputLayout expTot = findViewById(R.id.til_exp_tot);
        TextInputLayout expBy = findViewById(R.id.til_exp_by);
        TextInputLayout expDate = findViewById(R.id.til_exp_date);

        // Data Prepopulate for edit activity
        Intent intent = getIntent();
        Object obj = intent.getSerializableExtra("EditTransaction");
        if(obj!=null){
            Transaction trn = (Transaction) obj;
            selCat.setText(trn.trnCat);
            Objects.requireNonNull(expName.getEditText()).setText(trn.trnName);
            Objects.requireNonNull(expPrice.getEditText()).setText(trn.price.toString());
            Objects.requireNonNull(expQuantity.getEditText()).setText(trn.noOfItems.toString());
            Objects.requireNonNull(expTot.getEditText()).setText(trn.total.toString());
            Objects.requireNonNull(expBy.getEditText()).setText(trn.byWhom);
            Objects.requireNonNull(expDate.getEditText()).setText(DateUtil.getStrDateFromLongDate(trn.trnDate));
        }


        List<Category> cats= TrackerDB.getDb(context).categoryDao().getAllCategory();
        String[] catArr = cats.stream().map(i->i.catName).toArray(String[]::new);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddExpenseActivity.this,android.R.layout.simple_list_item_1, catArr);
        selCat.setAdapter(adapter);

        Objects.requireNonNull(expDate.getEditText()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyEditTextDatePicker datePicker = new MyEditTextDatePicker(context, R.id.edt_exp_date);
                datePicker.onClick(view);
            }
        });
        Objects.requireNonNull(expTot.getEditText()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isError = false;
                Float pr = null;
                Integer quan = null;
                String price = Objects.requireNonNull(expPrice.getEditText()).getText().toString().trim();
                String noOfItems = Objects.requireNonNull(expQuantity.getEditText()).getText().toString().trim();
                if(price.equalsIgnoreCase("")){
                    expPrice.setError("Enter Price");
                    isError = true;
                } else {
                    try {
                        pr = Float.parseFloat(price);
                    } catch (NumberFormatException|NullPointerException ex){
                        Objects.requireNonNull(expPrice.getEditText()).setError("Enter A Valid Price");
                        isError = true;
                    } catch (Exception e){
                        Objects.requireNonNull(expPrice.getEditText()).setError("Enter A Valid Price");
                        isError = true;
                    }
                }
                if(noOfItems.equalsIgnoreCase("")){
                    expQuantity.setError("Enter Item Quantity");
                    isError = true;
                } else{
                    try {
                        quan = Integer.parseInt(noOfItems);
                    }catch (NumberFormatException|NullPointerException ex){
                        Objects.requireNonNull(expQuantity.getEditText()).setError("Enter A Valid Quantity");
                        isError = true;
                    }
                    catch (Exception ex){
                        Objects.requireNonNull(expQuantity.getEditText()).setError("Enter A Valid Quantity");
                        isError = true;
                    }
                }
                if(!isError){
                    expPrice.setErrorEnabled(false);
                    expQuantity.setErrorEnabled(false);
                    Objects.requireNonNull(expPrice.getEditText()).setError(null);
                    Objects.requireNonNull(expQuantity.getEditText()).setError(null);
                    Objects.requireNonNull(expTot.getEditText()).setText( ""+pr * quan);
                }
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean isError = false;
                Transaction trn = new Transaction();
                String trnName = Objects.requireNonNull(expName.getEditText()).getText().toString().trim();
                String price = Objects.requireNonNull(expPrice.getEditText()).getText().toString().trim();
                String noOfItems = Objects.requireNonNull(expQuantity.getEditText()).getText().toString().trim();
                String byWhom = Objects.requireNonNull(expBy.getEditText()).getText().toString().trim();
                String trnDate = Objects.requireNonNull(expDate.getEditText()).getText().toString().trim();
                String trnCat = selCat.getText().toString().trim();
                if (trnCat.equalsIgnoreCase("")){
                    expCat.setError("Select A Category");
                    isError = true;
                }
                if(trnName.equalsIgnoreCase("")){
                    expName.setError("Enter Expense Name");
                    isError = true;
                }
                if(price.equalsIgnoreCase("")){
                    expPrice.setError("Enter Price");
                    isError = true;
                } else {
                    try {
                        trn.price = Float.parseFloat(price);
                    } catch (NumberFormatException|NullPointerException ex){
                        Objects.requireNonNull(expPrice.getEditText()).setError("Enter A Valid Price");
                        isError = true;
                    } catch (Exception e){
                        Objects.requireNonNull(expPrice.getEditText()).setError("Enter A Valid Price");
                        isError = true;
                    }
                }
                if(noOfItems.equalsIgnoreCase("")){
                    expQuantity.setError("Enter Item Quantity");
                    isError = true;
                } else{
                    try {
                        trn.noOfItems = Integer.parseInt(noOfItems);
                    }catch (NumberFormatException|NullPointerException ex){
                        Objects.requireNonNull(expQuantity.getEditText()).setError("Enter A Valid Quantity");
                        isError = true;
                    }
                    catch (Exception ex){
                        Objects.requireNonNull(expQuantity.getEditText()).setError("Enter A Valid Quantity");
                        isError = true;
                    }
                }
                if(byWhom.equalsIgnoreCase("")){
                    expBy.setError("Enter By Whom");
                    isError = true;
                }
                if(trnDate.equalsIgnoreCase("")){
                    expDate.setError("Enter Transaction Date");
                    isError = true;
                }

                if(!isError) {
                    trn.trnName = trnName;
                    trn.byWhom = byWhom;
                    try {
                        trn.trnDate = DateUtil.getLongDate(trnDate);
                    } catch (ParseException ignored) {
                    }
                    trn.total = trn.price * trn.noOfItems;
                    trn.trnType = "Debit";
                    trn.trnCat = trnCat;
                    TrackerDB db = TrackerDB.getDb(context);
                    if (obj != null) {
                        try {
                            Transaction t1 = (Transaction) obj;
                            trn.id = t1.id;
                            db.transactionDao().update(trn);
                            new SweetAlertDialog(
                                    context, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Success")
                                    .setContentText("Expense Edited Successfully.")
                                    .show();
                            clrBtn.performClick();

                        } catch (Exception ex) {
                            new SweetAlertDialog(
                                    context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Failed")
                                    .setContentText("Expense Edit Failed.")
                                    .show();
                        }
                    } else {
                        try {
                            db.transactionDao().insertAll(trn);
                            new SweetAlertDialog(
                                    context, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Success")
                                    .setContentText("Expense Created Successfully.")
                                    .show();
                            clrBtn.performClick();
                        } catch (Exception ex) {
                            new SweetAlertDialog(
                                    context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Failed")
                                    .setContentText("Expense Creation Failed.")
                                    .show();
                        }
                    }
                }
            }
        });
        clrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Objects.requireNonNull(expName.getEditText()).getText().clear();
                Objects.requireNonNull(expCat.getEditText()).getText().clear();
                Objects.requireNonNull(expPrice.getEditText()).getText().clear();
                Objects.requireNonNull(expQuantity.getEditText()).getText().clear();
                Objects.requireNonNull(expTot.getEditText()).getText().clear();
                Objects.requireNonNull(expBy.getEditText()).getText().clear();
                Objects.requireNonNull(expDate.getEditText()).getText().clear();
                selCat.getText().clear();
            }
        });
    }
}