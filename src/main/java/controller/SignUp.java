/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controler.util.LoginSessionBean;
import controller.util.JsfUtil;
import controller.util.SmsGateway;
import entity.User;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Daniel Rub <daniel.rubambura at danielrubambura@gmail.com>
 */
@Named(value = "signUp")
@ViewScoped
public class SignUp implements Serializable {

    /**
     * Creates a new instance of SignUp
     */
    public SignUp() {
    }
    @Inject
    private UserController userController;
    @Inject
    Login login;

    private String pw1, pw2;
    private User user = new User();

    private boolean isRulesChecked;
    private String activationCode;
    private String code;
    @PersistenceContext(unitName = "lipaPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    private int cursor;

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isIsRulesChecked() {
        return isRulesChecked;
    }

    public void setIsRulesChecked(boolean isRulesChecked) {
        this.isRulesChecked = isRulesChecked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPw1() {
        return pw1;
    }

    public void setPw1(String pw1) {
        this.pw1 = pw1;
    }

    public String getPw2() {
        return pw2;
    }

    public void setPw2(String pw2) {
        this.pw2 = pw2;
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public void createAccount(ActionEvent actionEvent) {
        if (!getPw1().equals(getPw2())) {
            JsfUtil.addErrorMessage("les deux mots de passe ne correspondent pas");
        } else if (em
                .createNamedQuery("User.findByEmail", User.class)
                .setParameter("email", getUser().getEmail())
                .getResultList().size()
                > 0) {
            JsfUtil.addErrorMessage("cette adresse e-mail est déjà utilisée");
        } else if (em
                .createNamedQuery("User.findByTelephone", User.class)
                .setParameter("telephone", getUser().getTelephone())
                .getResultList().size()
                > 0) {
            JsfUtil.addErrorMessage("ce numéro de téléphone est déjà utilisé");
        } else {
            //sendig sms to the user with code 'code' here!

            setCursor(2);
            double random = Math.random();
            code = String.valueOf(random).length() < 10 ? "10220" : String.valueOf(random).substring(2, 7);
            System.out.println("code = " + code);
            SmsGateway.sendOne("NASIMU", getUser().getTelephone(), "Votre code d'activation est " + code);
        }
    }

    public void ActivateCode(ActionEvent actionEvent) {
        if (getActivationCode().trim().equals(getCode())) {

            setCursor(3);
            //saving new user here, toping up account, then create session for new user
            try {

                getUser().setMotDePasse(getPw1());
                userController.getSelected();
                userController.getSelected().setEmail(getUser().getEmail());
                userController.getSelected().setMotDePasse(getUser().getMotDePasse());
                userController.getSelected().setNom(getUser().getNom());
                userController.getSelected().setPrenom(getUser().getPrenom());
                userController.getSelected().setTelephone("+243"+getUser().getTelephone().substring(getUser().getTelephone().length()-9, getUser().getTelephone().length()));
                userController.saveNewNoFeedBack(actionEvent);
                JsfUtil.addSuccessMessage("votre compte a été créé");
                User sessionUser = em.createNamedQuery("User.findByTelephone", User.class).setParameter("telephone", "+243"+getUser().getTelephone().substring(getUser().getTelephone().length()-9, getUser().getTelephone().length())).getSingleResult();
                login.setUserConnected(sessionUser);
                HttpSession session = LoginSessionBean.getSession();
                session.setAttribute("telephone", "+243"+getUser().getTelephone().substring(getUser().getTelephone().length()-9, getUser().getTelephone().length()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            JsfUtil.addErrorMessage("le code entré n'est pas valide");
        }
    }

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

}
