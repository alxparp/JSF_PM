package com.genome.parpalak.controllers;

import com.genome.parpalak.bean.History;
import com.genome.parpalak.bean.User;
import com.genome.parpalak.genome.models.HistoryModel;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(eager=true)
@SessionScoped
public class HistoryController implements Serializable {
    
    ArrayList<History> currentHistories;
    HistoryModel historyModel;
    FacesContext context = FacesContext.getCurrentInstance();
    TaskController taskController;
    User user = context.getApplication().evaluateExpressionGet(context, "#{user}", User.class);
    private int selectedProjectId;
    
    
    public HistoryController() {
        historyModel = new HistoryModel();
        taskController = context.getApplication().evaluateExpressionGet(context, "#{taskController}", TaskController.class);
        selectedProjectId = taskController.getSelectedProjectId();
    }
    
    public HistoryController(int selectedProjectId) {
        historyModel = new HistoryModel();
        this.selectedProjectId = selectedProjectId;
    }
    
    public void fillHistories() {
        currentHistories = historyModel.getAllHistories(selectedProjectId);
    }
    
    public String clickHistory() {
        fillHistories();
        return "history.xhtml";
    }
    
    public void addNewHistory(String description) {
        historyModel.addNewHistory(description,selectedProjectId,user.getUsername());
        fillHistories();
    }

    public ArrayList<History> getCurrentHistories() {
        return currentHistories;
    }

    public void setCurrentHistories(ArrayList<History> currentHistories) {
        this.currentHistories = currentHistories;
    }
 
}
