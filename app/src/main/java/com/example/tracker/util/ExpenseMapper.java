package com.example.tracker.util;

import com.example.tracker.dto.Deposit;
import com.example.tracker.dto.SectionItem;
import com.example.tracker.entity.Transaction;

import java.text.ParseException;
import java.util.Objects;

public class ExpenseMapper {
    public static Transaction depositToTransaction(Deposit deposit){
        Transaction trn = new Transaction();
        if(!Objects.isNull(deposit.getId())){
            trn.id = deposit.getId();
        }
        trn.trnName = deposit.getName();
        trn.total = deposit.getAmount();
        trn.price = deposit.getAmount();
        trn.noOfItems = 1;
        trn.trnType = "Credit";
        trn.byWhom = deposit.getFrom();
        try {
            trn.trnDate = DateUtil.getLongDate(deposit.getDate());
        } catch (ParseException ignored) {
        }
        return trn;
    }

    public static Deposit transactionToDeposit(Transaction transaction){
        Deposit deposit = new Deposit();
        deposit.setId(transaction.id);
        deposit.setName(transaction.trnName);
        deposit.setAmount(transaction.price);
        deposit.setFrom(transaction.byWhom);
        deposit.setDate(DateUtil.getStrDateFromLongDate(transaction.trnDate));
        return deposit;
    }

    public static SectionItem transactionToSectionItem(Transaction trn){
        SectionItem si = new SectionItem();
        si.setId(trn.id);
        si.setExpName(trn.trnName);
        si.setExpType(trn.trnType);
        si.setExpTotal(trn.total.toString());
        si.setByWhom(trn.byWhom);
        si.setNoOfItems(trn.noOfItems.toString());
        si.setPrice(trn.price.toString());
        return si;
    }

}
