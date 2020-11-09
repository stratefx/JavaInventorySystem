/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Mike Bliss
 */
public class InHouse extends Part{
    
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
    }

    /**
     * Setter for InHouse product's machine Id. 
     * @param machineId 
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
    /**
     * Getter for InHouse product's machine Id. 
     * @return machineId Machine Id
     */
    public int getMachineId() {
        return machineId;
    }
    
}

