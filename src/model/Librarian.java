// specify the package
package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;

import exception.MultiplePrimaryKeysException;
import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;

import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import event.Event;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

/** The class containing the Librarian for the Library application */
//==============================================================
public class Librarian implements IView, IModel {
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.

    // For Impresario
    private Properties dependencies;
    private ModelRegistry myRegistry;

    private Worker myWorker;

    // GUI Components
    private Hashtable<String, Scene> myViews;
    private Stage myStage;

    private String loginErrorMessage = "";
    private String transactionErrorMessage = "";

    // constructor for this class
    //----------------------------------------------------------
    public Librarian() {
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();

        // STEP 3.1: Create the Registry object - if you inherit from
        // EntityBase, this is done for you. Otherwise, you do it yourself
        myRegistry = new ModelRegistry("Librarian");
        if(myRegistry == null) {
            new Event(Event.getLeafLevelClassName(this), "Librarian",
                    "Could not instantiate Registry", Event.ERROR);
        }

        // STEP 3.2: Be sure to set the dependencies correctly
        setDependencies();

        // Set up the initial view
        createAndShowLoginView();
    }

    //-----------------------------------------------------------------------------------
    private void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("Login", "LoginError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * Method called from client to get the value of a particular field
     * held by the objects encapsulated by this object.
     *
     * @param	key	Name of database column (field) for which the client wants the value
     *
     * @return	Value associated with the field
     */
    //----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("TransactionError") == true) {
            return transactionErrorMessage;
        }
        else if (key.equals("LoginError") == true) {
            return loginErrorMessage;
        }
        else return "";
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        // STEP 4: Write the sCR method component for the key you
        // just set up dependencies for

        if ((key.equals("InsertBook") == true) ||
            (key.equals("ModifyBook") == true) ||
            (key.equals("DeleteBook") == true) ||
            (key.equals("InsertStudent") == true) ||
            (key.equals("ModifyStudent") == true) ||
            (key.equals("DeleteStudent") == true) ||
            (key.equals("InsertWorker") == true) ||
            (key.equals("ModifyWorker") == true) ||
            (key.equals("DeleteWorker") == true) ||
            (key.equals("RunDelCheck") == true) ||
            (key.equals("BooksCheckedOut") == true) ||
            (key.equals("StudentsWithBooks") == true)) {

            String transType = key;
            transType = transType.trim();
            doTransaction(transType);
        }
        else if (key.equals("CheckInBook")) {
            doCheckInTransaction();
        }
        else if (key.equals("CheckOutBook")) {
            doCheckOutTransaction();
        }
        else if (key.equals("Login") == true) {
            if (value != null) {
                loginErrorMessage = "";

                boolean flag = loginLibrarian((Properties)value);
                if (flag == true) {
                    createAndShowLibrarianView();
                }
            }
        }
        else if (key.equals("CancelTransaction") == true) {
            createAndShowLibrarianView();
        }
        else if (key.equals("Done") == true) {
            System.exit(0);
        }

        myRegistry.updateSubscribers(key, this);
    }

    public boolean loginLibrarian(Properties props) {
        try {
            String bIdSent = props.getProperty("BannerId");
            String pwSent = props.getProperty("Password");

            myWorker = new Worker(bIdSent);
            boolean flag = myWorker.matchPassword(pwSent);
            if (flag) return true;
            else {
                loginErrorMessage = "ERROR: Wrong password";
                return false;
            }
        }
        catch (InvalidPrimaryKeyException ex) {
            loginErrorMessage = "ERROR: " + ex.getMessage();
            return false;
        }
        catch (MultiplePrimaryKeysException excep) {
            loginErrorMessage = "ERROR: " + excep.getMessage();
            System.out.println(excep.toString());
            return false;
        }

    }

    private void createAndShowLoginView() {
        Scene currentScene = (Scene)myViews.get("LoginView");

        if (currentScene == null) {
            View newView = ViewFactory.createView("LoginView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("LoginView", currentScene);
        }

        // make the view visible by installing it into the frame
        swapToView(currentScene);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value) {
        // DEBUG System.out.println("Librarian.updateState: key: " + key);

        stateChangeRequest(key, value);
    }

    /**
     * Create a Transaction depending on the Transaction type
     */
    //----------------------------------------------------------
    public void doTransaction(String transactionType) {
        try {
            Transaction trans = TransactionFactory.createTransaction(transactionType);

            trans.subscribe("CancelTransaction", this);
            trans.stateChangeRequest("DoYourJob", "");
        }
        catch (Exception ex) {
            transactionErrorMessage = "FATAL ERROR: TRANSACTION FAILURE: Unrecognized transaction!!";
            new Event(Event.getLeafLevelClassName(this), "createTransaction",
                    "Transaction Creation Failure: Unrecognized transaction " + ex.toString(),
                    Event.ERROR);
        }
    }

    //----------------------------------------------------------
    public void doCheckInTransaction() {
        try {
            Transaction trans = TransactionFactory.createTransaction("CheckInBook");

            trans.subscribe("CancelTransaction", this);
            trans.stateChangeRequest("SetWorker", myWorker);
            trans.stateChangeRequest("DoYourJob", "");
        }
        catch (Exception ex) {
            transactionErrorMessage = "FATAL ERROR: TRANSACTION FAILURE: Unrecognized transaction!!";
            new Event(Event.getLeafLevelClassName(this), "createTransaction",
                    "Transaction Creation Failure: Unrecognized transaction " + ex.toString(),
                    Event.ERROR);
        }
    }

    //----------------------------------------------------------
    public void doCheckOutTransaction() {
        try {
            Transaction trans = TransactionFactory.createTransaction("CheckOutBook");

            trans.subscribe("CancelTransaction", this);
            trans.stateChangeRequest("SetWorker", myWorker);
            trans.stateChangeRequest("DoYourJob", "");
        }
        catch (Exception ex) {
            transactionErrorMessage = "FATAL ERROR: TRANSACTION FAILURE: Unrecognized transaction!!";
            new Event(Event.getLeafLevelClassName(this), "createTransaction",
                    "Transaction Creation Failure: Unrecognized transaction " + ex.toString(),
                    Event.ERROR);
        }
    }

    //------------------------------------------------------------
    private void createAndShowLibrarianView() {
        Scene currentScene = (Scene)myViews.get("LibrarianView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("LibrarianView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("LibrarianView", currentScene);
        }
        swapToView(currentScene);
    }


    /** Register objects to receive state updates. */
    //----------------------------------------------------------
    public void subscribe(String key, IView subscriber) {
        // DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
        // forward to our registry
        myRegistry.subscribe(key, subscriber);
    }

    /** Unregister previously registered objects. */
    //----------------------------------------------------------
    public void unSubscribe(String key, IView subscriber) {
        // DEBUG: System.out.println("Cager.unSubscribe");
        // forward to our registry
        myRegistry.unSubscribe(key, subscriber);
    }



    //-----------------------------------------------------------------------------
    public void swapToView(Scene newScene) {

        if (newScene == null) {
            System.out.println("Librarian.swapToView(): Missing view for display");
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }

        myStage.setScene(newScene);
        myStage.sizeToScene();

        //Place in center
        WindowPosition.placeCenter(myStage);
    }
}

