/*
Last update: 29 April 2021.

Methods that interact directly with the News API.

Contributing authors: Austin Matias, Quan Dinh.
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
    protected JSONObject sortByCountry(String _country) {
        //Clean the input to make sure that it is formatted correctly.
        String cleanCountry = _country.toLowerCase();
        //Parameter for the API call, made as variables so that they can be changed should the API change.
        String callAction = "top-headlines?country=";
        //The string to actual be passed to the API.
        String urlString = BASE_URL + callAction + cleanCountry + API_PREFIX + API_KEY;

        //Connecting to the API and saving the contents for use with the JSON parser.
        StringBuilder content = connectAndReturnContents(urlString);

        //Creating a JSON object from the content provided by the API.
        try {
            return new JSONObject(content.toString());

        } catch (Exception e) {
            System.out.println("Error in API_Translator.sortByCountry : " + e.toString());

        }
        return new JSONObject();
    }

    /**
     * This class gets the top headlines for a particular category.
     * Method by Austin Matias
     * @param _category the category of headlines to get.
     * @return a JSON object containing the top headlines for that category.
     */
    protected JSONObject sortByCategory(String _category) {
        //Clean the input to make sure that it is formatted correctly.
        String cleanCategory = _category.toLowerCase();
        //Parameter for the API call, made as variables so that they can be changed should the API change.
        String callAction = "top-headlines?country=us&category=";
        //The string to actual be passed to the API.
        String urlString = BASE_URL + callAction + cleanCategory + API_PREFIX + API_KEY;

        //Connecting to the API and saving the contents for use with the JSON parser.
        StringBuilder content = connectAndReturnContents(urlString);

        //Creating a JSON object from the content provided by the API.
        try {
            return new JSONObject(content.toString());

        } catch (Exception e) {
            System.out.println("Error in API_Translator.sortByCategory: " + e);
        }

        return new JSONObject();
    }

    /**
     * Method to search by a specific keyword or multiple keywords.
     * Method by Quan Dinh.
     * @param _keyword The keyword phrase to search by.
     * @return A JSON Object containing all headlines returned from the search.
     */
    protected JSONObject sortByKeyword(String _keyword) {
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

    /**
     * Method to search by domain.
     * Method by Quan Dinh.
     * @param _domainName The domain to search for.
     * @return A JSON Object containing all headlines returned from the search.
     */
    protected JSONObject sortByDom(String _domainName) {
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
     * Method by Austin Matias.
     * @param _object the JSON Object containing articles.
     * @return The ArrayList of article objects.
     */
    protected ArrayList<Article> getArrayListOfArticlesFromJSONObject(JSONObject _object){
        //declaring the ArrayList.
        ArrayList<Article> articleArrayList = new ArrayList<>();
        //A loop that goes for the length of the JSON object, adding each article to the ArrayList.
        try {
            for (int i = 0; i < _object.getJSONArray("articles").length(); i++){
                articleArrayList.add(getSpecificArticleFromJSON(_object, i));
            }
        } catch (Exception e){
            System.out.println("Error in API_Translator.getArrayListOfArticlesFromJSONObject: " + e);
        }
        return articleArrayList;
    }
  
    /**
     * A method that connects to the API using a given URL, and returns a string containing data from the given URL.
     * Method by Austin Matias.
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
                return new StringBuilder("Error: API request failed. Status: ").append(status);
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
            return new StringBuilder("Error in API_Translator.connectAndReturnContents: ").append(e);
        }
    }

    /**
     * This class takes a JSON Object with multiple articles, and pulls a specific one out.
     * Method by Austin Matias.
     * @param _object The JSON Object containing multiple articles.
     * @param _articleNumber The index in the array of the article to be pulled out.
     * @return An article object containing information about the article.
     */
    private Article getSpecificArticleFromJSON(JSONObject _object, int _articleNumber){
        try {
            return new Article(_object.getJSONArray("articles").getJSONObject(_articleNumber));
        } catch (Exception e){
            System.out.println("Error in API_Translator.getSpecificArticleFromJSON: " + e);
        }
        //Return statement should never be reached. Only in the case of an error,
        //in which point the error log is printed to the console.
        return new Article();
    }
}
