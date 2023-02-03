package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.tracker.adapter.GroupAdapter;
import com.example.tracker.dbhelper.TrackerDB;
import com.example.tracker.dto.SectionItem;
import com.example.tracker.dto.ViewSection;
import com.example.tracker.entity.Transaction;
import com.example.tracker.util.DateUtil;
import com.example.tracker.util.ExpenseMapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ViewScreenActivity extends AppCompatActivity {
    private RecyclerView rvGroup;
    private GroupAdapter groupAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_screen);
        rvGroup = findViewById(R.id.rvGroup);
        rvGroup.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        String type = intent.getStringExtra("TYPE");
        String fromDate = intent.getStringExtra("fromDate");
        String toDate = intent.getStringExtra("toDate");
        groupAdapter = new GroupAdapter(getSectionList(type,fromDate,toDate));
        rvGroup.setAdapter(groupAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //groupAdapter.swapList(getSectionList());
    }

    public List<ViewSection> getSectionList(String type,String fromDate,String toDate){
        List<ViewSection> sectionLst = new ArrayList<>();
        TrackerDB db = TrackerDB.getDb(this);
        List<Transaction> trnLst = new ArrayList<>();
        long fDate = 0L;
        long tDate = 0L;
        try {
            fDate = DateUtil.getLongDate(fromDate);
            tDate = DateUtil.getLongDate(toDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if(type.equalsIgnoreCase("Debit")||type.equalsIgnoreCase("Credit")){
            trnLst= db.transactionDao().findByTypeAndBetweenDates(fDate,tDate,type);
        }else {
            trnLst= db.transactionDao().findBetweenDates(fDate,tDate);
        }
        Map<String,List<SectionItem>> sortMap = new LinkedHashMap<>();
        trnLst.forEach(i->{
            SectionItem item = ExpenseMapper.transactionToSectionItem(i);
            if(sortMap.containsKey(DateUtil.getStrDateFromLongDate(i.trnDate))){
                sortMap.get(DateUtil.getStrDateFromLongDate(i.trnDate)).add(item);
            } else {
                List<SectionItem> items = new ArrayList<>();
                items.add(item);
                sortMap.put(DateUtil.getStrDateFromLongDate(i.trnDate),items);
            }
        });
        Log.i("DB Map Sorted",sortMap.toString());
        sortMap.keySet().forEach(e->{
            ViewSection section = new ViewSection();
            section.setTrnDate(e);
            section.setItemList(sortMap.get(e));
            sectionLst.add(section);
        });
        return sectionLst;
    }
}