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
import Model.Product;
import java.io.IOException;
import java.net.URL;
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
public class MainScreenController implements Initializable {
    
    // All the various components for the main screen
    @FXML
    private TextField partsSearch;
    @FXML
    private Button partsSearchBtn;
    @FXML
    private TableView partsTbl;
    @FXML
    private TableColumn partId;
    @FXML
    private TableColumn partName;
    @FXML
    private TableColumn partInvLev;
    @FXML
    private TableColumn partPrice;
    @FXML
    private Button addPartBtn;
    @FXML
    private Button modPartBtn;
    @FXML
    private Button delPartBtn;
    @FXML
    private TextField productsSearch;
    @FXML
    private Button productsSearchBtn;
    @FXML
    private TableView productsTbl;
    @FXML
    private TableColumn prodId;
    @FXML
    private TableColumn prodName;
    @FXML
    private TableColumn prodInvLev;
    @FXML
    private TableColumn prodPrice;
    @FXML
    private Button addProductBtn;
    @FXML
    private Button modProductBtn;
    @FXML
    private Button delProductBtn;
    @FXML
    private Button exitBtn;
    
    // These ObservableLists correspond with the two tables on the main screen
    ObservableList<Part> obsPart = Inventory.getAllParts();
    ObservableList<Product> obsProduct = Inventory.getAllProducts();
    
    
    // Method is attached to the two "add" buttons and does its job after distinguishing which one it was called from
    @FXML
    private void add(ActionEvent event) throws IOException{
        FXMLLoader loader;
        Stage stage = new Stage();
        if(event.getSource().equals(this.addPartBtn)){
            loader = new FXMLLoader(getClass().getResource("/View/AddPartScreen.fxml"));
            Parent addPart = loader.load();
            Scene addPartScene = new Scene(addPart);
            AddPartScreenController addPartController = loader.getController();
            stage.setScene(addPartScene);
            stage.show();
        } else if(event.getSource().equals(this.addProductBtn)){
            loader = new FXMLLoader(getClass().getResource("/View/AddProductScreen.fxml"));
            Parent addProduct = loader.load();
            Scene addProductScene = new Scene(addProduct);
            AddProductScreenController addProductController = loader.getController();            
            stage.setScene(addProductScene);
            stage.show();
        }
    }
    
    // Method is attached to the two "modify" buttons and does its job after distinguishing which one it was called from
    @FXML
    private void mod(ActionEvent event) throws IOException{
        FXMLLoader loader;
        Stage stage = new Stage();
        if(event.getSource().equals(this.modPartBtn)){
            loader = new FXMLLoader(getClass().getResource("/View/ModifyPartScreen.fxml"));
            Parent modPart = loader.load();
            Scene modPartScene = new Scene(modPart);
            ModifyPartScreenController modPartController = loader.getController();
            Part selectedPart = (Part) this.partsTbl.getSelectionModel().getSelectedItem();
            stage.setScene(modPartScene);
            modPartController.loadFields(selectedPart);
            stage.show();
        } else if(event.getSource().equals(this.modProductBtn)){
            loader = new FXMLLoader(getClass().getResource("/View/ModifyProductScreen.fxml"));
            Parent modProduct = loader.load();
            Scene modProductScene = new Scene(modProduct);
            ModifyProductScreenController modProdController = loader.getController();
            Product selectedProduct = (Product) this.productsTbl.getSelectionModel().getSelectedItem();
            modProdController.loadFields(selectedProduct, obsPart);
            stage.setScene(modProductScene);
            stage.show();
        }
    }
    
    // Method is attached to the two "search" buttons and does its job after distinguishing which one it was called from
    // also uses the corresponding search field to find out what the user is searching for
    @FXML
    private void search(ActionEvent event) {
        if(event.getSource().equals(this.partsSearchBtn)) {
            if(this.partsSearch.getText().isEmpty()) {
                this.partsTbl.setItems(Inventory.getAllParts());
            } else {
                this.partsTbl.setItems(Inventory.lookupPart(this.partsSearch.getText()));
            }
        } else if(event.getSource().equals(this.productsSearchBtn)) {
            if(this.productsSearch.getText().isEmpty()) {
                this.productsTbl.setItems(Inventory.getAllProducts());
            } else {
                this.productsTbl.setItems(Inventory.lookupProduct(this.productsSearch.getText()));
            }
        }
    }
    
    // Method is attached to the two "delete" buttons and does its job after distinguishing which one it was called from
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
            if(event.getSource().equals(this.delPartBtn)) {
                Inventory.deletePart((Part)this.partsTbl.getSelectionModel().getSelectedItem());
            } else if(event.getSource().equals(this.delProductBtn)) {
                Inventory.deleteProduct((Product)this.productsTbl.getSelectionModel().getSelectedItem());
            }
        }
    }
    
    @FXML
    private void exit(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        this.partsTbl.setItems(obsPart);
        this.productsTbl.setItems(obsProduct);
        /*
        InhousePart myPart = new InhousePart(1, 25, 5, 50, 34.99, "Flange extender", 249);
        OutsourcedPart theirPart = new OutsourcedPart(2, 35, 6, 67, 49.56, "Weedle bot", "Shelby Company Ltd");
        Product myProduct = new Product(1, 25, 5, 50, 99.99, "Hammer Time");
        inventory.addPart(myPart);
        inventory.addPart(theirPart);
        inventory.addProduct(myProduct);
        */
        
        // Sets up the table cells to automatically fill with the correct values from the inventory based on the two
        // ObservableLists set up earlier
        this.partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.partInvLev.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        this.prodId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.prodInvLev.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.prodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    
    
}
