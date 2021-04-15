/*
Last update: 22 March 2021

The main method of the project

Contributing authors: Austin Matias
 */

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Storage storage = new Storage();
        storage.initializeStorage();
        ArrayList<Article> favorites = storage.favoriteArray;
        selection();
    }

    /**
     * Given a list of articles, this method prints out the articles to the console, formatted to look nice.
     * @param listOfArticles the JSON Object containing the list of articles to print.
     */
    public static void printArticles(JSONObject listOfArticles){
        Storage storage = new Storage();
        API_Translator translator = new API_Translator();
        ArrayList<Article> temp = translator.getArrayListOfArticlesFromJSONObject(listOfArticles);
        for (int i = 0; i < 10; i++){
            storage.storeNewFavorite(temp.get(i).toString());
        };
        System.out.println();
        System.out.println(temp.toString());
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

    public static void topCountryHeadlines(){
        //possible country tags not yet implemented
        //list of possible countries [ae, ar, at, au, be, bg, br, ca, ch, cn, co, cu, cz, de, eg, fr, gb, gr, hk, hu,
        // id, ie, il, in, it, jp, kr, lt, lv, ma, mx, my, ng, nl, no, nz, ph, pl, pt, ro, rs, ru, sa, se, sg, si, sk,
        // th, tr, tw, ua, us, ve, za]
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the country you'd like!");
        String country = in.next();

        API_Translator translator = new API_Translator();

        JSONObject topUSHeadlines = translator.getAllTopHeadlinesForCountry(country);
        printArticles(topUSHeadlines);
    }

    public static void topCategoryHeadlines(){
        //possible category tags not yet implemented
        //possible categories are business, entertainment, general, health, science, sports, technology.
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the category you'd like!");
        String category = in.next();

        API_Translator translator = new API_Translator();

        JSONObject topCategoryHeadlines = translator.getAllTopHeadlinesForCategory(category);
        printArticles(topCategoryHeadlines);
    }
}
