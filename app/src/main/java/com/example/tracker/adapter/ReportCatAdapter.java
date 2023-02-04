package com.example.tracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracker.R;
import com.example.tracker.dto.CatData;
import com.example.tracker.dto.SectionItem;

import java.util.ArrayList;
import java.util.List;

public class ReportCatAdapter extends RecyclerView.Adapter<ReportCatAdapter.ReportCatViewHolder> {
    private static List<CatData> catItemLists = new ArrayList<>();
    private List<CatData> catList = new ArrayList<>();

    public ReportCatAdapter(List<CatData> catList) {
        this.catList = catList;
    }

    @NonNull
    @Override
    public ReportCatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_row,parent,false);
        return new ReportCatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportCatViewHolder holder, int position) {
        CatData catItem = catList.get(position);
        catItemLists.add(catItem);
        holder.tvCatName.setText(catItem.getCatName());
        holder.tvVal.setText(catItem.getAmount().toString());
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public class ReportCatViewHolder extends RecyclerView.ViewHolder{

        public TextView tvCatName,tvVal;
        public ReportCatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCatName = itemView.findViewById(R.id.rpt_cat_name);
            tvVal = itemView.findViewById(R.id.tvVal);
        }
    }
}
