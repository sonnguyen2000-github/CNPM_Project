package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("LOGIN");
        primaryStage.setScene(new Scene(root, 334, 192));
        primaryStage.setResizable(false);
        Login controller = loader.getController();
        controller.setOnClose();
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
