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
public class InhousePart extends Part {
    
    private int machineId;
    
    public InhousePart(int id, int stock, int min, int max, double price, String name, int machineId){
        super(id, stock, min, max, price, name);
        this.machineId = machineId;
    }
    
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }
    
    public int getMachineId(){
        return this.machineId;
    }
}
