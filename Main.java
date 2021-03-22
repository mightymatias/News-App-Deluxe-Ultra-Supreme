/*
Last update: 22 March 2021

The main method of the project

Contributing authors: Austin Matias
 */

public class Main {
    public static void main(String[] args) {

        API_Translator api_translator = new API_Translator();
        String testString = "https://newsapi.org/v2/everything?q=Apple&from=2021-03-22&sortBy=popularity&apiKey=061df24bf9374cc9a4d897e1655b7aee";
        System.out.println(api_translator.connectAndReturnContents(testString));
    }
}
