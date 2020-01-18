/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author longh
 */
public class Inventory {
    
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts =FXCollections.observableArrayList();
    
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    public static Part lookupPart(int partId){
        return allParts.filtered(t -> t.getId() == partId).get(0);
    }
    
    public static Product lookupProduct(int productId){
        return allProducts.filtered(t -> t.getId() == productId).get(0);
    }
    
    public static ObservableList<Part> lookupPart(String partName){
        return allParts.filtered(t -> t.getName().toLowerCase().contains(partName.toLowerCase()));
    }
    
    public static ObservableList<Product> lookupProduct(String productName){
        return allProducts.filtered(t -> t.getName().toLowerCase().contains(productName.toLowerCase()));
    }
    
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }
    
    public static boolean deletePart(Part selectedPart){
        Iterator itr = allParts.iterator();
        
        while(itr.hasNext()){
            if(itr.next().equals(selectedPart)){
                itr.remove();
                return true;
            }
        }
        return false;
    }
    
    public static boolean deleteProduct(Product selectedProduct){
        Iterator itr = allProducts.iterator();
        
        while(itr.hasNext()){
            if(itr.next().equals(selectedProduct)){
                itr.remove();
                return true;
            }
        }
        return false;
    }
    
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
