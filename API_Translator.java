/*
Last update: 27 April 2021

Methods that interact directly with the News API.

Contributing authors: Austin Matias, Quan Dinh
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import org.json.*;

public class API_Translator {

    //The API key for the NewsAPI.
    private final String API_KEY = "061df24bf9374cc9a4d897e1655b7aee";

    //The prefix used for all API calls.
    private final String API_PREFIX = "&apiKey=";

    //The base url which is used for all calls.
    private final String BASE_URL = "https://newsapi.org/v2/";

     /**
      * Method that retrieves the top headlines for a certain country.
      * Method by Austin Matias.
      * @param _country The country to get the top headlines for.
      * @return A JSON Object containing all the top headlines for a certain country.
      */
    public JSONObject sortByCountry(String _country) {
        //list of possible countries [ae, ar, at, au, be, bg, br, ca, ch, cn, co, cu, cz, de, eg, fr, gb, gr, hk, hu,
        // id, ie, il, in, it, jp, kr, lt, lv, ma, mx, my, ng, nl, no, nz, ph, pl, pt, ro, rs, ru, sa, se, sg, si, sk,
        // th, tr, tw, ua, us, ve, za]
        String cleanCountry = _country.toLowerCase();
        String callAction = "top-headlines?country=";
        String urlString = BASE_URL + callAction + cleanCountry + API_PREFIX + API_KEY;

        StringBuilder content = connectAndReturnContents(urlString);

        try {
            return new JSONObject(content.toString());

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());

        }
        return new JSONObject();
    }

    /**
     * This class gets the top headlines for a particular category.
     * Method by Austin Matias
     * @param _category the category of headlines to get.
     * @return a JSON object containing the top headlines for that category.
     */
    public JSONObject sortByCategory(String _category) {
        String cleanCategory = _category.toLowerCase();
        String callAction = "top-headlines?country=us&category=";
        String urlString = BASE_URL + callAction + cleanCategory + API_PREFIX + API_KEY;

        StringBuilder content = connectAndReturnContents(urlString);

        try {
            return new JSONObject(content.toString());

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return new JSONObject();
    }

    /**
     * Method to search by a specific keyword or multiple keywords.
     * Method by Quan Dinh.
     * @param _keyword The keyword phrase to search by.
     * @return
     */
    public JSONObject sortByKeyword(String _keyword) {
        String cleanKw = _keyword.replaceAll("\\s", "_").toLowerCase();
        String callAction = "top-headlines?q=";
        String urlString = BASE_URL + callAction + cleanKw + API_PREFIX + API_KEY;

        StringBuilder content = connectAndReturnContents(urlString);

        try {
            return new JSONObject(content.toString());

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return new JSONObject();
    }

    /* Quan Dinh Search by domain
     *   NOTE: the domain is the name part only, example: www.facebook.com the input will be facebook
     * */

    /**
     * Method to search by domain.
     * Method by Quan Dinh.
     * @param _domainName The domian to search for.
     * @return
     */
    public JSONObject sortByDom(String _domainName) {
        String cleanDom = _domainName.toLowerCase();
        String callAction = "top-headlines?domains=";
        String urlString = BASE_URL + callAction + cleanDom + ".com" + API_PREFIX + API_KEY;

        StringBuilder content = connectAndReturnContents(urlString);

        try {
            return new JSONObject(content.toString());

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return new JSONObject();
    }

    /**
     * This method takes in a JSON Object and creates an ArrayList of Article objects.
     * @param _object the JSON Object containing articles.
     * @return The ArrayList of article objects.
     */
    protected ArrayList<Article> getArrayListOfArticlesFromJSONObject(JSONObject _object){
        ArrayList<Article> articleArrayList = new ArrayList<>();
        try {
            for (int i = 0; i < _object.getJSONArray("articles").length(); i++){
                articleArrayList.add(getSpecificArticleFromJSON(_object, i));
            }
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
        return articleArrayList;
    }
  
    /**
     * A method that connects to the API using a given URL, and returns a string containing data from the given URL.
     * @param _urlString The URL to connect to.
     * @return A StringBuilder object containing the results of the URL connection.
     */
    private StringBuilder connectAndReturnContents (String _urlString){
        try{
            //Make the connection.
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
            return new StringBuilder("*****Error: ").append(e);
        }
    }

    /**
     * This class takes a JSON Object with multiple articles, and pulls a specific one out.
     * @param _object The JSON Object containing multiple articles.
     * @param _articleNumber The index in the array of the article to be pulled out.
     * @return An article object containing information about the article.
     */
    private Article getSpecificArticleFromJSON(JSONObject _object, int _articleNumber){
        try {
            return new Article(_object.getJSONArray("articles").getJSONObject(_articleNumber));
        } catch (Exception e){
            System.out.println("**Error: " + e);
        }
        return new Article();
    }

    /**
     * A helper method to get the number of articles in a JSON Object, useful when iterating through the JSON Object
     * in other methods.
     * @param _object The JSON Object to get the length of.
     * @return The number of articles in the JSON Object.
     */
    private int getLengthOfJSONObjectArticleArray(JSONObject _object){
        int length = 0;
        try {
            length = _object.getJSONArray("articles").length();
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
        return length;
    }
}
