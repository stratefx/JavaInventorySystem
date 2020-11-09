/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Idea for future updates would be to add a feature that allows the user to export
 * the data to an external text file. 
 */

/**
 * This application is an inventory system. 
 * 
 * @author Blisstopher
 */
public class Main extends Application {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // InHouse and Outsourced Part objects created to auopopulate TableView before launch
        InHouse part1 = new InHouse(1, "Sprocket", 12.00, 3, 1, 10, 4);
        InHouse part2 = new InHouse(2, "Widget", 9.00, 4, 3, 5, 2);
        Outsourced part3 = new Outsourced(3, "Flux Capacitor", 52.00, 3, 1, 8, "Acme");
        Outsourced part4 = new Outsourced(4, "Transfuncuator", 24.00, 3, 3, 10, "General Widgetry");

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        
        // Product object created to autopopulate TableView before launch
        Product product1 = new Product(1, "Wonka Bar", 2.99, 4, 1, 12);
        Product product2 = new Product(2, "Blinker Fluid", 1199.99, 3, 0, 5);
        product2.addAssociatedPart(part1);
        //System.out.println(product2.getAllAssociatedParts().get(0).getName());
        
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        
        
        launch(args);
    }

    /**
     * Start method is the entry point for a Java FXML application. 
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Management System");
        stage.show();
    }
    
}
