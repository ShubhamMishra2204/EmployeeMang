package org.example.Controllers;


import org.example.Config.Config;
import org.example.Repositories.MyCollection;
import org.example.Services.CSVFileHandler;
import org.example.Services.Interfaces.MyFileHandler;
import org.example.Services.JsonFileHandler;
import org.example.Services.XMLFileHandler;

public class MyController {
    private MyCollection collection = new MyCollection();
    private MyFileHandler csvHandler = new CSVFileHandler(Config.readCsvPath);
    private MyFileHandler xmlHandler = new XMLFileHandler(Config.readXmlPath);
    private MyFileHandler jsonHandler = new JsonFileHandler(Config.readJsonPath);

    public void loadData() throws InterruptedException {
        Thread csvThread = new Thread(() -> csvHandler.read());
        Thread xmlThread = new Thread(() -> xmlHandler.read());
        Thread jsonThread = new Thread(() -> jsonHandler.read());

        csvThread.start();
        xmlThread.start();
        jsonThread.start();

        csvThread.join();
        xmlThread.join();
        jsonThread.join();

        System.out.println("Total records read= " + collection.getWriteCounter());
    }

    public void saveData() {
         Thread csvThread = new Thread(() -> csvHandler.write());
        Thread xmlThread = new Thread(() -> xmlHandler.write());
        Thread jsonThread = new Thread(() -> jsonHandler.write());

         csvThread.start();
         xmlThread.start();
         jsonThread.start();

        try {
            csvThread.join();
            xmlThread.join();
            jsonThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}