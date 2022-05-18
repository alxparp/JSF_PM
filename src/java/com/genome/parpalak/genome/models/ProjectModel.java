package com.genome.parpalak.genome.models;

import com.genome.parpalak.bean.Project;
import java.util.ArrayList;

public class ProjectModel extends Model {
    
    public ArrayList<Project> fillProjectsByUser(String username) {
        String sql = "select proj.name, proj.project_id "
                + "from projects proj, participant part, project_participant pp "
                + "where proj.project_id = pp.project_id "
                + "and pp.participant_id = part.participant_id "
                + "and part.username = '" + username + "'";
        
        ArrayList<Project> projectList = new ArrayList<>();
        
        ArrayList<ArrayList> listOfLists = selectData(sql, new String[] {"name", "project_id"});
        for(ArrayList list : listOfLists) {
                Project project = new Project();
                project.setName(list.get(0).toString());
                project.setProjectId(Integer.valueOf(list.get(1).toString()));
                projectList.add(project);
        }
        
        return projectList;
    }
    
    public ArrayList<Project> fillProjectsAll() {
        String sql = "select name, project_id from projects order by name";
        
        ArrayList<Project> projectList = new ArrayList<>();
        
        ArrayList<ArrayList> listOfLists = selectData(sql, new String[] {"name", "project_id"});
        for(ArrayList list : listOfLists) {
                Project project = new Project();
                project.setName(list.get(0).toString());
                project.setProjectId(Integer.valueOf(list.get(1).toString()));
                projectList.add(project);
        }
        
        return projectList;
    }
    
    public void addNewProject(String project) {
        String sql = "insert into projects(name) values('" + project + "')";
        updateData(sql);
    }
    
    public void deleteProject(int projectId) {
        String sql = "delete from projects where project_id = " + projectId;
        updateData(sql);
    }
    
}
