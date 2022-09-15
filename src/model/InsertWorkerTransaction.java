//specifying package
package model;

//system imports
import utilities.GlobalVariables;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;


//project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

public class InsertWorkerTransaction extends Transaction {

    private Worker myWorker;

    //GUI Components
    private String transactionErrorMessage = "";

    public InsertWorkerTransaction() throws Exception{
        super();
    }

    protected void setDependencies(){
        dependencies = new Properties();
        dependencies.setProperty("WorkerData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the Patron,
     * verifying its uniqueness, etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props) {
        Calendar rightNow = Calendar.getInstance();
        Date todayDate = rightNow.getTime();
        String todayDateText = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);
        try {
            props.setProperty("DateOfLatestCredentialsStatus", todayDateText);
            props.setProperty("DateOfHire", todayDateText);
            myWorker = new Worker(props);
            myWorker.update();
            transactionErrorMessage = (String)myWorker.getState("UpdateStatusMessage");

        }
        //fix these catch blocks or remove them ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        catch (Exception excep) {
            transactionErrorMessage = "Error in saving Worker: " + excep.toString();
            excep.printStackTrace();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in saving Worker: " + excep.toString(),
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
        else {
            if (myWorker != null) {
                Object val = myWorker.getState(key);
                if (val != null)
                    return val;
            }
        }
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        if (key.equals("DoYourJob") == true) {
            doYourJob();
        }
        else
        if (key.equals("WorkerData") == true) {
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
        Scene currentScene = myViews.get("InsertWorkerView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("InsertWorkerView", this);
            currentScene = new Scene(newView);
            myViews.put("InsertWorkerView", currentScene);

            return currentScene;
        }
        else {
            return currentScene;
        }
    }

}
