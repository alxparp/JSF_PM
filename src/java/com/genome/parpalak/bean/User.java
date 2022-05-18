package com.genome.parpalak.bean;

import com.genome.parpalak.controllers.TaskController;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@SessionScoped
public class User implements Serializable {

    private String password;
    private String username;
    private String email;
    private String name;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    public String registration() {       
        
        new TaskController().registerUser(name, username, password, email);
        
        return "index";
    }

    public String login() {

        try {
            ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).login(username, password);
            return "projects";
        } catch (ServletException ex) {
            ResourceBundle bundle = ResourceBundle.getBundle("com.genome.parpalak.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(bundle.getString("login_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("registr_form", message);
        }
        return "index";
    }

    public String logout() {
        String result = "/index.xhtml?faces-redirect=true";

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
        } catch (ServletException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        return result;
    }

}
