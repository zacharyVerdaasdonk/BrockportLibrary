// tabs=4
//************************************************************
//	COPYRIGHT 2021, Kyle D. Adams and Sandeep Mitra, State
//   University of New York. - Brockport (SUNY Brockport) 
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// project imports

//==============================================================
public class MessageView extends Text {

	// Class constructor
	//----------------------------------------------------------
	public MessageView(String initialMessage) {
		super(initialMessage);
		setFont(Font.font("COPPERPLATE", FontWeight.BOLD, 16));
		setFill(Color.BLUE);
		setWrappingWidth(400);
		setTextAlignment(TextAlignment.LEFT);
	}

	/**
	 * Display ordinary message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message) {
		// display the passed text in red
		setFill(Color.LIGHTGREEN);
		setText(message);
	}
        
	public void displayInfoMessage(String message) {
		// display the passed text in red
		setFill(Color.GOLD);
		setText(message);
	}
	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message) {
		// display the passed text in red
		setFill(Color.FIREBRICK);
		setText(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		setText("                           ");
	}


}



