package com.genome.parpalak.validators;

import com.genome.parpalak.bean.Participant;
import com.genome.parpalak.controllers.ParticipantController;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("participantValidator")
public class ParticipantValidator implements Validator {

    FacesContext context = FacesContext.getCurrentInstance();
    ParticipantController currentParticipantController = context.getApplication().evaluateExpressionGet(context, "#{participantController}", ParticipantController.class);
    ParticipantController participantController = new ParticipantController();
    ResourceBundle bundle = ResourceBundle.getBundle("com.genome.parpalak.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String email = value.toString();
        if (!containsParticipantWithEmail(email)) {
            FacesMessage message = new FacesMessage(bundle.getString("participant_not_exist_in_db"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
        if (containsParticipantInCurrentProject(email)) {
            FacesMessage message = new FacesMessage(bundle.getString("participant_exist"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

    private boolean containsParticipantInCurrentProject(String value) {
        for (Participant participant : currentParticipantController.getCurrentParticipantList()) {
            if (value.equals(participant.getEmail())) {
                return true;
            }
        }
        return false;
    }

    private boolean containsParticipantWithEmail(String value) {
        participantController.fillParticipantsForValiator();
        for (Participant participant : participantController.getCurrentParticipantList()) {
            if (value.equals(participant.getEmail())) {
                return true;
            }
        }
        return false;
    }

}
