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
public class Outsourced extends Part{
    
    private String companyName;
    
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    
    /**
     * Setter for outsourced product's company name. 
     * @param companyName 
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    /**
     * Getter for outsourced product's company name. 
     * @return companyName Company Name
     */
    public String getCompanyName() {
        return companyName;
    }
}

