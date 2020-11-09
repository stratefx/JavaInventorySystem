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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;


/**
 * FXML Controller class
 *
 * @author Mike Bliss
 */
public class ModifyPartMenuController implements Initializable {

    Stage stage;
    Parent scene;
    
    private Part partObj;
    
    @FXML
    private RadioButton modifyPartInHouseRBtn;

    @FXML
    private ToggleGroup radioTG;

    @FXML
    private RadioButton modifyPartOutsourcedRbtn;

    @FXML
    private TextField modifyPartIdTxt;

    @FXML
    private TextField modifyPartNameTxt;

    @FXML
    private TextField modifyPartInvTxt;

    @FXML
    private TextField modifyPartPriceTxt;

    @FXML
    private TextField modifyPartMaxTxt;

    @FXML
    private TextField modifyPartMachineIdTxt;

    @FXML
    private TextField modifyPartMinTxt;

    @FXML
    private Button modifyPartSaveBtn;

    @FXML
    private Button modifyPartCancelBtn;
    
    @FXML
    private Label modPartLabel;

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /*
        Initialize method is set up to auto populate the text fields but I need to 
        know how to access the specific part from the main menu that is selected
        before opening the modify screen
        */
        
        /*
        int count= 0;
        for(Part index : Inventory.getAllParts()) {
            count++;
        }
        int id = count + 1;
        */
        
    }    
    
    /**
     * Automatically populates text fields with the selected part object's
     * information for editing purposes at initialization. 
     * @param p A part object.
     */
    public void autoPopulateDisplay(Part p) {
        
        partObj = p;
        modifyPartNameTxt.setText(String.valueOf(partObj.getName()));
        modifyPartIdTxt.setText(Integer.toString(partObj.getId()));
        modifyPartInvTxt.setText(String.valueOf(partObj.getStock()));
        modifyPartPriceTxt.setText(String.valueOf(partObj.getPrice()));
        modifyPartMaxTxt.setText(String.valueOf(partObj.getMax()));
        modifyPartMinTxt.setText(String.valueOf(partObj.getMin()));
        
        if(partObj instanceof InHouse){
            modifyPartInHouseRBtn.setSelected(true);
            modifyPartMachineIdTxt.setText(String.valueOf(((InHouse)partObj).getMachineId()));
            modPartLabel.setText("InHouse");
        } else if(partObj instanceof Outsourced) {
            modifyPartOutsourcedRbtn.setSelected(true);
             modifyPartMachineIdTxt.setText(((Outsourced)partObj).getCompanyName());
            modPartLabel.setText("Outsourced");
        }
    }
    
    /**
     * onAction for cancel button on the modify part menu. 
     * Returns user to the main screen.
     * @param event An event object.
     * @throws IOException Handles input/output exceptions.
     */
    @FXML
    void onActionModPartCancel(ActionEvent event) throws IOException {
        changeScreen(event, "/view/Main.fxml");
    }

    /**
     * onAction InHouse radio button for the modify part menu. 
     * @param event An event object.
     */
    @FXML
    void onActionModPartInHouseRadio(ActionEvent event) {
        modPartLabel.setText("Machine ID");
    }

    /**
     * onAction Outsourced radio button for the modify part menu. 
     * @param event An event object.
     */
    @FXML
    void onActionModPartOutsourcedRadio(ActionEvent event) {
        modPartLabel.setText("Company Name");
    }

    /**
     * onAction save button for the modify part menu. 
     * Replaces selected part object in ObservableList with new object.
     * @param event An event object.
     * @throws IOException Handles input/output exceptions.
     */
    @FXML
    void onActionModPartSave(ActionEvent event) throws IOException {
        
        String companyName;
        
        try{
            partObj.setName(modifyPartNameTxt.getText());
            partObj.setId(partObj.getId());
            partObj.setMax(Integer.parseInt(modifyPartMaxTxt.getText()));
            partObj.setMin(Integer.parseInt(modifyPartMinTxt.getText()));
            partObj.setPrice(Double.parseDouble(modifyPartPriceTxt.getText()));
            partObj.setStock(Integer.parseInt(modifyPartInvTxt.getText()));
            
            int id = partObj.getId();
            String name = partObj.getName();
            int min = partObj.getMin();
            int max = partObj.getMax();
            int inv = partObj.getStock();
            double price = partObj.getPrice();

            int machineId = Integer.parseInt(modifyPartMachineIdTxt.getText());
            int index = partObj.getId() - 1;
            
            if(partObj.getMin() > partObj.getMax()) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Oops!");
                error.setContentText("Min cannot be greater than max.");
                Optional<ButtonType> error1 = error.showAndWait();
            } else if (partObj.getMin() > partObj.getStock()) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Oops!");
                error.setContentText("Stock must be greater than or equal to min.");
                Optional<ButtonType> error2 = error.showAndWait();
            } else if(partObj.getStock() > partObj.getMax()) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Oops!");
                error.setContentText("Stock must be less than or equal to max.");
                Optional<ButtonType> error3 = error.showAndWait();
            } else {
                // DETERMINE IN HOUSE OR OUTSOURCED HERE
                if(modifyPartInHouseRBtn.isSelected()) {
                    machineId = Integer.parseInt(modifyPartMachineIdTxt.getText());
                    InHouse partObjInHouse = new InHouse(id, name, price, inv, min, max, machineId);               
                    Inventory.updatePart(index, partObjInHouse);
                } else if (modifyPartOutsourcedRbtn.isSelected()) {
                    companyName = modifyPartMachineIdTxt.getText();
                    Outsourced partObjOutsourced = new Outsourced(id, name, price, inv, min, max, companyName);
                    Inventory.updatePart(index, partObjOutsourced);
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
     * Redirects user to specified destination. 
     * Reduces redundant code.
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
