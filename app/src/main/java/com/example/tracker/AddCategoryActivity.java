package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tracker.dbhelper.TrackerDB;
import com.example.tracker.entity.Category;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddCategoryActivity extends AppCompatActivity {
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        Button btnClear = findViewById(R.id.btn_cat_clr);
        Button btnSubmit = findViewById(R.id.btn_cat_submit);
        TextInputLayout catName = findViewById(R.id.cat_name);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isError = false;
                String name = Objects.requireNonNull(catName.getEditText()).getText().toString().trim();
                if(name.equalsIgnoreCase("")){
                    catName.setError("Enter By Whom");
                    isError = true;
                }
                if(!isError){
                    Category cat = new Category();
                    cat.catName = name;
                    try {
                        TrackerDB.getDb(context).categoryDao().insertAll(cat);
                        new SweetAlertDialog(
                                context, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Success")
                                .setContentText("Category Created Successfully.")
                                .show();
                    } catch (Exception ex){
                        new SweetAlertDialog(
                                context, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error Occurred")
                                .setContentText("Category Creation Failed.")
                                .show();
                    }
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(catName.getEditText()).getText().clear();
            }
        });
    }
}