
import java.io.*;
import java.util.ArrayList;
/*
User data storage that takes in a string of either a URL or a title of a article
 Methods:// add,delete,most recent,print list.
 Contributed:Dillon Halbert 4/9/21.
 */
public class Array_storage_for_user extends Article {
        final int SIZE_OF_ARRAY = 100;
        String[] userArray = new String[SIZE_OF_ARRAY];
        int _numberOfArticles = 0;
        //just waiting on the get from article class
        String _userArticle;

        private void add(String[] userArray, String _userArticle, int _numberOfArticles) {
                userArray[_numberOfArticles] = _userArticle;
                System.out.println("Your Article has been added" + _numberOfArticles + ":" + userArray);
        }

        public void remove(int number_Of_Article_In_List_User_Wants_Deleted) {
                if (number_Of_Article_In_List_User_Wants_Deleted > _numberOfArticles) {
                        System.out.println("You have entered a number that does not equal on of your options!");
                } else if (number_Of_Article_In_List_User_Wants_Deleted < 0) {
                        System.out.println("Please enter a positive value.");
                } else {
                        //first element and all the way up to the last element
                        if (number_Of_Article_In_List_User_Wants_Deleted == 0) {
                                for (int i = number_Of_Article_In_List_User_Wants_Deleted; i < userArray.length - 1; i++) {
                                        userArray[i] = userArray[i + 1];
                                        System.out.println("Article" + _numberOfArticles + "removed");
                                        //System.out.println(prints list);
                                        _numberOfArticles--;

                                }
                        } else if (number_Of_Article_In_List_User_Wants_Deleted == _numberOfArticles){
                                userArray[_numberOfArticles] = null;
                                System.out.println("Article" + _numberOfArticles + "removed");
                                //System.out.println(prints list);
                                _numberOfArticles--;
                        }
                }
        }


}






