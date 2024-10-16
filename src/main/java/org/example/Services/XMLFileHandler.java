package org.example.Services;

import org.example.Config.Config;
import org.example.Entity.Employee;
import org.example.Repositories.MyCollection;
import org.example.Services.Interfaces.MyFileHandler;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XMLFileHandler implements MyFileHandler {
    private String filePath;

    public XMLFileHandler(String filePath) {
        this.filePath = filePath;
    }


    public void read() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder xmlContent = new StringBuilder();

            while ((line = br.readLine()) != null) {
                xmlContent.append(line.trim());
            }

            String xml = xmlContent.toString();
            String recordTag = "<record>";
            String endRecordTag = "</record>";
            int startIndex = 0;

            while ((startIndex = xml.indexOf(recordTag, startIndex)) != -1) {
                int endIndex = xml.indexOf(endRecordTag, startIndex);
                String record = xml.substring(startIndex, endIndex + endRecordTag.length());
                String firstName = extractValue(record, "firstName");
                String lastName = extractValue(record, "lastname");
                String dateOfBirthStr = extractValue(record, "dateOfBirth");
                String experienceStr = extractValue(record, "experience");

                // Parse date and experience, then create Employee object if needed
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Date dateOfBirth = dateFormat.parse(dateOfBirthStr);
                double experience = Double.parseDouble(experienceStr);


                Employee employee = new Employee(firstName, lastName, dateOfBirth, experience);
                MyCollection.add(employee);

                startIndex = endIndex + endRecordTag.length();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private String extractValue(String record, String tagName) {
        String startTag = "<" + tagName + ">";
        String endTag = "</" + tagName + ">";
        int startIndex = record.indexOf(startTag) + startTag.length();
        int endIndex = record.indexOf(endTag, startIndex);
        return record.substring(startIndex, endIndex).trim();
    }


    public void write() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        MyCollection myCollection = new MyCollection(); // Create an instance of MyCollection

        try (BufferedWriter writer = new BufferedWriter(new  FileWriter(Config.writeXmlPath))) {
            writer.write("<employees>"); // Start the root element
            for(int i = 0; i < 100; i++){
                Employee employee = myCollection.get();
                if(employee != null) { // Loop until no more employees are available
                    // Write each employee as an XML record
                    String xmlRecord = String.format(
                            "<record><firstName>%s</firstName><lastName>%s</lastName><dateOfBirth>%s</dateOfBirth><experience>%.2f</experience></record>",
                            employee.getFirstName(),
                            employee.getLastName(),
                            dateFormat.format(employee.getDateOfBirth()),
                            employee.getExperience()
                    );
                    writer.write(xmlRecord);
                }
            }
            writer.write("</employees>"); // End the root element
        } catch (IOException e) {
            System.out.println("Error writing to XML file: " + e.getMessage());
        }
    }
}