/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.Part;

/**
 *
 * @author Daniel Rub <daniel.rubambura at danielrubambura@gmail.com>
 */
@Named(value = "validator")
@RequestScoped
public class Validator implements Serializable {

    public void validateTelephone(FacesContext ctx,
            UIComponent comp,
            Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        try {

            String phone = String.valueOf(value);
            if (phone.length() == 9
                    & !phone.startsWith("84")
                    & !phone.startsWith("99")
                    & !phone.startsWith("85")
                    & !phone.startsWith("97")
                    & !phone.startsWith("81")
                    & !phone.startsWith("82")
                    & !phone.startsWith("89")) {

                msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veillez entrer un numéro valide. ex: 0995360034", "Veillez entrer un numéro valide. ex: 0995360034"));
            } else if (phone.length() == 10
                    & !phone.startsWith("084")
                    & !phone.startsWith("099")
                    & !phone.startsWith("085")
                    & !phone.startsWith("097")
                    & !phone.startsWith("081")
                    & !phone.startsWith("082")
                    & !phone.startsWith("089")) {
                msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veillez entrer un numéro valide. ex: 0995360034", "Veillez entrer un numéro valide. ex: 0995360034"));
            } else if (phone.length() == 12
                    & !phone.startsWith("24384")
                    & !phone.startsWith("24399")
                    & !phone.startsWith("24385")
                    & !phone.startsWith("24397")
                    & !phone.startsWith("24381")
                    & !phone.startsWith("24382")
                    & !phone.startsWith("24389")) {

                msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veillez entrer un numéro valide. ex: 0995360034", "Veillez entrer un numéro valide. ex: 0995360034"));
            } else if (phone.length() == 13
                    & !phone.startsWith("+24384")
                    & !phone.startsWith("+24399")
                    & !phone.startsWith("+24385")
                    & !phone.startsWith("+24397")
                    & !phone.startsWith("+24381")
                    & !phone.startsWith("+24382")
                    & !phone.startsWith("+24389")) {

                msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veillez entrer un numéro valide. ex: 0995360034", "Veillez entrer un numéro valide. ex: 0995360034"));
            } else if (phone.length() == 14
                    & !phone.startsWith("0024384")
                    & !phone.startsWith("0024399")
                    & !phone.startsWith("0024385")
                    & !phone.startsWith("0024397")
                    & !phone.startsWith("0024381")
                    & !phone.startsWith("0024382")
                    & !phone.startsWith("0024389")) {

                msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veillez entrer un numéro valide. ex: 0995360034", "Veillez entrer un numéro valide. ex: 0995360034"));
            } else if (phone.length() != 9
                    & phone.length() != 10
                    & phone.length() != 12
                    & phone.length() != 13
                    & phone.length() != 14) {
                msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veillez entrer un numéro valide. ex: 0995360034", "Veillez entrer un numéro valide. ex: 0995360034"));
            }

        } catch (Exception e) {
            msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veillez entrer un numéro valide. ex: 0995360034", "Veillez entrer un numéro valide. ex: 0995360034"));
        }

        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public void validateString(FacesContext ctx,
            UIComponent comp,
            Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        try {
            String phone = String.valueOf(value);
            if (phone.trim().equals("")) {
                msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vous ne pouvez pas mettre que des epaces! ", "Vous ne pouvez pas mettre que des epaces!"));
            }

        } catch (Exception e) {
            msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vous ne pouvez pas mettre que des epaces! ", "Vous ne pouvez pas mettre que des epaces!"));
        }

        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public void validateSendingHour(FacesContext ctx,
            UIComponent comp,
            Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        try {
            String hour = String.valueOf(value);
            int h = Integer.parseInt(hour.substring(0, 2));
            int m = Integer.parseInt(hour.substring(3, 5));

            if (h > 23 || m > 59) {
                msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veillez entrer une heure valide ", "Veillez entrer une heure valide"));
            } else {
            }
        } catch (Exception e) {
            msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veillez entrer une heure valide ", "Veillez entrer une heure valide"));
        }

        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public void validateRulesCheckBox(FacesContext ctx,
            UIComponent comp,
            Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        try {
            boolean isChecked = (boolean) (value);
            if (!isChecked) {
                msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veillez lire et accepter les conditions d'utilisation", "Veillez lire et accepter les conditions d'utilisation"));
            }
        } catch (Exception e) {
            msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veillez lire et accepter les conditions d'utilisation", "Veillez lire et accepter les conditions d'utilisation"));
        }

        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public void validateFile(FacesContext ctx,
            UIComponent comp,
            Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        Part file = (Part) value;

        if (!file.getSubmittedFileName().endsWith("xls") & !file.getSubmittedFileName().endsWith("xlsx")) {
            msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "le fichier chargé n'est pas un fichier excel", "le fichier chargé n'est pas un fichier excel"));
        }
        if (file.getSize() > 10000000) {
            msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "la taille du fichier autorisée est 10 Mo", " la taille du fichier autorisée est 5 Mo"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public void validateEmail(FacesContext ctx,
            UIComponent comp,
            Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        try {
            String email = String.valueOf(value);
            if (email.trim().equals("")) {
                msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vous ne pouvez pas mettre que des epaces! ", "Vous ne pouvez pas mettre que des epaces!"));
            }
            if (email.contains("@") && email.contains(".")) {

            } else {
                msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "veillez entrer une adresse email correcte ", "veillez entrer une adresse email correcte "));
            }

        } catch (Exception e) {
            msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vous ne pouvez pas mettre que des epaces! ", "Vous ne pouvez pas mettre que des epaces!"));
        }

        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

}
