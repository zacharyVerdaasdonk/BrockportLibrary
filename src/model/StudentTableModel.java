package model;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;
import sun.java2d.pipe.SpanShapeRenderer;

public class StudentTableModel {
    private final SimpleStringProperty bannerId;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty email;
    private final SimpleStringProperty borrowerStatus;
    private final SimpleStringProperty dateOfLatestBorrowerStatus;
    private final SimpleStringProperty dateOfRegistration;
    private final SimpleStringProperty notes;
    private final SimpleStringProperty status;

    public StudentTableModel(Vector<String> patronInformation){
        bannerId = new SimpleStringProperty(patronInformation.elementAt(0));
        firstName = new SimpleStringProperty(patronInformation.elementAt(1));
        lastName = new SimpleStringProperty(patronInformation.elementAt(2));
        phone = new SimpleStringProperty(patronInformation.elementAt(3));
        email = new SimpleStringProperty(patronInformation.elementAt(4));
        borrowerStatus = new SimpleStringProperty(patronInformation.elementAt(5));
        dateOfLatestBorrowerStatus = new SimpleStringProperty(patronInformation.elementAt(6));
        dateOfRegistration = new SimpleStringProperty(patronInformation.elementAt(7));
        notes = new SimpleStringProperty(patronInformation.elementAt(8));
        status = new SimpleStringProperty(patronInformation.elementAt(9));
    }

    public String getBannerId(){
        return bannerId.get();
    }
    public void setPatronId(String number){
        bannerId.set(number);
    }
    public String getFirstName(){
        return firstName.get();
    }
    public void setFirstName(String words){
        firstName.set(words);
    }
    public String getLastName(){
        return lastName.get();
    }
    public void setLastName(String words){
        lastName.set(words);
    }
    public String getPhone(){
        return phone.get();
    }
    public void setPhone(String number){
        phone.set(number);
    }
    public String getEmail(){
        return email.get();
    }
    public void setEmail(String words){
        email.set(words);
    }
    public String getBorrowerStatus(){
        return borrowerStatus.get();
    }
    public void setBorrowerStatus(String words){
        borrowerStatus.set(words);
    }
    public String getdateOfLatestBorrowerStatus(){
        return dateOfLatestBorrowerStatus.get();
    }
    public void setdateOfLatestBorrowerStatus(String words){
        dateOfLatestBorrowerStatus.set(words);
    }
    public String getDateOfRegistration(){
        return dateOfRegistration.get();
    }
    public void setDateOfRegistration(String words){
        dateOfRegistration.set(words);
    }
    public String getNotes(){
        return notes.get();
    }
    public void setNotes(String words){
        notes.set(words);
    }
    public String getStatus(){
        return status.get();
    }
    public void setStatus(String words){
        status.set(words);
    }

}
