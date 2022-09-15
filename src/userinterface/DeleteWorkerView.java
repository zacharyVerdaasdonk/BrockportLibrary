package userinterface;

import exception.InvalidPrimaryKeyException;
import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.Node;
import model.BookBarcodePrefix;

import java.util.Properties;

public class DeleteWorkerView extends View {

    // other private data
    private final int labelWidth = 120;
    private final int labelHeight = 25;

    // GUI components
    protected Button submit;
    protected Button doneButton;

    protected TextField bannerId;
    protected TextField firstName;
    protected TextField lastName;

    // other GUI Components here
    protected MessageView statusLog;
    protected DropShadow shadow = new DropShadow();

    protected String keyToSendWithData = "";

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public DeleteWorkerView(IModel Book) {

        super(Book, "DeleteWorkerView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContents());

        container.getChildren().add(createStatusLog("             "));
        container.getChildren().add(new Text());
        container.getChildren().add(new Text());

        getChildren().add(container);

        populateFields();

        doneButton.requestFocus();

        keyToSendWithData = "WorkerData";
        myModel.subscribe("TransactionError", this);

    }

    // Create the labels and fields
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

    //------------------------------------------------------------
    protected VBox createFormContents() {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);

        Text prompt = new Text("Are you sure you want to delete this worker?");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text banLabel = new Text(" BannerId : ");
        banLabel.setFont(myFont);
        banLabel.setWrappingWidth(150);
        banLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(banLabel, 0, 1);
        grid.add(bannerId = new TextField(), 1, 1);

        Text fnLabel = new Text(" First Name : ");
        fnLabel.setFont(myFont);
        fnLabel.setWrappingWidth(150);
        fnLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(fnLabel, 0, 2);
        grid.add(firstName = new TextField(), 1, 2);

        Text lnLabel = new Text(" Last Name : ");
        lnLabel.setFont(myFont);
        lnLabel.setWrappingWidth(150);
        lnLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(lnLabel, 0, 3);
        grid.add(lastName = new TextField(), 1, 3);

        ImageView icon = new ImageView(new Image("/images/remove_icon.png"));
        icon.setFitHeight(15);
        icon.setFitWidth(15);
        submit = new Button("Delete", icon);
        submit.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        submit.setOnAction((ActionEvent e) -> {
            clearErrorMessage();
            clearOutlines();

            Properties props = new Properties();
            props.setProperty("Status", "Inactive");
            myModel.stateChangeRequest(keyToSendWithData, props);

        });

        submit.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            submit.setEffect(new DropShadow());
        });
        submit.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            submit.setEffect(null);
        });

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);

        buttonContainer.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            buttonContainer.setStyle("-fx-background-color: GOLD");
        });
        buttonContainer.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            buttonContainer.setStyle("-fx-background-color: SLATEGREY");
        });
        icon = new ImageView(new Image("/images/return.png"));
        icon.setFitHeight(15);
        icon.setFitWidth(15);
        doneButton = new Button("Return", icon);
        doneButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });
        doneButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            doneButton.setEffect(new DropShadow());
        });
        doneButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            doneButton.setEffect(null);
        });
        buttonContainer.getChildren().add(submit);
        buttonContainer.getChildren().add(doneButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(buttonContainer);

        return vbox;
    }

    // Create the status log field
    //-------------------------------------------------------------
    private MessageView createStatusLog(String initialMessage) {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields() {
        String banText = (String) myModel.getState("BannerId");
        bannerId.setText(banText);
        bannerId.setEditable(false);
        String firstText = (String) myModel.getState("FirstName");
        firstName.setText(firstText);
        firstName.setEditable(false);
        String lastText = (String) myModel.getState("LastName");
        lastName.setText(lastText);
        lastName.setEditable(false);
    }
    //-------------------------------------------------------
    protected void clearOutlines() {
        bannerId.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
    }

    //---------------------------------------------------------
    public void updateState(String key, Object value) {
        if (key.equals("TransactionError") == true) {
            // display the passed text
            String message = (String) value;
            if ((message.startsWith("Err")) || (message.startsWith("ERR"))) {
                displayErrorMessage(message);
            } else {
                displayMessage(message);
            }
        }
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message) {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display Informational message
     */
    //----------------------------------------------------------
    public void displayMessage(String message) {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }
}
