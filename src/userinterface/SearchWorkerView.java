package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Properties;

// project imports
import impresario.IModel;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SearchWorkerView extends View {
    // GUI components
    protected TextField workerFirstName;
    protected TextField workerLastName;

    protected Button submitButton;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public SearchWorkerView(IModel sislot) {
        // sislot - Search ISLO Transaction (any controller
        // that requires ISLOs to be searched for)
        super(sislot, "SearchWorkerView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionError", this);
    }

    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle() {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text("       BROCKPORT LIBRARY SYSTEM          ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(500);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    //-------------------------------------------------------------
    protected String getActionText() {
        return "Enter Worker First & Last Name:";
    }

    // Create the main form content
    //-------------------------------------------------------------
    public VBox createFormContent() {
        VBox vbox = new VBox(10);

        Text blankText = new Text("  ");
        blankText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        blankText.setWrappingWidth(350);
        blankText.setTextAlignment(TextAlignment.CENTER);
        blankText.setFill(Color.WHITE);
        vbox.getChildren().add(blankText);

        Text prompt = new Text(getActionText());
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        prompt.setFont(Font.font("Copperplate", FontWeight.THIN, 18));
        vbox.getChildren().add(prompt);
        vbox.setAlignment(Pos.CENTER);

        GridPane grid0 = new GridPane();
        grid0.setAlignment(Pos.CENTER);
        grid0.setHgap(10);
        grid0.setVgap(10);
        grid0.setPadding(new Insets(0, 25, 10, 0));

        Text workerFNameLabel = new Text(" First Name: ");
        Font myFont = Font.font("Comic Sans", FontWeight.THIN, 16);
        workerFNameLabel.setFont(myFont);
        workerFNameLabel.setFill(Color.BLACK);
        workerFNameLabel.setWrappingWidth(150);
        workerFNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid0.add(workerFNameLabel, 0, 1);

        workerFirstName = new TextField();
        workerFirstName.setStyle("-fx-focus-color: darkgreen;");
        /*workerFirstName.setOnAction((ActionEvent e) -> {
            clearErrorMessage();
            Properties props = new Properties();
            String workerFNm = workerFirstName.getText();
            if (workerFNm.length() > 0) {
                props.setProperty("FirstName", workerFNm);
            }

            //myModel.stateChangeRequest("SearchWorkers", props);
        });*/
        grid0.add(workerFirstName, 1, 1);


        Text workerLNameLabel = new Text(" Last Name: ");
        workerLNameLabel.setFont(myFont);
        workerLNameLabel.setFill(Color.BLACK);
        workerLNameLabel.setWrappingWidth(150);
        workerLNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid0.add(workerLNameLabel, 0, 2);

        workerLastName = new TextField();
        workerLastName.setStyle("-fx-focus-color: darkgreen;");
        /*workerLastName.setOnAction((ActionEvent e) -> {
            clearErrorMessage();
            Properties props = new Properties();
            String workerLNm = workerLastName.getText();
            if (workerLNm.length() > 0) {
                props.setProperty("LastName", workerLNm);
            }

            myModel.stateChangeRequest("SearchWorkers", props);
        });*/
        grid0.add(workerLastName, 1, 2);

        vbox.getChildren().add(grid0);


        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        doneCont.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            doneCont.setStyle("-fx-background-color: WHITE");
        });
        doneCont.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            doneCont.setStyle("-fx-background-color: WHITE");
        });
        ImageView icon = new ImageView(new Image("/images/searchcolor.png"));
        icon.setFitHeight(15);
        icon.setFitWidth(15);
        submitButton = new Button("Search",icon);
        submitButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        submitButton.setOnAction((ActionEvent e) -> {
            clearErrorMessage();
            Properties props = new Properties();
            String workerFNm = workerFirstName.getText();
            String workerLNm = workerLastName.getText();
            if (workerFNm.length() < 21) {
                props.setProperty("FirstName", workerFNm);
                if(workerLNm.length() < 21) {
                    props.setProperty("LastName", workerLNm);
                    myModel.stateChangeRequest("SearchWorkers", props);
                }
                else {
                    displayErrorMessage("ERROR: Enter a last name shorter than 20 characters!");
                }
            } else {
                displayErrorMessage("ERROR: Enter a first name shorter than 20 characters!");
            }

        });
        submitButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            submitButton.setEffect(new DropShadow());
        });
        submitButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            submitButton.setEffect(null);
        });
        doneCont.getChildren().add(submitButton);
        icon = new ImageView(new Image("/images/return.png"));
        icon.setFitHeight(15);
        icon.setFitWidth(15);
        doneButton = new Button("Return",icon);
        doneButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        doneButton.setOnAction((ActionEvent e) -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelSearchWorker", null);
        });
        doneButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            doneButton.setEffect(new DropShadow());
        });
        doneButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            doneButton.setEffect(null);
        });
        doneCont.getChildren().add(doneButton);

        vbox.getChildren().add(doneCont);
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }

    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields() {
        clearValues();
        clearErrorMessage();
    }

    //----------------------------------------------------------
    public void clearValues() {
        workerFirstName.clear();
        workerLastName.clear();
    }

    // Update method

    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        clearValues();
    }


    // Display error message

    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }


    // Display info message

    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }


    // Clear error message

    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
}
