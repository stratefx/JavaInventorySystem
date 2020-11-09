/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Mike Bliss
 */
public class Product {
    
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Returns ObservableList associated parts. 
     * @return associatedParts An observable list. 
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * Adds a part to observable list associatedParts. 
     * @param part 
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    /**
     * Removes an associated part from ObservableList associatedParts. 
     * @param part 
     */
    public void deleteAssociatedPart(Part part) {
        associatedParts.remove(part);
    }

    /**
     * Getter for product id. 
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for product id. 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for product name. 
     * @return name A product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for a product name. 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for a product price. 
     * @return price A product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for a product price. 
     * @param price 
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter for product inventory stock on hand. 
     * @return stock Inventory currently on hand.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for product inventory  stock on hand. 
     * @param stock 
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter for min. 
     * @return min Minimum
     */
    public int getMin() {
        return min;
    }

    /**
     * Setter for min. 
     * @param min Minimum
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Getter for max. 
     * @return max Maximum
     */
    public int getMax() {
        return max;
    }

    /**
     * Setter for max. 
     * @param max 
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    
}
