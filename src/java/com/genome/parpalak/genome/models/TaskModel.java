package com.genome.parpalak.genome.models;

import com.genome.parpalak.bean.Pager;
import com.genome.parpalak.bean.Task;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskModel extends Model {

    Pager pager;

    public TaskModel(Pager pager) {
        this.pager = pager;
    }

    public TaskModel() {

    }

    public String getBoardNameById(int tableId) {
        String sql = "select name from board where table_id = " + tableId;

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"name"});
        for (ArrayList list : listOfLists) {
            return list.get(0).toString();
        }

        return "";
    }

    public void updateTask(String story, int selectedProjectId) {
        String sql = "insert into task(name, table_id, project_id) "
                + "values('" + story + "', 1, " + selectedProjectId + ")";
        updateData(sql);
    }

    public void updateDescription(String description, int taskId) {
        String sql = "update task set description = '" + description + "' "
                + "where task_id = " + taskId;
        updateData(sql);
    }

    public void deleteStory(String taskId) {
        String sql = "delete from task where task_id = " + taskId;
        updateData(sql);
    }

    public void registerUser(String name, String username, String password, String email) {
        String sql = "insert into participant(name, username, password, email) "
                + "values('" + name + "', '" + username + "', '" + password + "', '" + email + "')";
        updateData(sql);

        sql = "insert into participants_groups(groupid, username) values('user', '" + username + "')";
        updateData(sql);
    }

    public ArrayList<Task> fillTasksAll(int selectedProjectId) {
        String sql = "select task_id, name, description, table_id "
                + "from task "
                + "where  project_id = " + selectedProjectId;

        sql += getSqlForPager(sql, new String[]{"task_id"});

        ArrayList<Task> taskList = new ArrayList<>();

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"task_id", "name", "description", "table_id"});
        for (ArrayList list : listOfLists) {
            Task task = new Task();
            task.setTaskId(Integer.valueOf(list.get(0).toString()));
            task.setName(list.get(1).toString());
            task.setDescription(list.get(2).toString());
            task.setTableId(Integer.valueOf(list.get(3).toString()));
            taskList.add(task);
        }

        return taskList;
    }

    public String getSqlForPager(String sql, String[] parameters) {
        ArrayList<ArrayList> listOfLists = selectData(sql, parameters);
        int total = listOfLists.size();
        StringBuilder sqlBuilder = new StringBuilder("");

        pager.setTotalTasksCount(total);
        int tasksOnPage = pager.getTasksCountOnPage();

        if (total > pager.getTasksCountOnPage()) {
            sqlBuilder.append(" limit ").append(pager.getSelectedPageNumber() * tasksOnPage - tasksOnPage).append(",").append(tasksOnPage);
        }

        return sqlBuilder.toString();
    }

    public ArrayList<Task> fillStory(int selectedStoryId) {
        String sql = "select t.task_id, t.name, t.datetime, t.description, b.name as board, null as participant_name "
                + "from task t, board b "
                + "where t.table_id = b.table_id "
                + "and t.participant_id is null "
                + "and t.task_id = " + selectedStoryId
                + " union "
                + "select t.task_id, t.name, t.datetime, t.description, b.name as board, p.name as participant_name "
                + "from task t, board b, participant p "
                + "where t.table_id = b.table_id "
                + "and p.participant_id = t.participant_id "
                + "and t.task_id = " + selectedStoryId;

        System.out.println(sql);

        ArrayList<Task> taskList = new ArrayList<>();

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"task_id", "name", "datetime", "description", "board", "participant_name"});
        for (ArrayList list : listOfLists) {
            Task task = new Task();
            task.setTaskId(Integer.valueOf(list.get(0).toString()));
            task.setName(list.get(1).toString());
            if (list.get(2) != "") {
                task.setDatetime((Date) list.get(2));
            } else {
                task.setDatetime(null);
            }
            task.setDescription(list.get(3).toString());
            task.setBoard(list.get(4).toString());
            String participant = list.get(5).toString();
            if (participant.equals("")) {
                task.setParticipant(null);
            } else {
                task.setParticipant(participant);
            }
            taskList.add(task);
        }

        return taskList;
    }

    public ArrayList<Task> fillTasksBySearch(int selectedProjectId, String search) {
        String sql = "select task_id, name, description, table_id from task "
                + "where project_id = " + selectedProjectId
                + " and lower(name) like '%" + search.toLowerCase() + "%' order by name ";

        sql += getSqlForPager(sql, new String[]{"task_id"});

        ArrayList<Task> taskList = new ArrayList<>();

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"task_id", "name", "description", "table_id"});
        for (ArrayList list : listOfLists) {
            Task task = new Task();
            task.setTaskId(Integer.valueOf(list.get(0).toString()));
            task.setName(list.get(1).toString());
            task.setDescription(list.get(2).toString());
            task.setTableId(Integer.valueOf(list.get(3).toString()));
            taskList.add(task);
        }
        return taskList;
    }
    
    public ArrayList<Task> fillTasksByProjectWithoutPager(int selectedProjectId) {
        String sql = "select task_id, name, description, table_id "
                + "from task "
                + "where project_id = " + selectedProjectId + " "
                + "and sprint_id is null "
                + "order by name desc";

        ArrayList<Task> taskList = new ArrayList<>();

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"task_id", "name", "description", "table_id"});
        for (ArrayList list : listOfLists) {
            Task task = new Task();
            task.setTaskId(Integer.valueOf(list.get(0).toString()));
            task.setName(list.get(1).toString());
            task.setDescription(list.get(2).toString());
            task.setTableId(Integer.valueOf(list.get(3).toString()));
            taskList.add(task);
        }
        return taskList;
    }

    public ArrayList<Task> fillTasksByProject(int selectedProjectId) {
        String sql = "select task_id, name, description, table_id "
                + "from task "
                + "where project_id = " + selectedProjectId + " "
                + "and sprint_id is null "
                + "order by name desc";

        sql += getSqlForPager(sql, new String[]{"task_id"});

        ArrayList<Task> taskList = new ArrayList<>();

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"task_id", "name", "description", "table_id"});
        for (ArrayList list : listOfLists) {
            Task task = new Task();
            task.setTaskId(Integer.valueOf(list.get(0).toString()));
            task.setName(list.get(1).toString());
            task.setDescription(list.get(2).toString());
            task.setTableId(Integer.valueOf(list.get(3).toString()));
            taskList.add(task);
        }
        return taskList;
    }

    public void moveThroughBoards(int taskId, int tableId) {
        String sql = "update task set table_id = " + tableId + " where task_id = " + taskId;
        updateData(sql);
    }

    public void updateTasksWithSprint(int sprintId, ArrayList<Task> list) {
        for (Task task : list) {
            String sql = "update task set sprint_id = " + sprintId + " "
                    + "where task_id = " + task.getTaskId();
            updateData(sql);
        }
    }

    public void updateTerm(int taskId, Date term) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "update task set datetime = '" + sdf.format(term) + "' where task_id = " + taskId;
        updateData(sql);
    }

}
