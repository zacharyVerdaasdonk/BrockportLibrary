package model;

import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;
import javafx.scene.Scene;
import org.omg.CORBA.DynAnyPackage.Invalid;
import userinterface.View;
import userinterface.ViewFactory;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

public class CheckOutBookTransaction extends Transaction {
    private Book myBook;
    private Student myStudent;
    private StudentCollection myStudentCollection;
    private Rental myRental;
    private Worker myWorker;

    private String transactionErrorMessage = "";

    public CheckOutBookTransaction() throws Exception{
        super();
    }

    protected void setDependencies() {
        dependencies = new Properties();

        dependencies.setProperty("SearchBooks", "TransactionError");
        dependencies.setProperty("DateData", "TransactionError");
        dependencies.setProperty("SearchStudents", "TransactionError");
        dependencies.setProperty("StudentSelected", "TransactionError");
        dependencies.setProperty("CancelSearchBook", "CancelTransaction");
        dependencies.setProperty("CancelCheckOutSearch", "CancelTransaction");
        dependencies.setProperty("CancelStudentList", "CancelTransaction");
        dependencies.setProperty("CancelDueDate", "CancelTransaction");
        myRegistry.setDependencies(dependencies);

    }

    public void processTransaction(Properties props){
        try{
            String bannerId = props.getProperty("BannerId");
            String firstName = props.getProperty("FirstName");
            String lastName = props.getProperty("LastName");

            if (bannerId == null || bannerId.length() == 0) {
                myStudentCollection = new StudentCollection();
                myStudentCollection.findStudentsWithFirstNameLastNameLikeAndActive(firstName, lastName);
                createAndShowStudentCollectionView();
            }
            else {
                try {
                    myStudent = new Student(bannerId);
                    System.out.println(myStudent.getState("BorrowerStatus"));
                    if(myStudent.getState("BorrowerStatus").equals("Delinquent")) {
                        transactionErrorMessage = "ERROR: Selected student is delinquent!";
                    }
                    else if(myStudent.getState("Status").equals("Inactive")) {
                        transactionErrorMessage = "ERROR: Selected student is inactive!";
                    }
                    else {
                        createAndShowSearchBookView();
                    }
                }
                catch (InvalidPrimaryKeyException e) {
                    transactionErrorMessage = "ERROR: No student with BannerID: " + bannerId + " found!";
                }
            }
        }
        catch(Exception excep){
            transactionErrorMessage = "Error in retrieving Student: " + excep.toString();
            //DEBUG System.out.println(excep);
            //DEBUG excep.printStackTrace();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in retrieving Student: " + excep.toString(),
                    Event.ERROR);
            }

        }

    public void processCheckOutBookTransaction(Properties props) {
        String bookBarcode = "";
        try {
            bookBarcode = props.getProperty("Barcode");
            myBook = new Book(bookBarcode);
            createAndShowBookDueDateView();
        }
        catch (InvalidPrimaryKeyException excpt) {
            transactionErrorMessage = "ERROR: No book with barcode: " + bookBarcode + " found!";
        }
        //fix these catch blocks or remove them ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        catch (Exception excep) {
            transactionErrorMessage = "ERROR: Unexpected error in CheckOutBookTransaction: " + excep.toString();
            // DEBUG excep.printStackTrace();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in finding Book: " + excep.toString(),
                    Event.ERROR);
        }
    }

    public void processDueDateTransaction(Properties props) {
        String barcode = (String)myBook.getState("Barcode");

        Calendar rightNow = Calendar.getInstance();
        Date todayDate = rightNow.getTime();
        String todayDateText = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);
        try {
            myRental = new Rental(barcode, true);
            transactionErrorMessage = "ERROR: This book is already checked out!";
        }
        catch (InvalidPrimaryKeyException ex) {
            props.setProperty("BorrowerId", (String)myStudent.getState("BannerId"));
            props.setProperty("BookId", barcode);
            props.setProperty("CheckoutDate", todayDateText);
            props.setProperty("CheckinDate", "");
            props.setProperty("CheckinWorkerId", "");

            myRental = new Rental(props);
            myRental.stateChangeRequest("CheckoutWorkerId", myWorker.getState("BannerId"));
            myRental.update();
            transactionErrorMessage = (String)myRental.getState("UpdateStatusMessage");
        }
    }

    protected Scene createView() {
        Scene currentScene = myViews.get("CheckOutSearchView");

        if(currentScene == null) {
            //Create our initial view
            View newView = ViewFactory.createView("CheckOutSearchView", this);
            currentScene = new Scene(newView);
            myViews.put("CheckOutSearchView", currentScene);
            return currentScene;
        } else {
         return currentScene;
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
        return null;
    }

    public void stateChangeRequest(String key, Object value) {
        transactionErrorMessage = "";

        if(key.equals("DoYourJob") == true) {
            doYourJob();
        }
        else if (key.equals("SearchStudents") == true){
            processTransaction((Properties)value);
        }
        else if(key.equals("SearchBooks") == true) {
            processCheckOutBookTransaction((Properties)value);
        }
        else if(key.equals("DateData") == true) {
            processDueDateTransaction((Properties)value);
        }
        else if (key.equals("SetWorker") == true) {
            Worker w = (Worker)value;
            myWorker = w;
        }
        else if(key.equals("StudentSelected") == true){
            try{
                myStudent = new Student((String)value);
                if(myStudent.getState("BorrowerStatus").equals("Delinquent")) {
                    transactionErrorMessage = "ERROR: Selected student is delinquent!";
                }
                else {
                    createAndShowSearchBookView();
                }
            } catch (Exception ex) {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating SearchBookView", Event.ERROR);
            }
        }
        myRegistry.updateSubscribers(key, this);
    }

    private void createAndShowSearchBookView() {
        Scene currentScene = (Scene)myViews.get("SearchBookView");

        if(currentScene == null){
            //create our initial view
            View newView = ViewFactory.createView("SearchBookView", this);
            currentScene = new Scene(newView);
            myViews.put("SearchBookView", currentScene);
        }
        swapToView(currentScene);
    }

    private void createAndShowBookDueDateView() {
        Scene currentScene = (Scene)myViews.get("BookDueDateView");

        if(currentScene == null){
            //create our initial view
            View newView = ViewFactory.createView("BookDueDateView", this);
            currentScene = new Scene(newView);
            myViews.put("BookDueDateView", currentScene);
        }
        swapToView(currentScene);
    }


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
}
