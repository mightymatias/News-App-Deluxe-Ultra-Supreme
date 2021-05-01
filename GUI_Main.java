/*
Last update: 30 April 2021

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
import java.util.ArrayList;

public class GUI_Main extends Application {

    //A list of articles as provided by the API through the translator.
    private static ArrayList<Article> articleList = new ArrayList<>();

    //The storage object to be used throughout the program
    private static Storage favoriteStorage = new Storage();

    //Arrays for all of the data that will be processed
    private static ArrayList<String> title = new ArrayList<>();
    private static ArrayList<String> author = new ArrayList<>();
    private static ArrayList<String> summary = new ArrayList<>();
    private static ArrayList<String> url = new ArrayList<>();
    private static ArrayList<String> imageURL = new ArrayList<>();

    //Drop shadow for pictures
    private static DropShadow shadow = new DropShadow();

    //Caption for hyperlink button
    private final static String[] captions = new String[] {
            "View Article"
    };

    //Hyperlink creation for articles
    private final Hyperlink[] hpls = new Hyperlink[captions.length];

    //List of possible country codes that API takes as input
    private final static String[] countries = new String[] {
            "ar", "au", "at", "be", "br", "bg", "ca", "co",
            "cu", "cz", "eg", "fr", "de", "gr", "hu", "in",
            "id", "ie", "il", "it", "jp", "lv", "lt", "my", "mx",
            "ma", "nl", "nz", "ng", "no", "ph", "pl", "pt", "ro",
            "ru", "sa", "rs", "sg", "sk", "si", "za", "kr", "se",
            "ch", "tw", "tr", "ua", "gb", "us", "ve"
    };

    //Full country names associated with the country code
    private final static String[] fullCountryName = new String[] {
            "Argentina", "Australia", "Austria", "Belgium", "Brazil",
            "Bulgaria", "Canada", "Colombia", "Cuba", "Czech Republic",
            "Egypt", "France", "Germany", "Greece", "Hungary", "India",
            "Indonesia", "Ireland", "Israel", "Italy", "Japan", "Latvia", "Lithuania",
            "Malaysia", "Mexico", "Morocco", "Netherlands", "New Zealand", "Nigeria",
            "Norway", "Philippines", "Poland", "Portugal", "Romania", "Russia", "Saudi Arabia",
            "Serbia", "Singapore", "Slovakia", "Slovenia", "South Africa", "South Korea",
            "Sweden", "Switzerland", "Taiwan", "Turkey", "Ukraine",
            "United Kingdom", "United States", "Venezuela"
    };

    //Creates main stage for the GUI so that all methods are using the same stage and not making a new stage each time
    private final Stage stage = new Stage();

    /**
     * Main GUI method that creates the entire GUI interface, including webengine.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start (Stage primaryStage) {

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1600, 800);

        //Menubar component creation
        MenuBar menuBar = new MenuBar();
        Menu menuSettings = new Menu ("Settings");

        MenuItem viewFavorites = new MenuItem("View Favorites");
        viewFavorites.setOnAction((ActionEvent v) -> {
            viewFavorites();
        });

        //Country selection
        Menu country = new Menu ("Country");
        for (int a = 0; countries.length > a; a++) {
            MenuItem subCountry = new MenuItem(fullCountryName[a]);
            int finalA = a;

            //Adds article info to arrays to be displayed
            subCountry.setOnAction((ActionEvent f) -> {
                viewArticles(countries[finalA]);
            });
            country.getItems().add(subCountry);
        }

        //Menu button for exiting the program
        MenuItem exit = new MenuItem("Save & Exit");
        exit.setOnAction((ActionEvent t) -> {
            favoriteStorage.saveArrayToFile();
            System.exit(0);
        });

        menuBar.prefHeightProperty().bind(primaryStage.widthProperty());
        menuSettings.getItems().addAll(viewFavorites, country, exit);
        menuBar.getMenus().addAll(menuSettings);

        root.setTop(menuBar);

        //Loading GIF to make the GUI top quality
        ImageView imageView = new ImageView();
        imageView.setImage(new Image("loading.gif"));
        imageView.setFitWidth(700);
        imageView.setPreserveRatio(true);
        root.setCenter(imageView);

        //Sets stage name and color
        stage.setTitle("NADUS");
        scene.setFill(Color.WHITE);
        stage.setResizable(false);

        //Generates stage
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Starts program and launches storage
     * @param args System arguments.
     */
    protected static void main (String[] args) {
        favoriteStorage.initializeStorage();
        launch(args);
        favoriteStorage.saveArrayToFile();
    }

    /**
     * Calls GUI_translator to get api article information
     * @param _articleList The list of articles to prime.
     */
    protected static void primeArrayLists (ArrayList<Article> _articleList){
        GUI_Translator guiTranslator = new GUI_Translator();
        title = guiTranslator.setTitleList(_articleList);
        author = guiTranslator.setAuthorList(_articleList);
        summary = guiTranslator.setDescriptionList(_articleList);
        url = guiTranslator.setUrlList(_articleList);
        imageURL = guiTranslator.setImageUrlList(_articleList);
    }

    /**
     * Method to provide all of the info to the arrays, will be its own class later on but is here now for testing purposes
     * @param countrySelection The country code to be passed into the program.
     */
    protected void viewArticles(String countrySelection) {
        GUI_Translator translator = new GUI_Translator();
        this.articleList = translator.fetchArticles(countrySelection);
        primeArrayLists(articleList);

        //Loads gui
        guiDisplay();
    }

    /**
     * Method to get all favorites to display them
     */
    protected void viewFavorites () {
        primeArrayLists(favoriteStorage.favoriteArray);

        //Loads gui
        favoriteDisplay();
    }

    /**
     * Creates main GUI to display
     */
    private void guiDisplay() {
        //Creates scrollpane object and image objects
        ScrollPane sp = new ScrollPane();

        //VBox objects for various things
        VBox vb = new VBox();
        VBox vbWeb = new VBox();

        //Creates object groups for various GUI components
        Group root = new Group();
        Group webRoot = new Group();

        //Scene for the webview
        Scene webScene = new Scene(webRoot, 1600, 900);

        //Menubar component creation
        MenuBar menuBar = new MenuBar();
        Menu menuSettings = new Menu("Settings");

        MenuItem viewFavorites = new MenuItem("View Favorites");
        viewFavorites.setOnAction((ActionEvent v) -> {
            viewFavorites();
        });

        Menu country = new Menu("Country");

        //Country selection
        for (int a = 0; countries.length > a; a++) {
            MenuItem subCountry = new MenuItem(fullCountryName[a]);
            int finalA = a;

            //Adds article info to arrays to be displayed
            subCountry.setOnAction((ActionEvent f) -> {
                viewArticles(countries[finalA]);
                root.getChildren().clear();
                webRoot.getChildren().clear();
            });
            country.getItems().add(subCountry);
        }

        //Adds button to exit program
        MenuItem exit = new MenuItem("Save & Exit");
        exit.setOnAction((ActionEvent t) -> {
            favoriteStorage.saveArrayToFile();
            System.exit(0);
        });

        menuSettings.getItems().addAll(viewFavorites, country, exit);
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
        sp.setPrefSize(1600, 775);
        sp.pannableProperty().set(true);

        //Loop that pushes all of the article information to the GUI
        for (int i = 0; i < (imageURL.toArray().length); i++) {

            //Allows the images to be displayed
            final Image image;
            final ImageView pic;
            image = new Image(imageURL.get(i));
            pic = new ImageView(image);

            //Provides image url when you hover over picture
            final Tooltip tooltip = new Tooltip(GUI_Main.imageURL.get(i));
            tooltip.setFont(new Font("Arial", 16));
            Tooltip.install(pic, tooltip);

            //Creates button to view the article
            final Hyperlink hpl = hpls[0] = new Hyperlink(captions[0]);
            final String url = GUI_Main.url.get(i);
            hpl.setFont(new Font("Arial", 16));

            //Creates hyperlink button that opens article website and article page
            hpl.setOnAction((ActionEvent e) -> {
                webEngine.load(url);
                stage.setScene(webScene);
            });

            //Aligns webengine
            hbWeb.setAlignment(Pos.BASELINE_CENTER);
            hbWeb.getChildren().addAll(hpls);

            //Variable for setOnAction
            int finalI = i;

            //Creates favorite button to favorite or unfavorite articles
            Button favButton = new Button("Favorite");
            favButton.setFont(new Font("Arial", 14));
            favButton.setOnAction((ActionEvent z) -> {
                if(articleList.get(finalI).getIsFavorited() == false) {
                    favoriteStorage.newFavorite(articleList.get(finalI));
                    favButton.setText("Unfavorite");
                } else if(articleList.get(finalI).getIsFavorited() == true) {
                    favoriteStorage.removeFavorite(articleList.get(finalI));
                    favButton.setText("Favorite");
                }
            });

            //Displays various article attributes
            Text title = new Text(GUI_Main.title.get(i));
            title.setFont(new Font("Verdana Bold",  20));

            //Checks if author is provided
            Text auth;
            if (author.get(i) != "null") {
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
            if (length > 100) {
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
            if (summary.get(i) != "null") {
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

        //Scrollbar and article components are added to a single group to be passed to the scene
        root.getChildren().addAll(vb, sp);

        //Creates main scene to display articles
        Scene scene = new Scene(new VBox(), 1600, 800);

        //Sets up scene with menubar
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, root);

        //Text object to create proper spacing between return button and web object
        Text blank = new Text("\n\n");

        //Button to bring you back to article scene
        Button button = new Button("  Return  ");
        button.setFont(new Font("Arial", 14));
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

        //Generates stage
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates GUI to display favorited articles that have been saved
     */
    private void favoriteDisplay () {
        //Creates scrollpane object and image objects
        ScrollPane sp = new ScrollPane();

        //VBox objects for various things
        VBox vb = new VBox();
        VBox vbWeb = new VBox();

        //Creates object groups for various GUI components
        Group root = new Group();
        Group webRoot = new Group();

        //Scene for the webview
        Scene webScene = new Scene(webRoot, 1600, 900);

        //Menubar component creation
        MenuBar menuBar = new MenuBar();
        Menu menuSettings = new Menu("Settings");

        MenuItem viewFavorites = new MenuItem("View Favorites");
        viewFavorites.setOnAction((ActionEvent v) -> {
            viewFavorites();
        });

        Menu country = new Menu("Country");

        //Country selection
        for (int a = 0; countries.length > a; a++){
            MenuItem subCountry = new MenuItem(fullCountryName[a]);
            int finalA = a;

            //Adds article info to arrays to be displayed
            subCountry.setOnAction((ActionEvent f) -> {
                viewArticles(countries[finalA]);
                root.getChildren().clear();
                webRoot.getChildren().clear();
            });
            country.getItems().add(subCountry);
        }

        //Adds button to exit program
        MenuItem exit = new MenuItem("Save & Exit");
        exit.setOnAction((ActionEvent t) -> {
            favoriteStorage.saveArrayToFile();
            System.exit(0);
        });

        menuSettings.getItems().addAll(viewFavorites, country, exit);
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
        sp.setPrefSize(1600, 775);
        sp.pannableProperty().set(true);

        //Loop that pushes all of the article information to the GUI
        for(int i = 0; i < (imageURL.toArray().length); i++) {

            //Allows the images to be displayed
            final Image image;
            final ImageView pic;
            image = new Image(imageURL.get(i));
            pic = new ImageView(image);

            //Provides image url when you hover over picture
            final Tooltip tooltip = new Tooltip(GUI_Main.imageURL.get(i));
            tooltip.setFont(new Font("Arial", 16));
            Tooltip.install(pic, tooltip);

            //Creates button to view the article
            final Hyperlink hpl = hpls[0] = new Hyperlink(captions[0]);
            final String url = GUI_Main.url.get(i);
            hpl.setFont(new Font("Arial", 16));

            //Creates hyperlink button that opens article website and article page
            hpl.setOnAction((ActionEvent e) -> {
                webEngine.load(url);
                stage.setScene(webScene);
            });

            //Aligns webengine
            hbWeb.setAlignment(Pos.BASELINE_CENTER);
            hbWeb.getChildren().addAll(hpls);

            //Variable for setOnAction
            int finalI = i;

            //Creates favorite button to favorite or unfavorite articles
            Button favButton = new Button("Remove From Favorites");
            favButton.setFont(new Font("Arial", 14));
            favButton.setOnAction((ActionEvent z) -> {
                favoriteStorage.removeFavorite(favoriteStorage.favoriteArray.get(finalI));
                viewFavorites();
            });

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
            if (length > 100) {
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

        //Creates button that allows you to clear favorites; changes color to show action
        Button clearFavorites = new Button("Clear Favorites");
        clearFavorites.setFont(new Font("Arial", 20));
        clearFavorites.setStyle("-fx-background-color: rgb(160,160,160)");
        clearFavorites.setOnMouseEntered(e -> clearFavorites.setStyle("-fx-background-color: rgb(231,29,29)"));
        clearFavorites.setOnAction((ActionEvent n) -> {
            int favoriteLength = favoriteStorage.favoriteArray.toArray().length;
            for(int m = 0; m < favoriteLength; m++) {
                favoriteStorage.removeFavorite(favoriteStorage.favoriteArray.get(0));
            }
            viewFavorites();
        });
        clearFavorites.setOnMouseExited(e -> clearFavorites.setStyle("-fx-background-color: rgb(160,160,160)"));

        vb.getChildren().add(clearFavorites);
        vb.setPadding(new Insets(20,0,10,0));
        vb.setAlignment(Pos.TOP_CENTER);

        //Scrollbar and article components are added to a single group to be passed to the scene
        root.getChildren().addAll(vb, sp);

        //Creates main scene to display articles
        Scene scene = new Scene(new VBox(), 1600, 800);

        //Sets up scene with menubar
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, root);

        //Text object to create proper spacing between return button and web object
        Text blank = new Text("\n\n");

        //Button to bring you back to article scene
        Button button = new Button("  Return  ");
        button.setFont(new Font("Arial", 14));
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

        //Generates stage
        stage.setScene(scene);
        stage.show();
    }
}
