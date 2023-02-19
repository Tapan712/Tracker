package com.example.tracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracker.adapter.ChannelViewAdapter;
import com.example.tracker.adapter.ChatBoxAdapter;
import com.example.tracker.dbhelper.TrackerDB;
import com.example.tracker.dto.ChartItem;
import com.example.tracker.dto.PersonWithCredits;
import com.example.tracker.entity.Credits;
import com.example.tracker.entity.Person;
import com.example.tracker.util.AdapterUtil;
import com.example.tracker.util.DateUtil;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PersonViewActivity extends AppCompatActivity {
    private List<ChartItem> chartItems = new ArrayList<>();
    private RecyclerView rvChat;
    private ChatBoxAdapter chAdapter;
    private Context context = this;
    private List<PersonWithCredits> personWithCreditsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_view);
        Intent intent = getIntent();
        String pName = intent.getStringExtra("name");
        String pAction = intent.getStringExtra("amtStatus");
        Float amount = intent.getFloatExtra("amount",0F);
        String number = intent.getStringExtra("number");
        //get all transaction for the person
        this.personWithCreditsList = TrackerDB.getDb(this).personDao().getPersonDetailsByNumber(number);

        personWithCreditsList.get(0).creditsList.forEach(i->{
            ChartItem item = new ChartItem();
            item.setAmount(i.amount);
            item.setAction(i.crdType);
            item.setChannel(i.crdChannel);
            item.setDesc(i.desc);
            item.setDate(DateUtil.getStrDateFromLongDate(i.crdDate));
            chartItems.add(item);
        });
        rvChat = findViewById(R.id.rv_chat);
        rvChat.setLayoutManager(new LinearLayoutManager(context));
        chAdapter = new ChatBoxAdapter(chartItems);
        rvChat.setAdapter(chAdapter);
        // setup action bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle(pName);
            if(pAction!=null&&pAction.equalsIgnoreCase("Paid")) {
                actionBar.setSubtitle(Html.fromHtml("<font color=\"#F60000\">"+amount+ "</font>"));
            } else {
                actionBar.setSubtitle(Html.fromHtml("<font color=\"#0B8100\">"+amount+ "</font>"));
            }
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        // setup pay button
        Button btnPay = findViewById(R.id.btn_paid);
        TextInputLayout amtLay = findViewById(R.id.til_text);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean amtError = false;
                String amount = Objects.requireNonNull(amtLay.getEditText()).getText().toString().trim();
                if(amount.equalsIgnoreCase("")){
                    amtLay.setError("Enter Amount");
                    amtError = true;
                }
                try {
                    Float.parseFloat(amount);
                } catch (Exception ex){
                    amtLay.setError("Enter A Valid Amount");
                    amtError = true;
                }
                if(amount.equalsIgnoreCase("0")){
                    amtLay.setError("Enter A Number grater to Zero");
                    amtError = true;
                }
                if(!amtError) {
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.add_credit_amount);
                    dialog.show();
                    AutoCompleteTextView trnChan = dialog.findViewById(R.id.edt_trn_channel);
                    TextInputLayout crdDate = dialog.findViewById(R.id.crd_date);
                    TextInputLayout crdDesc = dialog.findViewById(R.id.crd_desc);
                    TextInputLayout llChannel = dialog.findViewById(R.id.trn_channel);
                    Button btnAdd = dialog.findViewById(R.id.btnAdd);
                    ChannelViewAdapter adapter = new ChannelViewAdapter(context, R.layout.credit_channels_list, AdapterUtil.getChannelList());
                    trnChan.setAdapter(adapter);
                    Objects.requireNonNull(crdDate.getEditText()).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DatePickerDialog dialogDt = new DatePickerDialog(context);
                            dialogDt.getDatePicker().setMaxDate(System.currentTimeMillis());
                            dialogDt.show();
                            dialogDt.setOnDateSetListener((datePicker, i, i1, i2) -> {
                                StringBuilder st = new StringBuilder();
                                st.append(i2 + "/");
                                st.append(i1 + 1 + "/");
                                st.append(i);
                                crdDate.getEditText().setText(st.toString());
                            });

                        }
                    });
                    btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean isError = false;
                            String chn = trnChan.getText().toString().trim();
                            String date = crdDate.getEditText().getText().toString().trim();
                            String desc = Objects.requireNonNull(crdDesc.getEditText()).getText().toString().trim();
                            if (chn.equalsIgnoreCase("")) {
                                llChannel.setError("Select Gateway");
                                isError = true;
                            }
                            if (date.equalsIgnoreCase("")) {
                                crdDate.setError("Enter Date");
                                isError = true;
                            }
                            if (!isError) {
                                Credits credits = new Credits();
                                credits.pId = personWithCreditsList.get(0).person.id;
                                credits.crdChannel = chn;
                                credits.desc = desc;
                                credits.crdType = "Paid";
                                credits.amount = Float.parseFloat(amount);
                                try {
                                    credits.crdDate = DateUtil.getLongDate(date);
                                } catch (ParseException ignore) {
                                }
                                ChartItem item = new ChartItem();
                                item.setAmount(credits.amount);
                                item.setAction(credits.crdType);
                                item.setChannel(credits.crdChannel);
                                item.setDesc(credits.desc);
                                item.setDate(DateUtil.getStrDateFromLongDate(credits.crdDate));
                                chartItems.add(item);
                                chAdapter.notifyItemInserted(chartItems.size()-1);
                                Person person = personWithCreditsList.get(0).person;
                                person.grossAmount = person.grossAmount-credits.amount;
                                if(person.grossAmount>=0){
                                    person.amtStatus = "Receive";
                                } else {
                                    person.amtStatus = "Paid";
                                }
                                if(person.amtStatus!=null&&person.amtStatus.equalsIgnoreCase("Paid")) {
                                    actionBar.setSubtitle(Html.fromHtml("<font color=\"#F60000\">"+person.grossAmount+ "</font>"));
                                } else {
                                    actionBar.setSubtitle(Html.fromHtml("<font color=\"#0B8100\">"+person.grossAmount+ "</font>"));
                                }
                                saveCredit(credits, person);
                                amtLay.getEditText().getText().clear();
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }
        });
        Button btnRcv = findViewById(R.id.btn_received);
        btnRcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean amtError = false;
                String amount = Objects.requireNonNull(amtLay.getEditText()).getText().toString().trim();
                if(amount.equalsIgnoreCase("")){
                    amtLay.setError("Enter Amount");
                    amtError = true;
                }
                try {
                    Float.parseFloat(amount);
                } catch (Exception ex){
                    amtLay.setError("Enter A Valid Amount");
                    amtError = true;
                }
                if(amount.equalsIgnoreCase("0")){
                    amtLay.setError("Enter A Number grater to Zero");
                    amtError = true;
                }
                if(!amtError) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_credit_amount);
                dialog.show();
                AutoCompleteTextView trnChan = dialog.findViewById(R.id.edt_trn_channel);
                TextInputLayout crdDate = dialog.findViewById(R.id.crd_date);
                TextInputLayout crdDesc = dialog.findViewById(R.id.crd_desc);
                TextInputLayout llChannel = dialog.findViewById(R.id.trn_channel);
                Button btnAdd = dialog.findViewById(R.id.btnAdd);
                ChannelViewAdapter adapter = new ChannelViewAdapter(context, R.layout.credit_channels_list, AdapterUtil.getChannelList());
                trnChan.setAdapter(adapter);
                Objects.requireNonNull(crdDate.getEditText()).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog dialogDt = new DatePickerDialog(context);
                        dialogDt.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dialogDt.show();
                        dialogDt.setOnDateSetListener((datePicker, i, i1, i2) -> {
                            StringBuilder st = new StringBuilder();
                            st.append(i2 + "/");
                            st.append(i1 + 1 + "/");
                            st.append(i);
                            crdDate.getEditText().setText(st.toString());
                        });

                    }
                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isError = false;
                        String chn = trnChan.getText().toString().trim();
                        String date = crdDate.getEditText().getText().toString().trim();
                        String desc = Objects.requireNonNull(crdDesc.getEditText()).getText().toString().trim();
                        if (chn.equalsIgnoreCase("")) {
                            llChannel.setError("Select Gateway");
                            isError = true;
                        }
                        if (date.equalsIgnoreCase("")) {
                            crdDate.setError("Enter Date");
                            isError = true;
                        }
                        if (!isError) {
                            Credits credits = new Credits();
                            credits.pId = personWithCreditsList.get(0).person.id;
                            credits.crdChannel = chn;
                            credits.desc = desc;
                            credits.crdType = "Receive";
                            credits.amount = Float.parseFloat(amount);
                            try {
                                credits.crdDate = DateUtil.getLongDate(date);
                            } catch (ParseException ignore) {
                            }
                            ChartItem item = new ChartItem();
                            item.setAmount(credits.amount);
                            item.setAction(credits.crdType);
                            item.setChannel(credits.crdChannel);
                            item.setDesc(credits.desc);
                            item.setDate(DateUtil.getStrDateFromLongDate(credits.crdDate));
                            chartItems.add(item);
                            chAdapter.notifyItemInserted(chartItems.size()-1);
                            Person person = personWithCreditsList.get(0).person;
                            person.grossAmount = person.grossAmount+credits.amount;
                            if(person.grossAmount>=0){
                                person.amtStatus = "Receive";
                            } else {
                                person.amtStatus = "Paid";
                            }
                            if(person.amtStatus!=null&&person.amtStatus.equalsIgnoreCase("Paid")) {
                                actionBar.setSubtitle(Html.fromHtml("<font color=\"#F60000\">"+person.grossAmount+ "</font>"));
                            } else {
                                actionBar.setSubtitle(Html.fromHtml("<font color=\"#0B8100\">"+person.grossAmount+ "</font>"));
                            }
                            saveCredit(credits, person);
                            amtLay.getEditText().getText().clear();
                            dialog.dismiss();
                        }
                    }
                });
            }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu ) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {

        switch (item.getItemId()){
            case R.id.paid:
                List<ChartItem> paidItems = chartItems.stream().filter(i->i.getAction().equalsIgnoreCase("Paid")).collect(Collectors.toList());
                chAdapter = new ChatBoxAdapter(paidItems);
                rvChat.setAdapter(chAdapter);
                Toast.makeText(this, "Filter Paid Applied", Toast.LENGTH_SHORT).show();
                break;
            case R.id.recvd:
                List<ChartItem> receivedItems = chartItems.stream().filter(i->i.getAction().equalsIgnoreCase("Receive")).collect(Collectors.toList());
                chAdapter = new ChatBoxAdapter(receivedItems);
                rvChat.setAdapter(chAdapter);
                Toast.makeText(this, "Filter Receive Applied", Toast.LENGTH_SHORT).show();
                break;
            case R.id.clrFil:
                chAdapter = new ChatBoxAdapter(chartItems);
                rvChat.setAdapter(chAdapter);
                Toast.makeText(this, "Filter Cleared", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                TrackerDB db = TrackerDB.getDb(context);
                personWithCreditsList.get(0).creditsList.forEach(i->{
                    db.creditsDao().delete(i);
                });
                Person person = personWithCreditsList.get(0).person;
                db.personDao().delete(person);
                Intent intent = new Intent(context,CBDashboardActivity.class);
                startActivity(intent);
                Toast.makeText(this, "User Deleted", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean saveCredit(Credits credits, Person person){
        try{
            TrackerDB.getDb(context).creditsDao().insertAll(credits);
            TrackerDB.getDb(context).personDao().update(person);
            return true;
        } catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}