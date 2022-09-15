package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Properties;

public class StudentsWithBooksCheckedOutView extends View{
    // other private data
    private final int labelWidth = 120;
    private final int labelHeight = 25;

    // GUI components
    protected Button listStudsWtBooksChkdOutBtn;
    protected Button doneButton;

    // other GUI Components here
    protected MessageView statusLog;
    protected DropShadow shadow = new DropShadow();

    protected String keyToSendWithData = "StudentsWithBooksCheckedOutData";

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public StudentsWithBooksCheckedOutView(IModel Book){
        super(Book, "StudentsWithBooksCheckedOutView");

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

        keyToSendWithData = "StudentsWithBooksCheckedOutData";
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
    private VBox createFormContents()
    {
        VBox vbox = new VBox(10);

        Text blankText = new Text("  ");
        blankText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        blankText.setWrappingWidth(350);
        blankText.setTextAlignment(TextAlignment.CENTER);
        blankText.setFill(Color.WHITE);
        vbox.getChildren().add(blankText);

        Text prompt = new Text("");
        prompt.setFont(Font.font("Copperplate", FontWeight.BOLD, 18));
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        vbox.getChildren().add(prompt);
        vbox.setAlignment(Pos.CENTER);


        HBox reportCont = new HBox(10);
        reportCont.setAlignment(Pos.CENTER);
        reportCont.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            reportCont.setStyle("-fx-background-color: WHITE");
        });
        reportCont.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            reportCont.setStyle("-fx-background-color: WHITE");
        });

        ImageView icon = new ImageView(new Image("/images/return.png"));
        icon.setFitHeight(15);
        icon.setFitWidth(15);

        listStudsWtBooksChkdOutBtn = new Button("List Students With Books Checked Out");
        listStudsWtBooksChkdOutBtn.setFont(Font.font("Comic Sans", FontWeight.MEDIUM, 14));
        listStudsWtBooksChkdOutBtn.setOnAction((ActionEvent e) -> {
            clearErrorMessage();
            Properties props = new Properties();
            myModel.stateChangeRequest(keyToSendWithData, props);
        });
        listStudsWtBooksChkdOutBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            listStudsWtBooksChkdOutBtn.setEffect(new DropShadow());
        });
        listStudsWtBooksChkdOutBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            listStudsWtBooksChkdOutBtn.setEffect(null);
        });
        listStudsWtBooksChkdOutBtn.setScaleX(1.65);
        listStudsWtBooksChkdOutBtn.setScaleY(1.65);
        reportCont.getChildren().add(listStudsWtBooksChkdOutBtn);
        vbox.getChildren().add(reportCont);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        doneCont.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            doneCont.setStyle("-fx-background-color: WHITE");
        });
        doneCont.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            doneCont.setStyle("-fx-background-color: WHITE");
        });


        Text blankText3 = new Text("  ");
        blankText3.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        blankText3.setWrappingWidth(350);
        blankText3.setTextAlignment(TextAlignment.CENTER);
        blankText3.setFill(Color.WHITE);
        vbox.getChildren().add(blankText3);

        doneButton = new Button("Return", icon);
        doneButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        doneButton.setOnAction((ActionEvent e) -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelListStudents", null);
        });
        doneButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            doneButton.setEffect(new DropShadow());
        });
        doneButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            doneButton.setEffect(null);
        });
        doneCont.getChildren().add(doneButton);

        vbox.getChildren().add(doneCont);
        clearOutlines();

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

    }
    //-------------------------------------------------------
    protected void clearOutlines() {
    }

    //---------------------------------------------------------
    public void updateState(String key, Object value) {
        System.out.println("Key: " + key + "; value: " + value);
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
