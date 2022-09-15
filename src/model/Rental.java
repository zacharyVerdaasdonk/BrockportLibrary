// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;

public class Rental extends EntityBase {
    private static final String myTableName = "Rental";

    protected Properties dependencies;

    // GUI Components
    private String updateStatusMessage = "";
    private boolean oldFlag = false;

    // constructor for this class
    //----------------------------------------------------------
    public Rental(String rentalId) throws InvalidPrimaryKeyException {
        super(myTableName);

        String query = "SELECT * FROM " + myTableName + " WHERE (Id = " + rentalId + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        // You must get one book at least
        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one book. More than that is an error
            if (size != 1) {
                throw new InvalidPrimaryKeyException("Multiple rental files matching Id : " + rentalId + " found.");
            }
            else {
                // copy all the retrieved data into persistent state
                Properties retrievedBookData = (Properties)allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedBookData.propertyNames();
                while (allKeys.hasMoreElements() == true) {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedBookData.getProperty(nextKey);

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
                oldFlag = true;
            }
        }
        // If no book is found for this barcode, throw an exception
        else {
            throw new InvalidPrimaryKeyException("No rental file matching Id : " + rentalId + " found.");
        }
    }

    public Rental(String barcode, boolean flag) throws InvalidPrimaryKeyException {
        super(myTableName);
        // construct a query that checks the BookId in Rental equals barcode AND CheckinDate IS NULL Or CheckinDate = ''

        String query = "SELECT * FROM " + myTableName +
                " WHERE (BookId = " + barcode + ") AND ((CheckinDate IS NULL ) OR (CheckinDate = ''))";

        Vector allDataRetrieved = getSelectQueryResult(query);

        // You must get one book at least
        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one book. More than that is an error
            if (size > 1) {
                throw new InvalidPrimaryKeyException("Multiple rental files matching Barcode : " + barcode +
                        " with Null CheckInDate found.");
            } else if (size == 1) {
                // copy all the retrieved data into persistent state
                Properties retrievedBookData = (Properties) allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedBookData.propertyNames();
                while (allKeys.hasMoreElements() == true) {
                    String nextKey = (String) allKeys.nextElement();
                    String nextValue = retrievedBookData.getProperty(nextKey);

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
                oldFlag = true;
            }
            else {
                throw new InvalidPrimaryKeyException("Matt is nuts");
            }
        }
        // If no book is found for this barcode, throw an exception
        else {
            throw new InvalidPrimaryKeyException("No rental file matching barcode : " + barcode + " found.");
        }
    }

    public Rental(Properties props) {
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

    public void update() {
        try {
            if (oldFlag == true) {
                Properties whereClause = new Properties();
                whereClause.setProperty("Id", persistentState.getProperty("Id"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Rental data updated successfully in database!";
            }
            else {
                int idRet = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("Id", idRet + "");
                oldFlag = true;
                updateStatusMessage = "Rental data installed successfully in database!";
            }
        }
        catch (SQLException ex) {
            System.out.println("Error in installing rental data in database!");
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
        catch (Exception excep) {
            System.out.println(excep.toString());
            excep.printStackTrace();
        }
    }

    /*public Vector<String> getEntryListView() {
        Vector<String> v = new Vector<String>();

        //change this as needed for displaying it when needed
        v.addElement(persistentState.getProperty("Id"));

        return v;
    }*/

    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;

        return persistentState.getProperty(key);
    }

    public void stateChangeRequest(String key, Object value) {
        if (value != null) {
            persistentState.setProperty(key, (String)value);
        }
    }

    public String toString() {
        return "Rental Id: " + persistentState.getProperty("Id") + "; Borrower Id: " +
                persistentState.getProperty("BorrowerId")  + "; Book Id: " +
                persistentState.getProperty("BookId") + "; Checkout Date: " +
                persistentState.getProperty("CheckoutDate") + "; Due Date: " +
                persistentState.getProperty("DueDate");
    }

    public void display() {
        System.out.println(toString());
    }

    public static int compare(Rental a, Rental b) {
        String aNum = (String)a.getState("Id");
        String bNum = (String)b.getState("Id");

        return aNum.compareTo(bNum);
    }

    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
