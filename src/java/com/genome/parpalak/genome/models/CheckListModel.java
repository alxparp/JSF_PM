package com.genome.parpalak.genome.models;

import com.genome.parpalak.bean.CheckList;
import com.genome.parpalak.bean.Items;
import com.genome.parpalak.bean.Pager;
import java.util.ArrayList;

public class CheckListModel extends Model {
    Pager pager;
    
    public CheckListModel(Pager pager) {
        this.pager = pager;
    }
    
    public ArrayList<CheckList> getStoryWithCheckLists(String selectedStoryId) {
        String sql = "select task_id, check_list_name "
                + "from task "
                + "where task_id = " + selectedStoryId;
        
        ArrayList<CheckList> checkLists = new ArrayList<>();

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"task_id", "check_list_name"});
        for (ArrayList list : listOfLists) {
            CheckList checkList = new CheckList();
            checkList.setCheckListId(Integer.valueOf(list.get(0).toString()));
            if(!list.get(1).toString().equals(""))
                checkList.setName(list.get(1).toString());
            else
                checkList.setName(null);
            checkLists.add(checkList);
        }

        return checkLists;
    }
    
    public ArrayList<Items> getCheckListItems(int checkListId) {
        String sql = "select item_id, name, completed "
                + "from items i "
                + "where task_id = " + checkListId;
        
        ArrayList<Items> items = new ArrayList<>();

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"item_id", "name", "completed"});
        for (ArrayList list : listOfLists) {
            Items item = new Items();
            item.setItemId(Integer.valueOf(list.get(0).toString()));
            item.setName(list.get(1).toString());
            items.add(item);
        }
        
        return items;
    }
    
    public void addNewItem(String checkListId, String name) {
        String sql = "insert into items(task_id, completed, name) "
                + "values(" + checkListId + ", 0, '" + name + "')";
        updateData(sql);
    }
    
    public void deleteItem(String checkListId, String itemId) {
        String sql = "delete from items "
                + "where item_id = " + itemId + " "
                + "and task_id = " + checkListId; 
        updateData(sql);
    }
    
    public void addNewCheckList(String taskId, String name) {
        String sql = "update task set check_list_name = '" + name + "' "
                + "where task_id = " + taskId;
        updateData(sql);
    }
    
    public void deleteCheckList(String checkListId) {
        String sql = "update task set check_list_name = null where task_id = " + checkListId;
        updateData(sql);
        
        sql = "delete from items where task_id = " + checkListId;
        updateData(sql);
    }
    
}
