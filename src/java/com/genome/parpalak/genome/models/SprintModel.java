package com.genome.parpalak.genome.models;

import com.genome.parpalak.bean.Sprint;
import com.genome.parpalak.bean.Task;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SprintModel extends Model {

    public SprintModel() {

    }

    public ArrayList<Task> getTasksBySprint(int sprintId, int projectId) {
        String sql = "select task_id, name, datetime, description, table_id "
                + "from task "
                + "where project_id = " + projectId + " "
                + "and sprint_id = " + sprintId + " "
                + "order by name desc";

        ArrayList<Task> taskList = new ArrayList<>();

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"task_id", "name", "datetime", "description", "table_id"});
        for (ArrayList list : listOfLists) {
            Task task = new Task();
            task.setTaskId(Integer.valueOf(list.get(0).toString()));
            task.setName(list.get(1).toString());
            if(list.get(2) != "")
                task.setDatetime((Date) list.get(2));
            else 
                task.setDatetime(null);
            task.setDescription(list.get(3).toString());
            task.setTableId(Integer.valueOf(list.get(4).toString()));
            taskList.add(task);
        }
        return taskList;
    }

    public void deleteSprint(int sprintId) {
        String sql = "update task set sprint_id = null where table_id = 1 or table_id = 2";
        updateData(sql);
        sql = "delete from sprint where sprint_id = " + sprintId;
        updateData(sql);
    }

    public void newSprint(String name, Date startDate, Date endDate, ArrayList<Task> tasks, int projectId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "insert into sprint(name, start, end, project_id, completed) "
                + "values('" + name + "', '" + sdf.format(startDate) + "', '" + sdf.format(endDate) + "', " + projectId + ", 0)";

        updateData(sql);

        int maxSprintId = getMaxSprintId();

        new TaskModel().updateTasksWithSprint(maxSprintId, tasks);

    }

    private int getMaxSprintId() {
        String sql = "select max(sprint_id) as max_sprint_id from sprint";

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"max_sprint_id"});
        for (ArrayList list : listOfLists) {
            return Integer.valueOf(list.get(0).toString());
        }
        return 0;
    }

    public ArrayList<Sprint> getSprintsAll(int projectId) {
        String sql = "select sprint_id, name, start, end, (end <= now()) as last_sprint_in_work, completed "
                + "from sprint "
                + "where project_id = " + projectId + " "
                + "order by sprint_id desc";

        ArrayList<Sprint> sprintList = new ArrayList<>();

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"sprint_id", "name", "start", "end", "last_sprint_in_work", "completed"});
        for (ArrayList list : listOfLists) {
            Sprint sprint = new Sprint();
            sprint.setSprintId(Integer.valueOf(list.get(0).toString()));
            sprint.setName(list.get(1).toString());
            sprint.setStartDate((Date) list.get(2));
            sprint.setEndDate((Date) list.get(3));
            Integer lastSprintInWork = Integer.valueOf(list.get(4).toString());
            Integer completed = Integer.valueOf(list.get(4).toString());
            if (lastSprintInWork == 1 || completed == 1) {
                sprint.setLastSprintInWork(true);
            } else {
                sprint.setLastSprintInWork(false);
            }
            if (completed == 1) {
                sprint.setCompleted(true);
            } else {
                sprint.setCompleted(false);
            }
            sprintList.add(sprint);
        }
        return sprintList;
    }

    public boolean ifSprintIsDone(int sprintId) {
        if(ifSprintIsCompleted(sprintId))
            return false;
        
        String sql = "select count(sprint_id) as count_sprint "
                + "from task "
                + "where table_id = 1 or table_id = 2 "
                + "and sprint_id = " + sprintId;
        
        System.out.println(sql);

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"count_sprint"});
        for (ArrayList list : listOfLists) {
            return Integer.valueOf(list.get(0).toString()) == 0 ? true : false;
        }
        return false;
    }
    
    public boolean ifSprintIsCompleted(int sprintId) {
        String sql = "select completed "
                + "from sprint "
                + "where sprint_id = " + sprintId;
        
        System.out.println(sql);

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"completed"});
        for (ArrayList list : listOfLists) {
            return Integer.valueOf(list.get(0).toString()) == 1 ? true : false;
        }
        return false;
    }
    
    public void finishSprint(int sprintId) {
        String sql = "update sprint set completed = 1 where sprint_id = " + sprintId;
        updateData(sql);
    }

}
