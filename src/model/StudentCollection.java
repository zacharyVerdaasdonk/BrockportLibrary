//specify the package
package model;

//system imports
import java.util.Properties;
import java.util.Vector;

public class StudentCollection extends EntityBase {
    private static final String myTableName = "StudentBorrower";

    private Vector<Student> studentList;

    public StudentCollection(){
        super(myTableName);
        studentList = new Vector<>();
    }

    public void findStudentsWithFirstNameLastNameLike(String first, String last) throws Exception {
        String query;
        if(first.length() == 0 && last.length() == 0) query = "SELECT * FROM " + myTableName;
        else query = "SELECT * FROM " + myTableName + " WHERE (FirstName LIKE '%" + first + "%' AND LastName LIKE '%" + last + "%')";
        helper(query);
    }

    public void findStudentsWithFirstNameLastNameLikeAndActive(String first, String last) throws Exception {
        String query;
        if(first.length() == 0 && last.length() == 0) query = "SELECT * FROM " + myTableName + " WHERE Status = 'Active'";
        else query = "SELECT * FROM " + myTableName + " WHERE (FirstName LIKE '%" + first + "%' AND LastName LIKE '%" + last + "%' AND Status = 'Active')";
        helper(query);
    }

    private void helper(String query) throws Exception {
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            studentList = new Vector<>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties nextWorkerData = (Properties)allDataRetrieved.elementAt(cnt);

                Student student = new Student(nextWorkerData);

                if (student != null) {
                    addStudent(student);
                }
            }
        }
        else {
            throw new Exception("No students found.");
        }
    }

    public Student retrieve(int bannerId) {
        Student retValue = null;
        for (int cnt = 0; cnt < studentList.size(); cnt++) {
            Student nextStudent = studentList.elementAt(cnt);
            String nextStudentId = (String)nextStudent.getState("BannerId");
            if (nextStudentId.equals(""+bannerId) == true) {
                retValue = nextStudent;
                break;
            }
        }
        return retValue;
    }

    public void addStudent(Student a) {
        int index = findIndexToAdd(a);
        studentList.insertElementAt(a, index); // To build up a collection sorted on some key
    }

    private int findIndexToAdd(Student a) {
        int low = 0;
        int high = studentList.size() - 1;
        int middle;

        while (low <= high) {
            middle = (low + high) / 2;

            Student midSession = (Student)studentList.elementAt(middle);

            int result = Student.compare(a, midSession);

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
        if (key.equals("Students") == true) {
            return studentList;
        }

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
        if (studentList != null) {
            for (int cnt = 0; cnt < studentList.size(); cnt++) {
                Student s = studentList.get(cnt);
                System.out.println("---------");
                System.out.println(s.toString());
            }
        }
    }
}
