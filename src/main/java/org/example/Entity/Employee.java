package org.example.Entity;
//package Entity;

import java.util.Date;

public class Employee {
    public String firstName;
    public  String lastName;
    public Date dateOfBirth;
    public double experience;

    public  Employee(String firstName,String lastName,Date dateOfBirth,double experience){
        this.firstName=firstName;
        this.lastName=lastName;
        this.dateOfBirth=dateOfBirth;
        this.experience=experience;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDateOfBirth(){
        return dateOfBirth;
    }
    public double getExperience(){
        return experience;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setDateOfBirth(Date dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    public void setExperience(double experience){
        this.experience = experience;
    }


}