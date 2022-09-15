package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

    public static View createView(String viewName, IModel model) {
        if (viewName.equals("LibrarianView") == true) {
            return new LibrarianView(model);
        } else if (viewName.equals("LoginView") == true) {
            return new LoginView(model);
        } else if (viewName.equals("InsertBookView") == true) {
            return new InsertBookView(model);
        } else if (viewName.equals("ModifyBookView") == true) {
            return new ModifyBookView(model);
        } else if (viewName.equals("DeleteBookView") == true) {
            return new DeleteBookView(model);
        } else if (viewName.equals("SearchBookView") == true) {
            return new SearchBookView(model);
        } else if (viewName.equals("BookCollectionView") == true) {
            return new BookCollectionView(model);
        } else if (viewName.equals("InsertStudentView") == true) {
            return new InsertStudentView(model);
        } else if (viewName.equals("ModifyStudentView") == true) {
            return new ModifyStudentView(model);
        } else if (viewName.equals("DeleteStudentView") == true) {
            return new DeleteStudentView(model);
        } else if (viewName.equals("SearchStudentView") == true) {
            return new SearchStudentView(model);
        } else if (viewName.equals("StudentCollectionView") == true) {
            return new StudentCollectionView(model);
        } else if (viewName.equals("InsertWorkerView") == true) {
            return new InsertWorkerView(model);
        } else if (viewName.equals("ModifyWorkerView") == true) {
            return new ModifyWorkerView(model);
        } else if (viewName.equals("DeleteWorkerView") == true) {
            return new DeleteWorkerView(model);
        } else if (viewName.equals("SearchWorkerView") == true) {
            return new SearchWorkerView(model);
        } else if (viewName.equals("WorkerCollectionView") == true) {
            return new WorkerCollectionView(model);
        } else if (viewName.equals("RunDelinquencyCheckView") == true) {
            return new RunDelinquencyCheckView(model);
        } else if (viewName.equals("CheckOutSearchView") == true) {
            return new CheckOutSearchView(model);
        } else if (viewName.equals("StudentsWithBooksCheckedOutView") == true) {
            return new StudentsWithBooksCheckedOutView(model);
        } else if (viewName.equals("BooksCheckedOutView") == true) {
            return new BooksCheckedOutView(model);
        } else if (viewName.equals("BookDueDateView") == true) {
            return new BookDueDateView(model);
        } else if (viewName.equals("BookCollectionView") == true) {
            return new BookCollectionView(model);
        } else if(viewName.equals("StudentsWithBooksCheckedOutCollectionView")){
            return new StudentsWithBooksCheckedOutCollectionView(model);
        } else return null;
    }
}
