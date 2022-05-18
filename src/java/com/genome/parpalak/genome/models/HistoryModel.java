package com.genome.parpalak.genome.models;

import com.genome.parpalak.bean.History;
import java.util.ArrayList;
import java.util.Date;

public class HistoryModel extends Model{
    
    public HistoryModel() {
        
    }
    
    public ArrayList<History> getAllHistories(int projectId) {
        String sql = "select h.history_id, h.description, h.datetime, p.name "
                + "from history h, participant p "
                + "where p.participant_id = h.participant_id "
                + "and h.project_id = " + projectId + " "
                + "order by h.history_id desc";
        
        ArrayList<History> historyList = new ArrayList<>();

        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"history_id", "description", "datetime", "name"});
        for (ArrayList list : listOfLists) {
            History history = new History();
            history.setHistoryId(Integer.valueOf(list.get(0).toString()));
            history.setDescription(list.get(1).toString());
            history.setDatetime((Date)list.get(2));
            history.setParticipant(list.get(3).toString());
            historyList.add(history);
        }

        return historyList;
    }
    
    public void addNewHistory(String description, int projectId, String username) {
         int participantId = new ParticipantModel().getParticipantIdByUsername(username);
        String sql = "insert into history(description, datetime, project_id, participant_id) "
                + "values('" + description + "', NOW(), " + projectId + ", " + participantId + ")";
        updateData(sql);
    }
    
}
