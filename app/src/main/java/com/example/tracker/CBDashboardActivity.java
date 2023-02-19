package com.example.tracker;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracker.adapter.PersonListAdapter;
import com.example.tracker.dbhelper.TrackerDB;
import com.example.tracker.dto.ChartItem;
import com.example.tracker.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CBDashboardActivity extends AppCompatActivity {
    private RecyclerView personView;
    private PersonListAdapter personListAdapter;
    private static final int RESULT_PICK_CONTACT =1;
    public static List<Person> personList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbdashboard);
        Button btnAddPer = findViewById(R.id.btn_add_per);
        TextView toPay = findViewById(R.id.txtPay);
        TextView toRcv= findViewById(R.id.txtRcv);
        personView = findViewById(R.id.rel_person);
        SearchView searchView = findViewById(R.id.searchView);
        personView.setLayoutManager(new LinearLayoutManager(this));
        personList = TrackerDB.getDb(this).personDao().getAllPerson();
        Float[] dashArr  = getTotal(personList);
        toPay.setText(Math.abs(dashArr[0])+"");
        toRcv.setText(Math.abs(dashArr[1])+"");
        personListAdapter = new PersonListAdapter(personList,this);
        personView.setAdapter(personListAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Person> filteredItems = personList.stream().filter(i->i.cName.toLowerCase().contains(s.toLowerCase())).collect(Collectors.toList());
                personListAdapter = new PersonListAdapter(filteredItems,CBDashboardActivity.this);
                personView.setAdapter(personListAdapter);
                return true;
            }
        });
        btnAddPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent (Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult (in, RESULT_PICK_CONTACT);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        personView = findViewById(R.id.rel_person);
        TextView toPay = findViewById(R.id.txtPay);
        TextView toRcv= findViewById(R.id.txtRcv);
        personView.setLayoutManager(new LinearLayoutManager(this));
        personList = TrackerDB.getDb(this).personDao().getAllPerson();
        Float[] dashArr  = getTotal(personList);
        toPay.setText(Math.abs(dashArr[0])+"");
        toRcv.setText(Math.abs(dashArr[1])+"");
        personListAdapter = new PersonListAdapter(personList,this);
        personView.setAdapter(personListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Toast.makeText(this, "Failed To pick contact", Toast.LENGTH_SHORT).show();
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;

        try {
            String phoneNo = null;
            String name = null;
            Uri uri = data.getData ();
            cursor = getContentResolver ().query (uri, null, null,null,null);
            cursor.moveToFirst ();
            int phoneIndex = cursor.getColumnIndex (ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

            phoneNo = cursor.getString (phoneIndex);
            name = cursor.getString(nameIndex);

            // create an object in db
            Person person = new Person();
            person.cName = name;
            person.cNumber = phoneNo;
            person.grossAmount = 0F;
            TrackerDB.getDb(CBDashboardActivity.this).personDao().insertAll(person);
            personList.add(person);
            personListAdapter.notifyItemInserted(personList.indexOf(person));
            Toast.makeText(this, name+"---->"+phoneNo, Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public Float[] getTotal(List<Person> personList){
        Float toPay = personList.stream().filter(Objects::nonNull).filter(i->i.amtStatus!=null&&i.amtStatus.equalsIgnoreCase("Receive")).map(e->e.grossAmount).reduce(Float::sum).orElse(0F);
        Float toReceive = personList.stream().filter(Objects::nonNull).filter(i->i.amtStatus!=null&&i.amtStatus.equalsIgnoreCase("Paid")).map(e->e.grossAmount).reduce(Float::sum).orElse(0F);
        return new Float[]{toPay,toReceive};
    }

}