package com.genome.parpalak.controllers;

import com.genome.parpalak.bean.Calendar;
import com.genome.parpalak.bean.Task;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(eager = true)
@SessionScoped
public class CalendarController implements Serializable {

    private ArrayList<Calendar> calendarList;
    FacesContext context = FacesContext.getCurrentInstance();
    SprintController sprintsListController = context.getApplication().evaluateExpressionGet(context, "#{sprintController}", SprintController.class);

    public CalendarController() {
        calendarList = new ArrayList<Calendar>();
        fillCalendar();
    }

    public int getNumberDaysInMonth() {
        GregorianCalendar calendar = new GregorianCalendar();
        int days = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
        System.out.println(days);
        return days;
    }
    
    public String clickCalendar() {
        fillCalendar();
        return "calendar";
    }

    public void fillCalendar() {

        calendarList.clear();
        ArrayList<Task> taskNameList = sprintsListController.getTasksNameList();
        if(taskNameList == null) 
            return;
        for (int i = 1; i <= getNumberDaysInMonth(); i++) {
            Calendar calendar = new Calendar();
            calendar.setDay(i);
            ArrayList<Task> taskList = new ArrayList<Task>();
            for (Task task : taskNameList) {

                Date date = task.getDatetime();
                if (date != null) {
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.setTime(date);
                    int day = cal.get(java.util.Calendar.DAY_OF_MONTH);

                    System.out.println(day);
                    if (day == i) {
                        taskList.add(task);
                    }
                }
            }
            calendar.setTaskList(taskList);
            calendarList.add(calendar);
        }
    }

    public ArrayList<Calendar> getCalendarList() {
        return calendarList;
    }

    public void setCalendarList(ArrayList<Calendar> calendarList) {
        this.calendarList = calendarList;
    }

}
