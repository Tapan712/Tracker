package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.tracker.adapter.GroupAdapter;
import com.example.tracker.adapter.ReportCatAdapter;
import com.example.tracker.dbhelper.TrackerDB;
import com.example.tracker.dto.CatData;
import com.example.tracker.entity.Transaction;
import com.example.tracker.util.DateUtil;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportScreenActivity extends AppCompatActivity {
    private Context context = this;
    private RecyclerView rvCat;
    private ReportCatAdapter catAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_screen);
        PieChart pieChart = findViewById(R.id.piechart);
        Intent intent = getIntent();
        String tDate = intent.getStringExtra("toDate");
        String fDate = intent.getStringExtra("fromDate");
        Long toDate = 0L;
        Long fromDate = 0L;
        try {
            toDate = DateUtil.getLongDate(tDate);
            fromDate = DateUtil.getLongDate(fDate);
        } catch (ParseException ignore) {
        }
        Map<String,Float> resMap = getDataMap(fromDate,toDate);
        Float debit = resMap.get("Debit");
        Float credit = resMap.get("Credit")!=0F?resMap.get("Credit"):1F;
        Float balance = resMap.get("Balance");
        // Add pie slice
        pieChart.addPieSlice(
                new PieModel((balance/credit)*100,Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel((debit/credit)*100,Color.parseColor("#EF5350")));
        resMap.remove("Debit");
        resMap.remove("Credit");
        resMap.remove("Balance");
        resMap.remove(null);
        List<CatData> catList = new ArrayList<>();
        resMap.keySet().forEach(e->{
            CatData catData = new CatData();
            catData.setCatName(e);
            catData.setAmount(Math.round(resMap.get(e)));
            catList.add(catData);
        });
        rvCat = findViewById(R.id.rv_rptCat);
        rvCat.setLayoutManager(new LinearLayoutManager(context));
        catAdapter = new ReportCatAdapter(catList);
        rvCat.setAdapter(catAdapter);
        pieChart.startAnimation();
    }

    public Map<String,Float> getDataMap(Long fromDate,Long toDate){
        Map<String,Float> resMap = new HashMap<>();
        List<Transaction> trnList=TrackerDB.getDb(context).transactionDao().findBetweenDates(fromDate,toDate);
        Float debit = trnList.stream().filter(i->i.trnType.equalsIgnoreCase("Debit")).map(i->i.total).reduce(Float::sum).orElse(0F);
        Float credit = trnList.stream().filter(i->i.trnType.equalsIgnoreCase("Credit")).map(i->i.total).reduce(Float::sum).orElse(0F);
        Float bal = credit-debit;
        resMap.put("Debit",debit);
        resMap.put("Credit",credit);
        resMap.put("Balance",bal);
        trnList.forEach(t->{
            if(t.trnCat!=null){
            resMap.put(t.trnCat,resMap.containsKey(t.trnCat)?resMap.get(t.trnCat)+t.total:t.total);
            }
        });
        return resMap;
    }
}