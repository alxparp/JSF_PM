package com.genome.parpalak.controllers;

import com.genome.parpalak.bean.Message;
import com.genome.parpalak.bean.User;
import com.genome.parpalak.genome.models.MessageModel;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class MessageController {

    ArrayList<Message> currentMessagesList;
    MessageModel messageModel;
    String description;
    FacesContext context = FacesContext.getCurrentInstance();
    TaskController tasksListController = context.getApplication().evaluateExpressionGet(context, "#{taskController}", TaskController.class);
    User userController = context.getApplication().evaluateExpressionGet(context, "#{user}", User.class);
    
    public MessageController() {
        messageModel = new MessageModel();
        fillMessagesList();
    }

    public void fillMessagesList() {
        currentMessagesList = messageModel.getMessagesList(tasksListController.getSelectedProjectId());
    }

    public void addMessage() {
        messageModel.addMessage(tasksListController.getSelectedProjectId(), userController.getUsername(), description);
        fillMessagesList();
        description = "";
    }

    public ArrayList<Message> getCurrentMessagesList() {
        return currentMessagesList;
    }

    public void setCurrentMessagesList(ArrayList<Message> currentMessagesList) {
        this.currentMessagesList = currentMessagesList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
