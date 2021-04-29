/*
Last update: 27 April 2021

The main class that runs the GUI for the News App.

Contributing authors: Connor Contursi, Austin Matias
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
import javafx.scene.control.ProgressIndicator;

public class GUI_Main extends Application {

    private static ArrayList<Article> articleList = new ArrayList<>();

    private static Storage favoriteStorage = new Storage();

    //Arrays for all of the data that will be processed
    private static ArrayList<String> title = new ArrayList<>();
    private static ArrayList<String> author = new ArrayList<>();
    private static ArrayList<String> summary = new ArrayList<>();
    private static ArrayList<String> URL = new ArrayList<>();
    private static ArrayList<String> imageURL = new ArrayList<>();

    //Drop shadow for pictures
    DropShadow shadow = new DropShadow();

    //caption for hyperlink button
    final static String[] captions = new String[]{
            "Article"
    };

    //hyperlink creation for articles
    final Hyperlink[] hpls = new Hyperlink[captions.length];

    //list of possible countries [ae, ar, at, au, be, bg, br, ca, ch, cn, co, cu, cz, de, eg, fr, gb, gr, hk, hu,
    // id, ie, il, in, it, jp, kr, lt, lv, ma, mx, my, ng, nl, no, nz, ph, pl, pt, ro, rs, ru, sa, se, sg, si, sk,
    // th, tr, tw, ua, us, ve, za]
    final static String[] countries = new String[]{
            "ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu", "cz", "de", "eg", "fr", "gb", "gr", "hk", "hu",
            "id", "ie", "il", "in", "it", "jp", "kr", "lt", "lv", "ma", "mx", "my", "ng", "nl", "no", "nz", "ph", "pl", "pt", "ro", "rs", "ru", "sa", "se", "sg", "si", "sk",
            "th", "tr", "tw", "ua", "us", "ve", "za"
    };

    final Stage stage = new Stage();

    /**
     *     Method to provide all of the info to the arrays, will be its own class later on but is here now for testing purposes
     * @param cou
     */
    public void info(String cou){
        //Country that will be passed to API
        String country = cou;

        API_Translator translator = new API_Translator();

        //Gets all article information
        JSONObject topHeadlines = translator.sortByCountry(country);
        this.articleList = translator.getArrayListOfArticlesFromJSONObject(topHeadlines);

//        for (int i = 0; i < articleArrayList.toArray().length; i++){
//            favoriteStorage.newFavorite(articleArrayList.get(i));
//        }

        primeArrayLists(articleList);

        //Loads gui
        guiDisplay();
    }

    /**
     *     Main GUI method that creates the entire GUI interface, including webengine
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        //VBox object for various things
        VBox vb = new VBox();

        //Creates object groups for various GUI components
        Group root = new Group();

        //Menubar component creation
        MenuBar menuBar = new MenuBar();
        Menu menuSettings = new Menu("Settings");
        Menu country = new Menu("Country");

        //Country selection
        for(int a = 0; countries.length > a; a++){
            MenuItem subCountry = new MenuItem(countries[a]);
            int finalA = a;

            //Adds article info to arrays to be displayed
            subCountry.setOnAction((ActionEvent f) -> {
                info(countries[finalA]);
            });
            country.getItems().add(subCountry);
        }

        //Menu button for exiting the program
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((ActionEvent t) -> {
            favoriteStorage.saveArrayToFile();
            System.exit(0);
        });

        menuSettings.getItems().addAll(country, exit);
        menuBar.getMenus().addAll(menuSettings);

        //Progress indicator
        ProgressIndicator PI = new ProgressIndicator();
        final VBox Ind = new VBox();
        Ind.getChildren().addAll(PI);
        Ind.setSpacing(5);
        Ind.setPadding(new Insets(10, 10, 10, 10));
        Ind.setAlignment(Pos.CENTER);

        //Adds components to group to be displayed
        root.getChildren().addAll(vb, Ind, menuBar);

        //creates main scene to display articles
        Scene sceneMenu = new Scene(root, 1600, 800);

        //sets the stage to be scene and sets background color
        stage.setScene(sceneMenu);
        stage.setTitle("NADUS");
        sceneMenu.setFill(Color.WHITE);

        //generates stage
        stage.setScene(sceneMenu);
        stage.show();
    }

    /**
     * Creates main GUI to display
     */
    public void guiDisplay() {
        //Creates scrollpane object and image objects
        ScrollPane sp = new ScrollPane();

        //VBox objects for various things
        VBox vb = new VBox();
        VBox vbWeb = new VBox();

        //Creates object groups for various GUI components
        Group root = new Group();
        Group webRoot = new Group();

        //Scene for the webview [Under Construction]
        Scene webScene = new Scene(webRoot, 1600, 900);

        //Menubar component creation
        MenuBar menuBar = new MenuBar();
        Menu menuSettings = new Menu("Settings");
        Menu country = new Menu("Country");

        //Country selection
        for(int a = 0; countries.length > a; a++){
            MenuItem subCountry = new MenuItem(countries[a]);
            int finalA = a;

            //Adds article info to arrays to be displayed
            subCountry.setOnAction((ActionEvent f) -> {
                info(countries[finalA]);
                root.getChildren().clear();
                webRoot.getChildren().clear();
            });
            country.getItems().add(subCountry);
        }

        //Adds button to exit program
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((ActionEvent t) -> {
            favoriteStorage.saveArrayToFile();
            System.exit(0);
        });

        menuSettings.getItems().addAll(country, exit);
        menuBar.getMenus().addAll(menuSettings);

        //Creates webview and webengine to display articles
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        //Resizes browser to fit window
        browser.prefHeightProperty().bind(stage.heightProperty());
        browser.prefWidthProperty().bind(stage.widthProperty());

        //Creates HBox for web content
        HBox hbWeb = new HBox();

        //Scrollpane properties
        sp.fitToHeightProperty().set(true);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.setContent(vb);
        sp.setHbarPolicy((ScrollPane.ScrollBarPolicy.NEVER));
        sp.setVmax(800);
        sp.setPrefSize(1600, 800);
        sp.pannableProperty().set(true);

        //Loop that pushes all of the article information to the GUI
        for(int i = 0; i < (imageURL.toArray().length); i++) {

            //Allows the images to be displayed
            final Image image;
            final ImageView pic;

            //Placeholder image in case image url is not provided for an article
            final String placeholder = "http://www.bobos.it/new/wp-content/uploads/2017/11/tv-noise-0212-retro-tv-color-bars-loop_4yiztcvfg__F0000.png";

            //Checks to make sure url is provided
            if(imageURL.get(i) != "null") {
                image = new Image(imageURL.get(i));
                pic = new ImageView(image);
            } else {
                image = new Image(placeholder);
                pic = new ImageView(image);
            }

            //Creates button to view the article
            final Hyperlink hpl = hpls[0] = new Hyperlink(captions[0]);
            final String url = URL.get(i);

            //Creates hyperlink button that opens article website and article page
            hpl.setOnAction((ActionEvent e) -> {
                webEngine.load(url);
                stage.setScene(webScene);
            });

            //Aligns webengine
            hbWeb.setAlignment(Pos.BASELINE_CENTER);
            hbWeb.getChildren().addAll(hpls);

            //Label fav = new Label("Favorite");
            //Label unfav = new Label("Unfavorite");

            //String fav = "Favorite";
            //String unfav = "Unfavorite";

            Button favButton = new Button("Favorite");

            int finalI = i;
            if(articleList.get(finalI).getIsFavorited() == false) {
                favButton.setOnAction((ActionEvent t) -> {
                    favoriteStorage.newFavorite(articleList.get(finalI));
                    System.out.println(articleList.get(finalI).getIsFavorited());
                    favButton.setText("Unfavorite");
                });
            } else if(articleList.get(finalI).getIsFavorited() == true) {
                favButton.setText("Favorite");
            }

            //Displays various article attributes
            Text title = new Text(GUI_Main.title.get(i));
            title.setFont(new Font("Verdana Bold",  20));

            //Checks if author is provided
            Text auth;
            if(author.get(i) != "null") {
                auth = new Text(author.get(i));
                auth.setFont(new Font("Arial Italic", 14));
            } else {
                auth = new Text("Not Provided");
                auth.setFont(new Font("Arial Italic", 14));
            }

            //Checks how long the description is and puts it on two lines if it is too long
            int length = summary.get(i).length();
            String sum = summary.get(i);
            String printDescription;
            StringBuilder description = new StringBuilder();

            int spaceAdded = 0;

            //Checks how long the description is and puts it on two lines if it is too long
            if(length > 100) {
                for (int j = 0; j < length; j++) {
                    description.append(sum.charAt(j));
                    if (j > (length / 2) & spaceAdded == 0 & sum.charAt(j) == ' ') {
                        spaceAdded = 1;
                        description.append("\n");
                    }
                }

                //Converts stringbuilder to string so it can be converted to a text object
                printDescription = description.toString();
            } else {
                printDescription = summary.get(i);
            }


            //Checks if description is provided
            Text desc;
            if(summary.get(i) != "null") {
                desc = new Text(printDescription);
                desc.setFont(new Font("Arial", 16));
            } else {
                desc = new Text("Not Provided");
                desc.setFont(new Font("Arial", 16));
            }

            //Allows for reasonable spacing between article objects
            Text blank = new Text("\n\n");

            //Adjusts picture size
            pic.setFitHeight(300);
            pic.setFitWidth(534);
            pic.setEffect(shadow);

            //Drop shadow modifiers
            shadow.setColor(Color.GREY);
            shadow.setOffsetX(2);
            shadow.setOffsetY(2);

            //Adds all article pieces to scene and aligns it
            vb.getChildren().addAll(pic, title, auth, desc, hpl, favButton, blank);
            vb.setPadding(new Insets(30,0,10,0));
            vb.setAlignment(Pos.TOP_CENTER);

        }

        //Scrollbar, menubar, and article components are added to a single group to be passed to the scene
        root.getChildren().addAll(vb, sp, menuBar);

        //Creates main scene to display articles
        Scene scene = new Scene(root, 1600, 800);

        //Text object to create proper spacing between return button and web object
        Text blank = new Text("\n\n");

        //Button to bring you back to article scene
        Button button = new Button("  Return  ");
        button.setOnAction((ActionEvent e) -> {
            stage.setScene(scene);
        });

        //Generates vbox object for web elements and sets proper spacing
        vbWeb.getChildren().addAll(button, blank);
        vbWeb.getChildren().addAll(hbWeb, browser);
        vbWeb.setAlignment(Pos.TOP_CENTER);
        vbWeb.setPadding(new Insets(20,0,10,0));
        VBox.setVgrow(browser, Priority.ALWAYS);
        webRoot.getChildren().addAll(vbWeb);
        webRoot.setAutoSizeChildren(true);

        //Sets the stage to be scene and sets background color
        stage.setScene(scene);
        stage.setTitle("NADUS");
        scene.setFill(Color.WHITE);

        //Generates stage
        stage.setScene(scene);
        stage.show();
    }

    /**
     *     Starts program
     * @param args
     */
    public static void main(String[] args) {
        favoriteStorage.initializeStorage();
        launch(args);
        favoriteStorage.saveArrayToFile();
    }

    /**
     *     Calls GUI_translator to get api article information
     * @param _articleList
     */
    public static void primeArrayLists(ArrayList<Article> _articleList){
        GUI_Translator guiTranslator = new GUI_Translator();
        title = guiTranslator.setTitleList(_articleList);
        author = guiTranslator.setAuthorList(_articleList);
        summary = guiTranslator.setDescriptionList(_articleList);
        URL = guiTranslator.setUrlList(_articleList);
        imageURL = guiTranslator.setImageUrlList(_articleList);
    }
}
