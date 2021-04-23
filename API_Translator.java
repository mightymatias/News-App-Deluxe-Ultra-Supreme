/*
Last update: 16 April 2021

Methods that interact directly with the News API.

Contributing authors: Austin Matias
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import org.json.*;

public class API_Translator {

    private final String API_KEY = "061df24bf9374cc9a4d897e1655b7aee";

    private final String API_PREFIX = "&apiKey=";

    private final String BASE_URL = "https://newsapi.org/v2/";

    /** Start of API connection
     * A method that connects to the API using a given URL, and returns a string containing data from the given URL.
     * @param _urlString The URL to connect to.
     * @return A StringBuilder object containing the results of the URL connection.
     */
    public StringBuilder connectAndReturnContents(String _urlString) {
        try {
            //Make the connection.
            URL url = new URL(_urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            //Examine the response code.
            int status = con.getResponseCode();
            if (status != 200) {
                System.out.println("connection request failed");
                return new StringBuilder("Error: API request failed." +
                        " Status: ").append(status);
            } else {
                //Parse input stream into a text string.
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                //Close the connection.
                in.close();
                con.disconnect();
                return content;
            }
        } catch (Exception e) {
            return new StringBuilder("Error: ").append(e);
        }

    }
    /**END of API connection*/

    /**Start of Searching methods
     * Method that retrieves the top headlines for a certain country.
     *
     * @param _country The country to get the top headlines for.
     * @return A JSON Object containing all the top headlines for a certain country.
     */

    /*  Matias Search by country
     *   Supported 54 countries
     * */
    public JSONObject sortByCountry(String _country) {
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

    /*  Matias Search by category
     *   Supported possible categories are business, entertainment, general, health, science, sports, technology.
     * */
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

    /*  Quan Dinh Search by keyword
     *   Supported single keyword and multiple keywords
     * */
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

    /**END OF Searching methods*/

    /**Start of JSON extraction*
     * These modthod take a JSON Object with multiple articles, and pulls a specific one out.
     * @param _object The JSON Object containing multiple articles.
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

    public ArrayList<Article> getArrayListOfArticlesFromJSONObject(JSONObject _object){
        ArrayList<Article> articleArrayList = new ArrayList<>();
        try {
            for (int i = 1; i <= _object.getJSONArray("articles").length(); i++){
                articleArrayList.add(getSpecificArticleFromJSON(_object, i));
            }
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
        return articleArrayList;
    }

    public int getLengthOfJSONObjectArticleArray(JSONObject _object){
        int length = 0;
        try {
            length = _object.getJSONArray("articles").length();
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
        return length;
    }
    /**END of Json extractions*/
}
