/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Daniel Rub <daniel.rubambura at danielrubambura@gmail.com>
 */
public class SmsGateway {

    private static final boolean IS_PRODUCTION_MODE_ENABLED = false;

    public static void sendOne(String senderName, String to, String message) {
        if (IS_PRODUCTION_MODE_ENABLED) {
            ArrayList<String> phoneList = new ArrayList<>();
            phoneList.add(to);
            Mblox.sendSms(phoneList, senderName, message);
        } else {
            message = JsfUtil.deAccent(message);
            System.out.println("senderName = " + senderName);
            System.out.println("to = " + to);
            System.out.println("message = " + message);
        }

    }

    public static void sendOneWithDelay(String senderName, String to, String message, Date sendingDate) throws ParseException {
        //changing time zone to UTC
        SimpleDateFormat isoFormat = new SimpleDateFormat();
        isoFormat.setTimeZone(TimeZone.getTimeZone("GMT+4"));
        String dateFormat = new SimpleDateFormat().format(sendingDate);
        Date parsedDate = isoFormat.parse(dateFormat);
        if (IS_PRODUCTION_MODE_ENABLED) {
            ArrayList<String> phoneList = new ArrayList<>();
            phoneList.add(to);
            Mblox.sendScheduledSms(phoneList, senderName, message, parsedDate);
        } else {
            message = JsfUtil.deAccent(message);
            System.out.println("senderName = " + senderName);
            System.out.println("to = " + to);
            System.out.println("message = " + message);
            System.out.println("sendingDate = " + parsedDate);
        }

    }

    public static void sendMultiple(String senderName, ArrayList<String> phoneNumbers, String message) {
        if (IS_PRODUCTION_MODE_ENABLED) {
            Mblox.sendSms(phoneNumbers, senderName, message);
        } else {
            message = JsfUtil.deAccent(message);
            System.out.println("senderName = " + senderName);
            System.out.println("phoneList = " + phoneNumbers);
            System.out.println("message = " + message);
        }

    }

    public static void sendMultipleWithDelay(String senderName, ArrayList<String> phoneNumbers, String message, Date sendingDate) throws ParseException {
//changing time zone to UTC
        SimpleDateFormat isoFormat = new SimpleDateFormat();
        isoFormat.setTimeZone(TimeZone.getTimeZone("GMT+4"));
        String dateFormat = new SimpleDateFormat().format(sendingDate);
        Date parsedDate = isoFormat.parse(dateFormat);
        if (IS_PRODUCTION_MODE_ENABLED) {
            Mblox.sendScheduledSms(phoneNumbers, senderName, message, parsedDate);
        } else {
            message = JsfUtil.deAccent(message);
            System.out.println("senderName = " + senderName);
            System.out.println("phoneList = " + phoneNumbers);
            System.out.println("message = " + message);
            System.out.println("sendingDate = " + parsedDate);

        }

    }

    public static void main(String[] args) throws ParseException {
//        Date date = new Date(); 998828270
//        date.setMinutes(date.getMinutes()+5);
//        sendOneWithDelay("Ngiayi", "995360034", "New user connected", date);
        String msg = "Salut";
        sendOne("+243990903481", "847665632", msg);

    }
}
