package com.genome.parpalak.validators;

import com.genome.parpalak.genome.models.ParticipantModel;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("loginRegistrationValidator")
public class LoginRegistrationValidator implements Validator {

    ParticipantModel participantModel = new ParticipantModel();
    ArrayList<String> users;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String newValue = value.toString();

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("com.genome.parpalak.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            if (newValue.length() != 0 && Character.isDigit(newValue.charAt(0))) {
                throw new IllegalArgumentException(bundle.getString("first_letter_error"));
            }
            if (containsUser(newValue)) {
                throw new IllegalArgumentException(bundle.getString("username_already_exists"));
            }
        } catch (IllegalArgumentException ex) {
            FacesMessage message = new FacesMessage(ex.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }
    
    private boolean containsUser(String user) {
        if(users == null)
            users = participantModel.getAllUsername();
        for(String userName : users) {
            if(userName.equals(user)) {
                return true;
            }
        }
        return false;
    }

}
