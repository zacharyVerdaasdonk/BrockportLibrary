package model;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;
import sun.java2d.pipe.SpanShapeRenderer;

public class WorkerTableModel {
    private final SimpleStringProperty bannerId;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty email;
    private final SimpleStringProperty credentials;
    private final SimpleStringProperty dateOfLatestCredentialsStatus;
    private final SimpleStringProperty dateOfHire;
    private final SimpleStringProperty status;

    public WorkerTableModel(Vector<String> patronInformation){
        bannerId = new SimpleStringProperty(patronInformation.elementAt(0));
        firstName = new SimpleStringProperty(patronInformation.elementAt(1));
        lastName = new SimpleStringProperty(patronInformation.elementAt(2));
        phone = new SimpleStringProperty(patronInformation.elementAt(3));
        email = new SimpleStringProperty(patronInformation.elementAt(4));
        credentials = new SimpleStringProperty(patronInformation.elementAt(5));
        dateOfLatestCredentialsStatus = new SimpleStringProperty(patronInformation.elementAt(6));
        dateOfHire = new SimpleStringProperty(patronInformation.elementAt(7));
        status = new SimpleStringProperty(patronInformation.elementAt(8));
    }

    public String getBannerId(){
        return bannerId.get();
    }
    public void setBannerId(String number){
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
    public String getCredentials(){
        return credentials.get();
    }
    public void setCredentials(String words){
        credentials.set(words);
    }
    public String getDateOfLatestCredentialsStatus(){
        return dateOfLatestCredentialsStatus.get();
    }
    public void setDateOfLatestCredentialsStatus(String words){
        dateOfLatestCredentialsStatus.set(words);
    }
    public String getDateOfHire(){
        return dateOfHire.get();
    }
    public void setDateOfHire(String words){
        dateOfHire.set(words);
    }
    public String getStatus(){
        return status.get();
    }
    public void setStatus(String words){
        status.set(words);
    }
}