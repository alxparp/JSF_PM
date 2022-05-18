package com.genome.parpalak.controllers;

import com.genome.parpalak.bean.Sprint;
import com.genome.parpalak.bean.Task;
import com.genome.parpalak.genome.models.SprintModel;
import com.genome.parpalak.genome.models.TaskModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@SessionScoped
public final class SprintController {

    private String name;
    private Date startDate;
    private Date endDate;
    private ArrayList<Task> currentTasks;
    private ArrayList<Task> tasksNameList;
    private boolean sprintIsDone;
    SprintModel sprintModel;
    ArrayList<Sprint> currentSprints;
    FacesContext context = FacesContext.getCurrentInstance();
    TaskController taskController = context.getApplication().evaluateExpressionGet(context, "#{taskController}", TaskController.class);
    HistoryController historyController;
    private final Map<String, Integer> searchList = new HashMap<>();
    private Sprint currentSprintView;
    private int sprintNumber = 0;

    public SprintController() {
        currentTasks = new ArrayList<Task>();
        sprintModel = new SprintModel();
        historyController = new HistoryController();
        fillSprints();
        fillSearchList();
        if(currentSprints.size() > 0) {
            fillTasksBySprint();
            ifSprintIsDone();
        }
    }
    
    public String addSprint() {
        taskController.fillReallyTasksAll();
        ifSprintIsDone();
        return "sprint";
    }
    
    public String addBoard() {
        fillSearchList();
        fillTasksBySprint();
        return "boards.xhtml";
    }

    public void fillSprints() {
        currentSprints = sprintModel.getSprintsAll(taskController.getSelectedProjectId());
    }
    
    public void ifSprintIsDone() {
        if(currentSprints.size() != 0)
            sprintIsDone = sprintModel.ifSprintIsDone(currentSprints.get(sprintNumber).getSprintId());
        else
            sprintIsDone = false;
    }
    
    public boolean ifSprintIsCompleted() {
        if(!currentSprints.isEmpty())
            return sprintModel.ifSprintIsCompleted(currentSprints.get(sprintNumber).getSprintId());
        return false;
    }
    
    public void fillTasksBySprint() {
        if(!currentSprints.isEmpty())
            tasksNameList = sprintModel.getTasksBySprint(currentSprints.get(sprintNumber).getSprintId(), taskController.getSelectedProjectId());
    }
    
    public void finishSprint() {
        sprintModel.finishSprint(currentSprints.get(sprintNumber).getSprintId());
        ifSprintIsDone();
    }
    
    private void moveThroughBoards(int taskId, String currentBoard, int tableId, TaskModel taskModel) {
        taskModel.moveThroughBoards(taskId, tableId);
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String taskName = params.get("task_name");
        String nextTableName = taskModel.getBoardNameById(tableId);
        historyController.addNewHistory(" переместил задание \"" + taskName + "\" из таблицы \"" + currentBoard + "\"  в таблицу \"" + nextTableName + "\"");
        fillTasksBySprint();
        ifSprintIsDone();
    }
    
    public void moveRight() {
        TaskModel taskModel = new TaskModel();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int taskId = Integer.valueOf(params.get("task_id"));
        int tableId = Integer.valueOf(params.get("table_id"))+1;
        String boardName = taskModel.getBoardNameById(tableId-1);
        moveThroughBoards(taskId, boardName, tableId, taskModel);
    }
    
    public void moveLeft() {
        TaskModel taskModel = new TaskModel();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int taskId = Integer.valueOf(params.get("task_id"));
        int tableId = Integer.valueOf(params.get("table_id"))-1;
        String boardName = taskModel.getBoardNameById(tableId+1);
        moveThroughBoards(taskId, boardName, tableId, taskModel);
    }

    public String newSprint() {
        ArrayList<Task> currentTasksList = taskController.getCurrentTaskListWithoutPager();
        int projectId = taskController.getSelectedProjectId();
        for (Task task : currentTasksList) {
            if (task.isEdit()) {
                currentTasks.add(task);
            }
        }
        if (currentTasks.isEmpty()) {
            return "";
        }
        sprintModel.newSprint(name, startDate, endDate, currentTasks, projectId);
        historyController.addNewHistory(" добавил новую итерацию \"" + name + "\"");
        fillSprints();
        return "sprints.xhtml";
    }

    public void deleteSprint() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int sprintId = Integer.valueOf(params.get("sprint_id"));
        String sprintName = params.get("sprint_name");
        sprintModel.deleteSprint(sprintId);
        historyController.addNewHistory(" удалил итерацию \"" + sprintName + "\"");
        fillSprints();
    }
    
    // изменить участника задачи
    public void searchTypeChanged(ValueChangeEvent e) {
        int sprintId = Integer.parseInt(e.getNewValue().toString());
        System.out.println(sprintId);
        int number = 0;
        for(Sprint sprint: currentSprints) {
            if(sprint.getSprintId() == sprintId){
                number = currentSprints.indexOf(sprint);
            }
        }
        setSprintNumber(number);
        ifSprintIsDone();
        fillTasksBySprint();
    }
    
    public Map<String, Integer> getSearchList() {
        return searchList;
    }
    
    // заполнение списка участников к проекту претендующих на карточку
    public void fillSearchList() {
        searchList.clear();
        for (int i = currentSprints.size()-1; i >= 0 ; i--) {
            searchList.put(currentSprints.get(i).getName(), currentSprints.get(i).getSprintId());
        }
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

    public ArrayList<Sprint> getCurrentSprints() {
        return currentSprints;
    }

    public void setCurrentSprints(ArrayList<Sprint> currentSprints) {
        this.currentSprints = currentSprints;
    } 

    public ArrayList<Task> getTasksNameList() {
        return tasksNameList;
    }

    public void setTasksNameList(ArrayList<Task> tasksNameList) {
        this.tasksNameList = tasksNameList;
    }

    public boolean isSprintIsDone() {
        return sprintIsDone;
    }

    public void setSprintIsDone(boolean sprintIsDone) {
        this.sprintIsDone = sprintIsDone;
    }

    public Sprint getCurrentSprintView() {
        return currentSprintView;
    }

    public void setCurrentSprintView(Sprint currentSprintView) {
        this.currentSprintView = currentSprintView;
    }

    public int getSprintNumber() {
        return sprintNumber;
    }

    public void setSprintNumber(int sprintNumber) {
        this.sprintNumber = sprintNumber;
    }
    
    

}
