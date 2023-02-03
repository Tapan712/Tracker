package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TransactionDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_dashboard);
        CardView addTrn = findViewById(R.id.addTrnCard);
        CardView addCat = findViewById(R.id.add_cat);
        CardView addDep = findViewById(R.id.add_deposit);
        CardView editExp = findViewById(R.id.edit_exp);
        CardView editDep = findViewById(R.id.edit_dep);
        CardView delExp = findViewById(R.id.del_exp);
        CardView delDep = findViewById(R.id.del_dep);
        addTrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAddExp();
            }
        });
        addCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAddCat();
            }
        });
        addDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAddDep();
            }
        });
        editExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadEditExp();
            }
        });
        editDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadEditDep();
            }
        });
        delExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDeleteExp();
            }
        });
        delDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDeleteDep();
            }
        });


    }
    private void loadAddExp(){
        Intent intent = new Intent(this,AddExpenseActivity.class);
        startActivity(intent);
    }
    private void loadAddCat(){
        Intent intent = new Intent(this,AddCategoryActivity.class);
        startActivity(intent);
    }
    private void loadAddDep(){
        Intent intent = new Intent(this,AddDepositActivity.class);
        startActivity(intent);
    }
    private void loadEditDep(){
        Intent intent = new Intent(this,EditExpenseFormActivity.class);
        intent.putExtra("Type","Credit");
        intent.putExtra("Action","Edit");
        startActivity(intent);
    }
    private void loadDeleteDep(){
        Intent intent = new Intent(this,EditExpenseFormActivity.class);
        intent.putExtra("Type","Credit");
        intent.putExtra("Action","Delete");
        startActivity(intent);
    }
    private void loadEditExp(){
        Intent intent = new Intent(this,EditExpenseFormActivity.class);
        intent.putExtra("Type","Debit");
        intent.putExtra("Action","Edit");
        startActivity(intent);
    }
    private void loadDeleteExp(){
        Intent intent = new Intent(this,EditExpenseFormActivity.class);
        intent.putExtra("Type","Debit");
        intent.putExtra("Action","Delete");
        startActivity(intent);
    }
}