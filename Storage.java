import java.io.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Storage extends Article {

    /**         HOW DOES THIS CLASS WORK? (BASIC)
     *          First: Having an ArrayList of favorite Article and Check for existent of txt file (if not exist then create) (if exist then load data into an arraylist)
     *          Second: (optional) to add an article to favorite                    use method  addArticle (Article _article)
     *          Third:  (optional) to delete an article from the List               use method  deleteArticle (String _title)
     *          Fourth: (optional) to select an article from the List               use method  selectArticle (String _title)
     */

    private String filePath = "FavoriteArticles.txt";
    protected ArrayList<Article> favoriteArray = new ArrayList<>();

    /**Default Constructor*/
    public Storage() {

    }

    /**Initialization Phase*/
    /**
     * Create file and load file data into array
     */
    protected void initializeStorage() throws IOException {
        ensureFileExistence();
        loadArray();
        clearStorage();
    }

    /**
     * Check file existence if not -> create the file
     */
    private boolean ensureFileExistence() {
        //create the file object
        File favoriteArticles = new File(filePath);
        //check to see if the file exists, if not, create it
        try {
            if (!favoriteArticles.exists()) {
                favoriteArticles.createNewFile();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
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
            System.out.println("Error: " + e);
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
        }
        else
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

