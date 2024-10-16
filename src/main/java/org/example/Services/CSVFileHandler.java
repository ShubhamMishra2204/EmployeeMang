package org.example.Services;


import org.example.Config.Config;
import org.example.Entity.Employee;
import org.example.Repositories.MyCollection;
import org.example.Services.Interfaces.MyFileHandler;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVFileHandler implements MyFileHandler {
    private String filePath;

    public CSVFileHandler(){}


    public CSVFileHandler(String filePath) {
        this.filePath = filePath;
    }

    public void read() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into parts
                String[] parts = line.split(",");

                // Skip header or lines with insufficient parts
                if (parts.length < 4) {
                    System.out.println("Skipping line due to insufficient data: " + line);
                    continue;
                }

                // Extract firstName and check for header
                String firstName = parts[0].trim();
                if (firstName.equals("firstName")) {
                    continue; // Skip header line
                }

                // Extract lastName, dateOfBirth, and experience
                String lastName = parts[1].trim();
                String date = parts[2].trim();
                Date dateOfBirth = dateFormat.parse(date);
                double experience = Double.parseDouble(parts[3].trim());

                // Create Employee object and add to collection
                Employee employee = new Employee(firstName, lastName, dateOfBirth, experience);
                MyCollection.add(employee);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("ParseException: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: " + e.getMessage());
        }
    }
    public void write() {
        try (BufferedWriter writer = new BufferedWriter(new  FileWriter(Config.writeCsvPath))) {

            writer.write("firstName,lastName,dateOfBirth,experience");
            writer.newLine();

            for (int i = 0; i < 100; i++) {
                Employee person = MyCollection.get();
                if(person != null){


                    String csvRecord = String.format(
                            "%s,%s,%s,%.2f",
                            person.getFirstName(),
                            person.getLastName(),
                            new SimpleDateFormat("MM/dd/yyyy").format(person.getDateOfBirth()),
                            person.getExperience()
                    );

                    writer.write(csvRecord);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}