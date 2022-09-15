// system imports

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;

// project imports
import event.Event;
import javafx.scene.image.Image;

import userinterface.MainStageContainer;
import userinterface.WindowPosition;

import model.Librarian;

public class LibrarianMain extends Application {

    private Librarian lib;		// the main behavior for the application

    /** Main stage of the application */
    private Stage mainStage;

    // start method for this class, the main application object
    //----------------------------------------------------------
    public void start(Stage primaryStage) {
        // Create the top-level container (main frame) and add contents to it.
        MainStageContainer.setStage(primaryStage, "Brockport Library System");
        mainStage = MainStageContainer.getInstance();
        mainStage.getIcons().add(new Image("/images/all_in_one_logo.png")); // set small icon in top left to bport icon
        // Finish setting up the stage (ENABLE THE GUI TO BE CLOSED USING THE TOP RIGHT
        // 'X' IN THE WINDOW), and show it.
        mainStage.setOnCloseRequest(new EventHandler <javafx.stage.WindowEvent>() {
            @Override
            public void handle(javafx.stage.WindowEvent event) {
                System.exit(0);
            }
        });

        try {
            lib = new Librarian();
        }
        catch(Exception exc) {
            System.err.println("Could not create Librarian");
            new Event(Event.getLeafLevelClassName(this), "LibrarianMain.<init>", "Unable to create Librarian object", Event.ERROR);
            exc.printStackTrace();
        }

        WindowPosition.placeCenter(mainStage);
        mainStage.show();
    }

    /**
     * The "main" entry point for the application. Carries out actions to
     * set up the application
     */
    public static void main(String args []) {
        launch(args);
    }
}
