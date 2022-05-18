package com.genome.parpalak.bean;

import java.util.ArrayList;

public class Calendar {
    
    private int day;
    private ArrayList<Task> taskList;

    public Calendar() {
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> task) {
        this.taskList = task;
    }
    
}
