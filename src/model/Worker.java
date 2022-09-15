// specify the package
package model;

// system imports`
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;
import exception.PasswordMismatchException;
import database.*;

import impresario.IView;

/** The class containing the Worker for the Brockport Library Application */
//==============================================================
public class Worker extends EntityBase implements IView {
    private static final String myTableName = "Worker";

    protected Properties dependencies;

    // GUI Components
    private String updateStatusMessage = "";
    private boolean oldFlag = false;

    // constructor for this class
    //----------------------------------------------------------
    public Worker(Properties props) {
        super(myTableName);
        oldFlag = false;

        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true) {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    //----------------------------------------------------------
    public Worker(String idToQuery) throws InvalidPrimaryKeyException, MultiplePrimaryKeysException {
        super(myTableName);

        String query = "SELECT * FROM " + myTableName + " WHERE (BannerId = '" + idToQuery + "')";

        Vector allDataRetrieved =  getSelectQueryResult(query);

        // You must get one worker at least
        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();
            // There should be EXACTLY one worker. More than that is an error
            if (size > 1) {

                throw new MultiplePrimaryKeysException("Multiple workers matching BannerId : " + idToQuery + " found.");
            }
            else if (size == 1) {
                // copy all the retrieved data into persistent state
                Properties retrievedCustomerData = (Properties)allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedCustomerData.propertyNames();
                while (allKeys.hasMoreElements() == true) {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedCustomerData.getProperty(nextKey);

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
                oldFlag = true;
            }
            else
            {
                throw new InvalidPrimaryKeyException("No worker matching BannerId : " + idToQuery + " found.");
            }
        }
        // If no worker found for this username, throw an exception
        else {
            throw new InvalidPrimaryKeyException("No worker matching BannerId : " + idToQuery + " found.");
        }
    }

    //---------------------------------------------------------------------------------------
    public boolean matchPassword(String providedPwd) {
        String dbPassword = persistentState.getProperty("Password");
        return providedPwd.equals(dbPassword);
    }

    //---------------------------------------------------------------------------------------
    public void update() {
        try {
            if (oldFlag == true) {
                Properties whereClause = new Properties();
                whereClause.setProperty("BannerId", persistentState.getProperty("BannerId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Worker data updated successfully in database!";
            }
            else {
                insertPersistentState(mySchema, persistentState);
                oldFlag = true;
                updateStatusMessage = "Worker data installed successfully in database!";
            }
        }
        catch (SQLException ex) {
            updateStatusMessage = "ERROR: Worker with this BannerID already exists!";
            System.out.println("Error in installing worker data in database!");
        }
    }

    public Vector<String> getEntryListView() {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("BannerId"));
        v.addElement(persistentState.getProperty("FirstName"));
        v.addElement(persistentState.getProperty("LastName"));
        v.addElement(persistentState.getProperty("Phone"));
        v.addElement(persistentState.getProperty("Email"));
        v.addElement(persistentState.getProperty("Credentials"));
        v.addElement(persistentState.getProperty("DateOfLatestCredentialsStatus"));
        v.addElement(persistentState.getProperty("DateOfHire"));
        v.addElement(persistentState.getProperty("Status"));

        return v;
    }

    public static int compare(Worker a, Worker b) {
        String aNum = (String)a.getState("BannerId");
        String bNum = (String)b.getState("BannerId");

        return aNum.compareTo(bNum);
    }

    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("UpdateStatusMessage"))
        {
            return updateStatusMessage;
        }
        return persistentState.getProperty(key);
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        persistentState.setProperty(key, (String)value);

        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
}


