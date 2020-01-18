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
public class OutsourcedPart extends Part {
    
    private String companyName;
    
    public OutsourcedPart(int id, int stock, int min, int max, double price, String name, String companyName){
        super(id, stock, min, max, price, name);
        this.companyName = companyName;
    }
    
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    
    public String getCompanyName(){
        return this.companyName;
    }
}
