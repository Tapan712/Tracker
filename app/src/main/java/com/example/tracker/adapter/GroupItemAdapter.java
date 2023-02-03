package com.example.tracker.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracker.R;
import com.example.tracker.dto.SectionItem;

import java.util.ArrayList;
import java.util.List;

public class GroupItemAdapter extends RecyclerView.Adapter<GroupItemAdapter.GroupItemViewHolder> {
    static private List<SectionItem> sectionItemLists = new ArrayList<>();
    private List<SectionItem> sectionItemList = new ArrayList<>();

    public GroupItemAdapter(List<SectionItem> sectionItemList) {
        this.sectionItemList = sectionItemList;
    }

    @NonNull
    @Override
    public GroupItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_items,parent,false);
        return new GroupItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupItemViewHolder holder, int position) {
        SectionItem secItem = sectionItemList.get(position);
        sectionItemLists.add(secItem);
        holder.tvName.setText(secItem.getExpName());
        holder.tvType.setText(secItem.getExpType());
        holder.tvTotal.setText(secItem.getExpTotal());
        holder.tvByWhom.setText(secItem.getByWhom());
        holder.tvNoItems.setText(secItem.getNoOfItems());
        holder.tvPrice.setText(secItem.getPrice());
//        debit = expense red, credit = deposit green
        if(secItem.getExpType()!=null){
            if(secItem.getExpType().equalsIgnoreCase("Debit")){
                holder.tvTotal.setTextColor(Color.parseColor("#F60000"));
            }
            else{
                holder.tvTotal.setTextColor(Color.parseColor("#0B8100"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return sectionItemList.size();
    }

    public class GroupItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName,tvType,tvTotal,tvByWhom,tvNoItems,tvPrice;
        public GroupItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoItems = itemView.findViewById(R.id.noOfItems);
            tvByWhom = itemView.findViewById(R.id.byWhom);
            tvName = itemView.findViewById(R.id.name);
            tvPrice = itemView.findViewById(R.id.price);
            tvType = itemView.findViewById(R.id.type);
            tvTotal = itemView.findViewById(R.id.totPrice);
        }
    }
}
