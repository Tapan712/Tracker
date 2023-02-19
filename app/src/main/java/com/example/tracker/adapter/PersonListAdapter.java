package com.example.tracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracker.PersonViewActivity;
import com.example.tracker.R;
import com.example.tracker.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.PersonListViewHolder> {
    List<Person> personList = new ArrayList<>();
    private Context context;

    public PersonListAdapter(List<Person> personList, Context context) {
        this.personList = personList;
        this.context = context;
    }

    @NonNull
    @Override
    public PersonListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_list_item,parent,false);
        return new PersonListAdapter.PersonListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonListViewHolder holder, int position) {
        Person person = personList.get(position);
        holder.tvName.setText(person.cName);
        holder.tvGross.setText(person.grossAmount.toString());
        if(person.amtStatus!=null&&person.amtStatus.equalsIgnoreCase("Paid")) {
            holder.tvGross.setTextColor( context.getResources().getColor(R.color.lbl_red));
        } else {
            holder.tvGross.setTextColor( context.getResources().getColor(R.color.lbl_green));
        }
        holder.prsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,PersonViewActivity.class);
                intent.putExtra("name",person.cName);
                intent.putExtra("number",person.cNumber);
                intent.putExtra("amount",person.grossAmount);
                intent.putExtra("amtStatus",person.amtStatus);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class PersonListViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName,tvGross;
        CardView prsCard;

        public PersonListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_prs_name);
            tvGross = itemView.findViewById(R.id.tv_prs_gross);
            prsCard = itemView.findViewById(R.id.prs_card);
        }
    }
}
