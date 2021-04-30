/* Last update: 29 April 2021

 The main method of the project

 Contributing authors:Dillon Halbert, Connor Contursi
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Test_Class extends Storage {

    //Article objects for testing
    Article ex1 = new Article("title 1","author","there is a description","urrrrrrrrrrrrlllllll.com","null","4/30");
    Article ex2 = new Article("title 2","author","there is maybe a description","urrrrrrrrrrrrlllllll.com","null","4/30");
    Article ex3 = new Article("title 3","author","there is no description","urrrrrrrrrrrrlllllll.com","null","4/30");

    ArrayList<Article> example = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Storage.newFavorite test: " + testStorageDotNewFavorite());
    }

    /**
     * Testing if newFavorite method in Storage.java works
     * Test by Austin Matias.
     * @return Returns true if test was a success.
     */
    private static boolean testStorageDotNewFavorite(){
        //Creating test objects
        Article testArticle = new Article("test title", "test author", "test description");
        Storage testStorage = new Storage("testFavorite.txt");

        //running the method that is the subject of the test
        testStorage.newFavorite(testArticle);

        //return the result of the text.
        return testStorage.favoriteArray.toArray().length == 1;
    }

    /**
     * Test case 1
     * @return
     */
    private boolean test1(){
        Scanner in = new Scanner(System.in);

        Storage test1 = new Storage();
        test1.initializeStorage();
        System.out.println("Enter number 1 if you want to clear txt file for testing");
        String key = in.nextLine();
        if (key.equals("1")) {
            test1.clear();
        } else {
            System.out.println("TXT FILE SAVED");
        }

        example.add(ex1);
        test1.storeTxt("text");
        example.add(ex2);
        example.add(ex2);
        example.remove(ex1);
        return false;
    }


}
