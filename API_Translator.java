/*
Last update: 22 March 2021

Methods that interact directly with the News API.

Contributing authors: Austin Matias
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import org.json.*;


public class API_Translator {

    private final String API_KEY = "061df24bf9374cc9a4d897e1655b7aee";

    private final String BASE_URL = "https://newsapi.org/v2/";

    public StringBuilder connectAndReturnContents (String _urlString){
        try{
            //Make the connection.
            URL url = new URL(_urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            //Examine the response code.
            int status = con.getResponseCode();
            if (status != 200){
                return new StringBuilder("Error: API request failed." +
                        " Status: ").append(status);
            } else {
                //Parse input stream into a text string.
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null){
                    content.append(inputLine);
                }
                //Close the connection.
                in.close();
                con.disconnect();
                return content;
            }
        } catch (Exception e){
            return new StringBuilder("Error: ").append(e);
        }

    }

}
