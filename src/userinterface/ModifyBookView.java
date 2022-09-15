package userinterface;

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
import javafx.scene.control.ComboBox;

import java.util.Properties;

public class ModifyBookView extends InsertBookView {

    protected String [] allowedCondition;

    protected ComboBox conditionMatt;

    public ModifyBookView(IModel model) {

        super(model);
    }

    public void populateFields() {
        allowedCondition = new String[2];
        allowedCondition[0] = "Good";
        allowedCondition[1] = "Damaged";
        conditionMatt.getItems().add(allowedCondition[0]);
        conditionMatt.getItems().add(allowedCondition[1]);

        String barcodeText = (String) myModel.getState("Barcode");
        String titleText = (String) myModel.getState("Title");
        String a1Text = (String) myModel.getState("Author1");
        String a2Text = (String) myModel.getState("Author2");
        String a3Text = (String) myModel.getState("Author3");
        String a4Text = (String) myModel.getState("Author4");
        String pubText = (String) myModel.getState("Publisher");
        String pubYearText = (String) myModel.getState("YearOfPublication");
        String isbnText = (String) myModel.getState("ISBN");
        String conditionText = (String) myModel.getState("BookCondition");
        String sugPriceText = (String) myModel.getState("SuggestedPrice");
        String notesText = (String) myModel.getState("Notes");

        barcode.setText(barcodeText);
        barcode.setEditable(false);
        title.setText(titleText);
        author1.setText(a1Text);
        author2.setText(a2Text);
        author3.setText(a3Text);
        author4.setText(a4Text);
        publisher.setText(pubText);
        pubYear.setText(pubYearText);
        isbn.setText(isbnText);
        conditionMatt.setValue(conditionText);
        sugPrice.setText(sugPriceText);
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

        Text prompt = new Text("Modify Book Information");
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

        Text conditionLabel = new Text(" Condition : ");
        conditionLabel.setFont(myFont);
        conditionLabel.setWrappingWidth(150);
        conditionLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(conditionLabel, 0, 10);
        conditionMatt = new ComboBox();
        grid.add(conditionMatt, 1, 10);

        Text sugPriceLabel = new Text(" Suggested Price : ");
        sugPriceLabel.setFont(myFont);
        sugPriceLabel.setWrappingWidth(150);
        sugPriceLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(sugPriceLabel, 0, 11);
        grid.add(sugPrice = new TextField(), 1, 11);

        Text notesLabel = new Text(" Notes : ");
        notesLabel.setFont(myFont);
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(notesLabel, 0, 12);
        notes = new TextArea();
        notes.setPrefColumnCount(20);
        notes.setPrefRowCount(5);
        notes.setWrapText(true);
        grid.add(notes, 1, 12);

        ImageView icon = new ImageView(new Image("/images/savecolor.png"));
        icon.setFitHeight(15);
        icon.setFitWidth(15);
        submit = new Button("Update", icon);
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
            String condText = (String) conditionMatt.getValue();
            String sugPriceText = (String) sugPrice.getText();
            String notesText = (String) notes.getText();

            // Validate the category strings to make sure they are integers
            Properties props = new Properties();

            if(titleText.length() != 0) {
                props.setProperty("Title", titleText);
                if(a1Text.length() != 0) {
                    props.setProperty("Author1", a1Text);
                    if (pubYearText.length() != 0 && pubYearText.length() < 12 && pubYearText.matches("[0-9]+")) {
                        props.setProperty("Author2", a2Text);
                        props.setProperty("Author3", a3Text);
                        props.setProperty("Author4", a4Text);
                        props.setProperty("Publisher", pubText);
                        props.setProperty("YearOfPublication", pubYearText);
                        props.setProperty("ISBN", isbnText);
                        props.setProperty("BookCondition", condText);
                        props.setProperty("SuggestedPrice", sugPriceText);
                        props.setProperty("Notes", notesText);
                        props.setProperty("Status", "Active");
                        myModel.stateChangeRequest(keyToSendWithData, props);
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
