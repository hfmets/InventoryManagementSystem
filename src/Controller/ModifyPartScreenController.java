/*
 * To change this license header, choose License Headers in Project Properties.
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
public class ModifyPartScreenController implements Initializable {
    
    // All the various components of the modify part screen
    @FXML
    private RadioButton inHouse;
    @FXML
    private RadioButton outSource;
    @FXML
    private TextField modPartId;
    @FXML
    private TextField modPartName;
    @FXML
    private TextField modPartInv;
    @FXML
    private TextField modPartPrice;
    @FXML
    private TextField modPartMin;
    @FXML
    private TextField modPartMax;
    @FXML
    private TextField modPartAddField;
    @FXML
    private Label addFieldLabel;
    @FXML
    private Button savePart;
    @FXML
    private Button cancelPart;
    
    // Variable to hold the part being modified
    private Part thisPart;
    
    // Method that passes in some key information from the main screen controller and sets up this controller
    public void loadFields(Part selectedPart){
        if(selectedPart instanceof Model.InhousePart) {
            this.inHouse.setSelected(true);
            this.addFieldLabel.setText("Machine ID");
            this.modPartAddField.setText(Integer.toString(((InhousePart) selectedPart).getMachineId()));
        } else if(selectedPart instanceof Model.OutsourcedPart) {
            this.outSource.setSelected(true);
            this.addFieldLabel.setText("Company Name");
            this.modPartAddField.setText(((OutsourcedPart) selectedPart).getCompanyName());
        }
        this.modPartId.setText(Integer.toString(selectedPart.getId()));
        this.modPartName.setText(selectedPart.getName());
        this.modPartInv.setText(Integer.toString(selectedPart.getStock()));
        this.modPartPrice.setText(Double.toString(selectedPart.getPrice()));
        this.modPartMin.setText(Integer.toString(selectedPart.getMin()));
        this.modPartMax.setText(Integer.toString(selectedPart.getMax()));
        
        this.thisPart = selectedPart;
    }
    
    // Method changes a label depending on whether the part is an Inhouse or Outsourced Part
    @FXML
    private void change(ActionEvent event) {
        if(event.getSource().equals(this.inHouse)) {
            this.addFieldLabel.setText("Machine ID");
        } else if(event.getSource().equals(this.outSource)) {
            this.addFieldLabel.setText("Company Name");
        }
    }
    
    // Method cancels the process of modifying a part
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
    
    // Method saves the changes made and updates the part
    @FXML
    private void update(ActionEvent event) {
        Part newPart;
        if(this.inHouse.isSelected()) {
            newPart = new InhousePart(Integer.parseInt(this.modPartId.getText()), Integer.parseInt(this.modPartInv.getText()),
                Integer.parseInt(this.modPartMin.getText()), Integer.parseInt(this.modPartMax.getText()), Double.parseDouble(this.modPartPrice.getText()),
                this.modPartName.getText(), Integer.parseInt(this.modPartAddField.getText()));
            Inventory.updatePart(this.thisPart.getId() - 1, newPart);
        } else if(this.outSource.isSelected()) {
            newPart = new OutsourcedPart(Integer.parseInt(this.modPartId.getText()), Integer.parseInt(this.modPartInv.getText()),
                Integer.parseInt(this.modPartMin.getText()), Integer.parseInt(this.modPartMax.getText()), Double.parseDouble(this.modPartPrice.getText()),
                this.modPartName.getText(), this.modPartAddField.getText());
            Inventory.updatePart(this.thisPart.getId() - 1, newPart);
        }
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
