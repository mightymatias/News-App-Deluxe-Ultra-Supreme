/*
Last update: 27 April 2021.

The Storage object contains method that manipulate persistent data storage.

Contributing authors: Austin Matias, Dillon Halbert, Quan Dinh.
 */

import java.io.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Storage extends Article {

    //The ArrayList that the favorite articles are saved in while the program runs.
    protected ArrayList<Article> favoriteArray = new ArrayList<>();

    protected int arrayCounter=0;
  
      /**         HOW DOES THIS CLASS WORK? (BASIC)
     *          First: Having an ArrayList of favorite Article and Check for existent of txt file (if not exist then create) (if exist then load data into an arraylist)
     *          Second: (optional) to add an article to favorite                    use method  addArticle (Article _article)
     *          Third:  (optional) to delete an article from the List               use method  deleteArticle (String _title)
     *          Fourth: (optional) to select an article from the List               use method  selectArticle (String _title)
     */

    //The file path for the file that will contain the favorite articles while the application is closed.
    private String filePath = "FavoriteArticles.txt";

    //The default constructor
    public Storage(){}

    /**Initialization Phase*/
    /**
     * Create file and load file data into array
     */
    protected void initializeStorage() throws IOException {
        ensureFileExistence();
        loadArray();
        clearStorage();
    }

    protected void saveArrayToFile(){
        clear();
        for(int i=0;i<favoriteArray.toArray().length;i++){
            storeTxt(favoriteArray.get(i).toString());
        }

    }

    protected void newFavorite(Article article){
        String title = article.getTitle();
        String author = article.getAuthor();
        String description = article.getDescription();
        String url = article.getUrl();
        String urlToImage = article.getUrlToImage();
        String publishedAt = article.getPublishedAt();
        favoriteArray.add(article);
    }

    private boolean ensureFileExistence() {
        //create the file object
        File favoriteArticles = new File(filePath);
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
     * Load file data to array
     */
    private void loadArray() {
        try {
            Scanner input = new Scanner(new File(filePath));
            //setting variables to be assigned to object
            while (input.hasNextLine()) {
                String title = input.nextLine();
                String author = input.nextLine();
                String description = input.nextLine();
                String url = input.nextLine();
                String urlToImage = input.nextLine();
                String publishedAt = input.nextLine();
                input.nextLine();
                favoriteArray.add(new Article(title, author, description, url, urlToImage, publishedAt));
            }
        } catch (Exception e) {
            System.out.println("Error with Storage.loadArray: " + e);
            e.printStackTrace();
        }
        System.out.println(favoriteArray);
    }

    private void storeTxt(String _newFavoriteFile) {
        try {
            FileWriter out = new FileWriter(filePath, true);
            out.append(_newFavoriteFile);
            out.close();
        } catch (Exception e) {
            System.out.println("Error with Storage.storeTxt: " + e);
            e.printStackTrace();
        }
        //System.out.println(favoriteArray);
    }

    /**Modification Phase*/
    /**Clear storage*/
    public void clearStorage() throws IOException {
        FileWriter fw = new FileWriter("FavoriteArticles.txt", false);
        fw.write("");
        fw.close();
    }

    /** Delete from DB
     /** @paramm _title might be getting from Article.getTitle
     * Using hashmap to search for that article index in the array
     * After that remove it from running Array before update storage*/
    public void deleteArticle (String _title) {
        /**String gonna be the title as key, Integer gonna be the index of that title in array.*/
        /** def loadfactor 0.7f*/
        Map<String, Integer> articleIndex = new HashMap<String, Integer>();

        for (int i = 0; i < favoriteArray.size(); i++) {                                        /** looping any indexing title of article in favoriteArray*/
            String currentIndexTitle = favoriteArray.get(i).getTitle();
            int indexValue = i;
            articleIndex.put(currentIndexTitle, indexValue);                                    /**put current pair ("title", index) into the map*/
        }

        if (articleIndex.containsKey(_title)) {                                                 /**checking existence of article*/
            int getIndex = articleIndex.get(_title);                                            /** getting the index from given title*/
            favoriteArray.remove(getIndex);
            System.out.println("Article has been removed from your favorite list.");
        }
        else
        {
            System.out.println("The article you are looking for is not exist!");
        }
    }
  
  private void delete(int x) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter a positive number");
            if (x < 0) {
                System.out.println("Error number is negative: ");

            } else if (x > favoriteArray.toArray().length) {
                System.out.println("Error number is bigger than users list: ");
            } else {
                favoriteArray.remove(x);
            }
        } catch (Exception e) {
            System.out.println("Error with Storage.delete: " + e);
            e.printStackTrace();
        }
        saveArrayToFile();
    }
  
  private void clear() {
        try {
            FileWriter favoriteFile = new FileWriter(filePath, false);
            favoriteFile.write("");
        } catch (Exception e){
            System.out.println("Error with Storage.clear: " + e);
            e.printStackTrace();
        }

        /*
        Commenting this out to try a new way of clearing the file. This old code was running indefinitely

        Scanner input = null;
        try {
            input = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        */
    }
  
  /** Clear the current txt file and reload into it with new list from the arraylist*/
    public void updateStorage() throws IOException {
        clearStorage();
        FileWriter textLoader = new FileWriter("FavoriteArticles.txt", true);
        for (int i  = 0; i < favoriteArray.size(); i++) {                                       /**toString each article in the favoriteArray then add it into the DB*/
            String theArticle = favoriteArray.get(i).toString();
            textLoader.append(theArticle);
        }
        textLoader.close();
    }

    /** Add a new article to theDataArray (line 11) but this time require an Article Obj*/
    public void addArticle(Article _article)
    {
        favoriteArray.add(_article);
    }

    /**Support method*/
    /**Select article from storage
     * Require title name String
     * Return article String*/
    public String selectArticle(String _title) {
        String theStrArticle  = "";
        /**String gonna be the title as key, Integer gonna be the index of that title in array.
         * def loadfactor 0.7f*/
        Map<String, Integer> articleIndex = new HashMap<String, Integer>();

        for (int i = 0; i < favoriteArray.size(); i++) {                                                                //looping any indexing title of article in favoriteArray
            String currentIndexTitle = favoriteArray.get(i).getTitle();
            articleIndex.put(currentIndexTitle, i);                                                                     //put current pair ("title", index) into the map
          }
        if (articleIndex.containsKey(_title)) {                                                                         //checking existence of article
            int getIndex = articleIndex.get(_title);                                                                    //getting the index from given title
            System.out.println("Article found!");
            theStrArticle = favoriteArray.get(getIndex).toString();
            System.out.println(theStrArticle);
        } else
        {
            System.out.println("The article you are looking for is not exist!");
        }
        return theStrArticle;
    }

    /**View saved favorite articles*/
    public void viewArticles() {
        for (int i = 0; i < favoriteArray.size(); i++)
        {
            System.out.println("Article #" + i + ":\n" + favoriteArray.get(i));
        }
    }
}

