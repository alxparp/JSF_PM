package com.genome.parpalak.validators;

import com.genome.parpalak.genome.models.ParticipantModel;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailRegistrationValidator")
public class EmailRegistrationValidator implements Validator {
    ParticipantModel participantModel = new ParticipantModel();
    ArrayList<String> emails;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String newValue = value.toString();
        if(containsEmail(newValue)) {
            FacesMessage message = new FacesMessage("Такая почта уже существует");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
    private boolean containsEmail(String email) {
        if(emails == null)
            emails = participantModel.getAllEmail();
        for(String emailName : emails) {
            if(emailName.equals(email)) {
                return true;
            }
        }
        return false;
    }
    
}
