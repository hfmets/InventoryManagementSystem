/*
 * To change this license header, choose Licensect Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author longh
 */
public class AddPartScreenController implements Initializable {
    
    // All the various components for the add part screen
    @FXML
    private TextField partName;
    @FXML
    private TextField partInv;
    @FXML
    private TextField partPrice;
    @FXML
    private TextField partMax;
    @FXML
    private TextField partMin;
    @FXML
    private TextField partAddField;
    @FXML
    private Label addFieldLabel;
    @FXML
    private Button savePart;
    @FXML
    private Button cancelPart;
    @FXML
    private RadioButton inHouse;
    @FXML
    private RadioButton outSource;
    
    // Method changes the appropriate label depending on whether the part is an Inhouse or Outsourced Part
    @FXML
    private void change(ActionEvent event) {
        if(event.getSource().equals(this.inHouse)) {
            this.addFieldLabel.setText("Machine ID");
        } else if(event.getSource().equals(this.outSource)) {
            this.addFieldLabel.setText("Company Name");
        }
    }
    
    // Method cancels out of the add part process
    @FXML
    private void cancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ConfirmModal.fxml"));
        Parent confirmModal = loader.load();
        Scene confirmModalScene = new Scene(confirmModal);
        ConfirmModalController confirmModalController = loader.getController();
        Stage newStage = new Stage();
        newStage.setScene(confirmModalScene);
        newStage.showAndWait();
        
        if(confirmModalController.confirmation){
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }
    
    // Method saves the new part and updates the inventory
    @FXML
    private void update(ActionEvent event) {
        Part newPart;
        if(this.inHouse.isSelected()) {
            newPart = new InhousePart(1 + Inventory.getAllParts().size(), Integer.parseInt(this.partInv.getText()), Integer.parseInt(this.partMin.getText()),
                Integer.parseInt(this.partMax.getText()), Double.parseDouble(this.partPrice.getText()), this.partName.getText(), Integer.parseInt(this.partAddField.getText()));
            Inventory.addPart(newPart);
        } else if(this.outSource.isSelected()) {
            newPart = new OutsourcedPart(1 + Inventory.getAllParts().size(), Integer.parseInt(this.partInv.getText()), Integer.parseInt(this.partMin.getText()),
                Integer.parseInt(this.partMax.getText()), Double.parseDouble(this.partPrice.getText()), this.partName.getText(), this.partAddField.getText());
            Inventory.addPart(newPart);
        }
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.outSource.setSelected(true);
    }    
    
}
