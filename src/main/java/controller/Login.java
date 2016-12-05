package controller;

import controler.util.LoginSessionBean;

import entity.User;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    private String pwd;
    private String telephone;
    private User userConnected;
    @Inject
    PaymentController paymentController;
    
    @PersistenceContext(unitName = "lipaPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public User getUserConnected() {
        return userConnected;
    }

    public void setUserConnected(User userConnected) {
        this.userConnected = userConnected;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    //validate login
    public String validateUsernamePassword() {
        TypedQuery<User> namedQuery = getEm().createNamedQuery("User.findByTelephone", User.class);
        namedQuery.setParameter("telephone", "+243" + getTelephone().substring(getTelephone().length() - 9, getTelephone().length()));
        User user = new User();
        if (!namedQuery.getResultList().isEmpty()) {
            user = namedQuery.getSingleResult();
            boolean valid = (user.getMotDePasse().equals(getPwd()));
            if (valid) {
                HttpSession session = LoginSessionBean.getSession();
                session.setAttribute("telephone", "+243" + getTelephone().substring(getTelephone().length() - 9, getTelephone().length()));
                setUserConnected(user);
                if (Payment.getAmount() == 0.0) {
                    return "home";
                } else {
                    paymentController.setCursor(2);
                    return "payment";
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Nom d'utilisateur ou mot de passe incorrect",
                                "veillez entrer des données correctes"));
                return "index";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Nom d'utilisateur ou mot de passe incorrect",
                            "veillez entrer des données correctes"));
            return "index";
        }

    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = LoginSessionBean.getSession();
        session.invalidate();
        return "/index";
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
