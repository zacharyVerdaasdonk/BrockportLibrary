// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;

public class BookBarcodePrefix extends EntityBase {
    private static final String myTableName = "BookBarcodePrefix";

    protected Properties dependencies;

    // GUI Components
    private String updateStatusMessage = "";

    // constructor for this class
    //----------------------------------------------------------
    public BookBarcodePrefix(String preVal) throws InvalidPrimaryKeyException {
        super(myTableName);

        String query = "SELECT * FROM " + myTableName + " WHERE (PrefixValue = " + preVal + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        // You must get one book barcode prefix at least
        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one book barcode prefix. More than that is an error
            if (size != 1) {
                throw new InvalidPrimaryKeyException("Multiple book barcode prefix's matching prefix value : " + preVal + " found.");
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
            }
        }
        // If no book is found for this barcode, throw an exception
        else {
            throw new InvalidPrimaryKeyException("No book matching prefix value : " + preVal + " found.");
        }
    }

    //--------------------------------------------------------------------------
    public BookBarcodePrefix(Properties props) {
        super(myTableName);

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

    //--------------------------------------------------------------------------
    public Vector<String> getEntryListView() {
        Vector<String> v = new Vector<String>();

        //change this as needed for displaying it when needed
        v.addElement(persistentState.getProperty("PrefixValue"));
        v.addElement(persistentState.getProperty("Discipline"));

        return v;
    }

    //--------------------------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;
        return persistentState.getProperty(key);
    }

    //---------------------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        if (value != null) {
            persistentState.setProperty(key, (String)value);
        }
    }


    //----------------------------------------------------------------------------
    public String toString() {
        return "Prefix Value: " + persistentState.getProperty("Prefix Value") + "; Discipline: " +
                persistentState.getProperty("Discipline");
    }

    public void display() {
        System.out.println(toString());
    }

    public static int compare(Book a, Book b) {
        String aNum = (String)a.getState("PrefixValue");
        String bNum = (String)b.getState("PrefixValue");

        return aNum.compareTo(bNum);
    }

    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
