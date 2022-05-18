package com.genome.parpalak.bean;

public class Items {
    
    private int itemId;
    private String name;
    private int completed;
    private int checkListId;

    public Items() {
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getCheckListId() {
        return checkListId;
    }

    public void setCheckListId(int checkListId) {
        this.checkListId = checkListId;
    }
    
}
