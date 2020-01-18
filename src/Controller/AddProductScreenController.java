/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author longh
 */
public class AddProductScreenController implements Initializable {
    
    // All the various components for the add products screen
    @FXML
    private TextField prodName;
    @FXML
    private TextField prodInv;
    @FXML
    private TextField prodMin;
    @FXML
    private TextField prodMax;
    @FXML
    private TextField prodPrice;
    @FXML
    private TextField partSearchField;
    @FXML
    private TableColumn invPartId;
    @FXML
    private TableColumn invPartName;
    @FXML
    private TableColumn invPartLevel;
    @FXML
    private TableColumn invPartPrice;
    @FXML
    private TableColumn prodPartId;
    @FXML
    private TableColumn prodPartName;
    @FXML
    private TableColumn prodPartLevel;
    @FXML
    private TableColumn prodPartPrice;
    @FXML
    private Button partSearch;
    @FXML
    private Button partAdd;
    @FXML
    private Button partDelete;
    @FXML
    private Button saveProduct;
    @FXML
    private Button cancel;
    @FXML
    private TableView invTbl;
    @FXML
    private TableView prodTbl;
    
    // ObservableList for the second table view that shows parts included in the product being made
    ObservableList<Part> prodList = FXCollections.observableArrayList();
    // Variable for the new product being created
    Product newProduct;
    
    // Method for the add button to add a part from the inventory list to the product list
    @FXML
    private void add(ActionEvent event) {
        this.prodList.add((Part)this.invTbl.getSelectionModel().getSelectedItem());
    }
    
    // Method to delete a part from the product list
    @FXML
    private void delete(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ConfirmModal.fxml"));
        Parent confirmModal = loader.load();
        Scene confirmModalScene = new Scene(confirmModal);
        ConfirmModalController confirmModalController = loader.getController();
        Stage newStage = new Stage();
        newStage.setScene(confirmModalScene);
        newStage.showAndWait();
        
        if(confirmModalController.confirmation){
            this.prodList.remove((Part)this.prodTbl.getSelectionModel().getSelectedItem());
        }
    }
    
    // Method to save the product and update the inventory with the new product
    // Has a check to make sure the product contains at least one part
    @FXML
    private void update(ActionEvent event) throws IOException {
        
        this.newProduct = new Product(1 + Inventory.getAllProducts().size(), Integer.parseInt(this.prodInv.getText()),
            Integer.parseInt(this.prodMin.getText()), Integer.parseInt(this.prodMax.getText()), Double.parseDouble(this.prodPrice.getText()), this.prodName.getText());
        
        Iterator itr = this.prodList.iterator();
        while(itr.hasNext()) {
            newProduct.addAssociatedPart((Part) itr.next());
        }
        
        if(newProduct.getAssociatedParts().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ExceptionParts.fxml"));
            Parent exceptionModal = loader.load();
            Scene exceptionModalScene = new Scene(exceptionModal);
            Stage newStage = new Stage();
            newStage.setScene(exceptionModalScene);
            newStage.show();
        } else if(!newProduct.getAssociatedParts().isEmpty()) {
            Inventory.addProduct(newProduct);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }
    
    // Method to cancel out of the product creation process
    @FXML
    private void cancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ConfirmModal.fxml"));
        Parent confirmModal = loader.load();
        Scene confirmModalScene = new Scene(confirmModal);
        ConfirmModalController confirmModalController = loader.getController();
        Stage newStage = new Stage();
        newStage.setScene(confirmModalScene);
        newStage.showAndWait();
        
        if(confirmModalController.confirmation) {
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }
    
    // Method to search the inventory table
    @FXML
    private void search(ActionEvent event) {
        if(this.partSearchField.getText().isEmpty()) {
            this.invTbl.setItems(Inventory.getAllParts());
        } else {
            this.invTbl.setItems(Inventory.lookupPart(this.partSearchField.getText()));
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.invTbl.setItems(Inventory.getAllParts());
        this.prodTbl.setItems(this.prodList);
        
        // Sets up the cells for the table view to automatically fill
        this.invPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.invPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.invPartLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.invPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        this.prodPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.prodPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.prodPartLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.prodPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    
    
}
