
/*
    04/22/2021
    Connor Contursi, Austin Matias
    A GUI class for CSC 340.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

public class GuiMain extends Application {

    //Arrays for all of the data that will be processed
    private static ArrayList<String> article = new ArrayList<String>();
    private static ArrayList<String> author = new ArrayList<String>();
    private static ArrayList<String> summary = new ArrayList<String>();
    private static ArrayList<String> URL = new ArrayList<String>();
    private static ArrayList<String> imageURL = new ArrayList<String>();

    //Creates scrollpane object and image objects
    final ScrollPane sp = new ScrollPane();

    //VBox objects for various things
    final VBox vb = new VBox();
    final VBox vbWeb = new VBox();

    //Drop shadow for pictures
    DropShadow shadow = new DropShadow();

    //caption for hyperlink button
    final static String[] captions = new String[]{
            "Article"
    };

    //hyperlink creation for articles
    final Hyperlink[] hpls = new Hyperlink[captions.length];

    //Method to provide all of the info to the arrays, will be its own class later on but is here now for testing purposes
    public void info(){
        /*
        article.add("Multiple people shot at Austin-East High School in Knoxville - Knoxville News Sentinel");
        article.add("Joel Greenberg tried to get pardon from Trump through Gaetz: report - Business Insider");
        article.add("Biden's CBP nominee defended sanctuary cities, criticized police working with immigration authorities - Fox News");
        article.add("Microsoft acquires Nuance—makers of Dragon speech rec—for $16 billion - Ars Technica");
        article.add("Minnesota police chief says he believes officer meant to grab Taser before shooting Black man during traffic stop - NBC News");
        article.add("Prince Harry reunites with cousin Eugenie before Prince Philip's funeral - Page Six");
        article.add("Kanye Asks for Joint Custody, Just Like Kim Kardashian, In Divorce Case - TMZ");
        article.add("Germany Is Expected To Centralize Its COVID-19 Response. Some Fear It May Be Too Late - NPR");
        article.add("Macaulay Culkin and Brenda Song welcome their first child together, Dakota - USA TODAY");
        article.add("Former Chiefs coach Britt Reid charged with DWI in crash that seriously hurt 5-year-old girl - WDAF FOX4 Kansas City");

        author.add("Joe Smith");
        author.add("Grace Panetta");
        author.add("Evie Fordham");
        author.add("Jim Salter");
        author.add("David K. Li");
        author.add("Sara Nathan");
        author.add("TMZ Staff");
        author.add("Esme Nicholson");
        author.add("Jenna Ryu");
        author.add("Andrew Lynch, Heidi Schmidt");

        summary.add("Police have responded to a shooting at Austin-East Magnet High School in Knoxville that has injured multiple people, including a police officer.");
        summary.add("Greenberg, who is facing 33 federal charges for crimes including sex trafficking of a minor, could be key in the DOJ's reported probe into Gaetz.");
        summary.add("President Biden's expected pick to lead U.S. Customs and Border Protection, Chris Magnus, has a record of defending sanctuary cities and resisting Trump administration orders about undocumented immigrants.");
        summary.add("Nuance's deep-learning-based speech recognition serves 77% of US hospitals.");
        summary.add("Daunte Wright, 20, was killed during a traffic stop, sparking tense protests near Minneapolis.");
        summary.add("Eugenie is the first member of the royal family to see Harry in more than a year.");
        summary.add("Kanye West filed his answer to Kim Kardashian's divorce petition, and it's basically a mirror image of his estranged wife's legal docs.");
        summary.add("The country's scattershot approach, with each of 16 states imposing different regulations, has come under mounting criticism as cases surpass 3 million and");
        summary.add("Congratulations are in order! The two actors revealed the birth of their first child together, a baby boy named Dakota.");
        summary.add("More than two months after former Kansas City Chiefs coach Britt Reid was involved in a crash that seriously injured a 5-year-old girl, the Jackson County Prosecutor’s Office charged him with DWI — serious physical injury on Monday.");

        URL.add("https://www.knoxnews.com/story/news/crime/2021/04/12/police-respond-reports-shooting-near-austin-east-high-school/7194244002/");
        URL.add("https://www.businessinsider.com/joel-greenberg-tried-to-get-trump-pardon-through-gaetz-report-2021-4");
        URL.add("https://www.foxnews.com/politics/biden-cbp-nominee-chris-magnus-sanctuary-city-immigration");
        URL.add("https://arstechnica.com/gadgets/2021/04/microsoft-acquires-nuance-makers-of-dragon-speech-rec-for-16-billion/");
        URL.add("https://www.nbcnews.com/news/us-news/minnesota-police-chief-says-he-believes-officer-meant-grab-taser-n1263817");
        URL.add("https://pagesix.com/2021/04/12/prince-harry-reunites-with-eugenie-before-prince-philips-funeral/");
        URL.add("https://www.tmz.com/2021/04/12/kanye-west-divorce-answer-joint-custody-kim-kardashian/");
        URL.add("https://www.npr.org/2021/04/12/986408031/germany-is-expected-to-centralize-its-covid-19-response-some-fear-it-may-be-too");
        URL.add("https://www.usatoday.com/story/entertainment/celebrities/2021/04/12/macaulay-culkin-brenda-song-welcome-their-first-child-together-dakota/7191294002/");
        URL.add("https://fox4kc.com/sports/chiefs/former-chiefs-coach-britt-reid-charged-with-dwi-in-crash-that-seriously-hurt-5-year-old-girl/");

        imageURL.add("https://www.gannett-cdn.com/presto/2021/04/12/PKNS/ae9cc539-3c68-4b5e-a84b-1230540122f1-KNS-shooting_AE_BP_1.jpg?crop=3322,1869,x0,y346&width=3200&height=1680&fit=bounds");
        imageURL.add("https://i.insider.com/5af9c3fa42e1cc1b98150b23?width=1200&format=jpeg");
        imageURL.add("https://static.foxnews.com/foxnews.com/content/uploads/2021/04/AP21102618581630.jpg");
        imageURL.add("https://cdn.arstechnica.net/wp-content/uploads/2021/04/GettyImages-128755111-dragon-medical-760x380.jpg");
        imageURL.add("https://media4.s-nbcnews.com/j/newscms/2021_15/3463989/210412-brooklyn-center-protest-jm-0927_591a6a33c825d817030a5d3402d9f076.nbcnews-fp-1200-630.jpg");
        imageURL.add("https://pagesix.com/wp-content/uploads/sites/3/2021/04/prince-harry-princess-eugenie.jpg?quality=90&strip=all&w=1200");
        imageURL.add("https://imagez.tmz.com/image/9a/16by9/2021/04/10/9a2ef5a2e17f49e4a60d5a3943813109_xl.jpg");
        imageURL.add("https://media.npr.org/assets/img/2021/04/12/gettyimages-1232264282_wide-6cf5f56d92155b1888551db4e389fe4054001d0e.jpg?s=1400");
        imageURL.add("https://www.gannett-cdn.com/presto/2021/04/12/USAT/37a8a088-1932-4006-8f6f-4d8ad199bf69-song-macaulay.jpg?crop=1919,1080,x0,y57&width=1600&height=800&fit=bounds");
        imageURL.add("https://fox4kc.com/wp-content/uploads/sites/16/2021/04/britt-reid-mugshot.jpg?w=1280");
         */
    }

    //Main GUI method
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Adds article info to arrays to be displayed
        info();

        //Creates object groups for various GUI components
        Group root = new Group();
        Group webRoot = new Group();

        //Scene for the webview [Under Construction]
        Scene webScene = new Scene(webRoot, 1600, 800);

        //Menubar component creation
        MenuBar menuBar = new MenuBar();
        Menu menuSettings = new Menu("Settings");
        MenuItem country = new MenuItem("Country");

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });

        menuSettings.getItems().addAll(country, exit);
        menuBar.getMenus().addAll(menuSettings);

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        browser.prefHeightProperty().bind(primaryStage.heightProperty());
        browser.prefWidthProperty().bind(primaryStage.widthProperty());

        HBox hbWeb = new HBox();

        sp.fitToHeightProperty().set(true);

        sp.setContent(vb);
        sp.setHbarPolicy((ScrollPane.ScrollBarPolicy.NEVER));
        sp.setVmax(800);
        sp.setPrefSize(1600, 800);
        sp.pannableProperty().set(true);

        //Loop that pushes all of the article information to the GUI
        for(int i = 0; i < article.toArray().length; i++) {
            //Allows the images to be displayed
            final Image image = new Image(imageURL.get(i));
            final ImageView pic = new ImageView(image);

            //Creates button to view the article
            final Hyperlink hpl = hpls[0] = new Hyperlink(captions[0]);
            final String url = URL.get(i);

            hpl.setOnAction((ActionEvent e) -> {
                webEngine.load(url);
                primaryStage.setScene(webScene);
            });

            hbWeb.setAlignment(Pos.BASELINE_CENTER);
            hbWeb.getChildren().addAll(hpls);

            //displays various article attributes
            Text title = new Text(article.get(i));
            title.setFont(new Font("Verdana Bold",  20));

            Text auth = new Text(author.get(i));
            auth.setFont(new Font("Arial Italic", 14));

            Text desc = new Text(summary.get(i));
            desc.setFont(new Font("Arial", 16));

            //Allows for reasonable spacing between article objects
            Text blank = new Text("\n\n");

            //Adjusts picture size
            pic.setFitHeight(300);
            pic.setFitWidth(534);
            //pic.setPreserveRatio(true);
            pic.setEffect(shadow);

            //Drop shadow modifiers
            shadow.setColor(Color.GREY);
            shadow.setOffsetX(2);
            shadow.setOffsetY(2);

            //Adds all article pieces to scene and aligns it
            vb.getChildren().addAll(pic, title, auth, desc, hpl, blank);
            vb.setPadding(new Insets(30,0,10,0));
            vb.setAlignment(Pos.TOP_CENTER);

        }

        //Scrollbar, menubar, and article components are added to a single group to be passed to the scene
        root.getChildren().addAll(vb, sp, menuBar);

        //creates main scene to display articles
        Scene scene = new Scene(root, 1600, 800);

        //text object to create proper spacing between return button and web object
        Text blank = new Text("\n\n");

        //button to bring you back to article scene
        Button button = new Button("  Return  ");
        button.setOnAction((ActionEvent e) -> {
            primaryStage.setScene(scene);
        });

        //generates vbox object for web elements and sets proper spacing
        vbWeb.getChildren().addAll(button, blank);
        vbWeb.getChildren().addAll(hbWeb, browser);
        vbWeb.setAlignment(Pos.TOP_CENTER);
        vbWeb.setPadding(new Insets(20,0,10,0));
        VBox.setVgrow(browser, Priority.ALWAYS);
        webRoot.getChildren().addAll(vbWeb);
        webRoot.setAutoSizeChildren(true);

        //sets the stage to be scene and sets background color
        primaryStage.setScene(scene);
        primaryStage.setTitle("NADUS");
        scene.setFill(Color.WHITE);

        //generates stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        topCountryHeadlines();
        launch(args);
    }

    public static void topCountryHeadlines(){
        //possible country tags not yet implemented
        //list of possible countries [ae, ar, at, au, be, bg, br, ca, ch, cn, co, cu, cz, de, eg, fr, gb, gr, hk, hu,
        // id, ie, il, in, it, jp, kr, lt, lv, ma, mx, my, ng, nl, no, nz, ph, pl, pt, ro, rs, ru, sa, se, sg, si, sk,
        // th, tr, tw, ua, us, ve, za]
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the country you'd like!");
        String country = "us";

        API_Translator translator = new API_Translator();


        JSONObject topUSHeadlines = translator.getAllTopHeadlinesForCountry(country);
        ArrayList<Article> articleArrayList = translator.getArrayListOfArticlesFromJSONObject(topUSHeadlines);
        primeArrayLists(articleArrayList);

    }

    public static void primeArrayLists(ArrayList<Article> _articleList){
        GUI_Translator guiTranslator = new GUI_Translator();
        article = guiTranslator.setTitleList(_articleList);
        author = guiTranslator.setAuthorList(_articleList);
        summary = guiTranslator.setDescriptionList(_articleList);
        URL = guiTranslator.setUrlList(_articleList);
        imageURL = guiTranslator.setImageUrlList(_articleList);
    }
}
