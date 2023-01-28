package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tracker.util.MyEditTextDatePicker;

public class ViewFormActivity extends AppCompatActivity {
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);
        EditText txtFromDate = findViewById(R.id.txtDate);
        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyEditTextDatePicker datePicker = new MyEditTextDatePicker(context, R.id.txtDate);
                datePicker.onClick(view);
            }
        });
        EditText txtToDate = findViewById(R.id.txtToDate);
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
                txtFromDate.getText().clear();
                txtToDate.getText().clear();
            }
        });
    }
}