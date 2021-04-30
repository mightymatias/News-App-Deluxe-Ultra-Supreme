/*
Last update: 30 April 2021.

The Article object contains all relevant data about a specific article.

Contributing authors: Austin Matias.
 */

import org.json.JSONObject;

public class Article {

    //The title of the article.
    private String title = "No title to show.";

    //The author of the article.
    private String author = "No author to show.";

    //The description of the article.
    private String description = "No description to show.";

    //The url of the article.
    private String url = "No URL to show.";

    //The url to the header image of the article.
    private String urlToImage = "No URL image to show";

    //The time the article was published at.
    private String publishedAt = "No publishing date to show.";

    //The favorited status of an article.
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
            //Setting all of the class level variables to their values from the JSON object.
            this.title = _object.getString("title");
            this.author = _object.getString("author");
            this.description = _object.getString("description");
            this.url = _object.getString("url");
            if (_object.getString("urlToImage").equals("null")){
                this.urlToImage = "http://www.bobos.it/new/wp-content/uploads/2017/11/tv-noise-0212-retro-tv-color-bars-loop_4yiztcvfg__F0000.png";
            } else {
                this.urlToImage = _object.getString("urlToImage");
            }
            this.publishedAt = _object.getString("publishedAt");
        } catch (Exception e){
            System.out.println("Error in Article's JSON constructor: " + e);
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
            //Setting all of the class level variables to their values provided from the passed parameters.
            this.title = _title;
            this.author = _author;
            this.description = _description;
            this.url = _url;
            this.urlToImage = _urlToImage;
            this.publishedAt = _publishedAt;
        } catch (Exception e){
            System.out.println("Error in Article's manual constructor: " + e);
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

    //=================  SETTERS  ===============

    /**
     * This method sets the favorite state of the article.
     * @param _state A boolean depicting whether the article's favorited status should be true or false.
     */
    protected void setIsFavorited(Boolean _state){this.isFavorited = _state;}

    //=================  GETTERS  ===============

    /**
     * A method to get the title of the article.
     * @return A string containing the title of the article.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * A method to get the author of the article.
     * @return A string containing the author of the article.
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * A method to get the description of the article.
     * @return A string containing the description of the article.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * A method to get the URL of the article.
     * @return A string containing the URL of the article.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * A method to get the URL to the header image for the article.
     * @return A string containing the URL to the header image for the article.
     */
    public String getUrlToImage() {
        return this.urlToImage;
    }

    /**
     * A method to get the favorited status of the article.
     * @return A boolean containing the favorited status of the article.
     */
    public Boolean getIsFavorited() {return this.isFavorited;}
}
