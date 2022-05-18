package com.genome.parpalak.controllers;

import com.genome.parpalak.bean.Comment;
import com.genome.parpalak.genome.models.CommentModel;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class CommentController {

    ArrayList<Comment> currentCommentsList;
    CommentModel commentModel;
    String description;
    HistoryController historyController;

    public CommentController() {
        commentModel = new CommentModel();
        historyController = new HistoryController();
    }

    public void fillCommentsList() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int selectedStoryId = Integer.valueOf(params.get("task_id"));

        currentCommentsList = commentModel.getCommentsList(selectedStoryId);
    }

    public void addComment() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int selectedStoryId = Integer.valueOf(params.get("task_id"));
        String storyName = params.get("task_name");
        String username = params.get("username");
        commentModel.addComment(selectedStoryId, username, description);
        historyController.addNewHistory(" добавил новую итерацию \"" + storyName + "\"");
        fillCommentsList();
        description = "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Comment> getCurrentCommentsList() {
        return currentCommentsList;
    }

    public void setCurrentCommentsList(ArrayList<Comment> currentCommentsList) {
        this.currentCommentsList = currentCommentsList;
    }

}
