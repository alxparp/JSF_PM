package com.genome.parpalak.controllers;

import com.genome.parpalak.bean.Pager;
import com.genome.parpalak.bean.Task;
import com.genome.parpalak.genome.models.TaskModel;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(eager = true)
@SessionScoped
public final class TaskController implements Serializable {

    private String search;
    private String story;
    private ArrayList<Task> currentTaskList;
    private ArrayList<Task> currentTaskListWithoutPager;
    private int selectedProjectId = 1;
    private int selectedStoryId = 0;
    private boolean participantEdit;
    private String taskParticipant;
    private final TaskModel taskModel;
    Pager pager;
    HistoryController historyController;
    
    public TaskController() {
        historyController = new HistoryController(selectedProjectId);
        pager = new Pager();
        taskModel = new TaskModel(pager);
        fillTasksAll();
        fillReallyTasksAll();
    }
    
    public void fillReallyTasksAll() {
        currentTaskListWithoutPager = taskModel.fillTasksByProjectWithoutPager(selectedProjectId);
    }

    //<editor-fold defaultstate="collapsed" desc="регистрация пользователя">
    // регистрация пользователя
    public void registerUser(String name, String username, String password, String email) {
        taskModel.registerUser(name, username, transformToSHA256(password), email);
    }

    // кодировать пароль в sha256
    private String transformToSHA256(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(string.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();

            return String.format("%064x", new java.math.BigInteger(1, digest));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return string;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Заполнить список: all, backlog, search, project">
    // заполнить список задачами из БД

    private void fillTasksAll() {
        currentTaskList = taskModel.fillTasksByProject(selectedProjectId);
    }

    // заполнить задачи по Бэклогу
    public String fillTasksAllByBacklog() {
        selectedStoryId = 0;
        currentTaskList = taskModel.fillTasksByProject(selectedProjectId);
        return "/pages/main.xhtml";
    }

    // найденные задачи
    public void fillTasksBySearch() {

        if (search.trim().length() == 0) {
            fillTasksAll();
            return;
        }

        currentTaskList = taskModel.fillTasksBySearch(selectedProjectId, search);

        pager.setSelectedPageNumber(1);

    }

    // задачи, которые находяться в проекте
    public void fillTasksByProject() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedProjectId = Integer.valueOf(params.get("project_id"));

        currentTaskList = taskModel.fillTasksByProject(selectedProjectId);

        pager.setSelectedPageNumber(1);

    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="постраничность">
    // количество задач на странице

    public void tasksOnPageChanged(ValueChangeEvent e) {
        pager.setTasksCountOnPage(Integer.valueOf(e.getNewValue().toString()));
        pager.setSelectedPageNumber(1);
        currentTaskList = taskModel.fillTasksByProject(selectedProjectId);
    }

    // выбор страницы
    public String selectPage() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        pager.setSelectedPageNumber(Integer.valueOf(params.get("page_number")));
        currentTaskList = taskModel.fillTasksByProject(selectedProjectId);

        return "tasks";
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="карточка">
    // заполнить карточку данными

    public String fillStory() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedStoryId = Integer.valueOf(params.get("task_id"));

        taskModel.fillStory(selectedStoryId);

        currentTaskList = taskModel.fillTasksByProject(selectedProjectId);

        pager.setSelectedPageNumber(1);

        return "story";
    }

    // заполнить карточку участниками
//    public void fillStoryFromParticipant() {
//        currentTaskList = taskModel.fillStory(selectedStoryId);
//    }

    // удаление карточки
    public void deleteStory() {
        Map<String, String> param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String taskId = param.get("task_id");
        String taskName = param.get("task_name");
        taskModel.deleteStory(taskId);
        historyController.addNewHistory(" удалил карточку \"" + taskName + "\"");
        currentTaskList = taskModel.fillTasksAll(selectedProjectId);
    }

    // добавить новую карточку
    public void addNewStory() {
        if (story.trim().length() == 0) {
            return;
        }

        taskModel.updateTask(story, selectedProjectId);
        historyController.addNewHistory(" добавил карточку \"" + story + "\"");
        currentTaskList = taskModel.fillTasksByProject(selectedProjectId);
        story="";
        pager.setSelectedPageNumber(1);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters and setters">

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public ArrayList<Task> getCurrentTaskList() {
        return currentTaskList;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public int getSelectedProjectId() {
        return selectedProjectId;
    }

    public void setSelectedProjectId(int selectedProjectId) {
        this.selectedProjectId = selectedProjectId;
    }

    public String getTaskParticipant() {
        return taskParticipant;
    }

    public void setTaskParticipant(String taskParticipant) {
        this.taskParticipant = taskParticipant;
    }

    public int getSelectedStoryId() {
        return selectedStoryId;
    }

    public void setSelectedStoryId(int selectedStoryId) {
        this.selectedStoryId = selectedStoryId;
    }

    public void setParticipantEdit(boolean participantEdit) {
        this.participantEdit = participantEdit;
    }

    public boolean getParticipantEdit() {
        return participantEdit;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

//</editor-fold>

    public ArrayList<Task> getCurrentTaskListWithoutPager() {
        return currentTaskListWithoutPager;
    }

    public void setCurrentTaskListWithoutPager(ArrayList<Task> currentTaskListWithoutPager) {
        this.currentTaskListWithoutPager = currentTaskListWithoutPager;
    }

    
}
