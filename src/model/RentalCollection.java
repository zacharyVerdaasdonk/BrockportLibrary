// specify the package
package model;

// system imports
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

public class RentalCollection extends EntityBase {
    private static final String myTableName = "Rental";

    private Vector<Rental> rentalList;

    public RentalCollection() {
        super(myTableName);
        rentalList = new Vector<>();
    }

    public void findLateRentals() throws Exception {
        Calendar rightNow = Calendar.getInstance();
        Date todayDate = rightNow.getTime();
        String todayDateText = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);
        String query = "SELECT * FROM Rental WHERE ((DueDate < CURRENT_DATE) AND ((CheckinDate IS NULL) OR (CheckinDate = '')))";
        helper(query);
    }

    public void findCheckedOutBooks() throws Exception {
        String query = "SELECT * FROM " + myTableName +  " WHERE ((CheckinDate IS NULL) OR (CheckinDate = ''))";
        helper(query);
    }

    private void helper(String query) throws Exception {
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            rentalList = new Vector<>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties nextRentalData = (Properties)allDataRetrieved.elementAt(cnt);

                Rental rent = new Rental(nextRentalData);

                if (rent != null) {
                    addRental(rent);
                }
            }
        }
        else {
            throw new Exception("No rental files found.");
        }
    }

    public Rental retrieve(int rentalId) {
        Rental retValue = null;
        for (int cnt = 0; cnt < rentalList.size(); cnt++) {
            Rental nextRental = rentalList.elementAt(cnt);
            String nextRentalId = (String)nextRental.getState("Id");
            if (nextRentalId.equals(""+rentalId) == true) {
                retValue = nextRental;
                break;
            }
        }
        return retValue;
    }

    public int getSize() {
        if(rentalList != null) return rentalList.size();
        return 0;
    }

    public Rental elementAt(int cnt) {
        if (rentalList != null) {
            if ((cnt >= 0) && (cnt < rentalList.size()))
                return rentalList.get(cnt);
        }
        return null;
    }
    private void addRental(Rental a) {
        int index = findIndexToAdd(a);
        rentalList.insertElementAt(a, index); // To build up a collection sorted on some key
    }

    private int findIndexToAdd(Rental a) {
        int low = 0;
        int high = rentalList.size() - 1;
        int middle;

        while (low <= high) {
            middle = (low + high) / 2;

            Rental midSession = (Rental)rentalList.elementAt(middle);

            int result = Rental.compare(a, midSession);

            if (result == 0) {
                return middle;
            }
            else if (result < 0) {
                high = middle - 1;
            }
            else {
                low = middle + 1;
            }
        }
        return low;
    }

    public Object getState(String key) {
        if (key.equals("Rentals") == true)
            return rentalList;
        return "";
    }

    public void stateChangeRequest(String key, Object value) {
    }

    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }

    public void display() {
        if (rentalList != null) {
            for (int cnt = 0; cnt < rentalList.size(); cnt++) {
                Rental r = rentalList.get(cnt);
                System.out.println("---------");
                System.out.println(r.toString());
            }
        }
    }
}
