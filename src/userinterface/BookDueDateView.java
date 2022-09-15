// tabs=4
//************************************************************
//	COPYRIGHT 2021, Kyle D. Adams, Matthew E. Morgan
//    and Sandeep Mitra, State University of New York.
//   - Brockport (SUNY Brockport)
//	ALL RIGHTS RESERVED
//
// This file is the product of SUNY Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of SUNY Brockport.
//************************************************************
//

// specify the package

package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

// project imports
import impresario.IModel;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/** The class containing the Search ISLO View for the ISLO Data
 *  Management application
 */
//==============================================================
public class BookDueDateView extends View {

    // GUI components
    protected DatePicker datePick;


    protected Button submitButton;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public BookDueDateView(IModel sislot) {
        // sislot - Search ISLO Transaction (any controller
        // that requires ISLOs to be searched for)
        super(sislot, "SearchBookView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        container.getChildren().add(new Text(" "));

        getChildren().add(container);

        container.getChildren().add(new Text(" "));

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
        return "Select Due Date for Book:";
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

        Text datePickLabel = new Text(" Due Date: ");
        Font myFont = Font.font("Comic Sans", FontWeight.THIN, 16);
        datePickLabel.setFont(myFont);
        datePickLabel.setFill(Color.BLACK);
        datePickLabel.setWrappingWidth(150);
        datePickLabel.setTextAlignment(TextAlignment.RIGHT);
        grid0.add(datePickLabel, 0, 1);

        datePick = new DatePicker();
        datePick.setStyle("-fx-focus-color: darkgreen;");
        grid0.add(datePick, 1, 1);

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
        submitButton = new Button("Select",icon);
        submitButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        submitButton.setOnAction((ActionEvent e) -> {
            clearErrorMessage();
            Properties props = new Properties();
            LocalDate dateP = datePick.getValue();
            if(dateP != null) {
                String dateStr = dateP.toString();
                props.setProperty("DueDate", dateStr);
                myModel.stateChangeRequest("DateData", props);
            }
            else {
                displayErrorMessage("ERROR: Please select a date!");
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
            myModel.stateChangeRequest("CancelDueDate", null);
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
        //datePick.clear();

    }

    // Update method

    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        clearValues();
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


