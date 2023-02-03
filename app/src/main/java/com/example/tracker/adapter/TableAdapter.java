package com.example.tracker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracker.AddDepositActivity;
import com.example.tracker.AddExpenseActivity;
import com.example.tracker.R;
import com.example.tracker.TransactionDashboardActivity;
import com.example.tracker.dbhelper.TrackerDB;
import com.example.tracker.dto.SectionItem;
import com.example.tracker.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableItemViewHolder> {
    static private List<SectionItem> sectionItemLists = new ArrayList<>();
    private List<SectionItem> sectionItemList = new ArrayList<>();

    private String type;
    private String action;

    private Context context;

    public TableAdapter(String action ,String type,Context context, List<SectionItem> sectionItemList) {
        this.sectionItemList = sectionItemList;
        this.action = action;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public TableItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_items,parent,false);
        return new TableItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableItemViewHolder holder, int position) {
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
        holder.llRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrackerDB db = TrackerDB.getDb(context);
                Transaction trn = db.transactionDao().findById(secItem.getId()).orElse(null);
                if(action.equalsIgnoreCase("Edit")) {
                    Intent intent = new Intent(context, type.equalsIgnoreCase("Debit") ? AddExpenseActivity.class : AddDepositActivity.class);
                    intent.putExtra("EditTransaction", trn);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else if (action.equalsIgnoreCase("Delete")) {
                    try {
                        db.transactionDao().delete(trn);
//                        notifyItemChanged(position);
                        new SweetAlertDialog(
                                context, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Success")
                                .setContentText("Transaction Deleted Successfully.")
                                .show();
                    } catch (Exception ex){
                        new SweetAlertDialog(
                                context, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Failed")
                                .setContentText("Transaction Deletion Failed.")
                                .show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sectionItemList.size();
    }

    public class TableItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName,tvType,tvTotal,tvByWhom,tvNoItems,tvPrice;
        private CardView llRow;
        public TableItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoItems = itemView.findViewById(R.id.noOfItems);
            tvByWhom = itemView.findViewById(R.id.byWhom);
            tvName = itemView.findViewById(R.id.name);
            tvPrice = itemView.findViewById(R.id.price);
            tvType = itemView.findViewById(R.id.type);
            tvTotal = itemView.findViewById(R.id.totPrice);
            llRow = itemView.findViewById(R.id.card_item);
        }
    }
}
