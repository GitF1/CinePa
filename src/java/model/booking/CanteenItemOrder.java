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
    private int voucherID;
    
    public CanteenItemOrder() {
        super();
    }

    public CanteenItemOrder(int amount, int id) {
        super();
        setItemID(id);
        this.amount = amount;
    }

    public int getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(int voucherID) {
        this.voucherID = voucherID;
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

    @Override
    public String toString() {
        return "CanteenItemOrder{" + "amount=" + amount + "id: " + id + '}';
    }

}
