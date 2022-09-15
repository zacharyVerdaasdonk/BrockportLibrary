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

public class DeleteStudentTransaction extends Transaction {
    private Student myStudent;
    private StudentCollection myStudentCollection;

    // GUI Components
    private String transactionErrorMessage = "";
    /**
     * Constructor for this class.
     */
    public DeleteStudentTransaction() throws Exception {
        super();
    }

    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("StudentData", "TransactionError");
        dependencies.setProperty("CancelSearchStudent", "CancelTransaction");
        dependencies.setProperty("CancelStudentList", "CancelTransaction");
        myRegistry.setDependencies(dependencies);
    }

    //----------------------------------------------------------
    public void processTransaction(Properties props){
        try{
            String firstName = props.getProperty("FirstName");
            String lastName = props.getProperty("LastName");
            myStudentCollection = new StudentCollection();
            myStudentCollection.findStudentsWithFirstNameLastNameLike(firstName, lastName);
            createAndShowStudentCollectionView();
        }
        catch(Exception excep){
            transactionErrorMessage = "Error in saving Student: " + excep.toString();
            //DEBUG System.out.println(excep);
            //DEBUG excep.printStackTrace();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in saving Student: " + excep.toString(),
                    Event.ERROR);
        }
    }

    public void processDeleteTransaction(Properties props) {
        try {
            Enumeration keyNames = props.propertyNames();
            while (keyNames.hasMoreElements() == true) {
                String nextKey = (String)keyNames.nextElement();
                String nextVal = props.getProperty(nextKey);
                myStudent.stateChangeRequest(nextKey, nextVal);
            }
            myStudent.update();
            transactionErrorMessage = (String)myStudent.getState("UpdateStatusMessage");
        }
        //fix these catch blocks or remove them ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        catch (Exception excep) {
            transactionErrorMessage = "Error in deleting Worker: " + excep.toString();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in deleting Worker: " + excep.toString(),
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
        else if (key.equals("StudentList") == true) {
            return myStudentCollection;
        }
        else {
            if (myStudent != null) {
                Object val = myStudent.getState(key);
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
        else if (key.equals("SearchStudents") == true) {
            processTransaction((Properties)value);
        }
        else if (key.equals("StudentData") == true) {
            processDeleteTransaction((Properties)value);
        }
        else if (key.equals("StudentSelected") == true) {
            try {
                myStudent = new Student((String) value);
                createAndShowDeleteStudentView();
            }
            catch (Exception ex) {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating DeleteStudentView", Event.ERROR);
            }
        }
        myRegistry.updateSubscribers(key, this);
    }

    //------------------------------------------------------
    private void createAndShowStudentCollectionView() {
        Scene currentScene = (Scene)myViews.get("StudentCollectionView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("StudentCollectionView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("StudentCollectionView", currentScene);
        }
        swapToView(currentScene);
    }

    //------------------------------------------------------
    protected void createAndShowDeleteStudentView() {
        Scene currentScene = myViews.get("DeleteStudentView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("DeleteStudentView", this);
            currentScene = new Scene(newView);
            myViews.put("DeleteStudentView", currentScene);

        }
        swapToView(currentScene);
    }

    protected Scene createView() {
        Scene currentScene = myViews.get("SearchStudentView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("SearchStudentView", this);
            currentScene = new Scene(newView);
            myViews.put("SearchStudentView", currentScene);

            return currentScene;
        }
        else {
            return currentScene;
        }
    }
}
