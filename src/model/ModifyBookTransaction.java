package model;

import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


import java.util.Enumeration;
import java.util.Properties;

public class ModifyBookTransaction extends Transaction {
    private Book myBook;
    private BookCollection myBookCol;

    // GUI Components
    private String transactionErrorMessage = "";
    /**
     * Constructor for this class.
     */
    public ModifyBookTransaction() throws Exception {
        super();
    }

    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("BookData", "TransactionError");
        dependencies.setProperty("SearchBooks", "TransactionError");
        dependencies.setProperty("CancelSearchBook", "CancelTransaction");
        dependencies.setProperty("CancelBookList", "CancelTransaction");
        myRegistry.setDependencies(dependencies);
    }

    //----------------------------------------------------------
    public void processTransaction(Properties props) {
        String bookBarcode = "";
        try {
            bookBarcode = props.getProperty("Barcode");
            myBook = new Book(bookBarcode);
            createAndShowModifyBookView();
        }
        catch (InvalidPrimaryKeyException excpt) {
            transactionErrorMessage = "ERROR: No book with barcode: " + bookBarcode + " found!";
        }
        //fix these catch blocks or remove them ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        catch (Exception excep) {
            transactionErrorMessage = "ERROR: Unexpected error in ModifyBookTransaction.processTransaction: " + excep.toString();
           // DEBUG System.out.println(excep);
           // DEBUG excep.printStackTrace();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in saving Book: " + excep.toString(),
                    Event.ERROR);
        }
    }

    public void processModifyTransaction(Properties props) {
        try {
            Enumeration keyNames = props.propertyNames();
            while (keyNames.hasMoreElements() == true) {
                String nextKey = (String)keyNames.nextElement();
                String nextVal = props.getProperty(nextKey);
                myBook.stateChangeRequest(nextKey, nextVal);
            }
            myBook.update();
            transactionErrorMessage = (String)myBook.getState("UpdateStatusMessage");
        }
        //fix these catch blocks or remove them ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        catch (Exception excep) {
            transactionErrorMessage = "Error in saving Book: " + excep.toString();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in saving Book: " + excep.toString(),
                    Event.ERROR);
        }
    }

    public Object getState(String key) {
        if (key.equals("TransactionError") == true) {
            return transactionErrorMessage;
        }
        else if (key.equals("UpdateStatusMessage") == true) {
            return transactionErrorMessage;
        }
        else {
            if (myBook != null) {
                Object val = myBook.getState(key);
                if (val != null)
                    return val;
            }
        }
        return null;
    }

    //---------------------------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        transactionErrorMessage = "";

        if (key.equals("DoYourJob") == true) {
            doYourJob();
        }
        else if (key.equals("SearchBooks") == true) {
            processTransaction((Properties)value);
        }
        else if (key.equals("BookData") == true) {
            processModifyTransaction((Properties) value);
        }
        myRegistry.updateSubscribers(key, this);
    }

    //------------------------------------------------------
    protected void createAndShowModifyBookView() {
        Scene currentScene = myViews.get("ModifyBookView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("ModifyBookView", this);
            currentScene = new Scene(newView);
            myViews.put("ModifyBookView", currentScene);


        }
        swapToView(currentScene);
    }

    protected Scene createView() {
        Scene currentScene = myViews.get("SearchBookView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("SearchBookView", this);
            currentScene = new Scene(newView);
            myViews.put("SearchBookView", currentScene);

            return currentScene;
        }
        else {
            return currentScene;
        }
    }
}
