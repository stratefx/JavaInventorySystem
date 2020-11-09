/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Mike Bliss
 */
public class AddProductMenuController implements Initializable {

    Stage stage;
    Parent scene;
    Product prodObj = new Product(0, "", 0.00, 0, 0, 0);
    
    @FXML
    private TextField addProductIdTxt;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductPriceTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProdSearchTxt;

    @FXML
    private TableView<Part> AddProductTable;

    @FXML
    private TableColumn<Part, Integer> addProductIdCol;

    @FXML
    private TableColumn<Part, String> addProductNameCol;

    @FXML
    private TableColumn<Part, Integer> addProductInvCol;

    @FXML
    private TableColumn<Part, Double> addProductPriceCol;

    @FXML
    private Button addProductBtn;

    @FXML
    private TableView<Part> associatedPartTable;

    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;

    @FXML
    private TableColumn<Part, String> associatedPartNameCol;

    @FXML
    private TableColumn<Part, Integer> associatedPartInvCol;

    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;

    @FXML
    private Button removeAssociatePartBtn;

    @FXML
    private Button addProductSaveBtn;

    @FXML
    private Button addProductCancelBtn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        AddProductTable.setItems(Inventory.getAllParts());
        addProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        associatedPartTable.setItems(prodObj.getAllAssociatedParts());
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    
    
    /**
     * onAction for the cancel button on the add product screen.
     * @param event An event object.
     * @throws IOException Handles input/output exceptions.
     */
    @FXML
    void onActionAddProdCancel(ActionEvent event) throws IOException {
        changeScreen(event, "/view/Main.fxml");
    }

    /**
     * onAction button for removing associated parts on the add product menu.
     * @param event An event object.
     */
    @FXML
    void onActionAddProdRemoveAssocPart(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm delete");
        alert.setContentText("Are you sure you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK) {
            prodObj.getAllAssociatedParts().remove(associatedPartTable.getSelectionModel().getSelectedItem());
          }
    }

    /**
     * onAction for the save button on the add product menu.
     * @param event An event object.
     * @throws IOException Handles input/output exceptions.
     */
    @FXML
    void onActionAddProdSave(ActionEvent event) throws IOException {
        
        try {
            int count= 0;
            for(Product index : Inventory.getAllProducts()) {
                count++;
            }

            int id = count + 1;
            String name = addProductNameTxt.getText();
            double price = Double.parseDouble(addProductPriceTxt.getText());
            int stock = Integer.parseInt(addProductInvTxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());
            int max = Integer.parseInt(addProductMaxTxt.getText());
            Product product = new Product(id, name, price, stock, min, max);
            product.getAllAssociatedParts().addAll(prodObj.getAllAssociatedParts());

            
            if(min > max) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Oops!");
                error.setContentText("Min cannot be greater than max.");
                Optional<ButtonType> error1 = error.showAndWait();
            } else if (min > stock) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Oops!");
                error.setContentText("Stock must be greater than or equal to min.");
                Optional<ButtonType> error2 = error.showAndWait();
            } else if(stock > max) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Oops!");
                error.setContentText("Stock must be less than or equal to max.");
                Optional<ButtonType> error3 = error.showAndWait();
            } else {
                Inventory.addProduct(product);
                Part p = AddProductTable.getSelectionModel().getSelectedItem();
                product.addAssociatedPart(p);
                changeScreen(event, "/view/Main.fxml");
            }
            
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Please enter information in the correct format.");
            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    /**
     * onAction button adds association between parts and a product.
     * @param event An event object.
     */
    @FXML
    void onActionAddProduct(ActionEvent event) {
        Part p = AddProductTable.getSelectionModel().getSelectedItem();
        prodObj.addAssociatedPart(p);

    }

    /**
     * onAction product search text field on the add product menu.
     * @param event An event object.
     */
    @FXML
    void onActionAddProdSearch(ActionEvent event) {
        String str = addProdSearchTxt.getText();
        
    
        try {
            int var = Integer.parseInt(str);
            Part partVar = Inventory.lookupPart(var);
            AddProductTable.getSelectionModel().select(partVar);
        }
        catch(NumberFormatException e) {
            AddProductTable.setItems(Inventory.lookupPart(str));
        } 
    }
    
    /**
     * Redirects user to specified screen.
     * @param event An event object.
     * @param path Destination for redirect.
     * @throws IOException Handles input/output exceptions.
     */
    void changeScreen(ActionEvent event, String path) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow(); 
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
}
