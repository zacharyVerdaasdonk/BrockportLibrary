// specify the package
package model;

// system imports
import utilities.GlobalVariables;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;

import userinterface.View;
import userinterface.ViewFactory;

public class InsertBookTransaction extends Transaction {

    private Book myBook;

    // GUI Components
    private String transactionErrorMessage = "";

    public InsertBookTransaction() throws Exception {
        super();
    }

    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("BookData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the Book,
     * verifying its uniqueness, etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props) {
        try {
            String barcode = props.getProperty("Barcode");
            String barcodePrefix = barcode.substring(0,2);
            try {
                BookBarcodePrefix pref = new BookBarcodePrefix(barcodePrefix);
                String discip = (String) pref.getState("Discipline");
                props.setProperty("Discipline", discip);
                myBook = new Book(props);
                myBook.update();
                transactionErrorMessage = (String)myBook.getState("UpdateStatusMessage");
            }
            catch (InvalidPrimaryKeyException excep)
            {
                transactionErrorMessage = "ERROR: Invalid barcode: no matching discipline found";
            }
        }
        //fix these catch blocks or remove them ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        catch (Exception excep) {
            transactionErrorMessage = "Error in saving Book: " + excep.toString();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in saving Book: " + excep.toString(),
                    Event.ERROR);
        }
    }

    //-----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("TransactionError") == true) {
            return transactionErrorMessage;
        }
        else if (key.equals("UpdateStatusMessage") == true) {
            return transactionErrorMessage;
        }
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        if (key.equals("DoYourJob") == true) {
            doYourJob();
        }
        else
        if (key.equals("BookData") == true) {
            processTransaction((Properties)value);
        }
        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    //------------------------------------------------------
    protected Scene createView() {
        Scene currentScene = myViews.get("InsertBookView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("InsertBookView", this);
            currentScene = new Scene(newView);
            myViews.put("InsertBookView", currentScene);

            return currentScene;
        }
        else {
            return currentScene;
        }
    }
}
