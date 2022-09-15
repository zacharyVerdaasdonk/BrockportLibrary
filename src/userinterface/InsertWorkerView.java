package userinterface;

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
import javafx.scene.control.PasswordField;
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

import java.util.Properties;

public class InsertWorkerView extends View {

    // other private data
    private final int labelWidth = 120;
    private final int labelHeight = 25;

    // GUI components
    protected Button submit;
    protected Button doneButton;

    protected TextField bannerId;
    protected PasswordField password;
    protected TextField firstName;
    protected TextField lastName;
    protected TextField phone;
    protected TextField email;
    protected ComboBox credentials;
    protected String [] allowedCredentials = {"Ordinary", "Administrator"};

    // other GUI Components here
    protected MessageView statusLog;
    protected DropShadow shadow = new DropShadow();

    protected String keyToSendWithData = "";

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public InsertWorkerView(IModel Worker) {

        super(Worker, "InsertWorkerView");

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

        Text prompt = new Text("Enter All Worker Information");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text bannerLabel = new Text(" BannerId : ");
        bannerLabel.setFont(myFont);
        bannerLabel.setWrappingWidth(150);
        bannerLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(bannerLabel, 0, 1);
        grid.add(bannerId = new TextField(), 1, 1);

        Text passwordLabel = new Text(" Password : ");
        passwordLabel.setFont(myFont);
        passwordLabel.setWrappingWidth(150);
        passwordLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(passwordLabel, 0, 2);
        grid.add(password = new PasswordField(), 1, 2);

        Text fNameLabel = new Text(" First Name : ");
        fNameLabel.setFont(myFont);
        fNameLabel.setWrappingWidth(150);
        fNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(fNameLabel, 0, 3);
        grid.add(firstName = new TextField(), 1, 3);

        Text lNameLabel = new Text(" Last Name : ");
        lNameLabel.setFont(myFont);
        lNameLabel.setWrappingWidth(150);
        lNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(lNameLabel, 0, 4);
        grid.add(lastName = new TextField(), 1, 4);

        Text phoneLabel = new Text(" Phone Number : ");
        phoneLabel.setFont(myFont);
        phoneLabel.setWrappingWidth(150);
        phoneLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(phoneLabel, 0, 5);
        grid.add(phone = new TextField(), 1, 5);

        Text emailLabel = new Text(" Email : ");
        emailLabel.setFont(myFont);
        emailLabel.setWrappingWidth(150);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(emailLabel, 0, 6);
        grid.add(email = new TextField(), 1, 6);

        Text credLabel = new Text(" Credentials : ");
        credLabel.setFont(myFont);
        credLabel.setWrappingWidth(150);
        credLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(credLabel, 0, 7);
        grid.add(credentials = new ComboBox(FXCollections.observableArrayList(allowedCredentials)), 1, 7);

        ImageView icon = new ImageView(new Image("/images/pluscolor.png"));
        icon.setFitHeight(15);
        icon.setFitWidth(15);
        submit = new Button("Submit", icon);
        submit.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        submit.setOnAction((ActionEvent e) -> {
            clearErrorMessage();
            clearOutlines();

            String bannerIdText = (String) bannerId.getText();
            String passwordText = (String) password.getText();
            String firstNameText = (String) firstName.getText();
            String lastNameText = (String) lastName.getText();
            String phoneText = (String) phone.getText();
            String emailText = (String) email.getText();
            String credentialsText = (String) credentials.getValue();

            // Validate the category strings to make sure they are integers
            Properties props = new Properties();
            if(bannerIdText.length() != 0 && bannerIdText.length() < 10 && bannerIdText.matches("[0-9]+")) {
                props.setProperty("BannerId", bannerIdText);
                if (passwordText.length() != 0 && passwordText.length() < 31) {
                    props.setProperty("Password", passwordText);
                    if (firstNameText.length() != 0 && firstNameText.length() < 21) {
                        props.setProperty("FirstName", firstNameText);
                        if (lastNameText.length() != 0 && lastNameText.length() < 21) {
                            props.setProperty("LastName", lastNameText);
                            if (phoneText.length() < 13) {
                                props.setProperty("Phone", phoneText);
                                if (emailText.length() < 31) {
                                    props.setProperty("Email", emailText);
                                    props.setProperty("Credentials", credentialsText);
                                    props.setProperty("Status", "Active");
                                    myModel.stateChangeRequest(keyToSendWithData, props);
                                } else {
                                    displayErrorMessage("ERROR: Enter an email shorter than 30 characters!");
                                }
                            } else {
                                displayErrorMessage("ERROR: Enter a phone number shorter than 12 digits!");
                            }
                        } else {
                            displayErrorMessage("ERROR: Enter a last name between 1 and 20 characters!");
                        }
                    } else {
                        displayErrorMessage("ERROR: Enter a first name between 1 and 20 characters!");
                    }
                } else {
                    displayErrorMessage("ERROR: Enter a password between 1 and 20 characters!");
                }
            } else {
                displayErrorMessage("ERROR: Enter a proper value for the BannerId!");
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
            buttonContainer.setStyle("-fx-background-color: WHITE");
        });
        buttonContainer.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            buttonContainer.setStyle("-fx-background-color: WHITE");
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
        credentials.getSelectionModel().select(0);
    }

    protected void clearOutlines() {
        bannerId.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        password.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        firstName.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        lastName.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        phone.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        email.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        credentials.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
    }

    //---------------------------------------------------------
    public void updateState(String key, Object value) {
        if (key.equals("TransactionError")) {
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
