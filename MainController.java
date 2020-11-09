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
public class MainController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField mainPartSearchTxt;

    @FXML
    private TableView<Part> mainPartsTable;

    /*
     * TableColumn will be working with Part objects and using
     * an reference type wrappers
     */
    @FXML
    private TableColumn<Part, Integer> mainPartIdCol;

    @FXML
    private TableColumn<Part, String> mainPartNameCol;

    @FXML
    private TableColumn<Part, Integer> mainInvCol;

    @FXML
    private TableColumn<Part, Double> mainPriceCol;

    @FXML
    private Button mainPartsAddBtn;

    @FXML
    private Button mainPartsModifyBtn;

    @FXML
    private Button mainPartsDeleteBtn;

    @FXML
    private TextField mainProdSearchTxt;

    @FXML
    private TableView<Product> mainProdTable;

    @FXML
    private TableColumn<Product, Integer> mainProdIdCol;

    @FXML
    private TableColumn<Product, String> mainProdNameCol;

    @FXML
    private TableColumn<Product, Integer> mainProdInvCol;

    @FXML
    private TableColumn<Product, Double> mainProdPriceCol;

    @FXML
    private Button mainProdAddBtn;

    @FXML
    private Button mainProdModifyBtn;

    @FXML
    private Button mainProdDeleteBtn;
    
    @FXML
    private Button mainExitBtn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /* This is where we tell the TableView which ObservableList it will
        *  be working with using the setItems() method and use for auto populated
        *  data at runtime
        *
        *  syntax to be used: tableViewReference.setItems(observableList getter)
        */
        mainPartsTable.setItems(Inventory.getAllParts());
        
        /* Sets up table columns so they know data source for auto populated data
        *  create PropertyValueFactory object within setCellValueFactory and use <>
        *  like in an array list, to which we pass the field name of what we want,
        *  calling it's getter method and populating the cell with that data
        */
        mainPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        /*
         * Tells product TableView of main menu which observable list it will be
         * referencing and tells each column where they will get their data from
         */
        mainProdTable.setItems(Inventory.getAllProducts());
        
        mainProdIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainProdNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainProdInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
    }    

    /**
     * The change screen method allows for a single method to be called from 
     * event handlers to change screens rather than typing the code every time
     * @param event An event object.
     * @param path Source to change screen to.
     * @throws IOException 
     */
    void changeScreen(ActionEvent event, String path) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow(); 
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * onAction for the add part button on the main menu screen.
     * @param event An event object.
     * @throws IOException To handle input/output exceptions.
     */
     @FXML
    void onActionMainAddPart(ActionEvent event) throws IOException {
        changeScreen(event,"/view/AddPartMenu.fxml");
    }

    /**
     * onAction for the add product button on the main menu screen.
     * @param event An event object.
     * @throws IOException Handles input/output exceptions.
     */
    @FXML
    void onActionMainAddProduct(ActionEvent event) throws IOException {
        changeScreen(event, "/view/AddProductMenu.fxml");
    }

    /**
     * onAction for the delete part button on the main menu screen.
     * @param event An event object.
     */
    @FXML
    void onActionMainDeletePart(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm delete");
        alert.setContentText("Are you sure you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Part p = Inventory.lookupPart(mainPartsTable.getSelectionModel().getFocusedIndex() + 1);
            Inventory.deletePart(p);
        }

    }

    /**
     * onAction for the delete product button on the main menu screen.
     * @param event An event object.
     */
    @FXML
    void onActionMainDeleteProduct(ActionEvent event) {
        
        Product p = Inventory.lookupProduct(mainProdTable.getSelectionModel().getFocusedIndex() + 1);
        if(p.getAllAssociatedParts() != null){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Oops!");
                error.setContentText("Cannot delete product with assiciated parts.");
                Optional<ButtonType> result2 = error.showAndWait();      
        }else {      
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm delete");
            alert.setContentText("Are you sure you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();       
            if(result.isPresent() && result.get() == ButtonType.OK) {
                //Product p = Inventory.lookupProduct(mainProdTable.getSelectionModel().getFocusedIndex() + 1);
                Inventory.deleteProduct(p);
            }
        }
    }

    /**
     * onAction for the modify part button on the main menu screen.
     * @param event An event object.
     * @throws IOException Handles input/output exceptions.
     */
    @FXML
    void onActionMainModifyPart(ActionEvent event) throws IOException {
        if(mainPartsTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartMenu.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            ModifyPartMenuController modPart = loader.getController();
            modPart.autoPopulateDisplay(mainPartsTable.getSelectionModel().getSelectedItem());
            stage = (Stage)((Button)event.getSource()).getScene().getWindow(); 
            stage.setScene(scene);
            stage.show();
        }
        
    }

    /**
     * onAction for the modify product button on the main menu screen.
     * @param event An event object.
     * @throws IOException Handles input/output exceptions.
     */
    @FXML
    void onActionMainModifyProduct(ActionEvent event) throws IOException {

        if(mainProdTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductMenu.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            ModifyProductMenuController modProd = loader.getController();
            modProd.autoPopulateDisplay(mainProdTable.getSelectionModel().getSelectedItem());
            stage = (Stage)((Button)event.getSource()).getScene().getWindow(); 
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * onAction for the search parts text field.
     * @param event An event object.
     */
    @FXML
    void onActionMainPartsSearch(ActionEvent event) {
        String str = mainPartSearchTxt.getText();
    
        if(mainPartSearchTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You must enter an ID or Name to search.");
            Optional<ButtonType> result = alert.showAndWait(); 
        }
        
        try {
            int var = Integer.parseInt(str);
            Part partVar = Inventory.lookupPart(var);
            mainPartsTable.getSelectionModel().select(partVar);
        }
        catch(NumberFormatException e) {
            mainPartsTable.setItems(Inventory.lookupPart(str));
        } 
     
    }

    /**
     * onAction for the search product text field.
     * @param event An event object.
     */
    @FXML
    void onActionMainProductSearch(ActionEvent event) {
        // Line below causes entire loop to print
        //Inventory.lookupProduct(Integer.parseInt(mainProdSearchTxt.getText()));
        
        String str = mainProdSearchTxt.getText();
        
        if(mainProdSearchTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You must enter an ID or Name to search.");
            Optional<ButtonType> result = alert.showAndWait(); 
        }
                
        try {
            int var = Integer.parseInt(str); 
            Product prodVar = Inventory.lookupProduct(var);
            mainProdTable.getSelectionModel().select(prodVar);
        }
        catch(NumberFormatException e) {
            mainProdTable.setItems(Inventory.lookupProduct(str));
            //ObservableList var = allParts;
            //mainPartsTable.getSelectionModel().select(partVar);
        } 
    }
    
    /**
     * onAction for the exit button on the main menu screen. 
     * Terminates the program.
     * @param event An event object.
     */
        @FXML
    void onActionMainExit(ActionEvent event) {
        System.exit(0);
    }
    
}
