/*
Last update: 27 April 2021.

The Storage object contains method that manipulate persistent data storage.

Contributing authors: Austin Matias, Dillon Halbert, Quan Dinh.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage extends Article {

    //The ArrayList that the favorite articles are saved in while the program runs.
    protected ArrayList<Article> favoriteArray = new ArrayList<>();

    protected int arrayCounter=0;

    //The file path for the file that will contain the favorite articles while the application is closed.
    private String filePath = "FavoriteArticles.txt";

    //The default constructor
    public Storage(){}

    /**
     * To be used on program load to ensure that the favorite article file exists, and to create it if it does not,
     * then to load the array of favorited articles from system storage.
     */
    protected void initializeStorage() {
        ensureFileExistence();
        loadArray();

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
    }

    //x will be the input the user enters on the gui.
    private void add(){

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
        }
        while (input.hasNextLine()) {
            filePath.format(" ");
        }
        */
    }
}

