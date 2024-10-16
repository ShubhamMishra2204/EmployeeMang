package org.example.Controllers;

public class EntryPoint {
    public static void main(String[] args) throws InterruptedException {
        MyController myController = new MyController();
        myController.loadData();
        myController.saveData();
    }
}
