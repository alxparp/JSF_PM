package com.genome.parpalak.bean;

import java.util.ArrayList;

public class CheckList {
    
    private int checkListId;
    private String name;
    private ArrayList<Items> items;

    public CheckList() {
    }

    public int getCheckListId() {
        return checkListId;
    }

    public void setCheckListId(int checkListId) {
        this.checkListId = checkListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }
    
}
