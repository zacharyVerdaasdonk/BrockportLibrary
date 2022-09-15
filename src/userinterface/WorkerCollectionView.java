package userinterface;

// system imports
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;

import model.Worker;
import model.WorkerCollection;
import model.WorkerTableModel;

public class WorkerCollectionView extends View {
    protected Text promptText;
    protected TableView<WorkerTableModel> tableOfWorkers;

    protected Button doneButton;
    protected Button submit;

    protected MessageView statusLog;
    protected Text actionText;

    //--------------------------------------------------------------------------
    public WorkerCollectionView(IModel mst) {
        // mst - model - Modify Semester Transaction acronym
        super(mst, "WorkerCollectionView");

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
        tableOfWorkers.getSelectionModel().select(0); //autoselect first element
    }
    //--------------------------------------------------------------------------
    protected void populateFields()
    {
        getEntryTableModelValues();
    }

    //--------------------------------------------------------------------------
    protected void getEntryTableModelValues() {

        ObservableList<WorkerTableModel> tableData = FXCollections.observableArrayList();
        try {
            WorkerCollection workerCollection = (WorkerCollection)myModel.getState("WorkerList");

            Vector entryList = (Vector)workerCollection.getState("Workers");

            if (entryList.size() > 0) {

                Enumeration entries = entryList.elements();

                while (entries.hasMoreElements() == true) {

                    Worker nextWorker = (Worker)entries.nextElement();
                    Vector<String> view = nextWorker.getEntryListView();

                    // add this list entry to the list
                    WorkerTableModel nextTableRowData = new WorkerTableModel(view);
                    tableData.add(nextTableRowData);

                }
                if(entryList.size() == 1)
                    actionText.setText(entryList.size()+" Matching Worker Found!");
                else
                    actionText.setText(entryList.size()+" Matching Workers Found!");

                actionText.setFill(Color.LIGHTGREEN);
            }
            else {
                actionText.setText("No matching Workers Found!");
                actionText.setFill(Color.FIREBRICK);
            }

            tableOfWorkers.setItems(tableData);
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
        return "Select a worker:";
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

        tableOfWorkers = new TableView<WorkerTableModel>();
        tableOfWorkers.setEffect(new DropShadow());
        tableOfWorkers.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-selection-bar: gold;");
        tableOfWorkers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn workerFNameColumn = new TableColumn("First Name") ;
        workerFNameColumn.setMinWidth(160);
        workerFNameColumn.setCellValueFactory(new PropertyValueFactory<WorkerTableModel, String>("FirstName"));

        TableColumn workerLNameColumn = new TableColumn("Last Name") ;
        workerLNameColumn.setMinWidth(160);
        workerLNameColumn.setCellValueFactory(new PropertyValueFactory<WorkerTableModel, String>("LastName"));

        TableColumn bannerIdColumn = new TableColumn("Banner Id") ;
        bannerIdColumn.setMinWidth(160);
        bannerIdColumn.setCellValueFactory(new PropertyValueFactory<WorkerTableModel, String>("BannerId"));

        tableOfWorkers.getColumns().addAll(bannerIdColumn, workerFNameColumn, workerLNameColumn);

        tableOfWorkers.setOnMousePressed((MouseEvent event) -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
                processWorkerSelected();
            }
        });
        ImageView icon = new ImageView(new Image("/images/check.png"));
        icon.setFitHeight(15);
        icon.setFitWidth(15);

        submit = new Button("Submit", icon);
        submit.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        submit.setOnAction((ActionEvent e) -> {
            clearErrorMessage();
            processWorkerSelected();
        });

        submit.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            submit.setEffect(new DropShadow());
        });
        submit.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            submit.setEffect(null);
        });

        icon = new ImageView(new Image("/images/return.png"));
        icon.setFitHeight(15);
        icon.setFitWidth(15);
        doneButton = new Button("Return", icon);
        doneButton.setGraphic(icon);
        doneButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        doneButton.setOnAction((ActionEvent e) -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelWorkerList", null);
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
        btnContainer.getChildren().add(submit);
        btnContainer.getChildren().add(doneButton);

        actionText = new Text();//text set later
        actionText.setFont(Font.font("Copperplate", FontWeight.BOLD, 18));
        actionText.setWrappingWidth(350);
        actionText.setTextAlignment(TextAlignment.CENTER);

        vbox.getChildren().add(grid);
        tableOfWorkers.setPrefHeight(200);
        tableOfWorkers.setMaxWidth(500);
        vbox.getChildren().add(tableOfWorkers);
        vbox.getChildren().add(btnContainer);
        vbox.getChildren().add(actionText);
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }

    //--------------------------------------------------------------------------
    protected void processWorkerSelected()
    {
        WorkerTableModel selectedItem = tableOfWorkers.getSelectionModel().getSelectedItem();

        if(selectedItem != null) {
            String selectedId = selectedItem.getBannerId();

            myModel.stateChangeRequest("WorkerSelected", selectedId);
        }
    }

    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {

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
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
}
