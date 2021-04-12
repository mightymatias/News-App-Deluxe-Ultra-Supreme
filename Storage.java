import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    private String filePath = "FavoriteArticles.txt";
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

    public void storeNewFavorite(String _newFavoriteFile){
        try {
            FileWriter out = new FileWriter(filePath, true);
            out.append(_newFavoriteFile);
            out.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }
}
