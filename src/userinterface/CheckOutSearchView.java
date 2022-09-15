package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

import java.util.Properties;

public class CheckOutSearchView extends View {
    protected TextField firstName;
    protected TextField lastName;
    protected TextField bannerID;

    protected Button submitButton;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    public CheckOutSearchView(IModel sislot){
        // sislot - Search ISLO Transaction (any controller
        // that requires ISLOs to be searched for)
        super(sislot, "CheckOutSearchView");
        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        container.getChildren().add(new Text());

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
        return "If Known, Enter BannerID:";
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

        GridPane grid0 = new GridPane();
        grid0.setAlignment(Pos.CENTER);
        grid0.setHgap(10);
        grid0.setVgap(10);
        grid0.setPadding(new Insets(0, 25, 10, 0));

        Text bannerText = new Text(getActionText());
        //Font myFont = Font.font("Comic Sans", FontWeight.THIN, 16);
        bannerText.setFont(Font.font("Copperplate", FontWeight.THIN, 18));
        bannerText.setFill(Color.BLACK);
        bannerText.setWrappingWidth(400);
        bannerText.setTextAlignment(TextAlignment.LEFT);
        grid0.add(bannerText, 1, 0);

        Text bannerIDLabel = new Text(" BannerID: ");
        Font myFont = Font.font("Comic Sans", FontWeight.THIN, 16);
        bannerIDLabel.setFont(myFont);
        bannerIDLabel.setFill(Color.BLACK);
        bannerIDLabel.setWrappingWidth(150);
        bannerIDLabel.setTextAlignment(TextAlignment.RIGHT);
        grid0.add(bannerIDLabel, 0, 1);

        bannerID = new TextField();
        bannerID.setStyle("-fx-focus-color: darkgreen;");
        grid0.add(bannerID, 1, 1);


        Text otherwiseLabel = new Text("Otherwise");
        otherwiseLabel.setFont(Font.font("Copperplate", FontWeight.BOLD, 20));
        otherwiseLabel.setFill(Color.BLACK);
        otherwiseLabel.setWrappingWidth(200);
        otherwiseLabel.setTextAlignment(TextAlignment.CENTER);
        grid0.add(otherwiseLabel, 1,2);

        Text fnlNameLabel = new Text(" Enter Student First & Last Name: ");
        fnlNameLabel.setWrappingWidth(400);
        fnlNameLabel.setTextAlignment(TextAlignment.LEFT);
        fnlNameLabel.setFill(Color.BLACK);
        fnlNameLabel.setFont(Font.font("Copperplate", FontWeight.THIN, 18));
        grid0.add(fnlNameLabel, 1,3);

        Text firstNameLabel = new Text(" First Name: ");
        firstNameLabel.setFont(myFont);
        firstNameLabel.setFill(Color.BLACK);
        firstNameLabel.setWrappingWidth(150);
        firstNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid0.add(firstNameLabel, 0, 4);

        firstName = new TextField();
        firstName.setStyle("-fx-focus-color: darkgreen;");
        grid0.add(firstName, 1, 4);

        Text lastNameLabel = new Text(" Last Name: ");
        lastNameLabel.setFont(myFont);
        lastNameLabel.setFill(Color.BLACK);
        lastNameLabel.setWrappingWidth(150);
        lastNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid0.add(lastNameLabel, 0, 5);

        lastName = new TextField();
        lastName.setStyle("-fx-focus-color: darkgreen;");
        grid0.add(lastName, 1, 5);

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
            String firstNm = firstName.getText();
            String lastNm = lastName.getText();
            String bannerIDText = bannerID.getText();
            if(bannerIDText.length() < 10 ) {
                if (bannerIDText.matches("[0-9]+")) {
                    props.setProperty("BannerId", bannerIDText);
                    myModel.stateChangeRequest("SearchStudents", props);
                } else {
                    if (firstNm.length() < 21) {
                        props.setProperty("FirstName", firstNm);
                        if (lastNm.length() < 21) {
                            props.setProperty("LastName", lastNm);
                            myModel.stateChangeRequest("SearchStudents", props);
                        } else {
                            displayErrorMessage("ERROR: Enter a last name shorter than 20 characters!");
                        }
                    } else {
                        displayErrorMessage("ERROR: Enter a first name shorter than 20 characters!");
                    }
                }
            }
            else {
                displayErrorMessage("Enter a proper value for BannerID!");
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
            myModel.stateChangeRequest("CancelCheckOutSearch", null);
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
        bannerID.clear();
        firstName.clear();
        lastName.clear();
    }

    // Update method

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

