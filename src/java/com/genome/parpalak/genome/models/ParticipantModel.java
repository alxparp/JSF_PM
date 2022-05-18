package com.genome.parpalak.genome.models;

import com.genome.parpalak.bean.Pager;
import com.genome.parpalak.bean.Participant;
import java.util.ArrayList;

public class ParticipantModel extends Model {

    Pager pager;

    public ParticipantModel(Pager pager) {
        this.pager = pager;
    }

    public ParticipantModel() {

    }
    
    public ArrayList<String> getAllUsername() {
        String sql = "select username from participant";
        ArrayList<String> usersList = new ArrayList<>();

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"username"});
        for (ArrayList list : listOfLists) {
            usersList.add(list.get(0).toString());
        }

        return usersList;
    }
    
    public ArrayList<String> getAllEmail() {
        String sql = "select email from participant";
        ArrayList<String> emailsList = new ArrayList<>();

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"email"});
        for (ArrayList list : listOfLists) {
            emailsList.add(list.get(0).toString());
        }

        return emailsList;
    }

    // добавить участника к задаче
    public void setParticipantToStory(int participantId, int taskId) {
        String sql = "update task set participant_id = " + participantId + " "
                + "where task_id = " + taskId;
        updateData(sql);
    }
    
    // удалить участника из задачи
    public void deleteParticipantFromStory(String taskId) {
        String sql = "update task set participant_id = null "
                + "where task_id = " + taskId;
        updateData(sql);
    }

    // добавить участника к проекту по почте
    public void addNewParticipant(int selectedProjectId, String email) {
        String sql = "insert into project_participant(project_id, participant_id) "
                + "values(" + selectedProjectId + ", (select participant_id "
                                                        + "from participant "
                                                        + "where email = '" + email +"'))";
        updateData(sql);
    }

    // удалить участника из проекта - исправить
    public void deleteParticipant(String participantId, int projectId) {
        String sql = "delete from project_participant "
                + "where participant_id = " + participantId + " "
                + "and project_id = " + projectId;
        updateData(sql);
    }
    
    public ArrayList<Participant> fillParticipantsByProject(int selectedProjectId) {
        String sql = "select part.participant_id, part.name, part.email "
                + "from participant part, project_participant proj_part "
                + "where part.participant_id = proj_part.participant_id "
                + "and proj_part.project_id = " + selectedProjectId;

        ArrayList<Participant> participantList = new ArrayList<>();

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"participant_id", "name", "email"});
        for (ArrayList list : listOfLists) {
            Participant participant = new Participant();
            participant.setParticipantId(Integer.valueOf(list.get(0).toString()));
            participant.setName(list.get(1).toString());
            participant.setEmail(list.get(2).toString());
            participantList.add(participant);
        }

        return participantList;
    }
    
    public ArrayList<Participant> fillParticipantsAll() {
        String sql = "select participant_id, email from participant ";

        ArrayList<Participant> participantList = new ArrayList<>();

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"participant_id", "email"});
        for (ArrayList list : listOfLists) {
            Participant participant = new Participant();
            participant.setParticipantId(Integer.valueOf(list.get(0).toString()));
            participant.setEmail(list.get(1).toString());
            participantList.add(participant);
        }

        return participantList;
    }
    
    public int getParticipantIdByUsername(String username) {
        String sql = "select participant_id "
                + "from participant "
                + "where username = '" + username + "'";

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"participant_id"});
        for (ArrayList list : listOfLists) {
            return Integer.valueOf(list.get(0).toString());
        }
        
        return 0;
    }

}
