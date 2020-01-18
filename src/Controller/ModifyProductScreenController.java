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
public class ModifyProductScreenController implements Initializable {

    // All the various components for the modify products screen
    @FXML
    private TextField modProdId;
    @FXML
    private TextField modProdName;
    @FXML
    private TextField modProdInv;
    @FXML
    private TextField modProdPrice;
    @FXML
    private TextField modProdMax;
    @FXML
    private TextField modProdMin;
    @FXML
    private TableView invTbl;
    @FXML
    private TableView prodTbl;
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
    private Button addPart;
    @FXML
    private Button saveProduct;
    @FXML
    private Button deletePart;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchBtn;
    @FXML
    private Button cancelProduct;
    
    // Variable for the product that is being modified
    private Product newProduct;
    
    // Methond that passes in a lot of important data and sets up the rest of this controller including the two table views
    public void loadFields(Product selectedProduct, ObservableList<Part> obsPart) {
        this.modProdId.setText(Integer.toString(selectedProduct.getId()));
        this.modProdName.setText(selectedProduct.getName());
        this.modProdInv.setText(Integer.toString(selectedProduct.getStock()));
        this.modProdPrice.setText(Double.toString(selectedProduct.getPrice()));
        this.modProdMax.setText(Integer.toString(selectedProduct.getMax()));
        this.modProdMin.setText(Integer.toString(selectedProduct.getMin()));
      
        this.invTbl.setItems(obsPart);
        this.newProduct = new Product(selectedProduct.getId(), selectedProduct.getStock(), selectedProduct.getMin(), selectedProduct.getMax(),
            selectedProduct.getPrice(), selectedProduct.getName());
        Iterator itr = selectedProduct.getAssociatedParts().iterator();
        while(itr.hasNext()) {
            newProduct.addAssociatedPart((Part)itr.next());
        }
        this.prodTbl.setItems(newProduct.getAssociatedParts());  
    }
    
    // Method to add parts to the product from the inventory table view
    @FXML
    private void add(ActionEvent event) {
        this.newProduct.addAssociatedPart((Part)this.invTbl.getSelectionModel().getSelectedItem());
    }
    
    // Method to delete parts from the products table view
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
            this.newProduct.deleteAssociatedPart((Part)this.prodTbl.getSelectionModel().getSelectedItem());
        }
    }
    
    // Method to cancel out of the modify product process
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
    
    // Method to save the changes made and update the inventory
    @FXML
    private void update(ActionEvent event) throws IOException {
        this.newProduct.setName(this.modProdName.getText());
        this.newProduct.setStock(Integer.parseInt(this.modProdInv.getText()));
        this.newProduct.setMin(Integer.parseInt(this.modProdMin.getText()));
        this.newProduct.setMax(Integer.parseInt(this.modProdMax.getText()));
        this.newProduct.setPrice(Double.parseDouble(this.modProdPrice.getText()));
        
        if(this.newProduct.getAssociatedParts().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ExceptionParts.fxml"));
            Parent exceptionModal = loader.load();
            Scene exceptionModalScene = new Scene(exceptionModal);
            Stage newStage = new Stage();
            newStage.setScene(exceptionModalScene);
            newStage.show();
        } else if(!this.newProduct.getAssociatedParts().isEmpty()) {
            Inventory.updateProduct(this.newProduct.getId() - 1, newProduct);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        
    }
    
    // Method to search the inventory table view
    @FXML
    private void search(ActionEvent event) {
        if(this.searchField.getText().isEmpty()) {
            this.invTbl.setItems(Inventory.getAllParts());
        } else {
            this.invTbl.setItems(Inventory.lookupPart(this.searchField.getText()));
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Sets up the table cells to update automatically
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
