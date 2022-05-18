package com.genome.parpalak.validators;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("loginValidator")
public class LoginValidator implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String newValue = value.toString();

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("com.genome.parpalak.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            if (newValue.length() != 0 && Character.isDigit(newValue.charAt(0))) {
                throw new IllegalArgumentException(bundle.getString("first_letter_error"));
            }
        } catch (IllegalArgumentException ex) {
            FacesMessage message = new FacesMessage(ex.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }

}
