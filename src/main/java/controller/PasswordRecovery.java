/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.util.JsfUtil;
import controller.util.SmsGateway;
import entity.User;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Daniel Rub <daniel.rubambura at danielrubambura@gmail.com>
 */
@Named(value = "passwordRecovery")
@ViewScoped
public class PasswordRecovery implements Serializable {

    @PersistenceContext(unitName = "lipaPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    private User user = new User();
    private String code;
    private String codeEntered;
    private String pw1, pw2;
    private int cursor;
    private String telephone;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCodeEntered() {
        return codeEntered;
    }

    public void setCodeEntered(String codeEntered) {
        this.codeEntered = codeEntered;
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

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone=telephone;
    }

    /**
     * Creates a new instance of PasswordRecovery
     */
    public PasswordRecovery() {
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

    public void sendReinitCode() {
        TypedQuery<User> namedQuery = em.createNamedQuery("User.findByTelephone", User.class).setParameter("telephone", "+243"+getTelephone().substring(getTelephone().length()-9, getTelephone().length()));
        if (!namedQuery.getResultList().isEmpty()) {
            User singleResult = namedQuery.getSingleResult();
            setUser(namedQuery.getSingleResult());
            double random = Math.random();
            code = String.valueOf(random).length() < 10 ? "10220" : String.valueOf(random).substring(2, 7);
            setCursor(2);
            SmsGateway.sendOne("Nasimu", singleResult.getTelephone(), "Votre code de reunitialisation est " + code);
        } else {
            JsfUtil.addErrorMessage("ce numéro de téléphone n'est associé à aucun compte");
        }
    }

    public void confirmCode() {
        if (getCode().equals(getCodeEntered())) {
            setCursor(3);
        } else {
            JsfUtil.addErrorMessage("le code entré n'est pas valide");
        }
    }

    public void changePassword() {
        if (getPw1().equals(getPw2())) {
            getUser().setMotDePasse(getPw1());
            try {
                utx.begin();
                em.merge(getUser());
                utx.commit();
                JsfUtil.addSuccessMessage("votre mot de passe a été réinitialisé. Vous pouvez maintenant vous connecter");
                setCursor(4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JsfUtil.addErrorMessage("les deux mots de passe ne correspondent pas");
        }
    }
}
