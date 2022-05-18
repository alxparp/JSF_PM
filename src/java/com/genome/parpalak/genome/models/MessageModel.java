package com.genome.parpalak.genome.models;

import com.genome.parpalak.bean.Message;
import java.util.ArrayList;

public class MessageModel extends Model {
    
     public MessageModel() {
    }
    
    public ArrayList<Message> getMessagesList(int projectId) {
        String sql = "select m.message_id, m.description, p.name "
                + "from message m, participant p "
                + "where m.participant_id = p.participant_id "
                + "and m.project_id = " + projectId + " "
                + "order by m.message_id desc";
        
        ArrayList<Message> messageList = new ArrayList<>();
        
        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"message_id", "description", "name"});
        for (ArrayList list : listOfLists) {
            Message message = new Message();
            message.setMessageId(Integer.valueOf(list.get(0).toString()));
            message.setDescription(list.get(1).toString());
            message.setParticipant(list.get(2).toString());
            messageList.add(message);
        }
        
        return messageList;
    }
    
    public void addMessage(int projectId, String username, String description) {
        int participantId = new ParticipantModel().getParticipantIdByUsername(username);
        String sql = "insert into message(description, project_id, participant_id) "
                + "values('" + description + "', " + projectId + ", " + participantId + ")";
        updateData(sql);
    }
    
}
