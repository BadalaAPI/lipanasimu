/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
@WebServlet(name = "payment", urlPatterns = {"/payment"})
public class Payment extends HttpServlet {

    public static double amount, commission;
    public static String currency, token, carrier;

    public static double getCommission() {
        return commission;
    }

    public static void setCommission(double commission) {
        Payment.commission = commission;
    }

    public static double getAmount() {
        return amount;
    }

    public static void setAmount(double amount) {
        Payment.amount = amount;
    }

    public static String getCurrency() {
        return currency;
    }

    public static void setCurrency(String currency) {
        Payment.currency = currency;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Payment.token = token;
    }

    public static String getCarrier() {
        return carrier;
    }

    public static void setCarrier(String carrier) {
        Payment.carrier = carrier;
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
            out.println("<title>Servlet Payment</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Payment at " + request.getContextPath() + "</h1>");
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
        //getting data from marchand website
        setToken(request.getParameter("token"));
        setAmount(Double.valueOf((request.getParameter("amount").length()>3) ? request.getParameter("amount").substring(0, 4) : request.getParameter("amount")));
        setCurrency(request.getParameter("currency"));
        setCarrier(request.getParameter("carrier"));
        setCommission(Double.valueOf(String.valueOf(getAmount() * 0.02).substring(0, 4)));
        request.getRequestDispatcher("/faces/payment.xhtml").forward(request, response);

        // processRequest(request, response);
        
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
