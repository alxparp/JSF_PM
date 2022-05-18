package com.genome.parpalak.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Sprint {
    
    private boolean lastSprintInWork;
    private int sprintId;
    private String name;
    private Date startDate;
    private Date endDate;
    private ArrayList<Task> sprintTasks;
    private boolean completed;
    
    public Sprint() {
        
    }

    public int getSprintId() {
        return sprintId;
    }

    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ArrayList<Task> getSprintTasks() {
        return sprintTasks;
    }

    public void setSprintTasks(ArrayList<Task> sprintTasks) {
        this.sprintTasks = sprintTasks;
    }
    
    public String getStartDateString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(startDate);
    }
    
    public String getEndDateString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(endDate);
    }

    public boolean isLastSprintInWork() {
        return lastSprintInWork;
    }

    public void setLastSprintInWork(boolean lastSprintInWork) {
        this.lastSprintInWork = lastSprintInWork;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
 
}
