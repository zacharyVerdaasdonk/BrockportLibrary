//specify the package
package model;

//system imports
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

//project imports
import exception.InvalidPrimaryKeyException;

public class Student extends EntityBase {
    private static final String myTableName = "StudentBorrower";

    protected Properties dependencies;

    //GUI Components
    private String updateStatusMessage = "";
    private boolean oldFlag = false;

    //constructor for this class
    public Student(String BannerId) throws InvalidPrimaryKeyException {
        super(myTableName);

        String query = "SELECT * FROM " + myTableName + " WHERE (BannerId = " + BannerId + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        //You must get one Student Borrower at least
        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            //There should be EXACTLY one Student Borrower. More than that is an error
            if (size != 1) {
                throw new InvalidPrimaryKeyException("Multiple Student Borrowers matching Id : " + BannerId + " found.");
            } else {
                //Copy all the retrieved data into persistent state
                Properties retrievedPatronData = (Properties) allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedPatronData.propertyNames();
                while (allKeys.hasMoreElements() == true) {
                    String nextKey = (String) allKeys.nextElement();
                    String nextValue = retrievedPatronData.getProperty(nextKey);

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
                oldFlag = true;
            }
        }
        else{
            throw new InvalidPrimaryKeyException("No Student Borrower matching BannerId : "
            + BannerId + " found.");
        }
    }

    public Student(Properties props){
        super(myTableName);
        oldFlag = false;

        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while(allKeys.hasMoreElements() == true){
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if(nextValue != null){
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    public void update() {
        try {
            if (oldFlag == true) {
                Properties whereClause = new Properties();
                whereClause.setProperty("BannerId", persistentState.getProperty("BannerId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Student Borrower data updated successfully in database!";
            }
            else {
                insertPersistentState(mySchema, persistentState);
                oldFlag = true;
                updateStatusMessage = "Student Borrower data installed successfully in database!";
            }
        }
        catch (SQLException ex) {
            updateStatusMessage = "ERROR: Student with this BannerID already exists!";
            System.out.println("Error in installing student borrower data in database!");
        }
    }

    protected void setDelinquent() {
        Calendar rightNow = Calendar.getInstance();
        Date todayDate = rightNow.getTime();
        String todayDateText = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);
        persistentState.setProperty("DateOfLatestBorrowerStatus", todayDateText);
        persistentState.setProperty("BorrowerStatus", "Delinquent");

    }

    public Vector<String> getEntryListView() {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("BannerId"));
        v.addElement(persistentState.getProperty("FirstName"));
        v.addElement(persistentState.getProperty("LastName"));
        v.addElement(persistentState.getProperty("Phone"));
        v.addElement(persistentState.getProperty("Email"));
        v.addElement(persistentState.getProperty("BorrowerStatus"));
        v.addElement(persistentState.getProperty("DateOfLatestBorrowerStatus"));
        v.addElement(persistentState.getProperty("DateOfRegistration"));
        v.addElement(persistentState.getProperty("Notes"));
        v.addElement(persistentState.getProperty("Status"));

        return v;
    }

    public String toString() {
        return "BannerID: " + persistentState.getProperty("BannerId") + "; FirstName: " +
                persistentState.getProperty("FirstName")  + "; LastName: " +
                persistentState.getProperty("LastName");
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

    public static int compare(Student a, Student b) {
        String aNum = (String)a.getState("BannerId");
        String bNum = (String)b.getState("BannerId");

        return aNum.compareTo(bNum);
    }

    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
