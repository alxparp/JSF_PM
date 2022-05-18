package com.genome.parpalak.controllers;

import com.genome.parpalak.bean.Pager;
import com.genome.parpalak.bean.Task;
import com.genome.parpalak.genome.models.TaskModel;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class StoryController implements Serializable {

    TaskModel taskModel;
    Pager pager;
    Task currentStory;
    int selectedStoryId;
    FacesContext context = FacesContext.getCurrentInstance();
    CheckListController checkListController = context.getApplication().evaluateExpressionGet(context, "#{checkListController}", CheckListController.class);
    CommentController commentController = context.getApplication().evaluateExpressionGet(context, "#{commentController}", CommentController.class);
    HistoryController historyController;
    CalendarController calendarController = context.getApplication().evaluateExpressionGet(context, "#{calendarController}", CalendarController.class);

    public StoryController() {
        historyController = new HistoryController();
        pager = new Pager();
        taskModel = new TaskModel(pager);
    }

    public String fillStory() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedStoryId = Integer.valueOf(params.get("task_id"));

        checkListController.fillStoryWithCheckLists();
        commentController.fillCommentsList();

        currentStory = taskModel.fillStory(selectedStoryId).get(0);

        return "story";
    }

    public void updateTerm() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedStoryId = Integer.valueOf(params.get("task_id"));
        calendarController.fillCalendar();
        Date term = currentStory.getDatetime();
        taskModel.updateTerm(selectedStoryId, term);
        historyController.addNewHistory(" добавил/изменил срок для карточки \"" + currentStory.getName() + "\"");
    }

    public void updateDescription() {
        taskModel.updateDescription(currentStory.getDescription(), selectedStoryId);
        historyController.addNewHistory(" обновил описание карточки \"" + currentStory.getName() + "\"");
    }

    // заполнить карточку участниками
    public void fillStoryFromParticipant() {
        currentStory = taskModel.fillStory(selectedStoryId).get(0);
    }

    public Task getCurrentStory() {
        return currentStory;
    }

    public void setCurrentStory(Task currentStory) {
        this.currentStory = currentStory;
    }

    public int getSelectedStoryId() {
        return selectedStoryId;
    }

    public void setSelectedStoryId(int selectedStoryId) {
        this.selectedStoryId = selectedStoryId;
    }

}
