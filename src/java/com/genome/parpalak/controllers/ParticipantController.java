package com.genome.parpalak.controllers;

import com.genome.parpalak.bean.Pager;
import com.genome.parpalak.bean.Participant;
import com.genome.parpalak.genome.models.ParticipantModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;


@ManagedBean(eager = true)
@SessionScoped
public final class ParticipantController implements Serializable {
    private final Map<String, Integer> searchList = new HashMap<>();
    private ArrayList<Participant> currentParticipantList;
    
    FacesContext context = FacesContext.getCurrentInstance();
    TaskController tasksListController = context.getApplication().evaluateExpressionGet(context, "#{taskController}", TaskController.class);
    StoryController storyController = context.getApplication().evaluateExpressionGet(context, "#{storyController}", StoryController.class);

    public String participant;
    ParticipantModel participantModel;
    Pager pager;
    HistoryController historyController;

    // при входе в класс заполнить список участников к проекту
    // и список участников претендующих на карточку
    public ParticipantController() {
        pager = new Pager();
        historyController = new HistoryController();
        participantModel = new ParticipantModel(pager);
        fillParticipantsAll();
        fillSearchList();
    }
    
    //<editor-fold defaultstate="collapsed" desc="проект">
    // изменить список участников к определенному проекту при нажатии на ссылку
    public String clickParticipants() {
        fillParticipantsAll();
        fillSearchList();
        return "/pages/participants.xhtml";
    }
    
    // заполнить список участников к проекту
    public void fillParticipantsAll() {
        currentParticipantList = participantModel.fillParticipantsByProject(tasksListController.getSelectedProjectId());
    }
    
    // удалить участника от проекта
    public void deleteParticipant() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();        
        participantModel.deleteParticipant(params.get("participant_id"), tasksListController.getSelectedProjectId());
        String participantName = params.get("participant_name");
        historyController.addNewHistory(" удалил из проекта участника \"" + participantName + "\"");
        currentParticipantList = participantModel.fillParticipantsByProject(tasksListController.getSelectedProjectId());
        tasksListController.setParticipantEdit(false);
    }
    
    // добавить нового участника к проекту
    public void addNewParticipant() {

        for (Participant participantName : currentParticipantList) {
            if (participant.equals(participantName.getEmail())) {
                return;
            }
        }
        
        participantModel.addNewParticipant(tasksListController.getSelectedProjectId(), participant);
        historyController.addNewHistory(" добавил в проект участника \"" + participant + "\"");
        currentParticipantList = participantModel.fillParticipantsByProject(tasksListController.getSelectedProjectId());
        
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="задача">
    // получение списка участников к проекту претендующих на карточку
    public Map<String, Integer> getSearchList() {
        return searchList;
    }
    
    // заполнение списка участников к проекту претендующих на карточку
    public void fillSearchList() {
        searchList.clear();
        for (Participant part : currentParticipantList) {
            searchList.put(part.getName(), part.getParticipantId());
        }
    }
    
    // изменить участника задачи
    public void searchTypeChanged(ValueChangeEvent e) {
        int participantId = Integer.parseInt(e.getNewValue().toString());
        
        
        participantModel.setParticipantToStory(participantId, storyController.getSelectedStoryId());
        historyController.addNewHistory(" изменил/добавил участника к карточке ");
        tasksListController.setParticipantEdit(false);
        storyController.fillStoryFromParticipant();
    }
    
    public void deleteParticipantFromStory() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();        
        participantModel.deleteParticipantFromStory(params.get("task_id"));
        String storyName = params.get("task_name");
        historyController.addNewHistory(" удалил участника из карточки \"" + storyName + "\"");
        tasksListController.setParticipantEdit(true);
        storyController.fillStoryFromParticipant();
    }
//</editor-fold>    
    
    // Валидация
    // заполнить список всех существуюющих участников 
    // для последующего добавления к проекту
    public void fillParticipantsForValiator() {
        currentParticipantList = participantModel.fillParticipantsAll();
    }

    //<editor-fold defaultstate="collapsed" desc="getters and setters">
    public ArrayList<Participant> getCurrentParticipantList() {
        return currentParticipantList;
    }

    public void setCurrentParticipantList(ArrayList<Participant> currentParticipantList) {
        this.currentParticipantList = currentParticipantList;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }
//</editor-fold>

}
