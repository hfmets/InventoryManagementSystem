/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author longh
 */
public class ConfirmModalController implements Initializable {
    
    // All the various components for the confirmation modal
    @FXML
    private Button yesBtn;
    @FXML
    private Button noBtn;
    
    // Public variable used to tell whether the user wishes to move on or not
    public boolean confirmation;
    
    // Method gets the users input and then hides this window
    @FXML
    private void confirm(ActionEvent event) {
        if(event.getSource().equals(this.yesBtn)) {
            confirmation = true;
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } else if(event.getSource().equals(this.noBtn)) {
            confirmation = false;
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
