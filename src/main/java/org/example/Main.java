package org.example;

import org.example.Controllers.MyController;

public class Main {
    public static void main(String[] args) throws InterruptedException {


        MyController controller=new MyController();
        controller.loadData();
        controller.saveData();
    }
}