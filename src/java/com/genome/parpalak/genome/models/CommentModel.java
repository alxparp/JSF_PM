package com.genome.parpalak.genome.models;

import com.genome.parpalak.bean.Comment;
import java.util.ArrayList;


public class CommentModel extends Model {
    
    public CommentModel() {
    }
    
    public ArrayList<Comment> getCommentsList(int taskId) {
        String sql = "select c.comment_id, c.description, p.name "
                + "from comment c, participant p "
                + "where p.participant_id = c.participant_id "
                + "and task_id = " + taskId + " "
                + "order by c.comment_id desc";
        
        ArrayList<Comment> commentList = new ArrayList<>();
        
        ArrayList<ArrayList> listOfLists = selectData(sql, new String[]{"comment_id", "description", "name"});
        for (ArrayList list : listOfLists) {
            Comment comment = new Comment();
            comment.setCommentId(Integer.valueOf(list.get(0).toString()));
            comment.setDescription(list.get(1).toString());
            comment.setParticipant(list.get(2).toString());
            commentList.add(comment);
        }
        
        return commentList;
    }
    
    public void addComment(int taskId, String username, String description) {
        int participantId = new ParticipantModel().getParticipantIdByUsername(username);
        String sql = "insert into comment(description, task_id, participant_id) "
                + "values('" + description + "', " + taskId + ", " + participantId + ")";
        updateData(sql);
    }
    
}
