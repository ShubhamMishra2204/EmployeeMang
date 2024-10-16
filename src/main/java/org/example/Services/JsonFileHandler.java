package org.example.Services;

import org.example.Config.Config;
import org.example.Entity.Employee;
import org.example.Repositories.MyCollection;
import org.example.Services.Interfaces.MyFileHandler;

import java.io.*;
        import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class JsonFileHandler implements MyFileHandler {
    private String filePath;

    public JsonFileHandler(String filePath) {
        this.filePath = filePath;
    }


    public void read() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Clean the line and parse the fields
                line = line.trim().replace("{", "").replace("}", "").replace("\"", "");
                String[] parts = line.split(",");

                String firstName = "";
                String lastName = "";
                String dateOfBirthStr = "";
                double experience = 0;

                // Extract values from the parts
                for (String part : parts) {
                    String[] keyValue = part.split(":");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();
//                        System.out.println("key"+key+"->"+ "value ->"+value);

                        // Clean the value to handle trailing characters

                        switch (key) {
                            case "firstName":
                                firstName = value;
                                break;
                            case "lastname":
                                lastName = value;
                                break;
                            case "dateOfBirth":
                                dateOfBirthStr = value;
                                break;
                            case "experience":
                                value = value.replaceAll("[^0-9.]", "").trim(); // Remove any non-numeric characters
                                experience = Double.parseDouble(value);
                                break;
                        }
                    }
                }

                // Parse the date
                Date dateOfBirth = dateFormat.parse(dateOfBirthStr);

                // Create Employee object
                Employee employee = new Employee(firstName, lastName, dateOfBirth, experience);
                MyCollection.add(employee);
            }
        } catch (IOException | ParseException e) {

            System.out.println(e);
        }
    }


    public void write() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Config.writeJsonPath))) {
            writer.write("[");
            for (int i = 0; i < 100; i++) {
                Employee person = MyCollection.get();
                if(person != null){

//                System.out.println("lastname ->"+person.lastName);
                    String jsonRecord = String.format(
                            "{\"firstName\":\"%s\", \"lastName\":\"%s\", \"dateOfBirth\":\"%s\", \"experience\":%f}",
                            person.getFirstName(),
                            person.getLastName(),
                            new SimpleDateFormat("MM/dd/yyyy").format(person.getDateOfBirth()),
                            person.getExperience()
                    );

                    writer.write(jsonRecord);

                    if (i < 99) {
                        writer.write(","); // Add a comma after each record except the last one
                    }
                }
            }
            writer.write("]"); // End of JSON array
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}