package userinterface;

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
import model.Student;
import model.StudentCollection;
import model.StudentTableModel;

import java.util.Enumeration;
import java.util.Vector;

public class StudentsWithBooksCheckedOutCollectionView extends View{
    protected Text promptText;
    protected TableView<StudentTableModel> tableOfStudents;

    protected Button doneButton;

    protected MessageView statusLog;
    protected Text actionText;
    //--------------------------------------------------------------------------
    public StudentsWithBooksCheckedOutCollectionView(IModel mst){
        super(mst, "StudentsWithBooksCheckedOutCollectionView");
        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setStyle("-fx-background-color: WHITE");
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                                            "));

        container.getChildren().add(new Text("              ")); // To handle large error/info messages
        // for this rather narrow screen

        getChildren().add(container);
        populateFields();
    }
    //--------------------------------------------------------------------------
    protected void populateFields()
    {
        getEntryTableModelValues();
    }

    //--------------------------------------------------------------------------
    protected void getEntryTableModelValues() {

        ObservableList<StudentTableModel> tableData = FXCollections.observableArrayList();
        try {
            StudentCollection studentCollection = (StudentCollection)myModel.getState("StudentList");
            Vector entryList = (Vector)studentCollection.getState("Students");
            if (entryList.size() > 0) {

                Enumeration entries = entryList.elements();

                while (entries.hasMoreElements()) {

                    Student nextStudent = (Student)entries.nextElement();
                    Vector<String> view = nextStudent.getEntryListView();

                    // add this list entry to the list
                    StudentTableModel nextTableRowData = new StudentTableModel(view);
                    tableData.add(nextTableRowData);

                }
                if(entryList.size() == 1)
                    actionText.setText(entryList.size()+" Matching Student Found!");
                else
                    actionText.setText(entryList.size()+" Matching Students Found!");

                actionText.setFill(Color.LIGHTGREEN);
            }
            else {
                actionText.setText("No Matching Students Found!");
                actionText.setFill(Color.FIREBRICK);
            }

            tableOfStudents.setItems(tableData);
        }
        catch (Exception e) {//SQLException e) {
            // Need to handle this exception
            System.out.println(e);
            e.printStackTrace();
        }

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

    //---------------------------------------------------------
    protected String getPromptText() {
        return "";
    }


    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent() {
        VBox vbox = new VBox(10);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 25, 10, 0));

        promptText = new Text(getPromptText());//text set later
        promptText.setFont(Font.font("Copperplate", FontWeight.BOLD, 18));
        promptText.setWrappingWidth(350);
        promptText.setTextAlignment(TextAlignment.CENTER);
        vbox.getChildren().add(promptText);

        tableOfStudents = new TableView<StudentTableModel>();
        tableOfStudents.setEffect(new DropShadow());
        tableOfStudents.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-selection-bar: gold;");

        TableColumn bannerIDColumn = new TableColumn("BannerID") ;
        bannerIDColumn.setMinWidth(160);
        bannerIDColumn.setCellValueFactory(new PropertyValueFactory<StudentTableModel, String>("BannerId"));

        TableColumn fNameColumn = new TableColumn("First Name") ;
        fNameColumn.setMinWidth(160);
        fNameColumn.setCellValueFactory(new PropertyValueFactory<StudentTableModel, String>("FirstName"));

        TableColumn lNameColumn = new TableColumn("Last Name") ;
        lNameColumn.setMinWidth(160);
        lNameColumn.setCellValueFactory(new PropertyValueFactory<StudentTableModel, String>("LastName"));

        tableOfStudents.getColumns().addAll(bannerIDColumn, fNameColumn, lNameColumn);
        ImageView icon = new ImageView(new Image("/images/check.png"));
        icon.setFitHeight(15);
        icon.setFitWidth(15);

        icon = new ImageView(new Image("/images/return.png"));
        icon.setFitHeight(15);
        icon.setFitWidth(15);
        doneButton = new Button("Return", icon);
        doneButton.setGraphic(icon);
        doneButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        doneButton.setOnAction((ActionEvent e) -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelStudentBooksList", null);
        });
        doneButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            doneButton.setEffect(new DropShadow());
        });
        doneButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            doneButton.setEffect(null);
        });
        HBox btnContainer = new HBox(10);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnContainer.setStyle("-fx-background-color: WHITE");
        });
        btnContainer.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnContainer.setStyle("-fx-background-color: WHITE");
        });
        btnContainer.getChildren().add(doneButton);

        actionText = new Text();//text set later
        actionText.setFont(Font.font("Copperplate", FontWeight.BOLD, 18));
        actionText.setWrappingWidth(350);
        actionText.setTextAlignment(TextAlignment.CENTER);

        vbox.getChildren().add(grid);
        tableOfStudents.setPrefHeight(200);
        tableOfStudents.setMaxWidth(500);
        vbox.getChildren().add(tableOfStudents);
        vbox.getChildren().add(btnContainer);
        vbox.getChildren().add(actionText);
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }

    //--------------------------------------------------------------------------
    protected void processStudentSelected()
    {}

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

    //--------------------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }


    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Display info message
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
