/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.util.SmsGateway;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;

/**
 *
 * @author Daniel
 */
@Named(value = "paymentController")
@SessionScoped
public class PaymentController implements Serializable {

    private double amount, commission;
    private String currency="", carrier="", token="";
    private int cursor;
    private String telephone;
    private boolean codeCorrect;

    public boolean isCodeCorrect() {
        codeCorrect=Inbound.getCode().equals(Inbound.getReceivedcode());
        //Inbound.setReceivedcode("");
        return codeCorrect;
    }

    public void setCodeCorrect(boolean codeCorrect) {
        this.codeCorrect = codeCorrect;
    }
    @Inject
    Login login;

    public String getTelephone() {
        if (getCursor() == 2) {
            return login.getUserConnected().getTelephone();
        } else {
            return telephone;
        }
    }

    public void setTelephone(String telephone) {
        System.out.println("telephone = " + telephone);
        this.telephone = "+243" + telephone.substring(telephone.length() - 9, telephone.length());
    }

    public double getCommission() {
        return Payment.commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public String getCurrency() {
        if (currency.equals("")) {
              return Payment.currency;
        } else {
            return currency;
        }
      
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCarrier() {
        if (carrier.equals("")) {
              return Payment.carrier;
        } else {
            return carrier;
        }
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getToken() {
        if (token.equals("")) {
              return Payment.token;
        } else {
            return token;
        }
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getAmount() {
       if (amount==0) {
              return Payment.amount;
        } else {
            return amount;
        }
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void sendSms() {
        double total = getCommission() + getAmount();
        SmsGateway.sendOne("+447786202820", getTelephone(), "Vous etes sur le point de retirer " + total + " " + getCurrency() + " sur votre compte Airtel Money pour " + getCarrier() + ". Répondez 'lipa espace votre mot de passe' pour confirmer la transaction");
        setCursor(3);
    }

    /**
     * Creates a new instance of PaymentGateway
     */
    public PaymentController() {
    }

}
