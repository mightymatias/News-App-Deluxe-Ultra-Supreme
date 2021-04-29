/*
Last update: 29 April 2021

The main method of the project

Contributing authors: Austin Matias,Dillon Halbert
 */

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Storage storage = new Storage();
        storage.initializeStorage();
        selection();
    }

    /**
     * Given a list of articles, this method prints out the articles to the console, formatted to look nice.
     * @param listOfArticles the JSON Object containing the list of articles to print.
     */
    public static void printArticles(JSONObject listOfArticles){
        API_Translator translator = new API_Translator();
        ArrayList<Article> temp = translator.getArrayListOfArticlesFromJSONObject(listOfArticles);
        for (int i = 0; i < temp.toArray().length; i++){
            Article tempArticle = temp.get(i);
            System.out.println(tempArticle);
        }

    }

    /**
     * A simple selection method to have the user pick from a couple of different options.
     */
    public static void selection(){
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome! Please choose what you'd like to do by typing the integer to the left of the option.");
        System.out.println("1. Top 10 Headlines for a particular country.");
        System.out.println("2. Top 10 Headlines for a particular category");

        System.out.print("Enter your selection: ");
        int option = in.nextInt();

        switch (option){
            case 1:
                topCountryHeadlines();
                break;
            case 2:
                topCategoryHeadlines();
                break;
        }

    }

    /**
     * In this method the User enters 2 lower case letters indicating a country then it takes that data and inputs it into the article class
     */
    public static void topCountryHeadlines(){

        int i;
        String[] topCountryList={"ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu", "cz", "de", "eg", "fr", "gb", "gr", "hk", "hu", "id", "ie", "il", "in", "it", "jp", "kr", "lt", "lv", "ma", "mx", "my", "ng", "nl", "no", "nz", "ph", "pl", "pt", "ro", "rs", "ru", "sa", "se", "sg", "si", "sk", "th", "tr", "tw", "ua", "us", "ve", "za"};
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the country you'd like!");
        String country = in.next();
        for(i=0;i<topCountryList.length;i++){
            if (country.equals(topCountryList[i])){
                API_Translator translator = new API_Translator();
                JSONObject topUSHeadlines = translator.sortByCountry(country);
                printArticles(topUSHeadlines);
            }
            else{
                System.out.println("Error, topCountryHeadlines input failed");
              topCategoryHeadlines();
            }
        }
    }

    /**
     * User enters a category and then it searches based off that keyword.
     */
    public static void topCategoryHeadlines(){

        int i;
        String[] topCategoriesList= {"business","entertainment", "general","health", "science", "sports", "technology"};
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the category you'd like!");
        String category = in.next();
        for(i=0;i<topCategoriesList.length;i++){
            if(category.equals(topCategoriesList[i])){
                API_Translator translator = new API_Translator();

                JSONObject topCategoryHeadlines = translator.sortByCategory(category);
                printArticles(topCategoryHeadlines);
            }
            else{
                System.out.println("Error, topCategoryHeadlines input failed");
                topCountryHeadlines();
            }
        }


    }
}
