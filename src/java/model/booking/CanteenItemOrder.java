/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.booking;

import model.CanteenItem;

/**
 *
 * @author PC
 */
public class CanteenItemOrder extends CanteenItem {

    private int amount;

    public CanteenItemOrder() {
        super();
    }

    public CanteenItemOrder(int amount, int id, String name, double price, String image) {
        super(id, name, price, image);
        this.amount = amount;
    }

    public CanteenItemOrder(int amount) {
        super();
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
    
    

}
