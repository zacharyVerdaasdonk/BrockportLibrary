//specify the package
package model;

//system imports
import java.util.Properties;
import java.util.Vector;

public class WorkerCollection extends EntityBase{
    private static final String myTableName = "Worker";

    private Vector<Worker> workerList;

    public WorkerCollection(){
        super(myTableName);
        workerList = new Vector<>();
    }

    private void helper(String query) throws Exception {
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            workerList = new Vector<>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties nextWorkerData = (Properties)allDataRetrieved.elementAt(cnt);

                Worker worker = new Worker(nextWorkerData);

                if (worker != null) {
                    addWorker(worker);
                }
            }
        }
        else {
            throw new Exception("No workers found.");
        }
    }

    public void findWorkersWithFirstNameLastNameLike(String first, String last) throws Exception {
        String query;
        if(first.length() == 0 && last.length() == 0) query = "SELECT * FROM " + myTableName;
        else query = "SELECT * FROM " + myTableName + " WHERE (FirstName LIKE '%" + first + "%' AND LastName LIKE '%" + last + "%')";
        helper(query);
    }

    public Worker retrieve(int bannerId) {
        Worker retValue = null;
        for (int cnt = 0; cnt < workerList.size(); cnt++) {
            Worker nextWorker = workerList.elementAt(cnt);
            String nextWorkerId = (String)nextWorker.getState("BannerId");
            if (nextWorkerId.equals(""+bannerId) == true) {
                retValue = nextWorker;
                break;
            }
        }
        return retValue;
    }

    private void addWorker(Worker a) {
        int index = findIndexToAdd(a);
        workerList.insertElementAt(a, index); // To build up a collection sorted on some key
    }

    private int findIndexToAdd(Worker a) {
        int low = 0;
        int high = workerList.size() - 1;
        int middle;

        while (low <= high) {
            middle = (low + high) / 2;

            Worker midSession = (Worker)workerList.elementAt(middle);

            int result = Worker.compare(a, midSession);

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
        if (key.equals("Workers") == true)
            return workerList;
        return "";
    }

    public void stateChangeRequest(String key, Object value) {

    }


    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }

}
