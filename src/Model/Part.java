/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author longh
 */
public abstract class Part {
    
    private int id, stock, min, max;
    private double price;
    private String name;
    
    public Part(int id, int stock, int min, int max, double price, String name) {
        this.id = id;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.price = price;
        this.name = name;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setPrice(double price){
        this.price = price;
    }
    
    public void setStock(int stock){
        this.stock = stock;
    }
    
    public void setMin(int min){
        this.min = min;
    }
    
    public void setMax(int max){
        this.max = max;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public double getPrice(){
        return this.price;
    }
    
    public int getStock(){
        return this.stock;
    }
    
    public int getMin(){
        return this.min;
    }
    
    public int getMax(){
        return this.max;
    }
}
