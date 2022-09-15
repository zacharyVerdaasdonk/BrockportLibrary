// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import exception.InvalidPrimaryKeyException;

public class Book extends EntityBase {
    private static final String myTableName = "Book";

    protected Properties dependencies;

    // GUI Components
    private String updateStatusMessage = "";
    private boolean oldFlag = false;

    // constructor for this class
    //----------------------------------------------------------
    public Book(String bookId) throws InvalidPrimaryKeyException {
        super(myTableName);

        String query = "SELECT * FROM " + myTableName + " WHERE (Barcode = " + bookId + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        // You must get one book at least
        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one book. More than that is an error
            if (size != 1) {
                throw new InvalidPrimaryKeyException("Multiple books matching barcode : " + bookId + " found.");
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
            throw new InvalidPrimaryKeyException("No book matching barcode : " + bookId + " found.");
        }
    }

    public Book(Properties props) throws MySQLIntegrityConstraintViolationException {
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
                whereClause.setProperty("Barcode", persistentState.getProperty("Barcode"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Book data updated successfully in database!";
            }
            else {
                insertPersistentState(mySchema, persistentState);
                oldFlag = true;
                updateStatusMessage = "Book data installed successfully in database!";
            }
        }
        catch (SQLException ex) {
            updateStatusMessage = "ERROR: Book with this barcode already exists!";
            System.out.println("Error in installing book data in database!");
            //System.out.println(ex.toString());
            //ex.printStackTrace();
        }
        catch (Exception excep) {
            System.out.println(excep.toString());
            excep.printStackTrace();
        }
    }

    public Vector<String> getEntryListView() {
        Vector<String> v = new Vector<String>();

        //change this as needed for displaying it when needed
        v.addElement(persistentState.getProperty("Barcode"));
        v.addElement(persistentState.getProperty("Title"));
        v.addElement(persistentState.getProperty("Author1"));

        return v;
    }

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
        return "Barcode: " + persistentState.getProperty("Barcode") + "; Title: " +
                persistentState.getProperty("Title")  + "; Author: " +
                persistentState.getProperty("Author1") + "; Status: " +
                persistentState.getProperty("Status");
    }

    public void display() {
        System.out.println(toString());
    }

    public static int compare(Book a, Book b) {
        String aNum = (String)a.getState("Barcode");
        String bNum = (String)b.getState("Barcode");

        return aNum.compareTo(bNum);
    }

    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
