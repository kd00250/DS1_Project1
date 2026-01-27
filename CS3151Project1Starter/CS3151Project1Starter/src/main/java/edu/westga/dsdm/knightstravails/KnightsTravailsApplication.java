package edu.westga.dsdm.knightstravails;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Launches the knight's travails application.
 *
 * @author DSDM
 */
public class KnightsTravailsApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(KnightsTravailsApplication.class.getResource("view/kinights-travails-gui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Knight's Travails by Your Name");
        stage.setScene(scene);
        stage.show();
    }

    static void main(String[] args) {
        launch(KnightsTravailsApplication.class, args);
    }
}
