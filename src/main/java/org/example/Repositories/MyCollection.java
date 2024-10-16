package org.example.Repositories;

import org.example.Config.Config;
import org.example.Controllers.MyController;
import org.example.Entity.Employee;

import javax.swing.*;
import java.util.Date;


public class MyCollection {


    private static Employee[] employees = new Employee[Config.maxEmployeeCnt];
    private static int writeCounter = 0;
    private static int readCounter = 0;

    public static synchronized void add(Employee employee) {
        if (writeCounter < Config.maxEmployeeCnt) {
            employees[writeCounter] = employee;
            writeCounter++;
        }
    }

    public static synchronized Employee get() {
        if (readCounter < Config.maxEmployeeCnt) {
            return employees[readCounter++];
        }
        return new Employee("firstName", "lastName", new Date(), 50);
    }

    public int getWriteCounter() {
        return writeCounter;
    }
}