package model;

import event.Event;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;

public class StudentsWithBooksCheckedOutTransaction extends Transaction{

    private Rental myRental;
    private RentalCollection myRentalCollection;
    private Student myStudent;
    private StudentCollection myStudentCollection;

    // GUI Components
    private String transactionErrorMessage = "";

    public StudentsWithBooksCheckedOutTransaction() throws Exception {
        super();
    }

    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("StudentsWithBooksCheckedOutData", "TransactionError");
        dependencies.setProperty("CancelListStudents", "CancelTransaction");
        dependencies.setProperty("CancelStudentBooksList", "CancelTransaction");

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
            myStudentCollection = new StudentCollection();
            myRentalCollection.findCheckedOutBooks();
            int rentalsSize = myRentalCollection.getSize();
            for(int i = 0; i<rentalsSize; i++){
                myRental = new Rental((String)myRentalCollection.elementAt(i).getState("Id"));
                myStudent = new Student((String) myRental.getState("BorrowerId"));
                myStudentCollection.addStudent(myStudent);
            }

            createAndShowStudentsWithBooksCheckedOutCollectionView();


            //myStudent.update();
            //transactionErrorMessage = "Delinquency Check Complete!";
        }
        catch (Exception excep) {
            if(excep.toString().equals("java.lang.Exception: No rental files found.")) {
                transactionErrorMessage = "No Students Found!";
            }
            else {
                transactionErrorMessage = "Error in listing students: " + excep.toString();
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in listing students: " + excep.toString(),
                        Event.ERROR);
            }
        }
    }

    //-----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("TransactionError")) {
            return transactionErrorMessage;
        }
        else if (key.equals("UpdateStatusMessage")) {
            return transactionErrorMessage;
        }
        else if(key.equals("StudentList")){
            return myStudentCollection;
        }
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        if (key.equals("DoYourJob")) {
            doYourJob();
        }
        else
        if (key.equals("StudentsWithBooksCheckedOutData")) {
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
        Scene currentScene = myViews.get("StudentsWithBooksCheckedOutView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("StudentsWithBooksCheckedOutView", this);
            currentScene = new Scene(newView);
            myViews.put("StudentsWithBooksCheckedOutView", currentScene);

            return currentScene;
        }
        else {
            return currentScene;
        }
    }
    private void createAndShowStudentsWithBooksCheckedOutCollectionView() {
        Scene currentScene = (Scene)myViews.get("StudentsWithBooksCheckedOutCollectionView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("StudentsWithBooksCheckedOutCollectionView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("StudentsWithBooksCheckedOutCollectionView", currentScene);
        }
        swapToView(currentScene);
    }
}
