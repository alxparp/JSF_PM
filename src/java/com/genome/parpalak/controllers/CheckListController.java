package com.genome.parpalak.controllers;

import com.genome.parpalak.bean.CheckList;
import com.genome.parpalak.bean.Pager;
import com.genome.parpalak.genome.models.CheckListModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(eager=true)
@SessionScoped
public class CheckListController implements Serializable {
    
    CheckListModel checkListModel;
    ArrayList<CheckList> currentCheckLists;
    String newItem;
    String newCheckList;
    HistoryController historyController;
    
    public CheckListController() {
        Pager pager = new Pager();
        checkListModel = new CheckListModel(pager);
        historyController = new HistoryController();
    }
    
    public void fillStoryWithCheckLists() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        
        currentCheckLists = checkListModel.getStoryWithCheckLists(params.get("task_id"));
        for(CheckList checkList : currentCheckLists) {
            checkList.setItems(checkListModel.getCheckListItems(checkList.getCheckListId()));
        }
        
    }
    
    public void addNewItem() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String checkListId = params.get("check_list_id");
        String name = getNewItem();
        checkListModel.addNewItem(checkListId, name);
        fillStoryWithCheckLists();
        newItem = "";
        historyController.addNewHistory(" добавил пункт \"" + name + "\" в чеклист \"" + currentCheckLists.get(0).getName() + "\"");
    }
    
    public void deleteItem() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        checkListModel.deleteItem(params.get("check_list_id"), params.get("item_id"));
        historyController.addNewHistory(" удалил пункт \"" + params.get("item_name") + "\" из чеклиста \"" + currentCheckLists.get(0).getName() + "\"");
        fillStoryWithCheckLists();
    }
    
    public void deleteCheckList() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        checkListModel.deleteCheckList(params.get("check_list_id"));
        historyController.addNewHistory(" удалил чек-лист \"" + params.get("check_list_name") + "\" из задании \"" + params.get("task_name") + "\"");
        fillStoryWithCheckLists();
    }
    
    public void addNewCheckList() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String taskId = params.get("task_id");
        checkListModel.addNewCheckList(taskId, newCheckList);
        historyController.addNewHistory(" добавил чек-лист \"" + newCheckList + "\" в задании \"" + params.get("task_name") + "\"");
        fillStoryWithCheckLists();
        newCheckList = "";
    }

    public ArrayList<CheckList> getCurrentCheckLists() {
        return currentCheckLists;
    }

    public void setCurrentCheckLists(ArrayList<CheckList> currentCheckLists) {
        this.currentCheckLists = currentCheckLists;
    }

    public String getNewItem() {
        return newItem;
    }

    public void setNewItem(String newItem) {
        this.newItem = newItem;
    }

    public String getNewCheckList() {
        return newCheckList;
    }

    public void setNewCheckList(String newCheckList) {
        this.newCheckList = newCheckList;
    }
  
}
