package model;

//system imports
import utilities.GlobalVariables;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.text.SimpleDateFormat;
import java.util.*;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;

import userinterface.View;
import userinterface.ViewFactory;

public class CheckInABookTransaction extends Transaction{
    private Rental myRental;
    private Worker myWorker;

    // GUI Components
    private String transactionErrorMessage = "";

    public CheckInABookTransaction() throws Exception {
        super();
    }

    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("CancelSearchBook", "CancelTransaction");
        dependencies.setProperty("SearchBooks", "TransactionError");
        myRegistry.setDependencies(dependencies);
    }

    public void processTransaction(Properties props) {
        String bookBarcode = props.getProperty("Barcode");

        Calendar rightNow = Calendar.getInstance();
        Date todayDate = rightNow.getTime();
        String todayDateText = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);
        try{
            myRental = new Rental(bookBarcode, true);
            myRental.stateChangeRequest("CheckinDate", todayDateText);
            myRental.stateChangeRequest("CheckinWorkerId", myWorker.getState("BannerId"));
            myRental.update();
            transactionErrorMessage = "Book successfully checked in!";
        }
        catch(InvalidPrimaryKeyException excpt){
            transactionErrorMessage = "ERROR: No rental with barcode: " + bookBarcode + " found!";
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
        else if (key.equals("SetWorker") == true) {
            Worker w = (Worker)value;
            myWorker = w;
        }
        else if(key.equals("SearchBooks")){
            processTransaction((Properties) value);
        }
        myRegistry.updateSubscribers(key, this);
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
