
package org.utl.myspa.gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Alexis
 */
public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("/org/utl/myspa/gui/fxml/Login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        primaryStage.setTitle("Login - MySpa");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }
    
}
