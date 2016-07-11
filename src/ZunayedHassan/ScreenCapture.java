package ZunayedHassan;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

/**
 *
 * @author Zunayed Hassan
 */
public class ScreenCapture extends Pane {
    public static final String WINDOW_TITLE    = "Screen Capture";
    public static final int    WINDOW_WIDTH    = 800;
    public static final int    WINDOW_HEIGHT   = 600;
    public static final String APP_ICON        = "icon.png";
    public static final String EXTENSION       = "png";
    
    public String OutputFileNamePrefix = "Snapshot";
    
    private Alert _alert = null;
    
    public ScreenCapture() {
        super.setBackground(this._getBackground());
        this._showDialog("Press [CTRL + S] to take screenshot or press [ESCAPE] key to exit the program", "Startup Tips", "Getting Started", AlertType.INFORMATION);
    }
    
    private void _setWindowTransparent(Scene scene, Stage stage) {
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
    }
    
    private Background _getBackground() {
        return (new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    public void Initialize(Scene scene, Stage stage) {
        this._setWindowTransparent(scene, stage);
        scene.setOnKeyPressed(this._onKeyPressed);
    }
    
    EventHandler<KeyEvent> _onKeyPressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            // Ctrl + S
            if (event.isControlDown() && (event.getCode() == KeyCode.S)) {
                try {
                    _captureScreenshot();
                }
                catch (AWTException exception) {
                    Logger.getLogger(ScreenCapture.class.getName()).log(Level.SEVERE, null, exception);
                }
                catch (IOException exception) {
                    Logger.getLogger(ScreenCapture.class.getName()).log(Level.SEVERE, null, exception);
                }
                finally {
                    _exitProgram();
                }
            }
            // Escape
            else if (event.getCode() == KeyCode.ESCAPE) {
                _exitProgram();
            }
        }
    };
    
    private void _captureScreenshot() throws AWTException, IOException {
        // Take the Screenshot
        BufferedImage screencapture = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        
        // Save the image file
        String outputFileName = this._getGeneratedOutputFileName();
        
        File file = new File(outputFileName);
        ImageIO.write(screencapture, EXTENSION.toLowerCase(), file);
        
        Path currentRelativePath = Paths.get("");
        String absolutePath = currentRelativePath.toAbsolutePath().toString() + "\\";
        
        // Show the message
        this._showDialog("Your screenshot is saved to \'" + absolutePath + outputFileName + "\'", "Message", "Success", AlertType.INFORMATION);
    }
    
    private String _getGeneratedOutputFileName() {
        Date currentDateTime = new Date();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat ("E yyyy.MM.dd hh-mm-ss a zzz");
        
        return (this.OutputFileNamePrefix + " " + dateTimeFormat.format(currentDateTime) + "." + EXTENSION);
    }
    
    private void _exitProgram() {
        Platform.exit();
    }
    
    private void _showDialog(String message, String title, String headerText, AlertType alertType) {
        this._alert = new Alert(alertType);
        this._alert.setTitle(title);
        this._alert.setHeaderText(headerText);
        this._alert.setContentText(message);
        
        Stage alertStage = (Stage) this._alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(this.getClass().getResource(APP_ICON).toString()));
        
        this._alert.showAndWait();
    }
}
