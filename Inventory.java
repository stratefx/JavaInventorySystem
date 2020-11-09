/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Mike Bliss
 */
public class Inventory {
    
    /**
     * Each of these ObservableLists contain a Part object or a Product object
     * and reference an observableArrayList object of the FXCollections class
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
 
    /**
     * Adds new part to ObservableList allParts. 
     * @param newPart 
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    
    /**
     * Adds new product to ObservableList allProductsl 
     * @param newProduct 
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    
    /**
     * Searches ObservableList allParts to part, sorted by Id. 
     * @param partId
     * @return p A part. 
     */
    public static Part lookupPart(int partId) {
        Part p = null;
        boolean found = false;
        for(Part partLookup: Inventory.getAllParts()){
            if(partLookup.getId() == partId) {
                p = partLookup;
                found = true;
            }
        }
        
        if(!found){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("It seems we cannot find what you are looking for.");
                Optional<ButtonType> result = alert.showAndWait(); 
            }
        return p;         // returnS a part "P" to onActionMainPartsSearch
    }
    
    /**
     * Searches ObservableList allProducts for a product by Id. 
     * @param productId
     * @return p A product.
     */
    public static Product lookupProduct(int productId) {
        Product p = null;
        boolean found = false;
        for(Product productLookup: Inventory.getAllProducts()){
            if(productLookup.getId() == productId)
                p = productLookup;
                //System.out.println(productLookup.getName() + "lookupProduct int id works");
        }
        
        if(!found){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("It seems we cannot find what you are looking for.");
            Optional<ButtonType> result = alert.showAndWait(); 
        }
        
        return p;         // This returns the part "p" to the method that called it
    }
    
    /**
     * Searches ObservableList allParts for a part by Name. 
     * @param partName
     * @return filteredParts A part.
     */
    public static ObservableList<Part> lookupPart(String partName) {

        boolean found = false;
        
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        for(Part partLookup: Inventory.getAllParts()){
            if(partLookup.getName().contains(partName)){
                filteredParts.add(partLookup);
                found = true;
        }
    }
        
        if(!found){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("It seems we cannot find what you are looking for.");
            Optional<ButtonType> result = alert.showAndWait(); 
        }
        
        if(filteredParts.isEmpty())
            return allParts;
        
        return filteredParts;
    }
    
    /**
     * Searches ObservableList allProducts by Name. 
     * @param productName
     * @return A product. 
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        
        boolean found = false;
        
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        for(Product productLookup: Inventory.getAllProducts()){
            if(productLookup.getName().contains(productName)){
                filteredProducts.add(productLookup);
                found = true;
            }
        }
        
        if(!found){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("It seems we cannot find what you are looking for.");
            Optional<ButtonType> result = alert.showAndWait(); 
        }
       
        if(filteredProducts.isEmpty())
            return allProducts;
        
        return filteredProducts;
    }
    
    /**
     * Replaces one part with a new one at a specified index. 
     * @param index
     * @param selectedPart 
     */
    public static void updatePart(int index, Part selectedPart) {
        for(Part part : Inventory.getAllParts()){
            if(allParts.indexOf(part) == index) {
                Inventory.getAllParts().set(index, selectedPart);
            }
        }
    }
    
    /**
     * Replaces one product with a new one at a specified index. 
     * @param index
     * @param newProduct 
     */
    public static void updateProduct(int index, Product newProduct) {
        for(Product product : Inventory.getAllProducts()){
            if(allParts.indexOf(product) == index) {
                Inventory.getAllProducts().set(index, newProduct);
            }
        }
    }
    
    /**
     * Removes a part from ObservalbeList allParts. 
     * @param selectedPart
     * @return 
     */
    public static boolean deletePart(Part selectedPart) {
        
        // might need an exception here to inform if selected part is not found
        // will want to run through a loop and if statement to return true or false
        for(Part part : Inventory.getAllParts()){
            if(part.getId() == selectedPart.getId()){
                return Inventory.getAllParts().remove(part);
            }    
        }
        
        return false;
    }
    
    /**
     * Removes a product from ObservalbeList allProducts. 
     * @param selectedProduct
     * @return 
     */
    public static boolean deleteProduct(Product selectedProduct) {
         for(Product prod : Inventory.getAllProducts()){
             if(prod.getId() == selectedProduct.getId())
                 return Inventory.getAllProducts().remove(prod);
         }
         
         return false;
    }
    
    /**
     * Returns ObservableList allParts
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    /**
     * Returns ObservableList allProducts
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}

