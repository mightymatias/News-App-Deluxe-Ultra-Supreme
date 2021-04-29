/*
Last update: 27 April 2021.

The Article object contains all relevant data about a specific article.

Contributing authors: Austin Matias.
 */

import org.json.JSONObject;

public class Article {

    private String title = "No title to show.";
    private String author = "No author to show.";
    private String description = "No description to show.";
    private String url = "No URL to show.";
    private String urlToImage = "No URL image to show";
    private String publishedAt = "No publishing date to show.";
    private Boolean isFavorited = false;

    /**
     * The default constructor for the article object.
     */
    public Article(){}

    /**
     * A constructor used to create an article object out of a JSON Object.
     * @param _object The JSON Object containing the information for the article object.
     */
    public Article(JSONObject _object){
        try{
            this.title = _object.getString("title");
            this.author = _object.getString("author");
            this.description = _object.getString("description");
            this.url = _object.getString("url");
            this.urlToImage = _object.getString("urlToImage");
            this.publishedAt = _object.getString("publishedAt");
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    /**
     * A constructor used to custom make an article object. Used primarily for testing purposes.
     * @param _title The title of the article.
     * @param _author The author of the article.
     * @param _description A brief description of the article.
     * @param _url The URL to the article. Must be an actual URL.
     * @param _urlToImage The URL for article's header image. Must be an actual URL.
     * @param _publishedAt The time code for the publishing time of the article.
     */
    public Article(String _title, String _author, String _description, String _url, String _urlToImage, String _publishedAt){
        try{
            this.title = _title;
            this.author = _author;
            this.description = _description;
            this.url = _url;
            this.urlToImage = _urlToImage;
            this.publishedAt = _publishedAt;
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    /**
     * This method returns all of the information for the article in a neat format, primed for storage.
     * @return A string containing all information for the article, broken up by new line characters.
     */
    public String toString(){
        return this.title + "\n" + this.author + "\n" + this.description + "\n" + this.getUrl() + "\n" + this.urlToImage +
                "\n" + this.publishedAt + "\n\n";
    }

    //=================  GETTERS ===============

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUrlToImage() {
        return this.urlToImage;
    }

    public String getPublishedAt() {
        return this.publishedAt;
    }

    public Boolean getIsFavorited() {return this.isFavorited;}
}
