/*
Last update: 29 April 2021.

The Storage object contains method that manipulate persistent data storage.

Contributing authors: Austin Matias, Dillon Halbert, Quan Dinh.
 */

import java.io.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Storage {

    //The ArrayList that the favorite articles are saved in while the program runs.
    protected ArrayList<Article> favoriteArray = new ArrayList<>();

    //The file path for the file that will contain the favorite articles while the application is closed.
    private String filePath = "FavoriteArticles.txt";

    /**
     * The default constructor for the storage object.
     */
    protected Storage(){}

    /**
     * A constructor used to specify a directory for testing purposes.
     * @param _filePath the file path of the favorite file.
     */
    protected Storage(String _filePath){
        this.filePath = _filePath;
    }

    /**
     * To be used on program load to ensure that the favorite article file exists, and to create it if it does not,
     * then to load the array of favorited articles from system storage.
     */
    protected void initializeStorage() {
        ensureFileExistence();
        loadArray();
    }

    /**
     * The method that runs on application close to dump the favoriteArray into a text file
     * for long term data storage.
     */
    protected void saveArrayToFile(){
        clear();
        for(int i=0;i<this.favoriteArray.toArray().length;i++){
            storeTxt(this.favoriteArray.get(i).toString());
        }
    }

    /**
     * This method adds an Article object to favoriteArray.
     * @param _article The Article object to add.
     */
    protected void newFavorite(Article _article){
        _article.setIsFavorited(true);
        this.favoriteArray.add(_article);
    }

    /**
     * This method removes an Article object from favoriteArray.
     * @param _article The Article object to remove.
     */
    protected void removeFavorite(Article _article){
        _article.setIsFavorited(false);
        this.favoriteArray.remove(_article);
    }

    /**
     * A method to ensure that the file to save favorites to exists, and create the file
     * if it doesn't exist.
     * @return A boolean that is true if the file exists. Should always return true.
     */
    private boolean ensureFileExistence() {
        //create the file object
        File favoriteArticles = new File(this.filePath);

        //check to see if the file exists, if not, create it
        try {
            if (!favoriteArticles.exists()) {
                favoriteArticles.createNewFile();
            }
        } catch (Exception e) {
            System.out.println("Error with Storage.ensureFileExistence: " + e);
            e.printStackTrace();
        }

        //return statement should always return true
        return favoriteArticles.exists();
    }

    /**
     * This method loads the favoriteArray from the text file on start up.
     */
    private void loadArray() {
        try {
            //Create a scanner object with the text from the favorites file.
            Scanner input = new Scanner(new File(this.filePath));
            //Reading through the file, creating Article objects from the information contained in it.
            while (input.hasNextLine()) {
                String title = input.nextLine();
                String author = input.nextLine();
                String description = input.nextLine();
                String url = input.nextLine();
                String urlToImage = input.nextLine();
                String publishedAt = input.nextLine();
                input.nextLine();
                this.favoriteArray.add(new Article(title, author, description, url, urlToImage, publishedAt));
            }
        } catch (Exception e) {
            System.out.println("Error with Storage.loadArray: " + e);
            e.printStackTrace();
        }
    }

    /**
     * This method completely clears the favorites file.
     */
    protected void clear() {
        //Creates a new file object at the favorite file path, and writes an empty string to it,
        //thus completely clearing the file.
        try {
            FileWriter favoriteFile = new FileWriter(this.filePath, false);
            favoriteFile.write("");
        } catch (Exception e){
            System.out.println("Error with Storage.clear: " + e);
            e.printStackTrace();
        }
    }

    /**
     * This method stores a line of text to the favorites file.
     * @param _textToStore the line of text to store to the file.
     */
    protected void storeTxt(String _textToStore) {
        //Writes the string given from the parameter into the favorites file.
        try {
            FileWriter out = new FileWriter(this.filePath, true);
            out.append(_textToStore);
            out.close();
        } catch (Exception e) {
            System.out.println("Error with Storage.storeTxt: " + e);
            e.printStackTrace();
        }
    }


    //==============Quan's TESTED CODE ====================

    /**Modification Phase*/
    /**
     * The Purpose of this method is to clear the storage beforer reloaded it with modified data
     * @throws IOException in case file not exist
     */
    public void clearStorage() throws IOException {
        FileWriter fw = new FileWriter("FavoriteArticles.txt", false);
        fw.write("");
        fw.close();
    }

    /** Delete from DB
     /** @paramm _title might be getting from Article.getTitle
     * @throws IOException in case file not exist
     * Using hashmap to search for that article index in the array
     * After that remove it from running Array before update storage*/
    public void deleteArticle (String _title) throws IOException {
        /**String gonna be the title as key, Integer gonna be the index of that title in array.*/
        /** def loadfactor 0.7f*/
        Map<String, Integer> articleIndex = new HashMap<String, Integer>();

        for (int i = 0; i < favoriteArray.size(); i++) {                                                                // looping any indexing title of article in favoriteArray
            String currentIndexTitle = favoriteArray.get(i).getTitle();
            int indexValue = i;
            articleIndex.put(currentIndexTitle, indexValue);                                                            // put current pair ("title", index) into the map
        }

        if (articleIndex.containsKey(_title)) {                                                                         // checking existence of article
            int getIndex = articleIndex.get(_title);                                                                    // getting the index from given title
            favoriteArray.remove(getIndex);
            System.out.println("Article has been removed from your favorite list.");
        }
        else
        {
            System.out.println("The article you are looking for is not exist!");
        }
        updateStorage();

    }

    /** This method purpose is to use whenever you finish with modifying database and want to save it
     * @throws IOException in case file not exist
     * */
    public void updateStorage() throws IOException {
        clearStorage();
        FileWriter textLoader = new FileWriter("FavoriteArticles.txt", true);
        for (int i  = 0; i < favoriteArray.size(); i++) {                                                               // toString each article in the favoriteArray then add it into the DB
            String theArticle = favoriteArray.get(i).toString();
            textLoader.append(theArticle);
        }
        textLoader.close();
    }



    /** The purpose of this is to add a new article to theDataArray (line 11) but this time require an Article Obj
     * also prevent adding duplicate article to the array.
     * @param _article is the selected article that user want to add into favorite list
     * */
    public void addArticle(Article _article)
    {
        if (selectArticle(_article.getTitle()) != -1)
            System.out.println("Trying to add article but it is already exist! Skip forward");
        else
            favoriteArray.add(_article);
    }

    /**Support method*/
    /**The purpose of this methods is to search for an article to see if it exist or not
     * if yes return its index in the ArrayList
     * Otherwise, return -1 to check at call.
     * @param  _title is require to search for the article in the Array as a keyword
     * this can be acquired by Article.getTitle() or hand type or String input
     * */
    public int selectArticle(String _title) {
        /**String gonna be the title as key, Integer gonna be the index of that title in array.
         * def loadfactor 0.7f*/
        int getIndex = -1;
        Map<String, Integer> articleIndex = new HashMap<String, Integer>();

        for (int i = 0; i < favoriteArray.size(); i++) {                                                                //looping any indexing title of article in favoriteArray
            String currentIndexTitle = favoriteArray.get(i).getTitle();
            articleIndex.put(currentIndexTitle, i);                                                                     //put current pair ("title", index) into the map
        }
        if (articleIndex.containsKey(_title)) {                                                                         //checking existence of article
            getIndex= articleIndex.get(_title);
            return getIndex;                                                                                            //getting the index from given title
        }
        else
        {
            return getIndex;
        }

    }

    /**View selected article
     * the purpose for this methods is to see the article at @param index of the ArrayList of the favorite Article
     * @param index get index of the article
     * return print the article*/
    public void viewSelectedArticle(int index)
    {
        System.out.println(favoriteArray.get(index).toString());
    }
}


