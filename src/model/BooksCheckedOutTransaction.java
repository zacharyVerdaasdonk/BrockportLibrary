package model;

import event.Event;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;

public class BooksCheckedOutTransaction extends Transaction {

    private Rental myRental;
    private RentalCollection myRentalCollection;
    private Student myStudent;
    private Book myBook;
    private BookCollection myBookCollection;

    // GUI Components
    private String transactionErrorMessage = "";

    public BooksCheckedOutTransaction() throws Exception {
        super();
    }

    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("BooksCheckedOutData", "TransactionError");
        dependencies.setProperty("CancelBookList", "CancelTransaction");
        dependencies.setProperty("CancelListBooks", "CancelTransaction");
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
            myBookCollection = new BookCollection();
            myRentalCollection.findCheckedOutBooks();
            int rentalsSize = myRentalCollection.getSize();
            for(int i = 0; i<rentalsSize; i++) {
                myRental = new Rental((String) myRentalCollection.elementAt(i).getState("Id"));
                myBook = new Book((String) myRental.getState("BookId"));
                myBookCollection.addBook(myBook);
            }
            //myBookCollection.display();
            createAndShowBookCollectionView();
        }
        catch (Exception excep) {
            if(excep.toString().equals("java.lang.Exception: No rental files found.")) {
                transactionErrorMessage = "No Books Checked Out!";
            }
            else {
                transactionErrorMessage = "Error in listing books checked out: " + excep.toString();
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in listing books checked out: " + excep.toString(),
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
        else if(key.equals("BookList")){
            return myBookCollection;
        }
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        if (key.equals("DoYourJob")) {
            doYourJob();
        }
        else
        if (key.equals("BooksCheckedOutData")) {
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
        Scene currentScene = myViews.get("BooksCheckedOutView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("BooksCheckedOutView", this);
            currentScene = new Scene(newView);
            myViews.put("BooksCheckedOutView", currentScene);

            return currentScene;
        } else {
            return currentScene;
        }
    }
        private void createAndShowBookCollectionView() {
            Scene currentScene = (Scene)myViews.get("BookCollectionView");

            if (currentScene == null) {
                // create our initial view
                View newView = ViewFactory.createView("BookCollectionView", this); // USE VIEW FACTORY
                currentScene = new Scene(newView);
                myViews.put("BookCollectionView", currentScene);
            }
            swapToView(currentScene);
    }
}
