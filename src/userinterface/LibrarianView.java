
// specify the package
package userinterface;

// system imports
import java.text.NumberFormat;
import java.util.Properties;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

// project imports
import impresario.IModel;

/** The class containing the Librarian View for the Library application */
//==============================================================
public class LibrarianView extends View {

    // GUI components
    private Button insertNewBookButton;
    private Button modifyBookButton;
    private Button deleteBookButton;

    private Button insertStudentButton;
    private Button modifyStudentButton;
    private Button deleteStudentButton;

    private Button insertWorkerButton;
    private Button modifyWorkerButton;
    private Button deleteWorkerButton;

    private Button checkOutBookButton;
    private Button checkInBookButton;

    private Button runDelCheckButton;
    private Button booksCheckedOutButton;

    private Button studentWithBookButton;

    // Other buttons
    private Button doneButton;

    // For showing error message
    private MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public LibrarianView(IModel librarian) {
        super(librarian, "LibrarianView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create a Node (Text) for showing the title
        container.getChildren().add(createTitle());

        // create a Node (GridPane) for showing data entry fields
        container.getChildren().add(createFormContents());

        // Error message area
        container.getChildren().add(createStatusLog("                          "));

        getChildren().add(container);

        populateFields();

        // STEP 0: Be sure you tell your model what keys you are interested in
        myModel.subscribe("TransactionError", this);
    }

    // Create the label (Text) for the title of the screen
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

    // Create the main form contents
    //-------------------------------------------------------------
    private VBox createFormContents() {

        VBox container = new VBox(15);
        // create the buttons, listen for events, add them to the container
        container.setPadding(new Insets(10, 50, 50, 50));

        HBox bookCont = new HBox(10);
        bookCont.setAlignment(Pos.CENTER);

        insertNewBookButton = new Button("Insert a Book");
        insertNewBookButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        insertNewBookButton.setOnAction((ActionEvent e) -> {
            myModel.stateChangeRequest("InsertBook", null);
        });

        modifyBookButton = new Button("Modify a Book");
        modifyBookButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        modifyBookButton.setOnAction((ActionEvent e) -> {
            myModel.stateChangeRequest("ModifyBook", null);
        });

        deleteBookButton = new Button("Delete a Book");
        deleteBookButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        deleteBookButton.setOnAction((ActionEvent e) -> {
            myModel.stateChangeRequest("DeleteBook", null);
        });

        bookCont.getChildren().add(insertNewBookButton);
        bookCont.getChildren().add(modifyBookButton);
        bookCont.getChildren().add(deleteBookButton);

        HBox studentCont = new HBox(10);
        studentCont.setAlignment(Pos.CENTER);

        insertStudentButton = new Button("Insert a Student Borrower");
        insertStudentButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        insertStudentButton.setOnAction((ActionEvent e) -> {
            myModel.stateChangeRequest("InsertStudent", null);
        });

        modifyStudentButton = new Button("Modify a Student Borrower");
        modifyStudentButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        modifyStudentButton.setOnAction((ActionEvent e) -> {
            myModel.stateChangeRequest("ModifyStudent", null);
        });

        deleteStudentButton = new Button("Delete a Student Borrower");
        deleteStudentButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        deleteStudentButton.setOnAction((ActionEvent e) -> {
            myModel.stateChangeRequest("DeleteStudent", null);
        });

        studentCont.getChildren().add(insertStudentButton);
        studentCont.getChildren().add(modifyStudentButton);
        studentCont.getChildren().add(deleteStudentButton);

        HBox workerCont = new HBox(10);
        workerCont.setAlignment(Pos.CENTER);

        insertWorkerButton = new Button("Insert a Worker");
        insertWorkerButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        insertWorkerButton.setOnAction((ActionEvent e) -> {
            myModel.stateChangeRequest("InsertWorker", null);
        });

        modifyWorkerButton = new Button("Modify a Worker");
        modifyWorkerButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        modifyWorkerButton.setOnAction((ActionEvent e) -> {
            myModel.stateChangeRequest("ModifyWorker", null);
        });

        deleteWorkerButton = new Button("Delete a Worker");
        deleteWorkerButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        deleteWorkerButton.setOnAction((ActionEvent e) -> {
            myModel.stateChangeRequest("DeleteWorker", null);
        });

        workerCont.getChildren().add(insertWorkerButton);
        workerCont.getChildren().add(modifyWorkerButton);
        workerCont.getChildren().add(deleteWorkerButton);

        HBox checkCont = new HBox(10);
        checkCont.setAlignment(Pos.CENTER);

        checkOutBookButton = new Button("Check out a Book");
        checkOutBookButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        checkOutBookButton.setOnAction((ActionEvent e) -> {
            myModel.stateChangeRequest("CheckOutBook", null);
        });

        checkInBookButton = new Button("Check in a Book");
        checkInBookButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        checkInBookButton.setOnAction((ActionEvent e) -> {
            myModel.stateChangeRequest("CheckInBook", null);
        });

        runDelCheckButton = new Button("Run Delinquency Check");
        runDelCheckButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        runDelCheckButton.setOnAction((ActionEvent e) -> {
            myModel.stateChangeRequest("RunDelCheck", null);
        });

        checkCont.getChildren().add(checkOutBookButton);
        checkCont.getChildren().add(checkInBookButton);
        checkCont.getChildren().add(runDelCheckButton);

        HBox check2Cont = new HBox(10);
        check2Cont.setAlignment(Pos.CENTER);

        booksCheckedOutButton = new Button("See Books Currently Checked Out");
        booksCheckedOutButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        booksCheckedOutButton.setOnAction((ActionEvent e) -> {
            myModel.stateChangeRequest("BooksCheckedOut", null);
        });

        studentWithBookButton = new Button("See Students With Books Checked Out");
        studentWithBookButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        studentWithBookButton.setOnAction((ActionEvent e) -> {
            myModel.stateChangeRequest("StudentsWithBooks", null);
        });

        check2Cont.getChildren().add(booksCheckedOutButton);
        check2Cont.getChildren().add(studentWithBookButton);

        HBox bottomButtons = new HBox(10);
        bottomButtons.setAlignment(Pos.CENTER);

        doneButton = new Button("DONE");
        doneButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        doneButton.setOnAction((ActionEvent e) -> {
            myModel.stateChangeRequest("Done", null);
        });
        doneButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            statusLog.displayInfoMessage("Close Application");
        });
        doneButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            clearErrorMessage();
        });

        bottomButtons.getChildren().add(doneButton);

        container.getChildren().add(bookCont);
        container.getChildren().add(studentCont);
        container.getChildren().add(workerCont);
        container.getChildren().add(checkCont);
        container.getChildren().add(check2Cont);
        container.getChildren().add(bottomButtons);

        return container;
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

    //---------------------------------------------------------
    public void updateState(String key, Object value) {
        // STEP 6: Be sure to finish the end of the 'perturbation'
        // by indicating how the view state gets updated.
        if (key.equals("TransactionError") == true) {
            // display the passed text
            displayErrorMessage((String)value);
        }
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
}

