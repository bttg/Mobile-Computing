package com.example.myapplication.database;

//Class of specific types of income or expenditure
public class Record_TypeforEachOne {
    int identity;
    int id_for_notselected;
    int id_for_selected;
    String  image_name;
    int expenseorincome;

    public Record_TypeforEachOne(int identity, int id_for_notselected, int id_for_selected, String image_name, int expenseorincome) {
        this.identity = identity;
        this.id_for_notselected = id_for_notselected;
        this.id_for_selected = id_for_selected;
        this.image_name = image_name;
        this.expenseorincome = expenseorincome;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public int getId_for_notselected() {
        return id_for_notselected;
    }

    public void setId_for_notselected(int id_for_notselected) {
        this.id_for_notselected = id_for_notselected;
    }

    public int getId_for_selected() {
        return id_for_selected;
    }

    public void setId_for_selected(int id_for_selected) {
        this.id_for_selected = id_for_selected;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public int getExpenseorincome() {
        return expenseorincome;
    }

    public void setExpenseorincome(int expenseorincome) {
        this.expenseorincome = expenseorincome;
    }

    public Record_TypeforEachOne() {
    }
}
