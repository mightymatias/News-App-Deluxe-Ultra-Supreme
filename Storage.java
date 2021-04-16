/*
Last Update 16 April 2021

A storage object, with methods to manipulate it.

Contributing Authors: Austin Matias, Dillon Halbert
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    private final String filePath = "FavoriteArticles.txt";
    protected ArrayList<Article> favoriteArray = new ArrayList<>();

    public Storage (){

    }

    /**
     * To be used on program load to ensure that the favorite article file exists, and to create it if it does not,
     * then to load the array of favorited articles from system storage.
     */
    protected void initializeStorage(){
        ensureFileExistence();
        loadArray();

    }

    /**
     * This method ensures that the file at the specified file path exists, and if not, this method creates the file.
     * @return a boolean depicting whether or not the file exists. Should always be true, false would mean an error in
     * file creation.
     */
    private boolean ensureFileExistence(){
        //create the file object
        File favoriteArticles = new File(filePath);
        //check to see if the file exists, if not, create it
        try{
            if (!favoriteArticles.exists()) {
                favoriteArticles.createNewFile();
            }
        } catch (Exception e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
        //return statement should always return true
        return favoriteArticles.exists();
    }

    /**
     * This method takes a text file containing favorited articles and loads the favorited articles into an ArrayList.
     */
    private void loadArray(){
        try{
            Scanner input = new Scanner(new File(filePath));
            //setting variables to be assigned to object
            while (input.hasNextLine()){
                String title = input.nextLine();
                String author = input.nextLine();
                String description = input.nextLine();
                String url = input.nextLine();
                String urlToImage = input.nextLine();
                String publishedAt = input.nextLine();
                input.nextLine();

                favoriteArray.add(new Article (title, author, description, url, urlToImage, publishedAt));
            }

        } catch (Exception e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
        //System.out.println(favoriteArray);
    }

    /**
     * This method takes a string from an article and saves it to a text file.
     * @param _newFavoriteFile The string coming from an article to be saved.
     */
    protected void storeNewFavorite(String _newFavoriteFile){
        try {
            FileWriter out = new FileWriter(filePath, true);
            out.append(_newFavoriteFile);
            out.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }

    /**
     * This method takes the ArrayList containing favorited articles and prints it into the text file containing
     * favorited articles.
     */
    private void saveArray(){
        for(int i = 0; i < favoriteArray.toArray().length; i++){
            storeNewFavorite(favoriteArray.get(i).toString());
        }
    }
}
