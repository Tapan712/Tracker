package com.example.tracker.dto;

import java.util.List;

public class ViewSection {
    private String trnDate;
    private List<SectionItem> itemList;

    public String getTrnDate() {
        return trnDate;
    }

    public void setTrnDate(String trnDate) {
        this.trnDate = trnDate;
    }

    public List<SectionItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SectionItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "ViewSection{" +
                "trnDate='" + trnDate + '\'' +
                ", itemList=" + itemList +
                '}';
    }
}
