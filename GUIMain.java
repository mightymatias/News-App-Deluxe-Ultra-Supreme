import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class GUIMain extends Application {

    final PageData[] pages = new PageData[] {
        new PageData("Title: Ten facts about penguins that will start world war three",
                "Description: I bet you didn't know all these funny silly"
                + "things about the worlds most violent birds...",
                "Via: TheOnion"),
        new PageData("Title: Holy moly is that a bird",
                "Description: Birds should not be allowed in banks, here's why...",
                "Via: Baybel"),
        new PageData("Title: BMW finally removes blinker stalk since their drivers don't use them anyways",
                "Description: If you drive a BMW and you don't use your turn signal, then fr*ck you...",
                "Via: Driver's Everywhere")
    };

    final String[] viewOptions = new String[] {
            "Title",
            "Binomial name",
            "Picture",
            "Description"
    };

    final ImageView pic = new ImageView();
    final Label title = new Label();
    final Label host = new Label();
    final Label description = new Label();
    private int currentIndex = -1;

    final static String[] captions = new String[]{
      "Articles"
    };

    final static String[] urls = new String[]{
       "https://www.theonion.com/"
    };

    final Hyperlink[] hpls = new Hyperlink[captions.length];

    //private int widgetCount = 0;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {


        primaryStage.setTitle("NADUS");
        Scene scene = new Scene(new VBox(), 800, 600);
        scene.setFill(Color.GRAY);

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        for(int i = 0; i < captions.length; i++){
            final Hyperlink hpl = hpls[i] = new Hyperlink(captions[i]);
            hpl.setFont(Font.font("Arial",14));
            final String url = urls[i];

            hpl.setOnAction((ActionEvent e) ->{
                webEngine.load(url);
            });
        }

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.getChildren().addAll(hpls);


        title.setFont(new Font("Verdana Bold", 16));
        host.setFont(new Font("Arial Italic", 10));
        pic.setFitHeight(150);
        pic.setPreserveRatio(true);
        description.setWrapText(true);
        description.setTextAlignment(TextAlignment.JUSTIFY);

        MenuBar menuBar = new MenuBar();

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_RIGHT);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 10));
        vbox.getChildren().addAll(hbox, browser, title, host, pic, description);
        VBox.setVgrow(browser, Priority.ALWAYS);

        Menu menuPreferences = new Menu("Preferences");
        MenuItem country = new MenuItem("Country");

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });


        /*String path = "https://std1.bebee.com/br/pb/3970/820e6940/1080";
        String pathToOpen = "https://std1.bebee.com/br/pb/3970/820e6940/1080";

        Image image = new Image(path);
        ImageView imageView = new ImageView(image);*/


        menuPreferences.getItems().addAll(country, exit);
        menuBar.getMenus().addAll(menuPreferences);



        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void shuffle() {
        int i = currentIndex;
        while (i == currentIndex) {
            i = (int) (Math.random() * pages.length);
        }
        pic.setImage(pages[i].image);
        title.setText(pages[i].name);
        host.setText("(" + pages[i].binNames + ")");
        description.setText(pages[i].description);
        currentIndex = i;
    }

    private static CheckMenuItem createMenuItem (String title, final Node node){
        CheckMenuItem cmi = new CheckMenuItem(title);
        cmi.setSelected(true);
        cmi.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val,
                 Boolean new_val) -> {
                    node.setVisible(new_val);
                });
        return cmi;
    }

    private class PageData {
        public String name;
        public String description;
        public String binNames;
        public Image image;
        public PageData(String name, String description, String binNames) {
            this.name = name;
            this.description = description;
            this.binNames = binNames;
            //image = new Image("https://std1.bebee.com/br/pb/3970/820e6940/1080");
        }
    }

}