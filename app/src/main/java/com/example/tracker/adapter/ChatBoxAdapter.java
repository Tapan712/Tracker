package com.example.tracker.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracker.R;
import com.example.tracker.dto.ChartItem;

import java.util.ArrayList;
import java.util.List;

public class ChatBoxAdapter extends RecyclerView.Adapter<ChatBoxAdapter.ChatBoxViewHolder>{

    List<ChartItem> chartItems = new ArrayList<>();

    public ChatBoxAdapter(List<ChartItem> chartItems) {
        this.chartItems = chartItems;
    }

    @NonNull
    @Override
    public ChatBoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_box,parent,false);
        return new ChatBoxAdapter.ChatBoxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatBoxViewHolder holder, int position) {
        ChartItem item = chartItems.get(position);
        if(item.getAmount()!=null){
            holder.tvAmount.setText(item.getAmount().toString());
        }
        holder.tvAction.setText(item.getAction());
        holder.tvDate.setText(item.getDate());
        holder.tvChannel.setText(item.getChannel());
        if(item.getDesc()!=null) {
            holder.tvDesc.setText(item.getDesc());
        }
        if(item.getAction().equalsIgnoreCase("Paid")){
            holder.tvAmount.setTextColor(Color.parseColor("#EF5350"));
        } else if (item.getAction().equalsIgnoreCase("Receive")) {
            holder.tvAmount.setTextColor(Color.parseColor("#0B8100"));
        }
    }

    @Override
    public int getItemCount() {
        return chartItems.size();
    }

    public class ChatBoxViewHolder extends RecyclerView.ViewHolder{
        private TextView tvAmount,tvAction,tvDate,tvChannel,tvDesc;
        public ChatBoxViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.box_amount);
            tvAction = itemView.findViewById(R.id.box_action);
            tvDate = itemView.findViewById(R.id.box_date);
            tvChannel = itemView.findViewById(R.id.box_channel);
            tvDesc = itemView.findViewById(R.id.box_desc);
        }
    }
}
