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

public class InsertBookView extends View {

    // other private data
    private final int labelWidth = 120;
    private final int labelHeight = 25;

    // GUI components
    protected Button submit;
    protected Button doneButton;

    protected TextField barcode;
    protected TextField title;
    protected TextField author1;
    protected TextField author2;
    protected TextField author3;
    protected TextField author4;
    protected TextField publisher;
    protected TextField pubYear;
    protected TextField isbn;
    protected TextField sugPrice;
    protected TextArea notes;

    // other GUI Components here
    protected MessageView statusLog;
    protected DropShadow shadow = new DropShadow();

    protected String keyToSendWithData = "";

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public InsertBookView(IModel Book) {

        super(Book, "InsertBookView");

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

        keyToSendWithData = "BookData";
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

        Text prompt = new Text("Enter All Book Information");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text barLabel = new Text(" Barcode : ");
        barLabel.setFont(myFont);
        barLabel.setWrappingWidth(150);
        barLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(barLabel, 0, 1);
        grid.add(barcode = new TextField(), 1, 1);

        Text titleLabel = new Text(" Title : ");
        titleLabel.setFont(myFont);
        titleLabel.setWrappingWidth(150);
        titleLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(titleLabel, 0, 2);
        grid.add(title = new TextField(), 1, 2);

        Text a1Label = new Text(" Author 1 : ");
        a1Label.setFont(myFont);
        a1Label.setWrappingWidth(150);
        a1Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(a1Label, 0, 3);
        grid.add(author1 = new TextField(), 1, 3);

        Text a2Label = new Text(" Author 2 : ");
        a2Label.setFont(myFont);
        a2Label.setWrappingWidth(150);
        a2Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(a2Label, 0, 4);
        grid.add(author2 = new TextField(), 1, 4);

        Text a3Label = new Text(" Author 3 : ");
        a3Label.setFont(myFont);
        a3Label.setWrappingWidth(150);
        a3Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(a3Label, 0, 5);
        grid.add(author3 = new TextField(), 1, 5);

        Text a4Label = new Text(" Author 4 : ");
        a4Label.setFont(myFont);
        a4Label.setWrappingWidth(150);
        a4Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(a4Label, 0, 6);
        grid.add(author4 = new TextField(), 1, 6);

        Text pubLabel = new Text(" Publisher : ");
        pubLabel.setFont(myFont);
        pubLabel.setWrappingWidth(150);
        pubLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(pubLabel, 0, 7);
        grid.add(publisher = new TextField(), 1, 7);

        Text pubYearLabel = new Text(" Year of Publication : ");
        pubYearLabel.setFont(myFont);
        pubYearLabel.setWrappingWidth(150);
        pubYearLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(pubYearLabel, 0, 8);
        grid.add(pubYear = new TextField(), 1, 8);

        Text isbnLabel = new Text(" ISBN : ");
        isbnLabel.setFont(myFont);
        isbnLabel.setWrappingWidth(150);
        isbnLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(isbnLabel, 0, 9);
        grid.add(isbn = new TextField(), 1, 9);

        Text sugPriceLabel = new Text(" Suggested Price : ");
        sugPriceLabel.setFont(myFont);
        sugPriceLabel.setWrappingWidth(150);
        sugPriceLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(sugPriceLabel, 0, 10);
        grid.add(sugPrice = new TextField(), 1, 10);

        Text notesLabel = new Text(" Notes : ");
        notesLabel.setFont(myFont);
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(notesLabel, 0, 11);
        notes = new TextArea();
        notes.setPrefColumnCount(20);
        notes.setPrefRowCount(5);
        notes.setWrapText(true);
        grid.add(notes, 1, 11);

        ImageView icon = new ImageView(new Image("/images/pluscolor.png"));
        icon.setFitHeight(15);
        icon.setFitWidth(15);
        submit = new Button("Submit", icon);
        submit.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        submit.setOnAction((ActionEvent e) -> {
            clearErrorMessage();
            clearOutlines();

            String barcodeText = (String) barcode.getText();
            String titleText = (String) title.getText();
            String a1Text = (String) author1.getText();
            String a2Text = (String) author2.getText();
            String a3Text = (String) author3.getText();
            String a4Text = (String) author4.getText();
            String pubText = (String) publisher.getText();
            String pubYearText = (String) pubYear.getText();
            String isbnText = (String) isbn.getText();
            String sugPriceText = (String) sugPrice.getText();
            String notesText = (String) notes.getText();

            // Validate the category strings to make sure they are integers
            Properties props = new Properties();
            if(barcodeText.length() != 0 && barcodeText.length() < 6 && barcodeText.matches("[0-9]+")) {
                props.setProperty("Barcode", barcodeText);
                if(titleText.length() != 0) {
                    props.setProperty("Title", titleText);
                    if(a1Text.length() != 0) {
                        props.setProperty("Author1", a1Text);
                        if (pubYearText.length() != 0 && pubYearText.length() < 5 && pubYearText.matches("[0-9]+")) {
                            props.setProperty("YearOfPublication", pubYearText);
                            if (isbnText.length() < 10 && pubYearText.matches("[0-9]+")) {
                                props.setProperty("Author2", a2Text);
                                props.setProperty("Author3", a3Text);
                                props.setProperty("Author4", a4Text);
                                props.setProperty("Publisher", pubText);
                                props.setProperty("ISBN", isbnText);
                                props.setProperty("BookCondition", "Good");
                                props.setProperty("SuggestedPrice", sugPriceText);
                                props.setProperty("Notes", notesText);
                                props.setProperty("Status", "Active");
                                myModel.stateChangeRequest(keyToSendWithData, props);
                            } else {
                                displayErrorMessage("ERROR: Enter a proper value for the ISBN!");
                            }
                        } else {
                            displayErrorMessage("ERROR: Enter a proper value for the publication year!");
                        }
                    }
                    else {
                        displayErrorMessage("ERROR: Enter the first author!");
                    }
                }
                else {
                    displayErrorMessage("ERROR: Enter a title!");
                }
            }
            else {
                displayErrorMessage("ERROR: Enter a proper value for the barcode!");
            }
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

    }
    //-------------------------------------------------------
    protected void clearOutlines() {
        barcode.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        title.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        author1.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        author2.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        author3.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        author4.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        publisher.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        pubYear.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        isbn.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        sugPrice.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        notes.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
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
