// specify the package
package model;

// system imports
import utilities.GlobalVariables;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;

import userinterface.View;
import userinterface.ViewFactory;

public class RunDelinquencyCheckTransaction extends Transaction {

    private Rental myRental;
    private RentalCollection myRentalCollection;
    private Student myStudent;

    // GUI Components
    private String transactionErrorMessage = "";

    public RunDelinquencyCheckTransaction() throws Exception {
        super();
    }

    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("DelinquencyData", "TransactionError");
        dependencies.setProperty("CancelRunDelCheck", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the Book,
     * verifying its uniqueness, etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props) {
        try {
            myRentalCollection = new RentalCollection();
            myRentalCollection.findLateRentals();

            int rentalsSize = myRentalCollection.getSize();
            for (int cnt=0; cnt < rentalsSize; cnt++) {
                myRental = myRentalCollection.elementAt(cnt);
                String borrowerIdText = (String) myRental.getState("BorrowerId");
                myStudent = new Student(borrowerIdText);
                myStudent.setDelinquent();
                myStudent.update();
            }
            transactionErrorMessage = "Delinquency Check Complete!";
        }
        catch (Exception excep) {
            if(excep.toString().equals("java.lang.Exception: No rental files found.")) {
                transactionErrorMessage = "No delinquents found!";
            }
            else {
                transactionErrorMessage = "Error in running delinquency check: " + excep.toString();
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in running delinquency check: " + excep.toString(),
                        Event.ERROR);
            }
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
        if (key.equals("DelinquencyData") == true) {
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
        Scene currentScene = myViews.get("RunDelinquencyCheckView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("RunDelinquencyCheckView", this);
            currentScene = new Scene(newView);
            myViews.put("RunDelinquencyCheckView", currentScene);

            return currentScene;
        }
        else {
            return currentScene;
        }
    }
}
