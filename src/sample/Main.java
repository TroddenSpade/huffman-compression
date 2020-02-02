package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;

public class Main extends Application {

    private static final int HEIGHT = 400;
    private static final int WIDTH = 740;

    private Scene startScene;
    private Scene compressScene;
    private Scene decompressScene;

    private String name = "";

    @Override
    public void start(Stage primaryStage) throws Exception{

        Huffman huffman = new Huffman();

        startScreen(primaryStage);
        compressScreen(primaryStage,huffman);
        extractScreen(primaryStage,huffman);
        primaryStage.setTitle("Huffman Compression");
        primaryStage.setScene(startScene);
        primaryStage.show();


    }


    public void startScreen(Stage stage){

        Label title = new Label("Huffman Compressor");
        title.setFont(new Font("Boulder", 30));

        BorderPane startPane = new BorderPane();
        startScene = new Scene(startPane, WIDTH, HEIGHT);
        title.setOnMouseClicked(event -> {
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/TroddenSpade/huffman-compression").toURI());
            } catch (Exception e) {}
        });

        Button compress = new Button("compress");
        compress.setOnAction(e -> {
            stage.setScene(compressScene);
        });
        Button decompress = new Button("decompress");
        decompress.setOnAction(e -> {
            stage.setScene(decompressScene);
        });

        HBox hb = new HBox(10);
        VBox vb = new VBox();
        hb.getChildren().addAll(compress,decompress);
        vb.getChildren().addAll(title);
        hb.setAlignment(Pos.CENTER);
        vb.setAlignment(Pos.CENTER);
        startPane.setTop(vb);
        startPane.setCenter(hb);

    }

    public void compressScreen(Stage stage,Huffman h){

        Button selectDir = new Button("Select from");
        Button saveDir = new Button("compress to ");

        TextField selectedTextField = new TextField ();
        selectedTextField.setPrefWidth(600);
        HBox hb = new HBox();
        hb.getChildren().addAll(selectDir,selectedTextField);
        hb.setSpacing(10);
        hb.setPadding(new Insets(10,0,10,10));

        selectDir.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Text Files", "*.txt")
            );
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                System.out.println(selectedFile);
                setName(selectedFile.getName().split("\\.")[0]);
                selectedTextField.setText(selectedFile.toString());
            }
        });

        TextField saveTextField = new TextField ();
        saveTextField.setPrefWidth(590);
        HBox hb2 = new HBox();
        hb2.getChildren().addAll(saveDir,saveTextField);
        hb2.setSpacing(10);
        hb2.setPadding(new Insets(10,0,10,10));

        saveDir.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File("/Users/parsasam"));
            File selectedDirectory = directoryChooser.showDialog(stage);
            saveTextField.setText(selectedDirectory.getAbsolutePath());

        });


        Button back = new Button("Back <-");
        Button compressButton = new Button("Compress");

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(back,compressButton);
        hb3.setSpacing(100);
        hb3.setAlignment(Pos.CENTER);
        back.setOnAction(e -> {
           stage.setScene(startScene);

        });

        compressButton.setOnAction(e -> {
            try {
                h.compress(selectedTextField.getText(), saveTextField.getText()+"/"+name);
            }catch (Exception ex){
                System.out.println(ex);
            }
        });

        BorderPane pane = new BorderPane();
        compressScene =  new Scene(pane, WIDTH, HEIGHT);

        pane.setTop(hb);
        pane.setCenter(hb2);
        pane.setBottom(hb3);
    }


    public void extractScreen(Stage stage,Huffman h){

        Button selectDir = new Button("Select file");
        Button saveDir = new Button("decompress to ");

        TextField selectedTextField = new TextField ();
        selectedTextField.setPrefWidth(600);
        HBox hb = new HBox();
        hb.getChildren().addAll(selectDir,selectedTextField);
        hb.setSpacing(10);
        hb.setPadding(new Insets(10,0,10,10));

        selectDir.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
//            fileChooser.getExtensionFilters().addAll(
//                    new ExtensionFilter("Text Files", "*.txt")
//            );
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                System.out.println(selectedFile);
                selectedTextField.setText(selectedFile.toString());
                setName(selectedFile.getName().split("\\.")[0] + ".txt");
            }
        });

        TextField saveTextField = new TextField ();
        saveTextField.setPrefWidth(590);
        HBox hb2 = new HBox();
        hb2.getChildren().addAll(saveDir,saveTextField);
        hb2.setSpacing(10);
        hb2.setPadding(new Insets(10,0,10,10));

        saveDir.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File("/Users/parsasam"));
            File selectedDirectory = directoryChooser.showDialog(stage);
            saveTextField.setText(selectedDirectory.getAbsolutePath());
            System.out.println(selectedDirectory.getAbsolutePath());

        });


        Button back = new Button("Back <-");
        Button compressButton = new Button("Decompress");

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(back,compressButton);
        hb3.setSpacing(100);
        hb3.setAlignment(Pos.CENTER);
        back.setOnAction(e -> {
            stage.setScene(startScene);

        });

        compressButton.setOnAction(e -> {
            try {
                h.extract(selectedTextField.getText(), saveTextField.getText()+"/_"+name);
            }catch (Exception ex){
                System.out.println(ex);
            }
        });

        BorderPane pane = new BorderPane();
        decompressScene =  new Scene(pane, WIDTH, HEIGHT);

        pane.setTop(hb);
        pane.setCenter(hb2);
        pane.setBottom(hb3);
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
