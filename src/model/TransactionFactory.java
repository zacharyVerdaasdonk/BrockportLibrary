// specify the package
package model;

// system imports
import java.util.Vector;

// project imports

/** The class containing the TransactionFactory for the ISLO Data Management application
 */
//==============================================================
public class TransactionFactory {

    /**
     *
     */
    //----------------------------------------------------------
    public static Transaction createTransaction(String transType) throws Exception {
        Transaction retValue = null;

        if (transType.equals("InsertBook") == true) {
            retValue = new InsertBookTransaction();
        }
        else
        if (transType.equals("ModifyBook") == true) {
            retValue = new ModifyBookTransaction();
        }
        else
        if (transType.equals("DeleteBook") == true) {
            retValue = new DeleteBookTransaction();
        }
        else
        if (transType.equals("InsertStudent") == true) {
            retValue = new InsertStudentTransaction();
        }
        else
        if (transType.equals("ModifyStudent") == true) {
            retValue = new ModifyStudentTransaction();
        }
        else
        if (transType.equals("DeleteStudent") == true) {
            retValue = new DeleteStudentTransaction();
        }
        else
        if (transType.equals("InsertWorker") == true) {
            retValue = new InsertWorkerTransaction();
        }
        else
        if (transType.equals("ModifyWorker") == true) {
            retValue = new ModifyWorkerTransaction();
        }
        else
        if (transType.equals("DeleteWorker") == true) {
            retValue = new DeleteWorkerTransaction();
        }
        else
        if (transType.equals("CheckOutBook") == true) {
            retValue = new CheckOutBookTransaction();
        }
        else
        if (transType.equals("CheckInBook") == true) {
            retValue = new CheckInABookTransaction();
        }
        else
        if (transType.equals("RunDelCheck") == true) {
            retValue = new RunDelinquencyCheckTransaction();
        }
        else
        if (transType.equals("BooksCheckedOut") == true) {
            retValue = new BooksCheckedOutTransaction();
        }
        else
        if (transType.equals("StudentsWithBooks") == true) {
            retValue = new StudentsWithBooksCheckedOutTransaction();
        }
        return retValue;
    }
}
