/*
Last update: 22 March 2021

Methods that interact directly with the News API.

Contributing authors: Austin Matias
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Locale;

import org.json.*;


public class API_Translator {

    private final String API_KEY = "061df24bf9374cc9a4d897e1655b7aee";

    private final String API_PREFIX = "&apiKey=";

    private final String BASE_URL = "https://newsapi.org/v2/";

    /**
     * A method that connects to the API using a given URL, and returns a string containing data from the given URL.
     * @param _urlString The URL to connect to.
     * @return A StringBuilder object containing the results of the URL connection.
     */
    public StringBuilder connectAndReturnContents (String _urlString){
        try{
            //Make the connection.z
            URL url = new URL(_urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            //Examine the response code.
            int status = con.getResponseCode();
            if (status != 200){
                System.out.println("connection request failed");
                return new StringBuilder("Error: API request failed." +
                        " Status: ").append(status);
            } else {
                //Parse input stream into a text string.
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
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

    /**
     * Method that retrieves the top headlines for a certain country.
     * @param _country The country to get the top headlines for.
     * @return A JSON Object containing all the top headlines for a certain country.
     */
    public JSONObject getAllTopHeadlinesForCountry(String _country){
        String cleanCountry = _country.toLowerCase();
        String callAction = "top-headlines?country=";
        String urlString = BASE_URL + callAction + cleanCountry + API_PREFIX + API_KEY;

        StringBuilder content = connectAndReturnContents(urlString);

        try{
            return new JSONObject(content.toString());

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());

        }
        return new JSONObject();
    }

    public JSONObject getAllTopHeadlinesForCategory(String _category){
        String cleanCategory = _category.toLowerCase();
        String callAction = "top-headlines?country=us&category=";
        String urlString = BASE_URL + callAction + cleanCategory + API_PREFIX + API_KEY;

        StringBuilder content = connectAndReturnContents(urlString);

        try{
            return new JSONObject(content.toString());

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return new JSONObject();
    }

    /**
     * MIGHT NEED TO BE MOVED TO A DIFFERENT CLASS
     * This class takes a JSON Object with multiple articles, and pulls a specific one out.
     * @param _object The JSON Object containing multiple articles.
     * @param _articleNumber The index in the array of the article to be pulled out.
     * @return An article object containing information about the article.
     */
    public Article getSpecificArticleFromJSON(JSONObject _object, int _articleNumber){
        try {
            return new Article(_object.getJSONArray("articles").getJSONObject(_articleNumber));
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
        return new Article();
    }

}
