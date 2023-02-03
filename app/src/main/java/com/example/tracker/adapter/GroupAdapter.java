package com.example.tracker.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracker.R;
import com.example.tracker.dto.SectionItem;
import com.example.tracker.dto.ViewSection;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private List<ViewSection> sectionList;

    public GroupAdapter(List<ViewSection> sectionList) {
        this.sectionList = sectionList;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_groups,parent,false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        ViewSection viewSection = sectionList.get(position);
        String groupDate = viewSection.getTrnDate();
        List<SectionItem> sectionItemList = viewSection.getItemList();

        holder.textView.setText(groupDate);

        GroupItemAdapter groupItemAdapter = new GroupItemAdapter(sectionItemList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.textView.getContext());
        layoutManager.setInitialPrefetchItemCount(30);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerViewItem.setLayoutManager(layoutManager);
        holder.recyclerViewItem.setAdapter(groupItemAdapter);
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    public void swapList(List<ViewSection> list){
        sectionList = list;
        notifyDataSetChanged();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private RecyclerView recyclerViewItem;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.group_date);
            recyclerViewItem = itemView.findViewById(R.id.rvGroupItem);
        }
    }
}
