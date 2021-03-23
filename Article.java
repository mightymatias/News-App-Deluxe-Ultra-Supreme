/*
Last update: 22 March 2021

The Article object contains all relevant data about a specific article.

Contributing authors: Austin Matias

 */

import org.json.JSONObject;

public class Article {

    private String TITLE = "No title to show.";
    private String AUTHOR = "No author to show.";
    private String DESCRIPTION = "No description to show.";
    private String URL = "No URL to show.";
    private String URL_TO_IMAGE= "No URL image to show";
    private String PUBLISHED_AT = "No publishing date to show.";
    private String CONTENT = "No content to show.";

    public Article(){

    }

    public Article(JSONObject _object){
        try{
            this.TITLE = _object.getString("title");
            this.AUTHOR = _object.getString("author");
            this.DESCRIPTION = _object.getString("description");
            this.URL = _object.getString("url");
            this.URL_TO_IMAGE= _object.getString("urlToImage");
            this.PUBLISHED_AT = _object.getString("publishedAt");
            this.CONTENT = _object.getString("content");

        } catch (Exception e){
            System.out.println("Error: " + e);
        }

    }

    public String getTITLE() {
        return TITLE;
    }

    public String getAUTHOR() {
        return AUTHOR;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public String getURL() {
        return URL;
    }

    public String getURL_TO_IMAGE() {
        return URL_TO_IMAGE;
    }

    public String getPUBLISHED_AT() {
        return PUBLISHED_AT;
    }

    public String getCONTENT() {
        return CONTENT;
    }
}
