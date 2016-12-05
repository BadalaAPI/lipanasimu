/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.util.SmsGateway;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Daniel
 */
@WebServlet(name = "Inbound", urlPatterns = {"/inbound"})
public class Inbound extends HttpServlet {

    public static String sender, message, receivedcode, code = "1234";

    public static String getReceivedcode() {
        return receivedcode;
    }

    public static String getCode() {
        return code;
    }

    public static void setCode(String code) {
        Inbound.code = code;
    }

    public static void setReceivedcode(String receivedcode) {
        Inbound.receivedcode = receivedcode;
    }

    public static String getSender() {
        return sender;
    }

    public static void setSender(String sender) {
        Inbound.sender = sender;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        Inbound.message = message;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Inbound</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Nasimu Inbound Servlet  at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sender = request.getParameter("from");
        sender = (null == sender) ? "null" : sender;
        setSender(sender);
        String message = request.getParameter("text");
        message = (null == message) ? "null" : message;
        setMessage(message);
        String[] split = getMessage().split(" ");
        setReceivedcode(split[1]);
        if (getCode().equals(getReceivedcode())) {
            double total = Payment.getAmount() + Payment.getCommission();
            SmsGateway.sendOne("+425", "+"+getSender(), "Transaction ID: CI161032.1234.A3696. Votre paiement de " + total + " " + Payment.getCurrency() + " pour " + Payment.getCarrier() + " a reussie. Votre nouveau solde est 87.60 " + Payment.getCurrency());
        } else {
            SmsGateway.sendOne("+447786202820","+"+getSender(),"Mot de passe incorrect! veillez réessayer. 3 essais incorrects bloqueront votre compte");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
