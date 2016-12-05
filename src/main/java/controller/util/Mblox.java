package controller.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Mblox {

//    static String account = "kwasimu12";
//    static String token = "01052028f7b54945ae643d54fcea80c1";
//    static String account = "sambo12";
//    static String token = "70e04b5f1406482d8b09d184a60e6d7f";
//    static String account = "nasimu32";
//    static String token = "1885a1ab30af4e3280953515d6045132";
    static String account = "nasimu13";
    static String token = "3c0c05ceb9f6448c9045aaa716b7e648";

    public static String sendSms(ArrayList<String> phoneNumbers, String sender, String message) {
        message = JsfUtil.deAccent(message);
        for (String string : phoneNumbers) {
            phoneNumbers.set(phoneNumbers.indexOf(string), "\"" + string + "\"");
        }
        StringBuilder response = null;
        try {

            //numbers are comma separated with "" eg: "999","9994","998"
            // String phoneNumbers = "\"+243972738686\",\"+243995360034\"";
            URL url = new URL("https://api.mblox.com/xms/v1/" + account + "/batches");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token); //token

            String postData = "{\"from\": \" " + sender + " \",\"to\": " + phoneNumbers.toString() + ",\"body\": \"" + message + "\"}";
            OutputStream os = connection.getOutputStream();
            os.write(postData.getBytes());

            response = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            br.close();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        String resp = response.toString();
        System.out.println("resp = " + resp);
        return resp;
    }

    public static String sendScheduledSms(ArrayList<String> phoneNumbers, String sender, String message, Date date) {
        message = JsfUtil.deAccent(message);
        for (String string : phoneNumbers) {
            phoneNumbers.set(phoneNumbers.indexOf(string), "\"" + string + "\"");
        }
        StringBuilder response = null;
        try {

            //numbers are comma separated with "" eg: "999","9994","998"
            // String phoneNumbers = "\"+243972738686\",\"+243995360034\"";
            String sendingDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm'Z'").format(date);
            URL url = new URL("https://api.mblox.com/xms/v1/" + account + "/batches");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token); //token

            String postData = "{\"from\": \" " + sender + " \",\"to\": " + phoneNumbers.toString() + ",\"body\": \"" + message + "\",\"send_at\": \" " + sendingDate + "\"}";
            OutputStream os = connection.getOutputStream();
            os.write(postData.getBytes());

            response = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            br.close();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        String resp = response.toString();
        System.out.println("resp = " + resp);
        return resp;
    }

}
