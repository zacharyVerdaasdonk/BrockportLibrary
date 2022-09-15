package userinterface;

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ModifyStudentView extends InsertStudentView {
    protected String[] allowedConditions;

    protected DatePicker dateOfRegistrationPick;

    protected  ComboBox borrowerStatus;


    public ModifyStudentView(IModel model) {
        super(model);
    }

    public void populateFields() {
        allowedConditions = new String[2];
        allowedConditions[0] = "Good Standing";
        allowedConditions[1] = "Delinquent";
        borrowerStatus.getItems().add(allowedConditions[0]);
        borrowerStatus.getItems().add(allowedConditions[1]);

        String bannerIDText = (String) myModel.getState("BannerId");
        String firstNameText = (String) myModel.getState("FirstName");
        String lastNameText = (String) myModel.getState("LastName");
        String phoneText = (String) myModel.getState("Phone");
        String emailText = (String) myModel.getState("Email");
        String borrowerStatusText = (String) myModel.getState("BorrowerStatus");
        String dateOfRegistrationText = (String)myModel.getState("DateOfRegistration");
        String notesText = (String) myModel.getState("Notes");

        bannerID.setText(bannerIDText);
        bannerID.setEditable(false);
        firstName.setText(firstNameText);
        lastName.setText(lastNameText);
        phone.setText(phoneText);
        email.setText(emailText);
        borrowerStatus.setValue(borrowerStatusText);
        dateOfRegistrationPick.setValue(LocalDate.parse(dateOfRegistrationText));
        notes.setText(notesText);
    }

    protected VBox createFormContents() {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);

        Text prompt = new Text("Modify Student Information");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text bannerIDLabel = new Text(" Banner ID : ");
        bannerIDLabel.setFont(myFont);
        bannerIDLabel.setWrappingWidth(150);
        bannerIDLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(bannerIDLabel, 0, 1);
        grid.add(bannerID = new TextField(), 1, 1);

        Text fNameLabel = new Text(" First Name : ");
        fNameLabel.setFont(myFont);
        fNameLabel.setWrappingWidth(150);
        fNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(fNameLabel, 0, 2);
        grid.add(firstName = new TextField(), 1, 2);

        Text lNameLabel = new Text(" Last Name : ");
        lNameLabel.setFont(myFont);
        lNameLabel.setWrappingWidth(150);
        lNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(lNameLabel, 0, 3);
        grid.add(lastName = new TextField(), 1, 3);

        Text phoneLabel = new Text(" Phone Number : ");
        phoneLabel.setFont(myFont);
        phoneLabel.setWrappingWidth(150);
        phoneLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(phoneLabel, 0, 4);
        grid.add(phone = new TextField(), 1, 4);

        Text emailLabel = new Text(" Email : ");
        emailLabel.setFont(myFont);
        emailLabel.setWrappingWidth(150);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(emailLabel, 0, 5);
        grid.add(email = new TextField(), 1, 5);

        Text statusLabel = new Text(" Status : ");
        statusLabel.setFont(myFont);
        statusLabel.setWrappingWidth(150);
        statusLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(statusLabel, 0, 6);
        grid.add(borrowerStatus = new ComboBox(), 1, 6);

        Text dateOfRegistrationLabel = new Text(" Date of Registration: ");
        dateOfRegistrationLabel.setFont(myFont);
        dateOfRegistrationLabel.setWrappingWidth(150);
        dateOfRegistrationLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(dateOfRegistrationLabel, 0, 7);

        dateOfRegistrationPick = new DatePicker();
        dateOfRegistrationPick.setStyle("-fx-focus-color: darkgreen;");
        grid.add(dateOfRegistrationPick, 1, 7);

        Text notesLabel = new Text(" Notes : ");
        notesLabel.setFont(myFont);
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(notesLabel, 0, 8);
        notes = new TextArea();
        notes.setPrefColumnCount(20);
        notes.setPrefRowCount(5);
        notes.setWrapText(true);
        grid.add(notes, 1, 8);


        ImageView icon = new ImageView(new Image("/images/savecolor.png"));
        icon.setFitHeight(15);
        icon.setFitWidth(15);
        submit = new Button("Update", icon);
        submit.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
        submit.setOnAction((ActionEvent e) -> {
            clearErrorMessage();
            clearOutlines();

            String firstNameText = firstName.getText();
            String lastNameText = lastName.getText();
            String phoneText = phone.getText();
            String emailText = email.getText();
            String borrowerStatusValue = (String) borrowerStatus.getValue();
            LocalDate dateP = dateOfRegistrationPick.getValue();
            String notesText = notes.getText();

            // Validate the category strings to make sure they are integers
            Properties props = new Properties();
            String dateStr = dateP.toString();
            if((firstNameText.length() != 0) && (firstNameText.length() < 21)) {
                props.setProperty("FirstName", firstNameText);
                if (lastNameText.length() != 0 && lastNameText.length() < 21) {
                    props.setProperty("LastName", lastNameText);
                    if (phoneText.length() < 13) {
                        props.setProperty("Phone", phoneText);
                        if (emailText.length() < 31) {
                            props.setProperty("Email", emailText);
                            /*if(!props.getProperty("BorrowerStatus").equals(borrowerStatusValue)) {
                                Calendar rightNow = Calendar.getInstance();
                                Date todayDate = rightNow.getTime();
                                String todayDateText = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);
                                props.setProperty("DateOfLatestBorrowerStatus", todayDateText);
                            }*/
                            props.setProperty("BorrowerStatus", borrowerStatusValue);
                            props.setProperty("DateOfRegistration", dateStr);
                            props.setProperty("Notes", notesText);
                            myModel.stateChangeRequest(keyToSendWithData, props);
                        } else {
                            displayErrorMessage("ERROR: Enter a phone number shorter than 12 digits!");
                        }
                    } else {
                        displayErrorMessage("ERROR: Enter an email shorter than 30 characters!");
                    }
                }
                else {
                    displayErrorMessage("ERROR: Enter a last name between 1 and 20 characters!");
                }
            }
            else {
                displayErrorMessage("ERROR: Enter a first name between 1 and 20 characters!");
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