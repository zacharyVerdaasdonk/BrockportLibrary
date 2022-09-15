package userinterface;

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class ModifyWorkerView extends InsertWorkerView {
    protected DatePicker dateOfHirePick;

    public ModifyWorkerView(IModel model) {
        super(model);
    }

    public void populateFields() {
        String bannerIdText = (String) myModel.getState("BannerId");
        String passwordText = (String) myModel.getState("Password");
        String firstNameText = (String) myModel.getState("FirstName");
        String lastNameText = (String) myModel.getState("LastName");
        String phoneText = (String) myModel.getState("Phone");
        String emailText = (String) myModel.getState("Email");
        String credentialsText = (String) myModel.getState("Credentials");
        String hireDateText = (String) myModel.getState("DateOfHire");

        bannerId.setText(bannerIdText);
        bannerId.setEditable(false);
        password.setText(passwordText);
        firstName.setText(firstNameText);
        lastName.setText(lastNameText);
        phone.setText(phoneText);
        email.setText(emailText);
        credentials.setValue(credentialsText);
        dateOfHirePick.setValue(LocalDate.parse(hireDateText));
    }

    protected VBox createFormContents() {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);

        Text prompt = new Text("Modify Worker Information");
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

        Text passwordLabel = new Text(" Password : "); //see line 55
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

        Text phoneLabel = new Text(" Phone : ");
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

        Text hireLabel = new Text(" Date of Hire : ");
        hireLabel.setFont(myFont);
        hireLabel.setWrappingWidth(150);
        hireLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(hireLabel, 0, 8);

        dateOfHirePick = new DatePicker();
        dateOfHirePick.setStyle("-fx-focus-color: darkgreen;");
        grid.add(dateOfHirePick, 1, 8);

        ImageView icon = new ImageView(new Image("/images/savecolor.png"));
        icon.setFitHeight(15);
        icon.setFitWidth(15);
        submit = new Button("Update", icon);
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
            String credText = (String) credentials.getValue();
            LocalDate dateP = dateOfHirePick.getValue();

            // Validate the category strings to make sure they are integers
            Properties props = new Properties();
            String dateStr = dateP.toString();
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
                                String currentCredentials = (String) myModel.getState("Credentials");
                                if(!currentCredentials.equals(credText)) {
                                    Calendar rightNow = Calendar.getInstance();
                                    Date todayDate = rightNow.getTime();
                                    String todayDateText = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);
                                    props.setProperty("DateOfLatestCredentialsStatus", todayDateText);
                                }
                                props.setProperty("Credentials", credText);
                                props.setProperty("DateOfHire", dateStr);
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
}