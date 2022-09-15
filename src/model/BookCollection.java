// specify the package
package model;

// system imports
import java.util.Properties;
import java.util.Vector;

public class BookCollection extends EntityBase {
    private static final String myTableName = "Book";

    private Vector<Book> bookList;

    public BookCollection() {
        super(myTableName);
        bookList = new Vector<>();
    }

    /*public void findBooksOlderThanDate(String year) throws Exception {
        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear < " + year + ")";
        helper(query);
    }*/

    /*public void findBooksNewerThanDate(String year) throws Exception {
        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear > " + year + ")";
        helper(query);
    }*/

    public void findBooksWithTitleLike(String title) throws Exception {
        String query;
        if(title.length() == 0) query = "SELECT * FROM " + myTableName;
        else query = "SELECT * FROM " + myTableName + " WHERE Title LIKE '%" + title + "%'";
        helper(query);
    }

    /*public void findBooksWithAuthorLike(String author) throws Exception {
        String query = "SELECT * FROM " + myTableName + " WHERE author LIKE '%" + author + "%'";
        helper(query);
    }*/

    private void helper(String query) throws Exception {
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            bookList = new Vector<>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties nextBookData = (Properties)allDataRetrieved.elementAt(cnt);

                Book book = new Book(nextBookData);

                if (book != null) {
                    addBook(book);
                }
            }
        }
        else {
            throw new Exception("No books found.");
        }
    }

    public Book retrieve(int bookBarcode) {
        Book retValue = null;
        for (int cnt = 0; cnt < bookList.size(); cnt++) {
            Book nextBook = bookList.elementAt(cnt);
            String nextBookBarcode = (String)nextBook.getState("Barcode");
            if (nextBookBarcode.equals(""+bookBarcode) == true) {
                retValue = nextBook;
                break;
            }
        }
        return retValue;
    }

    public void addBook(Book a) {
        int index = findIndexToAdd(a);
        bookList.insertElementAt(a, index); // To build up a collection sorted on some key
    }

    private int findIndexToAdd(Book a) {
        int low = 0;
        int high = bookList.size() - 1;
        int middle;

        while (low <= high) {
            middle = (low + high) / 2;

            Book midSession = (Book)bookList.elementAt(middle);

            int result = Book.compare(a, midSession);

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
        if (key.equals("Books") == true)
            return bookList;
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
        if (bookList != null) {
            for (int cnt = 0; cnt < bookList.size(); cnt++) {
                Book b = bookList.get(cnt);
                System.out.println("---------");
                System.out.println(b.toString());
            }
        }
    }

}
