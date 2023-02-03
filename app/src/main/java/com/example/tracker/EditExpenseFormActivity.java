package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tracker.adapter.TableAdapter;
import com.example.tracker.dbhelper.TrackerDB;
import com.example.tracker.dto.SectionItem;
import com.example.tracker.entity.Transaction;
import com.example.tracker.util.DateUtil;
import com.example.tracker.util.ExpenseMapper;
import com.example.tracker.util.MyEditTextDatePicker;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditExpenseFormActivity extends AppCompatActivity {
    private final Context context = this;
    private RecyclerView tableGroup;
    private TableAdapter tableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense_form);
        Intent intent = getIntent();
        String type = intent.getStringExtra("Type");
        String action = intent.getStringExtra("Action");
        TextInputLayout tilSearch = findViewById(R.id.search_field);
        Button btnSearch = findViewById(R.id.btn_search);

        Objects.requireNonNull(tilSearch.getEditText()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyEditTextDatePicker datePicker = new MyEditTextDatePicker(context, R.id.edt_search);
                datePicker.onClick(view);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = Objects.requireNonNull(tilSearch.getEditText()).getText().toString().trim();
                Long lDate = 0L;
                try{
                    lDate = DateUtil.getLongDate(date);
                } catch (Exception ignore){
                }
                List<SectionItem> itemList = getTableList(type, lDate);
                tableGroup = findViewById(R.id.table_recycler);
                tableGroup.setLayoutManager(new LinearLayoutManager(context));
                tableAdapter = new TableAdapter(action,type,context,itemList);
                tableGroup.setAdapter(tableAdapter);
            }
        });
    }
    public List<SectionItem> getTableList(String trnType,Long trnDate){
        List<SectionItem> items = new ArrayList<>();
        List<Transaction> debitList= TrackerDB.getDb(context).transactionDao().findByTypeAndDate(trnType,trnDate);
        debitList.forEach(i->{
            SectionItem item = ExpenseMapper.transactionToSectionItem(i);
            items.add(item);
        });
        return items;
    }
}