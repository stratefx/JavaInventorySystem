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
public class ModifyProductMenuController implements Initializable {
    
    Stage stage;
    Parent scene;
    Product prodObject = new Product(0, "", 0.00, 0, 0, 0);

    @FXML
    private TextField modProdIdTxt;

    @FXML
    private TextField modProdNameTxt;

    @FXML
    private TextField modProdInvTxt;

    @FXML
    private TextField modProdPriceTxt;

    @FXML
    private TextField modProdMaxTxt;

    @FXML
    private TextField modProdMinTxt;

    @FXML
    private TextField modProdSearchTxt;

    @FXML
    private TableView<Part> modProdPartTable;

    @FXML
    private TableColumn<Part, Integer> modProdPartIdCol;

    @FXML
    private TableColumn<Part, String> modProdPartNameCol;

    @FXML
    private TableColumn<Part, Integer> modProdPartInvCol;

    @FXML
    private TableColumn<Part, Double> modProdPartPriceCol;

    @FXML
    private Button modProdAddBtn;

    @FXML
    private TableView<Part> modProdAssocPartTable;

    @FXML
    private TableColumn<Part, Integer> modProdAssocPartIdCol;

    @FXML
    private TableColumn<Part, String> modProdAssocPartNameCol;

    @FXML
    private TableColumn<Part, Integer> modPartAssocPartInvCol;

    @FXML
    private TableColumn<Part, Double> modPartAssocPartPriceCol;

    @FXML
    private Button modProdRemoveAssocPartBtn;

    @FXML
    private Button modProdSaveBtn;

    @FXML
    private Button modProdCancelBtn;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        modProdPartTable.setItems(Inventory.getAllParts());
        modProdPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProdPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProdPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProdPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        modProdAssocPartTable.setItems(prodObject.getAllAssociatedParts());
        modProdAssocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProdAssocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modPartAssocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modPartAssocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    
    
    /**
     * Automatically populates text fields with selected objects information for editing
     * at initialization. 
     * @param p A product object.
     */
        public void autoPopulateDisplay(Product p) {
        
        prodObject = p;
        modProdNameTxt.setText(String.valueOf(prodObject.getName()));
        modProdIdTxt.setText(Integer.toString(prodObject.getId()));
        modProdInvTxt.setText(String.valueOf(prodObject.getStock()));
        modProdPriceTxt.setText(String.valueOf(prodObject.getPrice()));
        modProdMaxTxt.setText(String.valueOf(prodObject.getMax()));
        modProdMinTxt.setText(String.valueOf(prodObject.getMin()));
        
        modProdAssocPartTable.setItems(prodObject.getAllAssociatedParts());
        
    }
    
    /**
     * onAction for add button on modify product menu. 
     * @param event An event object.
     */
    @FXML
    void onActionModProdAdd(ActionEvent event) {
        Part p = modProdPartTable.getSelectionModel().getSelectedItem();
        prodObject.addAssociatedPart(p);
        //System.out.println(prodObject.getAllAssociatedParts().get(0).getName());
    }

    /**
     * onAcion for cancel button on modify product menu. 
     * Returns user to main menu.
     * @param event An event object.
     * @throws IOException Handles input/output exceptions.
     */
    @FXML
    void onActionModProdCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow(); 
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * onAction button for removing associated parts on the modify product menu. 
     * @param event An event object.
     */
    @FXML
    void onActionModProdRemoveAssocPart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm delete");
        alert.setContentText("Are you sure you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK) {
            prodObject.getAllAssociatedParts().remove(modProdAssocPartTable.getSelectionModel().getSelectedItem());
          }
    }

    /**
     * onAction for the save button on the modify product menu.
     * @param event An event object.
     * @throws IOException Handles input/output exceptions.
     */
    @FXML
    void onActionModProdSave(ActionEvent event) throws IOException {
        
        
        try{
            prodObject.setId(prodObject.getId());
            prodObject.setName(modProdNameTxt.getText());
            prodObject.setStock(Integer.parseInt(modProdInvTxt.getText()));
            prodObject.setPrice(Double.parseDouble(modProdPriceTxt.getText()));
            prodObject.setMax(Integer.parseInt(modProdMaxTxt.getText()));
            prodObject.setMin(Integer.parseInt(modProdMinTxt.getText()));

            int index = prodObject.getId() - 1;
            
            
            if(prodObject.getMin() > prodObject.getMax()) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Oops!");
                error.setContentText("Min cannot be greater than max.");
                Optional<ButtonType> error1 = error.showAndWait();
            } else if (prodObject.getMin() > prodObject.getStock()) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Oops!");
                error.setContentText("Stock must be greater than or equal to min.");
                Optional<ButtonType> error2 = error.showAndWait();
            } else if(prodObject.getStock() > prodObject.getMax()) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Oops!");
                error.setContentText("Stock must be less than or equal to max.");
                Optional<ButtonType> error3 = error.showAndWait();
            } else {
                Inventory.updateProduct(index, prodObject);
                Part p = modProdPartTable.getSelectionModel().getSelectedItem();
                if(prodObject.getAllAssociatedParts() != null) {
                    prodObject.addAssociatedPart(p);
                }
                changeScreen(event, "/view/Main.fxml");
            }
        }catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Please enter information in the correct format.");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * onAction for part search field on modify product menu. 
     * @param event An event object.
     */
    @FXML
    void onActionModProdSearch(ActionEvent event) {
        String str = modProdSearchTxt.getText();
    
        try {
            int var = Integer.parseInt(str);
            Part partVar = Inventory.lookupPart(var);
            modProdPartTable.getSelectionModel().select(partVar);
        }
        catch(NumberFormatException e) {
            modProdPartTable.setItems(Inventory.lookupPart(str));
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
