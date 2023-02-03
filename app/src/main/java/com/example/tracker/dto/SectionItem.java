package com.example.tracker.dto;

public class SectionItem {
    private Integer id;
    private String expName;
    private String expType;
    private String expTotal;
    private String byWhom;
    private String noOfItems;
    private String price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }

    public String getExpTotal() {
        return expTotal;
    }

    public void setExpTotal(String expTotal) {
        this.expTotal = expTotal;
    }

    public String getByWhom() {
        return byWhom;
    }

    public void setByWhom(String byWhom) {
        this.byWhom = byWhom;
    }

    public String getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(String noOfItems) {
        this.noOfItems = noOfItems;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SectionItem{" +
                "expName='" + expName + '\'' +
                ", expType='" + expType + '\'' +
                ", expTotal='" + expTotal + '\'' +
                ", byWhom='" + byWhom + '\'' +
                ", noOfItems='" + noOfItems + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
