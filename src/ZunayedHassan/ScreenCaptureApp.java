package ZunayedHassan;

import java.awt.AWTException;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/**
 *
 * @author Zunayed Hassan
 */
public class ScreenCaptureApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws AWTException, IOException {    
        ScreenCapture root = new ScreenCapture();

        Scene scene = new Scene(root, ScreenCapture.WINDOW_WIDTH, ScreenCapture.WINDOW_HEIGHT);
        root.Initialize(scene, primaryStage);

        primaryStage.setTitle(ScreenCapture.WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream(ScreenCapture.APP_ICON)));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
