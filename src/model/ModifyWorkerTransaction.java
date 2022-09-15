//specify package
package model;

//specify imports
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

public class ModifyWorkerTransaction extends Transaction {
    private Worker myWorker;
    private WorkerCollection myWorkerCollection;

    //GUI Components
    private String transactionErrorMessage = "";

    //Constructor
    public ModifyWorkerTransaction() throws Exception{
        super();
    }

    protected void setDependencies(){
        dependencies = new Properties();
        dependencies.setProperty("WorkerData", "TransactionError");
        dependencies.setProperty("CancelSearchWorker", "CancelTransaction");
        dependencies.setProperty("CancelWorkerList", "CancelTransaction");
        myRegistry.setDependencies(dependencies);
    }

    public void processTransaction(Properties props){
        try{
            String firstName = props.getProperty("FirstName");
            String lastName = props.getProperty("LastName");
            myWorkerCollection = new WorkerCollection();
            myWorkerCollection.findWorkersWithFirstNameLastNameLike(firstName, lastName);
            createAndShowWorkerCollectionView();
        }
        catch(Exception excep){
            transactionErrorMessage = "Error in saving Worker: " + excep.toString();
            //DEBUG System.out.println(excep);
            //DEBUG excep.printStackTrace();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in saving Worker: " + excep.toString(),
                    Event.ERROR);
        }
    }

    public void processModifyTransaction(Properties props) {
        try {
            Enumeration keyNames = props.propertyNames();
            while (keyNames.hasMoreElements() == true) {
                String nextKey = (String)keyNames.nextElement();
                String nextVal = props.getProperty(nextKey);
                myWorker.stateChangeRequest(nextKey, nextVal);
            }
            myWorker.update();
            transactionErrorMessage = (String)myWorker.getState("UpdateStatusMessage");
        }
        //fix these catch blocks or remove them ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        catch (Exception excep) {
            transactionErrorMessage = "Error in saving Worker: " + excep.toString();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in saving Worker: " + excep.toString(),
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
        else if (key.equals("WorkerList") == true) {
            return myWorkerCollection;
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

    //---------------------------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        if (key.equals("DoYourJob") == true) {
            doYourJob();
        }
        else
        if (key.equals("SearchWorkers") == true) {
            processTransaction((Properties)value);
        }
        else if (key.equals("WorkerData") == true) {
            processModifyTransaction((Properties)value);
        }
        else
        if (key.equals("WorkerSelected") == true) {
            try {
                myWorker = new Worker((String) value);
                createAndShowModifyWorkerView();
            }
            catch (Exception ex) {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating ModifyStudentView", Event.ERROR);
            }
        }
        myRegistry.updateSubscribers(key, this);
    }

    private void createAndShowWorkerCollectionView() {
        Scene currentScene = (Scene)myViews.get("WorkerCollectionView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("WorkerCollectionView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("WorkerCollectionView", currentScene);
        }
        swapToView(currentScene);
    }

    private void createAndShowModifyWorkerView() {
        Scene currentScene = (Scene)myViews.get("ModifyWorkerView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("ModifyWorkerView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("ModifyWorkerView", currentScene);
        }
        swapToView(currentScene);
    }

    protected Scene createView() {
        Scene currentScene = myViews.get("SearchWorkerView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("SearchWorkerView", this);
            currentScene = new Scene(newView);
            myViews.put("SearchWorkerView", currentScene);

            return currentScene;
        }
        else {
            return currentScene;
        }
    }

}
