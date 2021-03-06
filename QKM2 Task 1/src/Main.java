import controller.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import model.Inventory;

/**
 * This is the entry point to the QKM2 Task 1 Inventory Management Application for C482 Software I.
 * The javadoc HTML files are located in the javadoc folder, which is on the same level as src.
 * @author Brandon McCleary
 */
public class Main extends Application {

    @Override
    public void init(){ System.out.println("Starting init method..."); }

    @Override
    public void stop(){
        System.out.println("Stopping application...");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println("Starting start method...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainForm.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();
        mainController.inventory = new Inventory();
        mainController.addDummyData();
        primaryStage.setTitle("QKM2 Task1");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println("Starting main method...");
        launch(args);
    }
}
