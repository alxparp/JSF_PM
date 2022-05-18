package com.genome.parpalak.controllers;

import com.genome.parpalak.bean.Project;
import com.genome.parpalak.bean.User;
import com.genome.parpalak.genome.models.ProjectModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(eager = true)
@SessionScoped
public class ProjectController implements Serializable {

    private ArrayList<Project> currentProjectList;
    private ProjectModel projectModel;
    private String project;
    private boolean editable;
    FacesContext context = FacesContext.getCurrentInstance();
    TaskController tasksListController = context.getApplication().evaluateExpressionGet(context, "#{taskController}", TaskController.class);
    User userController = context.getApplication().evaluateExpressionGet(context, "#{user}", User.class);
    boolean role = true;

    public ProjectController() {
        projectModel = new ProjectModel();
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        role = req.isUserInRole("ADMIN");
        System.out.println(role);
        currentProjectList = fillProjectsAllWithRole(role);
    }

    public ArrayList<Project> fillProjectsAllWithRole(boolean role) {
        if (role) {
            this.role = true;
            return projectModel.fillProjectsAll();
        } else {
            this.role = false;
            return projectModel.fillProjectsByUser(userController.getUsername());
        }
    }

    public void fillProjectsAll() {
        currentProjectList = fillProjectsAllWithRole(role);
    }

    public ArrayList<Project> getProjectList() {
        fillProjectsAllWithRole(role);
        return currentProjectList;
    }

    public void addNewProject() {
        projectModel.addNewProject(project);
        project = "";
        fillProjectsAll();
    }

    public void deleteProject() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int projectId = Integer.valueOf(params.get("project_id"));
        projectModel.deleteProject(projectId);
        fillProjectsAll();
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String fromProjectToMain() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int projectId = Integer.valueOf(params.get("project_id"));
        tasksListController.setSelectedProjectId(projectId);
        tasksListController.fillTasksByProject();
        tasksListController.fillReallyTasksAll();
        setEditable(true);
        return "main";
    }

    public ArrayList<Project> getCurrentProjectList() {
        return currentProjectList;
    }

    public void setCurrentProjectList(ArrayList<Project> currentProjectList) {
        this.currentProjectList = currentProjectList;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

}
